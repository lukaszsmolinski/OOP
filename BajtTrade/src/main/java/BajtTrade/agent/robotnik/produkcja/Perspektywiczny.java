package BajtTrade.agent.robotnik.produkcja;

import BajtTrade.agent.robotnik.Robotnik;
import BajtTrade.gielda.Gielda;
import BajtTrade.produkt.Produkt;
import com.squareup.moshi.Json;

import java.util.function.Function;

public class Perspektywiczny implements StrategiaProdukcji {

    @Json(name="historia_perspektywy")
    private final int historiaPerspektywy;

    public Perspektywiczny(int historiaPerspektywy) {
        this.historiaPerspektywy = historiaPerspektywy;
    }

    @Override
    public Produkt coProdukuje(Robotnik robotnik, Gielda gielda) {
        int dni = Math.min(historiaPerspektywy, gielda.dzien());
        Function<Produkt, Double> wzrost =
                p -> gielda.sredniaCena(p, 1) - gielda.sredniaCena(p, dni);
        return Produkt.PRODUKTY_GIELDOWE.stream()
                .reduce((p1, p2) -> wzrost.apply(p1) > wzrost.apply(p2) ? p1 : p2)
                .orElseThrow();
    }
}
