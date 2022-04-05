package gui.visualizers;

import objects.tiles.FloorTile;
import objects.tiles.StoneTile;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.Map;

public class ArenaPanel {
    private FloorTile[][] map = {
            {new StoneTile(), new StoneTile(), new StoneTile(), new StoneTile(), new StoneTile()},
            {new StoneTile(), new StoneTile(), new StoneTile(), new StoneTile(), new StoneTile()},
            {new StoneTile(), new StoneTile(), new StoneTile(), new StoneTile(), new StoneTile()},
            {new StoneTile(), new StoneTile(), new StoneTile(), new StoneTile(), new StoneTile()},
            {new StoneTile(), new StoneTile(), new StoneTile(), new StoneTile(), new StoneTile()}
    };

    private Map<Point2D, Rectangle> pointToRectangle;

    public ArenaPanel(){

    }

    private static Map<Point2D, Rectangle> generatePointToRectangle(FloorTile[][] map){
        var result = new HashMap<Point2D, Rectangle>();
        for (int x = 0; x < map.length; x++){
            for (int y = 0; y < map[0].length; y++){

            }
        }
    }
}
