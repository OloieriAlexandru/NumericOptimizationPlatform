package optimizationAlgorithms;

import functions.Function;
import main.CustomPair;
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
        CandidateHCSA           currentCandidate = new CandidateHCSA(f, 0);
        Double                  bestValue = GlobalState.getTheWorstValue();

        ArrayList<CustomPair<Double,Double>> solutions = new ArrayList<>();

        for (int i=0;i<generationsLimit;++i){
            currentCandidate.generateRandomCandidate();

            double start = currentCandidate.getDecimalRepresentationOfBestCandidate()[0];
            while (currentCandidate.hillClimbingFirstImprovementExploration());
            double end = currentCandidate.getDecimalRepresentationOfBestCandidate()[0];
            boolean toAdd = true;
            for (int j=0;j<solutions.size();++j){
                if (solutions.get(j).getKey() == start){
                    toAdd = false;
                    break;
                }
            }
            if (toAdd){
                solutions.add(new CustomPair<>(start, end));
            }

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
        for (CustomPair<Double,Double> sol:solutions){
            System.out.println(sol.getKey() + " " + sol.getValue());
        }

        return bestValue;
    }
}
