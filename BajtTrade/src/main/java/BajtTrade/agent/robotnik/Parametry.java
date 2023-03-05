package BajtTrade.agent.robotnik;

public class Parametry {

    public static final int DZIENNE_ZUZYCIE_JEDZENIA = 100;
    public static final int DZIENNE_ZUZYCIE_UBRAN = 100;
    public static final int MAX_DNI_BEZ_JEDZENIA = 2;
    public static final int[] KARA_ZA_BRAK_JEDZENIA = new int[] {0, 100, 300};

    public final int karaZaBrakUbran;

    public Parametry(int karaZaBrakUbran) {
        this.karaZaBrakUbran = karaZaBrakUbran;
    }
}
