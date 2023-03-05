package BajtTrade.agent.robotnik.kariera;

import BajtTrade.gielda.Gielda;

public class Konserwatysta extends Kariera {

    public Konserwatysta(SciezkaKariery sciezkaKariery, int poziom) {
        super(sciezkaKariery, poziom);
    }

    @Override
    public void uczSie(Gielda gielda) {
        awansuj();
    }

    @Override
    public Kariera.Zmiana zmiana() {
        return Zmiana.KONSERWATYSTA;
    }
}
