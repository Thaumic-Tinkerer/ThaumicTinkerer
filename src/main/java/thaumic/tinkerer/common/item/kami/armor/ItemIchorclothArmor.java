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
package thaumic.tinkerer.common.item.kami.armor;

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
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.research.ResearchPage;
import thaumic.tinkerer.client.core.helper.IconHelper;
import thaumic.tinkerer.client.core.proxy.TTClientProxy;
import thaumic.tinkerer.client.lib.LibResources;
import thaumic.tinkerer.common.ThaumicTinkerer;
import thaumic.tinkerer.common.core.handler.ConfigHandler;
import thaumic.tinkerer.common.core.handler.ModCreativeTab;
import thaumic.tinkerer.common.item.kami.ItemKamiResource;
import thaumic.tinkerer.common.lib.LibItemNames;
import thaumic.tinkerer.common.lib.LibResearch;
import thaumic.tinkerer.common.registry.ITTinkererItem;
import thaumic.tinkerer.common.registry.ThaumicTinkererArcaneRecipe;
import thaumic.tinkerer.common.registry.ThaumicTinkererRecipe;
import thaumic.tinkerer.common.research.IRegisterableResearch;
import thaumic.tinkerer.common.research.KamiResearchItem;
import thaumic.tinkerer.common.research.ResearchHelper;

import java.util.ArrayList;
import java.util.List;

public class ItemIchorclothArmor extends ItemArmor implements IVisDiscountGear,
		ISpecialArmor, ITTinkererItem {

	static ItemArmor.ArmorMaterial material = EnumHelper.addArmorMaterial(
			"ICHOR", 0, new int[] { 3, 8, 6, 3 }, 20);

	public ItemIchorclothArmor(int par2) {
		super(material, 0, par2);
		setCreativeTab(ModCreativeTab.INSTANCE);
	}

	public ItemIchorclothArmor(Integer par2) {
		this(par2.intValue());
	}

	public ItemIchorclothArmor() {
		this(0);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister par1IconRegister) {
		itemIcon = IconHelper.forItem(par1IconRegister, this);
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot,
			String type) {
		return slot == 2 ? LibResources.MODEL_ARMOR_ICHOR_2
				: LibResources.MODEL_ARMOR_ICHOR_1;
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer par2EntityPlayer,
			List list, boolean par4) {
		list.add(StatCollector.translateToLocal("tc.visdiscount") + ": "
				+ (armorType == 3 ? 3 : 4) + "%");
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
	public ArmorProperties getProperties(EntityLivingBase player,
			ItemStack armor, DamageSource source, double damage, int slot) {
		return new ArmorProperties(0, getArmorMaterial()
				.getDamageReductionAmount(slot) * 0.0425, Integer.MAX_VALUE);
	}

	@Override
	public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot) {
		return getArmorMaterial().getDamageReductionAmount(slot);
	}

	@Override
	public void damageArmor(EntityLivingBase entity, ItemStack stack,
			DamageSource source, int damage, int slot) {
		// NO-OP
	}

	@Override
	public int getVisDiscount(ItemStack arg0, EntityPlayer arg1, Aspect arg2) {
		return armorType == 3 ? 3 : 4;
	}

	@Override
	public ArrayList<Object> getSpecialParameters() {
		ArrayList<Object> result = new ArrayList<Object>();
		result.add(1);
		result.add(2);
		result.add(3);
		return result;
	}

	@Override
	public String getItemName() {
		switch (armorType) {
            case 3:
                return LibItemNames.ICHOR_BOOTS;
		case 2:
            return LibItemNames.ICHOR_LEGS;
            case 1:
                return LibItemNames.ICHOR_CHEST;
            case 0:
                return LibItemNames.ICHOR_HELM;
		default:
			return "INVAlID ARMOR TYPE";
		}
	}

	@Override
	public boolean shouldRegister() {
		return ConfigHandler.enableKami;
	}

	@Override
	public boolean shouldDisplayInTab() {
		return true;
	}

	@Override
	public IRegisterableResearch getResearchItem() {
		return armorType != 0 ? null
				: (IRegisterableResearch) new KamiResearchItem(
						LibResearch.KEY_ICHORCLOTH_ARMOR, new AspectList()
								.add(Aspect.ARMOR, 2).add(Aspect.CLOTH, 1)
								.add(Aspect.LIGHT, 1).add(Aspect.CRAFT, 1), 17,
                5, 5, new ItemStack(this)).setWarp(4)
                .setConcealed()
						.setParents(LibResearch.KEY_ICHOR_CLOTH)
						.setPages(
								new ResearchPage("0"),
								ResearchHelper
										.arcaneRecipePage(LibResearch.KEY_ICHORCLOTH_HELM),
								ResearchHelper
										.arcaneRecipePage(LibResearch.KEY_ICHORCLOTH_CHEST),
								ResearchHelper
										.arcaneRecipePage(LibResearch.KEY_ICHORCLOTH_LEGS),
								ResearchHelper
										.arcaneRecipePage(LibResearch.KEY_ICHORCLOTH_BOOTS));

	}

	@Override
	public ThaumicTinkererRecipe getRecipeItem() {
		switch (armorType) {
		case 0:
			return new ThaumicTinkererArcaneRecipe(LibResearch.KEY_ICHORCLOTH_HELM,
					LibResearch.KEY_ICHORCLOTH_ARMOR, new ItemStack(this),
					new AspectList().add(Aspect.WATER, 75), "CCC", "C C", 'C',
					new ItemStack(ThaumicTinkerer.registry
							.getFirstItemFromClass(ItemKamiResource.class), 1,
							1));
		case 1:
			return new ThaumicTinkererArcaneRecipe(LibResearch.KEY_ICHORCLOTH_CHEST,
					LibResearch.KEY_ICHORCLOTH_ARMOR, new ItemStack(this),
					new AspectList().add(Aspect.AIR, 75), "C C", "CCC", "CCC",
					'C',
					new ItemStack(ThaumicTinkerer.registry
							.getFirstItemFromClass(ItemKamiResource.class), 1,
							1));
		case 2:
			return new ThaumicTinkererArcaneRecipe(LibResearch.KEY_ICHORCLOTH_LEGS,
					LibResearch.KEY_ICHORCLOTH_ARMOR, new ItemStack(this),
					new AspectList().add(Aspect.FIRE, 75), "CCC", "C C", "C C",
					'C',
					new ItemStack(ThaumicTinkerer.registry
							.getFirstItemFromClass(ItemKamiResource.class), 1,
							1));
		case 3:
			return new ThaumicTinkererArcaneRecipe(LibResearch.KEY_ICHORCLOTH_BOOTS,
					LibResearch.KEY_ICHORCLOTH_ARMOR, new ItemStack(this),
					new AspectList().add(Aspect.EARTH, 75), "C C", "C C", 'C',
					new ItemStack(ThaumicTinkerer.registry
							.getFirstItemFromClass(ItemKamiResource.class), 1,
							1));

		}
		return null;
	}
}
