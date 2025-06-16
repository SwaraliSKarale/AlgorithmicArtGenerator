import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class PerlinPanel extends JPanel {
    private static final int WIDTH = 600;
    private static final int HEIGHT = 600;
    private static final int SCALE = 5;
    private double[][] noiseGrid;

    public PerlinPanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        regenerateNoise();
    }

    public void regenerateNoise() {
        noiseGrid = generateNoise(WIDTH / SCALE, HEIGHT / SCALE);
        repaint();
    }

    private double[][] generateNoise(int cols, int rows) {
        double[][] grid = new double[cols][rows];
        Random rand = new Random();

        for (int x = 0; x < cols; x++) {
            for (int y = 0; y < rows; y++) {
                grid[x][y] = rand.nextDouble();
            }
        }

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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int x = 0; x < noiseGrid.length; x++) {
            for (int y = 0; y < noiseGrid[0].length; y++) {
                g.setColor(getTerrainColor(noiseGrid[x][y]));
                g.fillRect(x * SCALE, y * SCALE, SCALE, SCALE);
            }
        }
    }

    private Color getTerrainColor(double value) {
        if (value < 0.3) return new Color(0, 0, 180);         // deep water
        else if (value < 0.4) return new Color(0, 100, 255);  // shallow water
        else if (value < 0.5) return new Color(240, 240, 100); // beach
        else if (value < 0.7) return new Color(50, 180, 50);   // grass
        else if (value < 0.85) return new Color(100, 100, 100); // mountain
        else return Color.WHITE; // snow
    }
}
