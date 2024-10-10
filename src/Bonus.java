public class Bonus {
    private int posx;
    private int posy;
    private int typeBonus;
    /**
     *  TypeBonus possibles :
     *            0 -> Bouclier
     *            1 -> Jumpstick
     *            2 -> Aimant
     *            3 -> Score*2
     *            4 -> Slow des obstacles
     */

    private double amplitude = 50; // amplitude de l'onde sinusoïdale
    private double frequency = 0.1; // fréquence de l'onde sinusoïdale
    private double phase = 0; // phase de l'onde sinusoïdale
    private double time = 0; // temps écoulé depuis le début de l'animation exclusif a la méthode move
    private int timeActivation = 0; // temps écoulé depuis le début de l'activation sur le personnage
    static int taille = 30;
    public Bonus() {
        posx = 1499;
        posy = 300;
        typeBonus = (int)(Math.random()*5);
        //typeBonus = 2;
    }

    /**public void move() {
        // Mettre à jour la position en fonction du temps
        time += 0.1;
        if(posy != 500 - Bonus.taille)
            posy = (int) (posy + amplitude * Math.sin(frequency * time + phase));
        else {
            amplitude *=-1;
            posy = (int) (posy + amplitude * Math.sin(frequency * time + phase));
        }
        posx = posx - 3;
    }**/
    public void move() {
        // Mettre à jour la position en fonction du temps
        if(posx<1500) {
            time += 0.05;
            double angle = frequency * time + phase;
            posy = (int) (350 + 150 * Math.sin(angle));
            posx = posx - (int) (5 * Math.cos(angle));
        }else posx++;
    }

    public int getTypeBonus(){
        return typeBonus;
    }
    public int getPosx() {
        return posx;
    }
    public int getTime(){return timeActivation;}
    public void timeUp(){timeActivation++;}
    public int getPosy() {
        return posy;
    }
}
