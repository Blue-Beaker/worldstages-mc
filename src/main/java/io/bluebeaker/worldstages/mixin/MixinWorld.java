package io.bluebeaker.worldstages.mixin;

import io.bluebeaker.worldstages.StageChecker;
import io.bluebeaker.worldstages.WorldStagesConfig;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Mixin(World.class)
public abstract class MixinWorld {
    //Prevents updating disabled blocks
    @Inject(method = "updateEntities", at = @At(value = "INVOKE", target = "Ljava/util/List;iterator()Ljava/util/Iterator;"))
    private void disableTick(CallbackInfo ci) {
        List<TileEntity> tileEntitiesToRemove = new ArrayList<TileEntity>();
        for(TileEntity te : ((World)(Object)this).tickableTileEntities){
            ResourceLocation blockID=TileEntity.getKey(te.getClass());
            if(blockID!=null && StageChecker.instance.checkTileEntityDisabled(((World)(Object)this),blockID)){
                tileEntitiesToRemove.add(te);
            }
        }
        for(TileEntity te:tileEntitiesToRemove){
            ((World)(Object)this).tickableTileEntities.remove(te);
            // WorldStagesMod.logInfo(String.valueOf(TileEntity.getKey(te.getClass())));
        }
    }
    //Prevents activating disabled blocks
    @Inject(method = "getRedstonePower(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/EnumFacing;)I",at=@At("HEAD"),cancellable = true)
    private void disableRedstonePower(BlockPos pos, EnumFacing facing,CallbackInfoReturnable<Integer> cir){
        if(WorldStagesConfig.blockConfig.disableRedstoneInteraction&&StageChecker.instance.checkBlockDisabled(((World)(Object)this), pos)){
            cir.setReturnValue(0);
        }
    }
    //Prevents reading redstone from disabled blocks
    @Inject(method = "isBlockPowered(Lnet/minecraft/util/math/BlockPos;)Z",at=@At("HEAD"),cancellable = true)
    private void disableBlockPowered(BlockPos pos,CallbackInfoReturnable<Boolean> cir){
        if(WorldStagesConfig.blockConfig.disableRedstoneInteraction&&StageChecker.instance.checkBlockDisabled(((World)(Object)this), pos)){
            cir.setReturnValue(false);
        }
    }
    //Prevents updating disabled blocks
    @Redirect(method = "immediateBlockTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;updateTick(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/state/IBlockState;Ljava/util/Random;)V"))
    private void immediateBlockTick(Block block,World world,BlockPos pos,IBlockState state,Random random) {
        if(!WorldStagesConfig.blockConfig.disableUpdates) return;
        ResourceLocation id= block.getRegistryName();
        if(id!=null && StageChecker.instance.checkBlockDisabled(((World)(Object)this),id)){
            // WorldStagesMod.logInfo(id.toString());
        }else{
            block.updateTick(world, pos, state, random);
        }
    }

}
