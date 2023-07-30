package io.bluebeaker.worldstages;

import java.util.HashSet;

import io.bluebeaker.IWorldStagesStorage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
@SideOnly(Side.CLIENT)
public class ClientWorldStages implements IWorldStagesStorage {
    public HashSet<String> stages = new HashSet<String>();
    public static ClientWorldStages instance = new ClientWorldStages();
    public static ClientWorldStages get(){
        return instance;
    }
    @Override
    public boolean isStageActive(String stage) {
        return stages.contains(stage);
    }
    @Override
    public HashSet<String> getStages() {
        return this.stages;
    }
    @Override
    public void addStage(String stage) {
        throw new UnsupportedOperationException("Cant add stage from ClientWorldStages");
    }
    @Override
    public void removeStage(String stage) {
        throw new UnsupportedOperationException("Cant remove stage from ClientWorldStages");
    }
}
