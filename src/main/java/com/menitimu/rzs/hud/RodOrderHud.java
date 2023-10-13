package com.menitimu.rzs.hud;

import cc.polyfrost.oneconfig.config.annotations.Exclude;
import cc.polyfrost.oneconfig.hud.Hud;
import cc.polyfrost.oneconfig.libs.universal.UMatrixStack;
import com.menitimu.rzs.stuff.RodOrder;
import com.menitimu.rzs.util.RandomStuff;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class RodOrderHud extends Hud {
    @Exclude
    private static final ResourceLocation ROD_ACTIVE_1 = new ResourceLocation("rzs:textures/rod_1_active.png");
    @Exclude
    private static final ResourceLocation ROD_ACTIVE_2 = new ResourceLocation("rzs:textures/rod_2_active.png");
    @Exclude
    private static final ResourceLocation ROD_ACTIVE_3 = new ResourceLocation("rzs:textures/rod_3_active.png");
    @Exclude
    private static final ResourceLocation ROD_ACTIVE_4 = new ResourceLocation("rzs:textures/rod_4_active.png");
    @Exclude
    private static final ResourceLocation ROD_INACTIVE_1 = new ResourceLocation("rzs:textures/rod_1_inactive.png");
    @Exclude
    private static final ResourceLocation ROD_INACTIVE_2 = new ResourceLocation("rzs:textures/rod_2_inactive.png");
    @Exclude
    private static final ResourceLocation ROD_INACTIVE_3 = new ResourceLocation("rzs:textures/rod_3_inactive.png");
    @Exclude
    private static final ResourceLocation ROD_INACTIVE_4 = new ResourceLocation("rzs:textures/rod_4_inactive.png");
    @Exclude
    private static final ResourceLocation ROD_ACTIVE_BAR = new ResourceLocation("rzs:textures/rod_bar_active.png");
    @Exclude
    private static final ResourceLocation ROD_INACTIVE_BAR = new ResourceLocation("rzs:textures/rod_bar_inactive.png");
    @Exclude
    private static final ResourceLocation ROD_CURRENT = new ResourceLocation("rzs:textures/rod_current.png");

    public RodOrderHud(){
        position.setSize(getWidth(scale, true), getHeight(scale, true));
    }
    @Override
    protected void draw(UMatrixStack matrices, float x, float y, float scale, boolean example) {
        if(example){
            RandomStuff.renderImage(ROD_INACTIVE_1, x, y, 22 * scale, 22 * scale);
            RandomStuff.renderImage(ROD_INACTIVE_2, x + (32 * scale), y, 22 * scale, 22 * scale);
            RandomStuff.renderImage(ROD_INACTIVE_BAR, x + (21 * scale), y + (6 * scale), 12 * scale, 10 * scale);
            RandomStuff.renderImage(ROD_INACTIVE_3, x + (64 * scale), y, 22 * scale, 22 * scale);
            RandomStuff.renderImage(ROD_INACTIVE_BAR, x + (53 * scale), y + (6 * scale), 12 * scale, 10 * scale);
            RandomStuff.renderImage(ROD_INACTIVE_4, x + (96 * scale), y, 22 * scale, 22 * scale);
            RandomStuff.renderImage(ROD_INACTIVE_BAR, x + (85 * scale), y + (6 * scale), 12 * scale, 10 * scale);
            return;
        }
        if(RodOrder.displayTime > 0 && RandomStuff.onGame && RandomStuff.map == RandomStuff.MapType.AA) {
            GlStateManager.color(1F, 1F, 1F, (float) RodOrder.displayTime / 100);
            switch (RodOrder.players) {
                case 4:
                    if (RodOrder.lrTimes >= 1)
                        RandomStuff.renderImage(ROD_ACTIVE_1, x, y, 22 * scale, 22 * scale);
                    else
                        RandomStuff.renderImage(ROD_INACTIVE_1, x, y, 22 * scale, 22 * scale);
                    if (RodOrder.lrTimes >= 2) {
                        RandomStuff.renderImage(ROD_ACTIVE_2, x + (32 * scale), y, 22 * scale, 22 * scale);
                        RandomStuff.renderImage(ROD_ACTIVE_BAR, x + (21 * scale), y + (6 * scale), 12 * scale, 10 * scale);
                    } else {
                        RandomStuff.renderImage(ROD_INACTIVE_2, x + (32 * scale), y, 22 * scale, 22 * scale);
                        RandomStuff.renderImage(ROD_INACTIVE_BAR, x + (21 * scale), y + (6 * scale), 12 * scale, 10 * scale);
                    }
                    if (RodOrder.lrTimes >= 3) {
                        RandomStuff.renderImage(ROD_ACTIVE_3, x + (64 * scale), y, 22 * scale, 22 * scale);
                        RandomStuff.renderImage(ROD_ACTIVE_BAR, x + (53 * scale), y + (6 * scale), 12 * scale, 10 * scale);
                    } else {
                        RandomStuff.renderImage(ROD_INACTIVE_3, x + (64 * scale), y, 22 * scale, 22 * scale);
                        RandomStuff.renderImage(ROD_INACTIVE_BAR, x + (53 * scale), y + (6 * scale), 12 * scale, 10 * scale);
                    }
                    if (RodOrder.lrTimes == 4) {
                        RandomStuff.renderImage(ROD_ACTIVE_4, x + (96 * scale), y, 22 * scale, 22 * scale);
                        RandomStuff.renderImage(ROD_INACTIVE_BAR, x + (85 * scale), y + (6 * scale), 12 * scale, 10 * scale);
                    } else {
                        RandomStuff.renderImage(ROD_INACTIVE_4, x + (96 * scale), y, 22 * scale, 22 * scale);
                        RandomStuff.renderImage(ROD_INACTIVE_BAR, x + (85 * scale), y + (6 * scale), 12 * scale, 10 * scale);
                    }
                    if (RodOrder.lrTimes >= 1)
                        RandomStuff.renderImage(ROD_CURRENT, x + ((32 * (RodOrder.lrTimes - 1) - 1)* scale), y - scale, 24 * scale, 24 * scale);
                    break;
                case 3:
                    x += (float) (16.5 * scale);
                    if (RodOrder.lrTimes >= 1)
                        RandomStuff.renderImage(ROD_ACTIVE_1, x, y, 22 * scale, 22 * scale);
                    else
                        RandomStuff.renderImage(ROD_INACTIVE_1, x, y, 22 * scale, 22 * scale);
                    if (RodOrder.lrTimes >= 2) {
                        RandomStuff.renderImage(ROD_ACTIVE_2, x + (32 * scale), y, 22 * scale, 22 * scale);
                        RandomStuff.renderImage(ROD_ACTIVE_BAR, x + (21 * scale), y + (6 * scale), 12 * scale, 10 * scale);
                    } else {
                        RandomStuff.renderImage(ROD_INACTIVE_2, x + (32 * scale), y, 22 * scale, 22 * scale);
                        RandomStuff.renderImage(ROD_INACTIVE_BAR, x + (21 * scale), y + (6 * scale), 12 * scale, 10 * scale);
                    }
                    if (RodOrder.lrTimes == 3) {
                        RandomStuff.renderImage(ROD_ACTIVE_3, x + (64 * scale), y, 22 * scale, 22 * scale);
                        RandomStuff.renderImage(ROD_ACTIVE_BAR, x + (53 * scale), y + (6 * scale), 12 * scale, 10 * scale);
                    } else {
                        RandomStuff.renderImage(ROD_INACTIVE_3, x + (64 * scale), y, 22 * scale, 22 * scale);
                        RandomStuff.renderImage(ROD_INACTIVE_BAR, x + (53 * scale), y + (6 * scale), 12 * scale, 10 * scale);
                    }
                    if (RodOrder.lrTimes >= 1)
                        RandomStuff.renderImage(ROD_CURRENT, x + ((32 * (RodOrder.lrTimes - 1) - 1)* scale), y - scale, 24 * scale, 24 * scale);
                    break;
                case 2:
                    x += 33 * scale;
                    if (RodOrder.lrTimes >= 1)
                        RandomStuff.renderImage(ROD_ACTIVE_1, x, y, 22 * scale, 22 * scale);
                    else
                        RandomStuff.renderImage(ROD_INACTIVE_1, x, y, 22 * scale, 22 * scale);
                    if (RodOrder.lrTimes == 2) {
                        RandomStuff.renderImage(ROD_ACTIVE_2, x + (32 * scale), y, 22 * scale, 22 * scale);
                        RandomStuff.renderImage(ROD_ACTIVE_BAR, x + (21 * scale), y + (6 * scale), 12 * scale, 10 * scale);
                    } else {
                        RandomStuff.renderImage(ROD_INACTIVE_2, x + (32 * scale), y, 22 * scale, 22 * scale);
                        RandomStuff.renderImage(ROD_INACTIVE_BAR, x + (21 * scale), y + (6 * scale), 12 * scale, 10 * scale);
                    }
                    if (RodOrder.lrTimes >= 1)
                        RandomStuff.renderImage(ROD_CURRENT, x + ((32 * (RodOrder.lrTimes - 1) - 1)* scale), y - scale, 24 * scale, 24 * scale);
                    break;
            }
        }
        GlStateManager.color(1F, 1F, 1F, 1F);
    }

    @Override
    protected float getWidth(float scale, boolean example) {
        if(RodOrder.players <= 1 && !example)
            return 0;
        return 118 * scale;
    }

    @Override
    protected float getHeight(float scale, boolean example) {
        if(RodOrder.players <= 1 && !example)
            return 0;
        return 22 * scale;
    }
}
