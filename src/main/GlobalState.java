package main;

import functions.FunctionDescription;

import functions.Function;
import optimizationAlgorithms.OptimizationAlgorithmDescription;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Random;

public class GlobalState {
    public static OptimizationAlgorithmDescription      optimizationAlgorithmDescription;
    public static FunctionDescription                   functionUsed;
    public static Function                              function;
    public static ArrayList<Double>                     resultsBestValues = new ArrayList<>();
    public static double                                bestValue;
    public static double[]                              bestValueArguments;
    public static double[]                              allBestValueArguments;
    public static double                                worstValue;
    public static double                                mean;
    public static double                                sum;
    public static double                                standardDeviation;
    public static int                                   totalRuns;
    public static int                                   currentRun;
    public static boolean                               wasReset;
    public static int                                   iterationsCount = 100;
    public static int                                   functionArguments = 5;
    public static int                                   optimizationType = 1; // 1 - minimization, 2 - maximization
    public static double                                epsilon = 0.01;
    public static Random                                randomGen = new Random();
    public static double                                mutationProbability = 0.01;
    public static double                                crossOverProbability = 0.3;
    public static int                                   populationSize = 100;

    public static void reset(){
        wasReset = true;
        currentRun = 1;
        bestValue = worstValue = mean = sum = standardDeviation = 0;
        resultsBestValues.clear();
    }

    public static void setOptimizationAlgorithmDescription(OptimizationAlgorithmDescription oad){
        optimizationAlgorithmDescription = oad;
    }

    public static void setTotalRuns(int runsCount){
        totalRuns = runsCount;
    }

    public static void addResultValue(double value) {
        if (wasReset) {
            bestValue = worstValue = value;
            standardDeviation = 0;
            wasReset = false;
            if (bestValueArguments != null) {
                allBestValueArguments = bestValueArguments.clone();
            }
        } else {
        if (optimizationType == 1) {
            if (value < bestValue) {
                    bestValue = value;
                    if (bestValueArguments != null) {
                        allBestValueArguments = bestValueArguments.clone();
                    }
                }
                if (value > worstValue) {
                    worstValue = value;
                }
            } else {
                if (value > bestValue) {
                    bestValue = value;
                    if (bestValueArguments != null) {
                        allBestValueArguments = bestValueArguments.clone();
                    }
                }
                if (value < worstValue) {
                    worstValue = value;
                }
            }
        }
        sum += value;
        mean = sum / currentRun;
        resultsBestValues.add(value);
    }

    public static double getStandardDeviation(){
        double sd = 0, mul;
        for (int i=0;i<resultsBestValues.size();++i){
            mul = resultsBestValues.get(i) - mean;
            sd += mul * mul;
        }
        sd /= resultsBestValues.size();
        return Math.sqrt(sd);
    }

    public static void incrementCurrentRun() {
        ++currentRun;
    }

    public static double getTheWorstValue(){
        if (optimizationType == 1){
            return Double.MAX_VALUE;
        }
        return Double.MIN_VALUE;
    }

    public static double roundDoubleValueToXDecimals(double value, int x){
        if (Math.abs(value) < 1e-9){
            return 0.0;
        }
        return new BigDecimal(value).setScale(x, RoundingMode.HALF_EVEN).doubleValue();
    }

    public static double roundDoubleValueToTwoDecimals(double value){
        return roundDoubleValueToXDecimals(value, 2);
    }

    public static void roundDoubleArrayToTwoDecimals(double[] arr){
        for (int i=0;i<arr.length;++i){
            arr[i] = roundDoubleValueToXDecimals(arr[i], 5);
        }
    }

    private static void updateBestValueArgumentsArray(double[] args){
        roundDoubleArrayToTwoDecimals(args);
        bestValueArguments = args.clone();
    }

    public static double getBetterValue(double bestValue, double currentBest, double[] args){
        double ans = bestValue;
        if (optimizationType == 1){
            if (currentBest < ans){
                ans = currentBest;
                if (args != null){
                    updateBestValueArgumentsArray(args);
                }
            }
        } else {
            if (currentBest > ans) {
                ans = currentBest;
                if (args != null){
                    updateBestValueArgumentsArray(args);
                }
            }
        }
        return ans;
    }

    public static double getWorseValue(double worstValue, double value){
        double ans = worstValue;
        if (optimizationType == 1){
            if (value > ans){
                ans = value;
            }
        } else {
            if (value < ans){
                ans = value;
            }
        }
        return ans;
    }

    public static boolean solutionIsBetterThanBest(double bestValue, double curr){
        if (optimizationType == 1){
            return curr < bestValue;
        }
        return curr > bestValue;
    }

    public static boolean simulatedAnnealingSolutionIsBetterThanBest(double bestValue, double curr, double T){
        double value = randomGen.nextDouble();
        return value < Math.exp(-Math.abs(curr - bestValue) / T);
    }
}
