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
 * File Created @ [8 May 2013, 20:53:06 (GMT)]
 */
package vazkii.tinkerer.item;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import vazkii.tinkerer.client.util.helper.IconHelper;
import vazkii.tinkerer.util.helper.ItemNBTHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemTeleportSigil extends ItemMod {

	private static final String TAG_X = "x";
	private static final String TAG_Y = "y";
	private static final String TAG_Z = "z";
	private static final String TAG_DIMENSION = "dim";
	
	Icon enabledIcon;
	
	public ItemTeleportSigil(int par1) {
		super(par1);
		setMaxStackSize(1);
	}
	
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		if(stack.getItemDamage() == 1)
			return true;
		
		setData(stack, player.dimension, x, y, z);
		// TODO Fancify

		return true;
	}
	
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
		if(par1ItemStack.getItemDamage() == 1) {
			// TODO Something breaks in MP?
			if(par3EntityPlayer instanceof EntityPlayerMP && !par2World.isRemote) {
				regressStatus(par1ItemStack, (EntityPlayerMP) par3EntityPlayer);
				par1ItemStack.stackSize--;
			}
			// TODO Fancify, Charge!
		}
		
		return par1ItemStack;
	}
	
	@Override
	public boolean getShareTag() {
		return true;
	}
	
	private static void setData(ItemStack stack, int dim, int x, int y, int z) {
		ItemNBTHelper.setInt(stack, TAG_X, x);
		ItemNBTHelper.setInt(stack, TAG_Y, y);
		ItemNBTHelper.setInt(stack, TAG_Z, z);
		ItemNBTHelper.setInt(stack, TAG_DIMENSION, dim);
		stack.setItemDamage(1);
	}
	
	private static void regressStatus(ItemStack stack, EntityPlayerMP player) {
		int x = ItemNBTHelper.getInt(stack, TAG_X, 0);
		int y = ItemNBTHelper.getInt(stack, TAG_Y, 0);
		int z = ItemNBTHelper.getInt(stack, TAG_Z, 0);

		player.playerNetServerHandler.setPlayerLocation(x + 0.5, y + 1.6, z + 0.5, player.rotationPitch, player.rotationYaw);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		itemIcon = IconHelper.forItem(par1IconRegister, this, 0);
		enabledIcon = IconHelper.forItem(par1IconRegister, this, 1);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIconFromDamage(int par1) {
		return par1 == 1 ? enabledIcon : itemIcon;
	}
}
