package objects.entities;

import objects.weapons.Weapon;

import java.awt.geom.Point2D;

public class Player extends Entity{
    public Player(Point2D.Double position) {
        super(position, 0, new Weapon(), 0, 0, 0, 0, 0);
    }
}
