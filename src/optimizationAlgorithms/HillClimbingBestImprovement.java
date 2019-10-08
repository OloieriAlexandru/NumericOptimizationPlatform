package optimizationAlgorithms;

import functions.IFunction;
import main.GlobalState;
import main.UserInterface;

import java.util.ArrayList;

public class HillClimbingBestImprovement implements IOptimizationAlgorithm {
    private IFunction               f;
    private UserInterface           ui;

    HillClimbingBestImprovement(UserInterface userInterface){
        ui = userInterface;
    }

    @Override
    public double run() {
        return run(GlobalState.iterationsCount);
    }

    @Override
    public double run(int generationsLimit) {
        ArrayList<Double>       bestValues = new ArrayList<>();
        CandidateHCSA           currentCandidate = new CandidateHCSA(f, 5);
        Double                  bestValue = GlobalState.getTheWorstValue();

        for (int i=0;i<generationsLimit;++i){
            currentCandidate.generateRandomCandidate();

            while (currentCandidate.hillClimbingBestImprovementExploration());

            bestValue = GlobalState.getBetterValue(bestValue, currentCandidate.getCurrentBest());
            bestValues.add(bestValue);

            ui.graph.printGenerations(bestValues, generationsLimit, 1);
        }

        return bestValue;
    }

    @Override
    public void setFunction(IFunction function) {
        f = function;
    }
}
