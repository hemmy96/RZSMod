package com.menitimu.rzs.command;

import cc.polyfrost.oneconfig.utils.commands.annotations.Command;
import cc.polyfrost.oneconfig.utils.commands.annotations.Main;
import cc.polyfrost.oneconfig.utils.commands.annotations.SubCommand;
import cc.polyfrost.oneconfig.utils.commands.annotations.SubCommandGroup;
import com.menitimu.rzs.RZSMod;
import com.menitimu.rzs.stuff.ItemStuff;
import com.menitimu.rzs.stuff.NoMoreHolo;
import com.menitimu.rzs.util.PowerUP;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

@Command(value = RZSMod.MODID, description = "Access the " + RZSMod.NAME + " GUI.")
public class RZSCommand {
    @Main
    private void handle() {
        RZSMod.config.openGui();
    }
    @SubCommandGroup(value = "holo")
    private static class SubCommandGroup1{
        @Main
        private void howTo(){
            Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.RED + "使用方法:\n/RZS holo remove キャッシュ内のホログラムが削除されます\n/RZS holo clear キャッシュをクリアします 誤検出の際に必ず使用してください"));
        }
        @SubCommand()
        private void remove(){
            NoMoreHolo.byebye();
        }
        @SubCommand()
        private void clear(){
            NoMoreHolo.clearExeList();
            Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.GREEN + "キャッシュをクリアしました"));
        }
    }
    @SubCommandGroup(value = "item")
    private static class SubCommandGroup2{
        @Main
        private void howTo(){
            Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.RED + "使用方法:\n/RZS item [max|ins|ss] [round]"));
        }
        @SubCommand()
        private void max(int round){
            ItemStuff.setPattern(PowerUP.PowerUpType.AMMO, round - 1, true);
        }
        @SubCommand()
        private void ins(int round){
            ItemStuff.setPattern(PowerUP.PowerUpType.INSTA, round - 1, true);
        }
        @SubCommand()
        private void ss(int round){
            ItemStuff.setPattern(PowerUP.PowerUpType.SPREE, round - 1, true);
        }
    }
}
