package io.bluebeaker.worldstages.mixin;

import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import io.bluebeaker.worldstages.WorldStorageChecker;
import org.spongepowered.asm.mixin.injection.At;
import net.minecraft.block.Block;
import net.minecraft.block.state.BlockPistonStructureHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@Mixin(BlockPistonStructureHelper.class)
public abstract class MixinPistonHelper {
    @Inject(method = "addBlockLine", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;isStickyBlock(Lnet/minecraft/block/state/IBlockState;)Z"), cancellable = true)
    private boolean isStickyBlock(Block block, IBlockState state) {
        return false;
        // ResourceLocation id = state.getBlock().getRegistryName();
        // if (id != null && WorldStorageChecker.instance.checkBlockStateDisabled(state)) {
        //     return false;
        // } else
        //     return block.isStickyBlock(state);

    }
}
