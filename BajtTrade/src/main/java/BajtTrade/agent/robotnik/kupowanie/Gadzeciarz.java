package BajtTrade.agent.robotnik.kupowanie;

import BajtTrade.agent.robotnik.Robotnik;
import BajtTrade.gielda.oferta.OfertaKupnaRobotnika;
import BajtTrade.produkt.Produkt;
import com.squareup.moshi.Json;

import java.util.ArrayList;
import java.util.List;

public class Gadzeciarz implements StrategiaKupowania {

    @Json(name="liczba_narzedzi")
    private final int liczbaNarzedzi;

    public Gadzeciarz(int liczbaNarzedzi) {
        this.liczbaNarzedzi = liczbaNarzedzi;
    }

    @Override
    public List<OfertaKupnaRobotnika> coKupuje(Robotnik robotnik) {
        List<OfertaKupnaRobotnika> wynik = new ArrayList<>();
        wynik.add(new OfertaKupnaRobotnika(robotnik, Produkt.JEDZENIE, 100));
        int ubrania = robotnik.ileUbranBrakujeNaDwaDni();
        if (ubrania > 0) {
            wynik.add(new OfertaKupnaRobotnika(robotnik, Produkt.UBRANIA, ubrania));
        }
        wynik.add(new OfertaKupnaRobotnika(robotnik, Produkt.NARZEDZIA, liczbaNarzedzi));
        wynik.add(new OfertaKupnaRobotnika(robotnik, Produkt.PROGRAMY, robotnik.dzisiejszaProdukcja()));
        return wynik;
    }
}
