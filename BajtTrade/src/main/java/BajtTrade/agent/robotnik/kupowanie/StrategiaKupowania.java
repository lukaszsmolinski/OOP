package BajtTrade.agent.robotnik.kupowanie;

import BajtTrade.agent.robotnik.Robotnik;
import BajtTrade.gielda.oferta.OfertaKupnaRobotnika;

import java.util.List;

public interface StrategiaKupowania {

    List<OfertaKupnaRobotnika> coKupuje(Robotnik robotnik);
}
