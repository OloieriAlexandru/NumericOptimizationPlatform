package optimizationAlgorithms;

import functions.Function;
import javafx.util.Pair;
import main.GlobalState;

import java.util.Arrays;
import java.util.Random;

public class CandidateHCSA {
    private Function f;
    private int[]           bitwiseRepresentation;
    private int[]           bitLens;
    private double[]        decimalRepresentation;
    private double[]        minValues, maxValues;
    private double          currentBestValue;
    private int             argsCount;
    private Random          random;

    CandidateHCSA(Function function, int prec){
        f = function;
        argsCount = function.getArgumentsCount();
        Pair<double[],double[]> functionLimits = function.getArgumentsLimits();
        minValues = functionLimits.getKey();
        maxValues = functionLimits.getValue();

        bitLens = function.getArgumentsBitsLen(prec);

        int bitwiseRepresentationLen = 0;
        for (int i=0;i<bitLens.length;++i){
            bitwiseRepresentationLen += bitLens[i];
        }

        bitwiseRepresentation = new int[bitwiseRepresentationLen];
        decimalRepresentation = new double[argsCount];

        random = new Random();
    }

    public static int calculateBitLen(double minValue, double maxValue, int precision){
        double value = (maxValue - minValue) * Math.pow(10.0, (double)precision);
        double toCeil = Math.log(value) / Math.log(2.0);
        return (int)Math.ceil(toCeil);
    }

    void decodeBitwiseRepresentation(int[] bitwise, double[] decimal){
        int currentStartIndex = 0;
        for (int i=0;i<argsCount;++i){
            long    base2ToBase10 = 0;

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
            bitwiseRepresentation[i] = random.nextBoolean() ? 1 : 0;
        }
        decodeBitwiseRepresentation(bitwiseRepresentation, decimalRepresentation);
        currentBestValue = f.evaluate(decimalRepresentation);
    }

    double[] getDecimalRepresentationOfBestCandidate() {
        return decimalRepresentation;
    }

    double getCurrentBest() {
        return currentBestValue;
    }

    boolean hillClimbingFirstImprovementExploration(){
        double[]    currentDecimalRepresentation = new double[argsCount];
        int[]       currentBitwiseRepresentation = bitwiseRepresentation.clone();
        double      currentValue;
        boolean     foundBetterCandidate = false;

        for (int i=0;i<currentBitwiseRepresentation.length && !foundBetterCandidate;++i){
            currentBitwiseRepresentation[i] = 1 - currentBitwiseRepresentation[i];

            decodeBitwiseRepresentation(currentBitwiseRepresentation, currentDecimalRepresentation);
            currentValue = f.evaluate(currentDecimalRepresentation);

            if (GlobalState.solutionIsBetterThanBest(currentBestValue, currentValue)){
                currentBestValue = currentValue;
                bitwiseRepresentation = currentBitwiseRepresentation.clone();
                decimalRepresentation = currentDecimalRepresentation.clone();
                foundBetterCandidate = true;
            }

            currentBitwiseRepresentation[i] = 1 - currentBitwiseRepresentation[i];
        }

        return foundBetterCandidate;
    }

    boolean hillClimbingBestImprovementExploration(){
        double[]    currentDecimalRepresentation = new double[argsCount];
        int[]       currentBitwiseRepresentation = bitwiseRepresentation.clone();
        double      currentValue;
        boolean     foundBetterCandidate = false;

        for (int i=0;i<currentBitwiseRepresentation.length;++i){
            currentBitwiseRepresentation[i] = 1 - currentBitwiseRepresentation[i];

            decodeBitwiseRepresentation(currentBitwiseRepresentation, currentDecimalRepresentation);
            currentValue = f.evaluate(currentDecimalRepresentation);

            if (GlobalState.solutionIsBetterThanBest(currentBestValue, currentValue)){
                currentBestValue = currentValue;
                bitwiseRepresentation = currentBitwiseRepresentation.clone();
                decimalRepresentation = currentDecimalRepresentation.clone();
                foundBetterCandidate = true;
            }

            currentBitwiseRepresentation[i] = 1 - currentBitwiseRepresentation[i];
        }

        return foundBetterCandidate;
    }

    boolean simulatedAnnealingExploration(){
        return false;
    }
}
