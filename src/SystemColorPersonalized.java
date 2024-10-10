import java.awt.*;
import java.util.ArrayList;

public class SystemColorPersonalized {
    private ArrayList<Color> colors;
    private int currentColorIndex = 0;
    private int lastColorEntered = 0;

    public SystemColorPersonalized(){
        colors = new ArrayList<>(10);
        colors.add(Color.GRAY);
        colors.add(Color.YELLOW);
        colors.add(Color.RED);
        colors.add(Color.BLUE);
        colors.add(Color.GREEN);
        colors.add(Color.WHITE);
        colors.add(Color.ORANGE);
        colors.add(Color.PINK);
        colors.add(Color.CYAN);
        colors.add(Color.magenta);
    }
    public ArrayList<Color> visibleColors(){
        ArrayList<Color> visible = new ArrayList<>(3);
        switch(currentColorIndex) {
             default -> {
                 visible.add(colors.get(currentColorIndex-1));
                 visible.add(colors.get(currentColorIndex));
                 visible.add(colors.get(currentColorIndex+1));
             }
             case 0 -> {visible.add(colors.get(9));visible.add(colors.get(0));visible.add(colors.get(1));}
             case 9 -> {visible.add(colors.get(8));visible.add(colors.get(9));visible.add(colors.get(0));}
        }
        return visible;
    }
    public void nextColor(){
        if(currentColorIndex == 9 )
            currentColorIndex = 0;
        else currentColorIndex++;
    }
    public void previousColor(){
        if(currentColorIndex == 0 )
            currentColorIndex = 9;
        else currentColorIndex--;
    }

    public static void draw(Graphics g, ArrayList<Color> colorShowed){
        int i = 0;
        for(Color c : colorShowed){
            g.setColor(c);
            g.fillOval(i*200 + 400, 500 , 50 , 50);
            i++;
        }
    }

    public int getCurrentColorIndex() {
        return currentColorIndex;
    }
    public void changeColor(){
        lastColorEntered = currentColorIndex;
    }
    public Color getLastColorEntered(){
        return colors.get(lastColorEntered);
    }
}
