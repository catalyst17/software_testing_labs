package func;

public class BaseLogarithm {
    public double ln(Double x, Double eps) {
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
