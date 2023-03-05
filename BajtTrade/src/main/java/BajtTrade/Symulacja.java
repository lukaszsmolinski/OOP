package BajtTrade;

import BajtTrade.agent.robotnik.Robotnik;
import BajtTrade.agent.spekulant.Spekulant;
import BajtTrade.gielda.Gielda;
import BajtTrade.json.DziennyRaportJson;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Symulacja {

    private final int dlugosc;
    private final List<Robotnik> robotnicy;
    private final List<Spekulant> spekulanci;
    private final Gielda gielda;
    private final List<DziennyRaportJson> dzienneRaporty;

    public Symulacja(int dlugosc, List<Robotnik> robotnicy, List<Spekulant> spekulanci, Gielda gielda) {
        this.dlugosc = dlugosc;
        this.robotnicy = robotnicy;
        this.spekulanci = spekulanci;
        this.gielda = gielda;
        this.dzienneRaporty = new ArrayList<>();
    }

    public void przeprowadz() {
        for (int dzien = 1; dzien <= dlugosc; ++dzien) {
            gielda.rozpocznijNowyDzien();

            for (Robotnik robotnik : robotnicy) {
                if (!robotnik.czyMartwy()) {
                    robotnik.przeprowadzDzien(gielda);
                }
            }

            for (Spekulant spekulant : spekulanci) {
                spekulant.przeprowadzDzien(gielda);
            }

            gielda.zrealizujOferty();

            for (Robotnik robotnik : robotnicy) {
                if (!robotnik.czyMartwy()) {
                    robotnik.zuzyjPrzedmioty();
                }
            }

            dzienneRaporty.add(new DziennyRaportJson(gielda.dzienJson(),
                    robotnicy.stream().map(Robotnik::json).collect(Collectors.toList()),
                    spekulanci.stream().map(Spekulant::json).collect(Collectors.toList())));
        }
    }

    public List<DziennyRaportJson> podsumowanie() {
        return dzienneRaporty;
    }
}
