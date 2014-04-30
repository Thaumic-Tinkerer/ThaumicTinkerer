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
 * File Created @ [Dec 29, 2013, 10:15:39 PM (GMT)]
 */
package vazkii.tinkerer.common.item.kami;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import thaumcraft.common.items.wands.ItemFocusPouch;
import vazkii.tinkerer.client.core.helper.IconHelper;
import vazkii.tinkerer.client.core.proxy.TTClientProxy;
import vazkii.tinkerer.common.ThaumicTinkerer;
import vazkii.tinkerer.common.core.handler.ModCreativeTab;
import vazkii.tinkerer.common.lib.LibGuiIDs;

public class ItemIchorPouch extends ItemFocusPouch implements IBauble {

	public ItemIchorPouch() {
		super();
		setCreativeTab(ModCreativeTab.INSTANCE);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister par1IconRegister) {
		itemIcon = IconHelper.forItem(par1IconRegister, this);
	}

	@Override
	public IIcon getIconFromDamage(int par1) {
		return itemIcon;
	}

	@Override
	public EnumRarity getRarity(ItemStack par1ItemStack) {
		return TTClientProxy.kamiRarity;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
		par3EntityPlayer.openGui(ThaumicTinkerer.instance, LibGuiIDs.GUI_ID_ICHOR_POUCH, par2World, 0, 0, 0);
		return par1ItemStack;
	}

	@Override
	public ItemStack[] getInventory(ItemStack item) {
		ItemStack[] stackList = new ItemStack[13 * 9];
		if (item.hasTagCompound()) {
			NBTTagList var2 = item.stackTagCompound.getTagList("Inventory", Constants.NBT.TAG_COMPOUND);
			for (int var3 = 0; var3 < var2.tagCount(); var3++) {
				NBTTagCompound var4 = (NBTTagCompound)var2.getCompoundTagAt(var3);
				int var5 = var4.getByte("Slot") & 0xFF;
				if (var5 >= 0 && var5 < stackList.length)
					stackList[var5] = ItemStack.loadItemStackFromNBT(var4);
			}
		}

		return stackList;
	}

    @Override
    public BaubleType getBaubleType(ItemStack itemstack) {
        return BaubleType.BELT;
    }

    @Override
    public void onWornTick(ItemStack itemstack, EntityLivingBase player) {

    }

    @Override
    public void onEquipped(ItemStack itemstack, EntityLivingBase player) {

    }

    @Override
    public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {

    }

    @Override
    public boolean canEquip(ItemStack itemstack, EntityLivingBase player) {
        return true;
    }

    @Override
    public boolean canUnequip(ItemStack itemstack, EntityLivingBase player) {
        return true;
    }
}
