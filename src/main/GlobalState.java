package main;

import functions.FunctionDescription;

import functions.Function;
import optimizationAlgorithms.OptimizationAlgorithmDescription;

public class GlobalState {
    public static OptimizationAlgorithmDescription      optimizationAlgorithmDescription;
    public static FunctionDescription                   functionUsed;
    public static Function function;
    public static double                                bestValue;
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

    public static void reset(){
        wasReset = true;
        currentRun = 1;
        bestValue = worstValue = mean = sum = standardDeviation = 0;
    }

    public static void setOptimizationAlgorithmDescription(OptimizationAlgorithmDescription oad){
        optimizationAlgorithmDescription = oad;
    }

    public static void updateUsedFunction(FunctionDescription description){
        functionUsed = description;
    }

    public static void setTotalRuns(int runsCount){
        totalRuns = runsCount;
    }

    public static void addResultValue(double value){
        if (wasReset){
            bestValue = worstValue = value;
            standardDeviation = 0;
            wasReset = false;
        } else {
            if (optimizationType == 1){
                if (value < bestValue){
                    bestValue = value;
                }
                if (value > worstValue){
                    worstValue = value;
                }
            } else {
                if (value > bestValue){
                    bestValue = value;
                }
                if (value < worstValue){
                    worstValue = value;
                }
            }
        }
        sum += value;
        mean = sum / currentRun;
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

    public static double getBetterValue(double bestValue, double currentBest){
        double ans = bestValue;
        if (optimizationType == 1){
            if (currentBest < ans){
                ans = currentBest;
            }
        } else {
            if (currentBest > ans) {
                ans = currentBest;
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
}
