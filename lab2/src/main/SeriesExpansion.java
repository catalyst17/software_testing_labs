import java.util.stream.LongStream;

public class SeriesExpansion {
    public static double expanseSin(Double x, Double eps) {
        if (x.isInfinite() || x.isNaN() || eps.isNaN() || eps.isInfinite() || eps == 0.) {
            throw new IllegalArgumentException();
        }

        double result  = 0, previousResult;
        int i = 0;

        do {
            previousResult = result;
            result += (Math.pow(-1, i) * Math.pow(x, 2*i + 1)) /
                    (LongStream.rangeClosed(1, (2*i + 1)).reduce(1, (a, b) -> a*b));
            i++;
        } while (Math.abs(previousResult - result) > eps);

        return result;
    }

    public static double expanseLn(Double x, Double eps) {
        if (x.isInfinite() || x.isNaN() || x <= 0 || eps.isNaN() || eps.isInfinite() || eps == 0.) {
            throw new IllegalArgumentException();
        }

        double result = 0, previousResult;
        int i = 0;
        do {
            previousResult = result;
            result += Math.pow((x - 1) / (x + 1), 2 * i + 1) / (2 * i + 1);
            i++;
        } while(Math.abs(previousResult - result) > eps);

        return 2 * result;
    }
}
