package BajtTrade.json.moshi;

import BajtTrade.agent.robotnik.kupowanie.*;
import BajtTrade.agent.robotnik.praca.*;
import BajtTrade.agent.robotnik.produkcja.*;
import BajtTrade.agent.spekulant.handel.RegulujacyRynek;
import BajtTrade.agent.spekulant.handel.Sredni;
import BajtTrade.agent.spekulant.handel.StrategiaHandlu;
import BajtTrade.agent.spekulant.handel.Wypukly;
import BajtTrade.json.adapter.*;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory;

public abstract class MoshiFactory {

    public static Moshi MOSHI = new Moshi.Builder()
            .add(new LiczbaLubListaAdapter())
            .add(new RobotnikAdapter())
            .add(new SpekulantAdapter())
            .add(new SymulacjaAdapter())
            .add(StrategiaAdapter.stworz(StrategiaHandlu.class))
            .add(StrategiaAdapter.stworz(StrategiaKupowania.class))
            .add(StrategiaAdapter.stworz(StrategiaProdukcji.class))
            .add(StrategiaAdapter.stworz(StrategiaPracy.class))
            .add(
                PolymorphicJsonAdapterFactory.of(StrategiaKupowania.class, "typ")
                        .withSubtype(Czyscioszek.class, "czyscioszek")
                        .withSubtype(Gadzeciarz.class, "gadzeciarz")
                        .withSubtype(Technofob.class, "technofob")
                        .withSubtype(Zmechanizowany.class, "zmechanizowany"))
            .add(
                PolymorphicJsonAdapterFactory.of(StrategiaPracy.class, "typ")
                        .withSubtype(Okresowy.class, "okresowy")
                        .withSubtype(Oszczedny.class, "oszczedny")
                        .withSubtype(Pracus.class, "pracus")
                        .withSubtype(Rozkladowy.class, "rozkladowy")
                        .withSubtype(Student.class, "student"))
            .add(
                PolymorphicJsonAdapterFactory.of(StrategiaProdukcji.class, "typ")
                        .withSubtype(Chciwy.class, "chciwy")
                        .withSubtype(Krotkowzroczny.class, "krotkowzroczny")
                        .withSubtype(Losowy.class, "losowy")
                        .withSubtype(Perspektywiczny.class, "perspektywiczny")
                        .withSubtype(Sredniak.class, "sredniak"))
            .add(
                PolymorphicJsonAdapterFactory.of(StrategiaHandlu.class, "typ")
                        .withSubtype(RegulujacyRynek.class, "regulujacy_rynek")
                        .withSubtype(Sredni.class, "sredni")
                        .withSubtype(Wypukly.class, "wypukly"))
            .build();
}
