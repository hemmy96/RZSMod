package com.menitimu.rzs.config;

import com.menitimu.rzs.stuff.BetterVisibility;
import com.menitimu.rzs.stuff.WaypointManager;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class RZSKeyConfig {
    private static KeyBinding togglePlayerVis = new KeyBinding("Toggle Player Vis", 44, "RZS Mod");
    private static KeyBinding toggleEnemyVis = new KeyBinding("Toggle Enemy Vis", 46, "RZS Mod");
    private static KeyBinding toggleWaypoints = new KeyBinding("Toggle Waypoints", 45, "RZS Mod");
    public static void initKeyBind(){
        ClientRegistry.registerKeyBinding(togglePlayerVis);
        ClientRegistry.registerKeyBinding(toggleEnemyVis);
        ClientRegistry.registerKeyBinding(toggleWaypoints);
    }
    public static void onKeyInput(){
        if(togglePlayerVis.isPressed())
            BetterVisibility.togglePlayerVis();
        if(toggleEnemyVis.isPressed())
            BetterVisibility.toggleEnemyVis();
        if(toggleWaypoints.isPressed())
            WaypointManager.toggle();
    }
}
