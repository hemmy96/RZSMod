package com.menitimu.rzs.hud;

import cc.polyfrost.oneconfig.config.annotations.Color;
import cc.polyfrost.oneconfig.config.annotations.Exclude;
import cc.polyfrost.oneconfig.config.core.OneColor;
import cc.polyfrost.oneconfig.hud.Hud;
import cc.polyfrost.oneconfig.libs.universal.UMatrixStack;
import cc.polyfrost.oneconfig.platform.Platform;
import cc.polyfrost.oneconfig.renderer.TextRenderer;
import com.menitimu.rzs.stuff.RoundStuff;
import com.menitimu.rzs.util.RandomStuff;

public class RoundTimerHud extends Hud {
    @Exclude
    @Color(name = "Round Timer Color", category = "Time Stuffs")
    protected OneColor color = new OneColor(255, 255, 255);
    public RoundTimerHud(){
        position.setSize(getWidth(scale, true), getHeight(scale, true));
    }
    protected String getText(boolean example){
        if(example)
            return "0:00:0§r";
        return String.format("%d:%02d:%d§r", RoundStuff.getRoundTick() / 1200, (RoundStuff.getRoundTick() / 20 )% 60, (RoundStuff.getRoundTick() % 20) / 2);
    }

    @Override
    protected void draw(UMatrixStack matrices, float x, float y, float scale, boolean example) {
        if(RandomStuff.onGame || example)
            TextRenderer.drawScaledString(getText(example), x, y, color.getRGB(), TextRenderer.TextType.toType(1), scale);
    }

    @Override
    protected float getWidth(float scale, boolean example) {
        return (Platform.getGLPlatform().getStringWidth(getText(example)) * scale);
    }

    @Override
    protected float getHeight(float scale, boolean example) {
        return (8 * scale);
    }

}
