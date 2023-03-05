package BajtTrade.gielda.oferta;

import BajtTrade.agent.Agent;
import BajtTrade.produkt.Produkt;

public class OfertaSprzedazyRobotnika extends OfertaZPoziomem {

    public OfertaSprzedazyRobotnika(Agent wlasciciel, Produkt produkt, int liczbaProduktow, int poziomProduktu) {
        super(wlasciciel, produkt, liczbaProduktow, poziomProduktu);
    }
}
