package BajtTrade.produkt;

import com.squareup.moshi.Json;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum Produkt {

    @Json(name="jedzenie")
    JEDZENIE,
    @Json(name="ubrania")
    UBRANIA,
    @Json(name="narzedzia")
    NARZEDZIA,
    @Json(name="diamenty")
    DIAMENTY,
    @Json(name="programy")
    PROGRAMY;

    public static final List<Produkt> PRODUKTY_GIELDOWE =
            Arrays.stream(Produkt.values()).filter(p -> p != DIAMENTY).collect(Collectors.toUnmodifiableList());
}
