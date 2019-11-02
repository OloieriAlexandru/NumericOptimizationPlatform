package optimizationAlgorithms;

import functions.Function;
import main.CustomPair;
import main.GlobalState;
import main.UserInterface;

public class BruteForceSearchAlgorithm implements IOptimizationAlgorithm {
    private Function f;
    private UserInterface ui;
    private double[]        currentVariablesStates;
    private double[]        functionMinValues;
    private double[]        functionMaxValues;
    private double[]        epsilons;
    private double          bestValue;
    private static int      bruteForceSearchIterations = 25;

    BruteForceSearchAlgorithm(UserInterface userInterface){
        ui = userInterface;
    }

    @Override
    public double run(boolean drawGraph) {
        return run(GlobalState.iterationsCount, drawGraph);
    }

    private void recursiveBruteForceSearch(int variableIndex){
        if (variableIndex == f.getArgumentsCount()){
            double currentFunctionValue = f.evaluate(currentVariablesStates);
            bestValue = GlobalState.getBetterValue(bestValue, currentFunctionValue, currentVariablesStates);
            return;
        }
        currentVariablesStates[variableIndex] = functionMinValues[variableIndex];
        while (currentVariablesStates[variableIndex] <= functionMaxValues[variableIndex]){
            recursiveBruteForceSearch(variableIndex + 1);
            currentVariablesStates[variableIndex] += epsilons[variableIndex];
        }
    }

    @Override
    public double run(int generationsLimit, boolean drawGraph) {
        bruteForceSearchIterations = GlobalState.iterationsCount;

        currentVariablesStates = new double[f.getArgumentsCount()];

        CustomPair<double[], double[]> functionLimits = f.getArgumentsLimits();
        functionMinValues = functionLimits.getKey();
        functionMaxValues = functionLimits.getValue();
        epsilons = new double[functionMaxValues.length];
        for (int i=0;i<epsilons.length;++i){
            epsilons[i] = ( functionMaxValues[i] - functionMinValues[i] ) / (double) bruteForceSearchIterations;
        }
        bestValue = GlobalState.getTheWorstValue();

        recursiveBruteForceSearch(0);

        return bestValue;
    }

    @Override
    public void setFunction(Function function) {
        f = function;
    }
}
