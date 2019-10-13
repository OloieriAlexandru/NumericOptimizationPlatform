package commandline;

import main.PanelConsoleOutput;
import main.Platform;

import java.util.ArrayList;

public class CommandLine {
    private Platform                        callback;
    private PanelConsoleOutput              consoleOutput;
    private ArrayList<CommandDescription>   commandDescriptions = new ArrayList<>();
    private static int                      commandInvalidIndex = -1;

    public CommandLine(Platform platform){
        callback = platform;

        initCommandLine();
    }

    private void initCommandLine() {
        commandDescriptions.add(new CommandDescription("run", Commands.RUN));
        commandDescriptions.get(commandDescriptions.size()-1).addNumberOption("-runs:", 1);
        commandDescriptions.get(commandDescriptions.size()-1).addBooleanOption("-disable-graph", false);

        commandDescriptions.add(new CommandDescription("toggle", Commands.TOGGLE_CONSOLE_OUTPUT));

        commandDescriptions.add(new CommandDescription("cmd", Commands.CMD));
        commandDescriptions.get(commandDescriptions.size()-1).addBooleanOption("-details", false);

        commandDescriptions.add(new CommandDescription("clc", Commands.CLC));
    }

    private String prepareCommand(String command) {
        StringBuilder   stringBuilder = new StringBuilder();
        int             tr = 0, cLen = command.length();

        while (tr < cLen && (command.charAt(tr) == ' ' || command.charAt(tr) == '\t' || command.charAt(tr) == '\n')) {
            ++tr;
        }
        while (cLen > tr && (command.charAt(cLen - 1) == ' ' || command.charAt(cLen - 1) == '\t' || command.charAt(cLen - 1) == '\n')){
            --cLen;
        }

        for (; tr < cLen; ++tr) {
            if (command.charAt(tr) == ' ' || command.charAt(tr) == '\t' || command.charAt(tr) == '\n') {
                stringBuilder.append(' ');
                while (tr < cLen-1 && (command.charAt(tr+1) == ' ' || command.charAt(tr+1) == '\t' || command.charAt(tr+1) == '\n')) {
                    ++tr;
                }
            } else {
                stringBuilder.append(command.charAt(tr));
            }
        }

        return stringBuilder.toString();
    }

    private int getCommandIndex(String command) {
        for (int tr = 0; tr < commandDescriptions.size(); ++tr) {
            if (commandDescriptions.get(tr).getName().equals(command)){
                return tr;
            }
        }
        return commandInvalidIndex;
    }

    public void parseAndExecuteCommand(String command){
        command = prepareCommand(command);
        String[] commandParts = command.split(" ",2);
        if (command.length() == 0 || commandParts.length == 0)
            return;

        int commandIndex = getCommandIndex(commandParts[0]);
        if (commandIndex == commandInvalidIndex) {
            consoleOutput.addLine("No such command!");
            return;
        }

        CommandOptions commandOptions = null;
        try{
            if (commandParts.length == 2) {
                commandOptions = commandDescriptions.get(commandIndex).parseOptions(commandParts[1]);
            } else {
                commandOptions = commandDescriptions.get(commandIndex).parseOptions("");
            }

            callback.executeCommand(commandDescriptions.get(commandIndex).getId(), commandOptions);
        }
        catch (IllegalStateException exception) {
            consoleOutput.addLine(exception.getMessage());
        }
    }

    public void setConsoleOutput(PanelConsoleOutput co) {
        consoleOutput = co;
    }

    public void printCommandLineInfo(boolean detailed){
        for (int tr = 0; tr < commandDescriptions.size(); ++tr){
            consoleOutput.addLine(commandDescriptions.get(tr).getName());
            if (detailed && commandDescriptions.get(tr).getNumberOfOptions() > 0){
                consoleOutput.addLine(commandDescriptions.get(tr).getOptionsAsString());
            }
        }
    }

    public void clearConsole(){
        consoleOutput.clearConsole();
    }
}
