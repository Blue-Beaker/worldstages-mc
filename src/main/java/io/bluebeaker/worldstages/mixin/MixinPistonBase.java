package io.bluebeaker.worldstages.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import io.bluebeaker.worldstages.StageChecker;

import org.spongepowered.asm.mixin.injection.At;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@Mixin(BlockPistonBase.class)
public abstract class MixinPistonBase {
    //Prevents piston pushing disabled blocks
    @Inject(method = "canPush", at = @At(value = "HEAD"),cancellable = true)
    private static void canPush(IBlockState blockStateIn, World worldIn, BlockPos pos, EnumFacing facing, boolean destroyBlocks, EnumFacing facing2, CallbackInfoReturnable<Boolean> cir) {
        if(StageChecker.instance.checkBlockDisabled(worldIn, pos)){
            cir.setReturnValue(false);
        };
    }
}
