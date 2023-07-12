package io.bluebeaker.worldstages.mixin;

import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Redirect;
import io.bluebeaker.worldstages.WorldStorageChecker;
import io.bluebeaker.worldstages.WorldStagesMod;

import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

@Mixin(WorldServer.class)
public class MixinWorldServer {
    @Redirect(method = "updateBlockTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;updateTick(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/state/IBlockState;Ljava/util/Random;)V"))
    private void updateBlockTick(Block block,World world,BlockPos pos,IBlockState state,Random random) {
        ResourceLocation id= block.getRegistryName();
        if(id!=null && WorldStorageChecker.instance.checkBlockDisabled(world,id.toString())){
            WorldStagesMod.logInfo(id.toString());
        }else{
            block.updateTick(world, pos, state, random);
        }
    }
    @Redirect(method = "tickUpdates", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;updateTick(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/state/IBlockState;Ljava/util/Random;)V"))
    private void tickUpdates(Block block,World world,BlockPos pos,IBlockState state,Random random) {
        ResourceLocation id= block.getRegistryName();
        if(id!=null && WorldStorageChecker.instance.checkBlockDisabled(world,id.toString())){
            WorldStagesMod.logInfo(id.toString());
        }else{
            block.updateTick(world, pos, state, random);
        }
    }
}
