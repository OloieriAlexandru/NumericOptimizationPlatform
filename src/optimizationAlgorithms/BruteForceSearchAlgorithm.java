package optimizationAlgorithms;

import functions.Function;
import javafx.util.Pair;
import main.GlobalState;
import main.UserInterface;

public class BruteForceSearchAlgorithm implements IOptimizationAlgorithm {
    private Function f;
    private UserInterface ui;
    private double[]        currentVariablesStates;
    private double[]        functionMinValues;
    private double[]        functionMaxValues;
    private double          bestValue;
    private static int      bruteForceSearchIterations = 35;

    BruteForceSearchAlgorithm(UserInterface userInterface){
        ui = userInterface;
    }

    @Override
    public double run() {
        return run(GlobalState.iterationsCount);
    }

    private void recursiveBruteForceSearch(int variableIndex){
        if (variableIndex == f.getArgumentsCount()){
            double currentFunctionValue = f.evaluate(currentVariablesStates);
            bestValue = GlobalState.getBetterValue(bestValue, currentFunctionValue, currentVariablesStates);
            return;
        }
        double epsilon = ( functionMaxValues[variableIndex] - functionMinValues[variableIndex] ) / (double) bruteForceSearchIterations;
        currentVariablesStates[variableIndex] = functionMinValues[variableIndex];
        while (currentVariablesStates[variableIndex] <= functionMaxValues[variableIndex]){
            recursiveBruteForceSearch(variableIndex + 1);
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

        recursiveBruteForceSearch(0);

        return bestValue;
    }

    @Override
    public void setFunction(Function function) {
        f = function;
    }
}
