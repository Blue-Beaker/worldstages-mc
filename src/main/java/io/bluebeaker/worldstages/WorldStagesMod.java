package io.bluebeaker.worldstages;

import io.bluebeaker.worldstages.network.WorldStagesMessage;
import io.bluebeaker.worldstages.network.WorldStagesMessage.WorldStagesMessageHandler;
import io.bluebeaker.worldstages.network.WorldStagesPacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Config.Type;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;

@Mod(modid = Tags.MOD_ID, name = Tags.MOD_NAME, version = Tags.VERSION, acceptableRemoteVersions = Tags.ACCEPTABLE_VERSIONS)
public class WorldStagesMod {
    public static final String MODID = Tags.MOD_ID;
    public static final String NAME = Tags.MOD_NAME;
    public static final String VERSION = Tags.VERSION;

    private static Logger logger;

    private MinecraftServer server;
    private static @Nullable World currentWorld;

    public WorldStagesMod() {
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(BlockEvents.class);
        MinecraftForge.EVENT_BUS.register(StageChecker.instance);
    }

    @Nullable
    public static World getCurrentWorld() {
        return currentWorld;
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
    }

    @SubscribeEvent
    public void onClientConnect(FMLNetworkEvent.ClientConnectedToServerEvent event) {
        WorldStagesPacketHandler.INSTANCE.registerMessage(WorldStagesMessageHandler.class, WorldStagesMessage.class, 0,
                Side.CLIENT);
    }
    @SubscribeEvent
    public void onClientDisconnect(FMLNetworkEvent.ClientDisconnectionFromServerEvent event){
        ClientWorldStages.clear();
    }

    @SubscribeEvent
    public void onConfigChangedEvent(OnConfigChangedEvent event) {
        if (event.getModID().equals(MODID)) {
            ConfigManager.sync(MODID, Type.INSTANCE);
            ConfigStorage.instance.load();
        }
    }

    @EventHandler
    public void onServerStarting(FMLServerStartingEvent event) {
        WorldStagesPacketHandler.INSTANCE.registerMessage(WorldStagesMessageHandler.class, WorldStagesMessage.class, 0,
                Side.CLIENT);
        ConfigStorage.instance.load();
        event.registerServerCommand(new WorldStagesCommand());
        this.server=event.getServer();
    }

    @EventHandler
    public void onServerStarted(FMLServerStartedEvent event){
        currentWorld=this.server.getWorld(0);
    }
    @EventHandler
    public void onServerStopping(FMLServerStoppingEvent event){
        currentWorld=null;
    }

    @SubscribeEvent
    public void onClientJoinServer(PlayerLoggedInEvent event) {
        if (event.player.world.isRemote)
            return;
        ((WorldStagesSavedData) WorldStagesSavedData.get(event.player.world)).notifyPlayer(event.player);
    }

    @SubscribeEvent
    public void onStageChanged(WorldStageEvent event){
        if(WorldStagesConfig.debug){
            if(!event.getWorld().isRemote){
                for(EntityPlayerMP player: server.getPlayerList().getPlayers()){
                    player.sendMessage(new TextComponentString("server:"+event.stages.toString()));
                }
            }
        }
    }
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onClientStageChanged(WorldStageEvent event){
        if(WorldStagesConfig.debug){
            if(event.getWorld().isRemote){
                Minecraft.getMinecraft().player.sendStatusMessage(new TextComponentString("client:"+event.stages.toString()),false);
            }
        }
    }

    public static void logInfo(String message) {
        logger.info(message);
    }

    public static void logError(String message) {
        logger.error(message);
    }
    public static Logger getLogger(){
        return logger;
    }
}
