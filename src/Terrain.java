import java.util.ArrayList;
import java.util.Random;

public class Terrain {
    private Personnage joueur;
    private ArrayList<Obstacle> listeObstacle;
    private ArrayList<Piece> listePiece;
    private Bonus bonus = null;
    private boolean willRestart;
    static int bonusPiece = 0;
    static long score;
    public boolean collideWithGround =false;
    public Terrain(){
        listeObstacle = new ArrayList<>();
        listePiece = new ArrayList<>();
        joueur = new Personnage();
        pushObstacle();
        resetScore();
        bonusPiece = 0;
        willRestart = true;
    }
    public boolean finished(){
        if(!joueur.enVie()){
            new ReadAndWrite().writeMaxHighScore();
            return true;
        }return false;


    }
    public void resetScore(){score = 0;}
    public boolean willRestart(){return willRestart;}
    public void willRestartSoon(){willRestart = false;}
    public void tour(){

        if(popObstacle()){
            pushObstacle();
        }

        moveAll();
        //System.out.println(joueur.getPosx());
        joueur.fall();
        joueur.checkGround();

        joueur.checkWall();
        checkCollide();
        pushThunder();
        pushAndPopBonus();
        joueur.update();
        if(joueur.getBonus()!=null)
            if(joueur.getBonus().getTypeBonus() ==3)
                score += 2;
        score++;
        generatePattern();

    }
    public void checkCollide(){
        double x,y;
        for(int i = 0; i < 360 ; i++){
            double theta = i * Math.PI / 180.0; // conversion degrés à radians
            x = getPosXJoueur() + Personnage.rayon * Math.cos(theta);
            y = getPosYJoueur() + Personnage.rayon * Math.sin(theta);
            if(obstacleVisible()) {
                Obstacle collide = checkCollideBis(x,y);
                if (collide!=null) {
                    joueur.estTouche();
                    listeObstacle.remove(collide);
                    if(collide.getT()==0)
                        pushObstacle();
                }
            }
            if(checkCollidePiece(x,y))
                bonusPiece++;
            if(bonus!=null)
                checkCollideBonus(x,y);
        }
    }
    public Obstacle checkCollideBis(double cx, double cy){
        for(Obstacle obs : getVisibleObstacle()){
            switch (obs.getT()) {
                default -> {
                    if (cx <= obs.getPosx() + Obstacle.width && cx >= obs.getPosx())
                        if (cy <= obs.getPosy() && cy >= obs.getPosy() - Obstacle.taille)
                            return obs;
                }
                case 2 -> {

                    if ((cx > obs.getPosx()) && (cx < obs.getPosx()+1 ) && (obs.getPosy()>=3000)) {
                        //System.out.println(cx  + " *** " + obs.getPosx() + " " + (obs.getPosx() + Obstacle.width/2));
                        return obs;
                    }
                }
            }
        }return null;
    }
    public boolean checkCollidePiece(double cx,double cy){
        if(joueur.getBonus()==null || joueur.getBonus().getTypeBonus()!=2)
        for(Piece p : listePiece){
            if(cx >=p.getPosx() && cx<=p.getPosx()+Piece.taille+1)
                if(cy >=p.getPosy()+1 && cy<=p.getPosy()+Piece.taille+1 ){
                    listePiece.remove(p);
                    bonusPiece++;
                    return true;
            }
        }
        else if(bonus == null){
            int distanceMax = 125; // Distance maximale de collision en pixels
            for (Piece p : listePiece) {
                int pieceMinX = p.getPosx();
                int pieceMaxX = p.getPosx() + Piece.taille + 1;
                int pieceMinY = p.getPosy() + 1;
                int pieceMaxY = p.getPosy() + Piece.taille + 1;
                if (cx >= pieceMinX - distanceMax && cx <= pieceMaxX + distanceMax) {
                    if (cy >= pieceMinY - distanceMax && cy <= pieceMaxY + distanceMax) {
                        listePiece.remove(p);
                        bonusPiece++;
                        return true;
                    }
                }
            }

        }
        return false;
    }
    public void checkCollideBonus(double cx,double cy){
        if(cx >=bonus.getPosx() && cx<=bonus.getPosx()+Bonus.taille)
            if(cy >=bonus.getPosy()+1 && cy<=bonus.getPosy()+Bonus.taille ){
                joueur.buffed(bonus);
                bonus = null;
                return;
            }

    return;
    }
    public Personnage getJoueur(){return joueur;}
    public boolean popObstacle(){
        for(Obstacle o : listeObstacle) {
            if(o.getT() == 2 && o.getPosy() >= 7000){
                listeObstacle.remove(o);
                continue;
            }

            if (o.getPosx() < -50) {
                switch (o.getT()) {
                    case 1 -> {
                        listeObstacle.remove(o);
                        continue;
                    }
                    default -> {
                        score += 20;
                        listeObstacle.remove(o);

                    }

                }
                return true;

            }

        }return false;
    }
    public void pushObstacle(){
        if((  Terrain.score > 1500 && birdsNotExisting()))
            listeObstacle.add(new Obstacle(1));
        listeObstacle.add(new Obstacle());

    }
    public void pushThunder(){
        if((Terrain.score + 1) > 3000 && (Terrain.score % 1000 <= 2) )
            listeObstacle.add(new Obstacle(2));//*(300-(score)/5)+1
    }
    public void pushAndPopBonus(){
        int n =((int)((Math.random()+1) * 1000));
        //System.out.print(n +" - ");
        if(  bonus==null && (joueur.getBonus() == null || joueur.getBonus().getTime()>3000)){  //1115%n==1 &&
            bonus = new Bonus();
            System.out.println(bonus.getTypeBonus());
        } else if (bonus != null) {
            if(bonus.getPosx() >1500 )
                bonus = null;
        }
    }
    public boolean birdsNotExisting(){
        for(Obstacle o : listeObstacle)
            if(o.getT() == 1)
                return false;
        return true;
    }
    public void moveAll(){
        if(joueur.getBonus()!=null && joueur.getBonus().getTypeBonus()==4){
            for(Obstacle o : listeObstacle){
                o.move(false);
            }
        }
        else{
            for(Obstacle o : listeObstacle){
                o.move();
            }
        }

        if(!listePiece.isEmpty()){
            for(Piece p : listePiece)
                p.move();
        }if(bonus != null)
            bonus.move();
    }
    public boolean obstacleVisible(){
        for(Obstacle obs : listeObstacle) {
            if (-50 < obs.getPosx() && 1200 > obs.getPosx())
                return true;
        }
        return false;
    }

