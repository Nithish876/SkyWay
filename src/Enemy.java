import java.awt.*;
import javax.swing.*;

public class Enemy {

    private Image image;
    private double speed = 300;
    private double x;
    private double y;

    public Enemy(double x, double y) {
        this.x = x;
        this.y = y;
        image = new ImageIcon(
            getClass().getResource("/enemy_ship.png")
        ).getImage();
    }

    public void update(double delta) {
        x -= speed * delta;
    }

    public void render(Graphics2D g) {
        g.drawImage(image, (int) x, (int) y, 80, 80, null);
    }

    public boolean isOffScreen() {
        return x < -100;
    }

    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, 60, 60);
    }
}
