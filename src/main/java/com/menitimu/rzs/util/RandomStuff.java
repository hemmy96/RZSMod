package com.menitimu.rzs.util;

import cc.polyfrost.oneconfig.config.core.OneColor;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.menitimu.rzs.config.RZSConfig;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class RandomStuff {
    private static final Pattern TEXT_COLOR_CODE = Pattern.compile("(?i)§[0-9a-gk-or]");
    private static ArrayList<String> scoreboardLines;
    public static boolean onZombies = false;
    public static boolean onGame = false;
    public static MapType map = MapType.NONE;
    public static Difficulty difficulty = Difficulty.NORMAL;
    public static int maxPlayers = 0;
    public static boolean afterGame = false;
    public static boolean isSBLoaded = false;
    public static Map<AbstractClientPlayer, AbstractClientPlayer> playerMap = new HashMap<>();
    public enum MapType{
        NONE,
        DE,
        BB,
        AA
    }
    public enum Difficulty{
        RIP,
        HARD,
        NORMAL
    }
    public static double calcDistance(Entity entityIn){
        RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();
        return Math.sqrt(Math.pow(renderManager.viewerPosX - entityIn.posX, 2.0D) + Math.pow(renderManager.viewerPosZ - entityIn.posZ, 2.0D));
    }
    public static double calcDistancePoint(Point point){
        EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
        return Math.pow(point.getX() - player.posX + 0.5D, 2.0D) + Math.pow(point.getY() - player.posY + 0.5D, 2.0D) + Math.pow(point.getZ() - player.posZ + 0.5D, 2.0D);
    }
    public static Point calcCord(Point point, float partialTick){
        EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
        double x = point.getX() - (player.lastTickPosX + (player.posX - player.lastTickPosX) * partialTick);
        double y = point.getY() - (player.lastTickPosY + (player.posY - player.lastTickPosY) * partialTick);
        double z = point.getZ() - (player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * partialTick);
        return new Point(x, y, z);
    }
    public static double calcAngle(double x, double y, double z){
        x += 0.5D; y -= 1D; z += 0.5D;
        EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
        float yaw = (player.rotationYaw % 360 + 360) % 360;
        float pitch = player.rotationPitch;
        double Dyaw = Math.abs((Math.toDegrees(Math.atan2(x, z)) % 360 + 360) % 360 + yaw - 360);
        double Dpitch = Math.abs(Math.toDegrees(Math.atan2(Math.sqrt(Math.pow(x, 2D) + Math.pow(z, 2D)), y)) - 90 - pitch);
        return Dyaw + Dpitch;
    }
    public static void renderHitBox(Entity entityIn, double x, double y, double z){
        if(!Minecraft.getMinecraft().getRenderManager().isDebugBoundingBox() && !entityIn.isInvisible()) {
            GlStateManager.depthMask(false);
            GlStateManager.disableTexture2D();
            GlStateManager.disableLighting();
            GlStateManager.disableCull();
            GlStateManager.disableBlend();
            AxisAlignedBB axisAlignedBB = entityIn.getEntityBoundingBox();
            AxisAlignedBB axisAlignedBB1 = new AxisAlignedBB(axisAlignedBB.minX - entityIn.posX + x, axisAlignedBB.minY - entityIn.posY + y, axisAlignedBB.minZ - entityIn.posZ + z, axisAlignedBB.maxX - entityIn.posX + x, axisAlignedBB.maxY - entityIn.posY + y, axisAlignedBB.maxZ - entityIn.posZ + z);
            RenderGlobal.drawOutlinedBoundingBox(axisAlignedBB1, RZSConfig.hitBoxColor.getRed(), RZSConfig.hitBoxColor.getGreen(), RZSConfig.hitBoxColor.getBlue(), RZSConfig.hitBoxColor.getAlpha());
            GL11.glDisable(3042);
            GlStateManager.enableTexture2D();
            GlStateManager.enableLighting();
            GlStateManager.enableCull();
            GlStateManager.disableBlend();
            GlStateManager.depthMask(true);
        }
    }
    public static void renderRect(double x, double y, double z, double width, double height, OneColor color){
        GlStateManager.pushMatrix();
        GlStateManager.translate(0F, 0F,z);
        GL11.glNormal3f(0.0F, 1.0F, 0.0F);
        GlStateManager.disableLighting();
        GlStateManager.depthMask(false);
        GlStateManager.disableDepth();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        GlStateManager.disableTexture2D();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        worldrenderer.pos(x, y, 0.0D).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        worldrenderer.pos(x, y + height, 0.0D).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        worldrenderer.pos(x + width, y + height, 0.0D).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        worldrenderer.pos(x + width, y, 0.0D).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.enableDepth();
        GlStateManager.depthMask(true);
        GlStateManager.disableBlend();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.popMatrix();
    }
    public static void renderLabel(Entity entityIn, String text, double x, double y, double z){
        RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();
        double d0 = entityIn.getDistanceSqToEntity(Minecraft.getMinecraft().thePlayer);
        if (d0 <= (double)(64 * 64)) {
            FontRenderer fontrenderer = renderManager.getFontRenderer();
            float f = 1.6F;
            float f1 = 0.016666668F * f;
            GlStateManager.pushMatrix();
            GlStateManager.translate((float) x, (float) y + entityIn.height, (float) z);
            GL11.glNormal3f(0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(-renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
            GlStateManager.scale(-f1, -f1, f1);
            GlStateManager.disableLighting();
            GlStateManager.depthMask(false);
            GlStateManager.disableDepth();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            Tessellator tessellator = Tessellator.getInstance();
            WorldRenderer worldrenderer = tessellator.getWorldRenderer();
            int j = fontrenderer.getStringWidth(text) / 2;
            GlStateManager.disableTexture2D();
            worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
            worldrenderer.pos(-j - 1, -1, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
            worldrenderer.pos(-j - 1, 8, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
            worldrenderer.pos(j + 1, 8, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
            worldrenderer.pos(j + 1, -1, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
            tessellator.draw();
            GlStateManager.enableTexture2D();
            fontrenderer.drawString(text, -fontrenderer.getStringWidth(text) / 2, 0, 553648127);
            GlStateManager.enableDepth();
            GlStateManager.depthMask(true);
            fontrenderer.drawString(text, -fontrenderer.getStringWidth(text) / 2, 0, -1);
            GlStateManager.enableLighting();
            GlStateManager.disableBlend();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.popMatrix();
        }
    }
    public static void renderImage(ResourceLocation resourceLocation, float x, float y, float width, float height){
        Minecraft.getMinecraft().getTextureManager().bindTexture(resourceLocation);
        Tessellator tessellator = Tessellator.getInstance();
        GlStateManager.enableBlend();
        WorldRenderer worldRenderer = tessellator.getWorldRenderer();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldRenderer.pos(x, y + height, 0).tex(0, 1D).endVertex();
        worldRenderer.pos(x + width, y + height, 0).tex(1D, 1D).endVertex();
        worldRenderer.pos(x + width, y, 0).tex(1D, 0).endVertex();
        worldRenderer.pos(x, y, 0).tex(0, 0).endVertex();
        tessellator.draw();
        GlStateManager.disableBlend();
    }
    public static void renderTexImage(ResourceLocation resourceLocation, float x, float y, float width, float height, float textureX, float textureY){
        Minecraft.getMinecraft().getTextureManager().bindTexture(resourceLocation);
        Tessellator tessellator = Tessellator.getInstance();
        GlStateManager.enableBlend();
        WorldRenderer worldRenderer = tessellator.getWorldRenderer();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldRenderer.pos(x, y + height, 0).tex(0, textureY).endVertex();
        worldRenderer.pos(x + width, y + height, 0).tex(textureX, textureY).endVertex();
        worldRenderer.pos(x + width, y, 0).tex(textureX, 0).endVertex();
        worldRenderer.pos(x, y, 0).tex(0, 0).endVertex();
        tessellator.draw();
        GlStateManager.disableBlend();
    }
    public static void renderTexImage(ResourceLocation resourceLocation, float x, float y, float u, float v, int uWidth, int vHeight, int width, int height, float tileWidth, float tileHeight){
        Minecraft.getMinecraft().getTextureManager().bindTexture(resourceLocation);
        float f = 1.0F / tileWidth;
        float f1 = 1.0F / tileHeight;
        Tessellator tessellator = Tessellator.getInstance();
        GlStateManager.enableBlend();
        WorldRenderer worldRenderer = tessellator.getWorldRenderer();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldRenderer.pos(x, y + height, 0).tex((u * f), ((v + vHeight) * f1)).endVertex();
        worldRenderer.pos(x + width, y + height, 0).tex(((u + uWidth) * f), ((v + vHeight) * f1)).endVertex();
        worldRenderer.pos(x + width, y, 0).tex(((u + uWidth) * f), (v * f1)).endVertex();
        worldRenderer.pos(x, y, 0).tex((u * f), (v * f1)).endVertex();
        tessellator.draw();
        GlStateManager.disableBlend();
    }
    public static void renderEntity(AbstractClientPlayer player, float x, float y, double scale, GameProfile gameProfile){
        if(player == null || gameProfile == null) return;
        AbstractClientPlayer player1;
        if(!playerMap.containsKey(player)) {
            player1 = new AbstractClientPlayer(Minecraft.getMinecraft().theWorld, gameProfile) {
                @Override
                public boolean isSpectator() {
                    return super.isSpectator();
                }
                @Override
                public String getSkinType() {
                    return player.getSkinType();
                }
                @Override
                public boolean hasSkin() {
                    return player.hasSkin();
                }
                @Override
                public ResourceLocation getLocationSkin() {
                    return player.getLocationSkin();
                }
                @Override
                public boolean isWearing(EnumPlayerModelParts p_175148_1_) {
                    if(p_175148_1_ == EnumPlayerModelParts.CAPE)
                        return false;
                    else
                        return player.isWearing(p_175148_1_);
                }
            };
            player1.setEntityId(Integer.MAX_VALUE - playerMap.size() - 1);
            playerMap.put(player, player1);
        }
        player1 = playerMap.get(player);
        GlStateManager.enableColorMaterial();
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y,  0.0F);
        GlStateManager.scale(-scale, scale, scale);
        GlStateManager.rotate(180F, 0.0F, 0.0F, 1.0F);
        RenderHelper.enableStandardItemLighting();
        RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
        rendermanager.setRenderShadow(false);
        rendermanager.renderEntityWithPosYaw(player1, 0D, 0D, 0D, 0F, 1F);
        rendermanager.setRenderShadow(true);
        GlStateManager.popMatrix();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.enableDepth();
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.disableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }
    public static void reloadScoreBoard(){
        try {
            Scoreboard scoreboard = Minecraft.getMinecraft().theWorld.getScoreboard();
            ScoreObjective scoreObjective = scoreboard.getObjectiveInDisplaySlot(1);
            if(scoreObjective == null)
                isSBLoaded = false;
            scoreboardLines = new ArrayList<>();
            Collection<Score> scores = scoreboard.getSortedScores(scoreObjective);
            List<Score> filteredScores = scores.stream().filter(p_apply_1_ -> (p_apply_1_.getPlayerName() != null && !p_apply_1_.getPlayerName().startsWith("#"))).collect(Collectors.toList());
            if (filteredScores.size() > 15) {
                scores = Lists.newArrayList(Iterables.skip(filteredScores, scores.size() - 15));
            } else {
                scores = filteredScores;
            }
            Collections.reverse(filteredScores);
            for (Score line : scores) {
                ScorePlayerTeam team = scoreboard.getPlayersTeam(line.getPlayerName());
                String scoreboardLine = RandomStuff.stripText(ScorePlayerTeam.formatPlayerName(team, line.getPlayerName()).trim());
                scoreboardLines.add(scoreboardLine);
            }
            isSBLoaded = true;
        }catch (NullPointerException e){
            isSBLoaded = false;
        }
    }
    public static List<String> getScoreBoard(){
        return scoreboardLines;
    }
    public static String stripText(String string){
        return TEXT_COLOR_CODE.matcher(string).replaceAll("");
    }

}
