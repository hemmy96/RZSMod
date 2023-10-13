package com.menitimu.rzs;

import cc.polyfrost.oneconfig.events.EventManager;
import cc.polyfrost.oneconfig.events.event.*;
import cc.polyfrost.oneconfig.libs.eventbus.Subscribe;
import com.menitimu.rzs.config.RZSConfig;
import com.menitimu.rzs.config.RZSKeyConfig;
import com.menitimu.rzs.hud.LCTimeHud;
import com.menitimu.rzs.hud.TabMoreInfo;
import com.menitimu.rzs.hud.TeamHPHud;
import com.menitimu.rzs.stuff.*;
import com.menitimu.rzs.util.FlexUrLuck;
import com.menitimu.rzs.util.PowerUP;
import com.menitimu.rzs.util.RandomStuff;
import com.menitimu.rzs.util.Room;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.network.play.server.*;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

import java.util.regex.Pattern;

public class RZSEventHandler {
    private static int displayReviveTime = 0;
    private static String lastRevived = "";
    private static final Pattern TIME = Pattern.compile("[0-9:]+");
    public static void onInit(){
        MinecraftForge.EVENT_BUS.register(new RZSEventHandler());
        EventManager.INSTANCE.register(new RZSEventHandler());
        RZSKeyConfig.initKeyBind();
        ItemStuff.initPattern();
        onJoinInit();
    }
    @SubscribeEvent
    public void onJoinServer(FMLNetworkEvent.ClientConnectedToServerEvent event){
        onJoinInit();
    }
    @Subscribe
    public void onServerChange(WorldLoadEvent event){
        onJoinInit();
    }

