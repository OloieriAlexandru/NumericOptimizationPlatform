package functions;

import main.CustomPair;
import main.PanelConsoleOutput;

import java.util.ArrayList;

public class FunctionFactory {
    private PanelConsoleOutput                              consoleOutput;
    private ExpressionEvaluator                             expressionEvaluator;
    private static ArrayList<FunctionDescription>           predefinedFunctions;
    private static boolean                                  predefinedFunctionInit = false;

    public FunctionFactory(PanelConsoleOutput panelConsoleOutput){
        consoleOutput = panelConsoleOutput;
        expressionEvaluator = new ExpressionEvaluator(consoleOutput);

        if (!predefinedFunctionInit){
            initPredefinedFunctions();
        }
    }

    public static ArrayList<String> getPredefinedFunctionsNames() {
        if (!predefinedFunctionInit){
            initPredefinedFunctions();
        }
        
        ArrayList<String> predefinedFunctionsName = new ArrayList<>();

        for (int i=0;i<predefinedFunctions.size();++i){
            predefinedFunctionsName.add(predefinedFunctions.get(i).getName());
        }

        return predefinedFunctionsName;
    }

    public Function getFunction(String str){
        str = str.toLowerCase();

        Function predefinedFunction = getPredefinedFunction(str);

        if (predefinedFunction != null){
            return predefinedFunction;
        }

        if (expressionEvaluator.checkExpression(str)){
            return new ExpressionParsedFunction(str);
        }

        return null;
    }

    public static Function getPredefinedFunction(String str){
        str = str.toLowerCase();
        for (FunctionDescription predefinedFunction : predefinedFunctions){
            if (predefinedFunction.getName().toLowerCase().equals(str)){
                return predefinedFunction.getFunction();
            }
        }
        return null;
    }

    public static FunctionDescription getPredefinedFunctionDescription(String str){
        str = str.toLowerCase();
        for (FunctionDescription predefinedFunction : predefinedFunctions){
            if (predefinedFunction.getName().toLowerCase().equals(str)){
                Function function = getClonedFunction(predefinedFunction);
                predefinedFunction.setFunction(function);
                return predefinedFunction;
            }
        }
        return null;
    }

    private static Function getClonedFunction(FunctionDescription functionDescription) {
        if (functionDescription == null) {
            return null;
        }
        String functionName = functionDescription.getName().toLowerCase();
        Function function;
        if (functionName.equals("rastrigin")) {
            function = new FRastrigin();
        } else if (functionName.equals("beale")) {
            function = new FBeale();
        } else if (functionName.equals("sixhump")) {
            function = new FSixHumpCamel();
        } else if (functionName.equals("rosenbrock")) {
            function = new FRosenbrock();
        } else if (functionName.equals("sphere")) {
            function = new FSphere();
        } else if (functionName.equals("trid")) {
            function = new FTrid();
        } else if (functionName.equals("zakharov")) {
            function = new FZakharov();
        } else if (functionName.equals("schwefel")) {
            function = new FSchwefel();
        } else if (functionName.equals("griewank")){
            function = new FGriewank();
        } else {
            return null;
        }
        CustomPair<double[],double[]> functionLimits = functionDescription.getFunctionLimitsAsArrays();
        function.addLimits(functionLimits.getKey(), functionLimits.getValue());
        return function;
    }

    private static void initPredefinedFunctions(){
        predefinedFunctionInit = true;
        predefinedFunctions = new ArrayList<>();

        FunctionDescription rastriginFunctionDescription = new FunctionDescription("Rastrigin", FunctionArgumentsType.SameIntervals, 1);
        rastriginFunctionDescription.addArgument(new FunctionArgument("all", -5.12,5.12));

        FunctionDescription bealeFunctionDescription = new FunctionDescription("Beale", FunctionArgumentsType.SameIntervals, 1);
        bealeFunctionDescription.addArgument(new FunctionArgument("all", -4.5, 4.5));

        FunctionDescription sixHumpFunctionDescription = new FunctionDescription("SixHump", FunctionArgumentsType.DifferentIntervals, 1);
        sixHumpFunctionDescription.addArgument(new FunctionArgument("x1", -3,3));
        sixHumpFunctionDescription.addArgument(new FunctionArgument("x2", -2,2));

        FunctionDescription rosenbrockFunctionDescription = new FunctionDescription("Rosenbrock", FunctionArgumentsType.SameIntervals, 1);
        rosenbrockFunctionDescription.addArgument(new FunctionArgument("all", -5.0, 10.0));

        FunctionDescription sphereFunctionDescription = new FunctionDescription("Sphere", FunctionArgumentsType.SameIntervals, 1);
        sphereFunctionDescription.addArgument(new FunctionArgument("all", -10.0, 10.0));

        FunctionDescription tridFunctionDescription = new FunctionDescription("Trid", FunctionArgumentsType.SameIntervals, 1);
        tridFunctionDescription.addArgument(new FunctionArgument("all", -5.0, 5));

        FunctionDescription zakharovFunctionDescription = new FunctionDescription("Zakharov", FunctionArgumentsType.SameIntervals, 1);
        zakharovFunctionDescription.addArgument(new FunctionArgument("all", -5.0, 10));

        FunctionDescription schwefelFunctionDescription = new FunctionDescription("Schwefel", FunctionArgumentsType.SameIntervals, 1);
        schwefelFunctionDescription.addArgument(new FunctionArgument("all", -500.0, 500.0));

        FunctionDescription griewankFunctionDescription = new FunctionDescription("Griewank", FunctionArgumentsType.SameIntervals, 1);
        griewankFunctionDescription.addArgument(new FunctionArgument("all", -600.0, 600.0));

        predefinedFunctions.add(rastriginFunctionDescription);
        predefinedFunctions.add(griewankFunctionDescription);
        predefinedFunctions.add(bealeFunctionDescription);
        predefinedFunctions.add(sixHumpFunctionDescription);
        predefinedFunctions.add(rosenbrockFunctionDescription);
        predefinedFunctions.add(sphereFunctionDescription);
        predefinedFunctions.add(tridFunctionDescription);
        predefinedFunctions.add(zakharovFunctionDescription);
        predefinedFunctions.add(schwefelFunctionDescription);
    }

}
