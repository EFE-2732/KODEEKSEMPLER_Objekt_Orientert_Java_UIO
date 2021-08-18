import java.util.ArrayList;

public class SortRute extends Rute{

    SortRute(int y, int x, Labyrint labyrint) {
        super(y, x, labyrint);
    }

    @Override
    public void gaa(ArrayList<Tuppel> sti, ArrayList<ArrayList<Tuppel>> stier) {}

    @Override
    public char tilTegn() {
        return '#';
    }
}
