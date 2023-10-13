package com.menitimu.rzs.util;

public class Wave {

    private final int delay;
    private final String time;
    private int WaveFlags = 0;
    private static final int OLD = 1;
    private static final int GIANT = 1 << 1;
    private static final int CUBE = 1 << 2;
    private static final int SLIME = 1 << 3;
    private static final int BLAZE = 1 << 4;
    private static final int GOLEM = 1 << 5;
    private static final int BOSS = 1 << 6;
    private static final int GHAST = 1 << 7;

    Wave(int delay){
        this.delay = delay;
        this.time = String.format("00:%02d", delay / 20);
    }

    Wave(int delay, int waveType){
        WaveFlags |= waveType;
        this.time = String.format("00:%02d", delay / 20);
        this.delay = delay;
    }

    public int getDelay(){
        return delay;
    }

    public boolean hasFlag(int flag){
        return (WaveFlags & flag) == flag;
    }

    public String getTime() {
        return time;
    }
}
