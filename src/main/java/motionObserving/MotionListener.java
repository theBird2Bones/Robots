package motionObserving;

public interface MotionListener {
  void subscribe(MotionNotifier listener);

  void setFrequency(int ms);

  void setMotionFlag(boolean flag);

  void startTick();
}
