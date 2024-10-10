import java.util.ArrayList;

public class Piece {
    private int posx,posy;
    static final int taille = 30;
    public Piece(int x, int y){
        posx =1200 + x; posy =500+ y;

    }

    public int getPosx() {
        return posx;
    }

    public int getPosy() {
        return posy;
    }

    public void move(){
        posx= posx - 6;
    }
}
