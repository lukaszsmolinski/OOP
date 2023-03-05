package BajtTrade;

import BajtTrade.json.moshi.MoshiFactory;
import com.squareup.moshi.Moshi;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {

    public static void main(String[] args) {

        if (args.length != 2) {
            System.out.println("Nieprawidłowa liczba argumentów.");
            return;
        }

        Moshi moshi = MoshiFactory.MOSHI;

        String in;
        try {
            in = Files.readString(Path.of(args[0]));
        } catch (IOException e) {
            System.out.println("Błąd odczytu pliku wejściowego.");
            return;
        }

        Symulacja symulacja;
        try {
            symulacja = moshi.adapter(Symulacja.class).fromJson(in);
        } catch (IOException e) {
            System.out.println("Zawartość pliku wejściowego jest niepoprawna.");
            return;
        }

        symulacja.przeprowadz();

        try {
            String out = moshi.adapter(Symulacja.class).indent("    ").toJson(symulacja);
            Files.write(Path.of(args[1]), out.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            System.out.println("Błąd zapisu do pliku wyjściowego.");
            return;
        }
    }
}