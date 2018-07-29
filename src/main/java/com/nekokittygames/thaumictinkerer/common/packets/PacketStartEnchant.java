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

public class PacketStartEnchant implements IMessage {


    private BlockPos pos;

    public BlockPos getPos() {
        return pos;
    }

    public void setPos(BlockPos pos) {
        this.pos = pos;
    }



    public PacketStartEnchant()
    {

    }
    public PacketStartEnchant(TileEntityEnchanter enchanter)
    {
        this.pos=enchanter.getPos();
    }

    public PacketStartEnchant(BlockPos pos)
    {
        this.pos=pos;
    }
    @Override
    public void fromBytes(ByteBuf byteBuf) {
        pos=BlockPos.fromLong(byteBuf.readLong());
    }

    @Override
    public void toBytes(ByteBuf byteBuf) {
        byteBuf.writeLong(pos.toLong());
    }

    public static class Handler implements IMessageHandler<PacketStartEnchant,IMessage> {

        @Override
        public IMessage onMessage(PacketStartEnchant packetAddEnchant, MessageContext messageContext) {
            FMLCommonHandler.instance().getWorldThread(messageContext.netHandler).addScheduledTask(() -> handle(packetAddEnchant, messageContext));
            return null;
        }
        private void handle(PacketStartEnchant packetAddEnchant, MessageContext ctx) {
            EntityPlayerMP playerEntity = ctx.getServerHandler().player;
            World world = playerEntity.getEntityWorld();
            if(world.isBlockLoaded(packetAddEnchant.getPos()))
            {
                TileEntity te=world.getTileEntity(packetAddEnchant.getPos());
                if(te instanceof TileEntityEnchanter)
                {
                    TileEntityEnchanter enchanter= (TileEntityEnchanter) te;
                    enchanter.setWorking(true);
                }
            }
        }
    }
}
