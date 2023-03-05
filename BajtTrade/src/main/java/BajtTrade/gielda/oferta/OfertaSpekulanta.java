package BajtTrade.gielda.oferta;

import BajtTrade.agent.Agent;
import BajtTrade.produkt.Produkt;

public class OfertaSpekulanta extends OfertaZPoziomem {

    private final double cena;

    public OfertaSpekulanta(Agent wlasciciel, Produkt produkt, int liczbaProduktow, int poziomProduktu, double cena) {
        super(wlasciciel, produkt, liczbaProduktow, poziomProduktu);
        this.cena = cena;
    }

    public double cena() {
        return cena;
    }
}
