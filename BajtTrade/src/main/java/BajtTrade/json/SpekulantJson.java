package BajtTrade.json;

import BajtTrade.agent.spekulant.handel.StrategiaHandlu;

public class SpekulantJson {

    public final int id;
    public final ZasobyJson zasoby;
    public final StrategiaHandlu kariera;

    public SpekulantJson(int id, ZasobyJson zasoby, StrategiaHandlu kariera) {
        this.id = id;
        this.zasoby = zasoby;
        this.kariera = kariera;
    }
}
