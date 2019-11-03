package optimizationAlgorithms;

import functions.Function;

public interface IOptimizationAlgorithm {
    double run(boolean drawGraph, boolean printBestValue);
    double run(int generationsLimit, boolean drawGraph, boolean printBestValue);
    void setFunction(Function function);
}
