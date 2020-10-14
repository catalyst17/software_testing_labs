package func;

public class DependentLogarithm {
    private BaseLogarithm base;

    public DependentLogarithm(BaseLogarithm base) {
        this.base = base;
    }

    public double log(double x, double base, double eps){
        double lnA = this.base.ln(base, eps);
        return (lnA >= -eps && lnA <= eps) ? Double.NaN : this.base.ln(x, eps) / lnA;
    }
}
