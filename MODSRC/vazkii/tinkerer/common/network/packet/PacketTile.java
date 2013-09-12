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

import net.minecraft.network.INetworkManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import vazkii.tinkerer.common.core.helper.MiscHelper;
import vazkii.tinkerer.common.network.IPacket;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class PacketTile<T extends TileEntity> implements IPacket {

	private static final long serialVersionUID = -1447633008013055477L;

	int dim, x, y, z;

	transient T tile;

	public PacketTile(T tile) {
		this.tile = tile;

		this.x = tile.xCoord;
		this.y = tile.yCoord;
		this.z = tile.zCoord;
		this.dim = tile.worldObj.provider.dimensionId;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void handle(INetworkManager manager, Player player) {
		MinecraftServer server = MiscHelper.server();
		if(server != null) {
			World world = server.worldServerForDimension(dim);
			
			if(world == null) {
				MiscHelper.printCurrentStackTrace("No world found for dimension " + dim + "!");
				return;
			}

			TileEntity tile = world.getBlockTileEntity(x, y, z);
			if(tile != null) {
				T castedTile = (T) tile;
				this.tile = castedTile;
				handle();
			}
		}
	}

	public abstract void handle();
}