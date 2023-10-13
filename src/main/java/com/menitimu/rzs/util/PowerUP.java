package com.menitimu.rzs.util;

public class PowerUP {
    private final PowerUpType type;
    private final long Time;
    public PowerUP(PowerUpType type, long Time){
        this.type = type;
        this.Time = Time;
    }
    public PowerUpType getType(){
        return this.type;
    }
    public long getTime(){
        return this.Time;
    }
    public enum PowerUpType{
        INSTA,
        AMMO,
        DG,
        SPREE,
        CARP,
        BONUS
    }
}
