package optimizationAlgorithms;

import java.util.ArrayList;

import functions.Function;

public interface IGeneticAlgorithm {
    void                    setFunction(Function function);
    void                    generateFirstGeneration();
    void                    generateNextGeneration();
    boolean                 finished();
    ArrayList<Double>       getGenerationCandidates();
}
