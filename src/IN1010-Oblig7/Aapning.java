import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Aapning extends HvitRute{
    Aapning(int y, int x, Labyrint labyrint) {
        super(y, x, labyrint);
    }

    public void gaa(ArrayList<Tuppel> sti, ArrayList<ArrayList<Tuppel>> stier){
        ArrayList<Tuppel> kopi = new ArrayList<>(sti);
        kopi.add(getCoords());

        int index = Collections.binarySearch(stier, kopi, Comparator.comparingInt(ArrayList::size));
        stier.add(index>=0?index:-index-1, kopi);
    }
}
