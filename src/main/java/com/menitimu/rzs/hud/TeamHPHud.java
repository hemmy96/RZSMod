package com.menitimu.rzs.hud;

import cc.polyfrost.oneconfig.config.annotations.Exclude;
import cc.polyfrost.oneconfig.config.annotations.Switch;
import cc.polyfrost.oneconfig.hud.Hud;
import cc.polyfrost.oneconfig.libs.universal.UMatrixStack;
import cc.polyfrost.oneconfig.platform.Platform;
import cc.polyfrost.oneconfig.renderer.TextRenderer;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Ordering;
import com.menitimu.rzs.util.RandomStuff;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Team;
import net.minecraft.world.WorldSettings;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeamHPHud extends Hud {
    @Exclude
    private static final Ordering<NetworkPlayerInfo> field_175252_a = Ordering.from(new PlayerComparator());
    @Exclude
    private static List<NetworkPlayerInfo> list;
    @Exclude
    public static Map<String, Integer> HpMap = new HashMap<>();
    @Switch(name = "Render Self", category = "General")
    public static boolean renderSelf = true;
    @Switch(name = "Render Name in HUD", category = "General")
    public static boolean renderName = false;
    private void getString(){
        NetHandlerPlayClient netHandlerPlayClient = Minecraft.getMinecraft().thePlayer.sendQueue;
        if(netHandlerPlayClient == null || netHandlerPlayClient.getPlayerInfoMap() == null) return;
        list = field_175252_a.sortedCopy(netHandlerPlayClient.getPlayerInfoMap());
    }
    public String getPlayerName(NetworkPlayerInfo networkPlayerInfoIn) {
        return (networkPlayerInfoIn.getDisplayName() != null) ? networkPlayerInfoIn.getDisplayName().getFormattedText() : ScorePlayerTeam.formatPlayerName((Team)networkPlayerInfoIn.getPlayerTeam(), networkPlayerInfoIn.getGameProfile().getName());
    }

    @Override
    protected void draw(UMatrixStack matrices, float x, float y, float scale, boolean example) {
        getString();
        int h = 0;
        ScoreObjective scoreObjective = Minecraft.getMinecraft().theWorld.getScoreboard().getObjectiveInDisplaySlot(2);
        if(list == null || !RandomStuff.onGame) return;
        for(NetworkPlayerInfo networkPlayerInfo : list) {
            String text = getPlayerName(networkPlayerInfo);
            GameProfile gameprofile = networkPlayerInfo.getGameProfile();
            AbstractClientPlayer player = (AbstractClientPlayer) Minecraft.getMinecraft().theWorld.getPlayerEntityByUUID(gameprofile.getId());
            AbstractClientPlayer self = Minecraft.getMinecraft().thePlayer;
            if(player == self && !renderSelf || player == null) {
                continue;
            }
            if(scoreObjective != null) {
                int i = HpMap.getOrDefault(text, 20);
                String hp = " §7";
                if (!player.isSneaking() && !player.isInvisible()) {
                    i = scoreObjective.getScoreboard().getValueFromObjective(gameprofile.getName(), scoreObjective).getScorePoints();
                    HpMap.put(getPlayerName(networkPlayerInfo), i);
                    if(i >= self.getMaxHealth() * 0.7)
                        hp += "§a";
                    else if(i >= self.getMaxHealth() * 0.35)
                        hp += "§e";
                    else if(i >= self.getMaxHealth() * 0.16)
                        hp += "§c";
                    else
                        hp += "§4";
                }
                hp += i + "HP§r";
                TextRenderer.drawScaledString(hp, x + (8 + (renderName ? Platform.getGLPlatform().getStringWidth(text) : 0)) * scale, y + h * scale * 8, 0xFFFFFF, TextRenderer.TextType.SHADOW, scale);
            }
            if(renderName)
                TextRenderer.drawScaledString(text + "§r", x + 8 * scale, y + h * scale * 8, 0xFFFFFF, TextRenderer.TextType.SHADOW, scale);
            RandomStuff.renderTexImage(networkPlayerInfo.getLocationSkin(), x, y + h * scale * 8, 8F, 8F, 8, 8, (int)(8 * scale), (int)(8 * scale), 64F, 64F);
            if(player.isWearing(EnumPlayerModelParts.HAT))
                RandomStuff.renderTexImage(networkPlayerInfo.getLocationSkin(), x, y + h * scale * 8, 40F, 8F, 8, 8, (int)(8 * scale), (int)(8 * scale), 64F, 64F);
            h++;
        }
    }

    @Override
    protected float getWidth(float scale, boolean example) {
        int i = 0;
        if(list == null || !RandomStuff.onGame) return 0;
        for(NetworkPlayerInfo networkPlayerInfo : list)
            i = Math.max(Platform.getGLPlatform().getStringWidth(getPlayerName(networkPlayerInfo)), i);
        return (i + 8) * scale;
    }

    @Override
    protected float getHeight(float scale, boolean example) {
        if(list == null || !RandomStuff.onGame) return 0;
        return list.size() * scale * 8;
    }
    @SideOnly(Side.CLIENT)
    static class PlayerComparator implements Comparator<NetworkPlayerInfo> {
        private PlayerComparator() {}

        public int compare(NetworkPlayerInfo p_compare_1_, NetworkPlayerInfo p_compare_2_) {
            ScorePlayerTeam scoreplayerteam = p_compare_1_.getPlayerTeam();
            ScorePlayerTeam scoreplayerteam1 = p_compare_2_.getPlayerTeam();
            return ComparisonChain.start().compareTrueFirst((p_compare_1_.getGameType() != WorldSettings.GameType.SPECTATOR), (p_compare_2_.getGameType() != WorldSettings.GameType.SPECTATOR)).compare((scoreplayerteam != null) ? scoreplayerteam.getRegisteredName() : "", (scoreplayerteam1 != null) ? scoreplayerteam1.getRegisteredName() : "").compare(p_compare_1_.getGameProfile().getName(), p_compare_2_.getGameProfile().getName()).result();
        }
    }
}
