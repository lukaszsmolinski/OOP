package BajtTrade.agent.robotnik.produkcja;

import BajtTrade.agent.robotnik.Robotnik;
import BajtTrade.gielda.Gielda;
import BajtTrade.produkt.Produkt;

import java.util.Random;

public class Losowy implements StrategiaProdukcji {

    public Losowy() {
    }

    @Override
    public Produkt coProdukuje(Robotnik robotnik, Gielda gielda) {
        Produkt[] produkty = Produkt.values();
        return produkty[new Random().nextInt(produkty.length)];
    }
}
