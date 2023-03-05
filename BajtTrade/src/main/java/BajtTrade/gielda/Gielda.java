package BajtTrade.gielda;

import BajtTrade.agent.Agent;
import BajtTrade.gielda.oferta.Oferta;
import BajtTrade.gielda.oferta.OfertaKupnaRobotnika;
import BajtTrade.gielda.oferta.OfertaSpekulanta;
import BajtTrade.gielda.oferta.OfertaSprzedazyRobotnika;
import BajtTrade.json.InfoJsonWyjscie;
import BajtTrade.produkt.Produkt;
import com.squareup.moshi.Json;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public abstract class Gielda {

    public enum TypGieldy {
        @Json(name = "kapitalistyczna")
        KAPITALISTYCZNA,
        @Json(name = "socjalistyczna")
        SOCJALISTYCZNA,
        @Json(name = "zrownowazona")
        ZROWNOWAZONA
    }

    private static final Comparator<OfertaSpekulanta> COMPARATOR_OFERT_KUPNA_SPEKULANTA =
            Comparator.comparing(OfertaSpekulanta::produkt)
                    .thenComparing(OfertaSpekulanta::poziomProduktu)
                    .thenComparing(OfertaSpekulanta::cena, Comparator.reverseOrder());

    private static final Comparator<OfertaSpekulanta> COMPARATOR_OFERT_SPRZEDAZY_SPEKULANTA =
            Comparator.comparing(OfertaSpekulanta::produkt)
                    .thenComparing(OfertaSpekulanta::poziomProduktu, Comparator.reverseOrder())
                    .thenComparing(OfertaSpekulanta::cena);

    protected int dzien;
    protected Map<Produkt, Double> cenyDniaZerowego;
    protected Map<Agent, List<OfertaKupnaRobotnika>> ofertyKupnaRobotnikow;
    protected Map<Agent, List<OfertaSprzedazyRobotnika>> ofertySprzedazyRobotnikow;
    protected NavigableSet<OfertaSpekulanta> ofertyKupnaSpekulantow;
    protected NavigableSet<OfertaSpekulanta> ofertySprzedazySpekulantow;
    protected List<Statystyki> statystyki;

    public Gielda(Map<Produkt, Double> cenyDniaZerowego) {
        this.dzien = 0;
        this.cenyDniaZerowego = new EnumMap<>(Produkt.class);
        this.cenyDniaZerowego.putAll(cenyDniaZerowego);
        this.ofertySprzedazyRobotnikow = new HashMap<>();
        this.ofertyKupnaRobotnikow = new HashMap<>();
        this.ofertySprzedazySpekulantow = new TreeSet<>(COMPARATOR_OFERT_SPRZEDAZY_SPEKULANTA);
        this.ofertyKupnaSpekulantow = new TreeSet<>(COMPARATOR_OFERT_KUPNA_SPEKULANTA);
        this.statystyki = new ArrayList<>(List.of(new Statystyki()));
        this.statystyki.get(0).wypelnijPustePola(cenyDniaZerowego);
    }

    public int dzien() {
        return dzien;
    }

    public int najwyzszyDostepnyPoziom(Produkt produkt) {
        return statystyki.get(dzien).najwyzszyPoziom(produkt);
    }

    public void rozpocznijNowyDzien() {
        ++dzien;
        statystyki.add(new Statystyki());
    }

    public void zrealizujOferty() {
        dopasujOferty(kolejnoscRobotnikow());

        wykupProduktyRobotnikow();
        zwrocProduktySpekulantow();

        ofertyKupnaRobotnikow.clear();
        ofertyKupnaSpekulantow.clear();

        statystyki.get(dzien).wypelnijPustePola(cenyDniaZerowego);
    }

    public void dodajOferteSprzedazy(OfertaSprzedazyRobotnika oferta) {
        ofertySprzedazyRobotnikow.computeIfAbsent(oferta.wlasciciel(), k -> new ArrayList<>()).add(oferta);
        statystyki.get(dzien).wystawionoOferteSprzedazy(oferta);
    }

    public void dodajOferteKupna(OfertaKupnaRobotnika oferta) {
        ofertyKupnaRobotnikow.computeIfAbsent(oferta.wlasciciel(), k -> new ArrayList<>()).add(oferta);
    }

    public void dodajOferteSprzedazy(OfertaSpekulanta oferta) {
        ofertySprzedazySpekulantow.add(oferta);
        statystyki.get(dzien).wystawionoOferteSprzedazy(oferta);
    }

    public void dodajOferteKupna(OfertaSpekulanta oferta) {
        ofertyKupnaSpekulantow.add(oferta);
    }

    public int liczbaWystawionych(Produkt produkt, int liczbaDni) {
        return IntStream.range(Math.max(0, dzien - liczbaDni), dzien)
                .map(i -> statystyki.get(i).liczbaWystawionych(produkt))
                .sum();
    }

    public Produkt najczestszyProdukt(int liczbaDni) {
        int dni = Math.min(dzien, liczbaDni);
        return Produkt.PRODUKTY_GIELDOWE.stream()
                .reduce((p1, p2) -> liczbaWystawionych(p1, dni) > liczbaWystawionych(p2, dni) ? p1 : p2)
                .orElseThrow();
    }

    public double cenaMinimalna(Produkt produkt, int liczbaDni) {
        return IntStream.range(Math.max(dzien - liczbaDni, 0), dzien)
                .mapToDouble(i -> statystyki.get(i).najnizszaCena(produkt))
                .min().orElseThrow();
    }

    public double sredniaCena(Produkt produkt, int dniTemu) {
        return statystyki.get(Math.max(dzien - dniTemu, 0)).sredniaCena(produkt);
    }

    public int wystawionePrzedmioty(Produkt produkt, int dniTemu) {
        return statystyki.get(Math.max(dzien - dniTemu, 0)).liczbaWystawionych(produkt);
    }

    public InfoJsonWyjscie dzienJson() {
        return statystyki.get(dzien).json(dzien);
    }

    private void wykupProduktyRobotnikow() {
        List<Oferta> oferty = ofertySprzedazyRobotnikow.values().stream()
                .flatMap(List::stream).collect(Collectors.toList());
        for (Oferta ofertaSprzedazy : oferty) {
            double cena = cenaMinimalna(ofertaSprzedazy.produkt(), 1);
            double zysk = ofertaSprzedazy.liczbaProduktow() * cena;
            ofertaSprzedazy.wlasciciel().dodajDiamenty(zysk);
        }
        ofertySprzedazyRobotnikow.clear();
    }

    private void zwrocProduktySpekulantow() {
        for (OfertaSpekulanta oferta : ofertySprzedazySpekulantow) {
            oferta.wlasciciel().dodajProdukt(oferta.produkt(),
                    oferta.poziomProduktu(), oferta.liczbaProduktow());
        }
        ofertySprzedazySpekulantow.clear();
    }

    private Set<OfertaSpekulanta> pasujaceOferty(OfertaSprzedazyRobotnika oferta) {
        OfertaSpekulanta lewo = new OfertaSpekulanta(null, oferta.produkt(), 0, oferta.poziomProduktu(), Integer.MAX_VALUE);
        OfertaSpekulanta prawo = new OfertaSpekulanta(null, oferta.produkt(), 0, oferta.poziomProduktu(), Integer.MIN_VALUE);
        return ofertyKupnaSpekulantow.subSet(lewo, prawo);
    }

    private Set<OfertaSpekulanta> pasujaceOferty(OfertaKupnaRobotnika oferta) {
        OfertaSpekulanta lewo = new OfertaSpekulanta(null, oferta.produkt(), 0, Integer.MAX_VALUE, Integer.MIN_VALUE);
        OfertaSpekulanta prawo = new OfertaSpekulanta(null, oferta.produkt(), 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
        return ofertySprzedazySpekulantow.subSet(lewo, prawo);
    }

    private void wykonajTransakcje(Oferta kupno, Oferta sprzedaz, int liczba, int poziom, double cena) {
        kupno.zmniejszLiczbe(liczba);
        sprzedaz.zmniejszLiczbe(liczba);

        kupno.wlasciciel().dodajProdukt(sprzedaz.produkt(), poziom, liczba);
        kupno.wlasciciel().dodajDiamenty(-liczba * cena);

        sprzedaz.wlasciciel().dodajDiamenty(liczba * cena);

        statystyki.get(dzien).sprzedanoProdukt(kupno.produkt(), liczba, cena);
    }

    private void dopasujOferty(List<Agent> kolejnoscRobotnikow) {
        for (Agent robotnik : kolejnoscRobotnikow) {
            List<OfertaSprzedazyRobotnika> ofertySprzedazy =
                    ofertySprzedazyRobotnikow.getOrDefault(robotnik, new ArrayList<>());
            List<OfertaKupnaRobotnika> ofertyKupna =
                    ofertyKupnaRobotnikow.getOrDefault(robotnik, new ArrayList<>());

            ofertyKupna.sort(Comparator.comparing(Oferta::produkt));
            ofertySprzedazy.sort(Comparator.comparing(Oferta::produkt));

            for (OfertaSprzedazyRobotnika sprzedaz : ofertySprzedazy) {
                for (OfertaSpekulanta kupno : pasujaceOferty(sprzedaz)) {
                    int liczba = Math.min(kupno.liczbaProduktow(), sprzedaz.liczbaProduktow());
                    liczba = Math.min(liczba, (int) (kupno.wlasciciel().diamenty() / kupno.cena()));

                    if (liczba > 0) {
                        wykonajTransakcje(kupno, sprzedaz, liczba, kupno.poziomProduktu(), kupno.cena());
                    }

                    if (sprzedaz.liczbaProduktow() == 0) {
                        break;
                    }
                }
            }

            for (OfertaKupnaRobotnika kupno : ofertyKupna) {
                for (OfertaSpekulanta sprzedaz : pasujaceOferty(kupno)) {
                    int liczba = Math.min(kupno.liczbaProduktow(), sprzedaz.liczbaProduktow());
                    liczba = Math.min(liczba, (int) (kupno.wlasciciel().diamenty() / sprzedaz.cena()));

                    if (liczba > 0) {
                        wykonajTransakcje(kupno, sprzedaz, liczba, sprzedaz.poziomProduktu(), sprzedaz.cena());
                    }

                    if (kupno.liczbaProduktow() == 0) {
                        break;
                    }
                }
            }
        }
    }

    protected abstract List<Agent> kolejnoscRobotnikow();

}
