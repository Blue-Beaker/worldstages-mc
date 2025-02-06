package io.bluebeaker.worldstages.mixin;

import io.bluebeaker.worldstages.StageChecker;
import io.bluebeaker.worldstages.WorldStagesConfig;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Random;

@Mixin(WorldServer.class)
public class MixinWorldServer {
    @Redirect(method = "updateBlockTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;updateTick(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/state/IBlockState;Ljava/util/Random;)V"))
    private void updateBlockTick(Block block,World world,BlockPos pos,IBlockState state,Random random) {
        if(!WorldStagesConfig.blockConfig.disableUpdates) return;
        ResourceLocation id= block.getRegistryName();
        if(id!=null && StageChecker.instance.checkBlockDisabled(world,id)){
            // WorldStagesMod.logInfo(id.toString());
        }else{
            block.updateTick(world, pos, state, random);
        }
    }
    @Redirect(method = "tickUpdates", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;updateTick(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/state/IBlockState;Ljava/util/Random;)V"))
    private void tickUpdates(Block block,World world,BlockPos pos,IBlockState state,Random random) {
        if(!WorldStagesConfig.blockConfig.disableUpdates) return;
        ResourceLocation id= block.getRegistryName();
        if(id!=null && StageChecker.instance.checkBlockDisabled(world,id)){
            // WorldStagesMod.logInfo(id.toString());
        }else{
            block.updateTick(world, pos, state, random);
        }
    }
}
