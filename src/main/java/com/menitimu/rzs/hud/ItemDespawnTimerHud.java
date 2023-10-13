package com.menitimu.rzs.hud;

import cc.polyfrost.oneconfig.config.annotations.Exclude;
import cc.polyfrost.oneconfig.config.annotations.Switch;
import cc.polyfrost.oneconfig.config.core.OneColor;
import cc.polyfrost.oneconfig.hud.Hud;
import cc.polyfrost.oneconfig.libs.universal.UMatrixStack;
import cc.polyfrost.oneconfig.platform.Platform;
import cc.polyfrost.oneconfig.renderer.TextRenderer;
import com.menitimu.rzs.stuff.InGameTimer;
import com.menitimu.rzs.stuff.ItemStuff;
import com.menitimu.rzs.stuff.RoundStuff;
import com.menitimu.rzs.util.PowerUP;
import com.menitimu.rzs.util.RandomStuff;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.util.ResourceLocation;

import java.util.LinkedHashMap;
import java.util.Map;

public class ItemDespawnTimerHud extends Hud {
    @Exclude
    private static Map<Integer, PowerUP> Item = new LinkedHashMap<>();
    @Exclude
    private static float width = 0F;
    @Exclude
    private static int NOITEM = 1 << 8;
    @Exclude
    private static final ResourceLocation AmmoTexture = new ResourceLocation("minecraft:textures/items/arrow.png");
    @Exclude
    private static final ResourceLocation InstaTexture = new ResourceLocation("rzs:textures/skull.png");
    @Exclude
    private static final ResourceLocation SpreeTexture = new ResourceLocation("rzs:textures/spree.png");
    @Exclude
    private static final ResourceLocation DGTexture = new ResourceLocation("minecraft:textures/blocks/double_plant_sunflower_front.png");
    @Exclude
    private static final ResourceLocation CarpTexture = new ResourceLocation("rzs:textures/carp.png");
    @Exclude
    private static final ResourceLocation BGTexture = new ResourceLocation("minecraft:textures/blocks/gold_block.png");
    @Switch(name = "Display Next Round")
    public static boolean showNext = true;
    public ItemDespawnTimerHud(){
        position.setSize(getWidth(scale, true), getHeight(scale, true));
    }


