package gui.visualizers;

import domainLogic.GameController;
import utility.Point2DExtends;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

public class RobotVisualizer {
    private final GameController gameController;

    public RobotVisualizer(GameController gameController){
        this.gameController = gameController;
    }

    public void paint(Graphics2D g2d){
        drawRobot(
                g2d,
                gameController.getRobotPosition(),
                gameController.getRobotDirection()
        );
        drawTarget(g2d, gameController.getTargetPosition());
    }

    private void drawRobot(Graphics2D g, Point2D.Double robotPos, double direction) {
        var roundRobotPos = Point2DExtends.round(robotPos);

        AffineTransform t = AffineTransform.getRotateInstance(direction, roundRobotPos.x, roundRobotPos.y);
        g.setTransform(t);
        g.setColor(Color.MAGENTA);
        fillOval(g, roundRobotPos, 30, 10);
        g.setColor(Color.BLACK);
        drawOval(g, roundRobotPos, 30, 10);

        roundRobotPos.translate(10, 0);

        g.setColor(Color.WHITE);
        fillOval(g, roundRobotPos, 5, 5);
        g.setColor(Color.BLACK);
        drawOval(g, roundRobotPos, 5, 5);
    }

    private void drawTarget(Graphics2D g, Point2D.Double targetPos) {
        AffineTransform t = AffineTransform.getRotateInstance(0, 0, 0);
        g.setTransform(t);
        g.setColor(Color.GREEN);
        fillOval(g, Point2DExtends.round(targetPos), 5, 5);
        g.setColor(Color.BLACK);
        drawOval(g, Point2DExtends.round(targetPos), 5, 5);
    }

    private static void fillOval(Graphics g, Point center, int diam1, int diam2) {
        g.fillOval(center.x - diam1 / 2, center.y - diam2 / 2, diam1, diam2);
    }

    private static void drawOval(Graphics g, Point center, int diam1, int diam2) {
        g.drawOval(center.x - diam1 / 2, center.y - diam2 / 2, diam1, diam2);
    }
}
