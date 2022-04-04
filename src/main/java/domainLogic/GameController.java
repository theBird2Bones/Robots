package domainLogic;

import gui.GamePanel;

import java.awt.geom.Point2D;

public class GameController {
    private final GamePanel gamePanel;
    private static final double MAX_VELOCITY = 0.1;
    private static final double MAX_ANGULAR_VELOCITY = 0.001;

    private volatile Point2D.Double robotPosition = new Point2D.Double(100, 100);
    private volatile double robotDirection = 0;

    private volatile Point2D.Double targetPosition = new Point2D.Double(150, 100);


    public GameController(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public Point2D.Double getTargetPosition(){
        return targetPosition;
    }

    public void setTargetPosition(Point2D p){
        targetPosition.setLocation(p);
    }

    public Point2D.Double getRobotPosition(){
        return robotPosition;
    }

    public double getRobotDirection(){
        return robotDirection;
    }

    public void onModelUpdateEvent() {
        double distance = distance(
                targetPosition.x, targetPosition.y,
                robotPosition.x, robotPosition.y
        );
        if (distance < 0.5) {
            return;
        }
        double angleToTarget = angleTo(
                robotPosition.x, robotPosition.y,
                targetPosition.x, targetPosition.y
        );
        double angularVelocity = 0;

        if (angleToTarget > robotDirection) {
            angularVelocity = MAX_ANGULAR_VELOCITY;
        }
        if (angleToTarget < robotDirection) {
            angularVelocity = -MAX_ANGULAR_VELOCITY;
        }

        moveRobot(MAX_VELOCITY, angularVelocity, 10);
    }

    private void moveRobot(double velocity, double angularVelocity, double duration) {
        velocity = applyLimits(velocity, 0, MAX_VELOCITY);
        angularVelocity = applyLimits(angularVelocity, -MAX_ANGULAR_VELOCITY, MAX_ANGULAR_VELOCITY);

        double newX = robotPosition.x + velocity / angularVelocity *
                (Math.sin(robotDirection + angularVelocity * duration) - Math.sin(robotDirection));
        if (!Double.isFinite(newX)) {
            newX = robotPosition.x + velocity * duration * Math.cos(robotDirection);
        }

        double newY = robotPosition.y - velocity / angularVelocity *
                (Math.cos(robotDirection + angularVelocity * duration) - Math.cos(robotDirection));
        if (!Double.isFinite(newY)) {
            newY = robotPosition.y + velocity * duration * Math.sin(robotDirection);
        }

        robotPosition.setLocation(keepInsideWindow(newX, newY));
        double newDirection = asNormalizedRadians(robotDirection + angularVelocity * duration);
        robotDirection = newDirection;
    }

    private static double applyLimits(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

    private Point2D.Double keepInsideWindow(double X, double Y){
        return new Point2D.Double(
                applyLimits(X, 0, gamePanel.getWidth()),
                applyLimits(Y, 0, gamePanel.getHeight())
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
