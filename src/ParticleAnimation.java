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

    public void jumpAnimation(Graphics g, int characterX, int characterY) {
        // Nombre de particules à créer
        int particleCount = 20;

        // Création des particules
        for (int i = 0; i < particleCount; i++) {
            int size = random.nextInt(6) + 5; // Taille aléatoire entre 5 et 10 pixels
            int xPos = characterX + random.nextInt(30) - 15; // Position aléatoire dans une plage de 30 pixels autour du personnage en X
            int yPos = characterY + random.nextInt(30) - 15; // Position aléatoire dans une plage de 30 pixels autour du personnage en Y
            int xSpeed = random.nextInt(5) - 2; // Vitesse horizontale aléatoire entre -2 et 2 pixels par frame
            int ySpeed = random.nextInt(5) - 2; // Vitesse verticale aléatoire entre -2 et 2 pixels par frame
            Color color = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256)); // Couleur aléatoire

            particles.add(new Particle(xPos, yPos, size, xSpeed, ySpeed, color));
        }

        // Dessin des particules
        for (Particle particle : particles) {
            particle.update();
            particle.draw(g);
        }

        // Suppression des particules hors de l'écran
        particles.removeIf(particle -> particle.getX() < 0 || particle.getX() > g.getClipBounds().width ||
                particle.getY() < 0 || particle.getY() > g.getClipBounds().height);
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

