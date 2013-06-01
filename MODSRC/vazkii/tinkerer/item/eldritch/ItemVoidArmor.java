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
 * File Created @ [1 Jun 2013, 18:35:50 (GMT)]
 */
package vazkii.tinkerer.item.eldritch;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.EnumHelper;
import vazkii.tinkerer.client.util.helper.IconHelper;
import vazkii.tinkerer.core.proxy.TTClientProxy;
import vazkii.tinkerer.lib.LibResources;
import vazkii.tinkerer.util.helper.ModCreativeTab;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemVoidArmor extends ItemArmor {

	private static final String MATERIAL_NAME = "Void";
	private static final EnumArmorMaterial MATERIAL = EnumHelper.addArmorMaterial(MATERIAL_NAME, 45, new int[] { 3, 8, 6, 3 }, 17);

	public ItemVoidArmor(int par1, int type) {
		super(par1, MATERIAL, 0, type);
		setCreativeTab(ModCreativeTab.INSTANCE);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		itemIcon = IconHelper.forItem(par1IconRegister, this);
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, int layer) {
		return slot == 2 ? LibResources.MODEL_VOID_ARMOR1 : LibResources.MODEL_VOID_ARMOR;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack par1ItemStack) {
		return TTClientProxy.rarityVoid;
	}
}
