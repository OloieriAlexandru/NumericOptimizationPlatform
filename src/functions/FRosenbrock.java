package functions;

import main.GlobalState;

public class FRosenbrock extends Function {
    @Override
    public int getArgumentsCount() {
        return GlobalState.functionArguments;
    }

    @Override
    public double evaluate(double... args) {
        double ret = 0, act;
        for (int i=0;i<args.length-1;++i){
            act =  (args[i+1] - args[i]*args[i]);
            ret += 100.0 * act * act;
            act = (1.0 - args[i]);
            ret += act * act;
        }
        return ret;
    }
}
