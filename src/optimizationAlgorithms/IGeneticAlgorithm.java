package optimizationAlgorithms;

import java.util.ArrayList;

import functions.IFunction;

public interface IGeneticAlgorithm {
    void                    setFunction(IFunction function);
    void                    generateFirstGeneration();
    void                    generateNextGeneration();
    boolean                 finished();
    ArrayList<Double>       getGenerationCandidates();
}
