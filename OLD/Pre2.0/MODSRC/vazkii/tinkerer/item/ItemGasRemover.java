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
 * File Created @ [20 May 2013, 21:01:32 (GMT)]
 */
package vazkii.tinkerer.item;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import vazkii.tinkerer.block.BlockGas;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemGasRemover extends ItemMod {

	public ItemGasRemover(int par1) {
		super(par1);
		setMaxStackSize(1);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
		if(par3EntityPlayer.isSneaking()) {
			int xs = (int) par3EntityPlayer.posX;
			int ys = (int) par3EntityPlayer.posY;
			int zs = (int) par3EntityPlayer.posZ;

			for(int x = xs - 2; x < xs + 2; x++)
				for(int y = ys - 3; y < ys + 3; y++)
					for(int z = zs - 2; z < zs + 2; z++) {
						int id = par2World.getBlockId(x, y, z);
						if(Block.blocksList[id] != null && Block.blocksList[id] instanceof BlockGas) {
							BlockGas gas = (BlockGas) Block.blocksList[id];
							gas.placeParticle(par2World, x, y, z);
							par2World.setBlock(x, y, z, 0, 0, 1 | 2);
						}
					}

			par2World.playSoundAtEntity(par3EntityPlayer, "thaumcraft.wand", 0.2F, 1F);
		}

		return par1ItemStack;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack par1ItemStack) {
		return EnumRarity.uncommon;
	}
}
