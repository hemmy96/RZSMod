package com.menitimu.rzs.stuff;

import cc.polyfrost.oneconfig.config.core.OneColor;
import com.menitimu.rzs.config.RZSConfig;
import com.menitimu.rzs.util.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import org.lwjgl.opengl.GL11;

public class WaypointManager {
    private static RenderManager renderManager;
    private static Minecraft mc;
    private static FontRenderer fontRenderer;
    private static final int OLD = 1;
    private static final int GIANT = 1 << 1;
    private static final int CUBE = 1 << 2;
    private static final int SLIME = 1 << 3;
    private static final int BLAZE = 1 << 4;
    private static final int GOLEM = 1 << 5;
    private static final int BOSS = 1 << 6;
    private static final int GHAST = 1 << 7;
    private static final int NOITEM = 1 << 8;
    private static final int NO_UFO = 1 << 9;
    private static final int UFO_ONLY = 1 << 10;
    private static final int NO_WINDOW = 1 << 11;
    private static final int WE = 1 << 12;
    private static final int MINION = 1 << 13;
    private static boolean window = false;
    private static boolean ufo = false;
    private static boolean blaze = false;
    private static boolean golem = false;
    private static boolean slime = false;
    private static boolean cube = false;
    private static boolean we = false;
    private static boolean minion = false;
    public static boolean usedWp = false;
    public static double MinAngle;
    public static Waypoint TimerWaypoint = null;
    private static final double[] heightFactor = {0D, 0D, 0D, 0.7D, -1D, -1D, 0.4D, 0D};
    public static void toggle(){
        RZSConfig.renderWaypoint = !RZSConfig.renderWaypoint;
        Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.YELLOW + "Toggled waypoints " + (RZSConfig.renderWaypoint ? (EnumChatFormatting.GREEN + "ON") : (EnumChatFormatting.RED + "OFF")) + EnumChatFormatting.YELLOW + "!"));
    }
    public static void renderMain(RenderWorldLastEvent event){
        mc = Minecraft.getMinecraft();
        renderManager = mc.getRenderManager();
        fontRenderer = renderManager.getFontRenderer();
        if(fontRenderer == null || !RZSConfig.renderWaypoint || !RandomStuff.onGame) return;
        MinAngle = Double.MAX_VALUE;
        TimerWaypoint = null;
        switch (RandomStuff.map){
            case DE:
                for(Waypoint waypoint : WaypointData.DEData){
                    if(waypoint.isActive() && Room.rooms[waypoint.getRoom()])
                        renderWaypoint(waypoint, event.partialTicks);
                }
                break;
            case BB:
                for(Waypoint waypoint : WaypointData.BBData){
                    if(waypoint.isActive() && Room.rooms[waypoint.getRoom()])
                        renderWaypoint(waypoint, event.partialTicks);
                }
                break;
            case AA:
                for(Waypoint waypoint : WaypointData.AAData){
                    if(waypoint.isActive() && Room.rooms[waypoint.getRoom()])
                        renderWaypoint(waypoint, event.partialTicks);
                }
                break;
        }
        if(RZSConfig.renderCount) {
            long delay = RoundStuff.getDelayforWayp();
            if (TimerWaypoint != null && delay <= 100) {
                String text = String.format("%d.%d", delay / 20, (delay % 20) / 2);
                RenderTimer(text, TimerWaypoint, event.partialTicks, RoundStuff.getColorforWayp());
            }
        }
    }
    public static void renderWaypoint(Waypoint waypoint, float partialTick){
        usedWp = true;
        Point point = RandomStuff.calcCord(waypoint.getPoint(), partialTick);
        double x = point.getX();
        double y = point.getY() + 1D;
        double z = point.getZ();
        float distance = (float)Math.sqrt((x + 0.5D) * (x + 0.5D) + y * y + (z + 0.5D) * (z + 0.5D));
        /*
        case 0: window
        case 1: ufo
        case 2: blaze
        case 3: golem
        case 4: slime
        case 5: cube
        case 6: Wither Skeleton
        case 7: minion
         */
        switch (waypoint.getType()) {
            case 0:
                if((RandomStuff.calcDistancePoint(waypoint.getPoint()) <= 2500D || RZSConfig.NormalWaypDist ) && window && RZSConfig.NormalWayp) {
                    RenderBlock(x, y, z, 0.6F, 1.95F, distance, RZSConfig.NormalWaypColor.getRed(), RZSConfig.NormalWaypColor.getGreen(), RZSConfig.NormalWaypColor.getBlue());
                    if(RZSConfig.NormalWaypHS)
                        RenderHead(x, y, z, -0.7825D, distance);
                    double angle = RandomStuff.calcAngle(x, y, z);
                    if(MinAngle > angle){
                        MinAngle = angle;
                        TimerWaypoint = waypoint;
                    }
                }
                break;
            case 1:
                if(ufo && RZSConfig.UFOWayp) {
                    RenderBlock(x, y, z, 0.6F, 1.95F, distance, RZSConfig.UFOWaypColor.getRed(), RZSConfig.UFOWaypColor.getGreen(), RZSConfig.UFOWaypColor.getBlue());
                    if(RZSConfig.UFOWaypHS)
                        RenderHead(x, y, z, -0.7825D, distance);
                    double angle = RandomStuff.calcAngle(x, y, z);
                    if(MinAngle > angle){
                        MinAngle = angle;
                        TimerWaypoint = waypoint;
                    }
                }
                break;
            case 2:
                if(blaze && RZSConfig.BlazeWayp) {
                    RenderBlock(x, y, z, 0.6F, 1.8F, distance, RZSConfig.BlazeWaypColor.getRed(), RZSConfig.BlazeWaypColor.getGreen(), RZSConfig.BlazeWaypColor.getBlue());
                    if(RZSConfig.BlazeWaypHS)
                        RenderHead(x, y, z, -0.53D, distance);
                    double angle = RandomStuff.calcAngle(x, y, z);
                    if(MinAngle > angle){
                        MinAngle = angle;
                        TimerWaypoint = waypoint;
                    }
                }
                break;
            case 3:
                if(golem && RZSConfig.GolemWayp) {
                    RenderBlock(x, y, z, 1.4F, 2.7F, distance, RZSConfig.GolemWaypColor.getRed(), RZSConfig.GolemWaypColor.getGreen(), RZSConfig.GolemWaypColor.getBlue());
                    if(RZSConfig.GolemWaypHS)
                        RenderHead(x, y, z, -1.42D, distance);
                    double angle = RandomStuff.calcAngle(x, y, z);
                    if(MinAngle > angle){
                        MinAngle = angle;
                        TimerWaypoint = waypoint;
                    }
                }
                break;
            case 4:
                if(slime && RZSConfig.SlimeWayp) {
                    RenderBlock(x, y, z, 1F, 1F, distance, RZSConfig.SlimeWaypColor.getRed(), RZSConfig.SlimeWaypColor.getGreen(), RZSConfig.SlimeWaypColor.getBlue());
                    double angle = RandomStuff.calcAngle(x, y, z);
                    if(MinAngle > angle){
                        MinAngle = angle;
                        TimerWaypoint = waypoint;
                    }
                }
                break;
            case 5:
                if(cube && RZSConfig.CubeWayp) {
                    RenderBlock(x, y, z, 1F, 1F, distance, RZSConfig.CubeWaypColor.getRed(), RZSConfig.CubeWaypColor.getGreen(), RZSConfig.CubeWaypColor.getBlue());
                    double angle = RandomStuff.calcAngle(x, y, z);
                    if(MinAngle > angle){
                        MinAngle = angle;
                        TimerWaypoint = waypoint;
                    }
                }
                break;
            case 6:
                if(we && RZSConfig.WEWayp) {
                    RenderBlock(x, y, z, 0.7F, 2.4F, distance, RZSConfig.WEWaypColor.getRed(), RZSConfig.WEWaypColor.getGreen(), RZSConfig.WEWaypColor.getBlue());
                    if(RZSConfig.WEWaypHS)
                        RenderHead(x, y, z, -1.165D, distance);
                    double angle = RandomStuff.calcAngle(x, y, z);
                    if(MinAngle > angle){
                        MinAngle = angle;
                        TimerWaypoint = waypoint;
                    }
                }
                break;
            case 7:
                if(RandomStuff.calcDistancePoint(waypoint.getPoint()) <= Math.pow(RZSConfig.MinionWaypDist, 2D) && minion && RZSConfig.MinionWayp) {
                    RenderBlock(x, y, z, 0.6F, 1.95F, distance, RZSConfig.MinionWaypColor.getRed(), RZSConfig.MinionWaypColor.getGreen(), RZSConfig.MinionWaypColor.getBlue());
                    if(RZSConfig.MinionWaypHS)
                        RenderHead(x, y, z, -0.7825D, distance);
                    double angle = RandomStuff.calcAngle(x, y, z);
                    if(MinAngle > angle){
                        MinAngle = angle;
                        TimerWaypoint = waypoint;
                    }
                }
                break;
        }
    }
    public static void onRoundInit(Round roundIn){
        window = true;
        ufo = true;
        if(roundIn.hasFlag(UFO_ONLY))
            window = false;
        if(roundIn.hasFlag(NO_UFO))
            ufo = false;
        if(roundIn.hasFlag(NO_WINDOW))
            window = false;
        blaze = roundIn.hasFlag(BLAZE);
        golem = roundIn.hasFlag(GOLEM);
        slime = roundIn.hasFlag(SLIME);
        cube = roundIn.hasFlag(CUBE);
        we = roundIn.hasFlag(WE);
        minion = roundIn.hasFlag(MINION);
    }
    public static void RenderTimer(String name, Waypoint waypoint, float partialTick, OneColor color){
        Point point = RandomStuff.calcCord(waypoint.getPoint(), partialTick);
        double x = point.getX();
        double y = point.getY() + 1D + heightFactor[waypoint.getType()];
        double z = point.getZ();
        float distance = (float)Math.sqrt((x + 0.5D) * (x + 0.5D) + y * y + (z + 0.5D) * (z + 0.5D));
        float scale = 0.02666667F;
        if (distance > 3.0F) {
            int renderDistance = mc.gameSettings.renderDistanceChunks * 16;
            if (distance > renderDistance) {
                float scaleFactor = renderDistance / distance;
                x *= scaleFactor;
                y *= scaleFactor;
                z *= scaleFactor;
                scale *= renderDistance / 3.0F;
            } else {
                scale *= distance / 3.0F;
            }
        }
        int red = waypoint.getColor().getRed();
        int green = waypoint.getColor().getGreen();
        int blue = waypoint.getColor().getBlue();
        GlStateManager.pushMatrix();
        GL11.glNormal3f(0.0F, 1.0F, 0.0F);
        GlStateManager.translate(x + 0.5D, y + 1 + distance / 9, z + 0.5D);
        GlStateManager.rotate(-renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(renderManager.playerViewX * (float)((mc.gameSettings.thirdPersonView == 2) ? -1 : 1), 1.0F, 0.0F, 0.0F);
        GlStateManager.scale(-scale, -scale, scale);
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        GlStateManager.depthMask(false);
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        int j = fontRenderer.getStringWidth(name) / 2;
        GL11.glLineWidth(1F);
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        worldrenderer.pos((-j - 1), -1, 0.0D).color(red, green, blue, 64).endVertex();
        worldrenderer.pos((-j - 1), 8, 0.0D).color(red, green, blue, 64).endVertex();
        worldrenderer.pos((j + 1), 8, 0.0D).color(red, green, blue, 64).endVertex();
        worldrenderer.pos((j + 1), -1, 0.0D).color(red, green, blue, 64).endVertex();
        tessellator.draw();
        worldrenderer.begin(3, DefaultVertexFormats.POSITION_COLOR);
        worldrenderer.pos((-j - 1), -1, 0.0D).color(red, green, blue, 128).endVertex();
        worldrenderer.pos((-j - 1), 8, 0.0D).color(red, green, blue, 128).endVertex();
        worldrenderer.pos((j + 1), 8, 0.0D).color(red, green, blue, 128).endVertex();
        worldrenderer.pos((j + 1), -1, 0.0D).color(red, green, blue, 128).endVertex();
        worldrenderer.pos((-j - 1), -1, 0.0D).color(red, green, blue, 128).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        fontRenderer.drawStringWithShadow(name, (float) -fontRenderer.getStringWidth(name) / 2, 0, color.getRGB());
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.disableBlend();
        GlStateManager.depthMask(true);
        GlStateManager.enableDepth();
        GlStateManager.popMatrix();
    }
    public static void RenderName(String name, double x, double y, double z, float distance, float scale, int red, int green, int blue){
        GlStateManager.pushMatrix();
        GL11.glNormal3f(0.0F, 1.0F, 0.0F);
        GlStateManager.translate(x + 0.5D, y + 1 + distance / 9, z + 0.5D);
        GlStateManager.rotate(-renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(renderManager.playerViewX * (float)((mc.gameSettings.thirdPersonView == 2) ? -1 : 1), 1.0F, 0.0F, 0.0F);
        GlStateManager.scale(-scale, -scale, scale);
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        GlStateManager.depthMask(false);
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        int j = fontRenderer.getStringWidth(name) / 2;
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        worldrenderer.pos((-j - 1), -1, 0.0D).color(red, green, blue, 64).endVertex();
        worldrenderer.pos((-j - 1), 8, 0.0D).color(red, green, blue, 64).endVertex();
        worldrenderer.pos((j + 1), 8, 0.0D).color(red, green, blue, 64).endVertex();
        worldrenderer.pos((j + 1), -1, 0.0D).color(red, green, blue, 64).endVertex();
        tessellator.draw();
        worldrenderer.begin(3, DefaultVertexFormats.POSITION_COLOR);
        worldrenderer.pos((-j - 1), -1, 0.0D).color(red, green, blue, 128).endVertex();
        worldrenderer.pos((-j - 1), 8, 0.0D).color(red, green, blue, 128).endVertex();
        worldrenderer.pos((j + 1), 8, 0.0D).color(red, green, blue, 128).endVertex();
        worldrenderer.pos((j + 1), -1, 0.0D).color(red, green, blue, 128).endVertex();
        worldrenderer.pos((-j - 1), -1, 0.0D).color(red, green, blue, 128).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        fontRenderer.drawString(name, -fontRenderer.getStringWidth(name) / 2, 0, 553648127);
        fontRenderer.drawString(name, -fontRenderer.getStringWidth(name) / 2, 0, -1);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.disableBlend();
        GlStateManager.depthMask(true);
        GlStateManager.enableDepth();
        GlStateManager.popMatrix();
    }

    public static void RenderBlock(double x, double y, double z, float width, float height, float distance, int red, int green, int blue){
        GlStateManager.pushMatrix();
        GlStateManager.translate(x - width / 2 + 0.5D , y + height - 1, z - width / 2 + 0.5D);
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        GlStateManager.depthMask(false);
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        worldrenderer.pos(0, 0, 0).color(red, green, blue, 96).endVertex();
        worldrenderer.pos(width, 0, 0).color(red, green, blue, 96).endVertex();
        worldrenderer.pos(width, 0, width).color(red, green, blue, 96).endVertex();
        worldrenderer.pos(0, 0, width).color(red, green, blue, 96).endVertex();
        worldrenderer.pos(width, -height, width).color(red, green, blue, 96).endVertex();
        worldrenderer.pos(0, -height, width).color(red, green, blue, 96).endVertex();
        worldrenderer.pos(0, 0, width).color(red, green, blue, 96).endVertex();
        worldrenderer.pos(width, 0, width).color(red, green, blue, 96).endVertex();
        worldrenderer.pos(0, -height, width).color(red, green, blue, 96).endVertex();
        worldrenderer.pos(0, -height, 0).color(red, green, blue, 96).endVertex();
        worldrenderer.pos(0, 0, 0).color(red, green, blue, 96).endVertex();
        worldrenderer.pos(0, 0, width).color(red, green, blue, 96).endVertex();
        worldrenderer.pos(0, -height, 0).color(red, green, blue, 96).endVertex();
        worldrenderer.pos(width, -height, 0).color(red, green, blue, 96).endVertex();
        worldrenderer.pos(width, 0, 0).color(red, green, blue, 96).endVertex();
        worldrenderer.pos(0, 0, 0).color(red, green, blue, 96).endVertex();
        worldrenderer.pos(width, -height, 0).color(red, green, blue, 96).endVertex();
        worldrenderer.pos(width, -height, width).color(red, green, blue, 96).endVertex();
        worldrenderer.pos(width, 0, width).color(red, green, blue, 96).endVertex();
        worldrenderer.pos(width, 0, 0).color(red, green, blue, 96).endVertex();
        worldrenderer.pos(0, -height, width).color(red, green, blue, 96).endVertex();
        worldrenderer.pos(width, -height, width).color(red, green, blue, 96).endVertex();
        worldrenderer.pos(width, -height, 0).color(red, green, blue, 96).endVertex();
        worldrenderer.pos(0, -height, 0).color(red, green, blue, 96).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.depthMask(true);
        GlStateManager.enableDepth();
        GlStateManager.popMatrix();
    }

    public static void RenderHead(double x, double y, double z, double eyeheight, float distance){
        GlStateManager.pushMatrix();
        GlStateManager.translate(x + 0.5 , y - eyeheight, z + 0.5);
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        GlStateManager.depthMask(false);
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        worldrenderer.pos(-0.1, 0.1, -0.1).color(255, 0, 0, 128).endVertex();
        worldrenderer.pos(0.1, 0.1, -0.1).color(255, 0, 0, 128).endVertex();
        worldrenderer.pos(0.1, 0.1, 0.1).color(255, 0, 0, 128).endVertex();
        worldrenderer.pos(-0.1, 0.1, 0.1).color(255, 0, 0, 128).endVertex();
        worldrenderer.pos(0.1, -0.1, 0.1).color(255, 0, 0, 128).endVertex();
        worldrenderer.pos(-0.1, -0.1, 0.1).color(255, 0, 0, 128).endVertex();
        worldrenderer.pos(-0.1, 0.1, 0.1).color(255, 0, 0, 128).endVertex();
        worldrenderer.pos(0.1, 0.1, 0.1).color(255, 0, 0, 128).endVertex();
        worldrenderer.pos(-0.1, -0.1, 0.1).color(255, 0, 0, 128).endVertex();
        worldrenderer.pos(-0.1, -0.1, -0.1).color(255, 0, 0, 128).endVertex();
        worldrenderer.pos(-0.1, 0.1, -0.1).color(255, 0, 0, 128).endVertex();
        worldrenderer.pos(-0.1, 0.1, 0.1).color(255, 0, 0, 128).endVertex();
        worldrenderer.pos(-0.1, -0.1, -0.1).color(255, 0, 0, 128).endVertex();
        worldrenderer.pos(0.1, -0.1, -0.1).color(255, 0, 0, 128).endVertex();
        worldrenderer.pos(0.1, 0.1, -0.1).color(255, 0, 0, 128).endVertex();
        worldrenderer.pos(-0.1, 0.1, -0.1).color(255, 0, 0, 128).endVertex();
        worldrenderer.pos(0.1, -0.1, -0.1).color(255, 0, 0, 128).endVertex();
        worldrenderer.pos(0.1, -0.1, 0.1).color(255, 0, 0, 128).endVertex();
        worldrenderer.pos(0.1, 0.1, 0.1).color(255, 0, 0, 128).endVertex();
        worldrenderer.pos(0.1, 0.1, -0.1).color(255, 0, 0, 128).endVertex();
        worldrenderer.pos(-0.1, -0.1, 0.1).color(255, 0, 0, 128).endVertex();
        worldrenderer.pos(0.1, -0.1, 0.1).color(255, 0, 0, 128).endVertex();
        worldrenderer.pos(0.1, -0.1, -0.1).color(255, 0, 0, 128).endVertex();
        worldrenderer.pos(-0.1, -0.1, -0.1).color(255, 0, 0, 128).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.depthMask(true);
        GlStateManager.enableDepth();
        GlStateManager.popMatrix();
    }
}
