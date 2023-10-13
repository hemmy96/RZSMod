package com.menitimu.rzs.util;

public class Round {

    private final int wavesInRound;
    private final Wave[] waves;
    private int RoundFlags = 0;
    private static final int NOITEM = 1;
    private static final int NO_UFO = 1 << 1;
    private static final int UFO_ONLY = 1 << 2;
    Round(Wave[] waves){
        this.waves = waves;
        wavesInRound = waves.length;
    }
    Round(Wave[] waves, int RoundFlags){
        this.waves = waves;
        wavesInRound = waves.length;
        this.RoundFlags = RoundFlags;
    }
    public int getWavesInRound(){
        return wavesInRound;
    }
    public Wave getWave(int value){
        return waves[value];
    }
    public boolean hasFlag(int flag){
        return (RoundFlags & flag) == flag;
    }
}