    @Override
    protected void draw(UMatrixStack matrices, float x, float y, float scale, boolean example) {
        OneColor color = new OneColor(255, 255, 255);
        int h = 0;
        if(example){
            RandomStuff.renderImage(SpreeTexture, x + scale, y + scale, 6 * scale, 6 * scale);
            float x1 = x + 8 * scale + width - (9 + Platform.getGLPlatform().getStringWidth("00.0s§r")) * scale;
            TextRenderer.drawScaledString("00.0s§r", x1, y, color.getRGB(), TextRenderer.TextType.SHADOW, scale);
            RandomStuff.renderImage(InstaTexture, x + scale, y - (7 * scale), 6 * scale, 6 * scale);
            TextRenderer.drawScaledString("00.0s§r", x1, y - (8 * scale), color.getRGB(), TextRenderer.TextType.SHADOW, scale);
            RandomStuff.renderImage(AmmoTexture, x, y - (8 * scale) * 2, 8 * scale, 8 * scale);
            TextRenderer.drawScaledString("00.0s§r", x1, y - (8 * scale) * 2, color.getRGB(), TextRenderer.TextType.SHADOW, scale);
            RandomStuff.renderImage(DGTexture, x, y - (8 * scale) * 3, 8 * scale, 8 * scale);
            TextRenderer.drawScaledString("00.0s§r", x1, y - (8 * scale) * 3, color.getRGB(), TextRenderer.TextType.SHADOW, scale);
            return;
        }
        if(RandomStuff.onGame) {
            float x1 = x + width;
            Item = ItemStuff.getItem();
            PowerUP AmmoItem = null;
            PowerUP InstaItem = null;
            PowerUP SpreeItem = null;
            Map<PowerUP, String> map1 = new LinkedHashMap<>();
            for (Map.Entry<Integer, PowerUP> entry : Item.entrySet()) {
                PowerUP powerUP = entry.getValue();
                long diff = powerUP.getTime() - InGameTimer.getTick();
                if(diff < 0L) {
                    ItemStuff.removeHolo(entry.getKey());
                    WorldClient worldClient = Minecraft.getMinecraft().theWorld;
                    worldClient.removeEntityFromWorld(entry.getKey());
                }
                switch (powerUP.getType()) {
                    case AMMO:
                        AmmoItem = powerUP;
                        break;
                    case INSTA:
                        InstaItem = powerUP;
                        break;
                    case SPREE:
                        SpreeItem = powerUP;
                        break;
                    default:
                        StringBuilder text = new StringBuilder();
                        if(diff > 300L)
                            text.append("§e");
                        else
                            text.append("§c");
                        text.append(String.format("%02d.%ds§r", diff / 20, (diff % 20) / 2));
                        map1.put(powerUP, text.toString());
                        break;
                }
            }
            long diff;
            if (RandomStuff.map == RandomStuff.MapType.AA) {
                if (SpreeItem != null) {
                    RandomStuff.renderImage(SpreeTexture, x + scale, y + scale, 6 * scale, 6 * scale);
                    diff = SpreeItem.getTime() - InGameTimer.getTick();
                    StringBuilder text = new StringBuilder();
                    if(diff > 300L)
                        text.append("§e");
                    else
                        text.append("§c");
                    text.append(String.format("%02d.%ds§r", diff / 20, (diff % 20) / 2));
                    TextRenderer.drawScaledString(text.toString(), x1 - Platform.getGLPlatform().getStringWidth(text.toString()) * scale, y, color.getRGB(), TextRenderer.TextType.SHADOW, scale);
                    h++;
                } else {
                    String text = ItemStuff.hudContent(PowerUP.PowerUpType.SPREE);
                    if(!text.isEmpty()){
                        RandomStuff.renderImage(SpreeTexture, x + scale, y + scale, 6 * scale, 6 * scale);
                        TextRenderer.drawScaledString(text, x1 - Platform.getGLPlatform().getStringWidth(text) * scale, y, color.getRGB(), TextRenderer.TextType.SHADOW, scale);
                        h++;
                    }
                }
            }
            if (InstaItem != null) {
                RandomStuff.renderImage(InstaTexture, x + scale, y - (8 * scale) * h + scale, 6 * scale, 6 * scale);
                diff = InstaItem.getTime() - InGameTimer.getTick();
                StringBuilder text = new StringBuilder();
                if(diff > 300L)
                    text.append("§e");
                else
                    text.append("§c");
                text.append(String.format("%02d.%ds§r", diff / 20, (diff % 20) / 2));
                TextRenderer.drawScaledString(text.toString(), x1 - Platform.getGLPlatform().getStringWidth(text.toString()) * scale, y - (8 * scale) * h, color.getRGB(), TextRenderer.TextType.SHADOW, scale);
                h++;
            } else {
                String text = ItemStuff.hudContent(PowerUP.PowerUpType.INSTA);
                if(!text.isEmpty()){
                    RandomStuff.renderImage(InstaTexture, x + scale, y - (8 * scale) * h + scale, 6 * scale, 6 * scale);
                    TextRenderer.drawScaledString(text, x1 - Platform.getGLPlatform().getStringWidth(text) * scale, y - (8 * scale) * h, color.getRGB(), TextRenderer.TextType.SHADOW, scale);
                    h++;
                }
            }
            if (AmmoItem != null) {
                RandomStuff.renderImage(AmmoTexture, x, y - (8 * scale) * h, 8 * scale, 8 * scale);
                diff = AmmoItem.getTime() - InGameTimer.getTick();
                StringBuilder text = new StringBuilder();
                if(diff > 300L)
                    text.append("§e");
                else
                    text.append("§c");
                text.append(String.format("%02d.%ds§r", diff / 20, (diff % 20) / 2));
                TextRenderer.drawScaledString(text.toString(), x1 - Platform.getGLPlatform().getStringWidth(text.toString()) * scale, y - (8 * scale) * h, color.getRGB(), TextRenderer.TextType.SHADOW, scale);
                h++;
            } else {
                String text = ItemStuff.hudContent(PowerUP.PowerUpType.AMMO);
                if(!text.isEmpty()){
                    RandomStuff.renderImage(AmmoTexture, x, y - (8 * scale) * h, 8 * scale, 8 * scale);
                    TextRenderer.drawScaledString(text, x1 - Platform.getGLPlatform().getStringWidth(text) * scale, y - (8 * scale) * h, color.getRGB(), TextRenderer.TextType.SHADOW, scale);
                    h++;
                }
            }
            for (Map.Entry<PowerUP, String> entry : map1.entrySet()) {
                switch (entry.getKey().getType()) {
                    case DG:
                        RandomStuff.renderImage(DGTexture, x, y - (8 * scale) * h, 8 * scale, 8 * scale);
                        break;
                    case CARP:
                        RandomStuff.renderImage(CarpTexture, x + scale, y - (8 * scale) * h + scale, 6 * scale, 6 * scale);
                        break;
                    case BONUS:
                        RandomStuff.renderImage(BGTexture, x + scale, y - (8 * scale) * h + scale, 6 * scale, 6 * scale);
                        break;
                }
                TextRenderer.drawScaledString(entry.getValue(), x1 - Platform.getGLPlatform().getStringWidth(entry.getValue()) * scale, y - (8 * scale) * h, color.getRGB(), TextRenderer.TextType.SHADOW, scale);
                h++;
            }
        }
    }

    @Override
    protected float getWidth(float scale, boolean example) {
        width = (9 + Platform.getGLPlatform().getStringWidth("00000")) * scale;
        return (9 + Platform.getGLPlatform().getStringWidth("00000")) * scale;
    }

    @Override
    protected float getHeight(float scale, boolean example) {
        return 8 * scale;
    }
}
