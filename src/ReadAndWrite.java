import java.io.*;

public class ReadAndWrite {
    private int piecesLocal,maxHighScore;
    private static final String filePath = "data/data.txt";
    public ReadAndWrite(){
        piecesLocal = Terrain.bonusPiece;
        maxHighScore =  0;
        //readPiecesAndHighScore();  charge dans les attributs du ReadAndWrite les datas des pieces et du max score
        // a utiliser fans writeMaxHighScore()

        //writeMaxHighScore();  Ecrit dans le fichier data les attributs du ReadAndWrite
    }
    public void readPiecesAndHighScore(){
        //Instructions afin de lire ligne par ligne le fichier data
        try {
            FileReader reader = new FileReader(filePath);

            BufferedReader bufferedReader = new BufferedReader(reader);
            String line1;
            String line2;
            line1 = bufferedReader.readLine();//lecture du fichier data
            line2 = bufferedReader.readLine();

            //Traitement de la ligne 1 :
            String[] parts = line1.split(" : ");
            String valueString = parts[1];
            int globalMP = Integer.parseInt(valueString);
            piecesLocal += globalMP;

            //Traitement de la ligne 2:
            parts = line2.split(" : ");
            valueString = parts[1];
            maxHighScore = Math.max((int)Terrain.score, Integer.parseInt(valueString));



        }catch(IOException e){
            System.out.println("Erreur lors de la lecture des données");
            e.printStackTrace();
        }
    }
    public void writeMaxHighScore(){
        readPiecesAndHighScore();
        try {
            FileWriter fw = new FileWriter(filePath);
            fw.write("Pièces Total : "+piecesLocal +"\n");
            fw.write("HighScore : "+maxHighScore );
            fw.close();
        }catch(IOException e){
            System.out.println("Erreur lors de l'écriture des données");
            e.printStackTrace();
        }
    }
    public String[] getAllLines() {
        String[] parts = new String[5];
        try {
            FileReader reader = new FileReader(filePath);

            BufferedReader bufferedReader = new BufferedReader(reader);
            String value = "";
            String line1;
            while ( (line1 = bufferedReader.readLine()) != null) {
                value += "§" + line1;

            }bufferedReader.close();
            String[] part = value.split("§");
            return part;
        } catch (IOException e) {
            System.out.println("Erreur lors de la lecture des données");
            e.printStackTrace();
        }
        return parts;
    }
}
