package com.menitimu.rzs;

import cc.polyfrost.oneconfig.utils.commands.CommandManager;
import com.menitimu.rzs.command.RZSCommand;
import com.menitimu.rzs.config.RZSConfig;
import com.menitimu.rzs.stuff.RoundStuff;
import com.menitimu.rzs.util.RoundData;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.util.logging.Logger;

@Mod(modid = RZSMod.MODID, name = RZSMod.NAME, version = RZSMod.VERSION)
public class RZSMod {
    public static final String MODID = "@ID@";
    public static final String NAME = "@NAME@";
    public static final String VERSION = "@VER@";
    @Mod.Instance(MODID)
    public static RZSMod INSTANCE;
    public static RZSConfig config;
    public static Logger logger = Logger.getLogger("RZS");
    @Mod.EventHandler
    public void onPreInit(FMLPreInitializationEvent event){
        RoundStuff.onPreInit(event.getModConfigurationDirectory());
    }
    @Mod.EventHandler
    public void onInit(FMLInitializationEvent event){
        config = new RZSConfig();
        CommandManager.INSTANCE.registerCommand(new RZSCommand());
        RZSEventHandler.onInit();
        RoundData.initRoundData();
    }
}
