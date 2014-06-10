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
 * File Created @ [12 Sep 2013, 17:36:35 (GMT)]
 */
package vazkii.tinkerer.common.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import vazkii.tinkerer.common.core.helper.ItemNBTHelper;
import vazkii.tinkerer.common.lib.LibItemNames;
import vazkii.tinkerer.common.registry.ItemBase;
import vazkii.tinkerer.common.research.IRegisterableResearch;

import java.util.List;

public class ItemSoulMould extends ItemBase {

	private static final String TAG_PATTERN = "pattern";

	public ItemSoulMould() {
		setMaxStackSize(1);
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
	public boolean itemInteractionForEntity(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, EntityLivingBase par3EntityLivingBase) {
		if (par2EntityPlayer != null)
			setPattern(par2EntityPlayer.getCurrentEquippedItem(), par3EntityLivingBase);
		else setPattern(par1ItemStack, par3EntityLivingBase);
		return true;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
		if (par3EntityPlayer.isSneaking())
			clearPattern(par1ItemStack);

		return par1ItemStack;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		String name = getPatternName(par1ItemStack);

		if (name.isEmpty())
			par3List.add(StatCollector.translateToLocal("ttmisc.soulMould.nonAssigned"));
		else
			par3List.add(String.format(StatCollector.translateToLocal("ttmisc.soulMould.pattern"), StatCollector.translateToLocal("entity." + name + ".name")));
	}

	private static void setPattern(ItemStack par1ItemStack, EntityLivingBase par2EntityLiving) {
		ItemNBTHelper.setString(par1ItemStack, TAG_PATTERN, EntityList.getEntityString(par2EntityLiving));
	}

	public static String getPatternName(ItemStack par1ItemStack) {
		return ItemNBTHelper.getString(par1ItemStack, TAG_PATTERN, "");
	}

	private static void clearPattern(ItemStack par1ItemStack) {
		ItemNBTHelper.getNBT(par1ItemStack).removeTag(TAG_PATTERN);
	}

	@Override
	public String getItemName() {
		return LibItemNames.SOUL_MOULD;
	}
}