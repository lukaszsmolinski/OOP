package BajtTrade.agent.spekulant.handel;

import BajtTrade.agent.spekulant.Spekulant;
import BajtTrade.gielda.Gielda;
import BajtTrade.produkt.Produkt;

public interface StrategiaHandlu {

    boolean czyKupuje(Produkt produkt, Gielda gielda);
    boolean czySprzedaje(Produkt produkt, Gielda gielda);
    double cenaKupna(Spekulant spekulant, Produkt produkt, int poziom, Gielda gielda);
    double cenaSprzedazy(Spekulant spekulant, Produkt produkt, int poziom, Gielda gielda);
}
