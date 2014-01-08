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
 * File Created @ [Dec 24, 2013, 9:37:40 PM (GMT)]
 */
package vazkii.tinkerer.common.item.kami.armor;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.EnumHelper;
import net.minecraftforge.common.ISpecialArmor;
import thaumcraft.api.IVisDiscounter;
import vazkii.tinkerer.client.core.helper.IconHelper;
import vazkii.tinkerer.client.core.proxy.TTClientProxy;
import vazkii.tinkerer.client.lib.LibResources;
import vazkii.tinkerer.common.core.handler.ModCreativeTab;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemIchorclothArmor extends ItemArmor implements IVisDiscounter, ISpecialArmor {

	static EnumArmorMaterial material = EnumHelper.addArmorMaterial("ICHOR", 0, new int[] { 3, 8, 6, 3 }, 20);

	public ItemIchorclothArmor(int par1, int par2) {
		super(par1, material, 0, par2);
		setCreativeTab(ModCreativeTab.INSTANCE);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		itemIcon = IconHelper.forItem(par1IconRegister, this);
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, int layer) {
		return slot == 2 ? LibResources.MODEL_ARMOR_ICHOR_2 : LibResources.MODEL_ARMOR_ICHOR_1;
	}

	@Override
	public int getVisDiscount() {
		return armorType == 3 ? 3 : 4;
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer par2EntityPlayer, List list, boolean par4) {
		list.add(StatCollector.translateToLocal("tc.visdiscount") + ": " + getVisDiscount() + "%");
	}

	@Override
	public EnumRarity getRarity(ItemStack par1ItemStack) {
		return TTClientProxy.kamiRarity;
	}

	@Override
	public boolean isItemTool(ItemStack par1ItemStack) {
		return true;
	}

	@Override
	public ArmorProperties getProperties(EntityLivingBase player,ItemStack armor, DamageSource source, double damage, int slot) {
		return new ArmorProperties(0, getArmorMaterial().getDamageReductionAmount(slot) * 0.0425, Integer.MAX_VALUE);
	}

	@Override
	public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot) {
		return getArmorMaterial().getDamageReductionAmount(slot);
	}

	@Override
	public void damageArmor(EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot) {	
		// NO-OP
	}

}
