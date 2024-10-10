public class Main {

    public static void main(String[] args){

        Terrain t = new Terrain();
        Fen f = new Fen(t);
        while (f.isEnabled()) {
            while (!t.finished()) {
                try {
                    if(!f.getMainMenu()){
                        t.tour();
                        f.repaint();
                        Thread.sleep(10);
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            }while(t.willRestart()){
               try {
                   //System.out.println('a');
                   Thread.sleep(10);
               }
               catch (Exception e){
                   break;
               }
            }
            t = new Terrain();
            f = new Fen(t);
        }
    }

}