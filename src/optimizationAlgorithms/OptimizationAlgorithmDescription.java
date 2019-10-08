package optimizationAlgorithms;

public class OptimizationAlgorithmDescription {
    private String              longName;
    private String              shortName;
    private IOptimizationAlgorithm   algorithm;

    OptimizationAlgorithmDescription(String name, IOptimizationAlgorithm alg){
        longName = name;
        shortName = name;
        algorithm = alg;
    }

    OptimizationAlgorithmDescription(String lName, String sName, IOptimizationAlgorithm alg){
        longName = lName;
        shortName = sName;
        algorithm = alg;
    }

    public String getShortName() {
        return shortName;
    }

    public String getLongName() {
        return longName;
    }

    public IOptimizationAlgorithm getAlgorithm() {
        return algorithm;
    }
}
