package io.bluebeaker.worldstages;

import java.util.HashSet;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;

public class WorldStagesWorldSavedData extends WorldSavedData {
    private static final String DATA_NAME = WorldStagesMod.MODID + "_active_stages";
    public HashSet<String> stages = new HashSet<String>();

    public WorldStagesWorldSavedData() {
        super(DATA_NAME);
    }

    public WorldStagesWorldSavedData(String s) {
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

    public static WorldStagesWorldSavedData get(World world) {
        MapStorage storage = world.getMapStorage();
        if(storage==null){return null;}
        WorldStagesWorldSavedData instance = (WorldStagesWorldSavedData) storage.getOrLoadData(WorldStagesWorldSavedData.class,
                DATA_NAME);
        if (instance == null) {
            instance = new WorldStagesWorldSavedData();
            storage.setData(DATA_NAME, instance);
        }
        return instance;
    }

    public void addStage(String stage){
        stages.add(stage);
        this.markDirty();
    }
    public void removeStage(String stage){
        stages.remove(stage);
        this.markDirty();
    }
}
