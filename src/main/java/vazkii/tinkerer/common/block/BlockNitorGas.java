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
 * File Created @ [11 Sep 2013, 17:47:28 (GMT)]
 */
package vazkii.tinkerer.common.block;

import java.util.List;
import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import vazkii.tinkerer.common.ThaumicTinkerer;
import vazkii.tinkerer.common.item.ModItems;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockNitorGas extends BlockGas {

	public BlockNitorGas() {
		super();
	}

	@Override
	public int tickRate(World par1World) {
		return par1World.provider.dimensionId == -1 ? 60 : 20;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random) {
		if(par5Random.nextFloat() < 0.03F)
			ThaumicTinkerer.tcProxy.sparkle(par2 + 0.5F, par3 + 0.5F, par4 + 0.5F, 1F, 4, par5Random.nextFloat() / 2);
	}

	@Override
	public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random) {
		if(!par1World.isRemote) {
			int dist = par1World.getBlockMetadata(par2, par3, par4) == 1 ? 6 : 1;
			List<EntityPlayer> players = par1World.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(par2 - dist, par3 - dist, par4 - dist, par2 + dist, par3 + dist, par4 + dist));
			if(players.isEmpty())
				par1World.setBlockToAir(par2, par3, par4);
			else {
				boolean has = false;
				for(EntityPlayer player : players)
					if(player.inventory.hasItem(ModItems.brightNitor) || ModItems.ichorLegsGem != null && player.getCurrentArmor(1) != null && player.getCurrentArmor(1).getItem() == ModItems.ichorLegsGem) {
						has = true;
						break;
					}

				if(!has)
					par1World.setBlockToAir(par2, par3, par4);
			}
			par1World.scheduleBlockUpdate(par2, par3, par4, this, tickRate(par1World));
		}
	}

	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z) {
		return world.getBlockMetadata(x, y, z) == 1 ? 15 : 12;
	}

	@Override
	public void onBlockAdded(World par1World, int par2, int par3, int par4) {
		if(!par1World.isRemote)
			par1World.scheduleBlockUpdate(par2, par3, par4, this, tickRate(par1World));
	}

}