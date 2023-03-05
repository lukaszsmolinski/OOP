package BajtTrade.agent.robotnik.produkcja;

import BajtTrade.agent.robotnik.Robotnik;
import BajtTrade.gielda.Gielda;
import BajtTrade.produkt.Produkt;

public class Krotkowzroczny implements StrategiaProdukcji {

    public Krotkowzroczny() {
    }

    @Override
    public Produkt coProdukuje(Robotnik robotnik, Gielda gielda) {
        return Produkt.PRODUKTY_GIELDOWE.stream()
                .reduce((p1, p2) -> gielda.sredniaCena(p1, 1) > gielda.sredniaCena(p2, 1) ? p1 : p2)
                .orElseThrow();
    }
}
