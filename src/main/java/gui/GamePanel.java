package gui;

import domainLogic.GameController;
import gui.visualizers.RobotVisualizer;

import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;

public class GamePanel extends JPanel {
    private final Timer timer = initTimer();
    private final GameController gameController;
    private final RobotVisualizer robotVisualizer;

    private static Timer initTimer() {
        return new Timer("events generator", true);
    }

    public GamePanel() {
        gameController = new GameController(this);
        robotVisualizer = new RobotVisualizer(gameController);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                onRedrawEvent();
            }
        }, 0, 50);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                gameController.onModelUpdateEvent();
            }
        }, 0, 10);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                gameController.setTargetPosition(e.getPoint());
                repaint();
            }
        });
        setDoubleBuffered(true);
    }

    protected void onRedrawEvent() {
        EventQueue.invokeLater(this::repaint);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        robotVisualizer.paint((Graphics2D) g);
    }
}
