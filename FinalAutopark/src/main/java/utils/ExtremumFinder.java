package utils;

public class ExtremumFinder {
    public static double getExtremum(double[] array, boolean findMax) {
        double extremum = array[0];
        for (double v : array) {
            if (v > extremum && findMax) {
                extremum = v;
            }
            if (v < extremum && !findMax) {
                extremum = v;
            }
        }

        return extremum;
    }

    public static int getExtremum(int[] array, boolean findMax) {
        int extremum = array[0];
        for (int v : array) {
            if (v > extremum && findMax) {
                extremum = v;
            }
            if (v < extremum && !findMax) {
                extremum = v;
            }
        }

        return extremum;
    }
}
