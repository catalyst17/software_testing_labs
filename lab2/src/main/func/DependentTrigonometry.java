package func;

public class DependentTrigonometry {
    private BaseTrigonometry base;

    public DependentTrigonometry(BaseTrigonometry base) {
        this.base = base;
    }

    public double cos(double x, double eps) {
        return 1 - 2 * Math.pow(base.sin(x / 2, eps), 2);
    }

    public double tan(double x, double eps) {
        double cos = cos(x, eps);
        return (cos >= -eps && cos <= eps) ? Double.NaN : base.sin(x, eps) / cos;
    }
}
