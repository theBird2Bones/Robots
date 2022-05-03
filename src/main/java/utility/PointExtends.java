package utility;

import java.awt.*;
import java.awt.geom.Point2D;

public class PointExtends {
    static public Point roundWithTranslate(Point2D.Double p, int dx, int dy){
        return new Point(round(p.x + dx), round(p.y + dy));
    }

    static public Point round(Point2D.Double p){
        return new Point(round(p.x), round(p.y));
    }

    private static int round(double value) {
        return (int) (value + 0.5);
    }

    public static Point2D.Double toDouble(Point p){
        return new Point2D.Double(p.x, p.y);
    }

    public static Point mult(Point p, int mult){
        return mult(p, mult, mult);
    }

    public static Point mult(Point p, int multX, int multY){
        return new Point(p.x * multX, p.y * multY);
    }

    public static Point2D mult(Point2D p, int multX, int multY){
        return new Point2D.Double(p.getX() * multX, p.getY() * multY);
    }
}