    public static void onJoinInit(){
        RandomStuff.onZombies = false;
        RandomStuff.onGame = false;
        RandomStuff.afterGame = false;
        RandomStuff.map = RandomStuff.MapType.NONE;
        RandomStuff.difficulty = RandomStuff.Difficulty.NORMAL;
        RandomStuff.maxPlayers = 0;
        InGameTimer.clearTick();
        ItemStuff.initItem();
        NoMoreHolo.clearList();
        RodOrder.displayTime = 0;
        RodOrder.lrTimes = 0;
        RodOrder.lrTimer = 0;
        Room.init();
        RZSConfig.renderWaypoint = false;
        WaypointManager.usedWp = false;
        RandomStuff.playerMap.clear();
        TabMoreInfo.LcMap.clear();
        RELODIN.onJoin();
        TeamHPHud.HpMap.clear();
        RoundStuff.thisRound = null;
        RoundStuff.TimeList.clear();
        displayReviveTime = 0;
        lastRevived = "";
        AlertStuff.onInit();
    }
    @SubscribeEvent
    public void onRenderPlayer(RenderPlayerEvent.Pre event){
        BetterVisibility.PlayerVisibility(event);
    }
    @SubscribeEvent
    public void onRenderBlaze(RenderLivingEvent.Pre<EntityBlaze> event){
        if(event.entity instanceof EntityBlaze && RandomStuff.onGame && RoundStuff.isBlaze){
            EntityBlaze blaze = (EntityBlaze) event.entity;
            try {
                boolean onFire = blaze.getDataWatcher().getWatchableObjectByte(12) != 0;
                blaze.setOnFire(onFire);
            } catch (NullPointerException ignored){
            }
        }
    }
    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event){
        RZSKeyConfig.onKeyInput();
    }
    @SubscribeEvent
    public void onEntityUpdate(LivingEvent.LivingUpdateEvent event){
        if(!RandomStuff.onGame || !(event.entity instanceof EntityArmorStand))
            return;
        EntityArmorStand entity = (EntityArmorStand) event.entity;
        if(!entity.getAlwaysRenderNameTag())
            return;
        ItemStuff.addHolo(entity.getEntityId());
    }
    @SubscribeEvent
    public void onRenderName(RenderLivingEvent.Specials.Post<EntityArmorStand> event){
        if(!(event.entity instanceof EntityArmorStand))
            return;
        EntityArmorStand entity = (EntityArmorStand) event.entity;
        if(RZSConfig.despawnBanner && RandomStuff.onGame)
            ItemStuff.renderTimer(entity, entity.getEntityId(), event.x, event.y, event.z);
        if(NoMoreHolo.count > 0)
            NoMoreHolo.byebye3(entity);
    }
    @SubscribeEvent
    public void onRenderGuiPre(RenderGameOverlayEvent.Pre event){
        if(event.type == RenderGameOverlayEvent.ElementType.BOSSHEALTH && RandomStuff.onGame && RZSMod.config.hud3.isEnabled()) {
            event.setCanceled(true);
        } else if (event.type == RenderGameOverlayEvent.ElementType.PLAYER_LIST && RandomStuff.onZombies && RZSMod.config.hud5.isEnabled()) {
            event.setCanceled(true);
        }
    }
    @SubscribeEvent
    public void onRenderText(RenderGameOverlayEvent.Post event){
        Minecraft mc = Minecraft.getMinecraft();
        FontRenderer fontRenderer = mc.getRenderManager().getFontRenderer();
        if(event.type == RenderGameOverlayEvent.ElementType.TEXT && fontRenderer != null){
            ScaledResolution scaledResolution = new ScaledResolution(mc);
            int screenWidth = scaledResolution.getScaledWidth();
            int screenHeight = scaledResolution.getScaledHeight();
            if(WaypointManager.usedWp)
                fontRenderer.drawStringWithShadow("Waypointが使用されました", screenWidth - fontRenderer.getStringWidth("Waypointが使用されました"), 0, 0xFF0000);
            if(displayReviveTime > 0 && RandomStuff.onGame)
                fontRenderer.drawStringWithShadow("§lREVIVED!", ((float) (screenWidth - fontRenderer.getStringWidth("REVIVED!")) / 2), screenHeight - 80, 0xFFFF55);
            AlertStuff.onRender(screenWidth, screenHeight);
        }
    }
    @SubscribeEvent
    public void onRenderLast(RenderWorldLastEvent event){
        WaypointManager.renderMain(event);
    }
    @Subscribe
    public void onTickEvent(TickEvent event){
        if(event.stage == Stage.END){
            RandomStuff.reloadScoreBoard();
            if(RandomStuff.onGame) {
                if (NoMoreHolo.count > 0)
                    NoMoreHolo.count--;
                if (displayReviveTime > 0)
                    displayReviveTime--;
                if (LCTimeHud.LcTime > 0)
                    LCTimeHud.LcTime--;
                if (RoundStuff.getRoundTick() == 6000 && RZSConfig.despawnAlert)
                    AlertStuff.pushAlert("§cDespawning!§r", 1);
                AlertStuff.onTick();
                InGameTimer.proceedTick(event);
                RoundStuff.tickProcess();
                RodOrder.tickProcess();
            }
        }
    }
    @Subscribe
    public void onChat(ChatReceiveEvent event){
        String message =  RandomStuff.stripText(event.message.getUnformattedText());
        if(!message.contains(":") && RandomStuff.onGame) {
            if (message.contains("activated Max Ammo!")) {
                ItemStuff.setPattern(PowerUP.PowerUpType.AMMO, RoundStuff.getRound(false), false);
                ItemStuff.isSpawnedAmmo = true;
            }else if(message.contains("activated Insta Kill")) {
                ItemStuff.setPattern(PowerUP.PowerUpType.INSTA, RoundStuff.getRound(false), false);
                ItemStuff.InstaTimer = InGameTimer.getTick() + 200L;
                ItemStuff.isSpawnedInsta = true;
            }else if(message.contains("activated Shopping Spree")){
                ItemStuff.setPattern(PowerUP.PowerUpType.SPREE, RoundStuff.getRound(false), false);
                ItemStuff.SpreeTimer = InGameTimer.getTick() + 400L;
                ItemStuff.isSpawnedSpree = true;
            }else if(message.contains("activated Double Gold")){
                ItemStuff.DGTimer = InGameTimer.getTick() + 600L;
            }else if(message.contains("has spawned!")) {
                RodOrder.lrTimes--;
                if(RodOrder.lrTimes == 0)
                    RodOrder.displayTime = 0;
            }else if (message.contains("opened")){
                Room.onRoomOpen(message);
            }else if(message.contains("spawned in the Great Hall")) {
                Room.rooms[7] = true;
            }else if(message.contains("in the Lucky Chest") && !message.contains("claimed")){
                String[] words = message.split(" ");
                if(words[0].equals("You")) {
                    words[0] = RandomStuff.stripText(Minecraft.getMinecraft().thePlayer.getName());
                    if(RandomStuff.map == RandomStuff.MapType.AA && words.length >= 4 && words[3].contains("Puncher") && RZSConfig.puncherAlert)
                        AlertStuff.pushAlert("§c<!>Puncher<!>§r", 1);
                }
                StringBuilder stringBuilder = new StringBuilder();
                for(int i = 1; i < words.length; i++)
                    stringBuilder.append(words[i]);
                if(TabMoreInfo.LcMap.containsKey(words[0]))
                    TabMoreInfo.LcMap.get(words[0]).onLcRoll(stringBuilder.toString());
                else {
                    TabMoreInfo.LcMap.put(words[0], new FlexUrLuck());
                    TabMoreInfo.LcMap.get(words[0]).onLcRoll(stringBuilder.toString());
                }
                if(message.contains("Puncher")) {
                    LCTimeHud.LcResult = 0;
                } else if(message.contains("Zapper")){
                    LCTimeHud.LcResult = 1;
                } else if(message.contains("Digger")){
                    LCTimeHud.LcResult = 2;
                } else if(message.contains("Flamethrower")){
                    LCTimeHud.LcResult = 3;
                } else if(message.contains("Soaker")){
                    LCTimeHud.LcResult = 4;
                } else if(message.contains("Elder")){
                    LCTimeHud.LcResult = 5;
                } else if(message.contains("Blow")){
                    LCTimeHud.LcResult = 6;
                } else if(message.contains("Rainbow")){
                    LCTimeHud.LcResult = 7;
                } else if(message.contains("Double")){
                    LCTimeHud.LcResult = 8;
                } else if(message.contains("Rod")){
                    LCTimeHud.LcResult = 9;
                } else if(message.contains("Heal")){
                    LCTimeHud.LcResult = 10;
                } else return;
                LCTimeHud.LcTime = 200;
            } else if (!message.contains("knocked down") && message.contains("revived")) {
                String[] words = message.split(" ");
                if(words.length < 3)
                    return;
                lastRevived = words[2];
                if(words[2].contains(Minecraft.getMinecraft().thePlayer.getName())){
                    displayReviveTime = 0;
                } else {
                    displayReviveTime = 40;
                    if(RZSConfig.playReviveSound)
                        Minecraft.getMinecraft().thePlayer.playSound(RZSConfig.reviveSound, RZSConfig.reviveSoundVolume, RZSConfig.reviveSoundPitch);
                }
            } else if (message.contains("knocked down") && (message.substring(0, message.indexOf(" ")) + "!").equals(lastRevived) && RZSConfig.downedRow) {
                AlertStuff.pushAlert("§cDowned in a row!§r", 2);
            }
        } else if(message.contains("You completed ")){
            String time = RoundStuff.getTime();
            String diff = "";
            if(message.contains("Round 30") && RoundStuff.thisRound != null) {
                int tick = RoundStuff.getTickFromLastWave();
                if(RandomStuff.map == RandomStuff.MapType.AA)
                    diff = RoundStuff.getDiff(tick, 104);
                else diff = RoundStuff.getDiff(tick, 29);
                RoundStuff.TimeList.add((RandomStuff.map == RandomStuff.MapType.AA ? "§f§lRound 105: §r§a" : "§f§lRound 30: §r§a") + (tick / 20) + "." + ((tick % 20) * 5) + "s " + diff);
                if(RandomStuff.map == RandomStuff.MapType.AA)
                    diff = RoundStuff.getRoundDiff(time, 104);
                else diff = RoundStuff.getRoundDiff(time, 29);
                RoundStuff.savePB();
            }
            RoundStuff.TimeList.add("§7----------------------------------------------------------------");
            StringBuilder stringBuilder = new StringBuilder();
            for (String line : RoundStuff.TimeList)
                stringBuilder.append(line).append("\n");
            if(message.contains("Round 30") && RandomStuff.map == RandomStuff.MapType.AA) {
                stringBuilder.append("§eYou completed §cRound 105 §ein §a").append(time).append("§e! ").append(diff);
            } else
                stringBuilder.append(event.message.getFormattedText().substring(event.message.getFormattedText().indexOf("Y") - 2)).append(" ").append(RoundStuff.Diff);
            if(RZSConfig.timeMoreInfo)
                event.message = new ChatComponentText(stringBuilder.toString());
            RoundStuff.TimeList.clear();
            if(message.contains("Round 10"))
                RoundStuff.TimeList.add("§cRound 10§e started in §a" + time + "§e! " + RoundStuff.Diff);
            else if(message.contains("Round 20"))
                RoundStuff.TimeList.add("§cRound 20§e started in §a" + time + "§e! " + RoundStuff.Diff);
            RoundStuff.TimeList.add("§7----------------------------------------------------------------");
        } else if(message.contains("Difficulty")) {
            if(message.contains("RIP"))
                RandomStuff.difficulty = RandomStuff.Difficulty.RIP;
            else if(message.contains("Hard"))
                RandomStuff.difficulty = RandomStuff.Difficulty.HARD;
        }
    }
    @Subscribe
    public void onPacket(ReceivePacketEvent event){
        if(RandomStuff.onZombies) {
            if (event.packet instanceof S0EPacketSpawnObject) {
                S0EPacketSpawnObject packet = (S0EPacketSpawnObject) event.packet;
                if (packet.getType() == 78)
                    NoMoreHolo.checkID(packet.getEntityID());
                return;
            }
            if (event.packet instanceof S13PacketDestroyEntities) {
                S13PacketDestroyEntities packet = (S13PacketDestroyEntities) event.packet;
                for (int entityID : packet.getEntityIDs()) {
                    NoMoreHolo.setBlackList(entityID);
                    ItemStuff.removeHolo(entityID);
                }
                return;
            }
            if (event.packet instanceof S29PacketSoundEffect) {
                S29PacketSoundEffect soundEffect = (S29PacketSoundEffect) event.packet;
                if (soundEffect.getSoundName().equals("ambient.weather.thunder") && soundEffect.getPitch() != 2.0D)
                    RodOrder.onRod();
                return;
            }
            if(event.packet instanceof S2FPacketSetSlot) {
                S2FPacketSetSlot packet = (S2FPacketSetSlot) event.packet;
                RELODIN.onPacket(packet);
                return;
            }
            if (event.packet instanceof S45PacketTitle) {
                S45PacketTitle title = (S45PacketTitle) event.packet;
                if (title.getType().equals(S45PacketTitle.Type.TITLE)) {
                    String text = RandomStuff.stripText(title.getMessage().getUnformattedText());
                    if (text.contains("Round ") && RandomStuff.onZombies) {
                        try {
                            int round = Integer.parseInt(text.substring(text.lastIndexOf(" ") + 1)) - 1;
                            RoundStuff.onRoundStart(round);
                            RandomStuff.onGame = true;
                        } catch (NumberFormatException e) {
                            RZSMod.logger.warning("Failed to set round! title:" + text);
                        }
                    }
                    if (text.contains("You Win!") || text.contains("Game Over!")) {
                        if(text.contains("Game Over!") && RZSConfig.timeMoreInfo) {
                            RoundStuff.TimeList.add(0, "§a▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬");
                            StringBuilder stringBuilder = new StringBuilder();
                            for (String line : RoundStuff.TimeList)
                                stringBuilder.append(line).append("\n");
                            stringBuilder.append("§7----------------------------------------------------------------").append("\n")
                                    .append("§eYou reached §cRound ").append((RoundStuff.getRound(true) + 1)).append(" §ein §a").append(RoundStuff.getTime()).append("§e!").append("\n")
                                    .append("§a▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬");
                            if(RZSConfig.timeMoreInfo)
                                Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText(stringBuilder.toString()));
                            RoundStuff.TimeList.clear();
                            RoundStuff.savePB();
                        }
                        RandomStuff.onGame = false;
                        RandomStuff.afterGame = true;
                    }
                }
                return;
            }
        }
    }
    @Subscribe
    public void onLocRaw(LocrawEvent event){
        if(event.info.getGameMode().equals("ZOMBIES_DEAD_END")){
            RandomStuff.onZombies = true;
            RandomStuff.map = RandomStuff.MapType.DE;
        } else if(event.info.getGameMode().equals("ZOMBIES_BAD_BLOOD")){
            RandomStuff.onZombies = true;
            RandomStuff.map = RandomStuff.MapType.BB;
        } else if(event.info.getGameMode().equals("ZOMBIES_ALIEN_ARCADIUM")){
            RandomStuff.onZombies = true;
            RandomStuff.map = RandomStuff.MapType.AA;
        } else {
            RandomStuff.onZombies = false;
            RandomStuff.onGame = false;
            RandomStuff.map = RandomStuff.MapType.NONE;
        }
    }
}
