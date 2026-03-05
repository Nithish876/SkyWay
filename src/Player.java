import java.awt.*;
import javax.swing.*;

public class Player {

    private Image image;
    private double x = 100;
    private double y = 300;
    private double velocityY = 0;

    private final double gravity = 900;
    private final double ground = 300;
    private boolean movingUp = false;
    private boolean movingDown = false;
    private double speed = 300;
    private Sound jumpSound = new Sound("/jump.wav");

    public Player() {
        image = new ImageIcon(
            getClass().getResource("/player_ship.png")
        ).getImage();
    }

    public void update(double delta) {
        // velocityY += gravity * delta;
        // y += velocityY * delta;
        if (movingUp) {
            y -= speed * delta;
        }

        if (movingDown) {
            y += speed * delta;
        }

        // if (y >= ground) {
        //     y = ground;
        //     velocityY = 0;
        // }
        if (y < 0) y = 0;
        if (y > 300) y = 300;
    }

    public void render(Graphics2D g) {
        g.drawImage(image, (int) x, (int) y, 100, 50, null);
    }

    public void setMovingUp(boolean state) {
        movingUp = state;
    }

    public void setMovingDown(boolean state) {
        movingDown = state;
    }

    public Bullet shoot() {
        return new Bullet(x + 90, y + 25);
    }

    public void jump() {
        if (y >= ground) {
            velocityY = -500;
            jumpSound.play();
        }
    }

    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, 40, 40);
    }
}
