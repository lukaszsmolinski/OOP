package BajtTrade.json;


import java.util.List;

public class DziennyRaportJson {

    public final InfoJsonWyjscie info;
    public final List<RobotnikJson> robotnicy;
    public final List<SpekulantJson> spekulanci;

    public DziennyRaportJson(InfoJsonWyjscie info, List<RobotnikJson> robotnicy, List<SpekulantJson> spekulanci) {
        this.info = info;
        this.robotnicy = robotnicy;
        this.spekulanci = spekulanci;
    }
}
