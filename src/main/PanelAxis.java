package main;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class PanelAxis extends JPanel {
    private BufferedImage       axisImage;
    private Graphics2D          graphics2D;

    PanelAxis() { }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;
        g2.drawImage(axisImage, 0, 0, this);
    }

    void paintBlankAxis(){
        resetImageAndGraphics();
        repaint();
    }

    private void resetImageAndGraphics(){
        axisImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        graphics2D = axisImage.createGraphics();

        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.setStroke(new BasicStroke((2)));

        graphics2D.setColor(new Color(100, 100, 100));

        paintCoordinateSystem(graphics2D);
    }


    public void drawGenerationRepresentation(ArrayList<AxisPoint> generationPoints) {
        //resetImageAndGraphics();
        paintPoints(generationPoints);
        repaint();
    }

    private void initGraphics2dObject(Graphics2D g2){
        g2.setStroke(new BasicStroke((2)));
    }

    private void paintPoints(ArrayList<AxisPoint> points){
        for (AxisPoint point:points){
            Ellipse2D e2d = new Ellipse2D.Double(point.x, point.y, 10, 10);
            graphics2D.fill(e2d);
        }
    }

    private void paintCoordinateSystem(Graphics2D g2){
        int width = axisImage.getWidth();
        int height = axisImage.getHeight();
        int midX = width/2;
        int midY = height/2;

        g2.drawLine(0, midY, width, midY);
        g2.drawLine(midX, 0, midX, height);
    }
}
