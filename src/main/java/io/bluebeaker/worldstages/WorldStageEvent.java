package io.bluebeaker.worldstages;

import java.util.HashSet;

import net.minecraftforge.fml.common.eventhandler.Event;

public class WorldStageEvent extends Event{
    HashSet<String> stages;
    String laststage;
    public WorldStageEvent(){
        super();
    }
    public WorldStageEvent(HashSet<String> stages){
        super();
        this.stages=stages;
    }
    public static class Add extends WorldStageEvent{
        public Add(HashSet<String> stages, String laststage){
            super(stages);
            this.laststage=laststage;
        }
    }
    public static class Remove extends WorldStageEvent{
        public Remove(HashSet<String> stages, String laststage){
            super(stages);
            this.laststage=laststage;
        }
    }
}
