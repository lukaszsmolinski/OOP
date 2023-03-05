package BajtTrade.json;

import BajtTrade.json.adnotacja.LiczbaLubLista;

import java.util.List;

public class ZasobyJson {

    public final double diamenty;
    public final @LiczbaLubLista List<Integer> ubrania;
    public final @LiczbaLubLista List<Integer> narzedzia;
    public final int jedzenie;
    public final @LiczbaLubLista List<Integer> programy;

    public ZasobyJson(double diamenty, List<Integer> ubrania, List<Integer> narzedzia, int jedzenie, List<Integer> programy) {
        this.diamenty = diamenty;
        this.ubrania = ubrania;
        this.narzedzia = narzedzia;
        this.jedzenie = jedzenie;
        this.programy = programy;
    }
}
