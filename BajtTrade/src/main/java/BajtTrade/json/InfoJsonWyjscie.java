package BajtTrade.json;

import BajtTrade.produkt.Produkt;

import java.util.Map;

public class InfoJsonWyjscie {

    public final int dzien;
    public final Map<Produkt, Double> ceny_srednie;
    public final Map<Produkt, Double> ceny_max;
    public final Map<Produkt, Double> ceny_min;

    public InfoJsonWyjscie(int dzien, Map<Produkt, Double> ceny_srednie, Map<Produkt, Double> ceny_max, Map<Produkt, Double> ceny_min) {
        this.dzien = dzien;
        this.ceny_srednie = ceny_srednie;
        this.ceny_max = ceny_max;
        this.ceny_min = ceny_min;
    }
}
