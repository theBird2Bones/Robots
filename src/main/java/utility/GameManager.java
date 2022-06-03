package utility;

import gui.MainApplicationFrame;
import gui.innerWindows.FightLogWindow;
import gui.innerWindows.FightWindow;
import localizer.ObservableLocalization;
import motionObserving.MotionListener;
import motionObserving.MotionManagerShouldBeConfigured;
import motionObserving.MotionNotifier;
import objects.entities.Enemy;
import objects.entities.Entity;
import objects.tiles.PassableTile;
import objects.tiles.Tile;

import java.beans.PropertyVetoException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class GameManager implements MotionListener {
  private static final Object syncObj = new Object();
  private static volatile GameManager instance;
  private PlayerManager playerManager;
  private Tile[][] map;

  private volatile boolean shouldMove;
  private int frequency;
  private List<MotionNotifier> listeners;

  private GameManager() { }

  private GameManager(Tile[][] map, PlayerManager playerManager, boolean motionFlag, int frequency) {
    this.map = map;
    this.playerManager = playerManager;
    this.shouldMove = motionFlag;
    this.frequency = frequency;
    listeners = new LinkedList<>();
  }

  public static GameManager instance() throws MotionManagerShouldBeConfigured {
    if (instance == null) {
      synchronized (syncObj) {
        if (instance == null) {
          throw new MotionManagerShouldBeConfigured();
        }
      }
    }
    return instance;
  }

  public static GameManager configInstance(
      Tile[][] map, PlayerManager playerManager, boolean motionFlag, int frequency) {
    if (instance == null) {
      synchronized (syncObj) {
        if (instance == null) {
          instance = new GameManager(map, playerManager, motionFlag, frequency);
        }
      }
    }
    return instance;
  }

  @Override
  public void subscribe(MotionNotifier listener) { }

  @Override
  public void setFrequency(int ms) {
    frequency = ms;
  }

  @Override
  public void setMotionFlag(boolean flag) {
    shouldMove = flag;
  }

  @Override
  public void startTick() {
    setMotionFlag(true);
    new Thread(
        () -> {
          while (shouldMove) {
            try {
              Thread.sleep(frequency);
            } catch (InterruptedException e) {
              throw new RuntimeException("failed on tick");
            }

            playerManager.getPlayer().move();

            if (map[playerManager.getPlayer().getPosition().x][playerManager.getPlayer().getPosition().y]
                instanceof PassableTile passableTilePlayerOn) {
              if (!passableTilePlayerOn.getEnemies().isEmpty()) {
                startFight(passableTilePlayerOn.getEnemies());
              }
            }
          }
        }).start();
  }

  private void startFight(List<Enemy> enemies){
    FightLogWindow fightLogger = new FightLogWindow(ObservableLocalization.instance().getBundle());
    fightLogger.setIsClosingNotification(false);
    AtomicReference<FightLogWindow> fightLoggerAR =
        new AtomicReference<>(fightLogger);

    MainApplicationFrame.addWindow(fightLogger);
    fightLogger.setVisible(true);
    fightLogger.setFocusable(true);
    fightLogger.setLayer(3);

    AtomicReference<Entity> playerAR = new AtomicReference<>(playerManager.getPlayer());
    FightTicker playerTicker = new FightTicker(playerAR, fightLoggerAR);

    var enemiesAR = enemies.stream()
        .map(AtomicReference<Entity>::new).toList();

    playerTicker.setFightTargets(enemiesAR);

    var enemyTickers = enemiesAR.stream()
        .map(e -> new FightTicker(e, fightLoggerAR))
        .peek(e -> e.setFightTarget(playerAR)) ;

    var fights = new LinkedList<Thread>();
    fights.add(playerTicker.startFight());
    fights.addAll(enemyTickers.map(FightTicker::startFight).toList());

    FightWindow fightWindow = new FightWindow(
            ObservableLocalization.instance().getBundle(),
            MainApplicationFrame.getDesktopPane().getSize(),
            playerAR,
            enemiesAR
    );

    fightWindow.setVisible(true);
    fightWindow.setFocusable(true);
    try {
      fightWindow.setMaximum(true);
    } catch (PropertyVetoException e) {
      e.printStackTrace();
    }
    MainApplicationFrame.addWindow(fightWindow);

    fights.forEach(Thread::start);
    fights.forEach(t -> {
      try {
        t.join();
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    });

    if(playerManager.getPlayer().isAlive()){
      var iter = enemies.iterator();
      while(iter.hasNext()){
        var entity = iter.next();
        if(!entity.isAlive()){
          iter.remove();
        }
      }
    } else {
      setMotionFlag(false);
    }


    try {
      fightWindow.setClosed(true);
      MainApplicationFrame.getDesktopPane().remove(fightWindow);
      fightLogger.setClosed(true);
      MainApplicationFrame.getDesktopPane().remove(fightLogger);
    } catch (PropertyVetoException e) {
      throw new RuntimeException(e);
    }

  }
}
