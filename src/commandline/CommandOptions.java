package commandline;

import java.util.ArrayList;

public class CommandOptions {
    private ArrayList<CommandOption> options = new ArrayList<>();

    public void printAllOptions() {
        for (CommandOption option: options){
            option.printOption();
        }
    }

    public void addOption(CommandOption commandOption) {
        options.add(commandOption);
    }

    public int getNumberValue(String optionName){
        for (int tr = 0; tr < options.size(); ++tr){
            if (options.get(tr).getOptionName().equals(optionName)){
                return options.get(tr).getNumberValue();
            }
        }
        return 0;
    }

    public String getStringValue(String optionName){
        for (int tr = 0; tr < options.size(); ++tr) {
            if (options.get(tr).getOptionName().equals(optionName)){
                return options.get(tr).getStringValue();
            }
        }
        return "";
    }

    public boolean getBooleanValue(String optionName) {
        for (int tr = 0; tr < options.size(); ++tr){
            if (options.get(tr).getOptionName().equals(optionName)){
                return options.get(tr).getBooleanValue();
            }
        }
        return false;
    }
}
