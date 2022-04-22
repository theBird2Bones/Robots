package gui.visualizers;

import lombok.Getter;
import objects.entities.Player;
import objects.tiles.DirtTile;
import objects.tiles.PassableTile;
import objects.tiles.StoneTile;
import objects.tiles.Tile;
import utility.MapCreator;
import utility.MapGenerator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.sql.Array;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class GamePanel extends JPanel {
    private final ArenaPainter arenaPainter;
    private final Timer timer = initTimer();

    private MapCreator mapCreator;

    @Getter
    private Tile[][] map;

    @Getter
    private Map<Point2D, Rectangle> pointToRectangle;

    @Getter
    private Player player;

    private static Timer initTimer() {
        return new Timer("game paint event generator", true);
    }

    public GamePanel(Dimension d) {
        setSize(d);
        setBackground(Color.black);
        var pack =MapGenerator.generate();
        map = pack.getFirst();
        var route = pack.getSecond();
        mapCreator = new MapCreator(this);
        pointToRectangle = mapCreator.generatePointToRectangle();
        player = new Player(findAvailablePoint());
        new Thread(() -> {
            try {
                while(true){
                    for(var e: route){
                        player.move( e.getFirst(),e.getSecond());
                        Thread.sleep(100);
                    }
                }
            } catch (InterruptedException e) {
                System.out.println("oh my");
            }
        }).start();

        arenaPainter = new ArenaPainter(this);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(!isFocusable()){
                    requestFocusInWindow();
                }
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
                if (e.getKeyCode() == 76) {
                    map = MapGenerator.generate().getFirst();
                    pointToRectangle = mapCreator.generatePointToRectangle();
                    player.setPosition(findAvailablePoint());

                    arenaPainter.updateBackground();
                    onRedrawEvent();
                } else if (e.getKeyCode() == 87) {
                    player.move(0, -1);
                } else if (e.getKeyCode() == 65) {
                    player.move(-1, 0);
                } else if (e.getKeyCode() == 83) {
                    player.move(0, 1);
                } else if (e.getKeyCode() == 68) {
                    player.move(1, 0);
                }
            }
        });
    }

    private Point findAvailablePoint() {
        for (int x = 0; x < map.length; x++) {
            for (int y = 0; y < map[0].length; y++) {
                if (map[x][y] instanceof PassableTile) {
                    return new Point(x, y);
                }
            }
        }
        return new Point(0, 0);
    }

    protected void onRedrawEvent() {
        EventQueue.invokeLater(this::repaint);
    }

    public Dimension getMapSize() {
        return new Dimension(map.length, map[0].length);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        arenaPainter.paint((Graphics2D) g);
    }
}
