package io.bluebeaker.worldstages;

import java.util.HashMap;
import java.util.HashSet;

public class ConfigStorage {

    public HashMap<String,String> TileEntityStages = new HashMap<String,String>(64);
    public HashMap<String,String> BlockStages = new HashMap<String,String>(64);
    public HashMap<String,String> BlockInteractionStages = new HashMap<String,String>(64);
    public HashMap<String,String> ModStages = new HashMap<String,String>(64);
    public HashSet<String> RegisteredStages = new HashSet<String>(16);
    public static final ConfigStorage instance = new ConfigStorage();

    public void load(){
        RegisteredStages.clear();
        
        for(String item:WorldStagesConfig.additionalStages){
            RegisteredStages.add(item);
        }

        BlockStages.clear();
        for(String item:WorldStagesConfig.stagedBlocks){
            String[] split=item.split("=",2);
            if(split.length==2){
                BlockStages.put(split[0].trim(), split[1].trim());
                RegisteredStages.add(split[1].trim());
            }else{
                WorldStagesMod.logInfo("[ERROR] Staged Blocks entry not splitted correctly:\n\t"+item);
            }
        }
        WorldStagesMod.logInfo("Loaded Block Stages: "+ConfigStorage.instance.BlockStages.toString());

        loadConfigSet(WorldStagesConfig.stagedBlockInteractions, BlockInteractionStages, "Block Interaction");

        TileEntityStages.clear();
        for(String item:WorldStagesConfig.stagedTileEntities){
            String[] split=item.split("=",2);
            if(split.length==2){
                TileEntityStages.put(split[0].trim(), split[1].trim());
                RegisteredStages.add(split[1].trim());
            }else{
                WorldStagesMod.logInfo("[ERROR] Staged TileEntities entry not splitted correctly:\n\t"+item);
            }
        }
        WorldStagesMod.logInfo("Loaded TileEntity Stages: "+ConfigStorage.instance.TileEntityStages.toString());

        ModStages.clear();
        for(String item:WorldStagesConfig.stagedMods){
            String[] split=item.split("=",2);
            if(split.length==2){
                ModStages.put(split[0].trim(), split[1].trim());
                RegisteredStages.add(split[1].trim());
            }else{
                WorldStagesMod.logInfo("[ERROR] Staged mods entry not splitted correctly:\n\t"+item);
            }
        }
        WorldStagesMod.logInfo("Loaded Mod Stages: "+ConfigStorage.instance.ModStages.toString());
    }
    private void loadConfigSet(String[] config, HashMap<String,String> stages,String name){
        stages.clear();
        for(String item:config){
            String[] split=item.split("=",2);
            if(split.length==2){
                stages.put(split[0].trim(), split[1].trim());
                RegisteredStages.add(split[1].trim());
            }else{
                WorldStagesMod.logInfo("[ERROR] Staged "+name+" entry not splitted correctly:\n\t"+item);
            }
        }
        WorldStagesMod.logInfo("Loaded "+name+": "+stages.toString());

    }
}