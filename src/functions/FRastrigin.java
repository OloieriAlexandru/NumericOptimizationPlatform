package functions;

import javafx.util.Pair;
import main.GlobalState;
import optimizationAlgorithms.CandidateHCSA;

public class FRastrigin implements IFunction {
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
        return GlobalState.functionArguments;
    }

    @Override
    public double evaluate(double... args) {
        double ans = 10.0 * args.length;
        double add = 0;
        for (int i=0;i<args.length;++i){
            add += (args[i] * args[i] - 10.0 * Math.cos(2.0*Math.PI*args[i]));
        }
        double ret = ans + add;
        if (Math.abs(ret) < 1e-6){
            ret = 0;
        }
        return ret;
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
