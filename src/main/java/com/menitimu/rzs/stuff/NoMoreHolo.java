package com.menitimu.rzs.stuff;

import com.menitimu.rzs.RZSMod;
import com.menitimu.rzs.config.RZSConfig;
import com.menitimu.rzs.util.ChatComponentBuilder;
import com.menitimu.rzs.util.RandomStuff;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.event.ClickEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public class NoMoreHolo {
    private static ArrayList<Integer> BlackList = new ArrayList<>();
    private static ArrayList<Integer> ExecutionList = new ArrayList<>();
    private static final Set<String> HOLO_LIST = new HashSet<>(Arrays.asList("REVIVING...", "HOLD SNEAK TO REVIVE!", "■■■■■■■■■■■■■■■","SHOPPING SPREE", "DOUBLE GOLD","MAX AMMO", "INSTA KILL", "CARPENTER", "BONUS GOLD"));
    private static final Pattern SECONDS = Pattern.compile("\\d+\\.\\d+s");
    public static int count = 0;
    public static void clearList(){
        BlackList.clear();
    }
    public static void clearExeList(){
        ExecutionList.clear();
    }
    public static void setBlackList(int entityID){
        WorldClient worldClient = Minecraft.getMinecraft().theWorld;
        if(!BlackList.contains(entityID) && worldClient.getEntityByID(entityID) == null) {
            RZSMod.logger.info("This could be a hologram entityID:" + entityID);
            BlackList.add(entityID);
            if(BlackList.size() >= RZSConfig.listSize)
                BlackList.remove(0);
        }
    }
    public static boolean checkID(int entityID){
        if(BlackList.contains(entityID) && RZSConfig.holoRemover && !ExecutionList.contains(entityID)) {
            ExecutionList.add(entityID);
            WorldClient worldClient = Minecraft.getMinecraft().theWorld;
            IChatComponent text = ChatComponentBuilder.of("RZSがホログラムを検出しました!").setColor(EnumChatFormatting.YELLOW).setHoverEvent(ChatComponentBuilder.of("" + worldClient.getEntityByID(entityID)).build()).build();
            IChatComponent commands = ChatComponentBuilder.of("[はい、それはホログラムです]").setColor(EnumChatFormatting.GREEN).setHoverEvent(ChatComponentBuilder.of("現在のホログラム数:" + ExecutionList.size() + "\nクリックするとキャッシュ内のホログラムが一斉に削除されます").build()).setClickEvent(ClickEvent.Action.RUN_COMMAND, "/rzs holo remove").build();
            IChatComponent commands1 = ChatComponentBuilder.of("[いいえ、それは間違いです!]").setColor(EnumChatFormatting.RED).setHoverEvent(ChatComponentBuilder.of("クリックするとホログラムのキャッシュが削除されます").build()).setClickEvent(ClickEvent.Action.RUN_COMMAND, "/rzs holo clear").build();
            commands.appendSibling(commands1);
            Minecraft.getMinecraft().thePlayer.addChatComponentMessage(text);
            Minecraft.getMinecraft().thePlayer.addChatComponentMessage(commands);
            return true;
        }
        return false;
    }
    public static void byebye(){
        WorldClient worldClient = Minecraft.getMinecraft().theWorld;
        for(int entityID : ExecutionList)
            worldClient.removeEntityFromWorld(entityID);
        ExecutionList.clear();
        Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.RED + "教育教育教育教育死刑教育執行"));
    }
    public static void byebye2(){
        WorldClient worldClient = Minecraft.getMinecraft().theWorld;
        for(int entityID : BlackList){
            Entity entity = worldClient.getEntityByID(entityID);
            if(entity != null) {
                String name = RandomStuff.stripText(entity.getName());
                if(entity instanceof EntityArmorStand && (HOLO_LIST.contains(name) || SECONDS.matcher(name).matches())){
                    RZSMod.logger.info("Removed holo : " + name);
                    worldClient.removeEntityFromWorld(entityID);
                }
            }
        }
        BlackList.clear();
        ExecutionList.clear();
        Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.RED + "死刑死刑死刑死刑死刑死刑死刑執行"));
    }
    public static void onPushed3(){
        Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.RED + "死刑執行通知発行"));
        count = 20;
    }
    public static void byebye3(EntityArmorStand entity){
        String name = RandomStuff.stripText(entity.getName());
        if(HOLO_LIST.contains(name) || SECONDS.matcher(name).matches()){
            RZSMod.logger.info("Removed holo : " + name);
            WorldClient worldClient = Minecraft.getMinecraft().theWorld;
            worldClient.removeEntityFromWorld(entity.getEntityId());
        }
    }
}
