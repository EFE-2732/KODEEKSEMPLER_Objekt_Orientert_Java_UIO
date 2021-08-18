public class Tuppel {
    public int getX() {
        return x;
    }

    private int x;

    public int getY() {
        return y;
    }

    private int y;
    Tuppel(int y, int x){
        this.x = x;
        this.y = y;
    }

    public String toString(){
        return "("+getX()+", "+getY()+")";
    }
}
