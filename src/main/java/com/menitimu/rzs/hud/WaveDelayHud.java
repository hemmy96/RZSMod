package com.menitimu.rzs.hud;

import cc.polyfrost.oneconfig.config.annotations.Color;
import cc.polyfrost.oneconfig.config.annotations.Exclude;
import cc.polyfrost.oneconfig.config.annotations.Switch;
import cc.polyfrost.oneconfig.config.core.OneColor;
import cc.polyfrost.oneconfig.hud.Hud;
import cc.polyfrost.oneconfig.libs.universal.UMatrixStack;
import cc.polyfrost.oneconfig.platform.Platform;
import cc.polyfrost.oneconfig.renderer.TextRenderer;
import com.menitimu.rzs.stuff.RoundStuff;
import com.menitimu.rzs.util.RandomStuff;

public class WaveDelayHud extends Hud {
    @Switch(name = "Boss Alert", category = "Time Stuffs", size = 2)
    public static boolean BossColor = true;
    @Color(name = "Default On Color", category = "Time Stuffs")
    public static OneColor defaultOn = new OneColor(255, 255, 0);
    @Color(name = "Default Off Color", category = "Time Stuffs")
    public static OneColor defaultOff = new OneColor(128, 128, 128);
    @Color(name = "Old On Color", category = "Time Stuffs")
    public static OneColor oldOn = new OneColor(0, 255, 0);
    @Color(name = "Old Off Color", category = "Time Stuffs")
    public static OneColor oldOff = new OneColor(0, 102, 102);
    @Color(name = "Giant On Color", category = "Time Stuffs")
    public static OneColor giantOn = new OneColor(0, 153, 255);
    @Color(name = "Giant Off Color", category = "Time Stuffs")
    public static OneColor giantOff = new OneColor(102, 51, 153);
    @Color(name = "Giant and Old On Color", category = "Time Stuffs")
    public static OneColor OldGiantOn = new OneColor(255, 0, 0);
    @Color(name = "Giant and Old Off Color", category = "Time Stuffs")
    public static OneColor OldGiantOff = new OneColor(120, 51, 0);
    @Switch(name = "Show Arrow", category = "Time Stuffs")
    public static boolean renderArrow = true;
    @Color(name = "Arrow Color", category = "Time Stuffs")
    public static OneColor ArrowColor = new OneColor(204, 0, 204);
    @Exclude
    private float ArrowWidth;
    @Exclude
    private String[] WaveTexts;
    @Exclude
    private OneColor[] enableColor;
    @Exclude
    private OneColor[] disableColor;
    public WaveDelayHud(){
        getWaveData(true);
        position.setSize(getWidth(scale, true), getHeight(scale, true));
    }
    protected void getWaveData(boolean example){
        if(example){
            WaveTexts = new String[]{
                    "➤ W1 00:10§r",
                    "➤ W2 00:20§r",
                    "➤ W3 00:30§r",
                    "➤ W4 00:40§r",
                    "➤ W5 00:50§r",
                    "➤ W6 01:00§r"
            };
            enableColor = new OneColor[]{
                    defaultOff,
                    defaultOff,
                    defaultOff,
                    defaultOff,
                    defaultOff,
                    defaultOff
            };
            disableColor = new OneColor[]{
                    defaultOff,
                    defaultOff,
                    defaultOff,
                    defaultOff,
                    defaultOff,
                    defaultOff
            };
            return;
        }
        WaveTexts = RoundStuff.getWaveText();
        enableColor = RoundStuff.getEnableColor();
        disableColor = RoundStuff.getDisableColor();
    }
    @Override
    protected void draw(UMatrixStack matrices, float x, float y, float scale, boolean example) {
        getWaveData(example);
        if(WaveTexts == null || (!RandomStuff.onGame && !example))
            return;
        float textY = y;
        int wave = RoundStuff.getWave();
        for(int i = 0; i < WaveTexts.length; i++){
            if(wave == i) {
                if(renderArrow && !example)
                    TextRenderer.drawScaledString("➤ ", x, textY, ArrowColor.getRGB(), TextRenderer.TextType.toType(1), scale);
                TextRenderer.drawScaledString(WaveTexts[i], example ? x : x + ArrowWidth, textY, enableColor[i].getRGB(), TextRenderer.TextType.toType(1), scale);
            } else
                TextRenderer.drawScaledString(WaveTexts[i], example ? x : x + ArrowWidth, textY, disableColor[i].getRGB(), TextRenderer.TextType.toType(1), scale);
            textY += 8 * scale;
        }
        TextRenderer.drawScaledString("", x, y, 0xffffff, TextRenderer.TextType.NONE, scale);
    }
    @Override
    protected float getWidth(float scale, boolean example) {
        if (WaveTexts == null) return 0;
        ArrowWidth = Platform.getGLPlatform().getStringWidth("➤ ") * scale;
        float width = 0;
        for (String line : WaveTexts) {
            width = Math.max(width, (Platform.getGLPlatform().getStringWidth(line) * scale));
        }
        return example ? width : ArrowWidth + width;
    }
    @Override
    protected float getHeight(float scale, boolean example) {
        return WaveTexts == null ? 0 : (WaveTexts.length * 8) * scale;
    }
}
