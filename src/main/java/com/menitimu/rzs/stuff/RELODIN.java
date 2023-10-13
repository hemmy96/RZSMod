package com.menitimu.rzs.stuff;

import com.menitimu.rzs.RZSMod;
import com.menitimu.rzs.hud.ReloadHud;
import com.menitimu.rzs.util.RandomStuff;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemDye;
import net.minecraft.network.play.server.S2FPacketSetSlot;
import net.minecraft.util.ResourceLocation;

public class RELODIN {
    private static GUNSTATE[] gunSlots = new GUNSTATE[]{GUNSTATE.NORMAL, GUNSTATE.NORMAL, GUNSTATE.NORMAL};
    private static final ResourceLocation Glitched = new ResourceLocation("rzs:textures/reload_glitch.png");
    private static final ResourceLocation Reload = new ResourceLocation("rzs:textures/reload.png");

    /*
    37: 1
    38: 2
    39: 3
     */
    public static void onJoin(){
        gunSlots = new GUNSTATE[]{GUNSTATE.EMPTY, GUNSTATE.EMPTY, GUNSTATE.EMPTY};
    }
    public static void onPacket(S2FPacketSetSlot packet){
        int slot = packet.func_149173_d() - 37;
        if(0 <= slot && slot <= 2){
            if(packet.func_149174_e() == null && gunSlots[slot] == GUNSTATE.RELOAD && gunSlots[slot] != GUNSTATE.GLITCHED){
                gunSlots[slot] = GUNSTATE.SUS;
                return;
            } else if(packet.func_149174_e() == null || packet.func_149174_e().getItem() instanceof ItemDye){
                gunSlots[slot] = GUNSTATE.EMPTY;
                return;
            }
            if(packet.func_149174_e().getItemDamage() != 0) {
                if (packet.func_149174_e() != null && !(packet.func_149174_e().getItem() instanceof ItemDye)) {
                    gunSlots[slot] = GUNSTATE.RELOAD;
                }
            } else if (packet.func_149174_e().stackSize == 1 && (gunSlots[slot] == GUNSTATE.SUS || (RandomStuff.map == RandomStuff.MapType.AA && gunSlots[slot] == GUNSTATE.EMPTY))) {
                gunSlots[slot] = GUNSTATE.GLITCHED;
            } else if (gunSlots[slot] != GUNSTATE.GLITCHED){
                gunSlots[slot] = GUNSTATE.NORMAL;
            }
        }
    }
    public static void onRenderSlot(){
        if(!RandomStuff.onGame)
            return;
        int g = 0;
        for(GUNSTATE slot : gunSlots){
            if(slot != GUNSTATE.EMPTY)
                g++;
        }
        float x = RZSMod.config.hud7.position.getX();
        float y = RZSMod.config.hud7.position.getY();
        float scale = RZSMod.config.hud7.getScale();
        x += ((3 - g) * 10 + 2) * scale;
        y += 2 * scale;
        GlStateManager.color(1F, 1F, 1F, ReloadHud.iconOpacity);
        for (int i = 0; i < g; i++) {
            if(ReloadHud.SlotFrame)
                RandomStuff.renderImage(new ResourceLocation("rzs:textures/slot" + i + ".png"), x, y, 16 * scale, 16 * scale);
            if(gunSlots[i] == GUNSTATE.GLITCHED && ReloadHud.ReloadGlitch)
                RandomStuff.renderImage(Glitched, x, y, 16 * scale, 16 * scale);
            else if(gunSlots[i] == GUNSTATE.RELOAD && ReloadHud.ReloadIcon)
                RandomStuff.renderImage(Reload, x, y, 16 * scale, 16 * scale);
            x += 20 * scale;
        }
        GlStateManager.color(1F, 1F, 1F, 1F);
    }
    enum GUNSTATE{
        NORMAL,
        RELOAD,
        SUS,
        GLITCHED,
        EMPTY
    }
}
