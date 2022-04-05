package utility;

import gui.visualizers.GamePanel;
import objects.tiles.DirtTile;
import objects.tiles.FloorTile;
import objects.tiles.PrecipiceTile;
import objects.tiles.StoneTile;
import utility.consts.GlobalConst;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MapCreator {
    private GamePanel gamePanel;

    public MapCreator(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public FloorTile[][] generateMap() {
        int width = roundUnderWindow(gamePanel.getWidth());
        int height = roundUnderWindow(gamePanel.getHeight());
        FloorTile[][] tileMap = new FloorTile[width][height];

        for (var xRow : tileMap) {
            Arrays.fill(xRow, new PrecipiceTile());
        }

        var rnd = new Random();
        for (var x = 2; x < width - 2; x++ ){
            var lower = rnd.nextInt(1, 3);
            var upper = rnd.nextInt(lower + 1, height);
            for (int y = lower; y < upper; y++)
                tileMap[x][y] = chooseTile();
        }

        return tileMap;
    }

    private FloorTile chooseTile(){
        double digit = new Random().nextDouble(0, 1);
        if(digit <=  0.7){
            return new StoneTile();
        } else {
            return new DirtTile();
        }
    }

    private int roundUnderWindow(int size) {
        return (int) (size / GlobalConst.TILE_SIZE);
    }

    public Map<Point2D, Rectangle> generatePointToRectangle() {
        var result = new HashMap<Point2D, Rectangle>();
        var map = gamePanel.getMap();

        for (int x = 0; x < map.length; x++) {
            for (int y = 0; y < map[0].length; y++) {
                var rec = new Rectangle(
                        x * GlobalConst.TILE_SIZE, y * GlobalConst.TILE_SIZE,
                        GlobalConst.TILE_SIZE, GlobalConst.TILE_SIZE
                );
                result.put(new Point(x, y), rec);
            }
        }
        return result;
    }
}
