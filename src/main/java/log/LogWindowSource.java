package log;

import java.awt.geom.Point2D;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LogWindowSource {
  private final int queueLength;

  private final ArrayList<LogEntry> messages;
  private final ArrayList<WeakReference<LogChangeListener>> listeners;
  private volatile List<WeakReference<LogChangeListener>> activeListeners;

  public LogWindowSource(int iQueueLength) {
    queueLength = iQueueLength;
    messages = new ArrayList<>(iQueueLength);
    listeners = new ArrayList<>();
  }

  public void registerListener(LogChangeListener listener) {
    synchronized (listeners) {
      listeners.add(new WeakReference<>(listener));
      activeListeners = null;
    }
  }

  public void unregisterListener(LogChangeListener listener) {
    synchronized (listeners) {
      listeners.removeIf(elem -> elem.get() == null || elem.get().equals(listener));
      activeListeners = null;
    }
  }

  public void append(LogLevel logLevel, String strMessage) {
    LogEntry entry = new LogEntry(logLevel, strMessage);
    if (size() > queueLength) {
      dequeueMessage();
    }
    enqueueMessage(entry);

    List<WeakReference<LogChangeListener>> activeListeners = this.activeListeners;
    if (activeListeners == null) {
      synchronized (listeners) {
        if (this.activeListeners == null) {
          activeListeners = List.copyOf(listeners);
          this.activeListeners = activeListeners;
        } else {
          activeListeners = this.activeListeners;
        }
      }
    }

    notifyListener(activeListeners);
  }

  private void notifyListener(List<WeakReference<LogChangeListener>> activeListeners) {
    for (var weakRefListener : activeListeners) {
      var listener = weakRefListener.get();
      if (listener == null) {
        activeListeners.remove(weakRefListener);
      } else {
        listener.onLogChanged();
      }
    }
  }

  private LogEntry dequeueMessage() {
    return messages.remove(0);
  }

  private void enqueueMessage(LogEntry message) {
    messages.add(message);
  }

  public int size() {
    return messages.size();
  }

  public Iterable<LogEntry> range(int startFrom, int count) {
    if (startFrom < 0 || startFrom >= messages.size()) {
      return Collections.emptyList();
    }
    int indexTo = Math.min(startFrom + count, messages.size());
    return messages.subList(startFrom, indexTo);
  }

  public Iterable<LogEntry> all() {
    return messages;
  }
}
