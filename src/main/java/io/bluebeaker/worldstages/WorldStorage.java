package io.bluebeaker.worldstages;

import java.util.HashSet;

public class WorldStorage {
    public static final WorldStorage instance = new WorldStorage();
    public static HashSet<String> stages = new HashSet<String>();
    public boolean checkStageActive(String stage){
        return stage==null || stages.contains(stage);
    }
    public boolean checkBlockDisabled(String BlockID){
        return !checkStageActive(ConfigStorage.instance.BlockStages.get(BlockID));
    }
    public boolean checkTileEntityDisabled(String TileEntityID){
        return !checkStageActive(ConfigStorage.instance.TileEntityStages.get(TileEntityID));
    }
}
