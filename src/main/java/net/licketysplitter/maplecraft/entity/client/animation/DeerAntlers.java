package net.licketysplitter.maplecraft.entity.client.animation;

import java.util.Arrays;
import java.util.Comparator;

public enum DeerAntlers {
    ZERO(0),
    TWO(1),
    FOUR(2),
    SIX(3),
    EIGHT(4);

    private static DeerAntlers[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(DeerAntlers::getID)).toArray(DeerAntlers[]::new);

    private final int id;

    DeerAntlers(int id){
        this.id = id;
    }

    public int getID(){
        return this.id;
    }

    public static DeerAntlers byID(int id){
        return BY_ID[id % BY_ID.length];
    }
}
