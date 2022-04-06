package gui.visualizers;

import domainLogic.RobotController;
import utility.PointExtends;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

public class RobotVisualizer {
    private final RobotController robotController;

    public RobotVisualizer(RobotController robotController){
        this.robotController = robotController;
    }

    public void paint(Graphics2D g2d){
        drawRobot(
                g2d,
                robotController.getRobotPosition(),
                robotController.getRobotDirection()
        );
        drawTarget(g2d, robotController.getTargetPosition());
    }

    private void drawRobot(Graphics2D g, Point2D.Double robotPos, double direction) {
        var roundRobotPos = PointExtends.round(robotPos);

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
        fillOval(g, PointExtends.round(targetPos), 5, 5);
        g.setColor(Color.BLACK);
        drawOval(g, PointExtends.round(targetPos), 5, 5);
    }

    private static void fillOval(Graphics g, Point center, int diam1, int diam2) {
        g.fillOval(center.x - diam1 / 2, center.y - diam2 / 2, diam1, diam2);
    }

    private static void drawOval(Graphics g, Point center, int diam1, int diam2) {
        g.drawOval(center.x - diam1 / 2, center.y - diam2 / 2, diam1, diam2);
    }
}
