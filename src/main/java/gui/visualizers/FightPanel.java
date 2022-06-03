package gui.visualizers;

import lombok.Getter;
import objects.entities.Entity;
import objects.entities.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicReference;

public class FightPanel extends JPanel {
  private final FightPainter fightPainter;
  private final Timer timer = initTimer();

  @Getter private final AtomicReference<Entity> player;
  @Getter private final List<AtomicReference<Entity>> enemies;

  public FightPanel(
      Dimension d, AtomicReference<Entity> player, List<AtomicReference<Entity>> enemies) {
    this.player = player;
    this.enemies = enemies;

    setSize(d);
    setBackground(Color.darkGray);

    fightPainter = new FightPainter(this, (Player) player.getAcquire());

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
    fightPainter.paint((Graphics2D) g);
  }

  protected void onRedrawEvent() {
    EventQueue.invokeLater(this::repaint);
  }

  private Timer initTimer() {
    return new Timer("game paint event generator", true);
  }
}
