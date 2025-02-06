package io.bluebeaker.worldstages;

import net.minecraft.world.World;
import net.minecraftforge.event.world.WorldEvent;

import javax.annotation.Nonnull;
import java.util.HashSet;

public class WorldStageEvent extends WorldEvent{
    public HashSet<String> stages;
    public String laststage;
    public WorldStageEvent(@Nonnull World world){
        super(world);
    }
    public WorldStageEvent(@Nonnull World world,HashSet<String> stages){
        super(world);
//        if(world==null){
//            throw new NullPointerException();
//        }
        this.stages=stages;
    }
    public static class Add extends WorldStageEvent{
        public Add(World world,HashSet<String> stages, String laststage){
            super(world,stages);
            this.laststage=laststage;
        }
    }
    public static class Remove extends WorldStageEvent{
        public Remove(World world,HashSet<String> stages, String laststage){
            super(world,stages);
            this.laststage=laststage;
        }
    }
}
