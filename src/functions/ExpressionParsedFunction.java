package functions;

import javafx.util.Pair;
import optimizationAlgorithms.CandidateHCSA;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ExpressionParsedFunction extends Function {
    private String              expression;
    private ArrayList<String>   arguments;

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
            bitLens[i] = CandidateHCSA.calculateBitLen(minVals[i],maxVals[i],precision);
        }
        return bitLens;
    }

    @Override
    public Pair<double[], double[]> getArgumentsLimits() {
        int args = getArgumentsCount();
        double[]    retMin = new double[args];
        double[]    retMax = new double[args];
        for (int i=0;i<args;++i){
            retMin[i] = minVals[i];
            retMax[i] = maxVals[i];
        }
        return new Pair<>(retMin,retMax);
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
        if (args.length != arguments.size()){
            return 0.0;
        }
        Map<String,Double> variableLimits = new HashMap<>();
        for (int i=0;i<arguments.size();++i) {
            variableLimits.put(arguments.get(i), args[i]);
        }
        return ExpressionEvaluator.evaluate(expression, variableLimits);
    }
}
