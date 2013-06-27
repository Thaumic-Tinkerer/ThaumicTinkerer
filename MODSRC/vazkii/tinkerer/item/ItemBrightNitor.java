/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the ThaumicTinkerer Mod.
 *
 * ThaumicTinkerer is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * ThaumicTinkerer is a Derivative Work on Thaumcraft 3.
 * Thaumcraft 3 © Azanor 2012
 * (http://www.minecraftforum.net/topic/1585216-)
 *
 * File Created @ [27 Jun 2013, 18:00:05 (GMT)]
 */
package vazkii.tinkerer.item;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import vazkii.tinkerer.block.ModBlocks;

public class ItemBrightNitor extends ItemMod {

	public ItemBrightNitor(int par1) {
		super(par1);
		setMaxStackSize(1);
	}

	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5) {
		int x = (int) par3Entity.posX;
		int y = (int) par3Entity.posY;
		int z = (int) par3Entity.posZ;

		if((par2World.getBlockId(x, y, z) == 0 || par2World.getBlockId(x, y, z) == ModBlocks.nitorGas.blockID) && !par2World.isRemote)
			par2World.setBlock(x, y, z, ModBlocks.nitorGas.blockID, 0, 2);
	}
}