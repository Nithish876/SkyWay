import java.awt.*;

public class Obstacle {

    private double x;
    private double y;
    private final double speed = 300;

    public Obstacle(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void update(double delta) {
        x -= speed * delta;
        if (x < -400) {
            x = 800;
        }
    }

    public void render(Graphics2D g) {
        g.setColor(Color.RED);
        g.fillRect((int) x, (int) y, 40, 40);
    }

    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, 40, 40);
    }
}
