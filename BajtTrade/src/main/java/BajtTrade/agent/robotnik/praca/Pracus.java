package BajtTrade.agent.robotnik.praca;

import BajtTrade.agent.robotnik.Robotnik;
import BajtTrade.gielda.Gielda;

public class Pracus implements StrategiaPracy {

    public Pracus() {
    }

    @Override
    public boolean czyPracuje(Robotnik robotnik, Gielda gielda) {
        return true;
    }
}
