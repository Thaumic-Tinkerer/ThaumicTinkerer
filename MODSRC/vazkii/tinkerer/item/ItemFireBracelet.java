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
 * File Created @ [2 May 2013, 11:50:56 (GMT)]
 */
package vazkii.tinkerer.item;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import vazkii.tinkerer.client.util.helper.IconHelper;
import vazkii.tinkerer.lib.LibFeatures;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemFireBracelet extends ItemMod {

	Icon disabledIcon;

	public ItemFireBracelet(int par1) {
		super(par1);
		setMaxStackSize(1);
		setMaxDamage(LibFeatures.FIRE_BRACELET_CHARGES + 1);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World,	EntityPlayer par3EntityPlayer) {
		if(par1ItemStack.getItemDamage() != LibFeatures.FIRE_BRACELET_CHARGES) {
			par1ItemStack.damageItem(1, par3EntityPlayer);
			// TODO Complete
		}
		return super.onItemRightClick(par1ItemStack, par2World, par3EntityPlayer);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		itemIcon = IconHelper.forItem(par1IconRegister, this, 1);
		disabledIcon = IconHelper.forItem(par1IconRegister, this, 0);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIconFromDamage(int par1) {
		return par1 == LibFeatures.FIRE_BRACELET_CHARGES ? disabledIcon : itemIcon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack par1ItemStack) {
		return EnumRarity.uncommon;
	}
}
