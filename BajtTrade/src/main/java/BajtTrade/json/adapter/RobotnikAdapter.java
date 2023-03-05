package BajtTrade.json.adapter;

import BajtTrade.agent.robotnik.kariera.Kariera;
import BajtTrade.agent.robotnik.kariera.Konserwatysta;
import BajtTrade.agent.robotnik.kariera.Rewolucjonista;
import BajtTrade.agent.robotnik.Robotnik;
import BajtTrade.json.RobotnikJson;
import com.squareup.moshi.FromJson;
import com.squareup.moshi.ToJson;

public class RobotnikAdapter {

    @ToJson
    public RobotnikJson toJson(Robotnik robotnik) {
        return robotnik.json();
    }

    @FromJson
    public Robotnik fromJson(RobotnikJson robotnikJson) {
        Kariera kariera;
        switch (robotnikJson.zmiana) {
            case REWOLUCJONISTA:
                kariera = new Rewolucjonista(robotnikJson.kariera, robotnikJson.poziom, robotnikJson.id);
                break;
            case KONSERWATYSTA:
                kariera = new Konserwatysta(robotnikJson.kariera, robotnikJson.poziom);
                break;
            default:
                throw new IllegalStateException("Niespodziewana wartość: zmiana: " + robotnikJson.zmiana);
        }
        return new Robotnik(robotnikJson.id, robotnikJson.zasoby,
                robotnikJson.uczenie, robotnikJson.kupowanie, robotnikJson.produkcja,
                kariera, robotnikJson.produktywnosc);
    }
}
