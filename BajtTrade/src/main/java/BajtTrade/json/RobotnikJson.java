package BajtTrade.json;

import BajtTrade.agent.robotnik.kariera.Kariera;
import BajtTrade.produkt.Produkt;
import BajtTrade.agent.robotnik.kupowanie.StrategiaKupowania;
import BajtTrade.agent.robotnik.praca.StrategiaPracy;
import BajtTrade.agent.robotnik.produkcja.StrategiaProdukcji;

import java.util.Map;

public class RobotnikJson {

    public final int id;
    public final ZasobyJson zasoby;
    public final int poziom;
    public final Kariera.SciezkaKariery kariera;
    public final StrategiaKupowania kupowanie;
    public final StrategiaProdukcji produkcja;
    public final StrategiaPracy uczenie;
    public final Kariera.Zmiana zmiana;
    public final Map<Produkt, Integer> produktywnosc;

    public RobotnikJson(int id, ZasobyJson zasoby, int poziom, Kariera.SciezkaKariery kariera,
                        StrategiaKupowania kupowanie, StrategiaProdukcji produkcja, StrategiaPracy uczenie,
                        Kariera.Zmiana zmiana, Map<Produkt, Integer> produktywnosc) {
        this.id = id;
        this.zasoby = zasoby;
        this.poziom = poziom;
        this.kariera = kariera;
        this.kupowanie = kupowanie;
        this.produkcja = produkcja;
        this.uczenie = uczenie;
        this.zmiana = zmiana;
        this.produktywnosc = produktywnosc;
    }
}
