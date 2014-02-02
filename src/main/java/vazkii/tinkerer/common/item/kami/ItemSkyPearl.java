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
 * File Created @ [Jan 10, 2014, 5:26:45 PM (GMT)]
 */
package vazkii.tinkerer.common.item.kami;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import thaumcraft.client.codechicken.core.vec.Vector3;
import vazkii.tinkerer.client.core.proxy.TTClientProxy;
import vazkii.tinkerer.common.block.ModBlocks;
import vazkii.tinkerer.common.core.helper.ItemNBTHelper;
import vazkii.tinkerer.common.core.helper.MiscHelper;
import vazkii.tinkerer.common.item.ItemMod;

public class ItemSkyPearl extends ItemMod {

	public static final String TAG_X = "x";
	public static final String TAG_Y = "y";
	public static final String TAG_Z = "z";
	public static final String TAG_DIM = "dim";

	public ItemSkyPearl(int par1) {
		super(par1);
		setMaxStackSize(1);
	}

	@Override
	public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10) {
		int id = par3World.getBlockId(par4, par5, par6);
		if(id == ModBlocks.warpGate.blockID && !isAttuned(par1ItemStack)) {
			setValues(par1ItemStack, par4, par5, par6, par2EntityPlayer.dimension);
			par3World.playSoundAtEntity(par2EntityPlayer, "random.orb", 0.3F, 0.1F);
		}

		return true;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
		if(par3EntityPlayer.isSneaking() && isAttuned(par1ItemStack)) {
			par2World.playSoundAtEntity(par3EntityPlayer, "random.orb", 0.3F, 0.1F);
			ItemNBTHelper.setInt(par1ItemStack, TAG_Y, -1);
		}

		return par1ItemStack;
	}

	@Override
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		addInfo(par1ItemStack, par2EntityPlayer.dimension, Vector3.fromEntityCenter(par2EntityPlayer), par3List, false);
	}

	public static void addInfo(ItemStack stack, int dim, Vector3 pos, List<String> list, boolean simpleMode) {
		if(isAttuned(stack)) {
			int x = getX(stack);
			int y = getY(stack);
			int z = getZ(stack);
			list.add("X: " + x);
			if(!simpleMode)
				list.add("Y: " + y);
			list.add("Z: " + z);
			if(getDim(stack) != dim) {
				if(!simpleMode)
					list.add(EnumChatFormatting.RED + StatCollector.translateToLocal("ttmisc.differentDim"));
			} else list.add(EnumChatFormatting.BLUE + StatCollector.translateToLocal("ttmisc.distance") + ": " + new BigDecimal(MiscHelper.pointDistanceSpace(x, simpleMode ? 0 : y, z, pos.x, simpleMode ? 0 : pos.y, pos.z)).setScale(2, RoundingMode.UP).toString() + "m");
		}
	}

	@Override
	public boolean hasEffect(ItemStack par1ItemStack) {
		return isAttuned(par1ItemStack);
	}

	public static void setValues(ItemStack stack, int x, int y, int z, int dim) {
		ItemNBTHelper.setInt(stack, TAG_X, x);
		ItemNBTHelper.setInt(stack, TAG_Y, y);
		ItemNBTHelper.setInt(stack, TAG_Z, z);
		ItemNBTHelper.setInt(stack, TAG_DIM, dim);
	}

	public static boolean isAttuned(ItemStack stack) {
		return ItemNBTHelper.detectNBT(stack) && ItemNBTHelper.getInt(stack, TAG_Y, -1) != -1;
	}

	public static int getX(ItemStack stack) {
		if(!isAttuned(stack))
			return 0;

		return ItemNBTHelper.getInt(stack, TAG_X, 0);
	}

	public static int getY(ItemStack stack) {
		if(!isAttuned(stack))
			return 0;

		return ItemNBTHelper.getInt(stack, TAG_Y, 0);
	}

	public static int getZ(ItemStack stack) {
		if(!isAttuned(stack))
			return 0;

		return ItemNBTHelper.getInt(stack, TAG_Z, 0);
	}

	public static int getDim(ItemStack stack) {
		if(!isAttuned(stack))
			return 0;

		return ItemNBTHelper.getInt(stack, TAG_DIM, 0);
	}

	@Override
	public EnumRarity getRarity(ItemStack par1ItemStack) {
		return TTClientProxy.kamiRarity;
	}
}