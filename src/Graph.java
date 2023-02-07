import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.io.FileNotFoundException;
import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Graph extends JPanel {
    private final int PREF_W = 600;
    private final int PREF_H = 600;
    private final int CENTER_W = 290;
    private final int CENTER_H = 290;
    private static final int BORDER_GAP = 20;
    private static final int GRAPH_POINT_WIDTH = 6;
    private ArrayList<Double> values;



    public Graph(ArrayList<Double> values){
        this.values = values;
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        AffineTransform oldTransform = g2.getTransform(); // New transform for scaling *
        g2.scale(1, 1); // Scale of graph *
        ArrayList<Ellipse2D> shapes = new ArrayList<Ellipse2D>();
        Line2D.Double line2D = new Line2D.Double();
        ArrayList<Point2D.Double> graphPoints = new ArrayList<Point2D.Double>();

        // For Loop - Populating graphPoints with this.values
        for(int i = 0; i<values.size() -1;i+=2){
            graphPoints.add(new Point2D.Double(values.get(i), values.get(i+1)));
        }
        // For Loop - Filling shapes with Ellipse2D objects to be drawn by graphics
        for(int i = 0; i<graphPoints.size();i++){
            shapes.add(new Ellipse2D.Double((CENTER_W+graphPoints.get(i).x) - (GRAPH_POINT_WIDTH/2), (CENTER_H-graphPoints.get(i).y) - (GRAPH_POINT_WIDTH/2), GRAPH_POINT_WIDTH, GRAPH_POINT_WIDTH));
        }
        // Draw Points
        for(int i=0; i<shapes.size();i++){
            g2.fill(shapes.get(i));
        }
        // Draw lines between points
        for(int i=0; i<shapes.size()-1;i++){
            line2D = new Line2D.Double(shapes.get(i).getX()+(GRAPH_POINT_WIDTH/2),shapes.get(i).getY()+(GRAPH_POINT_WIDTH/2),shapes.get(i+1).getX()+(GRAPH_POINT_WIDTH/2),shapes.get(i+1).getY()+(GRAPH_POINT_WIDTH/2));
            g2.draw(line2D);
        }

        g2.drawLine(CENTER_W, getHeight() - BORDER_GAP, CENTER_W, BORDER_GAP);
        g2.drawLine(BORDER_GAP, CENTER_H, getWidth() - BORDER_GAP, CENTER_H);

        g2.setTransform(oldTransform ); // Reset Transform

    }

    @Override
    public Dimension getPreferredSize(){
        return new Dimension(PREF_W, PREF_H);
    }


    // Method to get Min value of X points
    private double getMinX(){
        ArrayList<Double> xValues = new ArrayList<Double>();
        for(int i = 0; i<values.size();i++) {
            if (i % 2 != 0)
                xValues.add(values.get(i));
        }
        return Collections.min(xValues);
    }
    // Method to get Min Value of X points
    private double getMinY(){
        ArrayList<Double> yValues = new ArrayList<Double>();
        for(int i = 0; i<values.size();i++) {
            if (i % 2 != 0)
                yValues.add(values.get(i));
        }
        return Collections.min(yValues);
    }
    // Method to get Max Value of X
    private double getMaxX(){
        ArrayList<Double> xValues = new ArrayList<Double>();
        for(int i = 0; i<values.size();i++) {
            if (i % 2 != 0)
                xValues.add(values.get(i));
        }
        return Collections.max(xValues);
    }
    // Method to get Max Value of Y
    private double getMaxY(){
        ArrayList<Double> yValues = new ArrayList<Double>();
        for(int i = 0; i<values.size();i++) {
            if (i % 2 != 0)
                yValues.add(values.get(i));
        }
        return Collections.max(yValues);
    }

    // Create and Show Graphics Window
    private static void createAndShow() throws IOException{

        ArrayList<Double> values = new ArrayList<>();
        ArrayList<Double> xValues = new ArrayList<>();
        ArrayList<Double> yValues = new ArrayList<>();

        Scanner in = new Scanner(System.in); // Input Scanner
        System.out.println("Input File Path");
        String path = in.nextLine();
        File file = new File(path);
        Scanner sc = new Scanner(file); // File Scanner
        sc.nextLine();
        String currentLine;
        String splitLine[];

        // Split file into Floating-Point values
        while (sc.hasNextLine()) {
            currentLine = sc.nextLine();
            splitLine = currentLine.split(",");
            values.add(Double.parseDouble(splitLine[0].trim()));
            values.add(Double.parseDouble(splitLine[1].trim()));
        }
        // Split values into yValues List
        for(int i = 0; i<values.size();i++) {
            if (i % 2 != 0)
                yValues.add(values.get(i));
        }
        // Split values into xValues List
        for(int i = 0; i<values.size();i++) {
            if (i % 2 == 0)
                xValues.add(values.get(i));
        }


        sc.close();

        Graph mainPanel = new Graph(values);
        JFrame frame = new JFrame("Graph");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(mainPanel);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(900,900);
    }





    public static void main(String[]args) throws IOException{



        createAndShow();
    }
}
