package optimizationAlgorithms;

import functions.Function;
import main.GlobalState;
import main.UserInterface;

import java.util.ArrayList;

public class GeneticAlgorithmOptimized implements IOptimizationAlgorithm {
    private Function                f;
    private UserInterface           ui;
    public static int totalGenerationsSum = 0;
    public static int totalRuns = 0;

    GeneticAlgorithmOptimized(UserInterface userInterface){
        ui = userInterface;
    }

    @Override
    public double run(boolean drawGraph, boolean printBestValue) {
        return run(GlobalState.iterationsCount, drawGraph, printBestValue);
    }

    @Override
    public double run(int generationsLimit, boolean drawGraph, boolean printBestValue) {

        double bestValue = GlobalState.getTheWorstValue();
        PopulationOptimizedGA population = new PopulationOptimizedGA(f, 5);
        population.initializePopulation();

        int noChange = 0, generations = 1;
        boolean appliedHypermutation = false;

        while (noChange < GlobalState.genLimit){
            ++generations;
            population.mutatePopulation();
            population.crossOverPopulation();

            double currValue = population.selection();
            if (!GlobalState.solutionIsBetterThanBest(bestValue, currValue)){
                ++noChange;
                if (noChange == GlobalState.genLimit && !appliedHypermutation){
                    appliedHypermutation = true;
                    noChange = 0;
                    double oldProbability = GlobalState.mutationProbability;
                    GlobalState.mutationProbability = GlobalState.hypermutationProbability;
                    population.mutatePopulation();
                    GlobalState.mutationProbability = oldProbability;
                }
            } else {
                noChange = 0;
                bestValue = GlobalState.getBetterValue(bestValue, currValue, population.getDecimalRepresentationOfBestCandidate());
            }

        }
        totalGenerationsSum += generations;
        ++totalRuns;

        CandidateHCSA bestCandidate = new CandidateHCSA(population.getBestCandidate());
        while (bestCandidate.hillClimbingBestImprovementExploration());

        bestCandidate.hillClimbingBestImprovementExploration();

        return bestCandidate.getCurrentBest();
    }

    @Override
    public void setFunction(Function function) {
        f = function;
    }
}
