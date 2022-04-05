package gui.visualizers;

import lombok.Getter;
import objects.tiles.DirtTile;
import objects.tiles.FloorTile;
import objects.tiles.StoneTile;
import utility.MapCreator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class GamePanel extends JPanel {
    private final ArenaPainter arenaPainter;
    private final Timer timer = initTimer();

    private MapCreator mapCreator;

    @Getter
    private FloorTile[][] map = {
            {new StoneTile(), new StoneTile(), new StoneTile(), new DirtTile(), new StoneTile()},
            {new StoneTile(), new StoneTile(), new DirtTile(), new DirtTile(), new StoneTile()},
            {new DirtTile(), new DirtTile(), new DirtTile(), new StoneTile(), new StoneTile()},
            {new StoneTile(), new StoneTile(), new StoneTile(), new StoneTile(), new StoneTile()},
            {new StoneTile(), new StoneTile(), new StoneTile(), new StoneTile(), new StoneTile()}
    };

    @Getter
    private Map<Point2D, Rectangle> pointToRectangle;

    private static Timer initTimer() {
        return new Timer("events generator", true);
    }

    public GamePanel(){
        mapCreator = new MapCreator(this);
        pointToRectangle = mapCreator.generatePointToRectangle();

        arenaPainter = new ArenaPainter(this);


        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                onRedrawEvent();
            }
        }, 0, 50);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                requestFocusInWindow();
            }
        });

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyChar() == 'l'){
                    map = mapCreator.generateMap();
                    pointToRectangle = mapCreator.generatePointToRectangle();
                    arenaPainter.updateBackground();
                    onRedrawEvent();
                }
            }
        });

    }

    protected void onRedrawEvent() {
        EventQueue.invokeLater(this::repaint);
    }

    public Dimension getSize(){
        return new Dimension(map.length, map[0].length);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        arenaPainter.paint((Graphics2D) g);
    }
}
