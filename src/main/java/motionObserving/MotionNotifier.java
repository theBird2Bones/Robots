package motionObserving;

public interface MotionNotifier {
    void subscribe(MotionListener listener);
    void setFrequency(int ms);
    void setMotionFlag(boolean flag);
    void startTick();
}
