package optimizationAlgorithms;

import functions.Function;
import main.CustomPair;
import main.GlobalState;

public class CandidateGA {
    private Function        f;
    private int[]           bitwiseRepresentation;
    private int[]           bitLens;
    private double[]        decimalRepresentation;
    private double[]        minValues, maxValues;
    private int             argsCount;
    private double          currentBestValue;

    CandidateGA(Function function, int prec) {
        f = function;
        argsCount = function.getArgumentsCount();
        CustomPair<double[],double[]> functionLimits = function.getArgumentsLimits();
        minValues = functionLimits.getKey();
        maxValues = functionLimits.getValue();

        bitLens = function.getArgumentsBitsLen(prec);

        int bitwiseRepresentationLen = 0;
        for (int i=0;i<bitLens.length;++i){
            bitwiseRepresentationLen += bitLens[i];
        }

        bitwiseRepresentation = new int[bitwiseRepresentationLen];
        decimalRepresentation = new double[argsCount];
    }

    CandidateGA(CandidateGA other){
        this.f = other.f;
        this.bitwiseRepresentation = other.bitwiseRepresentation.clone();
        this.bitLens = other.bitLens.clone();
        this.decimalRepresentation = other.decimalRepresentation.clone();
        this.minValues = other.minValues.clone();
        this.maxValues = other.maxValues.clone();
        this.argsCount = other.argsCount;
        this.currentBestValue = other.currentBestValue;
    }

    int[] getBitwiseRepresentation() { return bitwiseRepresentation; }

    double[] getDecimalRepresentationOfBestCandidate() {
        return decimalRepresentation;
    }

    void createFromBitwiseRepresentation(int[] bitwise){
        bitwiseRepresentation = bitwise.clone();
        decodeBitwiseRepresentation(bitwiseRepresentation, decimalRepresentation);
        currentBestValue = f.evaluate(decimalRepresentation);
    }

    void decodeBitwiseRepresentation(int[] bitwise, double[] decimal){
        int currentStartIndex = 0;
        for (int i=0;i<argsCount;++i){
            long base2ToBase10 = 0;

            for (int j=0;j<bitLens[i];++j){
                base2ToBase10 *= 2;
                base2ToBase10 += (bitwise[currentStartIndex+j]);
            }

            decimal[i] = minValues[i] + (double)base2ToBase10 / (double)((long)(1 << bitLens[i]) - 1) * (maxValues[i] - minValues[i]);
            currentStartIndex += bitLens[i];
        }
    }

    void generateRandomCandidate() {
        for (int i=0;i<bitwiseRepresentation.length;++i){
            bitwiseRepresentation[i] = GlobalState.randomGen.nextBoolean() ? 1 : 0;
        }
        decodeBitwiseRepresentation(bitwiseRepresentation, decimalRepresentation);
        currentBestValue = f.evaluate(decimalRepresentation);
    }

    double evaluate(){
        decodeBitwiseRepresentation(bitwiseRepresentation, decimalRepresentation);
        return f.evaluate(decimalRepresentation);
    }

    void mutateCandidate(){
        for (int i=0;i<bitwiseRepresentation.length;++i){
            if (GlobalState.randomGen.nextDouble() < GlobalState.mutationProbability){
                bitwiseRepresentation[i] = 1 - bitwiseRepresentation[i];
            }
        }
    }
}
