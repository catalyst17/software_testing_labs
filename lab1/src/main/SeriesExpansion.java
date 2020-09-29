import java.util.stream.LongStream;

public class SeriesExpansion {
    private static final int n = 8;

    public static double expanse(double x) {
        x = ((x/Math.PI)%2)*Math.PI;
        if (x > Math.PI) x -= 2*Math.PI;
        if (x < -Math.PI) x += 2*Math.PI;

        double result = 0;

        for (int i = 0; i <= n; i++) {
            result += (Math.pow(-1, i) * Math.pow(x, 2*i)) /
                    (LongStream.rangeClosed(1, 2*i).reduce(1, (a, b) -> a*b));
        }

        return result;
    }
}
