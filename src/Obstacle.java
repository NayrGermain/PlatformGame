import java.awt.*;
import java.util.Random;

public class Obstacle {
    private double posx;
    private int posy;
    static final int taille = 50;
    static final int width = 50;
    private Image image;


    private final int type; // 0 le bloc de base 1 le bloc qui vole 2 le tonnerre
    public Obstacle(){
        this(Math.max(1200,1200 + (int)(1000 - Terrain.score)),0);
    }
    public Obstacle(int t){
        this(1200 ,t);
    }
    public Obstacle(int t, Image i) {this(t);image = i;}
    public Obstacle(int n, int t){
        switch (t) {
            case 1 -> {
                posx = n + Math.random() * 550;
                posy = 280;
            }
            case 2 ->{
                posx = 100 + (Math.random() * 800);
                posy = 0;
                this.explode();
            }
            default -> {
                posx =  n + Math.random() * 450 ;
                posy = 500;
            }
        }type = t;
    }

    public int getPosx(){ return (int)posx;}
    public int getT(){return type;}
    public int getPosy() {
        return posy;
    }

    public void move(){
        move(true); //mouvement classique
    }
    public void move(boolean b){
        /**
         * Methode permettant le mouvement d'un obstacle ou les différents types d'obstacles sont
         * liés a des instructions différentes par exemple l'obstacle volant se déplace a une vitesse réduite par rapport au bloc de type 0
         * De plus on scale la vitesse en fonction du score du joueur.
         */
        if(b){ //mouvement classique
            switch(type) {
                case 0 ->    posx = posx - (7 + Math.min(Terrain.score / 1600, 8));
                case 1 -> posx = posx - (6 + Math.min((Terrain.score-1500)/1100,8) );
                case 2 -> explode();
                default -> posx--;
            }
        }
        else { // mouvement si et seulement si on a un bonus de type Score*2 qui est actif
            switch(type) {
                case 0 ->    posx = posx - (4 + Math.min(Terrain.score / 1600, 9));
                case 1 -> posx = posx - (3 + Math.min((Terrain.score-1500)/1100,8) );
                case 2 -> explodeSlowed();
                default -> posx--;
            }
        }
    }

    private void explode(){
        posy = posy + 20;
    }
    private void explodeSlowed(){
        posy = posy + 10;
    }

    public Image getImage() {
        return image;
    }
}
