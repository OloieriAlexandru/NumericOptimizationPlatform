package optimizationAlgorithms;

import functions.Function;
import main.CustomPair;
import main.GlobalState;
import main.UserInterface;

import java.util.ArrayList;
import java.util.Random;

public class HillClimbingNaive implements IOptimizationAlgorithm {
    private Function        f;
    private UserInterface   ui;

    public HillClimbingNaive(UserInterface userInterface){
        ui = userInterface;
    }

    @Override
    public double run(boolean drawGraph, boolean printBestValue) {
        return run(GlobalState.iterationsCount, drawGraph, printBestValue);
    }

    @Override
    public double run(int generationsLimit, boolean drawGraph, boolean printBestValue) {
        ArrayList<Double>           bestValues = new ArrayList<>();
        Double                      bestValue = GlobalState.getTheWorstValue();
        CustomPair<double[], double[]> functionLimits = f.getArgumentsLimits();
        double[]                    functionMinValues = functionLimits.getKey();
        double[]                    functionMaxValues = functionLimits.getValue();

        int                         args = f.getArgumentsCount();
        double[]                    functionArgs = new double[args];
        double                      eps = GlobalState.epsilon, currentBest, initialValue, iterationBest;

        Random r = new Random();

        for (int i=0;i<generationsLimit;++i){

            for (int j=0;j<args;++j){
                functionArgs[j] = functionMinValues[j] + (functionMaxValues[j] - functionMinValues[j]) * r.nextDouble();
            }
            iterationBest = f.evaluate(functionArgs);

            boolean running = true, foundBetterValue;
            while (running){
                double[] hcFunctionArgs = functionArgs.clone();
                foundBetterValue = false;
                for (int j=0;j<hcFunctionArgs.length;++j){
                    initialValue = hcFunctionArgs[j];

                    hcFunctionArgs[j] -= eps;
                    if (hcFunctionArgs[j] < functionMinValues[j]){
                        hcFunctionArgs[j] = functionMinValues[j];
                    }
                    currentBest = f.evaluate(hcFunctionArgs);
                    if (GlobalState.solutionIsBetterThanBest(iterationBest, currentBest)) {
                        iterationBest = currentBest;
                        functionArgs = hcFunctionArgs.clone();
                        foundBetterValue = true;
                    }

                    hcFunctionArgs[j] = initialValue + eps;
                    if (hcFunctionArgs[j] > functionMaxValues[j]){
                        hcFunctionArgs[j] = functionMaxValues[j];
                    }
                    currentBest = f.evaluate(hcFunctionArgs);
                    if (GlobalState.solutionIsBetterThanBest(iterationBest, currentBest)) {
                        iterationBest = currentBest;
                        functionArgs = hcFunctionArgs.clone();
                        foundBetterValue = true;
                    }

                    hcFunctionArgs[j] = initialValue;
                }
                if (!foundBetterValue){
                    break;
                }
            }

            bestValue = GlobalState.getBetterValue(bestValue, iterationBest, functionArgs);

            if (drawGraph){
                if (printBestValue){
                    bestValues.add(bestValue);
                } else {
                    bestValues.add(iterationBest);
                }
                ui.graph.printGenerations(bestValues, generationsLimit, 0);
            }
        }

        return bestValue;
    }

    @Override
    public void setFunction(Function function) {
        f = function;
    }
}
