package io.bluebeaker.worldstages.mixin;
import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.bluebeaker.worldstages.ConfigStorage;
import io.bluebeaker.worldstages.WorldStorage;
import io.bluebeaker.worldstages.WorldstagesMod;

import org.spongepowered.asm.mixin.injection.At;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@Mixin(Block.class)
public abstract class MixinBlock {
    @Inject(method = "updateTick", at=@At("HEAD"),cancellable = true)
    private void updateTick(World world, BlockPos pos, IBlockState state, Random random,CallbackInfo ci){
        ResourceLocation id= state.getBlock().getRegistryName();
        if(id!=null && WorldStorage.instance.checkBlockDisabled(id.toString())){
            WorldstagesMod.logInfo(id.toString());
            ci.cancel();
        }
    }
}
