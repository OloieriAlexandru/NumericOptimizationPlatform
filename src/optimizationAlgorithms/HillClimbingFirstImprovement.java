package optimizationAlgorithms;

import functions.Function;
import main.GlobalState;
import main.UserInterface;

import java.util.ArrayList;

public class HillClimbingFirstImprovement implements IOptimizationAlgorithm {
    private Function f;
    private UserInterface           ui;

    public HillClimbingFirstImprovement(UserInterface userInterface){
        ui = userInterface;
    }

    @Override
    public void setFunction(Function function) {
        f = function;
    }

    @Override
    public double run(boolean drawGraph, boolean printBestValue) {
        return run(GlobalState.iterationsCount, drawGraph, printBestValue);
    }

    @Override
    public double run(int generationsLimit, boolean drawGraph, boolean printBestValue) {
        ArrayList<Double>       bestValues = new ArrayList<>();
        CandidateHCSA           currentCandidate = new CandidateHCSA(f, 5);
        Double                  bestValue = GlobalState.getTheWorstValue();

        for (int i=0;i<generationsLimit;++i){
            currentCandidate.generateRandomCandidate();

            while (currentCandidate.hillClimbingFirstImprovementExploration());

            bestValue = GlobalState.getBetterValue(bestValue, currentCandidate.getCurrentBest(), currentCandidate.getDecimalRepresentationOfBestCandidate());

            if (drawGraph){
                if (printBestValue){
                    bestValues.add(bestValue);
                } else {
                    bestValues.add(currentCandidate.getCurrentBest());
                }
                ui.graph.printGenerations(bestValues, generationsLimit, 0);
            }
        }

        return bestValue;
    }
}
