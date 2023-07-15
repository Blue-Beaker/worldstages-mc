package io.bluebeaker.worldstages;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.LangKey;
import net.minecraftforge.common.config.Config.Type;

@Config(modid = WorldStagesMod.MODID,type = Type.INSTANCE,category = "general")
public class WorldStagesConfig {
    @Comment("Staged Blocks and their stages.\nFormat: modid:tileentity_id=stage_name")
    @LangKey("config.stagedBlocks.name")
    public static String[] stagedBlocks = new String[0];

    @Comment("Staged TileEntities and their stages.\nFormat: modid:tileentity_id=stage_name")
    @LangKey("config.stagedTileentities.name")
    public static String[] stagedTileEntities = new String[0];

    public static BlockConfig blockConfig = new BlockConfig();

    @LangKey("category.blockconfig.name")
    public static class BlockConfig{

        @Comment("The block will not react to some updates.")
        @LangKey("config.disableUpdates.name")
        public boolean disableUpdates = true;

        @Comment("The block can't be left-clicked, including mining.")
        @LangKey("config.disableRedstoneInteraction.name")
        public boolean disableRedstoneInteraction = true;

        @Comment("The block can't be left-clicked, including mining.")
        @LangKey("config.disableLeftInteraction.name")
        public boolean disableLeftInteraction = true;

        @Comment("The block can't be right-clicked.")
        @LangKey("config.disableRightInteraction.name")
        public boolean disableRightInteraction = true;

        @Comment("The block can't be placed.")
        @LangKey("config.disablePlacement.name")
        public boolean disablePlacement = true;

        @Comment("The block will not interact with entities.\nThis includes colliding, falling and walking on it.")
        @LangKey("config.disableEntityInteraction.name")
        public boolean disableEntityInteraction = true;
    }
}
