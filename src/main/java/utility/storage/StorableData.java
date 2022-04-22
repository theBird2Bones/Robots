package utility.storage;

import gui.JInternalFrameExtended;

import java.awt.*;

public class StorableData {
    public final Class<? extends JInternalFrameExtended> clazz;
    public final Point position;
    public final boolean isVisible;

    public StorableData(Class<? extends JInternalFrameExtended> clazz, Point position, boolean isVisible){
        this.clazz = clazz;
        this.position = position;
        this.isVisible = isVisible;
    }

    public static <T extends JInternalFrameExtended> StorableData of(T frame){
        return new StorableData(frame.getClass(), frame.getLocation(), frame.isVisible());
    }

    public static <T extends JInternalFrameExtended> StorableData defaultValue(T frame){
        return new StorableData(frame.getClass(), new Point(10, 10), true);
    }

    public static StorableData defaultValue(Class<? extends JInternalFrameExtended> frame){
        return new StorableData(frame, new Point(10, 10), true);
    }
}
