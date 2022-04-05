package objects.tiles;

import java.awt.geom.Point2D;

public abstract class FloorTile {
    public final Point2D point;
    private boolean passability;

    public FloorTile(Point2D p, boolean isPassable){
        point = p;
        passability = isPassable;
    }

    public boolean isPassable(){
        return passability;
    }

    public void setPassability(boolean isPassable){
        passability = isPassable;
    }
}
