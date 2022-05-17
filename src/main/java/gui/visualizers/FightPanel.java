package gui.visualizers;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Timer;
import java.util.TimerTask;

public class FightPanel extends JPanel {
  private final Timer timer = initTimer();
  public FightPanel(Dimension d){
    setSize(d);
    setBackground(Color.darkGray);

    timer.schedule(
        new TimerTask() {
          @Override
          public void run() {
            if (!isFocusable()) {
              requestFocusInWindow();
            }
            onRedrawEvent();
          }
        },
        0,
        16);

    addMouseListener(
        new MouseAdapter() {
          @Override
          public void mouseClicked(MouseEvent e) {
            requestFocusInWindow();
          }
        });

  }

  @Override
  public void paint(Graphics g) {
    super.paint(g);
    g.setColor(Color.red);
    g.drawOval(15,15,15,15);
  }

  protected void onRedrawEvent() {
    EventQueue.invokeLater(this::repaint);
  }

  private Timer initTimer() {
    return new Timer("game paint event generator", true);
  }
}
