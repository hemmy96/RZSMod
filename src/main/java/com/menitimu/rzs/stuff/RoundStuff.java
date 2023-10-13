package com.menitimu.rzs.stuff;

import cc.polyfrost.oneconfig.config.core.OneColor;
import com.google.gson.Gson;
import com.menitimu.rzs.config.RZSConfig;
import com.menitimu.rzs.hud.WaveDelayHud;
import com.menitimu.rzs.util.*;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RoundStuff {
    private static final int OLD = 1;
    private static final int GIANT = 1 << 1;
    private static final int CUBE = 1 << 2;
    private static final int SLIME = 1 << 3;
    private static final int BLAZE = 1 << 4;
    private static final int GOLEM = 1 << 5;
    private static final int BOSS = 1 << 6;
    private static final int GHAST = 1 << 7;
    private static Long roundStartTick = 0L;
    private static int round = 0;
    private static int wave = 0;
    private static int totalWave;
    private static Wave[] waves;
    private static OneColor[] disableColor;
    private static OneColor[] enableColor;
    private static String[] waveText;
    public static Round thisRound;
    public static ArrayList<String> TimeList = new ArrayList<>();
    private static final Pattern TIME = Pattern.compile("[0-9:]+");
    public static Long getRoundTick(){
        return InGameTimer.getTick() - roundStartTick;
    }
    public static boolean isBlaze = true;
    public static YourPB PBData;
    public static String Diff = "";
    public static File configPath;
    public static void onPreInit(File path){
        configPath = path;
        File file = new File(configPath, "/rzs/PBData.json");
        file.getParentFile().mkdirs();
        Gson gson = new Gson();
        if(file.exists()) {
            try (FileReader fileReader = new FileReader(file)){
                PBData = gson.fromJson(fileReader, YourPB.class);
            } catch (IOException e) {
                PBData = new YourPB();
                PBData.init();
            }
        } else {
            PBData = new YourPB();
            PBData.init();
        }
        try (Writer writer = new FileWriter(file)){
            gson.toJson(PBData, writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void savePB(){
        File file = new File(configPath, "/rzs/PBData.json");
        Gson gson = new Gson();
        try (Writer writer = new FileWriter(file)){
            gson.toJson(PBData, writer);
        } catch (IOException e) {
            Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.RED + "PBのデータの保存に失敗しました 制作者に連絡してください"));
            e.printStackTrace();
        }
    }
    public static String getDiff(int value, int round){
        if(RandomStuff.map == RandomStuff.MapType.DE || RandomStuff.map == RandomStuff.MapType.BB) {
            int prev = PBData.setPB(value, round, RandomStuff.map, false, RandomStuff.difficulty);
            if(prev == -1)
                return "§f(§l§aNEW§f)";
            else {
                int diff = value - prev;
                if(diff < 0) {
                    diff = Math.abs(diff);
                    return "§f(§a-" + diff / 20 + "." + ((diff % 20) * 5) + "s§f)";
                } else if(diff > 0)
                    return "§f(§c+" + diff / 20 + "." + ((diff % 20) * 5) + "s§f)";
                else return "§f(§7±0.00)";
            }
        } else if(RandomStuff.map == RandomStuff.MapType.AA) {
            int prev = PBData.setAAPB(value, round, false, RandomStuff.maxPlayers);
            if(prev == -1)
                return "§f(§l§aNEW§f)";
            else {
                int diff = value - prev;
                if(diff < 0) {
                    diff = Math.abs(diff);
                    return "§f(§a-" + diff / 20 + "." + ((diff % 20) * 5) + "s§f)";
                } else if(diff > 0)
                    return "§f(§c+" + diff / 20 + "." + ((diff % 20) * 5) + "s§f)";
                else return "§f(§7±0.00)";
            }
        }
        return "§f(§cERROR§f)";
    }
    public static String getRoundDiff(String time, int round){
        if(time.equals("§cN/A")) return ("§f(§cERROR§f)");
        String[] str = time.split(":");
        int value = 0;
        try {
            for (int i = 0; i < str.length; i ++)
                value += (int) (Integer.parseInt(str[str.length - 1 - i]) * Math.pow(60, i));
        } catch (NumberFormatException e){
            return ("§f(§cERROR§f)");
        }
        if(RandomStuff.map == RandomStuff.MapType.DE || RandomStuff.map == RandomStuff.MapType.BB) {
            int prev = PBData.setPB(value, round, RandomStuff.map, true, RandomStuff.difficulty);
            if(prev == -1)
                return "§f(§l§aNEW§f)";
            else {
                int diff = value - prev;
                if(diff < 0) {
                    diff = Math.abs(diff);
                    return "§f(§a-" + (diff >= 3600 ? diff / 3600 + ":" : "") + (diff >= 3600 ? String.format("%02d", diff / 60) + ":" : diff / 60 + ":") + String.format("%02d", diff % 60) + "§f)";
                } else if(diff > 0)
                    return "§f(§c+" + (diff >= 3600 ? diff / 3600 + ":" : "") + (diff >= 3600 ? String.format("%02d", diff / 60) + ":" : diff / 60 + ":") + String.format("%02d", diff % 60) + "§f)";
                else return "§f(§7±0:00)";
            }
        } else if(RandomStuff.map == RandomStuff.MapType.AA) {
            int prev = PBData.setAAPB(value, round, true, RandomStuff.maxPlayers);
            if(prev == -1)
                return "§f(§l§aNEW§f)";
            else {
                int diff = value - prev;
                if(diff < 0) {
                    diff = Math.abs(diff);
                    return "§f(§a-" + (diff >= 3600 ? diff / 3600 + ":" : "") + (diff >= 3600 ? String.format("%02d", diff / 60) + ":" : diff / 60 + ":") + String.format("%02d", diff % 60) + "§f)";
                } else if(diff > 0)
                    return "§f(§c+" + (diff >= 3600 ? diff / 3600 + ":" : "") + (diff >= 3600 ? String.format("%02d", diff / 60) + ":" : diff / 60 + ":") + String.format("%02d", diff % 60) + "§f)";
                else return "§f(§7±0:00)";
            }
        }
        return "§f(§cERROR§f)";
    }
    public static int getTickFromLastWave(){
        return (int) (InGameTimer.getTick() - roundStartTick - waves[totalWave - 1].getDelay());
    }
    public static void onRoundStart(int value){
        if(RZSConfig.fromLastWave && thisRound != null && value != 0){
            Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.YELLOW + "You took " + EnumChatFormatting.RED + EnumChatFormatting.BOLD + ((InGameTimer.getTick() - roundStartTick - waves[totalWave - 1].getDelay()) / 20) + "." + String.format("%02d", (((InGameTimer.getTick() - roundStartTick - waves[totalWave - 1].getDelay()) % 20) * 5)) + "s" + EnumChatFormatting.RESET + EnumChatFormatting.YELLOW + " after last wave."));
        }
        if(value != 0){
            String time = getTime();
            String diff = getRoundDiff(time, value - 1);
            if(TimeList.isEmpty() || thisRound == null){
                if(value >= 20 && RandomStuff.map == RandomStuff.MapType.AA)
                    TimeList.add("§a▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬");
                TimeList.add("§cRound " + (value + 1) + "§e started in §a" + time + "§e! " + diff);
                TimeList.add("§7----------------------------------------------------------------");
            } else {
                int tick = (int) (InGameTimer.getTick() - roundStartTick - waves[totalWave - 1].getDelay());
                TimeList.add("§f§lRound " + value + ": §r§a" + (tick / 20) + "." + ((tick % 20) * 5) + "s " + getDiff(tick, value - 1));
                if(value % 10 == 0 && value < 30)
                    Diff = diff;
                if(value % 10 == 0 && value >= 30 && RandomStuff.map == RandomStuff.MapType.AA) {
                    TimeList.add(0, "§a▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬");
                    TimeList.add("§7----------------------------------------------------------------");
                    TimeList.add("§eYou completed §cRound " + value + " §ein §a" + time + "§e! " + diff);
                    TimeList.add("§a▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬");
                    StringBuilder stringBuilder = new StringBuilder();
                    for (String line : RoundStuff.TimeList)
                        stringBuilder.append(line).append("\n");
                    if(RZSConfig.timeMoreInfo)
                        Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText(stringBuilder.toString()));
                    TimeList.clear();
                    TimeList.add("§cRound " + (value + 1) + "§e started in §a" + time + "§e! " + diff);
                    TimeList.add("§7----------------------------------------------------------------");
                }
            }
        } else {
            TimeList.add("§cRound 1§e started in §a0:00§e!");
            TimeList.add("§7----------------------------------------------------------------");
        }
        ItemStuff.onRoundInit();
        round = value;
        roundStartTick = InGameTimer.getTick();
        wave = 0;
        isBlaze = RZSConfig.isBlaze;
        switch (RandomStuff.map){
            case DE:
                if(RoundData.DeadEnd.length - 1 < round || round < 0)
                    return;
                thisRound = RoundData.DeadEnd[round];
                break;
            case BB:
                if(RoundData.BadBlood.length - 1 < round || round < 0)
                    return;
                thisRound = RoundData.BadBlood[round];
                break;
            case AA:
                if(RoundData.AlienArcadium.length - 1 < round || round < 0)
                    return;
                thisRound = RoundData.AlienArcadium[round];
                break;
            default:
                return;
        }
        totalWave = thisRound.getWavesInRound();
        waves = new Wave[totalWave];
        disableColor = new OneColor[totalWave];
        enableColor = new OneColor[totalWave];
        waveText = new String[totalWave];
        for(int i = 0; i < totalWave; i++){
            waves[i] = thisRound.getWave(i);
            waveText[i] = "W" + (1 + i) + " " + waves[i].getTime();
            if(WaveDelayHud.BossColor) {
                if (waves[i].hasFlag(OLD | GIANT)) {
                    enableColor[i] = WaveDelayHud.OldGiantOn;
                    disableColor[i] = WaveDelayHud.OldGiantOff;
                    continue;
                }
                if (waves[i].hasFlag(OLD)) {
                    enableColor[i] = WaveDelayHud.oldOn;
                    disableColor[i] = WaveDelayHud.oldOff;
                    continue;
                }
                if (waves[i].hasFlag(GIANT)) {
                    enableColor[i] = WaveDelayHud.giantOn;
                    disableColor[i] = WaveDelayHud.giantOff;
                    continue;
                }
            }
            enableColor[i] = WaveDelayHud.defaultOn;
            disableColor[i] = WaveDelayHud.defaultOff;
        }
        WaypointManager.onRoundInit(thisRound);
    }
    public static String getTime() {
        String time;
        if(RandomStuff.isSBLoaded){
            time = RandomStuff.getScoreBoard().get(11);
            time = time.substring(time.indexOf(" "));
            Matcher matcher = TIME.matcher(time);
            StringBuilder stringBuilder = new StringBuilder();
            while (matcher.find()){
                stringBuilder.append(matcher.group());
            }
            time = stringBuilder.toString();
        } else {
            time = "§cN/A";
        }
        return time;
    }

    public static int getRound(boolean notForItem){
        if(!notForItem && InGameTimer.getTick() - roundStartTick < 100)
            return round - 1;
        return round;
    }
    public static int getWave(){
        return wave;
    }
    public static void tickProcess(){
        if(waves == null) return;
        long tick = getRoundTick();
        if(tick == 200)
            RandomStuff.maxPlayers = Integer.max(RandomStuff.maxPlayers, RodOrder.players);
        if(RZSConfig.playCountSound) {
            if(RZSConfig.onlyLastWave && (waves[totalWave - 1].getDelay() - tick) % 20 == 0 && (waves[totalWave - 1].getDelay() - tick) <= 40 && (waves[totalWave - 1].getDelay() - tick) > 0)
                Minecraft.getMinecraft().thePlayer.playSound(RZSConfig.CountSound, RZSConfig.CountSoundVolume, RZSConfig.CountSoundPitch);
            else if(!RZSConfig.onlyLastWave && (waves[wave].getDelay() - tick) % 20 == 0 && (waves[wave].getDelay() - tick) <= 40 && (waves[wave].getDelay() - tick) > 0)
                Minecraft.getMinecraft().thePlayer.playSound(RZSConfig.CountSound, RZSConfig.CountSoundVolume, RZSConfig.CountSoundPitch);
        }
        if(wave < totalWave && waves[wave].getDelay() == tick){
            if(wave == totalWave - 1 && RZSConfig.playWaveSound && RZSConfig.playLastWaveSound)
                Minecraft.getMinecraft().thePlayer.playSound(RZSConfig.LastWaveSound, RZSConfig.lastWaveSoundVolume, RZSConfig.lastWaveSoundPitch);
            else if(RZSConfig.playWaveSound)
                Minecraft.getMinecraft().thePlayer.playSound(RZSConfig.WaveSound, RZSConfig.waveSoundVolume, RZSConfig.waveSoundPitch);
            if(wave != totalWave - 1) wave++;
        }
    }
    public static long getDelayforWayp(){
        int tick = Math.toIntExact(getRoundTick());
        if(wave != 0 && waves[wave - 1].getDelay() - tick >= -20 && waves[wave].getDelay() - tick > 100)
            return 0;
        if(wave == totalWave - 1 && waves[wave].getDelay() - tick <= -20)
            return 1000;
        return Long.max(0, waves[wave].getDelay() - tick);
    }
    public static OneColor getColorforWayp(){
        Wave wave1 = waves[wave];
        int tick = Math.toIntExact(getRoundTick());
        if(wave != 0 && waves[wave - 1].getDelay() - tick >= -20 && waves[wave].getDelay() - tick > 100)
            wave1 = waves[wave - 1];
        if(RZSConfig.renderCountColor) {
            if (wave1.hasFlag(OLD)) {
                if (wave1.hasFlag(GIANT))
                    return WaveDelayHud.OldGiantOn;
                return WaveDelayHud.oldOn;
            }
            if (wave1.hasFlag(GIANT))
                return WaveDelayHud.giantOn;
            if (wave1.hasFlag(BLAZE))
                return RZSConfig.BlazeWaypColor;
            if (wave1.hasFlag(CUBE))
                return RZSConfig.CubeWaypColor;
            if (wave1.hasFlag(SLIME))
                return RZSConfig.SlimeWaypColor;
            if (wave1.hasFlag(GOLEM))
                return RZSConfig.GolemWaypColor;
        }
        return WaveDelayHud.defaultOn;
    }
    public static String[] getWaveText(){
        return waveText;
    }
    public static OneColor[] getEnableColor(){
        return enableColor;
    }
    public static OneColor[] getDisableColor(){
        return disableColor;
    }
}
