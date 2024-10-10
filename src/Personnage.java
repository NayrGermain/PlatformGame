public class Personnage {
    private int posx;
    private int posy;
    private int ay = 0;
    private int ax = 0;
    private int vy = 0;
    private int vx = 0;
    static final int rayon = 50;
    private int vitesseMax = 15;
    private boolean vie = true;
    private Bonus bonus = null;
    private boolean jumped = false;
    public Personnage(){
        posx = 100;
        posy = 480;
    }

    public int getPosx() {
        return posx;
    }

    public Bonus getBonus() {
        return bonus;
    }

    public int getPosy() {
        return posy;
    }
    public boolean enVie(){return vie;}
    public void estTouche(){
     if(bonus == null || (bonus != null && bonus.getTypeBonus() != 0))
        vie = false;
     else bonus = null;
    }
    public void applyForce (int fy) {
        ay += fy;

    }
    public void update(){
        if(bonus != null && bonus.getTypeBonus()==1) { //gravité modifié si le bonus jumpstick est effectif
            ay = ay;
            vy += ay;
        }
        else {
            vy += ay;
        }
        posy += vy;
        ay *= 0;

        updateBonus();
        updatex();
    }
    public void jump(){
        if(bonus!= null && bonus.getTypeBonus()==1)
        applyForce(-30);
        else applyForce(-21);
        jumped = true;
    }
    public void fall(){
        if(posy >= 480) {
            posy = 480;
        }
        else applyForce(1);
    }
    public void applyForcex (int fx) {
        if (Math.abs(vx) < vitesseMax) {
            if(fx<0 && ax > 0 || fx>0 && ax < 0) {
                vx *= 0;
            }
            ax += fx;
        }
    }
    public void applyFriction(double friction) {
        if (vx > 0) {
            vx -= friction;
            if (vx < 0) {
                vx = 0;
            }
        } else if (vx < 0) {
            vx += friction;
            if (vx > 0) {
                vx = 0;
            }
        }
    }

    public int getVelocity() {
        return vx;
    }
    public void updatex(){
        vx += ax;
        posx += vx;
        ax *= 0;
    }
    public void updateBonus(){
        /**
         * Vérification du bonus :
         *   S'il existe et que son temps d'existence est supérieur a 750 -> on le détruit
         *   Sinon s'il existe on incrémente son temps d'existence
         */
        if(bonus!=null && bonus.getTime()>750)
            bonus = null;
        else if(bonus!= null) {
            bonus.timeUp();
        }
    }

    public void moveForward(){applyForcex(10);}
    public void moveBackward(){applyForcex(-10);}

    public boolean checkGround(){
        if (posy >= 480) {
            vy *= -0;
            posy = 480;
            return true;
        }return false;
    }
    public void checkWall(){
        if (posx >= 1200) {
            posx = 1200;
            vx *=-0.5;
        }else if( posx<=0) {
            posx = 0;
            vx *=-0.5;
        }
    }
    public void buffed(Bonus bonus1){
        bonus = bonus1;
    }
    public boolean getJumped(){return jumped;}
    public void noMoreJumping(){jumped = false;}
}
