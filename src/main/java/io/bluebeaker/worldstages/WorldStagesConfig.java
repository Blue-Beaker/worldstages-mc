package io.bluebeaker.worldstages;

import java.util.HashMap;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.LangKey;
import net.minecraftforge.common.config.Config.RequiresWorldRestart;
import net.minecraftforge.common.config.Config.Type;

@Config(modid = WorldStagesMod.MODID,type = Type.INSTANCE,category = "general")
public class WorldStagesConfig {
    @Comment("Staged TileEntities and their stages.\nFormat: modid:tileentity_id=stage_name")
    @LangKey("config.stagedTileentities.name")
    @RequiresWorldRestart
    public static String[] stagedTileEntities = new String[0];

    @Comment("Staged Blocks and their stages.\nFormat: modid:tileentity_id=stage_name")
    @LangKey("config.stagedBlocks.name")
    @RequiresWorldRestart
    public static String[] stagedBlocks = new String[0];

}
