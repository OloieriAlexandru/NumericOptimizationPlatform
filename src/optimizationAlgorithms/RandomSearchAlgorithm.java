package optimizationAlgorithms;

import functions.Function;
import javafx.util.Pair;
import main.GlobalState;
import main.UserInterface;

public class RandomSearchAlgorithm implements IOptimizationAlgorithm {
    private Function f;
    private UserInterface   ui;
    private double[]        currentVariablesStates;
    private double[]        functionMinValues;
    private double[]        functionMaxValues;
    private double          bestValue;
    private static int      randomSearchIterations = 100;

    RandomSearchAlgorithm(UserInterface userInterface){
        ui = userInterface;
    }

    @Override
    public double run() {
        return run(GlobalState.iterationsCount);
    }

    private void recursiveRandomSearch(int variableIndex){
        if (variableIndex == f.getArgumentsCount()){
            // am generat random toate variabilele
            double currentFunctionValue = f.evaluate(currentVariablesStates);
            GlobalState.getBetterValue(bestValue, currentFunctionValue);
            return;
        }
        double epsilon = ( functionMaxValues[variableIndex] - functionMinValues[variableIndex] ) / (double) randomSearchIterations;
        currentVariablesStates[variableIndex] = functionMinValues[variableIndex];
        while (currentVariablesStates[variableIndex] <= functionMaxValues[variableIndex]){
            recursiveRandomSearch(variableIndex + 1);
            currentVariablesStates[variableIndex] += epsilon;
        }
    }

    @Override
    public double run(int generationsLimit) {
        currentVariablesStates = new double[f.getArgumentsCount()];

        Pair<double[], double[]> functionLimits = f.getArgumentsLimits();
        functionMinValues = functionLimits.getKey();
        functionMaxValues = functionLimits.getValue();
        bestValue = GlobalState.getTheWorstValue();

        recursiveRandomSearch(0);

        return bestValue;
    }

    @Override
    public void setFunction(Function function) {
        f = function;
    }
}
