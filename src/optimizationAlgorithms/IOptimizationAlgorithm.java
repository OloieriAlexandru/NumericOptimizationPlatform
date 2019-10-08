package optimizationAlgorithms;

import functions.IFunction;

public interface IOptimizationAlgorithm {
    double run();
    double run(int generationsLimit);
    void setFunction(IFunction function);
}
