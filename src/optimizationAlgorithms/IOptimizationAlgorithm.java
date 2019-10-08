package optimizationAlgorithms;

import functions.Function;

public interface IOptimizationAlgorithm {
    double run();
    double run(int generationsLimit);
    void setFunction(Function function);
}
