package BajtTrade.gielda.oferta;

import BajtTrade.agent.Agent;
import BajtTrade.produkt.Produkt;

public abstract class Oferta {

    protected Agent wlasciciel;
    protected Produkt produkt;
    protected int liczbaProduktow;

    public Oferta(Agent wlasciciel, Produkt produkt, int liczbaProduktow) {
        this.wlasciciel = wlasciciel;
        this.produkt = produkt;
        this.liczbaProduktow = liczbaProduktow;
    }

    public Produkt produkt() {
        return produkt;
    }

    public void zmniejszLiczbe(int n) {
        assert liczbaProduktow >= n;
        liczbaProduktow -= n;
    }

    public int liczbaProduktow() {
        return liczbaProduktow;
    }

    public Agent wlasciciel() {
        return wlasciciel;
    }
}
