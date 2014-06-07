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

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.ISpecialArmor;
import net.minecraftforge.common.util.EnumHelper;
import thaumcraft.api.IVisDiscountGear;
import thaumcraft.api.aspects.Aspect;
import vazkii.tinkerer.client.core.helper.IconHelper;
import vazkii.tinkerer.client.core.proxy.TTClientProxy;
import vazkii.tinkerer.client.lib.LibResources;
import vazkii.tinkerer.common.core.handler.ModCreativeTab;

import java.util.List;

public class ItemIchorclothArmor extends ItemArmor implements IVisDiscountGear, ISpecialArmor {

	static ItemArmor.ArmorMaterial material = EnumHelper.addArmorMaterial("ICHOR", 0, new int[]{ 3, 8, 6, 3 }, 20);

	public ItemIchorclothArmor(int par2) {
		super(material, 0, par2);
		setCreativeTab(ModCreativeTab.INSTANCE);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister par1IconRegister) {
		itemIcon = IconHelper.forItem(par1IconRegister, this);
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
		return slot == 2 ? LibResources.MODEL_ARMOR_ICHOR_2 : LibResources.MODEL_ARMOR_ICHOR_1;
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer par2EntityPlayer, List list, boolean par4) {
		list.add(StatCollector.translateToLocal("tc.visdiscount") + ": " + (armorType == 3 ? 3 : 4) + "%");
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
	public ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, int slot) {
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

	@Override
	public int getVisDiscount(ItemStack arg0, EntityPlayer arg1, Aspect arg2) {
		return armorType == 3 ? 3 : 4;
	}

}
