package main;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class PanelGraph extends JPanel {
    private BufferedImage       graphImage;
    private Graphics2D          graphics2D;
    private int                 numberOfSegmentsOnX = 12;
    private int                 numberOfSegmentsOnY = 12;
    private int                 xLeft;
    private int                 xRight;
    private int                 yBottom;
    private int                 yTop;
    private Set<Integer>        xSegments = new TreeSet<>();

    PanelGraph() {
        computeSegmentsOnX(GlobalState.iterationsCount);
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;
        g2.drawImage(graphImage, 0, 0, this);
    }

    public void printGenerations(ArrayList<Double> bestCandidates, int generationsCount, int sleep){
        resetImageAndGraphics();
        paintGraphBorders(graphics2D, generationsCount);
        printGenerationsGraph(graphics2D, bestCandidates, generationsCount);
        printExecutionInformation();
        paintImmediately(0, 0, getWidth(), getHeight());
        try{
            Thread.sleep(sleep);
        } catch (Exception e) { }
    }

    public void printInformation(){
        printExecutionInformation();
        paintImmediately(0,0, getWidth(), getHeight());
    }

    public void paintBorders(){
        paintBorders(GlobalState.iterationsCount);
    }

    public void paintBorders(int generationsCount) {
        resetImageAndGraphics();
        computeSegmentsOnX(generationsCount);
        computeValuesOfXAndY();
        paintGraphBorders(graphics2D, generationsCount);
        printExecutionInformation();
        repaint();
    }

    private void resetImageAndGraphics(){
        graphImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        graphics2D = graphImage.createGraphics();

        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.setStroke(new BasicStroke((2)));

        graphics2D.setColor(new Color(240, 240, 240));
        graphics2D.drawRect(0, 0, getWidth(), getHeight());

        graphics2D.setColor(new Color(100, 100, 100));
    }

    private void printExecutionInformation() {
        computeValuesOfXAndY();

        graphics2D.setColor(new Color(240, 240, 240));
        graphics2D.fillRect(xRight+6, 0, getWidth() - xRight, getHeight()-40);

        graphics2D.setColor(new Color(100, 100, 100));
        graphics2D.setFont(new Font(Font.MONOSPACED, Font.BOLD, 22));
        graphics2D.drawString("Execution Info", xRight + 8, 40);

        graphics2D.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 16));

        graphics2D.drawString("Function:", xRight + 8, 100);
        graphics2D.drawString(GlobalState.functionUsed == null ? "Custom" : GlobalState.functionUsed.getName(), xRight + 8, 116);

        graphics2D.drawString("Optimization Algorithm:", xRight+8, 140);
        graphics2D.drawString(GlobalState.optimizationAlgorithmDescription.getShortName(), xRight+8, 156);

        graphics2D.drawString("Function arguments:", xRight+8, 180);
        graphics2D.drawString(
                GlobalState.functionUsed == null ? "?" : Integer.toString(GlobalState.functionUsed.getFunction().getArgumentsCount()),
                xRight+8, 196);

        graphics2D.drawString("Best value:", xRight+8, 240);
        graphics2D.drawString(roundDoubleTo2FloatingPoints(GlobalState.bestValue), xRight+132, 240);

        graphics2D.drawString("Worst value:", xRight+8, 268);
        graphics2D.drawString(roundDoubleTo2FloatingPoints(GlobalState.worstValue), xRight+132, 268);

        graphics2D.drawString("   Mean:", xRight+8, 296);
        graphics2D.drawString(roundDoubleTo2FloatingPoints(GlobalState.mean), xRight+132, 296);

        graphics2D.drawString("   Runs:", xRight+8, 324);
        graphics2D.drawString(GlobalState.currentRun + "/" + GlobalState.totalRuns, xRight+132, 324);
    }

    private void computeValuesOfXAndY() {
        int width = getWidth();
        int height = getHeight();
        xLeft = 48;
        xRight = width - 240;
        yBottom = height - 28;
        yTop = 20;
    }

    private void printGenerationsGraph(Graphics2D g2, ArrayList<Double> bestCandidates, int totalGenerationsCount){
        computeValuesOfXAndY();

        Double minValue = Collections.min(bestCandidates);
        Double maxValue = Collections.max(bestCandidates);

        Double maxDiff = (maxValue - minValue == 0 ? 2 : maxValue - minValue);
        double yDiff = yBottom - 20 - yTop;
        int minReq = (int)(yDiff / numberOfSegmentsOnY);
        int lastX = 0, lastY = 0;

        Set<Integer> ySegments = computeSegmentsOnY(bestCandidates, minReq, yBottom, yDiff, maxValue, maxDiff);

        for (int i=0;i<bestCandidates.size();++i){
            int x = xLeft + (int)((double)(xRight-4-xLeft)/(double)totalGenerationsCount * i) + 8;
            int y;
            if (GlobalState.optimizationType == 1){
                y = (int)(yTop + 10 + yDiff * (maxValue - bestCandidates.get(i)) / maxDiff);
            } else {
                y = (int)(yBottom - 10 - yDiff * (1 - (maxValue - bestCandidates.get(i)) / maxDiff));
            }
            if (i > 0){
                g2.drawLine(x, y, lastX, lastY);
            }
            g2.drawOval(x-2, y-2, 4, 4);
            lastX = x;
            lastY = y;

            if (ySegments.contains(i)){
                g2.drawLine(xLeft-1,y,xLeft+1,y);
                g2.drawString(roundDoubleTo2FloatingPoints(bestCandidates.get(i)), 6, y+4);
            }
        }
    }

    private Set<Integer> computeSegmentsOnY(ArrayList<Double> bestCandidates, int minReq, int yBottom, double yDiff, double maxValue, double maxDiff) {
        Set<Integer> ySegments = new TreeSet<>();

        ArrayList<CustomPair<Integer,Integer>> arrayYSegments = new ArrayList<>();
        for (int i=0;i<bestCandidates.size();++i){
            int y;
            if (GlobalState.optimizationType == 1){
                y = (int)(yTop + 10 + yDiff * (maxValue - bestCandidates.get(i)) / maxDiff);
            } else {
                y = (int)(yBottom - 10 - yDiff * (1 - (maxValue - bestCandidates.get(i)) / maxDiff));
            }
            arrayYSegments.add(new CustomPair(y, i));
        }
        Collections.sort(arrayYSegments, (o1, o2) -> {
            if (o1.getKey().equals(o2.getKey())){
                if (o1.getValue().equals(o2.getValue())){
                    return 0;
                }
                return o1.getValue() < o2.getValue() ? -1 : 1;
            }
            return o1.getKey() < o2.getKey() ? -1 : 1;
        });
        int lastDrawnY = arrayYSegments.get(0).getKey();
        ySegments.add(arrayYSegments.get(0).getValue());
        for (int i=1;i<arrayYSegments.size();++i){
            if (arrayYSegments.get(i).getKey() - lastDrawnY >= minReq){
                lastDrawnY = arrayYSegments.get(i).getKey();
                ySegments.add(arrayYSegments.get(i).getValue());
            }
        }
        return ySegments;
    }

    private void paintGraphBorders(Graphics2D g2, int generationsCount) {
        computeValuesOfXAndY();

        g2.drawLine(xLeft-4,yBottom,xRight,yBottom);
        g2.drawLine(xLeft,yTop,xLeft,yBottom+4);

        for (Integer cx : xSegments){
            int x = xLeft + (int)((double)(xRight-4-xLeft)/(double)generationsCount * cx) + 8;
            int cnt = cx + 1;
            g2.drawLine(x, yBottom-4, x, yBottom + 4);
            g2.drawString(Integer.toString(cnt),x-4,yBottom+20);
        }
    }

    private void computeSegmentsOnX(int generationsCount){
        if (generationsCount < numberOfSegmentsOnX) {
            return;
        }
        ArrayList<Integer> diff = new ArrayList<>();
        int segLen = generationsCount / numberOfSegmentsOnX;
        int numLongSeg = generationsCount % numberOfSegmentsOnX;
        for (int i=0;i<numLongSeg+1;++i){
            diff.add(segLen+1);
        }
        for (int i=0;i<generationsCount-numLongSeg-1;++i){
            diff.add(segLen);
        }
        Collections.shuffle(diff);
        int curr = 0;
        xSegments.clear();
        for (int i=0;i<numberOfSegmentsOnX;++i){
            xSegments.add(curr);
            curr += diff.get(i);
        }
        xSegments.add(generationsCount-1);
    }

    private static String roundDoubleTo2FloatingPoints(Double number){
        return Double.toString(new BigDecimal(number).setScale(2, RoundingMode.HALF_EVEN).doubleValue());
    }
}
