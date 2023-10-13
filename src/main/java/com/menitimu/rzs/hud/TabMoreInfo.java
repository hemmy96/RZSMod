package com.menitimu.rzs.hud;

import cc.polyfrost.oneconfig.config.annotations.Exclude;
import cc.polyfrost.oneconfig.config.annotations.Text;
import cc.polyfrost.oneconfig.config.core.OneColor;
import cc.polyfrost.oneconfig.hud.Hud;
import cc.polyfrost.oneconfig.libs.universal.UMatrixStack;
import cc.polyfrost.oneconfig.platform.Platform;
import cc.polyfrost.oneconfig.renderer.TextRenderer;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Ordering;
import com.menitimu.rzs.stuff.ApiManager;
import com.menitimu.rzs.util.FlexUrLuck;
import com.menitimu.rzs.util.RandomStuff;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Team;
import net.minecraft.world.WorldSettings;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.*;

public class TabMoreInfo extends Hud {
    @Exclude
    private static final Ordering<NetworkPlayerInfo> field_175252_a = Ordering.from(new TabMoreInfo.PlayerComparator());
    @Exclude
    private static List<NetworkPlayerInfo> list = new ArrayList<>();
    @Exclude
    public static Map<String, FlexUrLuck> LcMap = new HashMap<>();
    @Text(name = "API url", category = "General", placeholder = "Leave this blank if you don't know what you are doing", size = 2)
    public static String APIurl = "";
    public TabMoreInfo(){
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        position.setPosition((float) scaledResolution.getScaledWidth() / 2, (float) scaledResolution.getScaledHeight() / 2);
    }
    private static void getString(){
        NetHandlerPlayClient netHandlerPlayClient = Minecraft.getMinecraft().thePlayer.sendQueue;
        if(netHandlerPlayClient == null || netHandlerPlayClient.getPlayerInfoMap() == null) return;
        list = field_175252_a.sortedCopy(netHandlerPlayClient.getPlayerInfoMap());
    }
    public static String getPlayerName(NetworkPlayerInfo networkPlayerInfoIn) {
        return (networkPlayerInfoIn.getDisplayName() != null) ? networkPlayerInfoIn.getDisplayName().getFormattedText() : ScorePlayerTeam.formatPlayerName((Team)networkPlayerInfoIn.getPlayerTeam(), networkPlayerInfoIn.getGameProfile().getName());
    }
    @Override
    protected void draw(UMatrixStack matrices, float x, float y, float scale, boolean example) {
        getString();
        ScoreObjective scoreObjective = Minecraft.getMinecraft().theWorld.getScoreboard().getObjectiveInDisplaySlot(0);
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        position.setPosition((scaledResolution.getScaledWidth() - (104 + (list.size() - 1) * 94) * scale) / 2, 30);
        if(list == null || !Minecraft.getMinecraft().gameSettings.keyBindPlayerList.isKeyDown() || !RandomStuff.onZombies || list.size() > 4) return;
        RandomStuff.renderRect(x, y, 0, (104 + (list.size() - 1) * 94) * scale, RandomStuff.onGame ? 198 * scale : 215 * scale, new OneColor(0, 0, 0, 80));
        for(NetworkPlayerInfo networkPlayerInfo : list) {
            RandomStuff.renderRect(x + 10 * scale, y + 10 * scale, 1D, 84 * scale, 120 * scale, new OneColor(0, 0, 0, 40));
            String name = getPlayerName(networkPlayerInfo);
            TextRenderer.drawScaledString(name + "§r", x + scale * (52 - (float) Platform.getGLPlatform().getStringWidth(name) / 2), y + scale, 0xFFFFFF, TextRenderer.TextType.SHADOW, scale);
            GameProfile gameprofile = networkPlayerInfo.getGameProfile();
            AbstractClientPlayer player = (AbstractClientPlayer) Minecraft.getMinecraft().theWorld.getPlayerEntityByUUID(gameprofile.getId());
            RandomStuff.renderEntity(player, x + 52 * scale, y + 120 * scale, scale * 50, gameprofile);
            if(RandomStuff.onGame || RandomStuff.afterGame) {
                String stripName = RandomStuff.stripText(name);
                if (!LcMap.containsKey(stripName))
                    LcMap.put(stripName, new FlexUrLuck());
                LcMap.get(stripName).renderResult(x + 10 * scale, y + 130 * scale, scale);
                if (scoreObjective != null) {
                    int i = scoreObjective.getScoreboard().getValueFromObjective(gameprofile.getName(), scoreObjective).getScorePoints();
                    String kills = "§lKills:§a" + i + "§r";
                    TextRenderer.drawScaledString(kills, x + (52 - (float) Platform.getGLPlatform().getStringWidth(kills) / 2) * scale, y + 185 * scale, 0xFFFFFF, TextRenderer.TextType.SHADOW, scale);
                } else {
                    String kills = "§lKills:§c???§r";
                    TextRenderer.drawScaledString(kills, x + (52 - (float) Platform.getGLPlatform().getStringWidth(kills) / 2) * scale, y + 185 * scale, 0xFFFFFF, TextRenderer.TextType.SHADOW, scale);
                }
            }  else {
                if (list.size() <= 4) {
                    ApiManager.renderStats(EntityPlayer.getUUID(gameprofile), x + 10 * scale, y + 130 * scale, scale);
                }
            }
            x += 94 * scale;
        }
    }

    @Override
    protected float getWidth(float scale, boolean example) {
        return 104 * scale;
    }

    @Override
    protected float getHeight(float scale, boolean example) {
        return 198 * scale;
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
