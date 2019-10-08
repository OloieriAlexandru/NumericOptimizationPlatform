package functions;

import javafx.util.Pair;

import java.util.ArrayList;

public class FunctionDescription {
    private FunctionArgumentsType           functionArgumentsType;
    private String                          name;
    private Function functionObject;
    private int                             optimizationType;
    private ArrayList<FunctionArgument>     arguments = new ArrayList<>();

    public FunctionDescription(String nm, FunctionArgumentsType argumentsType, int optType){
        name = nm;
        optimizationType = optType;
        functionArgumentsType = argumentsType;
    }

    public void setFunction(Function function){
        functionObject = function;
    }

    public void addArgument(FunctionArgument arg) {
        arguments.add(arg);
    }

    public String getName() {
        return name;
    }

    public Function getFunction() {
        return functionObject;
    }

    public Pair<double[],double[]> getFunctionLimitsAsArrays(){
        int args = arguments.size();
        double minArray[] = new double[args];
        double maxArray[] = new double[args];
        for (int i=0;i<args;++i){
            minArray[i] = arguments.get(i).getMinValue();
            maxArray[i] = arguments.get(i).getMaxValue();
        }
        return new Pair<>(minArray, maxArray);
    }

    public Pair<Double, Double> getFunctionLimits(){
        double min = arguments.get(0).getMinValue();
        double max = arguments.get(0).getMaxValue();
        return new Pair<>(min, max);
    }

    public FunctionArgumentsType getArgumentsType(){
        return functionArgumentsType;
    }

    public String[] getArgumentsNames() {
        String[] ret = new String[arguments.size()];
        for (int i=0;i<arguments.size();++i){
            ret[i] = arguments.get(i).getName();
        }
        return ret;
    }

    public int getOptimizationType() {
        return optimizationType;
    }
}
