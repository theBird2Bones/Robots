package domainLogic;

import gui.visualizers.RobotPanel;
import objects.entities.Player;
import utility.PlayerManager;
import utility.PointExtends;
import utility.Utility;

import java.awt.*;
import java.awt.geom.Point2D;

public class RobotController {
  private static final double MAX_VELOCITY = 0.1;
  private static final double MAX_ANGULAR_VELOCITY = 0.001;
  private final RobotPanel robotPanel;

  private final PlayerManager playerManager;
  private final Point2D.Double targetPosition = new Point2D.Double(150, 100);

  public RobotController(RobotPanel robotPanel, PlayerManager playerManager) {
    this.robotPanel = robotPanel;
    this.playerManager = playerManager;
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

  public Point2D.Double getTargetPosition() {
    return targetPosition;
  }

  public void setTargetPosition(Point2D p) {
    targetPosition.setLocation(p);
  }

  public Point2D.Double getRobotPosition() {
    return PointExtends.toDouble(playerManager.getPlayer().getPosition());
  }

  public double getRobotDirection() {
    return playerManager.getPlayer().getDirection();
  }

  public void onModelUpdateEvent() {
    double distance =
        distance(
            targetPosition.x, targetPosition.y,
            playerManager.getPlayer().getPosition().x, playerManager.getPlayer().getPosition().y);
    if (distance < 0.5) {
      return;
    }
    double angleToTarget =
        angleTo(
            playerManager.getPlayer().getPosition().x, playerManager.getPlayer().getPosition().y,
            targetPosition.x, targetPosition.y);
    double angularVelocity = 0;

    if (angleToTarget > playerManager.getPlayer().getDirection()) {
      angularVelocity = MAX_ANGULAR_VELOCITY;
    }
    if (angleToTarget < playerManager.getPlayer().getDirection()) {
      angularVelocity = -MAX_ANGULAR_VELOCITY;
    }

    moveRobot(MAX_VELOCITY, angularVelocity, 10);
  }

  private void moveRobot(double velocity, double angularVelocity, double duration) {
    velocity = Utility.applyLimits(velocity, 0, MAX_VELOCITY);
    angularVelocity =
        Utility.applyLimits(angularVelocity, -MAX_ANGULAR_VELOCITY, MAX_ANGULAR_VELOCITY);

    double newX =
        playerManager.getPlayer().getPosition().x
            + velocity
                / angularVelocity
                * (Math.sin(playerManager.getPlayer().getDirection() + angularVelocity * duration)
                    - Math.sin(playerManager.getPlayer().getDirection()));
    if (!Double.isFinite(newX)) {
      newX = playerManager.getPlayer().getPosition().x + velocity * duration * Math.cos(playerManager.getPlayer().getDirection());
    }

    double newY =
        playerManager.getPlayer().getPosition().y
            - velocity
                / angularVelocity
                * (Math.cos(playerManager.getPlayer().getDirection() + angularVelocity * duration)
                    - Math.cos(playerManager.getPlayer().getDirection()));
    if (!Double.isFinite(newY)) {
      newY = playerManager.getPlayer().getPosition().y + velocity * duration * Math.sin(playerManager.getPlayer().getDirection());
    }

    playerManager.getPlayer().setLocation(PointExtends.round(keepInsideWindow(newX, newY)));
    double newDirection = asNormalizedRadians(playerManager.getPlayer().getDirection() + angularVelocity * duration);
    playerManager.getPlayer().setDirection(newDirection);
  }

  private Point2D.Double keepInsideWindow(double X, double Y) {
    return new Point2D.Double(
        Utility.applyLimits(X, 0, robotPanel.getWidth()),
        Utility.applyLimits(Y, 0, robotPanel.getHeight()));
  }
}
