package io.bluebeaker.worldstages;

import io.bluebeaker.worldstages.network.WorldStagesMessage;
import io.bluebeaker.worldstages.network.WorldStagesPacketHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.common.MinecraftForge;

import java.util.HashSet;

public class WorldStagesSavedData extends WorldSavedData implements IWorldStagesStorage{
    private static final String DATA_NAME = WorldStagesMod.MODID + "_active_stages";
    public HashSet<String> stages = new HashSet<String>();
    public WorldStagesSavedData() {
        super(DATA_NAME);
    }

    public WorldStagesSavedData(String s) {
        super(s);
    }

    public boolean isStageActive(String stage){
        return stages.contains(stage);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        stages.clear();
        try {
            if (nbt.getTagId(DATA_NAME) == 9) {
                NBTTagList list = nbt.getTagList(DATA_NAME, 8);
                list.forEach((value) -> {
                    stages.add(((NBTTagString) value).getString());
                });
            }
        } catch (Exception e) {
            WorldStagesMod.logInfo(e.getStackTrace().toString());
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        NBTTagList stagesNBT = new NBTTagList();
        stages.forEach((value) -> {
            stagesNBT.appendTag(new NBTTagString(value));
        });
        compound.setTag(DATA_NAME, stagesNBT);
        WorldStagesMod.logInfo("Saving data:"+compound.toString());
        return compound;
    }

    public static IWorldStagesStorage get(World world) {
        if(world.isRemote){return ClientWorldStages.get();}
        MapStorage storage = world.getMapStorage();
        if(storage==null){return null;}
        WorldStagesSavedData instance = (WorldStagesSavedData) storage.getOrLoadData(WorldStagesSavedData.class,
                DATA_NAME);
        if (instance == null) {
            instance = new WorldStagesSavedData();
            storage.setData(DATA_NAME, instance);
            WorldStagesMod.getLogger().info("Creating WorldStagesSavedData "+Integer.toHexString(instance.hashCode())+" for "+Integer.toHexString(world.hashCode()));
        }
        return instance;
    }
    public void addStage(String stage){
        stages.add(stage);
        this.markDirty();
        WorldStagesPacketHandler.INSTANCE.sendToAll(new WorldStagesMessage(stages));
        World world = WorldStagesMod.getCurrentWorld();
        if(world!=null)
            MinecraftForge.EVENT_BUS.post(new WorldStageEvent.Add(world,stages,stage));
    }
    public void removeStage(String stage){
        stages.remove(stage);
        this.markDirty();
        WorldStagesPacketHandler.INSTANCE.sendToAll(new WorldStagesMessage(stages));
        World world = WorldStagesMod.getCurrentWorld();
        if(world!=null)
            MinecraftForge.EVENT_BUS.post(new WorldStageEvent.Remove(world,stages,stage));
    }
    public void notifyPlayer(EntityPlayer player){
        WorldStagesPacketHandler.INSTANCE.sendTo(new WorldStagesMessage(stages),(EntityPlayerMP)player);
    }

    @Override
    public HashSet<String> getStages() {
        return this.stages;
    }
}
