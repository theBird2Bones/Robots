package domainLogic;

import gui.visualizers.RobotPanel;
import objects.entities.Player;
import utility.PointExtends;
import utility.Utility;

import java.awt.*;
import java.awt.geom.Point2D;

public class RobotController {
    private static final double MAX_VELOCITY = 0.1;
    private static final double MAX_ANGULAR_VELOCITY = 0.001;
    private final RobotPanel robotPanel;

    private volatile Player robot;
    private volatile Point2D.Double targetPosition = new Point2D.Double(150, 100);


    public RobotController(RobotPanel robotPanel) {
        this.robotPanel = robotPanel;
        robot = new Player(new Point(100, 100));
    }

    public Point2D.Double getTargetPosition(){
        return targetPosition;
    }

    public void setTargetPosition(Point2D p){
        targetPosition.setLocation(p);
    }

    public Point2D.Double getRobotPosition(){
        return PointExtends.toDouble(robot.getPosition());
    }

    public double getRobotDirection(){
        return robot.getDirection();
    }

    public void onModelUpdateEvent() {
        double distance = distance(
                targetPosition.x, targetPosition.y,
                robot.getPosition().x, robot.getPosition().y
        );
        if (distance < 0.5) {
            return;
        }
        double angleToTarget = angleTo(
                robot.getPosition().x, robot.getPosition().y,
                targetPosition.x, targetPosition.y
        );
        double angularVelocity = 0;

        if (angleToTarget > robot.getDirection()) {
            angularVelocity = MAX_ANGULAR_VELOCITY;
        }
        if (angleToTarget < robot.getDirection()) {
            angularVelocity = -MAX_ANGULAR_VELOCITY;
        }

        moveRobot(MAX_VELOCITY, angularVelocity, 10);
    }

    private void moveRobot(double velocity, double angularVelocity, double duration) {
        velocity = Utility.applyLimits(velocity, 0, MAX_VELOCITY);
        angularVelocity = Utility.applyLimits(angularVelocity, -MAX_ANGULAR_VELOCITY, MAX_ANGULAR_VELOCITY);

        double newX = robot.getPosition().x + velocity / angularVelocity *
                (Math.sin(robot.getDirection() + angularVelocity * duration) - Math.sin(robot.getDirection()));
        if (!Double.isFinite(newX)) {
            newX = robot.getPosition().x + velocity * duration * Math.cos(robot.getDirection());
        }

        double newY = robot.getPosition().y - velocity / angularVelocity *
                (Math.cos(robot.getDirection() + angularVelocity * duration) - Math.cos(robot.getDirection()));
        if (!Double.isFinite(newY)) {
            newY = robot.getPosition().y + velocity * duration * Math.sin(robot.getDirection());
        }

        robot.setLocation(PointExtends.round(keepInsideWindow(newX, newY)));
        double newDirection = asNormalizedRadians(robot.getDirection() + angularVelocity * duration);
        robot.setDirection(newDirection);
    }

    private Point2D.Double keepInsideWindow(double X, double Y){
        return new Point2D.Double(
                Utility.applyLimits(X, 0, robotPanel.getWidth()),
                Utility.applyLimits(Y, 0, robotPanel.getHeight())
        );
    }

    private static double distance(double x1, double y1, double x2, double y2) {
        double diffX = x1 - x2;
        double diffY = y1 - y2;
        return Math.sqrt(diffX * diffX + diffY * diffY);
    }

    private static double angleTo(double fromX, double fromY, double toX, double toY) {
        double diffX = toX - fromX;
        double diffY = toY - fromY;

        return asNormalizedRadians(Math.atan2(diffY, diffX));
    }

    private static double asNormalizedRadians(double angle) {
        while (angle < 0) {
            angle += 2 * Math.PI;
        }
        while (angle >= 2 * Math.PI) {
            angle -= 2 * Math.PI;
        }
        return angle;
    }
}
