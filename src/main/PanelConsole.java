package main;

import commandline.CommandLine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PanelConsole extends JPanel {
    private JTextArea           consoleTextArea;
    private CommandLine         cmd;

    PanelConsole(CommandLine commandLine) {
        cmd = commandLine;

        initializeConsole();
    }

    void init() {
        setVisible(true);
    }

    private void initializeConsole(){
        setLayout(new BorderLayout());

        consoleTextArea = new JTextArea(2, 16);
        consoleTextArea.setLineWrap(true);
        consoleTextArea.setWrapStyleWord(true);
        consoleTextArea.setBackground(Color.BLACK);
        consoleTextArea.setForeground(Color.LIGHT_GRAY);
        consoleTextArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 18));
        consoleTextArea.setMargin(new Insets(16, 16, 8, 4));

        consoleTextArea.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {
                if (keyEvent.getKeyChar() == '\n') {
                    onEnterPressed();
                }
            }
            @Override
            public void keyPressed(KeyEvent keyEvent) { }
            @Override
            public void keyReleased(KeyEvent keyEvent) { }
        });

        JScrollPane scrollPane = new JScrollPane( consoleTextArea );
        add(scrollPane);
    }

    private void onEnterPressed() {
        cmd.parseAndExecuteCommand(consoleTextArea.getText());
        consoleTextArea.setText("");
    }
}
