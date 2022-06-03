package gui;

import objects.entities.Player;
import utility.PlayerManager;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Constructor;

public class RobotsProgram {
  public static void main(String[] args) {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (Exception e) {
      e.printStackTrace();
    }

    SwingUtilities.invokeLater(
        () -> {
          MainApplicationFrame frame = new MainApplicationFrame();
          frame.pack();
          frame.setVisible(true);
          frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        });
  }
}
