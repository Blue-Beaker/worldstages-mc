package io.bluebeaker.worldstages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.server.command.CommandTreeBase;
import net.minecraft.command.WrongUsageException;

public class WorldStagesCommand extends CommandTreeBase {

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    public WorldStagesCommand() {
        addSubcommand(new CommandAdd());
        addSubcommand(new CommandRemove());
        addSubcommand(new CommandList());
        addSubcommand(new CommandReload());
    }

    @Override
    public String getName() {
        return "worldstage";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "commands.worldstage.usage";
    }

    public static class CommandAdd extends CommandBase {

        @Override
        public String getName() {
            return "add";
        }

        @Override
        public String getUsage(ICommandSender sender) {
            return "commands.worldstage.add.usage";
        }

        @Override
        public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
            if (args.length >= 1) {
                WorldStagesSavedData data = (WorldStagesSavedData)WorldStagesSavedData.get(server.getWorld(0));
                String value = args[0];
                if (!data.isStageActive(value)) {
                    data.addStage(value);
                    notifyCommandListener(sender, this, 0, "commands.worldstage.add.success", value);
                } else {
                    throw new CommandException("commands.worldstage.add.error.exists", value);
                }
            } else {
                throw new WrongUsageException(getUsage(sender));
            }
        }

        @Override
        public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args,
                @Nullable BlockPos targetPos) {
            List<String> list= new ArrayList<String>(ConfigStorage.instance.RegisteredStages);
            list.removeAll(WorldStagesSavedData.get(server.getWorld(0)).getStages());
            return list;
        }
        @Override
        public List<String> getAliases() {
            return Arrays.<String>asList("enable","+");
        }
    }

    public static class CommandRemove extends CommandBase {

        @Override
        public String getName() {
            return "remove";
        }

        @Override
        public String getUsage(ICommandSender sender) {
            return "commands.worldstage.remove.usage";
        }

        @Override
        public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
            if (args.length >= 1) {
                WorldStagesSavedData data = (WorldStagesSavedData)WorldStagesSavedData.get(server.getWorld(0));
                String value = args[0];
                if (data.isStageActive(value)) {
                    data.removeStage(value);
                    notifyCommandListener(sender, this, 0, "commands.worldstage.remove.success", value);
                } else {
                    throw new CommandException("commands.worldstage.remove.error.notfound", value);
                }
            } else {
                throw new WrongUsageException(getUsage(sender));
            }
        }

        @Override
        public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args,
                @Nullable BlockPos targetPos) {
            return new ArrayList<String>(WorldStagesSavedData.get(server.getWorld(0)).getStages());
        }

        @Override
        public List<String> getAliases() {
            return Arrays.<String>asList("disable","rm","-");
        }
    }

    public static class CommandList extends CommandBase {
        @Override
        public String getName() {
            return "list";
        }

        @Override
        public String getUsage(ICommandSender sender) {
            return "commands.worldstage.list.usage";
        }

        @Override
        public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
            if (args.length == 0) {
                WorldStagesSavedData data = (WorldStagesSavedData)WorldStagesSavedData.get(server.getWorld(0));
                sender.sendMessage(new TextComponentTranslation("commands.worldstage.list.result",
                        data.stages.toString(), ConfigStorage.instance.RegisteredStages.toString()));
            } else {
                throw new WrongUsageException(getUsage(sender));
            }
        }
        @Override
        public List<String> getAliases() {
            return Arrays.<String>asList("ls");
        }
    }

    public static class CommandReload extends CommandBase {
        @Override
        public String getName() {
            return "reload";
        }

        @Override
        public String getUsage(ICommandSender sender) {
            return "commands.worldstage.reload.usage";
        }

        @Override
        public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
            if (args.length == 0) {
                ConfigStorage.instance.load();
                sender.sendMessage(new TextComponentTranslation("commands.worldstage.reload.result",
                        ConfigStorage.instance.BlockStages.size(),ConfigStorage.instance.TileEntityStages.size()));
            } else {
                throw new WrongUsageException(getUsage(sender));
            }
        }
    }
}
