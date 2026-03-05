import java.awt.*;

public class Bullet {

    private double x;
    private double y;
    private double speed = 600;

    public Bullet(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void update(double delta) {
        x += speed * delta;
    }

    public boolean isOffScreenWidth(int width) {
        return x > width;
    }

    public void render(Graphics2D g) {
        g.setColor(Color.YELLOW);
        g.fillRect((int) x, (int) y, 12, 4);
    }

    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, 12, 4);
    }
}
