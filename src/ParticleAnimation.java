import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class ParticleAnimation {
    private ArrayList<Particle> particles;
    private Random random;

    public ParticleAnimation() {
        particles = new ArrayList<>();
        random = new Random();
    }

    public void updateAndDraw(Graphics g) {
        // Mise à jour et dessin de toutes les particules existantes
        for (int i = particles.size() - 1; i >= 0; i--) {
            Particle p = particles.get(i);
            p.update();
            p.draw(g);

            // Suppression si hors écran
            if (p.getX() < 0 || p.getX() > g.getClipBounds().width ||
                    p.getY() < 0 || p.getY() > g.getClipBounds().height) {
                particles.remove(i);
            }
        }
    }

    public void triggerJumpAnimation(int characterX, int characterY) {
        // Création de nouvelles particules
        int particleCount = 20;
        for (int i = 0; i < particleCount; i++) {
            int size = random.nextInt(6) + 5;
            int xPos = characterX + random.nextInt(30) - 15;
            int yPos = characterY + random.nextInt(30) - 15;
            int xSpeed = random.nextInt(5) - 2;
            int ySpeed = random.nextInt(5) - 2;
            Color color = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));

            particles.add(new Particle(xPos, yPos, size, xSpeed, ySpeed, color));
        }
    }

    public class Particle {
        private int x;
        private int y;
        private int size;
        private int xSpeed;
        private int ySpeed;
        private Color color;

        public Particle(int x, int y, int size, int xSpeed, int ySpeed, Color color) {
            this.x = x;
            this.y = y;
            this.size = size;
            this.xSpeed = xSpeed;
            this.ySpeed = ySpeed;
            this.color = color;
        }

        public void update() {
            x += xSpeed;
            y += ySpeed;
        }

        public void draw(Graphics g) {
            g.setColor(color);
            g.fillRect(x, y, size, size);
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }
}

