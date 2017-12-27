import java.awt.*;
import java.util.*;
import javax.swing.*;

public class Quilt {

    public static final int WIND_SIZE = 600;
    public static float quiltScale;
    public static ArrayList<Layer> layers;
    
    public static void main(String[] args) {
        layers = new ArrayList<Layer>();

        Scanner scan = new Scanner(System.in);

        while (scan.hasNextLine()) {
            String input = scan.nextLine();
            String[] square;
            square = input.split(" ");

            layers.add(new Layer(Float.parseFloat(square[0]), new Color(
                Integer.parseInt(square[1]), Integer.parseInt(square[2]), 
                Integer.parseInt(square[3]))));
        }

        float width = 0;
        int depth = 0;
        for(Layer square : layers) {
            float scale = 1;
            for(int i = 0; i < depth + 1; i++) {
                scale *= layers.get(i).scale;
            }
            width += scale;
            depth++;
        }

        quiltScale = (float)(WIND_SIZE * 0.8) / width;

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame("Quilt");
                QPanel panel = new QPanel(quiltScale, layers, WIND_SIZE);
                panel.setPreferredSize(new Dimension(WIND_SIZE, WIND_SIZE));
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.getContentPane().add(panel);
                frame.pack();
                frame.setLocationByPlatform(true);
                frame.setVisible(true);
            }
        });
    }}

class QPanel extends JPanel {

    public float quiltScale;
    public ArrayList<Layer> layers;
    public ArrayList<Square> squares;
    public int windowSize; 

    public QPanel(float quiltScale, ArrayList<Layer> layers, int windowSize) {
        this.quiltScale = quiltScale;
        this.layers = layers;
        this.windowSize = windowSize;
        squares = new ArrayList<Square>();
    }

    @Override
    protected void paintComponent(Graphics g) {
        int width = (int)(layers.get(0).scale * quiltScale);
        DrawLayer(g, 0, windowSize/2, windowSize/2);
        Collections.sort(squares, new Comparator<Square>() {
            @Override
            public int compare(Square o1, Square o2) {
                return o1.layer - o2.layer;
            }
        });
        for(Square square : squares) {
            g.setColor(square.colour);
            g.fillRect(square.x, square.y, square.width, square.width);
        }
    }

    public void DrawLayer(Graphics g, int depth, int x, int y) {
        float scale = 1;
        for(int i = 0; i < depth + 1; i++) {
            scale *= layers.get(i).scale;
        }
        int width = (int)(scale * quiltScale);
        //g.setColor(layers.get(depth).colour);
        //g.fillRect(x - width/2, y - width/2, width, width);
        squares.add(new Square(layers.get(depth).colour, x - width/2, 
            y - width/2, width, depth));
        if(depth < layers.size() - 1) {
            DrawLayer(g, depth + 1, x - width/2, y - width/2);
            DrawLayer(g, depth + 1, x + width/2, y - width/2);
            DrawLayer(g, depth + 1, x - width/2, y + width/2);
            DrawLayer(g, depth + 1, x + width/2, y + width/2);
        }
    }
}

class Layer {
    float scale;
    Color colour;

    public Layer(float scale, Color colour) {
        this.scale = scale;
        this.colour = colour;
    }
}

class Square implements Comparable<Square>{
    Color colour;
    int x, y, width;
    Integer layer;

    @Override
    public int compareTo(Square other) {
        return this.layer.compareTo(other.layer);
    }

    public Square(Color colour, int x, int y, int width, int layer) {
        this.colour = colour;
        this.x = x;
        this.y = y;
        this.width = width;
        this.layer = layer;
    }
}