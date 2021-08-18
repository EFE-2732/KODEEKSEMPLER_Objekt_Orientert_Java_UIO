import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class Labyrint {
    protected Rute[][] grid;
    private int ymax;
    private int xmax;
    protected ArrayList<ArrayList<Tuppel>> stier;

    Labyrint(File fil) throws FileNotFoundException {
        Scanner scanner = new Scanner(fil);
        String[] tempSize = scanner.nextLine().split(" ");
        setYmax(Integer.parseInt(tempSize[0]));
        setXmax(Integer.parseInt(tempSize[1]));
        grid = new Rute[getYmax()][getXmax()];

        for (int y = 0; y < getYmax(); y++){
            String line = scanner.nextLine();
            for (int x = 0; x < getXmax(); x++){
                char c = line.charAt(x);
                if (c == '.'){
                    if(x==0||x==getXmax()-1||y==0||y==getYmax()-1){
                        grid[y][x] = new Aapning(y, x, this);
                    } else {
                        grid[y][x] = new HvitRute(y, x, this);
                    }

                } else if ( c == '#'){
                    grid[y][x] = new SortRute(y, x, this);
                }
            }
        }
    }

    Labyrint(String path) throws FileNotFoundException {
        this(new File(path));
    }

    public int getXmax() {
        return xmax;
    }

    public int getYmax() {
        return ymax;
    }

    public void setXmax(int xmax) {
        this.xmax = xmax;
    }

    public void setYmax(int ymax) {
        this.ymax = ymax;
    }

    public ArrayList<ArrayList<Tuppel>> finnUtveiFra(int kol, int rad){
        return getRute(rad, kol).finnUtvei();
    }

    public Rute[][] getGrid(){
        return grid;
    }

    public Rute getRute(int y, int x){
        if (0<=y && x<getXmax() && y<getYmax() && 0<=x){
            return grid[y][x];
        }
        return null;
    }

    @Override
    public String toString(){
        StringBuilder string = new StringBuilder();
        for (Rute[] y:grid){
            for (Rute x:y){
                string.append(x.tilTegn());
            }
            string.append("\n");
        }
        return string.toString();
    }
}
