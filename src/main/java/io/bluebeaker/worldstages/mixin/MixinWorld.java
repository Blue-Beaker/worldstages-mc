package io.bluebeaker.worldstages.mixin;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.bluebeaker.worldstages.ConfigStorage;
import io.bluebeaker.worldstages.WorldStorage;
import io.bluebeaker.worldstages.WorldstagesMod;

import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
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
            if(blockID!=null && WorldStorage.instance.checkBlockDisabled(blockID.toString())){
                tileEntitiesToRemove.add(te);
            }
        }
        for(TileEntity te:tileEntitiesToRemove){
            ((World)(Object)this).tickableTileEntities.remove(te);
            WorldstagesMod.logInfo(String.valueOf(TileEntity.getKey(te.getClass())));
        }
    }
    @Redirect(method = "immediateBlockTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;updateTick(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/state/IBlockState;Ljava/util/Random;)V"))
    private void immediateBlockTick(Block block,World world,BlockPos pos,IBlockState state,Random random) {
        ResourceLocation id= block.getRegistryName();
        if(id!=null && WorldStorage.instance.checkBlockDisabled(id.toString())){
            WorldstagesMod.logInfo(id.toString());
        }else{
            block.updateTick(world, pos, state, random);
        }
    }

}
