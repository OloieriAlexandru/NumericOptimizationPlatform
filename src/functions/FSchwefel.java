package functions;

import main.GlobalState;

public class FSchwefel extends Function {
    @Override
    public int getArgumentsCount() {
        return GlobalState.functionArguments;
    }

    @Override
    public double evaluate(double... args) {
        double res = 418.9829 * args.length;
        for (int i=0;i<args.length;++i){
            res -= args[i] * Math.sin(Math.sqrt(Math.abs(args[i])));
        }
        return res;
    }
}
