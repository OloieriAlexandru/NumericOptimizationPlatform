package functions;

public class FunctionArgument {
    private String  name;
    private double  minValue;
    private double  maxValue;

    FunctionArgument(String nm, double miV, double maV) {
        name = nm;
        minValue = miV;
        maxValue = maV;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMinValue() {
        return minValue;
    }

    public void setMinValue(double minValue) {
        this.minValue = minValue;
    }

    public double getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(double maxValue) {
        this.maxValue = maxValue;
    }
}
