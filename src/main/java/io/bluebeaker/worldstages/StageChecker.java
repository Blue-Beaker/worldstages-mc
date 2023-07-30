package io.bluebeaker.worldstages;

import java.util.HashSet;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class StageChecker {
    public static final StageChecker instance = new StageChecker();
    public static HashSet<String> stages = new HashSet<String>();
    public boolean checkStageActive(World world,String stage){
        return stage==null || WorldStagesSavedData.get(world).getStages().contains(stage);
    }
    public boolean checkModDisabled(World world,String modid){
        return !checkStageActive(world, ConfigStorage.instance.ModStages.get(modid));
    }
    public boolean checkModDisabled(World world,@Nullable ResourceLocation resourceLocation){
        if(resourceLocation==null) return false;
        return !checkStageActive(world, ConfigStorage.instance.ModStages.get(resourceLocation.getResourceDomain()));
    }
    public boolean checkBlockDisabled(World world,@Nullable ResourceLocation BlockID){
        return checkModDisabled(world, BlockID)||!checkStageActive(world,ConfigStorage.instance.BlockStages.get(String.valueOf(BlockID)));
    }
    public boolean checkBlockDisabled(World world,Block block){
        return checkBlockDisabled(world, block.getRegistryName());
    }
    public boolean checkBlockStateDisabled(World world,IBlockState state){
        return checkBlockDisabled(world, state.getBlock().getRegistryName());
    }
    public boolean checkBlockDisabled(World world,BlockPos pos){
        return checkBlockStateDisabled(world, world.getBlockState(pos));
    }

    public boolean checkTileEntityDisabled(World world,@Nullable ResourceLocation TileEntityID){
        return checkModDisabled(world, TileEntityID)||!checkStageActive(world,ConfigStorage.instance.TileEntityStages.get(String.valueOf(TileEntityID)));
    }
    public boolean checkTileEntityDisabled(World world,TileEntity tileEntity){
        return checkTileEntityDisabled(world, TileEntity.getKey(tileEntity.getClass()));
    }

    public boolean checkBlockInteractionDisabled(World world,@Nullable ResourceLocation BlockID){
        return !checkStageActive(world,ConfigStorage.instance.BlockInteractionStages.get(String.valueOf(BlockID)));
    }
    public boolean checkBlockInteractionDisabled(World world,Block block){
        return checkBlockInteractionDisabled(world, block.getRegistryName());
    }
    public boolean checkBlockInteractionDisabled(World world,BlockPos pos){
        return checkBlockInteractionDisabled(world,world.getBlockState(pos).getBlock());
    }
}
