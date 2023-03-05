package BajtTrade.json;

import BajtTrade.agent.robotnik.Robotnik;
import BajtTrade.agent.spekulant.Spekulant;

import java.util.List;

public class WejscieJson {

    public final InfoJsonWejscie info;
    public final List<Robotnik> robotnicy;
    public final List<Spekulant> spekulanci;

    public WejscieJson(InfoJsonWejscie info, List<Robotnik> robotnicy, List<Spekulant> spekulanci) {
        this.info = info;
        this.robotnicy = robotnicy;
        this.spekulanci = spekulanci;
    }
}
