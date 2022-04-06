package objects.entities;

import objects.weapons.Weapon;

import java.awt.*;

public class Player extends Entity{
    public Player(Point position) {
        super(position, 0, new Weapon(), 0, 0, 0, 0, 0);
    }
}
