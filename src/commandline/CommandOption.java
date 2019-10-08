package commandline;

public class CommandOption {
    private int             type;
    private int             numberValue;
    private String          stringValue;
    private boolean         booleanValue;
    private String          optionName;

    public CommandOption(String option, String optionValue) {
        optionName = option;
        type = 0;
        stringValue = optionValue;
    }

    public CommandOption(String option, int optionValue) {
        optionName = option;
        type = 1;
        numberValue = optionValue;
    }

    public CommandOption(String option, boolean optionValue) {
        optionName = option;
        type = 2;
        booleanValue = optionValue;
    }

    public int getType() {
        return type;
    }

    public int getNumberValue() {
        return numberValue;
    }

    public String getStringValue() {
        return stringValue;
    }

    public boolean getBooleanValue() {
        return booleanValue;
    }

    public String getOptionName() {
        return optionName;
    }

    public void printOption(){
        switch (type){
            case 0:
                System.out.println(optionName + " " + stringValue);
                break;
            case 1:
                System.out.println(optionName + " " + numberValue);
                break;
            case 2:
                System.out.println(optionName + " " + booleanValue);
                break;
        }
    }
}
