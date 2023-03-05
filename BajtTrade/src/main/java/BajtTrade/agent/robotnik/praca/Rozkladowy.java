package BajtTrade.agent.robotnik.praca;

import BajtTrade.agent.robotnik.Robotnik;
import BajtTrade.gielda.Gielda;

public class Rozkladowy implements StrategiaPracy {

    public Rozkladowy() {
    }

    @Override
    public boolean czyPracuje(Robotnik robotnik, Gielda gielda) {
        return Math.random() >= 1.0 / (gielda.dzien() + 3);
    }
}
