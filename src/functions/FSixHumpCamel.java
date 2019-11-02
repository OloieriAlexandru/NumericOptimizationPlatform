package functions;

import main.CustomPair;
import optimizationAlgorithms.CandidateHCSA;

public class FSixHumpCamel extends Function {
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
}
