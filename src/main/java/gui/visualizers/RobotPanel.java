package gui.visualizers;

import domainLogic.RobotController;
import utility.PlayerManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Timer;
import java.util.TimerTask;

public class RobotPanel extends JPanel {
  private final Timer timer = initTimer();
  private final RobotController robotController;
  private final RobotVisualizer robotVisualizer;

  public RobotPanel(Dimension d, PlayerManager playerManager) {
    robotController = new RobotController(this, playerManager);
    robotVisualizer = new RobotVisualizer(robotController);

    timer.schedule(
        new TimerTask() {
          @Override
          public void run() {
            onRedrawEvent();
          }
        },
        0,
        50);
    timer.schedule(
        new TimerTask() {
          @Override
          public void run() {
            robotController.onModelUpdateEvent();
          }
        },
        0,
        10);
    addMouseListener(
        new MouseAdapter() {
          @Override
          public void mouseClicked(MouseEvent e) {
            robotController.setTargetPosition(e.getPoint());
            repaint();
          }
        });

    setDoubleBuffered(true);
    setSize(d);
  }

  private static Timer initTimer() {
    return new Timer("events generator", true);
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
