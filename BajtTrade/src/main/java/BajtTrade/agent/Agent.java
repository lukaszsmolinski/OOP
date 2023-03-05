package BajtTrade.agent;

import BajtTrade.gielda.Gielda;
import BajtTrade.json.ZasobyJson;
import BajtTrade.produkt.Produkt;
import BajtTrade.produkt.Ubranie;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class Agent {

    protected int id;
    protected int jedzenie;
    protected double diamenty;
    protected List<Ubranie> ubrania;
    protected SortedMap<Integer, Integer> narzedzia;
    protected SortedMap<Integer, Integer> programy;

    public Agent(int id, ZasobyJson zasoby) {
        this.id = id;
        this.jedzenie = 0;
        this.diamenty = 0;
        this.ubrania = new ArrayList<>();
        this.narzedzia = new TreeMap<>();
        this.programy = new TreeMap<>();

        this.dodajDiamenty(zasoby.diamenty);
        this.dodajProdukt(Produkt.JEDZENIE, 1, zasoby.jedzenie);
        this.dodajProdukt(Produkt.UBRANIA, 1, zasoby.ubrania.get(0));
        this.dodajProdukt(Produkt.NARZEDZIA, 1, zasoby.narzedzia.get(0));
        this.dodajProdukt(Produkt.PROGRAMY, 1, zasoby.programy.get(0));
    }

    public abstract void przeprowadzDzien(Gielda gielda);

    public int id() {
        return id;
    }

    public double diamenty() {
        return diamenty;
    }

    public void dodajDiamenty(double diamenty) {
        this.diamenty += diamenty;
    }

    public void dodajProdukt(Produkt produkt, int poziom, int liczba) {
        switch (produkt) {
            case UBRANIA:
                ubrania.addAll(Stream.generate(() -> new Ubranie(poziom))
                            .limit(liczba).collect(Collectors.toList()));
                break;
            case JEDZENIE:
                jedzenie += liczba;
                break;
            case PROGRAMY:
                programy.compute(poziom, (k, v) -> v == null ? liczba : v + liczba);
                break;
            case NARZEDZIA:
                narzedzia.compute(poziom, (k, v) -> v == null ? liczba : v + liczba);
                break;
            case DIAMENTY:
                diamenty += liczba;
                break;
        }
    }

    public boolean czyPosiada(Produkt produkt, int poziom) {
        switch(produkt) {
            case JEDZENIE:
                return poziom == 1 && jedzenie > 0;
            case UBRANIA:
                return ubrania.stream().anyMatch(u -> u.poziom() == poziom);
            case NARZEDZIA:
                return narzedzia.getOrDefault(poziom, 0) > 0;
            case PROGRAMY:
                return programy.getOrDefault(poziom, 0) > 0;
            case DIAMENTY:
                return diamenty > 0;
        }
        return false;
    }

    public ZasobyJson zasobyJson() {
        List<Integer> poziomyUbran = new ArrayList<>(Collections.nCopies(
                ubrania.stream().mapToInt(Ubranie::poziom).max().orElse(0), 0));
        for (Ubranie ubranie : ubrania) {
            poziomyUbran.set(ubranie.poziom() - 1, poziomyUbran.get(ubranie.poziom() - 1) + 1);
        }

        List<Integer> poziomyNarzedzi = new ArrayList<>(Collections.nCopies(
                narzedzia.size() == 0 ? 0 : narzedzia.lastKey(), 0));
        narzedzia.forEach((k, v) -> poziomyNarzedzi.set(k - 1, v));

        List<Integer> poziomyProgramow = new ArrayList<>(Collections.nCopies(
                programy.size() == 0 ? 0 : programy.lastKey(), 0));
        programy.forEach((k, v) -> poziomyProgramow.set(k - 1, v));

        return new ZasobyJson(diamenty, poziomyUbran, poziomyNarzedzi, jedzenie, poziomyProgramow);
    }
}
