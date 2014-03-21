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
 * File Created @ [Dec 30, 2013, 12:46:22 AM (GMT)]
 */
package vazkii.tinkerer.common.item.kami;

import java.util.Arrays;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import vazkii.tinkerer.client.core.helper.IconHelper;
import vazkii.tinkerer.client.core.proxy.TTClientProxy;
import vazkii.tinkerer.common.block.tile.transvector.TileTransvectorInterface;
import vazkii.tinkerer.common.core.helper.ItemNBTHelper;
import vazkii.tinkerer.common.item.ItemMod;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemBlockTalisman extends ItemMod {

	private static final String TAG_BLOCK_ID = "blockID";
	private static final String TAG_BLOCK_META = "blockMeta";
	private static final String TAG_BLOCK_COUNT = "blockCount";

	Icon enabledIcon;

	public ItemBlockTalisman() {
		super();
		setMaxStackSize(1);
		setHasSubtypes(true);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
		if(getBlockID(par1ItemStack) != 0 && par3EntityPlayer.isSneaking()) {
			int dmg = par1ItemStack.getItemDamage();
			par1ItemStack.setItemDamage(~dmg & 1);
			par2World.playSoundAtEntity(par3EntityPlayer, "random.orb", 0.3F, 0.1F);
		}
		return par1ItemStack;
	}

	@Override
	public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10) {
		int id = par3World.getBlockId(par4, par5, par6);
		int meta = par3World.getBlockMetadata(par4, par5, par6);
		boolean set = setBlock(par1ItemStack, id, meta);

		if(!set) {
			int bid = getBlockID(par1ItemStack);
			int bmeta = getBlockMeta(par1ItemStack);

			TileEntity tile = par3World.getTileEntity(par4, par5, par6);
			if(tile != null && tile instanceof IInventory) {
				IInventory inv = (IInventory) tile;
				int[] slots = inv instanceof ISidedInventory ? ((ISidedInventory) inv).getAccessibleSlotsFromSide(par7) : TileTransvectorInterface.buildSlotsForLinearInventory(inv);
				for(int slot : slots) {
					ItemStack stackInSlot = inv.getStackInSlot(slot);
					if(stackInSlot == null) {
						ItemStack stack = new ItemStack(bid, 1, bmeta);
						int maxSize = stack.getMaxStackSize();
						stack.stackSize = remove(par1ItemStack, maxSize);
						if(stack.stackSize != 0) {
							if(inv.isItemValidForSlot(slot, stack) && (!(inv instanceof ISidedInventory) || ((ISidedInventory) inv).canInsertItem(slot, stack, par7))) {
								inv.setInventorySlotContents(slot, stack);
								inv.onInventoryChanged();
								set = true;
							}
						}
					} else if(stackInSlot.itemID == bid && stackInSlot.getItemDamage() == bmeta) {
						int maxSize = stackInSlot.getMaxStackSize();
						int missing = maxSize - stackInSlot.stackSize;
						if(inv.isItemValidForSlot(slot, stackInSlot) && (!(inv instanceof ISidedInventory) || ((ISidedInventory) inv).canInsertItem(slot, stackInSlot, par7))) {
							stackInSlot.stackSize += remove(par1ItemStack, missing);
							inv.onInventoryChanged();
							set = true;
						}
					}
				}
			} else {
				int remove = remove(par1ItemStack, 1);
				if(remove > 0) {
					Item.itemsList[bid].onItemUse(new ItemStack(bid, 1, bmeta), par2EntityPlayer, par3World, par4, par5, par6, par7, par8, par9, par10);
					set = true;
				}
			}
		}

		par2EntityPlayer.setCurrentItemOrArmor(0, par1ItemStack);

		return set;
	}

	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5) {
		int id = getBlockID(par1ItemStack);
		if(/*!par2World.isRemote && */par1ItemStack.getItemDamage() == 1 && id != 0 && par3Entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) par3Entity;
			int meta = getBlockMeta(par1ItemStack);

			int highest = -1;
			boolean hasFreeSlot = false;
			int[] counts = new int[player.inventory.getSizeInventory() - player.inventory.armorInventory.length];
			Arrays.fill(counts, 0);

			for(int i = 0; i < counts.length; i++) {
				ItemStack stack = player.inventory.getStackInSlot(i);
				if(stack == null) {
					hasFreeSlot = true;
					continue;
				}

				if(stack.itemID == id && stack.getItemDamage() == meta) {
					counts[i] = stack.stackSize;
					if(highest == -1)
						highest = i;
					else highest = counts[i] > counts[highest] && highest > 8 ? i : highest;
				}
			}

			if(highest == -1) {
				ItemStack heldItem = player.inventory.getItemStack();
				if(hasFreeSlot && (heldItem == null || heldItem.itemID != id || heldItem.getItemDamage() != meta)) {
					ItemStack stack = new ItemStack(id, remove(par1ItemStack, 64), meta);
					if(stack.stackSize != 0)
						player.inventory.addItemStackToInventory(stack);
				}
			} else {
				for(int i = 0; i < counts.length; i++) {
					int count = counts[i];

					if(i == highest || count == 0)
						continue;

					add(par1ItemStack, count);
					player.inventory.setInventorySlotContents(i, null);
				}

				int countInHighest = counts[highest];
				int maxSize = new ItemStack(id, 1, meta).getMaxStackSize();
				if(countInHighest < maxSize) {
					int missing = maxSize - countInHighest;
					ItemStack stackInHighest = player.inventory.getStackInSlot(highest);
					stackInHighest.stackSize += remove(par1ItemStack, missing);
				}
			}
		}
	}

	private boolean setBlock(ItemStack stack, int id, int meta) {
		if(getBlockID(stack) == 0 || getBlockCount(stack) == 0) {
			ItemNBTHelper.setInt(stack, TAG_BLOCK_ID, id);
			ItemNBTHelper.setInt(stack, TAG_BLOCK_META, meta);
			return true;
		}
		return false;
	}

	private static void setCount(ItemStack stack, int count) {
		ItemNBTHelper.setInt(stack, TAG_BLOCK_COUNT, count);
	}

	private void add(ItemStack stack, int count) {
		int current = getBlockCount(stack);
		setCount(stack, current + count);
	}

	public static int remove(ItemStack stack, int count) {
		int current = getBlockCount(stack);
		setCount(stack, Math.max(current - count, 0));

		return Math.min(current, count);
	}

	public static int getBlockID(ItemStack stack) {
		return ItemNBTHelper.getInt(stack, TAG_BLOCK_ID, 0);
	}

	public static int getBlockMeta(ItemStack stack) {
		return ItemNBTHelper.getInt(stack, TAG_BLOCK_META, 0);
	}

	public static int getBlockCount(ItemStack stack) {
		return ItemNBTHelper.getInt(stack, TAG_BLOCK_COUNT, 0);
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

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		int id = getBlockID(par1ItemStack);
		if(id != 0 && Block.blocksList[id] != null) {
			int count = getBlockCount(par1ItemStack);
			par3List.add(StatCollector.translateToLocal(new ItemStack(Block.blocksList[id], 1, getBlockMeta(par1ItemStack)).getUnlocalizedName() + ".name") + " (x" + count + ")");
		}

		if(par1ItemStack.getItemDamage() == 1)
			par3List.add(StatCollector.translateToLocal("ttmisc.active"));
		else par3List.add(StatCollector.translateToLocal("ttmisc.inactive"));
	}

	@Override
	public EnumRarity getRarity(ItemStack par1ItemStack) {
		return TTClientProxy.kamiRarity;
	}

}
