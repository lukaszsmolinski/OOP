package BajtTrade.agent.spekulant;

import BajtTrade.agent.Agent;
import BajtTrade.agent.spekulant.handel.StrategiaHandlu;
import BajtTrade.gielda.Gielda;
import BajtTrade.json.SpekulantJson;
import BajtTrade.json.ZasobyJson;
import BajtTrade.gielda.oferta.OfertaSpekulanta;
import BajtTrade.produkt.Produkt;
import BajtTrade.produkt.Ubranie;

import java.util.*;

public class Spekulant extends Agent {

    private final StrategiaHandlu strategiaHandlu;

    public Spekulant(int id, ZasobyJson zasoby, StrategiaHandlu strategiaHandlu) {
        super(id, zasoby);
        this.strategiaHandlu = strategiaHandlu;
    }

    @Override
    public void przeprowadzDzien(Gielda gielda) {
        wystawOfertyKupna(gielda);
        wystawOfertySprzedazy(gielda);
    }

    private void wystawOfertyKupna(Gielda gielda) {
        for (Produkt produkt : Produkt.PRODUKTY_GIELDOWE) {
            if (strategiaHandlu.czyKupuje(produkt, gielda)) {
                int maxPoziom = gielda.najwyzszyDostepnyPoziom(produkt);
                for (int poziom = 1; poziom <= maxPoziom; poziom++) {
                    double cena = strategiaHandlu.cenaKupna(this, produkt, poziom, gielda);
                    gielda.dodajOferteKupna(
                            new OfertaSpekulanta(this, produkt, 100, poziom, cena));
                }
            }
        }
    }

    private void wystawOfertySprzedazy(Gielda gielda) {
        if (strategiaHandlu.czySprzedaje(Produkt.JEDZENIE, gielda)) {
            wystaw(gielda, Produkt.JEDZENIE, jedzenie, 1);
            jedzenie = 0;
        }

        if (strategiaHandlu.czySprzedaje(Produkt.NARZEDZIA, gielda)) {
            for (int poziom : narzedzia.keySet()) {
                wystaw(gielda, Produkt.NARZEDZIA, narzedzia.get(poziom), poziom);
            }
            narzedzia.clear();
        }

        if (strategiaHandlu.czySprzedaje(Produkt.PROGRAMY, gielda)) {
            for (int poziom : programy.keySet()) {
                wystaw(gielda, Produkt.PROGRAMY, programy.get(poziom), poziom);
            }
            programy.clear();
        }

        if (strategiaHandlu.czySprzedaje(Produkt.UBRANIA, gielda)) {
            Map<Integer, Integer> m = new HashMap<>();
            for (Ubranie ubranie : ubrania) {
                m.compute(ubranie.poziom(), (k, v) -> v == null ? 1 : v + 1);
            }
            for (int poziom : m.keySet()) {
                wystaw(gielda, Produkt.UBRANIA, m.get(poziom), poziom);
            }
            ubrania.clear();
        }
    }

    private void wystaw(Gielda gielda, Produkt produkt, int liczba, int poziom) {
        if (liczba > 0) {
            gielda.dodajOferteSprzedazy(new OfertaSpekulanta(this, produkt, liczba, poziom,
                    strategiaHandlu.cenaSprzedazy(this, produkt, poziom, gielda)));
        }
    }

    public SpekulantJson json() {
        return new SpekulantJson(id, zasobyJson(), strategiaHandlu);
    }
}
