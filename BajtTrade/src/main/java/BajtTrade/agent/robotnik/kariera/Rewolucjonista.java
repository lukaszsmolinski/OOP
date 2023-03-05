package BajtTrade.agent.robotnik.kariera;

import BajtTrade.gielda.Gielda;

public class Rewolucjonista extends Kariera {

    private final int id;

    public Rewolucjonista(Kariera.SciezkaKariery sciezkaKariery, int poziom, int id) {
        super(sciezkaKariery, poziom);
        this.id = id;
    }

    @Override
    public void uczSie(Gielda gielda) {
        if (gielda.dzien() % 7 == 0) {
            sciezkaKariery = PREMIE.get(gielda.najczestszyProdukt(Math.max(id % 17, 1)));
        } else {
            awansuj();
        }
    }

    @Override
    public Kariera.Zmiana zmiana() {
        return Zmiana.REWOLUCJONISTA;
    }
}
