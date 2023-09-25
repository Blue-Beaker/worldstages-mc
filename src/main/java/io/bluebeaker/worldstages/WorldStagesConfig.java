package io.bluebeaker.worldstages;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.LangKey;
import net.minecraftforge.common.config.Config.Type;

@Config(modid = WorldStagesMod.MODID,type = Type.INSTANCE,category = "general")
public class WorldStagesConfig {
    @Comment("Staged Blocks and their stages.\nFormat: modid:blockid=stage_name")
    @LangKey("config.worldstage.stagedBlocks.name")
    public static String[] stagedBlocks = new String[0];

    @Comment("Blocks that has click interaction staged, and their stages.\nFormat: modid:blockid=stage_name")
    @LangKey("config.worldstage.stagedBlockInteractions.name")
    public static String[] stagedBlockInteractions = new String[0];

    @Comment("Staged TileEntities and their stages.\nFormat: modid:tileentity_id=stage_name")
    @LangKey("config.worldstage.stagedTileentities.name")
    public static String[] stagedTileEntities = new String[0];

    @Comment("Staged Mods and their stages.\nThis locks all blocks, tileentities and entities from that mod behind the stage.\nFormat: modid=stage_name")
    @LangKey("config.worldstage.stagedMods.name")
    public static String[] stagedMods = new String[0];

    public static BlockConfig blockConfig = new BlockConfig();

    @Comment("Register additional stages here for addons to use.\nFormat: stage_name")
    @LangKey("config.worldstage.additionalStages.name")
    public static String[] additionalStages = new String[0];

    @LangKey("category.blockconfig.name")
    public static class BlockConfig{

        @Comment("The block will not react to some updates.")
        @LangKey("config.worldstage.disableUpdates.name")
        public boolean disableUpdates = true;

        @Comment("The block can't be interact with redstone.")
        @LangKey("config.worldstage.disableRedstoneInteraction.name")
        public boolean disableRedstoneInteraction = true;

        @Comment("The block can't be left-clicked.")
        @LangKey("config.worldstage.disableLeftInteraction.name")
        public boolean disableLeftInteraction = true;

        @Comment("The block can't be right-clicked.")
        @LangKey("config.worldstage.disableRightInteraction.name")
        public boolean disableRightInteraction = true;

        @Comment("The block can't be placed.")
        @LangKey("config.worldstage.disablePlacement.name")
        public boolean disablePlacement = true;

        @Comment("The block will not interact with entities.\nThis includes colliding, falling and walking on it.")
        @LangKey("config.worldstage.disableEntityInteraction.name")
        public boolean disableEntityInteraction = true;
    }

    @Comment("Debug")
    @LangKey("config.worldstage.debug.name")
    public static boolean debug = false;
}
