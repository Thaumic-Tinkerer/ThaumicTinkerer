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
 * File Created @ [9 Sep 2013, 19:22:59 (GMT)]
 */
package thaumic.tinkerer.common.item.foci;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.wands.IWandFocus;
import thaumcraft.api.wands.ItemFocusBasic;
import thaumcraft.common.config.Config;
import thaumic.tinkerer.client.core.helper.IconHelper;
import thaumic.tinkerer.common.registry.ITTinkererItem;
import thaumic.tinkerer.common.registry.ItemBase;
import thaumic.tinkerer.common.registry.ThaumicTinkererRecipe;
import thaumic.tinkerer.common.research.IRegisterableResearch;

import java.util.ArrayList;
import java.util.List;

public abstract class ItemModFocus extends ItemFocusBasic implements ITTinkererItem{

	private IIcon ornament, depth;

	public ItemModFocus() {
		super();
		setMaxDamage(1);
		setNoRepair();
		setMaxStackSize(1);
	}

	protected boolean hasOrnament() {
		return false;
	}

	protected boolean hasDepth() {
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister par1IconRegister) {
		super.registerIcons(par1IconRegister);
		itemIcon=IconHelper.forItem(par1IconRegister,this);
		if (hasOrnament())
			ornament = IconHelper.forItem(par1IconRegister, this, "Orn");
		if (hasDepth())
			depth = IconHelper.forItem(par1IconRegister, this, "Depth");
	}

	@Override
	public ArrayList<Object> getSpecialParameters() {
		return null;
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
	public IRegisterableResearch getResearchItem() {
		return null;
	}

	@Override
	public ThaumicTinkererRecipe getRecipeItem() {
		return null;
	}

	@Override
	public boolean isItemTool(ItemStack par1ItemStack) {
		return true;
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
		AspectList cost = getVisCost(stack);
		if (cost != null) {
			list.add(StatCollector.translateToLocal(isVisCostPerTick(stack) ? "item.Focus.cost2" : "item.Focus.cost1"));
			addVisCostTooltip(cost, stack, player, list, par4);
		}
	}

	protected void addVisCostTooltip(AspectList cost, ItemStack stack, EntityPlayer player, List list, boolean par4) {
		for (Aspect aspect : cost.getAspectsSorted()) {
			float amount = cost.getAmount(aspect) / 100.0F;
			list.add(" " + '\u00a7' + aspect.getChatcolor() + aspect.getName() + '\u00a7' + "r x " + amount);
		}
	}

	@Override
	public int getItemEnchantability() {
		return 5;
	}

	@Override
	public EnumRarity getRarity(ItemStack itemstack) {
		return EnumRarity.rare;
	}

	@Override
	public IIcon getOrnament(ItemStack stack) {
		return ornament;
	}

	@Override
	public IIcon getFocusDepthLayerIcon(ItemStack stack) {
		return depth;
	}

	@Override
	public WandFocusAnimation getAnimation(ItemStack stack) {
		return WandFocusAnimation.WAVE;
	}

	@Override
	public boolean isVisCostPerTick(ItemStack stack) {
		return false;
	}

	public boolean isUseItem(ItemStack stack) {
		return isVisCostPerTick(stack);
	}

	@Override
	public ItemStack onFocusRightClick(ItemStack paramItemStack, World paramWorld, EntityPlayer paramEntityPlayer, MovingObjectPosition paramMovingObjectPosition) {
		if (isUseItem(paramItemStack))
			paramEntityPlayer.setItemInUse(paramItemStack, Integer.MAX_VALUE);

		return paramItemStack;
	}

	@Override
	public void onUsingFocusTick(ItemStack paramItemStack, EntityPlayer paramEntityPlayer, int paramInt) {
		// NO-OP
	}

	@Override
	public void onPlayerStoppedUsingFocus(ItemStack paramItemStack, World paramWorld, EntityPlayer paramEntityPlayer, int paramInt) {
		// NO-OP
	}

	@Override
	public abstract String getSortingHelper(ItemStack paramItemStack);

	@Override
	public boolean onFocusBlockStartBreak(ItemStack paramItemStack, int paramInt1, int paramInt2, int paramInt3, EntityPlayer paramEntityPlayer) {
		return false;
	}



}
