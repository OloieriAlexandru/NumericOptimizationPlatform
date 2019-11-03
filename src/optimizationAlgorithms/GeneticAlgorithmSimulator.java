package optimizationAlgorithms;

import functions.Function;
import main.UserInterface;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class GeneticAlgorithmSimulator implements IOptimizationAlgorithm{
    private IGeneticAlgorithm           geneticAlgorithm;
    private UserInterface               ui;
    private Function f;

    public GeneticAlgorithmSimulator(IGeneticAlgorithm ga, UserInterface userInterface){
        geneticAlgorithm = ga;
        ui = userInterface;
    }

    @Override
    public double run(boolean drawGraph, boolean printBestValue) {
        return run(100, drawGraph, printBestValue);
    }

    @Override
    public double run(int generationsLimit, boolean drawGraph, boolean printBestValue) {
        return runGenericGeneticAlgorithm(generationsLimit);
    }

    @Override
    public void setFunction(Function function) {
        f = function;
    }

    private double runGenericGeneticAlgorithm(int generationsLimit){
        ArrayList<Double>       currentGeneration;
        ArrayList<Double>       bestCandidates = new ArrayList<>();
        int                     generationNumber = 1;

        geneticAlgorithm.generateFirstGeneration();
        Random r = new Random();
        do
        {
            if (geneticAlgorithm.finished()){
                break;
            }
            currentGeneration = geneticAlgorithm.getGenerationCandidates();

            Double best = Collections.max(currentGeneration);
            bestCandidates.add(best);
            ui.graph.printGenerations(bestCandidates, generationsLimit, 100);

            geneticAlgorithm.generateNextGeneration();
        } while (++generationNumber <= generationsLimit);

        return 0.0;
    }
}
