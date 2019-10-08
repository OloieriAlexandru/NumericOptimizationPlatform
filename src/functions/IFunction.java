package functions;

import javafx.util.Pair;

public interface IFunction {
    int[]                       getArgumentsBitsLen(int precision);
    Pair<double[],double[]>     getArgumentsLimits();
    Pair<Double,Double>         getFunctionArgumentLimits(int index);
    int                         getArgumentsCount();
    double                      evaluate(double ... args);
    void                        addLimits(double minValues[], double maxValues[]);
    void                        updateMinLimit(int index, double val);
    void                        updateMaxLimit(int index, double val);
}
