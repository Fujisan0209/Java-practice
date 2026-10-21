public class Quad extends Linear {
    double c;

    public Quad(double a, double b, double c) {
        super(a, b);
        this.c = c;
    }

    @Override
    public double f(double x) {
        return this.a*x*x + this.b*x + this.c;
    }

    public double f(double x, double z) {
        return this.a*x + this.b*z + c;
    }
}