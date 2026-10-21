public class Linear {
    double a;
    double b;

    public Linear() {
        this.a = 1.0;
        this.b = 0.0;
    }

    public Linear(double a, double b) {
        this.a = a;
        this.b = b;
    }

    public double f(double x) {
        return this.a * x + this.b;
    }
}