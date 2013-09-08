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
 * File Created @ [8 Sep 2013, 22:12:14 (GMT)]
 */
package vazkii.tinkerer.common.item;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemGas extends ItemMod {

	private Block setBlock;

	public ItemGas(int par1, Block setBlock) {
		super(par1);
		this.setBlock = setBlock;
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
				par2World.setBlock(x, y, z, setBlock.blockID, 4, 2);
			else par3EntityPlayer.swingItem();
			par2World.scheduleBlockUpdate(x, y, z, setBlock.blockID, 10);
		}

		return par1ItemStack;
	}

}