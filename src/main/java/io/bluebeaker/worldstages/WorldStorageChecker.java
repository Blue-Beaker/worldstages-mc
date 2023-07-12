package io.bluebeaker.worldstages;

import java.util.HashSet;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WorldStorageChecker {
    public static final WorldStorageChecker instance = new WorldStorageChecker();
    public static HashSet<String> stages = new HashSet<String>();
    public boolean checkStageActive(World world,String stage){
        return stage==null || WorldStagesWorldSavedData.get(world).stages.contains(stage) || world.getGameRules().getInt("teststage")>0;
    }
    public boolean checkBlockDisabled(World world,String BlockID){
        return !checkStageActive(world,ConfigStorage.instance.BlockStages.get(BlockID));
    }
    public boolean checkBlockDisabled(World world,Block block){
        return checkBlockDisabled(world, String.valueOf(block.getRegistryName()));
    }
    public boolean checkBlockStateDisabled(World world,IBlockState state){
        return checkBlockDisabled(world, String.valueOf(state.getBlock().getRegistryName()));
    }
    public boolean checkBlockDisabled(World world,BlockPos pos){
        return checkBlockStateDisabled(world, world.getBlockState(pos));
    }
    public boolean checkTileEntityDisabled(World world,String TileEntityID){
        return !checkStageActive(world,ConfigStorage.instance.TileEntityStages.get(TileEntityID));
    }
    public boolean checkTileEntityDisabled(World world,TileEntity tileEntity){
        return checkTileEntityDisabled(world, String.valueOf(TileEntity.getKey(tileEntity.getClass())));
    }

    // public boolean checkStageActive(String stage){
    //     if(WorldStagesWorldSavedData.instance==null){
    //         return stage==null;
    //     }else{
    //         return stage==null || WorldStagesWorldSavedData.instance.stages.contains(stage);
    //     }
    // }
    // public boolean checkBlockDisabled(Block block){
    //     return !checkStageActive(String.valueOf(block.getRegistryName()));
    // }
    // public boolean checkBlockStateDisabled(IBlockState state){
    //     return checkBlockDisabled(state.getBlock());
    // }
}
