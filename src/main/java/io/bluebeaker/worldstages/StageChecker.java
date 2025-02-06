package io.bluebeaker.worldstages;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class StageChecker {
    public static final StageChecker instance = new StageChecker();
    private static final ConfigStorage config = ConfigStorage.instance;
    public HashSet<String> stages = new HashSet<String>();
    public HashSet<String> disabled_blocks = new HashSet<String>();
    public HashSet<String> disabled_block_interactions = new HashSet<String>();
    public HashSet<String> disabled_tileentities = new HashSet<String>();
    public HashSet<String> disabled_mods = new HashSet<String>();
    private StageChecker(){}

    @SubscribeEvent
    public void onStageChanged(WorldStageEvent event){
        stages=event.stages;
        refresh(event.stages);
        WorldStagesMod.logInfo("Stages changed to:"+event.stages.toString());
        WorldStagesMod.logInfo("disabled_blocks:"+disabled_blocks.toString());
        WorldStagesMod.logInfo("disabled_block_interactions:"+disabled_block_interactions.toString());
        WorldStagesMod.logInfo("disabled_tileentities:"+disabled_tileentities.toString());
        WorldStagesMod.logInfo("disabled_mods:"+disabled_mods.toString());
    }

    public void refresh(HashSet<String> stages) {
        disabled_blocks = genDisabledItems(stages, config.BlockStages);
        disabled_block_interactions = genDisabledItems(stages, config.BlockInteractionStages);
        disabled_tileentities = genDisabledItems(stages, config.TileEntityStages);
        disabled_mods = genDisabledItems(stages, config.ModStages);
    }

    private HashSet<String> genDisabledItems(Set<String> stages, Map<String, String> config) {
        HashSet<String> disabledItems = new HashSet<String>();
        for (String item : config.keySet()) {
            if (!stages.contains(config.get(item))) {
                disabledItems.add(item);
            }
        }
        return disabledItems;
    }

    public boolean checkStageActive(World world, String stage) {
        return stage == null || WorldStagesSavedData.get(world).getStages().contains(stage);
    }

    public boolean checkModDisabled(World world, String modid) {
        return this.disabled_mods.contains(modid);
        // return !checkStageActive(world, ConfigStorage.instance.ModStages.get(modid));
    }

    public boolean checkModDisabled(World world, @Nullable ResourceLocation resourceLocation) {
        if (resourceLocation == null)
            return false;
        return checkModDisabled(world, resourceLocation.getNamespace());
    }

    /**
     * @param world
     * @param BlockID
     * @return whether the block is disabled.
     */
    public boolean checkBlockDisabled(World world, @Nullable ResourceLocation BlockID) {
        return checkModDisabled(world, BlockID)
                || this.disabled_blocks.contains(String.valueOf(BlockID));
        // return checkModDisabled(world, BlockID)
        //         || !checkStageActive(world, ConfigStorage.instance.BlockStages.get(String.valueOf(BlockID)));
                
    }

    public boolean checkBlockDisabled(World world, Block block) {
        return checkBlockDisabled(world, block.getRegistryName());
    }

    public boolean checkBlockStateDisabled(World world, IBlockState state) {
        return checkBlockDisabled(world, state.getBlock().getRegistryName());
    }

    public boolean checkBlockDisabled(World world, BlockPos pos) {
        return checkBlockStateDisabled(world, world.getBlockState(pos));
    }

    /**
     * @param world
     * @param TileEntityID
     * @return whether the tileentity is disabled.
     */
    public boolean checkTileEntityDisabled(World world, @Nullable ResourceLocation TileEntityID) {
        return checkModDisabled(world, TileEntityID)
                || this.disabled_tileentities.contains(String.valueOf(TileEntityID));
        // return checkModDisabled(world, TileEntityID)
        //         || !checkStageActive(world, ConfigStorage.instance.TileEntityStages.get(String.valueOf(TileEntityID)));
    }

    public boolean checkTileEntityDisabled(World world, TileEntity tileEntity) {
        return checkTileEntityDisabled(world, TileEntity.getKey(tileEntity.getClass()));
    }

    /**
     * @param world
     * @param TileEntityID
     * @return whether the block is disabled for interactions.
     */
    public boolean checkBlockInteractionDisabled(World world, @Nullable ResourceLocation BlockID) {
        return this.disabled_block_interactions.contains(String.valueOf(BlockID));
        // return !checkStageActive(world, ConfigStorage.instance.BlockInteractionStages.get(String.valueOf(BlockID)));
    }

    public boolean checkBlockInteractionDisabled(World world, Block block) {
        return checkBlockInteractionDisabled(world, block.getRegistryName());
    }

    public boolean checkBlockInteractionDisabled(World world, BlockPos pos) {
        return checkBlockInteractionDisabled(world, world.getBlockState(pos).getBlock());
    }
}
