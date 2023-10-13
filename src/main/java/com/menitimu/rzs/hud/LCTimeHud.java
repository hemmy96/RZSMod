package com.menitimu.rzs.hud;

import cc.polyfrost.oneconfig.config.annotations.Exclude;
import cc.polyfrost.oneconfig.hud.Hud;
import cc.polyfrost.oneconfig.libs.universal.UMatrixStack;
import cc.polyfrost.oneconfig.platform.Platform;
import cc.polyfrost.oneconfig.renderer.TextRenderer;
import com.menitimu.rzs.util.RandomStuff;
import net.minecraft.util.ResourceLocation;

public class LCTimeHud extends Hud {
    @Exclude
    public static int LcTime = 0;
    @Exclude
    public static int LcResult = 0;
    @Exclude
    private static ResourceLocation LC_OPEN = new ResourceLocation("rzs:textures/open_chest.png");
    @Exclude
    private static ResourceLocation LC_CLOSE = new ResourceLocation("rzs:textures/close_chest.png");
    @Exclude
    private static final ResourceLocation Puncher = new ResourceLocation("minecraft:textures/items/diamond_axe.png");
    @Exclude
    private static final ResourceLocation Zapper = new ResourceLocation("minecraft:textures/items/diamond_pickaxe.png");
    @Exclude
    private static final ResourceLocation Digger = new ResourceLocation("minecraft:textures/items/gold_pickaxe.png");
    @Exclude
    private static final ResourceLocation Flame = new ResourceLocation("minecraft:textures/items/gold_hoe.png");
    @Exclude
    private static final ResourceLocation Soaker = new ResourceLocation("minecraft:textures/items/diamond_hoe.png");
    @Exclude
    private static final ResourceLocation Elder = new ResourceLocation("minecraft:textures/items/shears.png");
    @Exclude
    private static final ResourceLocation Blow = new ResourceLocation("minecraft:textures/items/iron_shovel.png");
    @Exclude
    private static final ResourceLocation Rainbow = new ResourceLocation("minecraft:textures/items/gold_shovel.png");
    @Exclude
    private static final ResourceLocation DBS = new ResourceLocation("minecraft:textures/items/flint_and_steel.png");
    @Exclude
    private static final ResourceLocation ROD = new ResourceLocation("minecraft:textures/items/blaze_rod.png");
    @Exclude
    private static final ResourceLocation HEAL = new ResourceLocation("minecraft:textures/items/apple_golden.png");
    @Exclude
    private static final ResourceLocation[] items = {Puncher, Zapper, Digger, Flame, Soaker, Elder, Blow, Rainbow, DBS, ROD, HEAL};
    @Override
    protected void draw(UMatrixStack matrices, float x, float y, float scale, boolean example) {
        if(example){
            RandomStuff.renderImage(LC_OPEN, x, y, 30 * scale, 30 * scale);
            RandomStuff.renderImage(Puncher, x + 7 * scale, y + 2 * scale, 16 * scale, 16 * scale);
            TextRenderer.drawScaledString("10.0s", x + (float) (30 - Platform.getGLPlatform().getStringWidth("10.0s")) * scale / 2, y + 20 * scale, 0xFFFFFF, TextRenderer.TextType.SHADOW, scale);
        } else if(RandomStuff.onGame){
            if(LcTime > 0){
                String time = LcTime / 20 + "." + ((LcTime % 20) / 2) + "s";
                RandomStuff.renderImage(LC_OPEN, x, y, 30 * scale, 30 * scale);
                RandomStuff.renderImage(items[LcResult], x + 7 * scale, y + 2 * scale, 16 * scale, 16 * scale);
                TextRenderer.drawScaledString(time, x + (float) (30 - Platform.getGLPlatform().getStringWidth(time)) * scale / 2, y + 20 * scale, 0xFFFFFF, TextRenderer.TextType.SHADOW, scale);
            } else {
                RandomStuff.renderImage(LC_CLOSE, x, y, 30 * scale, 30 * scale);
                TextRenderer.drawScaledString("0.0s", x + (float) (30 - Platform.getGLPlatform().getStringWidth("0.0s")) * scale / 2, y + 20 * scale, 0xFFFFFF, TextRenderer.TextType.SHADOW, scale);
            }
        }
    }

    @Override
    protected float getWidth(float scale, boolean example) {
        return 30 * scale;
    }

    @Override
    protected float getHeight(float scale, boolean example) {
        return 30 * scale;
    }
}
