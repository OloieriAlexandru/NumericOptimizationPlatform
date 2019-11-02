package main;

import functions.*;
import optimizationAlgorithms.OptimizationAlgorithmDescription;
import optimizationAlgorithms.OptimizationAlgorithmFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class PanelInputs extends JPanel {
    private UserInterface   ui;

    private JTextField      functionTextField = new JTextField();

    private JSpinner        functionMinValueSpinner;
    private JSpinner        functionMaxValueSpinner;
    private JComboBox<String> optimizationTypeComboBox;
    private JSpinner        functionArgumentsSpinner;

    private JPanel          functionsPanel = new JPanel();
    private JPanel          functionsPropertiesPanel = new JPanel();
    private JPanel          algorithmsPanel = new JPanel();

    private JLabel          sameIntervalsFunctionArgumentsCountLabel;

    private JLabel          differentIntervalsFunctionArgumentsChooseLabel;
    private JComboBox<String> differentIntervalsFunctionComboBox;

    private FunctionDescription       function;

    PanelInputs(UserInterface userInterface) {
        ui = userInterface;

        setBackground(Color.WHITE);
        setForeground(Color.LIGHT_GRAY);

        initInputs();
    }

    public double getFunctionMinValue(){
        return (double)functionMinValueSpinner.getValue();
    }

    public double getFunctionMaxValue(){
        return (double)functionMaxValueSpinner.getValue();
    }

    void initInputs() {
        initFunctionsInput();
        initFunctionsPropertiesInputs();
        initAlgorithmsInput();

        JSplitPane leftSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                functionsPanel, functionsPropertiesPanel);
        leftSplitPane.setDividerLocation(0.5);
        leftSplitPane.setResizeWeight(0.5);

        JSplitPane bothSplitPanes = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                leftSplitPane, algorithmsPanel);
        bothSplitPanes.setDividerLocation(0.7);
        bothSplitPanes.setResizeWeight(0.7);

        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weighty = constraints.weightx = 1;
        constraints.gridx = constraints.gridy = 0;
        add(bothSplitPanes, constraints);
    }

    void initFunctionsInput() {
        functionTextField.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 18));
        functionTextField.setColumns(16);
        functionTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent keyEvent) { }
            @Override
            public void keyPressed(KeyEvent keyEvent) { }
            @Override
            public void keyReleased(KeyEvent keyEvent) {
                updateFunction(functionTextField.getText());
            }
        });

        JList<Object> predefinedFunctionsList = new JList<>(FunctionFactory.getPredefinedFunctionsNames().toArray());
        predefinedFunctionsList.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 18));
        predefinedFunctionsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        predefinedFunctionsList.setLayoutOrientation(JList.VERTICAL);
        predefinedFunctionsList.setVisibleRowCount(1);

        predefinedFunctionsList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JList list = (JList)e.getSource();
                if (e.getClickCount() == 2){
                    functionTextField.setText((String)list.getSelectedValue());
                    updateFunction((String)list.getSelectedValue());
                }
            }
        });

        JScrollPane predefinedFunctionsScroller = new JScrollPane(predefinedFunctionsList);

        JSplitPane functionsSelectorSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, functionTextField, predefinedFunctionsScroller);
        functionsSelectorSplitPane.setDividerLocation(0.2);
        functionsSelectorSplitPane.setResizeWeight(0.2);

        functionsPanel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weighty = constraints.weightx = 1;
        constraints.gridx = constraints.gridy = 0;
        functionsPanel.add(functionsSelectorSplitPane, constraints);
    }

    void initFunctionsPropertiesInputs() {
        functionsPropertiesPanel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weighty = constraints.weightx = 1;
        constraints.gridx = constraints.gridy = 0;
        constraints.ipady = -4;

        JLabel iterationsLabel = new JLabel("Iterations:");
        iterationsLabel.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
        functionsPropertiesPanel.add(iterationsLabel, constraints);
        constraints.gridx = 1;
        SpinnerModel iterationsSpinnerModel = new SpinnerNumberModel(100, 1, 10000, 5);
        JSpinner iterationsSpinner = new JSpinner(iterationsSpinnerModel);
        iterationsSpinner.addChangeListener(e -> {
            GlobalState.iterationsCount = (int) iterationsSpinner.getValue();
            ui.graph.paintBorders();
        });
        iterationsSpinner.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
        functionsPropertiesPanel.add(iterationsSpinner, constraints);
        constraints.gridx = 0;
        constraints.gridy = 1;

        JLabel functionMinValue = new JLabel("Function min value:");
        functionMinValue.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
        functionsPropertiesPanel.add(functionMinValue, constraints);
        constraints.gridx = 1;
        SpinnerModel functionMinValueModel = new SpinnerNumberModel(-10.0, -100000,100000,0.02);
        functionMinValueSpinner = new JSpinner(functionMinValueModel);
        functionMinValueSpinner.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
        functionMinValueSpinner.addChangeListener(e -> {
            Function functionReference = GlobalState.functionUsed.getFunction();
            if (GlobalState.functionUsed.getArgumentsType() == FunctionArgumentsType.SameIntervals){
                functionReference.updateMinLimit(0, (double)functionMinValueSpinner.getValue());
            } else if (GlobalState.functionUsed.getArgumentsType() == FunctionArgumentsType.DifferentIntervals){
                functionReference.updateMinLimit(differentIntervalsFunctionComboBox.getSelectedIndex(), (double)functionMinValueSpinner.getValue());
            }
        });
        functionsPropertiesPanel.add(functionMinValueSpinner, constraints);
        constraints.gridx = 0;
        constraints.gridy = 2;

        JLabel functionMaxValue = new JLabel("Function max value:");
        functionMaxValue.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
        functionsPropertiesPanel.add(functionMaxValue, constraints);
        constraints.gridx = 1;
        SpinnerModel functionMaxValueModel = new SpinnerNumberModel(10.0, -100000,100000,0.02);
        functionMaxValueSpinner = new JSpinner(functionMaxValueModel);
        functionMaxValueSpinner.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
        functionMaxValueSpinner.addChangeListener(e -> {
            Function functionReference = GlobalState.functionUsed.getFunction();
            if (GlobalState.functionUsed.getArgumentsType() == FunctionArgumentsType.SameIntervals){
                functionReference.updateMaxLimit(0, (double)functionMaxValueSpinner.getValue());
            } else if (GlobalState.functionUsed.getArgumentsType() == FunctionArgumentsType.DifferentIntervals){
                functionReference.updateMaxLimit(differentIntervalsFunctionComboBox.getSelectedIndex(), (double)functionMaxValueSpinner.getValue());
            }
        });
        functionsPropertiesPanel.add(functionMaxValueSpinner, constraints);
        constraints.gridx = 0;
        constraints.gridy = 3;

        sameIntervalsFunctionArgumentsCountLabel = new JLabel("x=(x1,x2,...,xN) N:");
        sameIntervalsFunctionArgumentsCountLabel.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
        functionsPropertiesPanel.add(sameIntervalsFunctionArgumentsCountLabel, constraints);
        constraints.gridx = 1;
        SpinnerModel sameIntervalsFunctionArgumentsCountModel = new SpinnerNumberModel(5, 1, 100, 1);
        functionArgumentsSpinner = new JSpinner(sameIntervalsFunctionArgumentsCountModel);
        functionArgumentsSpinner.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
        functionsPropertiesPanel.add(functionArgumentsSpinner, constraints);
        functionArgumentsSpinner.addChangeListener(e -> {
            GlobalState.functionArguments = (int) functionArgumentsSpinner.getValue();
        });

        constraints.gridx = 0;
        differentIntervalsFunctionArgumentsChooseLabel = new JLabel("Argument:");
        differentIntervalsFunctionArgumentsChooseLabel.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
        differentIntervalsFunctionArgumentsChooseLabel.setVisible(false);
        functionsPropertiesPanel.add(differentIntervalsFunctionArgumentsChooseLabel, constraints);
        constraints.gridx = 1;
        differentIntervalsFunctionComboBox = new JComboBox<String>();
        differentIntervalsFunctionComboBox.setVisible(false);
        differentIntervalsFunctionComboBox.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        functionsPropertiesPanel.add(differentIntervalsFunctionComboBox, constraints);
        differentIntervalsFunctionComboBox.addActionListener(e -> {
            JComboBox box = (JComboBox)e.getSource();
            int index = box.getSelectedIndex();
            if (index == -1){
                return;
            }
            CustomPair<Double,Double> functionLimits = GlobalState.functionUsed.getFunction().getFunctionArgumentLimits(index);
            functionMinValueSpinner.setValue(functionLimits.getKey());
            functionMaxValueSpinner.setValue(functionLimits.getValue());
        });
    }

    void updateFunction(String functionStr) {
        function = FunctionFactory.getPredefinedFunctionDescription(functionStr);
        if (function == null) {
            ExpressionParsedFunction    expressionFunction = new ExpressionParsedFunction(functionStr);
            String[] argsNames = expressionFunction.getArgumentsNames();
            FunctionDescription         expressionFunctionDescription = new FunctionDescription("Custom", FunctionArgumentsType.DifferentIntervals, 1);
            expressionFunctionDescription.setFunction(expressionFunction);
            GlobalState.functionUsed = expressionFunctionDescription;
            optimizationTypeComboBox.setSelectedIndex(0);

            sameIntervalsFunctionArgumentsCountLabel.setVisible(false);
            functionArgumentsSpinner.setVisible(false);
            differentIntervalsFunctionArgumentsChooseLabel.setVisible(true);
            differentIntervalsFunctionComboBox.removeAllItems();
            for (int i=0;i<argsNames.length;++i){
                differentIntervalsFunctionComboBox.addItem(argsNames[i]);
            }
            if (argsNames.length > 0){
                differentIntervalsFunctionComboBox.setSelectedIndex(0);
            }
            differentIntervalsFunctionComboBox.setVisible(true);

        } else {
            GlobalState.functionUsed = function;
            if (function.getOptimizationType() == 1){
                optimizationTypeComboBox.setSelectedIndex(0);
            } else if (function.getOptimizationType() == 2){
                optimizationTypeComboBox.setSelectedIndex(1);
            }
            if (function.getArgumentsType() == FunctionArgumentsType.SameIntervals){
                differentIntervalsFunctionArgumentsChooseLabel.setVisible(false);
                differentIntervalsFunctionComboBox.setVisible(false);

                sameIntervalsFunctionArgumentsCountLabel.setVisible(true);
                functionArgumentsSpinner.setVisible(true);

                CustomPair<Double,Double> functionLimits = function.getFunctionLimits();
                functionMinValueSpinner.setValue(functionLimits.getKey());
                functionMaxValueSpinner.setValue(functionLimits.getValue());
            } else if (function.getArgumentsType() == FunctionArgumentsType.DifferentIntervals){
                sameIntervalsFunctionArgumentsCountLabel.setVisible(false);
                functionArgumentsSpinner.setVisible(false);
                differentIntervalsFunctionArgumentsChooseLabel.setVisible(true);
                String[] argsNames = function.getArgumentsNames();
                differentIntervalsFunctionComboBox.removeAllItems();
                for (int i=0;i<argsNames.length;++i){
                    differentIntervalsFunctionComboBox.addItem(argsNames[i]);
                }
                differentIntervalsFunctionComboBox.setSelectedIndex(0);
                differentIntervalsFunctionComboBox.setVisible(true);
                CustomPair<Double, Double> limits = function.getFunctionLimits();
                functionMinValueSpinner.setValue(limits.getKey());
                functionMaxValueSpinner.setValue(limits.getValue());
            }
        }
    }

    void initAlgorithmsInput() {
        JPanel optimizationTypePanel = new JPanel();
        optimizationTypePanel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = constraints.gridy = 0;
        constraints.weightx = 0.5;
        JLabel optimizationTypeLabel = new JLabel("Optimization type:");
        optimizationTypeLabel.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
        optimizationTypePanel.add(optimizationTypeLabel, constraints);
        constraints.gridx = 1;
        String[] optimizationTypes = { "Minimization", "Maximization" };
        optimizationTypeComboBox = new JComboBox<>(optimizationTypes);
        optimizationTypeComboBox.setSelectedIndex(0);
        optimizationTypeComboBox.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        optimizationTypeComboBox.addActionListener(e -> {
            JComboBox box = (JComboBox)e.getSource();
            if (box.getSelectedIndex() == 0){
                GlobalState.optimizationType = 1;
            } else {
                GlobalState.optimizationType = 2;
            }
        });
        optimizationTypePanel.add(optimizationTypeComboBox, constraints);

        ArrayList<String> optimizationAlgorithmsNames = OptimizationAlgorithmFactory.getOptimizationAlgorithmsNames();
        JList<Object> optimizationAlgorithmsList = new JList<>(optimizationAlgorithmsNames.toArray());
        optimizationAlgorithmsList.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 16));
        optimizationAlgorithmsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        optimizationAlgorithmsList.setLayoutOrientation(JList.VERTICAL);
        optimizationAlgorithmsList.setVisibleRowCount(1);
        optimizationAlgorithmsList.setSelectedIndex(0);
        GlobalState.setOptimizationAlgorithmDescription(OptimizationAlgorithmFactory.getOptimizationAlgorithmDescription(optimizationAlgorithmsNames.get(0)));

        JScrollPane optimizationAlgorithmsScroller = new JScrollPane(optimizationAlgorithmsList);
        optimizationAlgorithmsList.addListSelectionListener(e -> {
            ArrayList<String> optimizationAlgorithmsNames1 = OptimizationAlgorithmFactory.getOptimizationAlgorithmsNames();
            OptimizationAlgorithmDescription newDescription = OptimizationAlgorithmFactory.getOptimizationAlgorithmDescription(optimizationAlgorithmsNames1.get(optimizationAlgorithmsList.getSelectedIndex()));
            GlobalState.setOptimizationAlgorithmDescription(newDescription);
        });

        JSplitPane algorithmSelectorSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, optimizationTypePanel, optimizationAlgorithmsScroller);
        algorithmSelectorSplitPane.setDividerLocation(0.2);
        algorithmSelectorSplitPane.setResizeWeight(0.2);

        algorithmsPanel.setLayout(new GridBagLayout());
        constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weighty = constraints.weightx = 1;
        constraints.gridx = constraints.gridy = 0;
        algorithmsPanel.add(algorithmSelectorSplitPane, constraints);
    }

    String getFunctionDescription(){
        return functionTextField.getText();
    }

    void setVisibility(boolean visible){
        setVisible(visible);
    }

    void switchVisibility(){
        setVisible(!isVisible());
    }
}
