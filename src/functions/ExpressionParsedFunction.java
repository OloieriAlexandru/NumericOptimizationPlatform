package functions;

import javafx.util.Pair;
import optimizationAlgorithms.CandidateHCSA;

import java.util.ArrayList;

public class ExpressionParsedFunction implements IFunction {
    private String              expression;
    private ArrayList<String>   arguments;

    private double          minVals[];
    private double          maxVals[];

    public String[] getArgumentsNames(){
        String[] variableNames = new String[arguments.size()];
        for (int i=0;i<arguments.size();++i){
            variableNames[i] = arguments.get(i);
        }
        return variableNames;
    }

    @Override
    public int[] getArgumentsBitsLen(int precision) {
        int args = getArgumentsCount();
        int[] bitLens = new int[args];
        for (int i=0;i<args;++i){
            bitLens[i] = CandidateHCSA.calculateBitLen(minVals[0],maxVals[0],precision);
        }
        return bitLens;
    }

    @Override
    public Pair<double[], double[]> getArgumentsLimits() {
        int args = getArgumentsCount();
        double[]    retMin = new double[args];
        double[]    retMax = new double[args];
        for (int i=0;i<args;++i){
            retMin[i] = minVals[0];
            retMax[i] = maxVals[0];
        }
        return new Pair<>(retMin,retMax);
    }

    @Override
    public Pair<Double, Double> getFunctionArgumentLimits(int index) {
        return new Pair<>(minVals[index], maxVals[index]);
    }

    public ExpressionParsedFunction(String expr){
        expression = expr;
        arguments = ExpressionEvaluator.getFunctionVariables(expr);
        minVals = new double[arguments.size()];
        maxVals = new double[arguments.size()];
        for (int i=0;i<arguments.size();++i){
            minVals[i] = -5.0;
            maxVals[i] = 5.0;
        }
    }

    @Override
    public int getArgumentsCount() {
        return arguments.size();
    }

    @Override
    public double evaluate(double... args) {
        if (args.length == 0){
            return ExpressionEvaluator.evaluate(expression, 1.0);
        }  else {
            return ExpressionEvaluator.evaluate(expression, args[0]);
        }
    }

    @Override
    public void addLimits(double[] minValues, double[] maxValues) {
        minVals = minValues.clone();
        maxVals = maxValues.clone();
    }

    @Override
    public void updateMinLimit(int index, double val) {
        minVals[index] = val;
    }

    @Override
    public void updateMaxLimit(int index, double val) {
        maxVals[index] = val;
    }
}
