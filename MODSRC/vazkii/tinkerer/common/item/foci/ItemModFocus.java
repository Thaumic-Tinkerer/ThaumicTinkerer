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
package vazkii.tinkerer.common.item.foci;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.wands.IWandFocus;
import thaumcraft.common.config.Config;
import vazkii.tinkerer.client.core.helper.IconHelper;
import vazkii.tinkerer.common.item.ItemMod;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class ItemModFocus extends ItemMod implements IWandFocus {

	private Icon ornament, depth;

	public ItemModFocus(int par1) {
		super(par1);
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
	public void registerIcons(IconRegister par1IconRegister) {
		super.registerIcons(par1IconRegister);
		if(hasOrnament())
			ornament = IconHelper.forItem(par1IconRegister, this, "Orn");
		if(hasDepth())
			depth = IconHelper.forItem(par1IconRegister, this, "Depth");
	}

	@Override
	public boolean isItemTool(ItemStack par1ItemStack){
		return true;
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
		AspectList cost = getVisCost();
		if(cost != null && cost.size() > 0) {
			list.add(StatCollector.translateToLocal(isVisCostPerTick() ? "item.Focus.cost2" : "item.Focus.cost1"));
			addVisCostTooltip(cost, stack, player, list, par4);
		}
	}
	
	protected void addVisCostTooltip(AspectList cost, ItemStack stack, EntityPlayer player, List list, boolean par4) {
		for(Aspect aspect : cost.getAspectsSorted()) {
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
	public Icon getOrnament() {
		return ornament;
	}

	@Override
	public Icon getFocusDepthLayerIcon() {
		return depth;
	}

	@Override
	public WandFocusAnimation getAnimation() {
		return WandFocusAnimation.WAVE;
	}

	@Override
	public boolean isVisCostPerTick() {
		return false;
	}

	public boolean isUseItem() {
		return isVisCostPerTick();
	}

	@Override
	public ItemStack onFocusRightClick(ItemStack paramItemStack, World paramWorld, EntityPlayer paramEntityPlayer, MovingObjectPosition paramMovingObjectPosition) {
		if(isUseItem())
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
	public String getSortingHelper(ItemStack paramItemStack) {
		return "00";
	}

	@Override
	public boolean onFocusBlockStartBreak(ItemStack paramItemStack, int paramInt1, int paramInt2, int paramInt3, EntityPlayer paramEntityPlayer) {
		return false;
	}

	@Override
	public boolean acceptsEnchant(int paramInt) {
		return paramInt != Config.enchWandFortune.effectId;
	}

}
