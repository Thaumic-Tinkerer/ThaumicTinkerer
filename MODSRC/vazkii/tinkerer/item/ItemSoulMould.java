/**
 * This class was created by <TheWhiteWolves>. It's distributed as
 * part of the Thaumic Tinkerer Mod.
 * 
 * Thaumic Tinkerer is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 * 
 * Thaumic Tinkerer is a Derivative Work on Thaumcraft 3.
 * Thaumcraft 3 © Azanor 2012
 * (http://www.minecraftforum.net/topic/1585216-)
 * 
 * File Created @ [7 Jul 2013, 13:58:53 (GMT)]
 */
package vazkii.tinkerer.item;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import vazkii.tinkerer.item.ItemMod;
import vazkii.tinkerer.util.helper.ItemNBTHelper;

public class ItemSoulMould extends ItemMod {

	private static final String TAG_PATTERN = "pattern";
	private static final String TAG_PATTERN_NAME = "patternName";

	public ItemSoulMould(int par1) {
		super(par1);
		setMaxStackSize(1);
	}

	@Override
	public boolean itemInteractionForEntity(ItemStack par1ItemStack, EntityLiving par2EntityLiving) {
		storePattern(par1ItemStack, par2EntityLiving);
		return true;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
		//EntityLiving pattern = getPattern(par1ItemStack, par2World);
		if(par3EntityPlayer.isSneaking()) {
			clearPattern(par1ItemStack);
		}
		return par1ItemStack;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack par1ItemStack) {
		return EnumRarity.uncommon;
	}

	@Override
	public boolean getShareTag() {
		return true;
	}
	
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		if(par1ItemStack.getTagCompound() == null) {
			par3List.add(EnumChatFormatting.GOLD + "Current Pattern: Blank");
		}
		else {
			par3List.add(EnumChatFormatting.GOLD + "Current Pattern: " + StatCollector.translateToLocal("entity." + par1ItemStack.getTagCompound().getString("id") + ".name"));
		}
	}
	
	public EntityLiving getPattern(ItemStack par1ItemStack, World par2World) {
		String patternName = ItemNBTHelper.getString(par1ItemStack, TAG_PATTERN_NAME, "Blank");
		NBTTagCompound tag = ItemNBTHelper.getNBT(par1ItemStack);
		System.out.println(patternName);
		return null;
	}

	public static void storePattern(ItemStack par1ItemStack, EntityLiving par2EntityLiving) {
		System.out.println("entered pattern storing");
		if(!(par2EntityLiving instanceof EntityPlayer)) {
			NBTTagCompound tag = new NBTTagCompound();
			par2EntityLiving.writeToNBT(tag);
			tag.setString("id", (String)EntityList.classToStringMapping.get(par2EntityLiving.getClass()));
			par1ItemStack.setTagCompound(tag);
		}
	}

	private void clearPattern(ItemStack par1ItemStack) {
		ItemNBTHelper.setCompound(par1ItemStack, null);
	}
}
