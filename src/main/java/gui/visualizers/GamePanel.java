package gui.visualizers;

import lombok.Getter;
import objects.entities.Player;
import objects.tiles.DirtTile;
import objects.tiles.FloorTile;
import objects.tiles.StoneTile;
import utility.MapCreator;
import utility.consts.GlobalConst;

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

    @Getter
    private Player player;

    private static Timer initTimer() {
        return new Timer("game paint event generator", true);
    }

    public GamePanel() {
        mapCreator = new MapCreator(this);
        pointToRectangle = mapCreator.generatePointToRectangle();
        player = new Player(findAvailablePoint());

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
                if (e.getKeyChar() == 'l') {
                    map = mapCreator.generateMap();
                    pointToRectangle = mapCreator.generatePointToRectangle();
                    player.setPosition(findAvailablePoint());

                    arenaPainter.updateBackground();
                    onRedrawEvent();
                } else if (e.getKeyChar() == 'w') {
                    player.move(0, -1);
                } else if (e.getKeyChar() == 'a') {
                    player.move(-1, 0);
                } else if (e.getKeyChar() == 's') {
                    player.move(0, 1);
                } else if (e.getKeyChar() == 'd') {
                    player.move(1, 0);
                }
            }
        });
    }

    private Point findAvailablePoint() {
        for (int x = 0; x < map.length; x++) {
            for (int y = 0; y < map[0].length; y++) {
                if (map[x][y].isPassable()) {
                    return new Point(x, y);
                }
            }
        }
        return new Point(0, 0);
    }

    protected void onRedrawEvent() {
        EventQueue.invokeLater(this::repaint);
    }

    public Dimension getSize() {
        return new Dimension(map.length, map[0].length);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        arenaPainter.paint((Graphics2D) g);
    }
}
