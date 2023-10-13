package com.menitimu.rzs.util;

import cc.polyfrost.oneconfig.platform.Platform;
import cc.polyfrost.oneconfig.renderer.TextRenderer;
import com.menitimu.rzs.hud.LCTimeHud;
import net.minecraft.util.ResourceLocation;

public class FlexUrLuck {
    /*
    0: The Puncher
    1: Zombie Zapper
    2: Gold Digger
    3: Flamethrower
    4: Zombie Soaker
    5: Elder Gun
    6: Blow Dart
    7: Rainbow Rifle
    8: Double Barrel Shotgun
    9: Lightning Rod
    10: Heal Skill
     */
    private static final ResourceLocation Puncher = new ResourceLocation("minecraft:textures/items/diamond_axe.png");
    private static final ResourceLocation Zapper = new ResourceLocation("minecraft:textures/items/diamond_pickaxe.png");
    private static final ResourceLocation Digger = new ResourceLocation("minecraft:textures/items/gold_pickaxe.png");
    private static final ResourceLocation Flame = new ResourceLocation("minecraft:textures/items/gold_hoe.png");
    private static final ResourceLocation Soaker = new ResourceLocation("minecraft:textures/items/diamond_hoe.png");
    private static final ResourceLocation Elder = new ResourceLocation("minecraft:textures/items/shears.png");
    private static final ResourceLocation Blow = new ResourceLocation("minecraft:textures/items/iron_shovel.png");
    private static final ResourceLocation Rainbow = new ResourceLocation("minecraft:textures/items/gold_shovel.png");
    private static final ResourceLocation DBS = new ResourceLocation("minecraft:textures/items/flint_and_steel.png");
    private static final ResourceLocation ROD = new ResourceLocation("minecraft:textures/items/blaze_rod.png");
    private static final ResourceLocation HEAL = new ResourceLocation("minecraft:textures/items/apple_golden.png");
    private static final ResourceLocation BOX = new ResourceLocation("rzs:textures/box.png");
    private static final String NOTHING = "[何もない]";
    private static final ResourceLocation[] items = {Puncher, Zapper, Digger, Flame, Soaker, Elder, Blow, Rainbow, DBS, ROD, HEAL};
    private int[] LcCount = new int[11];
    public void onLcRoll(String result){
        if(result.contains("Puncher")) {
            LcCount[0]++;
        } else if(result.contains("Zapper")){
            LcCount[1]++;
        } else if(result.contains("Digger")){
            LcCount[2]++;
        } else if(result.contains("Flamethrower")){
            LcCount[3]++;
        } else if(result.contains("Soaker")){
            LcCount[4]++;
        } else if(result.contains("Elder")){
            LcCount[5]++;
        } else if(result.contains("Blow")){
            LcCount[6]++;
        } else if(result.contains("Rainbow")){
            LcCount[7]++;
        } else if(result.contains("Double")){
            LcCount[8]++;
        } else if(result.contains("Rod")){
            LcCount[9]++;
        } else if(result.contains("Heal")){
            LcCount[10]++;
        }
    }
    public void renderResult(float x, float y, float scale){
        int r = 0;
        int sum = 0;
        for (int i : LcCount) {
            if(i > 0) r++;
            sum += i;
        }
        TextRenderer.drawScaledString("§e§lLC Result(" + sum + "):§r", x, y, 0xFFFFFF, TextRenderer.TextType.SHADOW, scale);
        y += 10;
        if(r <= 4) {
            if(r == 0){
                TextRenderer.drawScaledString(NOTHING, x + (42 - (float) Platform.getGLPlatform().getStringWidth(NOTHING) / 2) * scale, y + 16 * scale, 0xFF5555, TextRenderer.TextType.SHADOW, scale);
                return;
            }
            y += 10 * scale;
            x += 42 * scale - r * 10 * scale;
        }
        int j = 0;
        for(int i = 0; i < 11; i++) {
            if(LcCount[i] > 0) {
                renderItem(x, y, scale, i, LcCount[i]);
                j++;
                if(j != 0 && j % 4 == 0){
                    y += 20 * scale;
                    x -= 60 * scale;
                } else
                    x += 20 * scale;
            }
        }
    }
    public void renderItem(float x, float y, float scale, int item, int count) {
        RandomStuff.renderImage(BOX, x, y, 20 * scale, 20 * scale);
        RandomStuff.renderImage(items[item], x + 2 * scale, y + 2 * scale, 16 * scale, 16 * scale);
        TextRenderer.drawScaledString(String.valueOf(count), x + (18 - Platform.getGLPlatform().getStringWidth(String.valueOf(count))) * scale, y + 10 * scale, 0xFFFFFF, TextRenderer.TextType.SHADOW, scale);
    }
}