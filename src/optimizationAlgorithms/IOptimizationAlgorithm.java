package optimizationAlgorithms;

import functions.Function;

public interface IOptimizationAlgorithm {
    double run(boolean drawGraph);
    double run(int generationsLimit, boolean drawGraph);
    void setFunction(Function function);
}
