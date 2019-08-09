package com.nekokittygames.thaumictinkerer.common.packets;

import com.nekokittygames.thaumictinkerer.common.tileentity.TileEntityEnchanter;
import io.netty.buffer.ByteBuf;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketIncrementEnchantLevel implements IMessage {

    private BlockPos pos;
    private int enchantID;
    private boolean plus;

    public PacketIncrementEnchantLevel(BlockPos pos, int enchantID, boolean plus) {
        this.pos = pos;
        this.enchantID = enchantID;
        this.plus = plus;
    }

    public PacketIncrementEnchantLevel() {

    }

    public PacketIncrementEnchantLevel(TileEntityEnchanter enchanter, int enchantID, boolean plus) {
        this(enchanter.getPos(), enchantID, plus);
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

    public boolean isPlus() {
        return plus;
    }

    public void setPlus(boolean plus) {
        this.plus = plus;
    }

    @Override
    public void fromBytes(ByteBuf byteBuf) {
        pos = BlockPos.fromLong(byteBuf.readLong());
        enchantID = byteBuf.readInt();
        plus = byteBuf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf byteBuf) {
        byteBuf.writeLong(pos.toLong());
        byteBuf.writeInt(enchantID);
        byteBuf.writeBoolean(plus);
    }

    public static class Handler implements IMessageHandler<PacketIncrementEnchantLevel, IMessage> {
        @Override
        public IMessage onMessage(PacketIncrementEnchantLevel packetIncrementEnchantLevel, MessageContext messageContext) {
            FMLCommonHandler.instance().getWorldThread(messageContext.netHandler).addScheduledTask(() -> handle(packetIncrementEnchantLevel, messageContext));
            return null;
        }

        private void handle(PacketIncrementEnchantLevel packetIncrementEnchantLevel, MessageContext ctx) {
            EntityPlayerMP playerEntity = ctx.getServerHandler().player;
            World world = playerEntity.getEntityWorld();
            if (world.isBlockLoaded(packetIncrementEnchantLevel.getPos())) {
                TileEntity te = world.getTileEntity(packetIncrementEnchantLevel.getPos());
                if (te instanceof TileEntityEnchanter) {
                    TileEntityEnchanter enchanter = (TileEntityEnchanter) te;
                    int index = enchanter.getEnchantments().indexOf(packetIncrementEnchantLevel.enchantID);
                    if (index != -1) {
                        Enchantment enchantment = Enchantment.getEnchantmentByID(enchanter.getEnchantments().get(index));
                        int currentLevel = enchanter.getLevels().get(index);
                        if (packetIncrementEnchantLevel.plus) {
                            currentLevel++;
                            currentLevel = Math.min(currentLevel, enchantment.getMaxLevel());
                        } else {
                            currentLevel--;
                            currentLevel = Math.max(currentLevel, enchantment.getMinLevel());
                        }
                        enchanter.getLevels().set(index, currentLevel);
                        enchanter.sendUpdates();
                    }
                }
            }
        }
    }
}
