package com.menitimu.rzs.hud;

import cc.polyfrost.oneconfig.config.annotations.Exclude;
import cc.polyfrost.oneconfig.config.annotations.Slider;
import cc.polyfrost.oneconfig.config.annotations.Switch;
import cc.polyfrost.oneconfig.hud.Hud;
import cc.polyfrost.oneconfig.libs.universal.UMatrixStack;
import com.menitimu.rzs.stuff.RELODIN;
import com.menitimu.rzs.util.RandomStuff;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class ReloadHud extends Hud {
    @Switch(name = "Slot Icon", category = "General")
    public static boolean SlotFrame = true;
    @Switch(name = "Reloading Icon", category = "General")
    public static boolean ReloadIcon = true;
    @Switch(name = "Glitched Reload Icon", category = "General")
    public static boolean ReloadGlitch = true;
    @Slider(name = "Icon Opacity", category = "General", min = 0.01F, max = 1F)
    public static float iconOpacity = 0.3F;
    @Exclude
    private static final ResourceLocation SLOT0 = new ResourceLocation("rzs:textures/slot0.png");
    @Exclude
    private static final ResourceLocation SLOT1 = new ResourceLocation("rzs:textures/slot1.png");
    @Exclude
    private static final ResourceLocation SLOT2 = new ResourceLocation("rzs:textures/slot2.png");
    @Exclude
    private static final ResourceLocation Glitched = new ResourceLocation("rzs:textures/reload_glitch.png");
    @Exclude
    private static final ResourceLocation Reload = new ResourceLocation("rzs:textures/reload.png");
    @Override
    protected void draw(UMatrixStack matrices, float x, float y, float scale, boolean example) {
        y += 2 * scale;
        if(example && !RandomStuff.onGame){
            GlStateManager.color(1F, 1F, 1F, iconOpacity);
            if(SlotFrame) {
                RandomStuff.renderImage(SLOT0, x + 2 * scale, y, 16 * scale, 16 * scale);
                RandomStuff.renderImage(SLOT1, x + 22 * scale, y, 16 * scale, 16 * scale);
                RandomStuff.renderImage(SLOT2, x + 42 * scale, y, 16 * scale, 16 * scale);
            }
            if(ReloadGlitch)
                RandomStuff.renderImage(Glitched, x + 2 * scale, y, 16 * scale, 16 * scale);
            if(ReloadIcon)
                RandomStuff.renderImage(Reload, x + 22 * scale, y, 16 * scale, 16 * scale);
            GlStateManager.color(1F, 1F, 1F, 1F);
        }
    }

    @Override
    protected float getWidth(float scale, boolean example) {
        return 60 * scale;
    }

    @Override
    protected float getHeight(float scale, boolean example) {
        return 20 * scale;
    }
}
