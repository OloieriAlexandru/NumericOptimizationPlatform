package functions;

import main.GlobalState;

public class FZakharov extends Function {
    @Override
    public int getArgumentsCount() {
        return GlobalState.functionArguments;
    }

    @Override
    public double evaluate(double... args) {
        double res, sum1 = 0, sum2 = 0, sum3 = 0;
        for (int i=0;i<args.length;++i){
            sum1 += args[i] * args[i];
            sum2 += 0.5 * (i+1) * args[i];
            sum3 += 0.5 * (i+1) * args[i];
        }
        res = sum1 + sum2 * sum2 + sum3 * sum3 * sum3 * sum3;
        return res;
    }
}
