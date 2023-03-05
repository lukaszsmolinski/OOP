package BajtTrade.json.adapter;

import com.squareup.moshi.*;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Set;

/* Pozwala wczytywać strategie bezparametrowe, podając tylko napis zamiast słownika. */
public class StrategiaAdapter<T> extends JsonAdapter<T> {

    private final JsonAdapter<T> adapter;

    public StrategiaAdapter(JsonAdapter<T> adapter) {
        this.adapter = adapter;
    }

    @ToJson
    @Override
    public void toJson(JsonWriter jsonWriter, T strategia) throws IOException {
        adapter.toJson(jsonWriter, strategia);
    }

    @FromJson
    @Override
    public T fromJson(JsonReader jsonReader) throws IOException {
        if (jsonReader.peek() == JsonReader.Token.BEGIN_OBJECT) {
            return adapter.fromJson(jsonReader);
        } else {
            return adapter.fromJson("{ \"typ\": \"" + jsonReader.nextString() + "\" }");
        }
    }

    public static <T> JsonAdapter.Factory stworz(Class<T> typ) {
        return new JsonAdapter.Factory() {
            @Override
            public @Nullable JsonAdapter<?> create(Type oczekiwanyTyp, Set<? extends Annotation> adnotacje, Moshi moshi) {
                if (typ != oczekiwanyTyp) {
                    return null;
                }
                JsonAdapter<T> adapter = moshi.nextAdapter(this, typ, adnotacje);
                return new StrategiaAdapter<>(adapter);
            }
        };
    }
}
