package gui.visualizers;

import domainLogic.GameController;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class RobotVisualizer {
    private final GameController gC;

    public RobotVisualizer(GameController gameController){
        gC = gameController;
    }

    public void paint(Graphics2D g2d){
        drawRobot(
                g2d,
                round(gC.getRobotPosition().x),
                round(gC.getRobotPosition().y),
                gC.getRobotDirection()
        );
        drawTarget(g2d, round(gC.getTargetPosition().x), round(gC.getTargetPosition().y));
    }

    private static int round(double value) {
        return (int) (value + 0.5);
    }

    private void drawRobot(Graphics2D g, int robotX, int robotY, double direction) {
        AffineTransform t = AffineTransform.getRotateInstance(direction, robotX, robotY);
        g.setTransform(t);
        g.setColor(Color.MAGENTA);
        fillOval(g, robotX, robotY, 30, 10);
        g.setColor(Color.BLACK);
        drawOval(g, robotX, robotY, 30, 10);
        g.setColor(Color.WHITE);
        fillOval(g, robotX + 10, robotY, 5, 5);
        g.setColor(Color.BLACK);
        drawOval(g, robotX + 10, robotY, 5, 5);
    }

    private void drawTarget(Graphics2D g, int targetX, int targetY) {
        AffineTransform t = AffineTransform.getRotateInstance(0, 0, 0);
        g.setTransform(t);
        g.setColor(Color.GREEN);
        fillOval(g, targetX, targetY, 5, 5);
        g.setColor(Color.BLACK);
        drawOval(g, targetX, targetY, 5, 5);
    }

    private static void fillOval(Graphics g, int centerX, int centerY, int diam1, int diam2) {
        g.fillOval(centerX - diam1 / 2, centerY - diam2 / 2, diam1, diam2);
    }

    private static void drawOval(Graphics g, int centerX, int centerY, int diam1, int diam2) {
        g.drawOval(centerX - diam1 / 2, centerY - diam2 / 2, diam1, diam2);
    }
}
