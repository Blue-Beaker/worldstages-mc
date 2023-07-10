package io.bluebeaker.worldstages;

import java.io.File;
import java.util.Collection;
import java.util.HashSet;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.Loader;

public class ConfigStorage {

    private Configuration config;
    private ConfigCategory categoryBlocks;

    public Collection<String> blacklistedTileEntityIDs = new HashSet<String>();
    public Collection<String> blacklistedBlockIDs = new HashSet<String>();

    public static final ConfigStorage instance = new ConfigStorage();

    public void setupConfig()
    {
        Property stagedTileEntities = new Property("staged_tileentities", new String[1], Property.Type.STRING);
        stagedTileEntities.setComment("Staged TileEntities and their stages.\nFormat: modid:tileentity_id=stage_name");

        Property stagedBlocks = new Property("staged_blocks", new String[1], Property.Type.STRING);
        stagedBlocks.setComment("Staged blocks.\nFormat: modid:blockid=stage_name");

        config = new Configuration(new File(Loader.instance().getConfigDir(), "worldstages.cfg"));
        categoryBlocks = config.getCategory("Blocks");
        categoryBlocks.put(stagedTileEntities.getName(), stagedTileEntities);
        categoryBlocks.put(stagedBlocks.getName(), stagedBlocks);
        config.save();
    }

    public void load(){
        this.setupConfig();
        blacklistedTileEntityIDs.add("minecraft:hopper");
        blacklistedTileEntityIDs.add("minecraft:furnace");
        blacklistedTileEntityIDs.add("minecraft:dispenser");
        blacklistedTileEntityIDs.add("minecraft:comparator");
        blacklistedBlockIDs.add("minecraft:dispenser");
        blacklistedBlockIDs.add("minecraft:comparator");
        blacklistedBlockIDs.add("minecraft:observer");
    }
}