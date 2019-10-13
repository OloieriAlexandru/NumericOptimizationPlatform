package functions;

import main.PanelConsoleOutput;

import java.util.ArrayList;
import java.util.Map;

class ExpressionEvaluationState{
    Map<String,Double> variableLimits;
    String expr;
    int index;

    ExpressionEvaluationState(String e, int i, Map<String,Double> varLimits){
        expr = e;
        index = i;
        variableLimits = varLimits;
    }
}

public class ExpressionEvaluator {
    private static PanelConsoleOutput consoleOutput;

    ExpressionEvaluator(PanelConsoleOutput panelConsoleOutput) {
        if (consoleOutput == null){
            consoleOutput = panelConsoleOutput;
        }
    }

    private static boolean isRservedSymbol(char ch){
        return ch == '*' || ch == '/' || ch == '+' || ch == '-' || ch  == '(' || ch == ')';
    }

    public static ArrayList<String> getFunctionVariables(String str){
        ArrayList<String> variables = new ArrayList<>();
        for (int i=0;i<str.length();++i){
            if (isRservedSymbol(str.charAt(i))){
                continue;
            }
            if (str.charAt(i) >= '0' && str.charAt(i) <= '9'){
                while (i < str.length() && str.charAt(i)>='0' && str.charAt(i)<='9'){
                    ++i;
                }
                --i;
                continue;
            }
            StringBuilder       stringBuilder = new StringBuilder();
            while (i < str.length() && !isRservedSymbol(str.charAt(i))){
                stringBuilder.append(str.charAt(i));
                ++i;
            }
            --i;
            variables.add(stringBuilder.toString());
        }
        return variables;
    }

    boolean checkExpression(String expression) {
        return true;
    }

    private static double evaluateTerm(ExpressionEvaluationState st){
        if (st.index < st.expr.length() && st.expr.charAt(st.index) == '('){
            ++st.index;
            double t = evaluateAddSub(st);
            ++st.index;
            return t;
        }
        if (st.index < st.expr.length() && st.expr.charAt(st.index) >= '0' && st.expr.charAt(st.index) <= '9'){
            double ans = 0;
            while (st.index < st.expr.length() && st.expr.charAt(st.index) >= '0' && st.expr.charAt(st.index) <= '9'){
                ans = ans * 10 + st.expr.charAt(st.index)-'0';
                ++st.index;
            }
            return ans;
        }
        StringBuilder var = new StringBuilder();
        while (st.index < st.expr.length() && !isRservedSymbol(st.expr.charAt(st.index))){
            var.append(st.expr.charAt(st.index));
            ++st.index;
        }
        String currVar = var.toString();
        if (!st.variableLimits.containsKey(currVar)){
            return 0.0;
        }
        return st.variableLimits.get(currVar);
    }

    private static double evaluateMulDiv(ExpressionEvaluationState st){
        double t1 = evaluateTerm(st);
        while (st.index < st.expr.length() && (st.expr.charAt(st.index) == '*' || st.expr.charAt(st.index) == '/')){
            boolean isMul = st.expr.charAt(st.index) == '*';
            ++st.index;
            double t2 = evaluateTerm(st);
            if (isMul){
                t1 *= t2;
            } else {
                if (t2 != 0.0){
                    t1 /= t2;
                }
            }
        }
        return t1;
    }

    private static double evaluateAddSub(ExpressionEvaluationState st){
        double t1 = evaluateMulDiv(st);
        while (st.index < st.expr.length() && (st.expr.charAt(st.index) == '+' || st.expr.charAt(st.index) == '-')) {
            boolean isAdd = st.expr.charAt(st.index) == '+';
            ++st.index;
            double t2 = evaluateMulDiv(st);
            if (isAdd){
                t1 += t2;
            } else {
                t1 -= t2;
            }
        }
        return t1;
    }

    public static double evaluate(String expression, Map<String,Double> variableLimits){
        ExpressionEvaluationState state = new ExpressionEvaluationState(expression, 0, variableLimits);
        return evaluateAddSub(state);
    }
}