    public ArrayList<Obstacle> getVisibleObstacle(){
        ArrayList<Obstacle> visible = new ArrayList<>();
        for(Obstacle obs : listeObstacle) {
            if (-50 < obs.getPosx() && 1200 > obs.getPosx())
                visible.add(obs);
        }return visible;
    }
    public ArrayList<Piece> getVisiblePiece(){
        ArrayList<Piece> visible = new ArrayList<>();
        if(listePiece.isEmpty())
            return visible;
        for(Piece p : listePiece) {
            if (-50 < p.getPosx() && 1200 > p.getPosx())
                visible.add(p);
        }return visible;
    }
    public int getPosYJoueur(){
        return joueur.getPosy();
    }
    public int getPosXJoueur(){
        return joueur.getPosx();
    }
    public Bonus getBonus(){return bonus;}
    public void generatePattern (){
        int pattern = (int)(Math.random() * 6);
        if(Terrain.score % 300 == 0 && listePiece.isEmpty()){
            switch (pattern){
                default -> {fullPieces(6,80,0,70);}
                case 1 ->{ fullPieces(3,Piece.taille+30,30,0);fullPieces(3,Piece.taille+30,-30,90); } //cas 3 et -3
                case 2 ->{fullPieces(6,Piece.taille + 30,0,200);} // barre horizontal volante
                case 3 ->{fullPieces(6,Piece.taille + 30,15,0);} // barre oblique partant du sol
                case 4 ->{fullPieces(6,Piece.taille + 30,0,0);} // barre au sol droite
                case 5 ->{fullPieces(5,0,Piece.taille +30,0);} // barre verticale
            }
        }
        else if(!listePiece.isEmpty()){
                for(Piece p : listePiece){
                    if(p.getPosx()<=-50)
                        listePiece.remove(p);
                }

        }return;
    }
    private void fullPieces(int n, int dx, int dy, int by){
        for(int i = 0; i < n ; i++){
            listePiece.add(new Piece(i*dx,-1*(by + i*dy)));
        }return;
    }
}
