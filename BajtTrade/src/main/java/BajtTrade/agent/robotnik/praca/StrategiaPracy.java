package BajtTrade.agent.robotnik.praca;

import BajtTrade.agent.robotnik.Robotnik;
import BajtTrade.gielda.Gielda;

public interface StrategiaPracy {

    boolean czyPracuje(Robotnik robotnik, Gielda gielda);
}
