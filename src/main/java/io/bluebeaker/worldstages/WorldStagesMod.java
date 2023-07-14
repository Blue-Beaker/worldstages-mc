package io.bluebeaker.worldstages;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerAboutToStartEvent;

import org.apache.logging.log4j.Logger;

@Mod(modid = WorldStagesMod.MODID, name = WorldStagesMod.NAME, version = WorldStagesMod.VERSION)
public class WorldStagesMod {
    public static final String MODID = "worldstages";
    public static final String NAME = "World Stages";
    public static final String VERSION = "1.0";

    private static Logger logger;

    public WorldStagesMod() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    }
    @EventHandler
    public void onWorldLoad(FMLServerAboutToStartEvent event){
        ConfigStorage.instance.load();
    }
    public static void logInfo(String message) {
        logger.info(message);
    }
}
