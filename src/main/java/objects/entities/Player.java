package objects.entities;

import objects.weapons.Weapon;

import java.awt.geom.Point2D;

public class Player extends Entity{
    public Player(
            Point2D.Double position, int maxAp, Weapon weapon,
                  double maxHealth, double attack, double defence, double damage, double initiative
    ) {
        super(position, maxAp, weapon, maxHealth, attack, defence, damage, initiative);
    }
}
