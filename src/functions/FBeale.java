package functions;

public class FBeale extends Function {
    @Override
    public int getArgumentsCount() {
        return 2;
    }

    @Override
    public double evaluate(double... args) {
        double x1 = args[0];
        double x2 = args[1];
        double t1 = (1.5 - x1 + x1 * x2);
        t1 = t1 * t1;
        double t2 = (2.25 - x1 * x1 * x2 * x2);
        t2 = t2 * t2;
        double t3 = (2.625 - x1 + x1 * x2 * x2 * x2);
        t3 = t3 * t3;
        return t1 + t2 + t3;
    }
}
