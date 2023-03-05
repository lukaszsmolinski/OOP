package BajtTrade.agent.spekulant.handel;

import BajtTrade.agent.spekulant.Spekulant;
import BajtTrade.gielda.Gielda;
import BajtTrade.produkt.Produkt;

public class Wypukly implements StrategiaHandlu {

    @Override
    public boolean czyKupuje(Produkt produkt, Gielda gielda) {
        if (gielda.dzien() == 1) {
            return true;
        }
        if (gielda.dzien() == 2) {
            return false;
        }
        double a = gielda.sredniaCena(produkt, 3);
        double b = gielda.sredniaCena(produkt, 2);
        double c = gielda.sredniaCena(produkt, 1);
        return (a + b) / 2 > c;
    }

    @Override
    public boolean czySprzedaje(Produkt produkt, Gielda gielda) {
        if (gielda.dzien() == 1) {
            return true;
        }
        if (gielda.dzien() == 2) {
            return false;
        }
        double a = gielda.sredniaCena(produkt, 3);
        double b = gielda.sredniaCena(produkt, 2);
        double c = gielda.sredniaCena(produkt, 1);
        return (a + b) / 2 < c;
    }

    @Override
    public double cenaKupna(Spekulant spekulant, Produkt produkt, int poziom, Gielda gielda) {
        return 0.9 * gielda.sredniaCena(produkt, 1);
    }

    @Override
    public double cenaSprzedazy(Spekulant spekulant, Produkt produkt, int poziom, Gielda gielda) {
        return 1.1 * gielda.sredniaCena(produkt, 1);
    }
}
