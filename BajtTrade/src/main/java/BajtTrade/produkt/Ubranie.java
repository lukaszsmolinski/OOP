package BajtTrade.produkt;

public class Ubranie {

    private final int poziom;
    private int zuzycie;

    public Ubranie(int poziom) {
        this.poziom = poziom;
        this.zuzycie = 0;
    }

    public int poziom() {
        return poziom;
    }

    public int pozostaleUzycia() {
        return poziom * poziom - zuzycie;
    }

    public boolean zuzyty() {
        return pozostaleUzycia() == 0;
    }

    public void uzyj() {
        assert zuzycie < poziom * poziom;
        ++zuzycie;
    }
}
