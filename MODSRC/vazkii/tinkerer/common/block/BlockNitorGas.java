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

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import vazkii.tinkerer.common.ThaumicTinkerer;
import vazkii.tinkerer.common.item.ModItems;

public class BlockNitorGas extends BlockGas {

	public BlockNitorGas(int par1) {
		super(par1);
		setLightValue(0.85F);
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
			List<EntityPlayer> players = par1World.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(par2, par3, par4, par2 + 1, par3 + 1, par4 + 1));
			if(players.isEmpty())
				par1World.setBlockToAir(par2, par3, par4);
			else {
				boolean has = false;
				for(EntityPlayer player : players)
					if(player.inventory.hasItem(ModItems.brightNitor.itemID)) {
						has = true;
						break;
					}

				if(!has)
					par1World.setBlockToAir(par2, par3, par4);
			}
			par1World.scheduleBlockUpdate(par2, par3, par4, blockID, tickRate(par1World));
		}
	}

	@Override
	public void onBlockAdded(World par1World, int par2, int par3, int par4) {
		if(!par1World.isRemote)
			par1World.scheduleBlockUpdate(par2, par3, par4, blockID, tickRate(par1World));
	}

}