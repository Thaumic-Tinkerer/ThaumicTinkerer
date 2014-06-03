package vazkii.tinkerer.common.network.packet.kami;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import vazkii.tinkerer.common.ThaumicTinkerer;
import vazkii.tinkerer.common.network.AbstractPacket;

/**
 * Created by Katrina on 28/02/14.
 */
public class PacketToggleArmor extends AbstractPacket {
    private static final long serialVersionUID = -1247633508013055777L;
    public boolean armorStatus;
    public PacketToggleArmor(boolean status)
    {
        armorStatus=status;
    }
    public PacketToggleArmor(){
        super();
    }

    @Override
    public void encodeInto(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) {
        byteBuf.writeBoolean(armorStatus);
    }

    @Override
    public void decodeInto(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) {
        armorStatus=byteBuf.readBoolean();
    }

    @Override
    public void handleClientSide(EntityPlayer entityPlayer) {
        if(entityPlayer !=null)
        {

            ThaumicTinkerer.proxy.setArmor(entityPlayer,armorStatus);

        }
    }

    @Override
    public void handleServerSide(EntityPlayer entityPlayer) {
        if(entityPlayer != null)
        {

            ThaumicTinkerer.proxy.setArmor(entityPlayer,armorStatus);

        }
    }
}
