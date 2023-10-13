package com.menitimu.rzs.stuff;

import cc.polyfrost.oneconfig.platform.Platform;
import cc.polyfrost.oneconfig.renderer.TextRenderer;
import com.menitimu.rzs.config.RZSConfig;
import com.menitimu.rzs.util.RandomStuff;
import net.minecraft.client.Minecraft;

import java.util.List;

public class AlertStuff {
    public static String alertText = "";
    public static int alertPriority = 0;
    public static int alertDisplayTime = 0;
    public static void onInit(){
        alertText = "";
        alertPriority = 0;
        alertDisplayTime = 0;
    }
    public static void pushAlert(String text, int priority){
        if(priority > alertPriority) {
            Minecraft.getMinecraft().thePlayer.playSound("random.orb", 2.5F, 0.5F);
            alertDisplayTime = 40;
            alertPriority = priority;
            alertText = text;
        }
    }
    public static void onRender(int screenWidth, int screenHeight){
        if(alertDisplayTime > 0 && RandomStuff.onGame)
            TextRenderer.drawScaledString(alertText, (float) (screenWidth - Platform.getGLPlatform().getStringWidth(alertText) * 4) / 2, (float) screenHeight / 2 - 74, 0xFFFFFF, TextRenderer.TextType.SHADOW, 4F);
    }
    public static void onTick(){
        if(alertDisplayTime > 0) {
            alertDisplayTime--;
            if(alertDisplayTime == 0)
                alertPriority = 0;
        }
        if(RandomStuff.isSBLoaded && RZSConfig.threeDown){
            int i = 0;
            List<String> lines = RandomStuff.getScoreBoard();
            for (int j = 5; j < 9; j++){
                if(lines.get(j).endsWith("E"))
                    i++;
            }
            if(i == RodOrder.players - 1 && RodOrder.players != 1)
                pushAlert("§c3 DOWN§r", 3);
        }
    }
}
