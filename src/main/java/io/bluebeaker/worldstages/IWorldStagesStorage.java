package io.bluebeaker.worldstages;

import java.util.HashSet;

public interface IWorldStagesStorage {
    public HashSet<String> getStages();
    public boolean isStageActive(String stage);
    public void addStage(String stage);
    public void removeStage(String stage);
}
