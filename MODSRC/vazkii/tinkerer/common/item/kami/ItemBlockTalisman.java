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
 * File Created @ [Dec 30, 2013, 12:46:22 AM (GMT)]
 */
package vazkii.tinkerer.common.item.kami;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import vazkii.tinkerer.client.core.helper.IconHelper;
import vazkii.tinkerer.client.core.proxy.TTClientProxy;
import vazkii.tinkerer.common.ThaumicTinkerer;
import vazkii.tinkerer.common.item.ItemMod;
import vazkii.tinkerer.common.lib.LibFeatures;

public class ItemBlockTalisman extends ItemMod {

	Icon enabledIcon;
	
	public ItemBlockTalisman(int par1) {
		super(par1);
		setMaxStackSize(1);
		setHasSubtypes(true);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
		int dmg = par1ItemStack.getItemDamage();
		par1ItemStack.setItemDamage(~dmg & 1);
		par2World.playSoundAtEntity(par3EntityPlayer, "random.orb", 0.3F, 0.1F);
		return par1ItemStack;
	}
	
	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5) {
		super.onUpdate(par1ItemStack, par2World, par3Entity, par4, par5);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		itemIcon = IconHelper.forItem(par1IconRegister, this, 0);
		enabledIcon = IconHelper.forItem(par1IconRegister, this, 1);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIconFromDamage(int par1) {
		return par1 == 1 ? enabledIcon : itemIcon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		if(par1ItemStack.getItemDamage() == 1)
			par3List.add(StatCollector.translateToLocal("ttmisc.active"));
		else par3List.add(StatCollector.translateToLocal("ttmisc.inactive"));
	}
	
	@Override
	public EnumRarity getRarity(ItemStack par1ItemStack) {
		return TTClientProxy.kamiRarity;
	}
	
}
