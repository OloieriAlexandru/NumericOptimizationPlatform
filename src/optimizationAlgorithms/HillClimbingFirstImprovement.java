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

            while (currentCandidate.hillClimbingFirstImprovementExploration());

            bestValue = GlobalState.getBetterValue(bestValue, currentCandidate.getCurrentBest());
            bestValues.add(bestValue);

            ui.graph.printGenerations(bestValues, generationsLimit, 1);
        }

        return bestValue;
    }
}
