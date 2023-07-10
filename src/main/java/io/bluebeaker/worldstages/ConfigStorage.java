package io.bluebeaker.worldstages;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

public class ConfigStorage {

    public HashMap<String,String> TileEntityStages = new HashMap<String,String>();
    public HashMap<String,String> BlockStages = new HashMap<String,String>();

    public static final ConfigStorage instance = new ConfigStorage();

    public void load(){
        BlockStages.clear();
        for(String item:WorldStagesConfig.stagedBlocks){
            String[] split=item.split("=",2);
            if(split.length==2){
                BlockStages.put(split[0].trim(), split[1].trim());
            }else{
                WorldstagesMod.logInfo("[ERROR] Staged Blocks entry not splitted correctly:\n\t"+item);
            }
        }

        WorldstagesMod.logInfo("Loaded Block Stages: "+ConfigStorage.instance.BlockStages.toString());
        TileEntityStages.clear();
        for(String item:WorldStagesConfig.stagedTileEntities){
            String[] split=item.split("=",2);
            if(split.length==2){
                TileEntityStages.put(split[0].trim(), split[1].trim());
            }else{
                WorldstagesMod.logInfo("[ERROR] Staged TileEntities entry not splitted correctly:\n\t"+item);
            }
        }
        WorldstagesMod.logInfo("Loaded TileEntity Stages: "+ConfigStorage.instance.TileEntityStages.toString());
    }
}