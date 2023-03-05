package BajtTrade.agent.robotnik.produkcja;

import BajtTrade.agent.robotnik.Robotnik;
import BajtTrade.gielda.Gielda;
import BajtTrade.produkt.Produkt;

public interface StrategiaProdukcji {

    Produkt coProdukuje(Robotnik robotnik, Gielda gielda);
}
