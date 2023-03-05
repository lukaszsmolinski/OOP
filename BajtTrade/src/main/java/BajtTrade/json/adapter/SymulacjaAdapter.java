package BajtTrade.json.adapter;

import BajtTrade.json.moshi.MoshiFactory;
import BajtTrade.agent.robotnik.Parametry;
import BajtTrade.agent.robotnik.Robotnik;
import BajtTrade.gielda.Gielda;
import BajtTrade.gielda.GieldaKapitalistyczna;
import BajtTrade.gielda.GieldaSocjalistyczna;
import BajtTrade.gielda.GieldaZrownowazona;
import BajtTrade.json.DziennyRaportJson;
import BajtTrade.json.WejscieJson;
import BajtTrade.Symulacja;
import com.squareup.moshi.FromJson;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.JsonWriter;
import com.squareup.moshi.ToJson;

import java.io.IOException;

public class SymulacjaAdapter {

    @ToJson
    public void toJson(JsonWriter jsonWriter, Symulacja symulacja) throws IOException {
        JsonAdapter<DziennyRaportJson> adapter = MoshiFactory.MOSHI.adapter(DziennyRaportJson.class);
        jsonWriter.beginArray();
        for (DziennyRaportJson raport : symulacja.podsumowanie()) {
            jsonWriter.jsonValue(adapter.toJsonValue(raport));
        }
        jsonWriter.endArray();
    }

    @FromJson
    public Symulacja fromJson(WejscieJson wejscie) {
        Gielda gielda;
        switch (wejscie.info.gielda) {
            case SOCJALISTYCZNA:
                gielda = new GieldaSocjalistyczna(wejscie.info.ceny);
                break;
            case KAPITALISTYCZNA:
                gielda = new GieldaKapitalistyczna(wejscie.info.ceny);
                break;
            case ZROWNOWAZONA:
                gielda = new GieldaZrownowazona(wejscie.info.ceny);
                break;
            default:
                throw new IllegalStateException("Niespodziewana wartość: gielda: " + wejscie.info.gielda);
        }

        Parametry parametry = new Parametry(wejscie.info.kara_za_brak_ubran);
        for (Robotnik robotnik : wejscie.robotnicy) {
            robotnik.ustawParametry(parametry);
        }

        return new Symulacja(wejscie.info.dlugosc, wejscie.robotnicy, wejscie.spekulanci, gielda);
    }
}
