package functions;

import javafx.util.Pair;
import optimizationAlgorithms.CandidateHCSA;

public abstract class Function {
    double[]            minVals;
    double[]            maxVals;

    public int[] getArgumentsBitsLen(int precision) {
        int args = getArgumentsCount();
        int[] bitLens = new int[args];
        for (int i=0;i<args;++i){
            bitLens[i] = CandidateHCSA.calculateBitLen(minVals[0],maxVals[0],precision);
        }
        return bitLens;
    }

    public Pair<double[],double[]> getArgumentsLimits(){
        int args = getArgumentsCount();
        double[]    retMin = new double[args];
        double[]    retMax = new double[args];
        for (int i=0;i<args;++i){
            retMin[i] = minVals[0];
            retMax[i] = maxVals[0];
        }
        return new Pair<>(retMin,retMax);
    }

    public Pair<Double,Double> getFunctionArgumentLimits(int index) {
        return new Pair<>(minVals[index], maxVals[index]);
    }

    public abstract int                getArgumentsCount();
    public abstract double             evaluate(double ... args);

    public void addLimits(double[] minValues, double[] maxValues) {
        minVals = minValues.clone();
        maxVals = maxValues.clone();
    }

    public void updateMinLimit(int index, double val) {
        minVals[index] = val;
    }

    public void updateMaxLimit(int index, double val) {
        maxVals[index] = val;
    }

}
