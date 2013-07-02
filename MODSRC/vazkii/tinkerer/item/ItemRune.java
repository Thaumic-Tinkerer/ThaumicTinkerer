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
 * File Created @ [29 Jun 2013, 20:45:47 (GMT)]
 */
package vazkii.tinkerer.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import thaumcraft.api.EnumTag;
import vazkii.tinkerer.client.util.helper.IconHelper;
import vazkii.tinkerer.lib.LibItemNames;
import vazkii.tinkerer.util.handler.SoulHeartHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemRune extends ItemMod {

	Icon crackIcon;

	public ItemRune(int par1) {
		super(par1);
		setHasSubtypes(true);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List) {
		for(EnumTag tag : EnumTag.class.getEnumConstants())
			if(tag != EnumTag.UNKNOWN)
				par3List.add(new ItemStack(par1, 1, tag.getId()));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses() {
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		itemIcon = IconHelper.forItem(par1IconRegister, this, 0);
		crackIcon = IconHelper.forItem(par1IconRegister, this, 1);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIconFromDamageForRenderPass(int par1, int par2) {
		return par2 == 1 ? crackIcon : itemIcon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack par1ItemStack, int par2) {
		return par2 == 0 ? 0xFFFFFF : EnumTag.get(par1ItemStack.getItemDamage()).color;
	}

	@Override
	public String getItemDisplayName(ItemStack par1ItemStack) {
		return LibItemNames.RUNE_D + EnumTag.get(par1ItemStack.getItemDamage()).name;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		par3List.add(EnumTag.get(par1ItemStack.getItemDamage()).meaning);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack par1ItemStack) {
		return EnumRarity.uncommon;
	}
	
	@Override // TODO TEMPORARY DEBUG!
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
		if(par3EntityPlayer.isSneaking())
			par3EntityPlayer.attackEntityFrom(DamageSource.causeIndirectMagicDamage(par3EntityPlayer, par3EntityPlayer), 1);
		else SoulHeartHandler.addHP(par3EntityPlayer, 1);
		SoulHeartHandler.updateClient(par3EntityPlayer);
		
		return par1ItemStack;
	}
}
