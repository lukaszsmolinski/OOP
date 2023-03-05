package BajtTrade.json.adapter;

import BajtTrade.agent.spekulant.Spekulant;
import BajtTrade.json.SpekulantJson;
import com.squareup.moshi.FromJson;
import com.squareup.moshi.ToJson;

public class SpekulantAdapter {

    @ToJson
    public SpekulantJson toJson(Spekulant spekulant) {
        return spekulant.json();
    }

    @FromJson
    public Spekulant fromJson(SpekulantJson spekulantJson) {
        return new Spekulant(spekulantJson.id, spekulantJson.zasoby, spekulantJson.kariera);
    }
}
