package com.menitimu.rzs.stuff;

import com.menitimu.rzs.config.RZSConfig;
import com.menitimu.rzs.hud.ItemDespawnTimerHud;
import com.menitimu.rzs.util.PowerUP;
import com.menitimu.rzs.util.RandomStuff;
import com.menitimu.rzs.util.Round;
import com.menitimu.rzs.util.RoundData;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class ItemStuff {

    private static Map<Integer, PowerUP> Item = new LinkedHashMap<>();
    private static int NOITEM = 1 << 8;
    public static long InstaTimer = -1;
    public static long SpreeTimer = -1;
    public static long DGTimer = -1;
    public static Pattern AmmoPattern = Pattern.UNCHECKED;
    public static Pattern InstaPattern = Pattern.UNCHECKED;
    public static Pattern SpreePattern = Pattern.UNCHECKED;
    public static boolean isSpawnedAmmo = false;
    public static boolean isSpawnedInsta = false;
    public static boolean isSpawnedSpree = false;
    private static final int[][] AmmoPatternRaw = {
            {1, 4, 7, 11, 15, 20, 25, 30, 35, 40, 45, 50, 55, 60, 65, 70, 75, 80, 85, 90, 95, 100},
            {2, 5, 8, 12, 16, 21, 26, 31, 36, 41, 46, 51, 56, 61, 66, 71, 76, 81, 86, 91, 96, 101}};
    private static final int[][] InstaPatternRaw = {
            {1, 4, 7, 10, 13, 16, 19, 22},
            {2, 5, 8, 11, 14, 17, 20, 23}};
    private static final int[][] SpreePatternRaw = {
            {4, 14, 44, 54, 64, 74, 84, 94},
            {5, 15, 25, 35, 45, 65, 75, 85, 95},
            {6, 16, 26, 36, 46, 66, 76, 86, 96}};
    public static Set<Integer> ammo1;
    public static Set<Integer> ammo2;
    public static Set<Integer> ins1;
    public static Set<Integer> ins2;
    public static Set<Integer> ss1;
    public static Set<Integer> ss2;
    public static Set<Integer> ss3;
    public static void initPattern(){
        ammo1 = new HashSet<>();
        for (int i : AmmoPatternRaw[0])
            ammo1.add(i);
        ammo2 = new HashSet<>();
        for (int i : AmmoPatternRaw[1])
            ammo2.add(i);
        ins1 = new HashSet<>();
        for (int i : InstaPatternRaw[0])
            ins1.add(i);
        ins2 = new HashSet<>();
        for (int i : InstaPatternRaw[1])
            ins2.add(i);
        ss1 = new HashSet<>();
        for (int i : SpreePatternRaw[0])
            ss1.add(i);
        ss2 = new HashSet<>();
        for (int i : SpreePatternRaw[1])
            ss2.add(i);
        ss3 = new HashSet<>();
        for (int i : SpreePatternRaw[2])
            ss3.add(i);
    }
    public static void onRoundInit(){
        isSpawnedAmmo = false;
        isSpawnedInsta = false;
        isSpawnedSpree = false;
    }
    public static void addHolo(int entityID){
        Entity entity = Minecraft.getMinecraft().theWorld.getEntityByID(entityID);
        if(entity.hasCustomName() && !Item.containsKey(entityID)){
            String name = RandomStuff.stripText(entity.getDisplayName().getUnformattedText());
            switch (name){
                case "MAX AMMO":
                    if(isSpawnedAmmo)
                        return;
                    Item.put(entityID, new PowerUP(PowerUP.PowerUpType.AMMO, InGameTimer.getTick() + 1200L));
                    isSpawnedAmmo = true;
                    setPattern(PowerUP.PowerUpType.AMMO, RoundStuff.getRound(false), false);
                    break;
                case "INSTA KILL":
                    if(isSpawnedInsta)
                        return;
                    Item.put(entityID, new PowerUP(PowerUP.PowerUpType.INSTA, InGameTimer.getTick() + 1200L));
                    isSpawnedInsta = true;
                    setPattern(PowerUP.PowerUpType.INSTA, RoundStuff.getRound(false), false);
                    break;
                case "DOUBLE GOLD":
                    Item.put(entityID, new PowerUP(PowerUP.PowerUpType.DG, InGameTimer.getTick() + 1200L));
                    break;
                case "CARPENTER":
                    Item.put(entityID, new PowerUP(PowerUP.PowerUpType.CARP, InGameTimer.getTick() + 1200L));
                    break;
                case "SHOPPING SPREE":
                    if(isSpawnedSpree)
                        return;
                    Item.put(entityID, new PowerUP(PowerUP.PowerUpType.SPREE, InGameTimer.getTick() + 1200L));
                    isSpawnedSpree = true;
                    setPattern(PowerUP.PowerUpType.SPREE, RoundStuff.getRound(false), false);
                    break;
                case "BONUS GOLD":
                    Item.put(entityID, new PowerUP(PowerUP.PowerUpType.BONUS, InGameTimer.getTick() + 1200L));
                    break;
            }
        }
    }
    public static LinkedHashMap<Integer, PowerUP> getItem(){
        return (LinkedHashMap<Integer, PowerUP>) Item;
    }
    public static void removeHolo(int entityID){
        Item.remove(entityID);
    }
    public static void initItem(){
        AmmoPattern = Pattern.UNCHECKED;
        InstaPattern = Pattern.UNCHECKED;
        SpreePattern = Pattern.UNCHECKED;
        Item.clear();
        InstaTimer = -1L;
        SpreeTimer = -1L;
        DGTimer = -1L;
    }
    public static void renderTimer(Entity entity ,int entityID, double x, double y, double z){
        if(entity != null && Item.containsKey(entityID)){
            long diff = Item.get(entityID).getTime() - InGameTimer.getTick();
            RandomStuff.renderLabel(entity, String.format("§c%02d.%ds", diff / 20, (diff % 20) / 2), x, y + 0.8D, z);
        }
    }
    public static void setMaxPattern(){
        switch (RZSConfig.AmmoPattern){
            case 0:
                AmmoPattern = Pattern.PATTERN1;
                break;
            case 1:
                AmmoPattern = Pattern.PATTERN2;
                break;
            case 2:
                AmmoPattern = Pattern.UNCHECKED;
                break;
        }
    }
    public static void setInsPattern(){
        switch (RZSConfig.InstaPattern){
            case 0:
                InstaPattern = Pattern.PATTERN1;
                break;
            case 1:
                InstaPattern = Pattern.PATTERN2;
                break;
            case 2:
                InstaPattern = Pattern.UNCHECKED;
                break;
        }
    }
    public static void setSSPattern(){
        switch (RZSConfig.SpreePattern){
            case 0:
                SpreePattern = Pattern.PATTERN1;
                break;
            case 1:
                SpreePattern = Pattern.PATTERN2;
                break;
            case 2:
                SpreePattern = Pattern.PATTERN3;
                break;
            case 3:
                SpreePattern = Pattern.UNCHECKED;
                break;

        }
    }
    public static void setPattern(PowerUP.PowerUpType powerUpType, int round, boolean isCommand){
        ChatComponentText result = new ChatComponentText(EnumChatFormatting.RED + "指定されたラウンドと合致するパターンが見つかりませんでした");
        switch (powerUpType) {
            case AMMO:
                if (AmmoPattern == Pattern.UNCHECKED || isCommand){
                    if (ammo1.contains(round)) {
                        AmmoPattern = Pattern.PATTERN1;
                        result = new ChatComponentText(EnumChatFormatting.BLUE + "" + EnumChatFormatting.BOLD + "MAX AMMO" + EnumChatFormatting.RESET + EnumChatFormatting.YELLOW + "のパターンを設定しました");
                    } else if (ammo2.contains(round)) {
                        AmmoPattern = Pattern.PATTERN2;
                        result = new ChatComponentText(EnumChatFormatting.BLUE + "" + EnumChatFormatting.BOLD + "MAX AMMO" + EnumChatFormatting.RESET + EnumChatFormatting.YELLOW + "のパターンを設定しました");
                    }
                }
                break;
            case INSTA:
                if (InstaPattern == Pattern.UNCHECKED || isCommand) {
                    if (ins1.contains(round)) {
                        InstaPattern = Pattern.PATTERN1;
                        result = new ChatComponentText(EnumChatFormatting.RED + "" + EnumChatFormatting.BOLD + "INSTA KILL" + EnumChatFormatting.RESET + EnumChatFormatting.YELLOW + "のパターンを設定しました");
                    } else if (ins2.contains(round)) {
                        InstaPattern = Pattern.PATTERN2;
                        result = new ChatComponentText(EnumChatFormatting.RED + "" + EnumChatFormatting.BOLD + "INSTA KILL" + EnumChatFormatting.RESET + EnumChatFormatting.YELLOW + "のパターンを設定しました");
                    }
                }
                break;
            case SPREE:
                if (SpreePattern == Pattern.UNCHECKED || isCommand) {
                    if (ss1.contains(round)) {
                        SpreePattern = Pattern.PATTERN1;
                        result = new ChatComponentText(EnumChatFormatting.DARK_PURPLE + "" + EnumChatFormatting.BOLD + "SHOPPING SPREE" + EnumChatFormatting.RESET + EnumChatFormatting.YELLOW + "のパターンを設定しました");
                    } else if (ss2.contains(round)) {
                        SpreePattern = Pattern.PATTERN2;
                        result = new ChatComponentText(EnumChatFormatting.DARK_PURPLE + "" + EnumChatFormatting.BOLD + "SHOPPING SPREE" + EnumChatFormatting.RESET + EnumChatFormatting.YELLOW + "のパターンを設定しました");
                    } else if (ss3.contains(round)) {
                        SpreePattern = Pattern.PATTERN3;
                        result = new ChatComponentText(EnumChatFormatting.DARK_PURPLE + "" + EnumChatFormatting.BOLD + "SHOPPING SPREE" + EnumChatFormatting.RESET + EnumChatFormatting.YELLOW + "のパターンを設定しました");
                    }
                }
                break;
        }
        if(isCommand)
            Minecraft.getMinecraft().thePlayer.addChatComponentMessage(result);
    }
    public static String hudContent(PowerUP.PowerUpType powerUpType){
        String text = "";
        int round = RoundStuff.getRound(true);
        switch (powerUpType){
            case AMMO:
                switch (AmmoPattern){
                    case PATTERN1:
                        if(ammo1.contains(round) && !RoundStuff.thisRound.hasFlag(NOITEM) && !isSpawnedAmmo)
                            text = "§7--.-s§r";
                        else if (ItemDespawnTimerHud.showNext) {
                            for (int i : AmmoPatternRaw[0]){
                                if(i > round && ItemStuff.checkFlag(i)) {
                                    text = "§9R." + (i + 1) + "§r";
                                    break;
                                }
                            }
                        }
                        break;
                    case PATTERN2:
                        if(ammo2.contains(round) && !RoundStuff.thisRound.hasFlag(NOITEM) && !isSpawnedAmmo)
                            text = "§7--.-s§r";
                        else if (ItemDespawnTimerHud.showNext) {
                            for (int i : AmmoPatternRaw[1]){
                                if(i > round && ItemStuff.checkFlag(i)) {
                                    text = "§9R." + (i + 1) + "§r";
                                    break;
                                }
                            }
                        }
                        break;
                    case UNCHECKED:
                        text = "§c  N/A§r";
                        break;
                }
                break;
            case INSTA:
                switch (InstaPattern){
                    case PATTERN1:
                        if(ins1.contains(round) && !RoundStuff.thisRound.hasFlag(NOITEM) && !isSpawnedInsta)
                            text = "§7--.-s§r";
                        else if (ItemDespawnTimerHud.showNext) {
                            for (int i : InstaPatternRaw[0]){
                                if(i > round && ItemStuff.checkFlag(i)) {
                                    text = "§cR." + (i + 1) + "§r";
                                    break;
                                }
                            }
                        }
                        break;
                    case PATTERN2:
                        if(ins2.contains(round) && !RoundStuff.thisRound.hasFlag(NOITEM) && !isSpawnedInsta)
                            text = "§7--.-s§r";
                        else if (ItemDespawnTimerHud.showNext) {
                            for (int i : InstaPatternRaw[1]){
                                if(i > round && ItemStuff.checkFlag(i)) {
                                    text = "§cR." + (i + 1) + "§r";
                                    break;
                                }
                            }
                        }
                        break;
                    case UNCHECKED:
                        text = "§c  N/A§r";
                        break;
                }
                break;
            case SPREE:
                switch (SpreePattern){
                    case PATTERN1:
                        if(ss1.contains(round) && !RoundStuff.thisRound.hasFlag(NOITEM) && !isSpawnedSpree)
                            text = "§7--.-s§r";
                        else if (ItemDespawnTimerHud.showNext) {
                            for (int i : SpreePatternRaw[0]){
                                if(i > round && ItemStuff.checkFlag(i)) {
                                    text = "§5R." + (i + 1) + "§r";
                                    break;
                                }
                            }
                        }
                        break;
                    case PATTERN2:
                        if(ss2.contains(round) && !RoundStuff.thisRound.hasFlag(NOITEM) && !isSpawnedSpree)
                            text = "§7--.-s§r";
                        else if (ItemDespawnTimerHud.showNext) {
                            for (int i : SpreePatternRaw[1]){
                                if(i > round && ItemStuff.checkFlag(i)) {
                                    text = "§5R." + (i + 1) + "§r";
                                    break;
                                }
                            }
                        }
                        break;
                    case PATTERN3:
                        if(ss3.contains(round) && !RoundStuff.thisRound.hasFlag(NOITEM) && !isSpawnedSpree)
                            text = "§7--.-s§r";
                        else if (ItemDespawnTimerHud.showNext) {
                            for (int i : SpreePatternRaw[2]){
                                if(i > round && ItemStuff.checkFlag(i)) {
                                    text = "§5R." + (i + 1) + "§r";
                                    break;
                                }
                            }
                        }
                        break;
                    case UNCHECKED:
                        text = "§c  N/A§r";
                        break;
                }
                break;
        }
        return text;
    }
    public static boolean checkFlag(int round){
        switch (RandomStuff.map){
            case DE:
                if(round >= 0 && round < 30 && !RoundData.DeadEnd[round].hasFlag(NOITEM))
                    return true;
                break;
            case BB:
                if(round >= 0 && round < 30 && !RoundData.BadBlood[round].hasFlag(NOITEM))
                    return true;
                break;
            case AA:
                if(round >= 0 && round < 105 && !RoundData.AlienArcadium[round].hasFlag(NOITEM))
                    return true;
                break;
        }
        return false;
    }
    public enum Pattern{
        UNCHECKED,
        PATTERN1,
        PATTERN2,
        PATTERN3
    }
}
