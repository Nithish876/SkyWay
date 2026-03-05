import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.*;

public class GamePanel extends JPanel implements Runnable {

    private Thread gameThread;
    private boolean running = false;

    private Player player = new Player();
    // private Obstacle obstacle = new Obstacle(800, 300);
    private ArrayList<Enemy> enemies = new ArrayList<>();
    private ArrayList<Bullet> bullets = new ArrayList<>();
    public int fps = 0;
    private double spawnTimer = 0;

    // 1. Added all layers and assigned progressive speeds for depth
    private ParallaxLayer skyLayer = new ParallaxLayer("/forest_sky.png", 0);
    private ParallaxLayer moonLayer = new ParallaxLayer("/forest_moon.png", 2);
    private ParallaxLayer mountainLayer = new ParallaxLayer(
        "/forest_mountain.png",
        10
    );
    private ParallaxLayer backLayer = new ParallaxLayer("/forest_back.png", 25);
    private ParallaxLayer midLayer = new ParallaxLayer("/forest_mid.png", 50);
    private ParallaxLayer longLayer = new ParallaxLayer("/forest_long.png", 75);
    private ParallaxLayer shortLayer = new ParallaxLayer(
        "/forest_short.png",
        100
    );

    public GamePanel() {
        setPreferredSize(new Dimension(800, 400));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(
            new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                        bullets.add(player.shoot());
                    }
                    if (e.getKeyCode() == KeyEvent.VK_W) {
                        player.setMovingUp(true);
                    }
                    if (e.getKeyCode() == KeyEvent.VK_S) {
                        player.setMovingDown(true);
                    }
                }

                @Override
                public void keyReleased(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_W) {
                        player.setMovingUp(false);
                    }

                    if (e.getKeyCode() == KeyEvent.VK_S) {
                        player.setMovingDown(false);
                    }
                }
            }
        );
    }

    public void startGame() {
        running = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        final int TARGET_FPS = 60;
        final double FRAME_TIME = 1_000_000_000.0 / TARGET_FPS;

        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        int frames = 0;

        while (running) {
            long now = System.nanoTime();
            long elapsed = now - lastTime;

            if (elapsed >= FRAME_TIME) {
                update(1.0 / TARGET_FPS);
                repaint();

                frames++;
                lastTime = now;
            }

            if (System.currentTimeMillis() - timer >= 1000) {
                fps = frames;
                frames = 0;
                timer += 1000;
            }

            Thread.yield();
        }
    }

    private void update(double delta) {
        spawnTimer += delta;
        if (spawnTimer > 2) {
            for (int i = 0; i < 5; i++) {
                enemies.add(
                    new Enemy(800 + i * 80, 50 + (int) (Math.random() * 250))
                );
            }

            spawnTimer = 0;
        }
        player.update(delta);
        // obstacle.update(delta);

        // 2. Update all layers
        skyLayer.update(delta, getWidth());
        moonLayer.update(delta, getWidth());
        mountainLayer.update(delta, getWidth());
        backLayer.update(delta, getWidth());
        midLayer.update(delta, getWidth());
        longLayer.update(delta, getWidth());
        shortLayer.update(delta, getWidth());

        for (int i = 0; i < bullets.size(); i++) {
            Bullet b = bullets.get(i);
            b.update(delta);
            if (b.isOffScreenWidth(getWidth())) {
                bullets.remove(i);
                i--;
                continue;
            }

            for (int j = 0; j < enemies.size(); j++) {
                Enemy e = enemies.get(j);

                if (b.getBounds().intersects(e.getBounds())) {
                    bullets.remove(i);
                    enemies.remove(j);

                    i--;
                    break;
                }
            }
        }
        for (int i = 0; i < enemies.size(); i++) {
            Enemy e = enemies.get(i);
            e.update(delta);
            if (e.isOffScreen()) {
                enemies.remove(i);
                i--;
            }
        }
        // if (player.getBounds().intersects(obstacle.getBounds())) {
        //     System.out.print("Game Over!");
        // }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // 3. Render strictly from BACK to FRONT
        skyLayer.render((Graphics2D) g, getWidth(), getHeight());
        moonLayer.render((Graphics2D) g, getWidth(), getHeight());
        mountainLayer.render((Graphics2D) g, getWidth(), getHeight());
        backLayer.render((Graphics2D) g, getWidth(), getHeight());
        midLayer.render((Graphics2D) g, getWidth(), getHeight());
        longLayer.render((Graphics2D) g, getWidth(), getHeight());
        shortLayer.render((Graphics2D) g, getWidth(), getHeight());
        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).render((Graphics2D) g);
        }
        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).render((Graphics2D) g);
        }

        player.render((Graphics2D) g);
        // obstacle.render((Graphics2D) g);

        g.setColor(Color.WHITE);
        g.drawString("FPS: " + fps, 10, 20);
    }
}
