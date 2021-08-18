import java.util.ArrayList;

public class HvitRute extends Rute{
    HvitRute(int y, int x, Labyrint labyrint) {
        super(y, x, labyrint);
    }

    @Override
    public void gaa(ArrayList<Tuppel> sti, ArrayList<ArrayList<Tuppel>> stier) {
        besoek();
        ArrayList<Tuppel> kopi = new ArrayList<>(sti);
        kopi.add(getCoords());
        for (Rute nabo:getNaboer()){
            if (!nabo.getBesoekt()){
                nabo.gaa(kopi, stier);
            }
        }
        forlat();
    }


    @Override
    public char tilTegn() {
        return '.';
    }
}
