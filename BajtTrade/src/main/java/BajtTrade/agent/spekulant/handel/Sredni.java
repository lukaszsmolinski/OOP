package BajtTrade.agent.spekulant.handel;

import BajtTrade.agent.spekulant.Spekulant;
import BajtTrade.gielda.Gielda;
import BajtTrade.produkt.Produkt;
import com.squareup.moshi.Json;

import java.util.stream.IntStream;

public class Sredni implements StrategiaHandlu {

    @Json(name="historia_spekulanta_sredniego")
    private final int historiaSpekulantaSredniego;

    public Sredni(int historiaSpekulantaSredniego) {
        this.historiaSpekulantaSredniego = historiaSpekulantaSredniego;
    }

    @Override
    public boolean czyKupuje(Produkt produkt, Gielda gielda) {
        return true;
    }

    @Override
    public boolean czySprzedaje(Produkt produkt, Gielda gielda) {
        return true;
    }

    @Override
    public double cenaKupna(Spekulant spekulant, Produkt produkt, int poziom, Gielda gielda) {
        int dni = Math.min(gielda.dzien(), historiaSpekulantaSredniego);
        return cenaPodstawowa(produkt, dni, gielda)
                * (spekulant.czyPosiada(produkt, poziom) ? 0.9 : 0.95);
    }

    @Override
    public double cenaSprzedazy(Spekulant spekulant, Produkt produkt, int poziom, Gielda gielda) {
        int dni = Math.min(gielda.dzien(), historiaSpekulantaSredniego);
        return 1.1 * cenaPodstawowa(produkt, dni, gielda);
    }

    private double cenaPodstawowa(Produkt produkt, int dni, Gielda gielda) {
        return IntStream.range(1, dni + 1).mapToDouble(i -> gielda.sredniaCena(produkt, i)).sum() / dni;
    }
}
