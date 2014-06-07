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
 * File Created @ [9 Sep 2013, 00:02:52 (GMT)]
 */
package vazkii.tinkerer.common.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import vazkii.tinkerer.common.block.BlockGas;

public class ItemGasRemover extends ItemMod {

	public ItemGasRemover() {
		super();
		setMaxStackSize(1);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
		if (par3EntityPlayer.isSneaking()) {
			int xs = (int) par3EntityPlayer.posX;
			int ys = (int) par3EntityPlayer.posY;
			int zs = (int) par3EntityPlayer.posZ;

			for (int x = xs - 3; x < xs + 3; x++)
				for (int y = ys - 3; y < ys + 3; y++)
					for (int z = zs - 3; z < zs + 3; z++) {
						Block block = par2World.getBlock(x, y, z);
						if (block != null && block instanceof BlockGas) {
							BlockGas gas = (BlockGas) block;
							gas.placeParticle(par2World, x, y, z);
							par2World.setBlock(x, y, z, Blocks.air, 0, 1 | 2);
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
