package utility;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class InternalFramesManager {
  private static final Object syncObj = new Object();
  private static volatile InternalFramesManager instance = null;
  // TODO: переписать на слабые ссылки
  private final Map<Class<? extends JInternalFrame>, JInternalFrame> internalFrameMap =
      new HashMap<>();

  private InternalFramesManager() {}

  public static InternalFramesManager instance() {
    if (instance == null) {
      synchronized (syncObj) {
        if (instance == null) {
          instance = new InternalFramesManager();
        }
      }
    }
    return instance;
  }

  public <T extends JInternalFrame> void registerFrame(T inst) {
    internalFrameMap.put(inst.getClass(), inst);
  }

  public <T extends JInternalFrame> T getFrameInstance(Class<T> key) {
    return (T) internalFrameMap.getOrDefault(key, null);
  }

  public <T extends JInternalFrame> void unregisterFrame(Class<T> key) {
    internalFrameMap.remove(key);
  }
}
