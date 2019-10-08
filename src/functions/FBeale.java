package functions;

import javafx.util.Pair;
import optimizationAlgorithms.CandidateHCSA;

public class FBeale implements IFunction {
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

    @Override
    public void addLimits(double[] minValues, double[] maxValues) {
        minVals = minValues;
        maxVals = maxValues;
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
