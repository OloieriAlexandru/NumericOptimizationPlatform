package main;

import commandline.CommandLine;

import javax.swing.*;
import java.awt.*;

public class UserInterface extends JFrame {
    PanelConsole                graphicConsole;
    public PanelAxis            axis;
    public PanelGraph           graph;
    public PanelInputs          inputs;
    public PanelConsoleOutput   consoleOutput;

    UserInterface(CommandLine commandLine){
        super("Numeric Optimization Platform - OLO");

        consoleOutput =     new PanelConsoleOutput();
        graphicConsole =    new PanelConsole(commandLine);
        axis =              new PanelAxis();
        graph =             new PanelGraph();
        inputs =            new PanelInputs(this);

        consoleOutput.setVisible(false);
        inputs.setVisibility(true);

        commandLine.setConsoleOutput(consoleOutput);

        init();
    }

    void init() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(1440,720));

        positionWindows();

        pack();
        graph.paintBorders(100);
    }

    private void positionWindows() {
        GridBagConstraints fullWindowConstraints = new GridBagConstraints();
        GridBagConstraints bottomConstraints = new GridBagConstraints();

        fullWindowConstraints.fill = bottomConstraints.fill = GridBagConstraints.BOTH;
        fullWindowConstraints.gridx = bottomConstraints.gridx = 0;
        fullWindowConstraints.gridy = bottomConstraints.gridy = 0;

        fullWindowConstraints.weightx = 1;
        fullWindowConstraints.weighty = 0.88;
        //getContentPane().add(axis,fullWindowConstraints);
        getContentPane().add(graph,fullWindowConstraints);
        graph.setVisible(true);

        JPanel bottomPart = new JPanel();
        bottomPart.setLayout(new GridBagLayout());
        bottomConstraints.weighty = 1;
        bottomConstraints.weightx = 0.4;
        bottomPart.add(graphicConsole,bottomConstraints);
        bottomConstraints.weightx = 0.6;
        bottomConstraints.gridx = 1;
        bottomPart.add(inputs,bottomConstraints);
        bottomPart.add(consoleOutput,bottomConstraints);

        fullWindowConstraints.gridy = 1;
        fullWindowConstraints.weighty = 0.12;

        getContentPane().add(bottomPart, fullWindowConstraints);
    }
}
