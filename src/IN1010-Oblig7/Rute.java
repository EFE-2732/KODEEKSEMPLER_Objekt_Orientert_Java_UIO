import java.util.ArrayList;

public abstract class Rute {
    private Labyrint labyrint;
    private Rute[] naboer = {null, null, null, null};
    private Boolean besoekt = false;
    private Tuppel coords;

    Rute(int y, int x, Labyrint labyrint){
        this.coords = new Tuppel(y,x);
        this.labyrint = labyrint;

        Rute top = labyrint.getRute(y-1,x);
        if(top != null){
            setRetning(top,0);
            top.setRetning(this, 2);
        }

        Rute left = labyrint.getRute(y,x-1);
        if(left!= null){
            setRetning(left,3);
            left.setRetning(this, 1);
        }

    }

    public Tuppel getCoords() {
        return coords;
    }

    public void setRetning(Rute rute, int retningInt){
        naboer[retningInt] = rute;

    }

    public Rute[] getNaboer(){
        return naboer;
    }

    public void besoek(){
        besoekt = true;
    }

    public void forlat(){
        besoekt = false;
    }

    public Boolean getBesoekt(){
        return besoekt;
    }

    public ArrayList<ArrayList<Tuppel>> finnUtvei(){
        ArrayList<ArrayList<Tuppel>> stier = new ArrayList<ArrayList<Tuppel>>();
        gaa(new ArrayList<Tuppel>(), stier);
        return stier;
    }

    public Labyrint getLabyrint() {
        return labyrint;
    }

    public abstract void gaa(ArrayList<Tuppel> sti, ArrayList<ArrayList<Tuppel>> stier);

    public abstract char tilTegn();
}
