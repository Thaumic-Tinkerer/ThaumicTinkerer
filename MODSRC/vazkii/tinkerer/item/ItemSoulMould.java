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

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

import vazkii.tinkerer.util.handler.EntityInteractionHandler;
import vazkii.tinkerer.util.helper.ItemNBTHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


public class ItemSoulMould extends ItemMod {

	private static final String TAG_PATTERN = "pattern";
	private static final String NON_ASSIGNED = "Blank";
	private String pattern = "Blank";

	public ItemSoulMould(int par1) {
		super(par1);
		setMaxStackSize(1);
	}

	@Override
	public boolean itemInteractionForEntity(ItemStack par1ItemStack, EntityLiving par2EntityLiving) {
		EntityPlayer player = EntityInteractionHandler.getLastInteractingPlayer();
		setPattern(player.getCurrentEquippedItem(), par2EntityLiving);
		return true;
	}
	
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		String name = getPatternName(par1ItemStack);
		par3List.add(EnumChatFormatting.GOLD + "Current Pattern: " + name);
	}
	
	private void setPattern(ItemStack par1ItemStack, EntityLiving par2EntityLiving) {
		ItemNBTHelper.setString(par1ItemStack, TAG_PATTERN, (String)EntityList.classToStringMapping.get(par2EntityLiving.getClass()));
	}
	
	private String getPatternName(ItemStack par1ItemStack) {
		return ItemNBTHelper.getString(par1ItemStack, TAG_PATTERN, NON_ASSIGNED);
	}
}