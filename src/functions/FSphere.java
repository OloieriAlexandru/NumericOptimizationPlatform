package functions;

import main.GlobalState;

public class FSphere extends Function {
    @Override
    public int getArgumentsCount() {
        return GlobalState.functionArguments;
    }

    @Override
    public double evaluate(double... args) {
        double ret = 0;
        for (int i=0;i<args.length;++i){
            ret += args[i] * args[i];
        }
        return ret;
    }
}
