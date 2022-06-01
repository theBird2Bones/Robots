package utility.storage;

import gui.JInternalFrameExtended;

import java.awt.*;

public class StorableData {
  public final Class<? extends JInternalFrameExtended> clazz;
  public final Point position;
  public final Dimension size;
  public final boolean isVisible;
  public final boolean isMaximum;

  public StorableData(
      Class<? extends JInternalFrameExtended> clazz,
      Point position,
      Dimension size,
      boolean isVisible,
      boolean isMaximum) {
    this.clazz = clazz;
    this.position = position;
    this.size = size;
    this.isVisible = isVisible;
    this.isMaximum = isMaximum;
  }

  public static <T extends JInternalFrameExtended> StorableData of(T frame) {
    return new StorableData(
        frame.getClass(),
        frame.getLocation(),
        frame.getSize(),
        frame.isVisible(),
        frame.isMaximum());
  }

  public static <T extends JInternalFrameExtended> StorableData defaultValue(T frame) {
    return defaultValue(frame.getClass());
  }

  public static StorableData defaultValue(Class<? extends JInternalFrameExtended> frame) {
    return new StorableData(frame, new Point(10, 10), new Dimension(250, 600), true, false);
  }
}
