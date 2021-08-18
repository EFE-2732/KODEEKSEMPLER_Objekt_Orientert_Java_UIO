public class SubSekvens {
    private String subsekvens;
    private int antall;

    public SubSekvens(String subsekvens, int antall){
        setSubsekvens(subsekvens);
        setAntall(antall);
    }

    static SubSekvens sum(SubSekvens sub1, SubSekvens sub2){
        return new SubSekvens(sub1.getSubsekvens(), sub1.getAntall()+sub2.getAntall());
    }

    public String getSubsekvens() {
        return subsekvens;
    }

    public void setSubsekvens(String subsekvens) {
        this.subsekvens = subsekvens;
    }

    public int getAntall() {
        return antall;
    }

    public void setAntall(int antall) {
        this.antall = antall;
    }
}
