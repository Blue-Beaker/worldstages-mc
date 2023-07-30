package io.bluebeaker.worldstages.network;

import io.bluebeaker.worldstages.WorldStagesMod;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

public class WorldStagesPacketHandler {
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(WorldStagesMod.MODID);
}
