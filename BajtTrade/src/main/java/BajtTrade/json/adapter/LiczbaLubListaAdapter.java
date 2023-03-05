package BajtTrade.json.adapter;

import BajtTrade.json.adnotacja.LiczbaLubLista;
import com.squareup.moshi.FromJson;
import com.squareup.moshi.JsonWriter;
import com.squareup.moshi.ToJson;

import java.io.IOException;
import java.util.List;

/* Wczytuje liczbę, ale wypisuje listę. */
public class LiczbaLubListaAdapter {

    @ToJson
    public void toJson(JsonWriter jsonWriter, @LiczbaLubLista List<Integer> list) throws IOException {
        jsonWriter.jsonValue(list);
    }

    @FromJson
    public @LiczbaLubLista List<Integer> fromJson(int liczba) {
        return List.of(liczba);
    }
}
