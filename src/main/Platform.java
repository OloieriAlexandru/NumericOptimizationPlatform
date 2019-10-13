package main;

import commandline.CommandLine;
import commandline.CommandOptions;
import commandline.Commands;
import functions.Function;
import optimizationAlgorithms.*;

import java.util.Arrays;
import java.util.Date;

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
        Date start = new Date();
        ui.graph.paintBorders();
        for (int i=0;i<runs;++i){
            double curr = optimizationAlgorithm.run(!commandOptions.getBooleanValue("-disable-graph"));
            GlobalState.addResultValue(curr);
            ui.graph.printInformation();
            if (i+1 < runs){
                GlobalState.incrementCurrentRun();
            }
        }
        Date end = new Date();
        double duration = (end.getTime()-start.getTime()) / 1000.0;
        ui.consoleOutput.addLine("Best value found: \nValue = " + GlobalState.roundDoubleValueToXDecimals(GlobalState.bestValue, 5) + "\nPoint: " + Arrays.toString(GlobalState.allBestValueArguments));
        ui.consoleOutput.addLine("Duration: " + Double.toString(GlobalState.roundDoubleValueToTwoDecimals(duration)) + "seconds \n");
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
