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
 * File Created @ [Nov 25, 2013, 8:52:43 PM (GMT)]
 */
package vazkii.tinkerer.common.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import thaumcraft.api.IGoggles;
import thaumcraft.api.IRepairable;
import thaumcraft.api.IVisDiscountGear;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.nodes.IRevealer;
import thaumcraft.api.research.ResearchPage;
import thaumcraft.common.config.ConfigItems;
import vazkii.tinkerer.client.core.helper.IconHelper;
import vazkii.tinkerer.client.lib.LibResources;
import vazkii.tinkerer.common.core.handler.ModCreativeTab;
import vazkii.tinkerer.common.lib.LibItemNames;
import vazkii.tinkerer.common.lib.LibResearch;
import vazkii.tinkerer.common.registry.ITTinkererItem;
import vazkii.tinkerer.common.research.ResearchHelper;
import vazkii.tinkerer.common.research.TTResearchItem;

import java.util.ArrayList;
import java.util.List;

public class ItemRevealingHelm extends ItemArmor implements IRepairable, IRevealer, IGoggles, IVisDiscountGear, ITTinkererItem {

	public ItemRevealingHelm() {
		super(ThaumcraftApi.armorMatThaumium, 2, 0);
		setMaxDamage(500);
		setCreativeTab(ModCreativeTab.INSTANCE);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister par1IconRegister) {
		itemIcon = IconHelper.forItem(par1IconRegister, this);
	}

	@Override
	public boolean showNodes(ItemStack paramItemStack, EntityLivingBase paramEntityLivingBase) {
		return true;
	}

	@Override
	public boolean showIngamePopups(ItemStack paramItemStack, EntityLivingBase paramEntityLivingBase) {
		return true;
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer par2EntityPlayer, List list, boolean par4) {
		list.add(StatCollector.translateToLocal("tc.visdiscount") + ": " + 5 + "%");
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
		return LibResources.MODEL_REVEALING_HELM;
	}

	@Override
	public EnumRarity getRarity(ItemStack par1ItemStack) {
		return EnumRarity.uncommon;
	}

	@Override
	public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack) {
		return par2ItemStack.isItemEqual(new ItemStack(ConfigItems.itemResource, 1, 2)) || super.getIsRepairable(par1ItemStack, par2ItemStack);
	}

	@Override
	public int getVisDiscount(ItemStack Itemstack, EntityPlayer Player, Aspect Aspect) {
		return 5;
	}

	@Override
	public ArrayList<Object> getSpecialParameters() {
		return null;
	}

	@Override
	public String getItemName() {
		return LibItemNames.REVEALING_HELM;
	}

	@Override
	public boolean shouldRegister() {
		return true;
	}

	@Override
	public boolean shouldDisplayInTab() {
		return true;
	}

	@Override
	public TTResearchItem getResearchItem() {
		TTResearchItem research;
		research = (TTResearchItem) new TTResearchItem(LibResearch.KEY_REVEALING_HELM, new AspectList().add(Aspect.AURA, 2).add(Aspect.ARMOR, 1), 0, 0, 1, new ItemStack(this)).setParents("GOGGLES").setParentsHidden("THAUMIUM");
		research.setPages(new ResearchPage("0"), ResearchHelper.arcaneRecipePage(LibResearch.KEY_REVEALING_HELM));
		return research;
	}
}
