package julie.structMetricsChecks;

import java.util.Arrays;

public class Utils {
  public static int[] concat(int[] first, int[] second) {
    int[] result = Arrays.copyOf(first, first.length + second.length);
    System.arraycopy(second, 0, result, first.length, second.length);
    return result;
  }

  public static <T> T[] concat(T[] first, T[] second) {
    T[] result = Arrays.copyOf(first, first.length + second.length);
    System.arraycopy(second, 0, result, first.length, second.length);
    return result;
  }

  public static double log(double x, int base) {
    return Math.log(x) / Math.log(base);
  }
  
  public static boolean contains(int[] array, Integer value) {
      return Arrays.stream(array).anyMatch(value::equals);
    }

  @SafeVarargs
  public static int[] concatAll(int[] first, int[]... rest) {
    int totalLength = first.length;
    for (int[] array : rest) {
      totalLength += array.length;
    }
    int[] result = Arrays.copyOf(first, totalLength);
    int offset = first.length;
    for (int[] array : rest) {
      System.arraycopy(array, 0, result, offset, array.length);
      offset += array.length;
    }
    return result;
  }
}
