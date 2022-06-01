package utility;

public final class Utility {
  public static double applyLimits(double value, double min, double max) {
    return Math.max(min, Math.min(max, value));
  }
}
