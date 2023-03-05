package BajtTrade.agent.robotnik.praca;

import BajtTrade.agent.robotnik.Robotnik;
import BajtTrade.gielda.Gielda;
import com.squareup.moshi.Json;

public class Oszczedny implements StrategiaPracy {

    @Json(name="limit_diamentow")
    private final double limitDiamentow;

    public Oszczedny(double limitDiamentow) {
        this.limitDiamentow = limitDiamentow;
    }

    @Override
    public boolean czyPracuje(Robotnik robotnik, Gielda gielda) {
        return robotnik.diamenty() < limitDiamentow;
    }
}
