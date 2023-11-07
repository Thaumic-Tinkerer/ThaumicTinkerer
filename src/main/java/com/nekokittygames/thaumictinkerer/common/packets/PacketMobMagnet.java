package com.nekokittygames.thaumictinkerer.common.packets;

import com.nekokittygames.thaumictinkerer.common.tileentity.TileEntityMobMagnet;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketMobMagnet implements IMessage {
    private BlockPos pos;
    private boolean adult;

    public PacketMobMagnet() {

    }
    public PacketMobMagnet(TileEntity te, boolean adult) {
        this(te.getPos(), adult);
    }

    public PacketMobMagnet(BlockPos pos, boolean adult) {
        this.pos = pos;
        this.adult = adult;
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
        this.adult = byteBuf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf byteBuf) {
        byteBuf.writeLong(pos.toLong());
        byteBuf.writeBoolean(adult);
    }

    public static class Handler implements IMessageHandler<PacketMobMagnet, IMessage> {

        @Override
        public IMessage onMessage(PacketMobMagnet packetMobMagnet, MessageContext messageContext) {
            FMLCommonHandler.instance().getWorldThread(messageContext.netHandler).addScheduledTask(() -> handle(packetMobMagnet, messageContext));
            return null;
        }

        private void handle(PacketMobMagnet packetMobMagnet, MessageContext ctx) {
            EntityPlayerMP playerEntity = ctx.getServerHandler().player;
            World world = playerEntity.getEntityWorld();
            if (world.isBlockLoaded(packetMobMagnet.getPos())) {
                TileEntity te = world.getTileEntity(packetMobMagnet.getPos());
                if (te instanceof TileEntityMobMagnet) {
                    TileEntityMobMagnet magnet = (TileEntityMobMagnet) te;
                    magnet.setPullAdults(packetMobMagnet.adult);
                }
            }
        }
    }
}
