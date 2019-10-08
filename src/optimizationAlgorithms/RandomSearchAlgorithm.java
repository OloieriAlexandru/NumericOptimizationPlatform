package optimizationAlgorithms;

import functions.Function;
import javafx.util.Pair;
import main.GlobalState;
import main.UserInterface;

import java.util.ArrayList;
import java.util.Random;

public class RandomSearchAlgorithm implements IOptimizationAlgorithm {
    private Function        f;
    private UserInterface   ui;

    RandomSearchAlgorithm(UserInterface userInterface){
        ui = userInterface;
    }

    @Override
    public double run() {
        return run(GlobalState.iterationsCount);
    }

    @Override
    public double run(int generationsLimit) {
        ArrayList<Double> bestValues = new ArrayList<>();
        Pair<double[], double[]> functionLimits = f.getArgumentsLimits();
        double[] functionMinValues = functionLimits.getKey();
        double[] functionMaxValues = functionLimits.getValue();

        int args = f.getArgumentsCount();
        double[] functionArgs = new double[args];
        double bestValue = GlobalState.getTheWorstValue();

        Random r = new Random();

        for (int i=0;i<generationsLimit;++i){
            for (int j=0;j<args;++j){
                functionArgs[j] = functionMinValues[j] + (functionMaxValues[j] - functionMinValues[j]) * r.nextDouble();
            }
            double res = f.evaluate(functionArgs);
            bestValue = GlobalState.getBetterValue(bestValue, res, functionArgs);

            bestValues.add(bestValue);

            ui.graph.printGenerations(bestValues, generationsLimit, 1);
        }

        return bestValue;
    }

    @Override
    public void setFunction(Function function) {
        f = function;
    }
}
