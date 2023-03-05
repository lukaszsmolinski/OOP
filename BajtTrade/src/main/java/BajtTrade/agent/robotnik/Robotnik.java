package BajtTrade.agent.robotnik;

import BajtTrade.agent.Agent;
import BajtTrade.agent.robotnik.kariera.Kariera;
import BajtTrade.agent.robotnik.kupowanie.StrategiaKupowania;
import BajtTrade.agent.robotnik.praca.StrategiaPracy;
import BajtTrade.agent.robotnik.produkcja.StrategiaProdukcji;
import BajtTrade.gielda.Gielda;
import BajtTrade.json.RobotnikJson;
import BajtTrade.json.ZasobyJson;
import BajtTrade.gielda.oferta.OfertaKupnaRobotnika;
import BajtTrade.gielda.oferta.OfertaSprzedazyRobotnika;
import BajtTrade.produkt.Produkt;
import BajtTrade.produkt.Ubranie;

import java.util.EnumMap;
import java.util.Map;

public class Robotnik extends Agent {

    private Parametry parametry;
    private final StrategiaPracy strategiaPracy;
    private final StrategiaKupowania strategiaKupowania;
    private final StrategiaProdukcji strategiaProdukcji;
    private final Kariera kariera;
    private final Map<Produkt, Integer> produktywnosc;
    private final Map<Produkt, Integer> bazowaProduktywnosc;
    private int dniBezJedzenia;
    private boolean martwy;
    private int dzisiejszaProdukcja;
    private boolean uczylSieDzisiaj;


    public Robotnik(int id, ZasobyJson zasoby, StrategiaPracy strategiaPracy, StrategiaKupowania strategiaKupowania, StrategiaProdukcji strategiaProdukcji, Kariera kariera, Map<Produkt, Integer> produktywnosc) {
        super(id, zasoby);
        this.strategiaPracy = strategiaPracy;
        this.strategiaKupowania = strategiaKupowania;
        this.strategiaProdukcji = strategiaProdukcji;
        this.kariera = kariera;
        this.dniBezJedzenia = 0;
        this.martwy = false;
        this.dzisiejszaProdukcja = 0;
        this.produktywnosc = new EnumMap<>(produktywnosc);
        this.bazowaProduktywnosc = new EnumMap<>(produktywnosc);
    }

    public void ustawParametry(Parametry parametry) {
        this.parametry = parametry;
    }

    public boolean czyMartwy() {
        return martwy;
    }

    public int dzisiejszaProdukcja() {
        return dzisiejszaProdukcja;
    }

    public int ileWyprodukuje(Produkt produkt) {
        return produktywnosc.get(produkt);
    }

    public int ileUbranBrakujeNaDwaDni() {
        int v1 = 0, v2 = 0;
        for (Ubranie ubranie : ubrania) {
            if (ubranie.pozostaleUzycia() == 1) {
                v1++;
            } else {
                v2++;
            }
        }
        return Math.max(0, 2 * Parametry.DZIENNE_ZUZYCIE_UBRAN - 2 * v2 - v1);
    }

    @Override
    public void przeprowadzDzien(Gielda gielda) {
        obliczProduktywnosc();
        if (strategiaPracy.czyPracuje(this, gielda)) {
            uczylSieDzisiaj = false;
            pracuj(gielda);
        } else {
            uczylSieDzisiaj = true;
            kariera.uczSie(gielda);
        }
    }

    public void zuzyjPrzedmioty() {
        uzyjUbrania();
        narzedzia.clear();
        zjedz();
        if (martwy) {
            diamenty = 0;
        }
    }


    public RobotnikJson json() {
        return new RobotnikJson(id, zasobyJson(), kariera.poziom(), kariera.sciezkaKariery(),
                strategiaKupowania, strategiaProdukcji, strategiaPracy, kariera.zmiana(),
                new EnumMap<>(bazowaProduktywnosc));
    }

    private void obliczProduktywnosc() {
        int premia = narzedzia.entrySet().stream().mapToInt(e -> e.getKey() * e.getValue()).sum();
        if (ubrania.size() < Parametry.DZIENNE_ZUZYCIE_UBRAN) {
            premia -= parametry.karaZaBrakUbran;
        }
        premia -= Parametry.KARA_ZA_BRAK_JEDZENIA[dniBezJedzenia];

        for (Produkt produkt : Produkt.values()) {
            produktywnosc.put(produkt, Math.max(0,
                    bazowaProduktywnosc.get(produkt) * (100 + premia + kariera.premia(produkt)) / 100));
        }
    }

    private void pracuj(Gielda gielda) {
        produkujIWystaw(gielda, strategiaProdukcji.coProdukuje(this, gielda));

        for (OfertaKupnaRobotnika oferta : strategiaKupowania.coKupuje(this)) {
            gielda.dodajOferteKupna(oferta);
        }
    }

    private void zjedz() {
        if (!uczylSieDzisiaj) {
            jedzenie -= Parametry.DZIENNE_ZUZYCIE_JEDZENIA;
        }

        if (jedzenie < 0) {
            jedzenie = 0;
            if (++dniBezJedzenia > Parametry.MAX_DNI_BEZ_JEDZENIA) {
                martwy = true;
            }
        } else {
            dniBezJedzenia = 0;
        }
    }

    private void uzyjUbrania() {
        for (int i = 0; i < Math.min(Parametry.DZIENNE_ZUZYCIE_UBRAN, ubrania.size()); i++) {
            ubrania.get(i).uzyj();
        }
        ubrania.removeIf(Ubranie::zuzyty);
    }

    private void produkujIWystaw(Gielda gielda, Produkt produkt) {
        int liczbaProduktow = dzisiejszaProdukcja = ileWyprodukuje(produkt);
        if (liczbaProduktow <= 0) {
            return;
        }

        if (produkt == Produkt.DIAMENTY) {
            diamenty += liczbaProduktow;
        } else if (produkt == Produkt.JEDZENIE) {
            gielda.dodajOferteSprzedazy(
                    new OfertaSprzedazyRobotnika(this, produkt, liczbaProduktow, 1));

        } else if (produkt == Produkt.PROGRAMY) {
            int poziom = kariera.sciezkaKariery() == Kariera.SciezkaKariery.PROGRAMISTA
                    ? kariera.poziom() : 1;
            gielda.dodajOferteSprzedazy(
                    new OfertaSprzedazyRobotnika(this, produkt, liczbaProduktow, poziom));
        }
        else {
            while (liczbaProduktow > 0) {
                if (programy.isEmpty()) {
                    // nie ma programów, więc produkujemy pierwszy poziom produktu
                    gielda.dodajOferteSprzedazy(
                            new OfertaSprzedazyRobotnika(this, produkt, liczbaProduktow, 1));
                    liczbaProduktow = 0;
                } else {
                    // wykorzystujemy programy do ulepszenia produktu
                    int poziom = programy.lastKey();
                    int liczba = Math.min(programy.get(poziom), liczbaProduktow);
                    gielda.dodajOferteSprzedazy(new OfertaSprzedazyRobotnika(this, produkt, liczba, poziom));
                    liczbaProduktow -= liczba;
                    programy.compute(poziom, (k, v) -> v - liczba);
                    if (programy.get(poziom) == 0) {
                        programy.remove(poziom);
                    }
                }
            }
        }
    }
}
