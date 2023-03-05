package BajtTrade.gielda;

import BajtTrade.json.InfoJsonWyjscie;
import BajtTrade.gielda.oferta.OfertaZPoziomem;
import BajtTrade.produkt.Produkt;

import java.util.EnumMap;
import java.util.Map;

public class Statystyki {

    private final Map<Produkt, Double> najnizszaCena;
    private final Map<Produkt, Double> sredniaCena;
    private final Map<Produkt, Double> najwyzszaCena;
    private final Map<Produkt, Integer> liczbaWystawionych;
    private final Map<Produkt, Integer> liczbaSprzedanych;
    private final Map<Produkt, Integer> najwyzszyPoziom;

    public Statystyki() {
        this.najnizszaCena = new EnumMap<>(Produkt.class);
        this.sredniaCena = new EnumMap<>(Produkt.class);
        this.najwyzszaCena = new EnumMap<>(Produkt.class);
        this.liczbaWystawionych = new EnumMap<>(Produkt.class);
        this.liczbaSprzedanych = new EnumMap<>(Produkt.class);
        this.najwyzszyPoziom = new EnumMap<>(Produkt.class);
        for (Produkt produkt : Produkt.PRODUKTY_GIELDOWE) {
            liczbaWystawionych.put(produkt, 0);
            liczbaSprzedanych.put(produkt, 0);
            najwyzszyPoziom.put(produkt, 0);
        }
    }

    public void wystawionoOferteSprzedazy(OfertaZPoziomem oferta) {
        liczbaWystawionych.compute(oferta.produkt(), (k, v) -> v == null ? 1 : v + oferta.liczbaProduktow());
        najwyzszyPoziom.compute(oferta.produkt(), (k, v) -> v == null ? 1 : Math.max(v, oferta.poziomProduktu()));
    }

    public void sprzedanoProdukt(Produkt produkt, int liczba, double cena) {
        int n = liczbaSprzedanych.get(produkt);
        sredniaCena.compute(produkt, (k, v) -> v == null ? cena : (v * n + liczba * cena) / (n + liczba));
        liczbaSprzedanych.put(produkt, n + liczba);
        najnizszaCena.compute(produkt, (k, v) -> v == null ? cena : Math.min(cena, v));
        najwyzszaCena.compute(produkt, (k, v) -> v == null ? cena : Math.max(cena, v));
    }

    public void wypelnijPustePola(Map<Produkt, Double> domyslneWartosci) {
        domyslneWartosci.forEach((k, v) -> {
            najnizszaCena.putIfAbsent(k, v);
            sredniaCena.putIfAbsent(k, v);
            najwyzszaCena.putIfAbsent(k, v);
        });
    }

    public double najnizszaCena(Produkt produkt) {
        return najnizszaCena.get(produkt);
    }

    public double sredniaCena(Produkt produkt) {
        return sredniaCena.get(produkt);
    }

    public int najwyzszyPoziom(Produkt produkt) {
        return najwyzszyPoziom.get(produkt);
    }

    public int liczbaWystawionych(Produkt produkt) {
        return liczbaWystawionych.get(produkt);
    }

    public InfoJsonWyjscie json(int dzien) {
        return new InfoJsonWyjscie(dzien, new EnumMap<>(sredniaCena),
                new EnumMap<>(najwyzszaCena), new EnumMap<>(najnizszaCena));
    }
}
