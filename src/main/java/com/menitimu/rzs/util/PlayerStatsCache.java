package com.menitimu.rzs.util;

import java.time.Duration;
import java.time.Instant;

public class PlayerStatsCache {
    private final String[] stats;
    private final Instant createdTime;
    private final Duration expireDuration;
    public PlayerStatsCache(String[] stats, Instant createdTime, Duration expireDuration){
        this.stats = stats;
        this.createdTime = createdTime;
        this.expireDuration = expireDuration;
    }
    public String[] getStats(){
        return this.stats;
    }
    public Instant getCreatedTime(){
        return this.createdTime;
    }
    public Duration getExpireDuration(){
        return this.expireDuration;
    }
}
