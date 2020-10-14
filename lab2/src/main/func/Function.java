package func;

public class Function {
    public static double calculate(double x, double eps) {
        return (x > 0)? func1(x, eps) : func2(x, eps);
    }

    private static double func1(double x, double eps) {
        BaseLogarithm baseLogarithm = new BaseLogarithm();
        DependentLogarithm dependentLogarithm = new DependentLogarithm(baseLogarithm);
        double log2 = dependentLogarithm.log(x, 2, eps);
        double log3 = dependentLogarithm.log(x, 3, eps);
        double log5 = dependentLogarithm.log(x, 5, eps);
        double log10 = dependentLogarithm.log(x, 10, eps);
        double ln = baseLogarithm.ln(x, eps);

        if (log10 == 0)
            return Double.NaN;

        return (((((log2 + ln) * (log5 * log3))-log5) * log10) + (((log10/log10)+log10) * log5));
    }

    private static double func2(double x, double eps) {
        BaseTrigonometry baseTrigonometry = new BaseTrigonometry();
        DependentTrigonometry dependentTrigonometry = new DependentTrigonometry(baseTrigonometry);
        double sin = baseTrigonometry.sin(x, eps);
        double cos = dependentTrigonometry.cos(x, eps);
        double tan = dependentTrigonometry.tan(x, eps);
        if (cos == 0)
            return Double.NaN;

        return (Math.pow((((cos * tan) + cos) + cos), 2)) / cos;
    }
}
