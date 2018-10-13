package com.nekokittygames.thaumictinkerer.common.packets;

import com.nekokittygames.thaumictinkerer.common.tileentity.TileEntityAnimationTablet;
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

public class PacketTabletButton implements IMessage {
    public BlockPos getPos() {
        return pos;
    }

    public void setPos(BlockPos pos) {
        this.pos = pos;
    }

    private BlockPos pos;
    private boolean rightClick;

    public boolean isRightClick() {
        return rightClick;
    }

    public void setRightClick(boolean rightClick) {
        this.rightClick = rightClick;
    }

    public PacketTabletButton()
    {

    }
    public PacketTabletButton(TileEntityAnimationTablet te, boolean rightClick)
    {
        this(te.getPos(),rightClick);
    }

    public PacketTabletButton(BlockPos pos, boolean rightClick) {
        this.pos = pos;
        this.rightClick = rightClick;
    }

    @Override
    public void fromBytes(ByteBuf byteBuf) {
        this.pos=BlockPos.fromLong(byteBuf.readLong());
        this.rightClick=byteBuf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf byteBuf) {
        byteBuf.writeLong(this.pos.toLong());
        byteBuf.writeBoolean(this.rightClick);
    }
    public static class Handler implements IMessageHandler<PacketTabletButton,IMessage> {

        @Override
        public IMessage onMessage(PacketTabletButton packetTabletButton, MessageContext messageContext) {
            FMLCommonHandler.instance().getWorldThread(messageContext.netHandler).addScheduledTask(() -> handle(packetTabletButton, messageContext));
            return null;
        }

        private void handle(PacketTabletButton packetTabletButton, MessageContext messageContext) {
            EntityPlayerMP playerEntity = messageContext.getServerHandler().player;
            World world = playerEntity.getEntityWorld();
            if (world.isBlockLoaded(packetTabletButton.getPos())) {
                TileEntity te = world.getTileEntity(packetTabletButton.getPos());
                if (te instanceof TileEntityAnimationTablet) {
                    TileEntityAnimationTablet animationTablet = (TileEntityAnimationTablet) te;
                    animationTablet.setRightClick(packetTabletButton.isRightClick());
                }
            }
        }
    }
}
