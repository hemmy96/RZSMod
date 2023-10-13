package com.menitimu.rzs.config;

import cc.polyfrost.oneconfig.config.Config;
import cc.polyfrost.oneconfig.config.annotations.Number;
import cc.polyfrost.oneconfig.config.annotations.*;
import cc.polyfrost.oneconfig.config.core.OneColor;
import cc.polyfrost.oneconfig.config.data.Mod;
import cc.polyfrost.oneconfig.config.data.ModType;
import com.menitimu.rzs.RZSMod;
import com.menitimu.rzs.hud.*;
import com.menitimu.rzs.stuff.ItemStuff;
import com.menitimu.rzs.stuff.NoMoreHolo;
import com.menitimu.rzs.util.Room;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class RZSConfig extends Config {
    @Slider(name = "Player Visibility Distance", min = 0.1F, max = 10.0F, category = "Better Visibility", subcategory = "Player Visibility")
    public static float playerVisDistance = 2.0F;
    @Switch(name = "Render Fire", category = "Better Visibility", subcategory = "Player Visibility")
    public static boolean renderFire = false;
    @Switch(name = "Render NameTag", category = "Better Visibility", subcategory = "Player Visibility")
    public static boolean renderName = true;
    @Switch(name = "Render HitBox", category = "Better Visibility", subcategory = "Player Visibility")
    public static boolean renderHitBox = false;
    @Color(name = "HitBox Color", category = "Better Visibility", subcategory = "Player Visibility")
    public static OneColor hitBoxColor = new OneColor(255, 255, 255, 255);
    @Number(name = "HitBox Thickness", min = 0.1F, max = 10.0F, category = "Better Visibility", subcategory = "Player Visibility")
    public static float hitBoxWidth = 1.0F;
    @Slider(name = "Enemy Visibility", min = 0.1F, max = 10.0F, category = "Better Visibility", subcategory = "Enemy Visibility")
    public static float enemyVisDistance = 1.0F;
    @Slider(name = "Enemy Opacity", category = "Better Visibility", min = 0.01F, max = 1F, subcategory = "Enemy Visibility")
    public static float enemyOpacity = 0.3F;
    @HUD(name = "Round Timer HUD", category = "Time Stuffs", subcategory = "Timer HUDs")
    public RoundTimerHud hud = new RoundTimerHud();
    @HUD(name = "Wave Delay HUD", category = "Time Stuffs", subcategory = "Timer HUDs")
    public WaveDelayHud hud1 = new WaveDelayHud();
    @Switch(name = "Enable Wave Sound", category = "Time Stuffs", subcategory = "Wave Sounds", size = 2)
    public static boolean playWaveSound = true;
    @Switch(name = "Wave Count Down", category = "Time Stuffs", subcategory = "Wave Sounds")
    public static boolean playCountSound = true;
    @Switch(name = "Last Wave Sound", category = "Time Stuffs", subcategory = "Wave Sounds")
    public static boolean playLastWaveSound = true;
    @Text(name = "Wave Sound", placeholder = "default: note.pling", category = "Time Stuffs", subcategory = "Wave Sounds")
    public static String WaveSound = "note.pling";
    @Text(name = "Wave Count Sound", placeholder = "default: note.pling", category = "Time Stuffs", subcategory = "Wave Sounds")
    public static String CountSound = "note.pling";
    @Text(name = "Last Wave Sound", placeholder = "default: random.orb", category = "Time Stuffs", subcategory = "Wave Sounds")
    public static String LastWaveSound = "random.orb";
    @Slider(name = "Wave Sound Volume", min = 0.1F, max = 10F, category = "Time Stuffs", subcategory = "Wave Sounds")
    public static float waveSoundVolume = 2.0F;
    @Slider(name = "Wave Count Sound Volume", min = 0.1F, max = 10F, category = "Time Stuffs", subcategory = "Wave Sounds")
    public static float CountSoundVolume = 2.0F;
    @Slider(name = "Last Wave Sound Volume", min = 0.1F, max = 10F, category = "Time Stuffs", subcategory = "Wave Sounds")
    public static float lastWaveSoundVolume = 2.0F;
    @Slider(name = "Wave Sound Pitch", min = 0.1F, max = 10F, category = "Time Stuffs", subcategory = "Wave Sounds")
    public static float waveSoundPitch = 2.0F;
    @Slider(name = "Wave Count Sound Pitch", min = 0.1F, max = 10F, category = "Time Stuffs", subcategory = "Wave Sounds")
    public static float CountSoundPitch = 0.5F;
    @Slider(name = "Last Wave Sound Pitch", min = 0.1F, max = 10F, category = "Time Stuffs", subcategory = "Wave Sounds")
    public static float lastWaveSoundPitch = 0.5F;
    @Switch(name = "Count Sound Only Last Wave", category = "Time Stuffs", subcategory = "Wave Sounds")
    public static boolean onlyLastWave = false;
    @Switch(name = "Time you took from last Wave", category = "Time Stuffs", subcategory = "Others")
    public static boolean fromLastWave = true;
    @Switch(name = "You reached round X0 but more info", category = "Time Stuffs", subcategory = "Others")
    public static boolean timeMoreInfo = true;
    @Dropdown(name = "Ammo Pattern", category = "Item Stuff",
            options = {"Pattern1", "Pattern2", "UNKNOWN"}
            , subcategory = "MAX AMMO"
            , description = "Pattern1 = 2, 5, 8, 12, 16, 21, 26, X1, X6..\nPattern2 = 3, 6, 9, 13, 17, 22, 27, X2, X7")
    public static int AmmoPattern = 2;
    @Button(name = "Change Pattern", text = "Set!", category = "Item Stuff", subcategory = "MAX AMMO")
    Runnable button = ItemStuff::setMaxPattern;
    @Dropdown(name = "Insta Pattern", category = "Item Stuff",
            options = {"Pattern1", "Pattern2", "UNKNOWN"}
            , subcategory = "INSTA KILL"
            , description = "Pattern1 = 2, 5, 8, 11, 14, 17, 20, 23\nPattern2 = 3, 6, 9, 12, 15, 18, 21, 24")
    public static int InstaPattern = 2;
    @Button(name = "Change Pattern", text = "Set!", category = "Item Stuff", subcategory = "INSTA KILL")
    Runnable button1 = ItemStuff::setInsPattern;
    @Dropdown(name = "Spree Pattern", category = "Item Stuff",
            options = {"Pattern1", "Pattern2", "Pattern3", "UNKNOWN"}
            , subcategory = "SHOPPING SPREE"
            , description = "Pattern1 = 5, 15, 25, X5..\nPattern2 = 6, 16, 26, X6..\nPattern3 = 7, 17, 27, X7..")
    public static int SpreePattern = 3;
    @Button(name = "Change Pattern", text = "Set!", category = "Item Stuff", subcategory = "SHOPPING SPREE")
    Runnable button2 = ItemStuff::setSSPattern;
    @Switch(name = "Item Despawn Banner", category = "Item Stuff", subcategory = "Item Timer")
    public static boolean despawnBanner = true;
    @HUD(name = "Item Despawn Timer HUD", category = "Item Stuff", subcategory = "Item Timer")
    public ItemDespawnTimerHud hud2 = new ItemDespawnTimerHud();
    @HUD(name = "Better Item Active Timer HUD", category = "Item Stuff", subcategory = "Item Timer")
    public ItemActiveTimerHud hud3 = new ItemActiveTimerHud();
    @Switch(name = "Item Active Timer Boss Bar", category = "Item Stuff", subcategory = "Item Timer")
    public static boolean bossBar = true;
    @Switch(name = "Enable Holo Remover", category = "Hologram", subcategory = "Hologram Remover")
    public static boolean holoRemover = true;
    @Slider(name = "Blacklist Size", category = "Hologram", min = 10, max = 100, subcategory = "Hologram Remover")
    public static int listSize = 30;
    @Button(name = "Remove Holo1", text = "<!>REMOVE<!>", category = "Hologram", subcategory = "Hologram Remover")
    Runnable button3 = NoMoreHolo::byebye;
    @Button(name = "Remove Holo2", text = "<!>DANGER<!>", category = "Hologram", subcategory = "Hologram Remover")
    Runnable button4 = NoMoreHolo::byebye2;
    @Button(name = "Remove Holo GOD LEVEL", text = "DON'T PUSH", category = "Hologram", subcategory = "Hologram Remover")
    Runnable button5 = NoMoreHolo::onPushed3;
    @Button(name = "Clear Cache", text = "CLEAR", category = "Hologram", subcategory = "Hologram Remover")
    Runnable button6 = () -> {
        NoMoreHolo.clearExeList();
        Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.GREEN + "キャッシュをクリアしました"));
    };
    @Switch(name = "Show Waypoints", category = "Waypoints", subcategory = "Waypoints")
    public static boolean renderWaypoint = false;
    @Switch(name = "Wave Count on Waypoints", category = "Waypoints", subcategory = "Waypoints")
    public static boolean renderCount = true;
    @Switch(name = "Wave Count Color", category = "Waypoints", subcategory = "Waypoints")
    public static boolean renderCountColor = true;
    @Dropdown(name = "Room Name", category = "Waypoints",
            options = {"Alley", "Office", "Hotel", "Apartment", "Power Station", "Garden", "Roof Top", "Gallery"}
            , subcategory = "Dead End"
    )
    public static int DeRoomId = 0;
    @Button(name = "Open / Close Room", text = "switch!", category = "Waypoints", subcategory = "Dead End")
    Runnable button7 = Room::toggleDeRoom;
    @Dropdown(name = "Room Name", category = "Waypoints",
            options = {"Courtyard", "Mansion", "Library", "Dungeon", "Crypts", "Balcony", "Graveyard", "Great Hall"}
            , subcategory = "Bad Blood"
    )
    public static int BbRoomId = 0;
    @Button(name = "Open / Close Room", text = "switch!", category = "Waypoints", subcategory = "Bad Blood")
    Runnable button8 = Room::toggleBbRoom;
    @Dropdown(name = "Room Name", category = "Waypoints",
            options = {"Park Entrance", "Roller Coaster", "Ferris Wheel", "Bumper Car"}
            , subcategory = "Alien Arcadium"
    )
    public static int AaRoomId = 0;
    @Button(name = "Open / Close Room", text = "switch!", category = "Waypoints", subcategory = "Alien Arcadium")
    Runnable button9 = Room::toggleAaRoom;
    @Header(text = "Waypoints Config", category = "Waypoints", size = 2)
    private boolean ignore10;
    @Switch(name = "Normal Waypoint", category = "Waypoints", subcategory = "Normal Waypoints")
    public static boolean NormalWayp = true;
    @Switch(name = "Render Head", category = "Waypoints", subcategory = "Normal Waypoints")
    public static boolean NormalWaypHS = true;
    @Color(name = "Normal Color", category = "Waypoints", subcategory = "Normal Waypoints")
    public static OneColor NormalWaypColor = new OneColor(255, 255 ,255);
    @Switch(name = "Render Waypoint Without Considering Distance", category = "Waypoints", subcategory = "Normal Waypoints")
    public static boolean NormalWaypDist = false;
    @Switch(name = "UFO Waypoint", category = "Waypoints", subcategory = "UFO Waypoints")
    public static boolean UFOWayp = true;
    @Switch(name = "Render Head", category = "Waypoints", subcategory = "UFO Waypoints")
    public static boolean UFOWaypHS = false;
    @Color(name = "UFO Color", category = "Waypoints", size = 2, subcategory = "UFO Waypoints")
    public static OneColor UFOWaypColor = new OneColor(255, 255, 255);
    @Switch(name = "Blaze Waypoint", category = "Waypoints", subcategory = "Blaze Waypoints")
    public static boolean BlazeWayp = true;
    @Switch(name = "Blaze Head", category = "Waypoints", subcategory = "Blaze Waypoints")
    public static boolean BlazeWaypHS = true;
    @Color(name = "Blaze Color", category = "Waypoints", size = 2, subcategory = "Blaze Waypoints")
    public static OneColor BlazeWaypColor = new OneColor(255, 128, 0);
    @Switch(name = "Golem Waypoint" ,category = "Waypoints", subcategory = "Golem Waypoints")
    public static boolean GolemWayp = true;
    @Switch(name = "Golem Head", category = "Waypoints", subcategory = "Golem Waypoints")
    public static boolean GolemWaypHS = true;
    @Color(name = "Golem Color", category = "Waypoints", size = 2, subcategory = "Golem Waypoints")
    public static OneColor GolemWaypColor = new OneColor(255, 255, 255);
    @Switch(name = "Slime Waypoint", category = "Waypoints", subcategory = "Slime Waypoints")
    public static boolean SlimeWayp = true;
    @Color(name = "Slime Color", category = "Waypoints", subcategory = "Slime Waypoints")
    public static OneColor SlimeWaypColor = new OneColor(0, 255, 0);
    @Switch(name = "Cube Waypoint", category = "Waypoints", subcategory = "Cube Waypoints")
    public static boolean CubeWayp = true;
    @Color(name = "Cube Color", category = "Waypoints", subcategory = "Cube Waypoints")
    public static OneColor CubeWaypColor = new OneColor(0, 0, 0);
    @Switch(name = "Wither Skeleton Waypoint", category = "Waypoints", subcategory = "Wither Skeleton Waypoints")
    public static boolean WEWayp = true;
    @Switch(name = "Wither Skeleton Head", category = "Waypoints", subcategory = "Wither Skeleton Waypoints")
    public static boolean WEWaypHS = true;
    @Color(name = "Wither Skeleton Color", category = "Waypoints", size = 2, subcategory = "Wither Skeleton Waypoints")
    public static OneColor WEWaypColor = new OneColor(0, 0, 0);
    @Switch(name = "Minion Waypoint", category = "Waypoints", subcategory = "Minion Waypoints")
    public static boolean MinionWayp = true;
    @Switch(name = "Minion Head", category = "Waypoints", subcategory = "Minion Waypoints")
    public static boolean MinionWaypHS = true;
    @Color(name = "Minion Color", category = "Waypoints", subcategory = "Minion Waypoints")
    public static OneColor MinionWaypColor = new OneColor(50, 50, 50);
    @Slider(name = "Render Minion Distance", min = 1F, max = 100F, category = "Waypoints", subcategory = "Minion Waypoints")
    public static float MinionWaypDist = 50F;
    @Switch(name = "Puncher Alert", category = "Alert", subcategory = "Alert")
    public static boolean puncherAlert = true;
    @Switch(name = "5 mins", category = "Alert", subcategory = "Alert")
    public static boolean despawnAlert = true;
    @Switch(name = "Downed in a row", category = "Alert", subcategory = "Alert")
    public static boolean downedRow = true;
    @Switch(name = "3 DOWN", category = "Alert", subcategory = "Alert")
    public static boolean threeDown = true;
    @HUD(name = "Rod Order HUD", category = "General")
    public RodOrderHud hud4 = new RodOrderHud();
    @HUD(name = "Tab More Info", category = "General")
    public TabMoreInfo hud5 = new TabMoreInfo();
    @HUD(name = "Team HP HUD", category = "General")
    public TeamHPHud hud6 = new TeamHPHud();
    @HUD(name = "Reloadin HUD", category = "General")
    public ReloadHud hud7 = new ReloadHud();
    @Switch(name = "Play sound when someone revived", category = "General", subcategory = "Revive Support")
    public static boolean playReviveSound = true;
    @Text(name = "Revive Sound", category = "General", subcategory = "Revive Support")
    public static String reviveSound = "random.orb";
    @Slider(name = "Revive Sound Volume", min = 0.1F, max = 10F, category = "General", subcategory = "Revive Support")
    public static float reviveSoundVolume = 2F;
    @Slider(name = "Revive Sound Pitch", min = 0.1F, max = 10F, category = "General", subcategory = "Revive Support")
    public static float reviveSoundPitch = 0.5F;
    @HUD(name = "LC Time HUD", category = "General")
    public LCTimeHud hud8 = new LCTimeHud();
    @Switch(name = "Blaze on Fire", category = "General", subcategory = "Random Stuff")
    public static boolean isBlaze = true;
    public RZSConfig() {
        super(new Mod(RZSMod.NAME, ModType.UTIL_QOL, "/assets/rzs/textures/CoolIcon.png"), RZSMod.MODID + ".json");
        initialize();
        addDependency("hitBoxColor", "disableHitBoxColor",() -> renderHitBox);
        addDependency("hitBoxWidth", "disableHitBoxwidth",() -> renderHitBox);
        addDependency("playCountSound", "disableCountSound",() -> playWaveSound);
        addDependency("playLastWaveSound", "disableLastWaveSound",() -> playWaveSound);
        addDependency("WaveSound", "disableWaveSound",() -> playWaveSound);
        addDependency("CountSound", "disableWaveSound",() -> playWaveSound && playCountSound);
        addDependency("LastWaveSound", "disableLastWaveSound",() -> playWaveSound && playLastWaveSound);
        addDependency("waveSoundVolume", "disableWaveSoundVolume",() -> playWaveSound);
        addDependency("CountSoundVolume", "disableCountSoundVolume",() -> playWaveSound && playCountSound);
        addDependency("lastWaveSoundVolume", "disableLastWaveSoundVolume",() -> playWaveSound && playLastWaveSound);
        addDependency("waveSoundPitch", "disableWaveSoundPitch",() -> playWaveSound);
        addDependency("CountSoundPitch", "disableCountSoundPitch",() -> playWaveSound && playCountSound);
        addDependency("lastWaveSoundPitch", "disableLastWaveSoundPitch",() -> playWaveSound && playLastWaveSound);
        addDependency("onlyLastWave", "disableOnlyLastWave",() -> playWaveSound);
        addDependency("listSize", "disableHoloListSize",() -> holoRemover);
        addDependency("button", "disableHolo1",() -> holoRemover);
        addDependency("button2", "disableHolo2",() -> holoRemover);
        addDependency("button3", "disableHolo3",() -> holoRemover);
        addDependency("button1", "disableHoloCacheClear",() -> holoRemover);
        addDependency("renderCountColor", "disableRenderCountColor",() -> renderCount);
        addDependency("NormalWaypHS", "disableNormalWaypHS",() -> NormalWayp);
        addDependency("NormalWaypColor", "disableNormalWaypColor",() -> NormalWayp);
        addDependency("UFOWaypHS", "disableUFOWaypHS",() -> UFOWayp);
        addDependency("UFOWaypColor", "disableUFOWaypColor",() -> UFOWayp);
        addDependency("BlazeWaypHS", "disableBlazeWaypHS",() -> BlazeWayp);
        addDependency("BlazeWaypColor", "disableBlazeWaypColor",() -> BlazeWayp);
        addDependency("GolemWaypHS", "disableGolemWaypHS",() -> GolemWayp);
        addDependency("GolemWaypColor", "disableGolemWaypColor",() -> GolemWayp);
        addDependency("SlimeWaypColor", "disableSlimeWaypColor",() -> SlimeWayp);
        addDependency("CubeWaypColor", "disableCubeWaypColor",() -> CubeWayp);
        addDependency("WEWaypHS", "disableWEWaypHS",() -> WEWayp);
        addDependency("WEWaypColor", "disableWEWaypColor",() -> WEWayp);
        addDependency("MinionWaypHS", "disableMinionWaypHS",() -> MinionWayp);
        addDependency("MinionWaypColor", "disableMinionWaypColor",() -> MinionWayp);
        addDependency("MinionWaypDist", "disableMinionWaypDist",() -> MinionWayp);
    }
}
