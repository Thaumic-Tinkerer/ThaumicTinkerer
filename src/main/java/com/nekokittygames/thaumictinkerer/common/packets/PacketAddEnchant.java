package com.nekokittygames.thaumictinkerer.common.packets;

import com.nekokittygames.thaumictinkerer.common.tileentity.TileEntityEnchanter;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketAddEnchant implements IMessage {


    private BlockPos pos;
    private int enchantID;

    public PacketAddEnchant() {

    }

    public PacketAddEnchant(TileEntityEnchanter enchanter, int enchantID) {
        this.pos = enchanter.getPos();
        this.enchantID = enchantID;
    }

    public PacketAddEnchant(BlockPos pos, int enchantID) {
        this.pos = pos;
        this.enchantID = enchantID;
    }

    public BlockPos getPos() {
        return pos;
    }

    public void setPos(BlockPos pos) {
        this.pos = pos;
    }

    public int getEnchantID() {
        return enchantID;
    }

    public void setEnchantID(int enchantID) {
        this.enchantID = enchantID;
    }

    @Override
    public void fromBytes(ByteBuf byteBuf) {
        pos = BlockPos.fromLong(byteBuf.readLong());
        enchantID = byteBuf.readInt();
    }

    @Override
    public void toBytes(ByteBuf byteBuf) {
        byteBuf.writeLong(pos.toLong());
        byteBuf.writeInt(enchantID);
    }

    public static class Handler implements IMessageHandler<PacketAddEnchant, IMessage> {

        @Override
        public IMessage onMessage(PacketAddEnchant packetAddEnchant, MessageContext messageContext) {
            FMLCommonHandler.instance().getWorldThread(messageContext.netHandler).addScheduledTask(() -> handle(packetAddEnchant, messageContext));
            return null;
        }

        private void handle(PacketAddEnchant packetAddEnchant, MessageContext ctx) {
            EntityPlayerMP playerEntity = ctx.getServerHandler().player;
            World world = playerEntity.getEntityWorld();
            if (world.isBlockLoaded(packetAddEnchant.getPos())) {
                TileEntity te = world.getTileEntity(packetAddEnchant.getPos());
                if (te instanceof TileEntityEnchanter) {
                    TileEntityEnchanter enchanter = (TileEntityEnchanter) te;
                    enchanter.appendEnchant(packetAddEnchant.enchantID);
                    enchanter.appendLevel(1);
                }
            }
        }
    }
}
