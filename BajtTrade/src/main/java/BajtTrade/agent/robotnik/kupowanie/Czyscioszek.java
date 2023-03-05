package BajtTrade.agent.robotnik.kupowanie;

import BajtTrade.agent.robotnik.Robotnik;
import BajtTrade.gielda.oferta.OfertaKupnaRobotnika;
import BajtTrade.produkt.Produkt;

import java.util.ArrayList;
import java.util.List;

public class Czyscioszek implements StrategiaKupowania {

    public Czyscioszek() {
    }

    @Override
    public List<OfertaKupnaRobotnika> coKupuje(Robotnik robotnik) {
        List<OfertaKupnaRobotnika> wynik = new ArrayList<>();
        wynik.add(new OfertaKupnaRobotnika(robotnik, Produkt.JEDZENIE, 100));
        int ubrania = robotnik.ileUbranBrakujeNaDwaDni();
        if (ubrania > 0) {
            wynik.add(new OfertaKupnaRobotnika(robotnik, Produkt.UBRANIA, ubrania));
        }
        return wynik;
    }
}
