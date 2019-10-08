package main;

import commandline.CommandLine;
import commandline.CommandOptions;
import commandline.Commands;
import functions.Function;
import optimizationAlgorithms.*;

public class Platform {
    private UserInterface                       ui;
    private CommandLine                         cmd;
    private IOptimizationAlgorithm              optimizationAlgorithm;
    private OptimizationAlgorithmFactory        optimizationAlgorithmFactory;

    public Platform() {
        cmd                             = new CommandLine(this);
        ui                              = new UserInterface(cmd);
        optimizationAlgorithmFactory    = new OptimizationAlgorithmFactory(ui);

        optimizationAlgorithm           = optimizationAlgorithmFactory.getOptimizationAlgorithm("Hill Climbing - First Improvement");
    }

    public void run() {
        ui.setVisible(true);
    }

    public void executeCommand(Commands command, CommandOptions commandOptions) {
        switch (command) {
            case RUN:
                commandRun(commandOptions);
                break;
            case TOGGLE_CONSOLE_OUTPUT:
                commandToggleConsoleOutput(commandOptions);
                break;
            case CMD:
                commandCmd(commandOptions);
                break;
            case CLC:
                commandClc(commandOptions);
                break;
        }
    }

    private void commandRun(CommandOptions commandOptions){
        if (GlobalState.functionUsed == null){
            return;
        }
        Function function = GlobalState.functionUsed.getFunction();
        optimizationAlgorithm = optimizationAlgorithmFactory.getOptimizationAlgorithm(GlobalState.optimizationAlgorithmDescription.getLongName());
        if (function == null || optimizationAlgorithm == null){
            return;
        }
        optimizationAlgorithm.setFunction(function);
        int runs = commandOptions.getNumberValue("-runs:");
        GlobalState.reset();
        GlobalState.setTotalRuns(runs);
        for (int i=0;i<runs;++i){
            double curr = optimizationAlgorithm.run();
            GlobalState.addResultValue(curr);
            ui.graph.printInformation();
            if (i+1 < runs){
                GlobalState.incrementCurrentRun();
            }
        }
    }

    private void commandToggleConsoleOutput(CommandOptions commandOptions){
        ui.consoleOutput.switchVisibility();
        ui.inputs.switchVisibility();
    }

    private void commandCmd(CommandOptions commandOptions){
        cmd.printCommandLineInfo(commandOptions.getBooleanValue("-details"));
    }

    private void commandClc(CommandOptions commandOptions){
        cmd.clearConsole();
    }
}
