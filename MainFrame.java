import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public static final int WIDTH = 600;
    public static final int HEIGHT = 600;

    private PerlinPanel perlinPanel = null;

    public static void startGUI() throws InterruptedException {
        MainFrame theGUI = new MainFrame();
        SwingUtilities.invokeLater(() -> theGUI.createFrame(theGUI));
        
        synchronized (theGUI) {
            theGUI.wait();
        }
    }

    public void createFrame(Object semaphore) {
        this.setTitle("Perlin Noise Terrain Map");
        this.setSize(WIDTH, HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Use a layout manager instead of setBounds
        this.setLayout(new BorderLayout());

        perlinPanel = new PerlinPanel();
        this.add(perlinPanel, BorderLayout.CENTER);

        // Create the "Regenerate" button
        CustomJButton regenerateButton = new CustomJButton("Regenerate");
        regenerateButton.addActionListener(e -> perlinPanel.regenerateNoise());

        // Put the button in a panel to center it
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(regenerateButton);

        this.add(buttonPanel, BorderLayout.SOUTH);

        this.setVisible(true);

        // Notify when setup is done
        synchronized (semaphore) {
            semaphore.notify();
        }
    }
}
