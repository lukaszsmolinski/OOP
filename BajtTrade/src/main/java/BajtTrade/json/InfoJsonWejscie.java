package BajtTrade.json;

import BajtTrade.gielda.Gielda;
import BajtTrade.produkt.Produkt;

import java.util.Map;

public class InfoJsonWejscie {

    public final int dlugosc;
    public final Gielda.TypGieldy gielda;
    public final int kara_za_brak_ubran;
    public final Map<Produkt, Double> ceny;

    public InfoJsonWejscie(int dlugosc, Gielda.TypGieldy gielda, int kara_za_brak_ubran, Map<Produkt, Double> ceny) {
        this.dlugosc = dlugosc;
        this.gielda = gielda;
        this.kara_za_brak_ubran = kara_za_brak_ubran;
        this.ceny = ceny;
    }
}
