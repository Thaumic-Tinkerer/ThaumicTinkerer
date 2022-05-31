package com.nekokittygames.thaumictinkerer.common.packets;

import com.nekokittygames.thaumictinkerer.common.blocks.BlockMagnet;
import com.nekokittygames.thaumictinkerer.common.tileentity.TileEntityMagnet;
import com.nekokittygames.thaumictinkerer.common.tileentity.TileEntityMobMagnet;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketMagnetMode implements IMessage {
    private BlockPos pos;
    private BlockMagnet.MagnetPull mode;

    public PacketMagnetMode() {

    }
    public PacketMagnetMode(TileEntity te, BlockMagnet.MagnetPull mode) {
        this(te.getPos(), mode);
    }

    public PacketMagnetMode(BlockPos pos, BlockMagnet.MagnetPull mode) {
        this.pos = pos;
        this.mode = mode;
    }

    public BlockPos getPos() {
        return pos;
    }

    public void setPos(BlockPos pos) {
        this.pos = pos;
    }

    @Override
    public void fromBytes(ByteBuf byteBuf) {

        this.pos = BlockPos.fromLong(byteBuf.readLong());
        // tmp
        this.mode = ByteBufUtils.readUTF8String(byteBuf).equalsIgnoreCase("push")?BlockMagnet.MagnetPull.PUSH:BlockMagnet.MagnetPull.PULL;
    }

    @Override
    public void toBytes(ByteBuf byteBuf) {
        byteBuf.writeLong(pos.toLong());
        ByteBufUtils.writeUTF8String(byteBuf,mode.getName());
    }

    public static class Handler implements IMessageHandler<PacketMagnetMode, IMessage> {

        @Override
        public IMessage onMessage(PacketMagnetMode packetMobMagnet, MessageContext messageContext) {
            FMLCommonHandler.instance().getWorldThread(messageContext.netHandler).addScheduledTask(() -> handle(packetMobMagnet, messageContext));
            return null;
        }

        private void handle(PacketMagnetMode packetMobMagnet, MessageContext ctx) {
            EntityPlayerMP playerEntity = ctx.getServerHandler().player;
            World world = playerEntity.getEntityWorld();
            if (world.isBlockLoaded(packetMobMagnet.getPos())) {
                TileEntity te = world.getTileEntity(packetMobMagnet.getPos());
                if (te instanceof TileEntityMagnet) {
                    TileEntityMagnet magnet = (TileEntityMagnet) te;
                    magnet.setMode(packetMobMagnet.mode);

                }
            }
        }
    }
}

