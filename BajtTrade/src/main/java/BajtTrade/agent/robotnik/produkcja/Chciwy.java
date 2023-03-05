package BajtTrade.agent.robotnik.produkcja;

import BajtTrade.agent.robotnik.Robotnik;
import BajtTrade.gielda.Gielda;
import BajtTrade.produkt.Produkt;

import java.util.function.Function;

public class Chciwy implements StrategiaProdukcji {

    public Chciwy() {
    }

    @Override
    public Produkt coProdukuje(Robotnik robotnik, Gielda gielda) {
        Function<Produkt, Double> zysk =
                p -> gielda.sredniaCena(p, 1) * robotnik.ileWyprodukuje(p);
        return Produkt.PRODUKTY_GIELDOWE.stream()
                .reduce((p1, p2) -> zysk.apply(p1) >= zysk.apply(p2) ? p1 : p2)
                .orElseThrow();
    }
}
