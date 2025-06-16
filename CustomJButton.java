import javax.swing.*;
import java.awt.*;

public class CustomJButton extends JButton {

    // Constructor sets all styles and gets the button text
    public CustomJButton(String text) {
        super(text);
        // Set background + foreground
        setForeground(Color.WHITE);
        setBackground(new Color(30, 144, 255));
        setFocusPainted(false);
        setBorderPainted(false);
        // Set font for the text
        setFont(new Font("SansSerif", Font.BOLD, 14));
        setOpaque(true);
        // Get hand cursor (when hovering over the button it shows a cursor)
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        // When hovering over the button it becomes darker
        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                setBackground(new Color(25, 120, 220));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                setBackground(new Color(30, 144, 255));
            }
        });
    }
}
