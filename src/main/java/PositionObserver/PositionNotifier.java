package PositionObserver;

public interface PositionNotifier {
  void notifyPosition();
  void subscribe(PositionListener listener);
}
