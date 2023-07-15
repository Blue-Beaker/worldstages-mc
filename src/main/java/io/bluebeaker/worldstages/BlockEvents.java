package io.bluebeaker.worldstages;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.LeftClickBlock;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.world.BlockEvent.EntityPlaceEvent;

public class BlockEvents {
    @SubscribeEvent
    public static void disableLeftInteraction(LeftClickBlock event){
        if(event.getWorld().isRemote) return;
        if(WorldStagesConfig.blockConfig.disableLeftInteraction&& StageChecker.instance.checkBlockDisabled(event.getWorld(), event.getPos())){
            event.getEntityPlayer().sendStatusMessage(new TextComponentTranslation("message.worldstage.blockdisabled"),true);
            event.setCanceled(true);
        }
    }
    @SubscribeEvent
    public static void disableRightInteraction(RightClickBlock event){
        if(event.getWorld().isRemote) return;
        if(WorldStagesConfig.blockConfig.disableRightInteraction&&StageChecker.instance.checkBlockDisabled(event.getWorld(), event.getPos())){
            event.getEntityPlayer().sendStatusMessage(new TextComponentTranslation("message.worldstage.blockdisabled"),true);
            event.setCanceled(true);
        }
    }
    @SubscribeEvent
    public static void disablePlacement(EntityPlaceEvent event){
        if(WorldStagesConfig.blockConfig.disablePlacement&&StageChecker.instance.checkBlockDisabled(event.getWorld(), event.getPos())){
            Entity entity=event.getEntity();
            if(entity instanceof EntityPlayer){
                ((EntityPlayer)entity).sendStatusMessage(new TextComponentTranslation("message.worldstage.blockdisabled"),true);
            }
            event.setCanceled(true);
        }
    }
}
