package functions;

import main.GlobalState;

public class FGriewank extends Function {
    @Override
    public int getArgumentsCount() {
        return GlobalState.functionArguments;
    }

    @Override
    public double evaluate(double... args) {
        double res = 1, sum = 0.0, mul = 1.0;
        for (int i=0;i<args.length;++i){
            sum += ((args[i]*args[i])/4000.0);
            mul *= Math.cos((args[i]/(Math.sqrt((double)(i+1)))));
        }
        res += sum;
        res -= mul;
        return res;
    }
}
