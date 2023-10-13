package com.menitimu.rzs.hud;

import cc.polyfrost.oneconfig.config.annotations.Exclude;
import cc.polyfrost.oneconfig.config.core.OneColor;
import cc.polyfrost.oneconfig.hud.Hud;
import cc.polyfrost.oneconfig.libs.universal.UMatrixStack;
import cc.polyfrost.oneconfig.platform.Platform;
import cc.polyfrost.oneconfig.renderer.TextRenderer;
import com.menitimu.rzs.config.RZSConfig;
import com.menitimu.rzs.stuff.InGameTimer;
import com.menitimu.rzs.stuff.ItemStuff;
import com.menitimu.rzs.util.RandomStuff;
import net.minecraft.util.ResourceLocation;

public class ItemActiveTimerHud extends Hud {
    @Exclude
    private static OneColor color = new OneColor(255, 255, 255);
    @Exclude
    private static final ResourceLocation InstaActive = new ResourceLocation("rzs:textures/Insta_active.png");
    @Exclude
    private static final ResourceLocation InstaInActive = new ResourceLocation("rzs:textures/Insta_inactive.png");
    @Exclude
    private static final ResourceLocation DGActive = new ResourceLocation("rzs:textures/DG_active.png");
    @Exclude
    private static final ResourceLocation DGInActive = new ResourceLocation("rzs:textures/DG_inactive.png");
    @Exclude
    private static final ResourceLocation SpreeActive = new ResourceLocation("rzs:textures/Spree_active.png");
    @Exclude
    private static final ResourceLocation SpreeInActive = new ResourceLocation("rzs:textures/Spree_inactive.png");

    protected String getText(boolean example){
        if(example)
            return "§c§lInsta Kill §r- §a§l10.0s§r, §6§lDouble Gold §r- §a§l30.0s§r";
        StringBuilder buffer = new StringBuilder();
        long diff = ItemStuff.InstaTimer - InGameTimer.getTick();
        if(diff > -60L && ItemStuff.InstaTimer != -1){
            buffer.append("§c§lInsta Kill §r- §a§l");
            diff = Long.max(diff, 0);
            buffer.append(String.format("%d.%ds", diff / 20, (diff % 20) / 2));
        }
        diff = ItemStuff.SpreeTimer - InGameTimer.getTick();
        if(diff > -60L && ItemStuff.SpreeTimer != -1){
            if(buffer.length() != 0)
                buffer.append("§r, ");
            buffer.append("§5§lShopping Spree §r- §a§l");
            diff = Long.max(diff, 0);
            buffer.append(String.format("%d.%ds", diff / 20, (diff % 20) / 2));
        }
        diff = ItemStuff.DGTimer - InGameTimer.getTick();
        if(diff > -60L && ItemStuff.DGTimer != -1){
            if(buffer.length() != 0)
                buffer.append("§r, ");
            buffer.append("§6§lDouble Gold §r- §a§l");
            diff = Long.max(diff, 0);
            buffer.append(String.format("%d.%ds", diff / 20, (diff % 20) / 2));
        }
        buffer.append("§r");
        return buffer.toString();
    }
    @Override
    protected void draw(UMatrixStack matrices, float x, float y, float scale, boolean example) {
        String text = getText(example);
        TextRenderer.drawScaledString(text, x + 91 * scale - (Platform.getGLPlatform().getStringWidth(text) * scale) / 2, y, color.getRGB(), TextRenderer.TextType.SHADOW, scale);
        if(example && RZSConfig.bossBar){
            RandomStuff.renderImage(InstaActive, x, y + 10 * scale, 182 * scale, 5 * scale);
            RandomStuff.renderImage(DGActive, x, y + 15 * scale, 182 * scale, 5 * scale);
            return;
        }
        if(RZSConfig.bossBar && RandomStuff.onGame) {
            int h = 1;
            long diff = ItemStuff.InstaTimer - InGameTimer.getTick();
            if (diff > -60L && ItemStuff.InstaTimer != -1) {
                diff = Long.max(diff, 0);
                float textureX = (float) diff / 200;
                float y1 = y + (5 + h * 5) * scale;
                RandomStuff.renderImage(InstaInActive, x, y1, 182 * scale, 5 * scale);
                RandomStuff.renderTexImage(InstaActive, x, y1, 182 * textureX * scale, 5 * scale, textureX, 1F);
                h++;
            }
            diff = ItemStuff.SpreeTimer - InGameTimer.getTick();
            if (diff > -60L && ItemStuff.SpreeTimer != -1) {
                diff = Long.max(diff, 0);
                float textureX = (float) diff / 400;
                float y1 = y + (5 + h * 5) * scale;
                RandomStuff.renderImage(SpreeInActive, x, y1, 182 * scale, 5 * scale);
                RandomStuff.renderTexImage(SpreeActive, x, y1, 182 * textureX * scale, 5 * scale, textureX, 1F);
                h++;
            }
            diff = ItemStuff.DGTimer - InGameTimer.getTick();
            if (diff > -60L && ItemStuff.DGTimer != -1) {
                diff = Long.max(diff, 0);
                float textureX = (float) diff / 600;
                float y1 = y + (5 + h * 5) * scale;
                RandomStuff.renderImage(DGInActive, x, y1, 182 * scale, 5 * scale);
                RandomStuff.renderTexImage(DGActive, x, y1, 182 * textureX * scale, 5 * scale, textureX, 1F);
            }
        }
    }

    @Override
    protected float getWidth(float scale, boolean example) {
        return (182 * scale);
    }

    @Override
    protected float getHeight(float scale, boolean example) {
        return 8 * scale;
    }
}
