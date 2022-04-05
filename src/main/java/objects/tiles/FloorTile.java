package objects.tiles;

public abstract class FloorTile implements Tile {
    private boolean passability;

    public FloorTile(boolean isPassable){
        passability = isPassable;
    }

    public boolean isPassable(){
        return passability;
    }

    public void setPassability(boolean isPassable){
        passability = isPassable;
    }
}
