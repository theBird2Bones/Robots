package utility;

import log.FightListener;
import objects.entities.Entity;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

/**
 * this is used to get independent fight tickers due to entities can fight with difference frequency
 * and asynchronously
 */
public class FightTicker {
  /** initialFrequncy in ms */
  private long initialFrequency = 1500;

  private AtomicReference<Entity> tickingEntity;

  private AtomicReference<? extends FightListener> fightListener;

  private List<AtomicReference<Entity>> fightTargets;

  public FightTicker(
      AtomicReference<Entity> tickingEntity,
      AtomicReference<? extends FightListener> fightListener) {
    this.tickingEntity = tickingEntity;
    this.fightListener = fightListener;
    this.fightTargets = new LinkedList<>();

    initialFrequency -= tickingEntity.getAcquire().getINITIATIVE() * 200;
  }

  public void setFightTarget(AtomicReference<Entity> target) {
    fightTargets.add(target);
  }

  public void setFightTargets(List<AtomicReference<Entity>> targets) {
    fightTargets.addAll(targets);
  }

  public Thread startFight() {
    var shouldContinue = true;
    Set<AtomicReference<Entity>> deads = new HashSet<>();
    return new Thread(
        () -> {
          w:
          while (shouldContinue) {
            if (!tickingEntity.getAcquire().isAlive()) {
              break;
            }
            for (var target : fightTargets) {
              if (!target.getAcquire().isAlive()) {
                deads.add(target);
                if (deads.size() == fightTargets.size()) {
                  break w;
                }
                continue;
              }
              try {
                Thread.sleep(initialFrequency);
              } catch (InterruptedException e) {
                throw new RuntimeException(e);
              }
              target.getAcquire().getDamageFrom(tickingEntity.getAcquire());
              fightListener
                  .getAcquire()
                  .notifyWith(
                      "%s caught punch from %s%nDamage: %s%nRemain: %nHealth:%s%n"
                          .formatted(
                              target.getAcquire().toString(),
                              tickingEntity.getAcquire().toString(),
                              tickingEntity.getAcquire().getATTACK(),
                              target.getAcquire().getHealth()));
            }
          }
        });
  }
}
