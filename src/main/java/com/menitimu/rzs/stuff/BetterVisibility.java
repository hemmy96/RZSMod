package com.menitimu.rzs.stuff;

import com.menitimu.rzs.config.RZSConfig;
import com.menitimu.rzs.util.RandomStuff;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.RenderPlayerEvent;
import org.lwjgl.opengl.GL11;

public class BetterVisibility {
    public static boolean shouldRender = true;
    public static boolean shouldRenderEnemy = true;
    public static void PlayerVisibility(RenderPlayerEvent.Pre event){
        EntityPlayer playerIn = event.entityPlayer;
        if(!shouldRender && playerIn != Minecraft.getMinecraft().thePlayer && !playerIn.isPlayerSleeping() && RandomStuff.calcDistance(playerIn) < RZSConfig.playerVisDistance){
            event.setCanceled(true);
            if(RZSConfig.renderName)
                event.renderer.renderName((AbstractClientPlayer) playerIn, event.x, event.y, event.z);
            if(RZSConfig.renderHitBox) {
                GL11.glLineWidth(RZSConfig.hitBoxWidth);
                RandomStuff.renderHitBox(playerIn, event.x, event.y, event.z);
                GL11.glLineWidth(1F);
            }
        }
    }
    public static void togglePlayerVis(){
        shouldRender = !shouldRender;
        Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.YELLOW + "Toggled player visibility " + (shouldRender ? (EnumChatFormatting.GREEN + "ON") : (EnumChatFormatting.RED + "OFF")) + EnumChatFormatting.YELLOW + "!"));
    }
    public static void toggleEnemyVis(){
        shouldRenderEnemy = !shouldRenderEnemy;
        Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.YELLOW + "Toggled enemy visibility " + (shouldRenderEnemy ? (EnumChatFormatting.GREEN + "ON") : (EnumChatFormatting.RED + "OFF")) + EnumChatFormatting.YELLOW + "!"));
    }
}
