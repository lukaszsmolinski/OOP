package BajtTrade.agent.robotnik.kupowanie;

import BajtTrade.agent.robotnik.Robotnik;
import BajtTrade.gielda.oferta.OfertaKupnaRobotnika;
import BajtTrade.produkt.Produkt;

import java.util.List;

public class Technofob implements StrategiaKupowania {

    public Technofob() {
    }

    @Override
    public List<OfertaKupnaRobotnika> coKupuje(Robotnik robotnik) {
        return List.of(new OfertaKupnaRobotnika(robotnik, Produkt.JEDZENIE, 100));
    }
}
