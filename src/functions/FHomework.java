package functions;

import main.CustomPair;
import optimizationAlgorithms.CandidateHCSA;

public class FHomework extends Function {
    @Override
    public int[] getArgumentsBitsLen(int precision) {
        int args = getArgumentsCount();
        int[] bitLens = new int[args];
        for (int i=0;i<args;++i){
            bitLens[i] = CandidateHCSA.calculateBitLen(minVals[i],maxVals[i],precision);
        }
        return bitLens;
    }

    @Override
    public CustomPair<double[], double[]> getArgumentsLimits() {
        int args = getArgumentsCount();
        double[]    retMin = new double[args];
        double[]    retMax = new double[args];
        for (int i=0;i<args;++i){
            retMin[i] = minVals[i];
            retMax[i] = maxVals[i];
        }
        return new CustomPair(retMin,retMax);
    }

    @Override
    public int getArgumentsCount() {
        return 1;
    }

    @Override
    public double evaluate(double... args) {
        double x = args[0];
        return x*x*x-60*x*x+900*x+100;
    }
}
