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
 * File Created @ [25 Apr 2013, 11:40:30 (GMT)]
 */
package vazkii.tinkerer.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import vazkii.tinkerer.block.ModBlocks;

public class ItemGlowstoneGas extends ItemMod {

	public ItemGlowstoneGas(int par1) {
		super(par1);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
		int x = (int) par3EntityPlayer.posX;
		int y = (int) par3EntityPlayer.posY + 1;
		int z = (int) par3EntityPlayer.posZ;
		boolean air = par2World.isAirBlock(x, y, z);

        if (!par3EntityPlayer.capabilities.isCreativeMode)
        	par1ItemStack.stackSize--;

        par2World.playSoundAtEntity(par3EntityPlayer, "random.pop", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

		if(air) {
			if(!par2World.isRemote)
				par2World.setBlock(x, y, z, ModBlocks.glowstoneGas.blockID, 4, 2);
			par2World.scheduleBlockUpdate(x, y, z, ModBlocks.glowstoneGas.blockID, 10);
		}

		return par1ItemStack;
	}

}
