package functions;

import javafx.util.Pair;
import optimizationAlgorithms.CandidateHCSA;

public class FSixHumpCamel implements IFunction {
    private double          minVals[];
    private double          maxVals[];

    @Override
    public int[] getArgumentsBitsLen(int precision) {
        int args = getArgumentsCount();
        int[] bitLens = new int[args];
        for (int i=0;i<args;++i){
            bitLens[i] = CandidateHCSA.calculateBitLen(minVals[0],maxVals[0],precision);
        }
        return bitLens;
    }

    @Override
    public Pair<double[], double[]> getArgumentsLimits() {
        int args = getArgumentsCount();
        double[]    retMin = new double[args];
        double[]    retMax = new double[args];
        for (int i=0;i<args;++i){
            retMin[i] = minVals[0];
            retMax[i] = maxVals[0];
        }
        return new Pair<>(retMin,retMax);
    }

    @Override
    public Pair<Double, Double> getFunctionArgumentLimits(int index) {
        return new Pair<>(minVals[index], maxVals[index]);
    }

    @Override
    public int getArgumentsCount() {
        return 2;
    }

    @Override
    public double evaluate(double... args) {
        if (args.length < 2){
            return 0.0;
        }
        double x1 = args[0];
        double x2 = args[1];
        double p1 = (4 - 2.1*x1*x1+x1*x1*x1*x1/3);
        p1 *= (x1*x1);
        double p2 = x1*x2;
        double p3 = (-4.0+4.0*x2*x2);
        p3 *= x2*x2;
        return p1+p2+p3;
    }

    @Override
    public void addLimits(double[] minValues, double[] maxValues) {
        minVals = minValues.clone();
        maxVals = maxValues.clone();
    }

    @Override
    public void updateMinLimit(int index, double val) {
        minVals[index] = val;
    }

    @Override
    public void updateMaxLimit(int index, double val) {
        maxVals[index] = val;
    }
}
