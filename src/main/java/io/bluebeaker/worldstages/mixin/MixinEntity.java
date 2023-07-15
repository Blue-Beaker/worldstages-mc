package io.bluebeaker.worldstages.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Redirect;

import io.bluebeaker.worldstages.StageChecker;

import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@Mixin(Entity.class)
public class MixinEntity {
    @Redirect(method = "doBlockCollisions()V",at=@At(value = "INVOKE",target = "Lnet/minecraft/block/Block;onEntityCollidedWithBlock(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/entity/Entity;)V"))
    public void onEntityCollidedWithBlock(Block block,World worldIn, BlockPos pos, IBlockState state, Entity entityIn){
        if(!StageChecker.instance.checkBlockDisabled(worldIn, pos)){
            block.onEntityCollidedWithBlock(worldIn, pos, state, entityIn);
        }
    }
    @Redirect(method = "updateFallState",at=@At(value = "INVOKE",target = "Lnet/minecraft/block/Block;onFallenUpon(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/entity/Entity;F)V"))
    public void onFallenUpon(Block block,World worldIn, BlockPos pos, Entity entityIn, float fallDistance){
        if(!StageChecker.instance.checkBlockDisabled(worldIn, pos)){
            block.onFallenUpon(worldIn, pos, entityIn,fallDistance);
        }else{
            entityIn.fall(fallDistance, 1.0F);
        }
    }
    @Redirect(method = "move",at=@At(value = "INVOKE",target = "Lnet/minecraft/block/Block;onLanded(Lnet/minecraft/world/World;Lnet/minecraft/entity/Entity;)V"))
    public void onLanded(Block block,World worldIn, Entity entityIn){
        if(!StageChecker.instance.checkBlockDisabled(worldIn,block)){
            block.onLanded(worldIn, entityIn);
        }else{
            entityIn.motionY = 0.0D;
        }
    }
    @Redirect(method = "move",at=@At(value = "INVOKE",target = "Lnet/minecraft/block/Block;onEntityWalk(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/entity/Entity;)V"))
    public void onEntityWalk(Block block,World worldIn,BlockPos pos, Entity entityIn){
        if(!StageChecker.instance.checkBlockDisabled(worldIn,block)){
            block.onEntityWalk(worldIn, pos, entityIn);
        }else{
        }
    }
}