import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class PerlinPanel extends JPanel {
    private static final int WIDTH = 600;
    private static final int HEIGHT = 600;
    private static final int SCALE = 5;
    private double[][] finalGrid;

    public PerlinPanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        regenerateNoise();
    }

    public void regenerateNoise() {
        finalGrid = generateNoise(WIDTH / SCALE, HEIGHT / SCALE);
        repaint();
    }

    // Randomize double values for the noise
    private double[][] generateNoise(int cols, int rows) {
        double[][] grid = new double[cols][rows];
        Random rand = new Random();

        // for every single grid value, it generates a double value
        for (int x = 0; x < cols; x++) {
            for (int y = 0; y < rows; y++) {
                grid[x][y] = rand.nextDouble();
            }
        }

        // Without this bottom chunk of code the perlin terrain looks
        // jagged and doesn't look smooth/realistic
        double[][] smooth = new double[cols][rows];
        for (int x = 1; x < cols - 1; x++) {
            for (int y = 1; y < rows - 1; y++) {
                double sum = 0;
                for (int dx = -1; dx <= 1; dx++) {
                    for (int dy = -1; dy <= 1; dy++) {
                        sum += grid[x + dx][y + dy];
                    }
                }
                smooth[x][y] = sum / 9.0;
            }
        }

        return smooth;
    }

    // Paints every single 'box', the location is found out by the scale
    // multiplied by the index of the box and the size is just the scale
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int x = 0; x < finalGrid.length; x++) {
            for (int y = 0; y < finalGrid[0].length; y++) {
                g.setColor(getTerrainColor(finalGrid[x][y]));
                g.fillRect(x * SCALE, y * SCALE, SCALE, SCALE);
            }
        }
    }

    // Randomizes the color for each box
    private Color getTerrainColor(double num) {
        if (num < 0.3) return new Color(0, 0, 180);         // deep water
        else if (num < 0.4) return new Color(0, 100, 255);  // shallow water
        else if (num < 0.5) return new Color(240, 240, 100); // beach
        else if (num < 0.7) return new Color(50, 180, 50);   // grass
        else if (num < 0.85) return new Color(100, 100, 100); // mountain
        else return Color.WHITE; // snow
    }
}
