package optimizationAlgorithms;

import functions.Function;
import main.GlobalState;
import main.UserInterface;

import java.util.ArrayList;

public class GeneticAlgorithm implements IOptimizationAlgorithm {
    private Function                f;
    private UserInterface           ui;

    GeneticAlgorithm(UserInterface userInterface){ ui = userInterface; }

    @Override
    public double run(boolean drawGraph, boolean printBestValue) {
        return run(GlobalState.iterationsCount, drawGraph, printBestValue);
    }

    @Override
    public double run(int generationsLimit, boolean drawGraph, boolean printBestValue) {

        double bestValue = GlobalState.getTheWorstValue();
        ArrayList<Double> bestValues = new ArrayList<>();
        PopulationGA population = new PopulationGA(f, 5);
        population.initializePopulation();

        for (int i=2;i<=generationsLimit;++i){
            population.mutatePopulation();
            population.crossOverPopulation();

            double currValue = population.selection();
            bestValue = GlobalState.getBetterValue(bestValue, currValue, population.getDecimalRepresentationOfBestCandidate());

            if (drawGraph){
                if (printBestValue){
                    bestValues.add(bestValue);
                } else {
                    bestValues.add(currValue);
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
