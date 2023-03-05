package BajtTrade.agent.robotnik.kariera;

import BajtTrade.gielda.Gielda;
import BajtTrade.produkt.Produkt;
import com.squareup.moshi.Json;

import java.util.EnumMap;
import java.util.Map;

public abstract class Kariera {

    public enum SciezkaKariery {
        @Json(name="rolnik")
        ROLNIK,
        @Json(name="gornik")
        GORNIK,
        @Json(name="rzemieslnik")
        RZEMIESLNIK,
        @Json(name="inzynier")
        INZYNIER,
        @Json(name="programista")
        PROGRAMISTA
    }

    public enum Zmiana {
        @Json(name="rewolucjonista")
        REWOLUCJONISTA,
        @Json(name="konserwatysta")
        KONSERWATYSTA,
    }

    protected static final Map<Produkt, SciezkaKariery> PREMIE =
            new EnumMap<>(Produkt.class) {{
                put(Produkt.JEDZENIE, SciezkaKariery.ROLNIK);
                put(Produkt.DIAMENTY,SciezkaKariery.GORNIK);
                put(Produkt.UBRANIA, SciezkaKariery.RZEMIESLNIK);
                put(Produkt.NARZEDZIA, SciezkaKariery.INZYNIER);
                put(Produkt.PROGRAMY, SciezkaKariery.PROGRAMISTA);
                assert PREMIE.size() == Produkt.values().length;
            }};

    protected SciezkaKariery sciezkaKariery;
    protected final Map<SciezkaKariery, Integer> poziomyKariery;

    public Kariera(SciezkaKariery sciezkaKariery, int poziom) {
        this.sciezkaKariery = sciezkaKariery;
        this.poziomyKariery = new EnumMap<>(SciezkaKariery.class);
        for (SciezkaKariery k : SciezkaKariery.values()) {
            this.poziomyKariery.put(k, k != sciezkaKariery ? 1 : poziom);
        }
    }

    public abstract void uczSie(Gielda gielda);

    public void awansuj() {
        poziomyKariery.put(sciezkaKariery, poziomyKariery.get(sciezkaKariery) + 1);
    }

    public int premia(Produkt produkt) {
        if (PREMIE.get(produkt) != sciezkaKariery) {
            return 0;
        }

        int poziom = poziomyKariery.get(sciezkaKariery);
        switch (poziom) {
            case 1: return 50;
            case 2: return 150;
            case 3: return 300;
            default: return 300 + (poziom - 3) * 25;
        }
    }

    public Kariera.SciezkaKariery sciezkaKariery() {
        return sciezkaKariery;
    }

    public int poziom() {
        return poziomyKariery.get(sciezkaKariery);
    }

    public abstract Kariera.Zmiana zmiana();
}
