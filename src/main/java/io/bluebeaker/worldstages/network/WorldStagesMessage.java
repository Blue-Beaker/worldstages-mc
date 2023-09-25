package io.bluebeaker.worldstages.network;

import java.io.IOException;
import java.util.HashSet;

import io.bluebeaker.worldstages.ClientWorldStages;
import io.bluebeaker.worldstages.WorldStageEvent;
import io.bluebeaker.worldstages.WorldStagesMod;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class WorldStagesMessage implements IMessage {
    // A default constructor is always required
    public WorldStagesMessage() {
    }

    private NBTTagCompound data;

    public WorldStagesMessage(NBTTagCompound toSend) {
        this.data = toSend;
    }

    public WorldStagesMessage(HashSet<String> stages) {
        NBTTagList list = new NBTTagList();
        for (String stage : stages) {
            list.appendTag(new NBTTagString(stage));
        }
        this.data = new NBTTagCompound();
        this.data.setTag("stages", list);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        PacketBuffer buffer = new PacketBuffer(buf);
        buffer.writeCompoundTag(data);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        PacketBuffer buffer = new PacketBuffer(buf);
        try {
            this.data = buffer.readCompoundTag();
        } catch (IOException exception) {
            WorldStagesMod.logError(exception.getStackTrace().toString());
        }
    }

    public static class WorldStagesMessageHandler implements IMessageHandler<WorldStagesMessage, IMessage> {
        // Do note that the default constructor is required, but implicitly defined in
        // this case
        public WorldStagesMessageHandler() {
        }
        @SideOnly(Side.CLIENT)
        @Override
        public IMessage onMessage(WorldStagesMessage message, MessageContext ctx) {
            // This is the player the packet was sent to the server from
            if (ctx.side.isClient()) {
                WorldStagesMod.logInfo("Received stages from server: "+message.data.toString());
                HashSet<String> stages = new HashSet<String>();
                for (NBTBase nbt : message.data.getTagList("stages", 8)) {
                    if (nbt instanceof NBTTagString) {
                        stages.add(((NBTTagString) (nbt)).getString());
                    }
                }
                ClientWorldStages.instance.stages = stages;
                MinecraftForge.EVENT_BUS.post(new WorldStageEvent(Minecraft.getMinecraft().world,stages));
            }
            return null;
        }
    }
}
