package BajtTrade.agent.robotnik.praca;

import BajtTrade.agent.robotnik.Robotnik;
import BajtTrade.gielda.Gielda;
import com.squareup.moshi.Json;

public class Okresowy implements StrategiaPracy {

    @Json(name="okresowosc_nauki")
    private final int okresowoscNauki;

    public Okresowy(int okresowoscNauki) {
        this.okresowoscNauki = okresowoscNauki;
    }

    @Override
    public boolean czyPracuje(Robotnik robotnik, Gielda gielda) {
        return gielda.dzien() % okresowoscNauki != 0;
    }
}
