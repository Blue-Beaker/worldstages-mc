package io.bluebeaker.worldstages.mixin;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import io.bluebeaker.worldstages.WorldStorageChecker;
import io.bluebeaker.worldstages.WorldStagesMod;

import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@Mixin(World.class)
public abstract class MixinWorld {
    // @Inject(method = "updateEntities", at = @At(value = "HEAD"))
    @Inject(method = "updateEntities", at = @At(value = "INVOKE", target = "Ljava/util/List;iterator()Ljava/util/Iterator;"))
    private void updateTick(CallbackInfo ci) {
        List<TileEntity> tileEntitiesToRemove = new ArrayList<TileEntity>();
        for(TileEntity te : ((World)(Object)this).tickableTileEntities){
            ResourceLocation blockID=TileEntity.getKey(te.getClass());
            if(blockID!=null && WorldStorageChecker.instance.checkTileEntityDisabled(((World)(Object)this),blockID.toString())){
                tileEntitiesToRemove.add(te);
            }
        }
        for(TileEntity te:tileEntitiesToRemove){
            ((World)(Object)this).tickableTileEntities.remove(te);
            WorldStagesMod.logInfo(String.valueOf(TileEntity.getKey(te.getClass())));
        }
    }
    @Inject(method = "getRedstonePower(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/EnumFacing;)I",at=@At("HEAD"),cancellable = true)
    private void getRedstonePower(BlockPos pos, EnumFacing facing,CallbackInfoReturnable<Integer> cir){
        if(WorldStorageChecker.instance.checkBlockDisabled(((World)(Object)this), pos)){
            cir.setReturnValue(0);
        }
    }
    @Inject(method = "neighborChanged",at=@At("HEAD"),cancellable = true)
    private void blockNeighborChanged(BlockPos pos, final Block blockIn, BlockPos fromPos,CallbackInfo ci){
        if(WorldStorageChecker.instance.checkBlockDisabled(((World)(Object)this), pos) || WorldStorageChecker.instance.checkBlockDisabled(((World)(Object)this), fromPos)){
            ci.cancel();
        }
    }
    @Redirect(method = "immediateBlockTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;updateTick(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/state/IBlockState;Ljava/util/Random;)V"))
    private void immediateBlockTick(Block block,World world,BlockPos pos,IBlockState state,Random random) {
        ResourceLocation id= block.getRegistryName();
        if(id!=null && WorldStorageChecker.instance.checkBlockDisabled(((World)(Object)this),id.toString())){
            WorldStagesMod.logInfo(id.toString());
        }else{
            block.updateTick(world, pos, state, random);
        }
    }

}
