package com.menitimu.rzs.stuff;

import cc.polyfrost.oneconfig.events.event.Stage;
import cc.polyfrost.oneconfig.events.event.TickEvent;
import net.minecraft.client.Minecraft;

public class InGameTimer {
    private static Long gameTick = 0L;
    public static Long getTick(){
        return gameTick;
    }
    public static void proceedTick(TickEvent event){
        Minecraft mc = Minecraft.getMinecraft();
        if(mc == null || mc.theWorld == null)
            return;
        if(event.stage == Stage.END)
            gameTick++;
    }

    public static void clearTick(){
        gameTick = 0L;
    }
}
