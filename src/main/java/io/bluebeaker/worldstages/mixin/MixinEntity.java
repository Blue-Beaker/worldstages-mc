package io.bluebeaker.worldstages.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

@Mixin(Entity.class)
public abstract class MixinEntity implements ICommandSender, ICapabilitySerializable<NBTTagCompound> {
    @Inject(method = "onEntityUpdate",at=@At("HEAD"),cancellable = true)
    private void cancelUpdate(CallbackInfo ci){
        if(((Entity)(Object)(this)).height>2.5)
        ci.cancel();
    }
}
