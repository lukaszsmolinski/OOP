package BajtTrade.agent.spekulant.handel;

import BajtTrade.agent.spekulant.Spekulant;
import BajtTrade.gielda.Gielda;
import BajtTrade.produkt.Produkt;

public class RegulujacyRynek implements StrategiaHandlu {

    public RegulujacyRynek() {
    }

    @Override
    public boolean czyKupuje(Produkt produkt, Gielda gielda) {
        return gielda.dzien() > 1;
    }

    @Override
    public boolean czySprzedaje(Produkt produkt, Gielda gielda) {
        return gielda.dzien() > 1;
    }

    @Override
    public double cenaKupna(Spekulant spekulant, Produkt produkt, int poziom, Gielda gielda) {
        return 1.1 * cenaPodstawowa(produkt, gielda);
    }

    @Override
    public double cenaSprzedazy(Spekulant spekulant, Produkt produkt, int poziom, Gielda gielda) {
        return 0.9 * cenaPodstawowa(produkt, gielda);
    }

    private double cenaPodstawowa(Produkt produkt, Gielda gielda) {
        return gielda.sredniaCena(produkt, 1)
                * gielda.wystawionePrzedmioty(produkt, 0)
                / Math.max(1., gielda.wystawionePrzedmioty(produkt, 1));
    }
}
