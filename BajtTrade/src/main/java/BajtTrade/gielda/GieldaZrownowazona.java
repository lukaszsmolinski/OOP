package BajtTrade.gielda;

import BajtTrade.agent.Agent;
import BajtTrade.produkt.Produkt;

import java.util.*;

public class GieldaZrownowazona extends Gielda {

    public GieldaZrownowazona(Map<Produkt, Double> cenyDniaZerowego) {
        super(cenyDniaZerowego);
    }

    @Override
    protected List<Agent> kolejnoscRobotnikow() {
        Comparator<Agent> kolejnosc = dzien % 2 == 0
                ? GieldaKapitalistyczna.KOLEJNOSC_ROBOTNIKOW
                : GieldaSocjalistyczna.KOLEJNOSC_ROBOTNIKOW;

        SortedSet<Agent> set = new TreeSet<>(kolejnosc);
        set.addAll(ofertyKupnaRobotnikow.keySet());
        set.addAll(ofertySprzedazyRobotnikow.keySet());
        return new ArrayList<>(set);
    }
}
