package commandline;

import main.CustomPair;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class CommandDescription {
    private Commands                        id;
    private String                          name;
    private ArrayList<CustomPair<Integer,String>> options = new ArrayList<>();
    private ArrayList<String>               stringDefaultValues = new ArrayList<>();
    private ArrayList<Integer>              numberDefaultValues = new ArrayList<>();
    private ArrayList<Boolean>              booleanDefaultValues = new ArrayList<>();

    CommandDescription(String commandName, Commands commandId) {
        name = commandName;
        id = commandId;
    }

    void addStringOption(String option, String defaultValue) {
        options.add(new CustomPair(0, option));

        stringDefaultValues.add(defaultValue);
        numberDefaultValues.add(0);
        booleanDefaultValues.add(false);
    }

    void addNumberOption(String option, int defaultValue) {
        options.add(new CustomPair(1, option));

        stringDefaultValues.add(null);
        numberDefaultValues.add(defaultValue);
        booleanDefaultValues.add(false);
    }

    void addBooleanOption(String option, boolean defaultValue) {
        options.add(new CustomPair(2, option));

        stringDefaultValues.add(null);
        numberDefaultValues.add(0);
        booleanDefaultValues.add(defaultValue);
    }

    int getNumberOfOptions() {
        return options.size();
    }

    Commands getId() {
        return id;
    }

    String  getName() {
        return name;
    }

    String getOptionsAsString(){
        StringBuilder stringBuilder = new StringBuilder();

        for (int tr = 0; tr < options.size(); ++tr){
            stringBuilder.append("\t");
            stringBuilder.append(options.get(tr).getValue());
            stringBuilder.append("\tdefault: ");
            switch (options.get(tr).getKey())
            {
                case 0:
                    stringBuilder.append(stringDefaultValues.get(tr));
                    break;
                case 1:
                    stringBuilder.append(numberDefaultValues.get(tr));
                    break;
                case 2:
                    stringBuilder.append(booleanDefaultValues.get(tr));
                    break;
            }
            if (tr + 1 != options.size()){
                stringBuilder.append('\n');
            }
        }

        return stringBuilder.toString();
    }

    CommandOptions parseOptions(String optionsString) throws IllegalStateException {
        CommandOptions resultOptions = new CommandOptions();
        Set<Integer> foundCommands = new HashSet<Integer>();

        String[] optionsParts = optionsString.split(" ");
        for (String option:optionsParts){
            if (option.length() == 0) {
                continue;
            }
            boolean found = false;
            for (int tr = 0; tr < options.size(); ++tr){
                if (option.startsWith(options.get(tr).getValue())) {
                    switch (options.get(tr).getKey()){
                        case 0:
                            resultOptions.addOption(new CommandOption(options.get(tr).getValue(),option.substring(options.get(tr).getValue().length())));
                            found = true;
                            foundCommands.add(tr);
                            break;
                        case 1:
                            try {
                                int numberValue = Integer.parseInt(option.substring(options.get(tr).getValue().length()));
                                resultOptions.addOption(new CommandOption(options.get(tr).getValue(),numberValue));
                                found = true;
                                foundCommands.add(tr);
                            } catch (NumberFormatException exception) {
                                throw new IllegalStateException("Invalid option: " + option);
                            }
                            break;
                        case 2:
                            if (option.length() == options.get(tr).getValue().length()){
                                resultOptions.addOption(new CommandOption(options.get(tr).getValue(),true));
                                found = true;
                                foundCommands.add(tr);
                            }
                            break;
                        default:
                            throw new IllegalStateException("Internal error!");
                    }
                    break;
                }
            }
            if (found){
                continue;
            }
            throw new IllegalStateException("Invalid option: " + option);
        }

        for (int tr = 0; tr < options.size(); ++tr) {
            if (foundCommands.contains(tr)) {
                continue;
            }
            switch (options.get(tr).getKey()) {
                case 0:
                    resultOptions.addOption(new CommandOption(options.get(tr).getValue(), stringDefaultValues.get(tr)));
                    break;
                case 1:
                    resultOptions.addOption(new CommandOption(options.get(tr).getValue(), numberDefaultValues.get(tr)));
                    break;
                case 2:
                    resultOptions.addOption(new CommandOption(options.get(tr).getValue(), booleanDefaultValues.get(tr)));
                    break;
                default:
                    throw new IllegalStateException("Internal error!");
            }
        }

        return resultOptions;
    }
}
