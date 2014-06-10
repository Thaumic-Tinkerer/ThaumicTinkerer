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
 * File Created @ [11 Sep 2013, 17:46:38 (GMT)]
 */
package vazkii.tinkerer.common.item;

import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import vazkii.tinkerer.common.block.ModBlocks;
import vazkii.tinkerer.common.lib.LibItemNames;
import vazkii.tinkerer.common.registry.ItemBase;
import vazkii.tinkerer.common.research.TTResearchItem;

public class ItemBrightNitor extends ItemBase {

	public ItemBrightNitor() {
		super();
		setMaxStackSize(1);
	}

	@Override
	public boolean shouldDisplayInTab() {
		return true;
	}

	@Override
	public TTResearchItem getResearchItem() {
		return null;
	}

	public static int meta = 0;

	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5) {
		int x = (int) Math.floor(par3Entity.posX);
		int y = (int) par3Entity.posY + 1;
		int z = (int) Math.floor(par3Entity.posZ);

		setBlock(x, y, z, par2World);
	}

	public static void setBlock(int x, int y, int z, World world) {
		if ((world.getBlock(x, y, z) == Blocks.air || world.getBlock(x, y, z) == ModBlocks.nitorGas) && !world.isRemote)
			world.setBlock(x, y, z, ModBlocks.nitorGas, meta, 2);
	}

	@Override
	public String getItemName() {
		return LibItemNames.BRIGHT_NTIOR;
	}
}