package objects.entities;

import lombok.Getter;
import lombok.Setter;
import objects.weapons.Weapon;

import java.awt.*;

public abstract class Entity {
  @Getter private final int MAX_AP;
  @Getter private final double MAX_HEALTH;
  @Getter private final double ATTACK;
  @Getter private final double DEFENSE;
  @Getter private final double INITIATIVE;

  @Getter @Setter private Point position;

  @Getter @Setter private double direction;
  @Getter
  private double health;
  @Getter private int ap;

  private Weapon weapon;

  public Entity(
      Point position,
      int maxAp,
      Weapon weapon,
      double maxHealth,
      double attack,
      double defence,
      double initiative) {
    MAX_AP = maxAp;
    MAX_HEALTH = maxHealth;
    ATTACK = attack;
    DEFENSE = defence;
    INITIATIVE = initiative;

    this.position = position;
    direction = 0;
    health = maxHealth;
    ap = maxAp;
    this.weapon = weapon;
  }

  public boolean isAlive(){
    return getHealth() > 0;
  }

  public void getDamageFrom(Entity entity){
    if(!isAlive()) return;
    health -= entity.ATTACK;
  }


  public void setHealth(double health) {
    this.health = health < 0 ? 0 : health > MAX_HEALTH ? MAX_HEALTH : health;
  }

  public void move(Point dP) {
    move(dP.x, dP.y);
  }

  public void move(double dx, double dy) {
    position.x += dx;
    position.y += dy;
  }

  public void setLocation(Point location) {
    position.setLocation(location);
  }

  public void setLocation(double x, double y) {
    position.setLocation(x, y);
  }
}
