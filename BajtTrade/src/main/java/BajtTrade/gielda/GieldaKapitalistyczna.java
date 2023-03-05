package BajtTrade.gielda;

import BajtTrade.agent.Agent;
import BajtTrade.produkt.Produkt;

import java.util.*;

public class GieldaKapitalistyczna extends Gielda {

    public static final Comparator<Agent> KOLEJNOSC_ROBOTNIKOW =
            Comparator.comparing(Agent::diamenty).thenComparing(Agent::id).reversed();

    public GieldaKapitalistyczna(Map<Produkt, Double> cenyDniaZerowego) {
        super(cenyDniaZerowego);
    }

    @Override
    protected List<Agent> kolejnoscRobotnikow() {
        SortedSet<Agent> set = new TreeSet<>(KOLEJNOSC_ROBOTNIKOW);
        set.addAll(ofertyKupnaRobotnikow.keySet());
        set.addAll(ofertySprzedazyRobotnikow.keySet());
        return new ArrayList<>(set);
    }
}
