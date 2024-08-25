package net.licketysplitter.maplecraft.entity.client;

import java.util.Arrays;
import java.util.Comparator;

public enum DeerVariant {
    DOE(0),
    BUCK(1);

    private static DeerVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(DeerVariant::getID)).toArray(DeerVariant[]::new);

    private final int id;

    DeerVariant(int id){
        this.id = id;
    }

    public int getID(){
        return this.id;
    }

    public static DeerVariant byID(int id){
        return BY_ID[id % BY_ID.length];
    }
}
