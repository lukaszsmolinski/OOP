package BajtTrade.gielda.oferta;

import BajtTrade.agent.Agent;
import BajtTrade.produkt.Produkt;

public abstract class OfertaZPoziomem extends Oferta {

    protected int poziomProduktu;

    public OfertaZPoziomem(Agent wlasciciel, Produkt produkt, int liczbaProduktow, int poziomProduktu) {
        super(wlasciciel, produkt, liczbaProduktow);
        this.poziomProduktu = poziomProduktu;
    }

    public int poziomProduktu() {
        return poziomProduktu;
    }
}
