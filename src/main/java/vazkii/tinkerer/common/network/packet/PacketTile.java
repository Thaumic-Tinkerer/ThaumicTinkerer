/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the ThaumicTinkerer Mod.
 *
 * ThaumicTinkerer is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * ThaumicTinkerer is a Derivative Work on Thaumcraft 4.
 * Thaumcraft 4 (c) Azanor 2012
 * (http://www.minecraftforum.net/topic/1585216-)
 *
 * File Created @ [9 Sep 2013, 17:00:10 (GMT)]
 */
package vazkii.tinkerer.common.network.packet;


import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import vazkii.tinkerer.common.core.helper.MiscHelper;
import vazkii.tinkerer.common.network.AbstractPacket;


public abstract class PacketTile<T extends TileEntity> extends AbstractPacket {

    public PacketTile(){
        super();
    }

	private static final long serialVersionUID = -1447633008013055477L;

	protected int dim, x, y, z;

	protected transient T tile;
	protected transient EntityPlayer player;

	public PacketTile(T tile) {
		this.tile = tile;

		this.x = tile.xCoord;
		this.y = tile.yCoord;
		this.z = tile.zCoord;
		this.dim = tile.getWorldObj().provider.dimensionId;
	}

	private void handle(EntityPlayer player) {
        MinecraftServer server = MiscHelper.server();
        this.player = player;

        if(server != null) {
            World world = server.worldServerForDimension(dim);

            if(world == null) {
                MiscHelper.printCurrentStackTrace("No world found for dimension " + dim + "!");
                return;
            }

            TileEntity tile = world.getTileEntity(x,y,z);
            if(tile != null) {
                this.tile = (T) tile;
                handle();
            }
        }
	}

	public abstract void handle();

    @Override
    public void encodeInto(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) {
        byteBuf.writeInt(x);
        byteBuf.writeInt(y);
        byteBuf.writeInt(z);
        byteBuf.writeInt(dim);
    }

    @Override
    public void decodeInto(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) {
        x=byteBuf.readInt();
        y=byteBuf.readInt();
        z=byteBuf.readInt();
        dim=byteBuf.readInt();

    }

    @Override
    public void handleClientSide(EntityPlayer entityPlayer) {
        handle(entityPlayer);
    }

    @Override
    public void handleServerSide(EntityPlayer entityPlayer) {
        handle(entityPlayer);
    }
}