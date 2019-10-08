package main;

import javax.swing.*;
import java.awt.*;

public class PanelConsoleOutput extends JPanel {
    private JTextArea   outputTextArea;

    public PanelConsoleOutput() {
        setLayout(new BorderLayout());

        outputTextArea = new JTextArea(2, 24);
        outputTextArea.setLineWrap(true);
        outputTextArea.setWrapStyleWord(true);
        outputTextArea.setBackground(Color.BLACK);
        outputTextArea.setForeground(Color.LIGHT_GRAY);
        outputTextArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 16));
        outputTextArea.setMargin(new Insets(4, 4, 4, 4));

        JScrollPane scrollPane = new JScrollPane( outputTextArea );
        add(scrollPane);
    }

    public void addLine(String line) {
        outputTextArea.append(line + '\n');
    }

    public void clearConsole() {
        outputTextArea.setText("");
    }

    public void setVisibility(boolean visible){
        setVisible(visible);
    }

    void switchVisibility(){
        setVisible(!isVisible());
    }
}
