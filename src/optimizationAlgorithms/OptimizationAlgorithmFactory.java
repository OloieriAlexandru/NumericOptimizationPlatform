package optimizationAlgorithms;

import main.UserInterface;

import java.util.ArrayList;

public class OptimizationAlgorithmFactory {
    private static ArrayList<OptimizationAlgorithmDescription>      optimizationAlgorithms = new ArrayList<>();
    private static boolean                                          optimizationAlgorithmsInit = false;
    private static UserInterface                                    ui;

    public OptimizationAlgorithmFactory(UserInterface userInterface){
        ui = userInterface;
        optimizationAlgorithms.clear();
        initOptimizationAlgorithms();
    }

    public IOptimizationAlgorithm    getOptimizationAlgorithm(String longName){
        longName = longName.toLowerCase();
        for (OptimizationAlgorithmDescription algorithmDescription:optimizationAlgorithms){
            if (algorithmDescription.getLongName().toLowerCase().equals(longName)){
                return algorithmDescription.getAlgorithm();
            }
        }
        return null;
    }

    public static OptimizationAlgorithmDescription getOptimizationAlgorithmDescription(String longName){
        longName = longName.toLowerCase();
        for (OptimizationAlgorithmDescription algorithmDescription:optimizationAlgorithms){
            if (algorithmDescription.getLongName().toLowerCase().equals(longName)){
                return algorithmDescription;
            }
        }
        return null;
    }

    public static ArrayList<String>     getOptimizationAlgorithmsNames() {
        if (!optimizationAlgorithmsInit){
            initOptimizationAlgorithms();
        }
        ArrayList<String>   optimizationAlgorithmsNames = new ArrayList<>();
        for (OptimizationAlgorithmDescription algorithmDescription:optimizationAlgorithms){
            optimizationAlgorithmsNames.add(algorithmDescription.getLongName());
        }
        return optimizationAlgorithmsNames;
    }

    private static void initOptimizationAlgorithms() {
        optimizationAlgorithmsInit = true;
        optimizationAlgorithms.add(new OptimizationAlgorithmDescription("Hill Climbing - First Improvement", "Hill Climbing - FI", new HillClimbingFirstImprovement(ui)));
        optimizationAlgorithms.add(new OptimizationAlgorithmDescription("Hill Climbing - Best Improvement", "Hill Climbing - BI", new HillClimbingBestImprovement(ui)));
        optimizationAlgorithms.add(new OptimizationAlgorithmDescription("Simulated Annealing", "Simulated Annealing", new SimulatedAnnealing(ui)));
        optimizationAlgorithms.add(new OptimizationAlgorithmDescription("Hill Climbing - Naive", "Naive Hill Climbing", new HillClimbingNaive(ui)));
        optimizationAlgorithms.add(new OptimizationAlgorithmDescription("Random Search Algorithm", "Random Search", new RandomSearchAlgorithm(ui)));
        optimizationAlgorithms.add(new OptimizationAlgorithmDescription("Brute Force Search Algorithm", "Brute Force Search", new BruteForceSearchAlgorithm(ui)));

        //optimizationAlgorithms.add("Simulated Annealing");
        //optimizationAlgorithms.add("Genetic Algorithm");
    }
}
