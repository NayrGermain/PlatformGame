import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observer;
import java.util.Random;


public class BackgroundElements {
    ArrayList<Image> listeImages = new ArrayList<>(); // Votre liste d'images


    // Variables pour le défilement des images
    int x = 1200; // Position horizontale de départ
    int y = 250; // Position verticale de départ

    Random random = new Random(); // Création d'une instance de Random
    public BackgroundElements(){
        try {
            listeImages.add(ImageIO.read( new File("images/arbre1.png") ));
            listeImages.add(ImageIO.read( new File("images/arbre2.png") ));
            listeImages.add(ImageIO.read( new File("images/buisson1.png") ));
        }catch(IOException exc){
            exc.printStackTrace();
        }
    }
    public void draw(Graphics g){
        int espace = random.nextInt(400) + 200; // Valeur aléatoire entre 10 et 29
        int i = 0;
        for (Image image : listeImages) {
            // Dessiner l'image à la position (x, y)
            g.drawImage(image, x + i * espace, 150, null);

            // Générer des valeurs aléatoires pour la vitesse de défilement et l'espacement
            int vitesse = (int)((1 + Math.min(Terrain.score / 1500, 2)));

            // Mettre à jour les coordonnées pour le prochain dessin en fonction des valeurs aléatoires
            x -= vitesse;
            // Si l'image est entièrement dessinée, réinitialiser la position
            if ( x<=-50 ) {
                x = 1500 + (int)(Math.random()*1400);
            }
            i++;
        }
    }
}