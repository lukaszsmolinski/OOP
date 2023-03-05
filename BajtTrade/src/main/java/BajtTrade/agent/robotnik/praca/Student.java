package BajtTrade.agent.robotnik.praca;

import BajtTrade.agent.robotnik.Robotnik;
import BajtTrade.gielda.Gielda;
import BajtTrade.produkt.Produkt;

public class Student implements StrategiaPracy {

    private final int zapas;
    private final int okres;

    public Student(int zapas, int okres) {
        this.zapas = zapas;
        this.okres = okres;
    }

    @Override
    public boolean czyPracuje(Robotnik robotnik, Gielda gielda) {
        double sredniaCena = 0;
        int dni = Math.min(okres, gielda.dzien());
        for (int i = 1; i <= dni; ++i) {
            sredniaCena += gielda.sredniaCena(Produkt.JEDZENIE, i) / dni;
        }
        return robotnik.diamenty() < sredniaCena * zapas * 100;
    }
}
