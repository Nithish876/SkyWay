import java.awt.*;
import javax.swing.*;

public class ParallaxLayer {

    private Image image;
    private double x;
    private double speed;

    public ParallaxLayer(String path, double speed) {
        // Fallback or error handling can be useful here if path is wrong
        image = new ImageIcon(getClass().getResource(path)).getImage();
        this.speed = speed;
        this.x = 0;
    }

    public void update(double delta, int panelWidth) {
        x -= speed * delta;

        // Fix: Use += panelWidth instead of = 0 to prevent micro-stutters when looping
        if (x <= -panelWidth) {
            x += panelWidth;
        }
    }

    public void render(Graphics2D g, int panelWidth, int panelHeight) {
        g.drawImage(image, (int) x, 0, panelWidth, panelHeight, null);
        g.drawImage(
            image,
            (int) x + panelWidth,
            0,
            panelWidth,
            panelHeight,
            null
        );
    }
}
