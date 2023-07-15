package io.bluebeaker.worldstages;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.common.config.Config.Type;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import org.apache.logging.log4j.Logger;

@Mod(modid = WorldStagesMod.MODID, name = WorldStagesMod.NAME, version = WorldStagesMod.VERSION)
public class WorldStagesMod {
    public static final String MODID = "worldstages";
    public static final String NAME = "World Stages";
    public static final String VERSION = "1.0";

    private static Logger logger;

    public WorldStagesMod() {
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(BlockEvents.class);
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
    }

    @SubscribeEvent
    public void onConfigChangedEvent(OnConfigChangedEvent event)
    {
        if (event.getModID().equals(MODID))
        {
            ConfigManager.sync(MODID, Type.INSTANCE);
            ConfigStorage.instance.load();
        }
    }

    @EventHandler
    public void onServerStarted(FMLServerStartingEvent event)
    {
        ConfigStorage.instance.load();
        event.registerServerCommand(new WorldStagesCommand());
    }
    
    public static void logInfo(String message) {
        logger.info(message);
    }
}
