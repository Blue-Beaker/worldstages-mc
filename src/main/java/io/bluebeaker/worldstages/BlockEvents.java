package io.bluebeaker.worldstages;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.LeftClickBlock;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.event.world.BlockEvent.EntityPlaceEvent;

public class BlockEvents {
    private static StageChecker checker = StageChecker.instance;
    @SubscribeEvent
    public static void disableLeftInteraction(LeftClickBlock event){
        // if(event.getWorld().isRemote) return;
        if(WorldStagesConfig.blockConfig.disableLeftInteraction&&checker.checkBlockDisabled(event.getWorld(), event.getPos())
        ||checker.checkBlockInteractionDisabled(event.getWorld(),event.getPos())){
            event.setUseBlock(Result.DENY);
        }
    }
    @SubscribeEvent
    public static void disableRightInteraction(RightClickBlock event){
        // if(event.getWorld().isRemote) return;
        if(WorldStagesConfig.blockConfig.disableRightInteraction&&checker.checkBlockDisabled(event.getWorld(), event.getPos())
        ||checker.checkBlockInteractionDisabled(event.getWorld(),event.getPos())){
            event.setUseBlock(Result.DENY);
        }
    }
    @SubscribeEvent
    public static void disablePlacement(EntityPlaceEvent event){
        if(WorldStagesConfig.blockConfig.disablePlacement&&checker.checkBlockDisabled(event.getWorld(), event.getPos())){
            Entity entity=event.getEntity();
            if(entity instanceof EntityPlayer){
                ((EntityPlayer)entity).sendStatusMessage(new TextComponentTranslation("message.worldstage.blockdisabled"),true);
            }
            event.setCanceled(true);
        }
    }
}
