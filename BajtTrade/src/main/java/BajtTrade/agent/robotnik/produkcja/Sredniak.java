package BajtTrade.agent.robotnik.produkcja;

import BajtTrade.agent.robotnik.Robotnik;
import BajtTrade.gielda.Gielda;
import BajtTrade.produkt.Produkt;
import com.squareup.moshi.Json;

import java.util.function.Function;
import java.util.stream.IntStream;

public class Sredniak implements StrategiaProdukcji {

    @Json(name="historia_sredniej_produkcji")
    private final int historiaSredniejProdukcji;

    public Sredniak(int historiaSredniejProdukcji) {
        this.historiaSredniejProdukcji = historiaSredniejProdukcji;
    }

    @Override
    public Produkt coProdukuje(Robotnik robotnik, Gielda gielda) {
        int dni = Math.min(gielda.dzien(), historiaSredniejProdukcji);
        Function<Produkt, Double> f = p -> IntStream.range(1, dni + 1)
                .mapToDouble(i -> gielda.sredniaCena(p, i)).max().orElseThrow();
        return Produkt.PRODUKTY_GIELDOWE.stream()
                .reduce((p1, p2) -> f.apply(p1) > f.apply(p2) ? p1 : p2)
                .orElseThrow();
    }
}
