package positionObserving;

public interface PositionNotifier {
  void notifyPosition();
  void subscribe(PositionListener listener);
}
