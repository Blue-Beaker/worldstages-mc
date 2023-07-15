package io.bluebeaker.worldstages;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.LangKey;
import net.minecraftforge.common.config.Config.Type;

@Config(modid = WorldStagesMod.MODID,type = Type.INSTANCE,category = "general")
public class WorldStagesConfig {
    @Comment("Staged Blocks and their stages.\nFormat: modid:tileentity_id=stage_name\nA §9/worldstages reload§r or server restart is needed for these lists to take effect.")
    @LangKey("config.stagedBlocks.name")
    public static String[] stagedBlocks = new String[0];

    @Comment("Staged TileEntities and their stages.\nFormat: modid:tileentity_id=stage_name")
    @LangKey("config.stagedTileentities.name")
    public static String[] stagedTileEntities = new String[0];
}
