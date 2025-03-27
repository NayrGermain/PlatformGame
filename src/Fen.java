import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Fen extends JPanel implements KeyListener {
    private Terrain t;
    private JFrame frame;
    private JLabel scoreLabel;
    private SystemColorPersonalized scp;
    private BackgroundElements backgroundElements;
    private ParticleAnimation particleAnimation;
    Image bg;
    Image spike;
    Image skull;
    Image explode;
    Image slime;
    Image ghost;
    ArrayList<Image> ennemies;
    private int height = 600, width = 1200;
    private boolean mainMenu = true;
    private boolean market = false;
    public Fen(Terrain t) {

        this.t = t;

        setBackground(Color.white);
        setPreferredSize(new Dimension(width,height));

        JFrame frame = new JFrame("Jeu");
        this.frame = frame;
        JLabel label = new JLabel(
                "Score : " + Long.toString(Terrain.score) +
                " Nombre de Piece : " + Terrain.bonusPiece);
        this.scoreLabel = label;
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ennemies = new ArrayList<>();
        try {
            bg = ImageIO.read(new File("images/backgroundCool.png"));
            spike = ImageIO.read(new File("images/spike.png"));
            skull = ImageIO.read(new File("images/skull.png"));

        }catch(IOException exc){exc.printStackTrace();}

        /**JButton btnJouer = new JButton("Jouer");

        btnJouer.setBounds(width/5,height -400 , 200, 100);
        btnJouer.setBackground(new Color(255,250, 250));
        btnJouer.addActionListener(e -> {
            mainMenu = false;
        });
        this.add(btnJouer);
        **/
        scp = new SystemColorPersonalized();
        backgroundElements = new BackgroundElements();
        particleAnimation = new ParticleAnimation();
        frame.getContentPane().add(this);
        frame.pack();
        frame.setVisible(true);
        this.add(scoreLabel);
        frame.addKeyListener(this);
        frame.setFocusable(true);
        frame.requestFocusInWindow();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(mainMenu){
            g.setColor(new Color(0,0,0));
            g.fillRect(0,0,width,height);
            g.setFont(new Font("Arial",Font.PLAIN,15));
            g.setColor(new Color(255,255,255));
            g.drawString("Appuyez sur Entrée pour jouer ou sur B pour se rendre à la boutique.",width/3,height/2);
            if(market){
                g.setColor(new Color(2,5,5));
                g.fillRect(0,0,width,height);

                g.setColor(new Color(20,100,240));
                g.fillRoundRect(45,height/10-25,340,50,20,15);
                g.setColor(new Color(255,255,255));
                g.drawString("Bienvenue dans la boutique ! Choisissez les articles que vous désirez.",width/3,height/4);
                g.setFont(new Font("Verdana", 2, 15));
                g.drawString("Selectionnez le skin désiré avec les flèches directionnels.",width/3,height/4+20);
                g.setFont(new Font("Arial",Font.PLAIN,15));
                String[] recup = new ReadAndWrite().getAllLines();
                String texteAffichage = "Nombre de pièce total acquises:" + recup[1].split(":")[1];
                g.drawString(texteAffichage, 50, height/10);
                g.setColor(Color.YELLOW);
                g.fillOval(340,height/10-20, Piece.taille, Piece.taille);
                SystemColorPersonalized.draw(g,scp.visibleColors());

                g.setColor(scp.getLastColorEntered());
                g.drawString("Couleur selectionnée ici",width/2 + 200,height/2);


            }
        }
        else {
            if (t.getJoueur().enVie()) {
                g.drawImage(bg, 0, 0, this);
                //g.setColor(new Color(0, 0, 60));
                //g.fillRect(0, 0, width, height);
                g.setColor(new Color(0, 10, 0));
                g.fillRect(0, 530, width, height);
                if (t.getJoueur().getBonus() != null) {
                    g.setColor(new Color(255,255,255));
                    g.drawString("Bonus actuelle : ",37,40);
                    g.drawString(t.getJoueur().getBonus().getTime()*100/750+"%",50,110);
                    g.drawRect(50,50,102,50);
                    g.setColor(new Color(10,125,30));
                    g.fillRect(51,51,(int)(t.getJoueur().getBonus().getTime()/7.5) ,48);
                    switch (t.getJoueur().getBonus().getTypeBonus()) {
                        case 0 -> {

                            g.setColor(new Color(255,255,255));
                            g.drawString("Bouclier",120,40);
                            g.setColor(new Color(255, 50, 140, 90));
                            g.fillOval(t.getPosXJoueur() - 10, t.getPosYJoueur() - 20, Personnage.rayon + 20, Personnage.rayon + 40);
                        }
                        case 1 -> {

                            g.setColor(new Color(255,255,255));
                            g.drawString("JetPack",120,40);
                            g.setColor(new Color(250, 200, 80));
                            g.fillRect(t.getPosXJoueur() - 10, t.getPosYJoueur() + 10, 10, 25);
                            if (t.getJoueur().getPosy() == 480) {

                                g.setColor(new Color(250, 100, 80));
                                g.fillOval(t.getPosXJoueur() - 10, t.getPosYJoueur() + 35, 10, 10);
                            } else {
                                g.setColor(new Color(250, 40, 40));
                                g.fillOval(t.getPosXJoueur() - 10, t.getPosYJoueur() + 35, 10, 30);
                            }
                        }
                        case 2 -> {

                            g.setColor(new Color(255,255,255));
                            g.drawString("Aimant à pièce",120,40);
                            g.setColor(new Color(220, 220, 220));
                            g.drawOval(t.getPosXJoueur() - 175 / 2, t.getPosYJoueur() - 175 / 2, Personnage.rayon + 175, Personnage.rayon + 175);

                        }
                        case 3 -> {
                            g.setColor(new Color(255,255,255));
                            g.drawString("Score Double",120,40);
                        }case 4 ->{
                            g.setColor(new Color(255,255,255));
                            g.drawString("Ralentisseur",120,40);
                        }
                    }
                }
                g.setColor(scp.getLastColorEntered());
                g.fillOval(t.getPosXJoueur(), t.getPosYJoueur(), Personnage.rayon, Personnage.rayon);
                ArrayList<Piece> pVisible = t.getVisiblePiece();
                if (!pVisible.isEmpty()) {
                    for (Piece p : pVisible) {
                        g.setColor(Color.YELLOW);
                        g.fillOval(p.getPosx(), p.getPosy(), Piece.taille, Piece.taille);
                    }
                }
                try {
                    if (t.getBonus().getPosx() < 1200 + Bonus.taille) {
                        g.setColor(new Color(100, 10, 255));
                        g.fillOval(t.getBonus().getPosx(), t.getBonus().getPosy(), Bonus.taille, Bonus.taille);
                    }
                } catch (NullPointerException e) {
                }

                if (t.obstacleVisible()) {
                    for (Obstacle o : t.getVisibleObstacle()) {
                        switch (o.getT()) {

                            case 1 -> { g.drawImage(skull, o.getPosx(), o.getPosy(), this);}
                            case 0 ->
                            {
                                g.setColor(new Color(100,160,225));
                             //   g.fillRect(o.getPosx(), o.getPosy() - 20, Obstacle.width, Obstacle.taille);
                                g.drawImage(o.getImage(), o.getPosx(), o.getPosy()-20, this);
                            }
                            case 2 -> {
                                if (o.getPosy() <= 1000) {
                                    g.setColor(new Color(255, 0, 0));
                                    g.drawLine(o.getPosx() + Obstacle.width, 0, o.getPosx() + Obstacle.width, 500);
                                    g.drawLine(o.getPosx(), 0, o.getPosx(), 500);
                                    g.setColor(new Color(226, 40, 60, 50));
                                } else if (o.getPosy() <= 2000) {
                                    g.setColor(new Color(255, 0, 0));
                                    g.drawLine(o.getPosx() + Obstacle.width / 3, 0, o.getPosx() + Obstacle.width / 3, 500);
                                    g.drawLine(o.getPosx() + Obstacle.width * 2 / 3, 0, o.getPosx() + Obstacle.width * 2 / 3, 500);
                                    g.setColor(new Color(226, 40, 60, 50));
                                } else if (o.getPosy() <= 3000) {
                                    g.setColor(new Color(255, 0, 0));
                                    g.drawLine(o.getPosx() + Obstacle.width / 2, 0, o.getPosx() + Obstacle.width / 2, 500);
                                    g.setColor(new Color(226, 40, 60, 50));
                                } else {
                                    g.setColor(new Color(150, 240, 240));
                                    g.fillOval(o.getPosx() - 5, 550 - 15, Obstacle.width + 10, 30);
                                    g.setColor(new Color(255, 40, 40));
                                }
                                g.fillRect(o.getPosx(), 0, Obstacle.width, 550);

                            }
                            default -> g.fillRect(o.getPosx(), o.getPosy() - 20, Obstacle.width, Obstacle.taille);

                        }
                    }
                }
                //backgroundElements.draw(g);
                /**particleAnimation.updateAndDraw(g); // À appeler à chaque frame

                // Quand le personnage touche le sol
                if (t.collideWithGround) {
                    System.out.println("je touche le sol");
                    particleAnimation.triggerJumpAnimation(t.getPosXJoueur()+Personnage.rayon/2, t.getPosYJoueur()+100);
                    t.collideWithGround = false;
                }**/

                scoreLabel.setForeground(new Color(255, 255, 255));
                scoreLabel.setText("Score : " + Long.toString(Terrain.score) + " Nombre de Piece : " + Terrain.bonusPiece);
            } else {

                frame.remove(this);
                // g.setColor(new Color(112, 100, 121));

                JLabel label = new JLabel(" Jeu Terminé ! Score : " + Terrain.score);
                label.setFont(new Font("Verdana", 1, 20));
                label.setHorizontalAlignment(SwingConstants.CENTER);
                label.setSize(this.getSize());
                frame.getContentPane().add(label);

                JLabel labReplay = new JLabel("Appuyez sur la touche R pour rejouer ");
                labReplay.setFont(new Font("Verdana", 1, 10));
                labReplay.setHorizontalAlignment(SwingConstants.LEFT);
                labReplay.setSize(this.getSize());

                frame.getContentPane().add(labReplay);
                frame.repaint();
            }
        }
    }
    public void keyTyped(KeyEvent e) {
        //System.out.print("dijao");
        keyPressed(e);
        return;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getExtendedKeyCode()==KeyEvent.VK_SPACE && t.getJoueur().getPosy() >= 475){
            t.getJoueur().jump();

        }  if(e.getExtendedKeyCode()==KeyEvent.VK_Q){
            t.getJoueur().moveBackward();
        }
        if(e.getExtendedKeyCode()==KeyEvent.VK_D){
            t.getJoueur().moveForward();
        }
        if(e.getExtendedKeyCode()==KeyEvent.VK_S && t.getPosYJoueur() != 480){
            t.getJoueur().applyForce(20);
        }
        if(!t.getJoueur().enVie() && e.getExtendedKeyCode() == KeyEvent.VK_R) {
            t.willRestartSoon();
            System.out.println('b');
        }if(mainMenu && e.getExtendedKeyCode() == KeyEvent.VK_ENTER && !market)
            mainMenu = false;
        if(mainMenu && e.getExtendedKeyCode() == KeyEvent.VK_B) {
            market = true;
            frame.repaint();
        }
        if(market && e.getExtendedKeyCode() == KeyEvent.VK_ESCAPE){
            market = false;
            frame.repaint();
        }
        if(market && e.getExtendedKeyCode() == KeyEvent.VK_LEFT){
            scp.previousColor();
            frame.repaint();
        }
        if(market && e.getExtendedKeyCode() == KeyEvent.VK_RIGHT){
            scp.nextColor();
            System.out.println(scp.getCurrentColorIndex());
            frame.repaint();
        }
        if(market && e.getExtendedKeyCode() == KeyEvent.VK_ENTER){
            scp.changeColor();
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        //try {
            while(t.getJoueur().getVelocity()!=0){
                t.getJoueur().applyFriction(0.001);
            }
        //}//catch(InterruptedException exc){
        //    exc.printStackTrace();
        //}
        return;
    }
    public boolean getMainMenu(){return mainMenu;}


}
