package log;

public interface FightNotifier {
  void subscribe(FightListener fighter);

  void unsubscribe(FightListener fighter);
}
