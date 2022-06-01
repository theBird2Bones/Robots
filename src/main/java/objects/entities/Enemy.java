package objects.entities;

import objects.weapons.Weapon;

import java.awt.*;

public abstract class Enemy extends Entity {
  public Enemy(
      Point position,
      int maxAp,
      Weapon weapon,
      double maxHealth,
      double attack,
      double defence,
      double initiative) {
    super(position, maxAp, weapon, maxHealth, attack, defence, initiative);
  }
}
