package functions;

import main.GlobalState;

public class FRastrigin extends Function {
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
}
