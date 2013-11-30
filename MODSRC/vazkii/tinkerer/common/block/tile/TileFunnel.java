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
 * File Created @ [28 Sep 2013, 19:34:46 (GMT)]
 */
package vazkii.tinkerer.common.block.tile;

import cpw.mods.fml.common.network.PacketDispatcher;
import net.minecraft.block.BlockHopper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.util.Facing;
import net.minecraftforge.common.ForgeDirection;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;
import thaumcraft.api.aspects.IAspectSource;
import thaumcraft.api.aspects.IEssentiaContainerItem;
import thaumcraft.common.blocks.ItemJarFilled;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.tiles.TileJarFillable;
import vazkii.tinkerer.common.lib.LibBlockNames;

public class TileFunnel extends TileEntity implements ISidedInventory, IAspectContainer {

	ItemStack[] inventorySlots = new ItemStack[1];

	@Override
	public void updateEntity() {
		ItemStack jar = getStackInSlot(0);
		if(jar != null && jar.getItem() instanceof ItemJarFilled) {
			if(!worldObj.isRemote) {
				ItemJarFilled item = (ItemJarFilled) jar.getItem();
				AspectList aspectList = item.getAspects(jar);
				if(aspectList != null && aspectList.size() == 1) {
					Aspect aspect = aspectList.getAspects()[0];

					TileEntity tile = worldObj.getBlockTileEntity(xCoord, yCoord - 1, zCoord);
					if(tile != null && tile instanceof TileEntityHopper) {
						TileEntity tile1 = getHopperFacing(tile.xCoord, tile.yCoord, tile.zCoord, tile.getBlockMetadata());
						if(tile1 instanceof TileJarFillable) {
							TileJarFillable jar1 = (TileJarFillable) tile1;

							AspectList aspectList1 = jar1.getAspects();
							if(aspectList1 != null && aspectList1.size() == 0 || aspectList1.getAspects()[0] == aspect && aspectList1.getAmount(aspectList1.getAspects()[0]) < 64) {
								jar1.addToContainer(aspect, 1);
								item.setAspects(jar, aspectList.remove(aspect, 1));
							}
						}
					}
				}
			}
		}
	}

	@Override
	public void onInventoryChanged() {
		PacketDispatcher.sendPacketToAllInDimension(getDescriptionPacket(), worldObj.provider.dimensionId);
	}
	
	private TileEntity getHopperFacing(int x, int y, int z, int meta) {
		int i = BlockHopper.getDirectionFromMetadata(meta);
		return worldObj.getBlockTileEntity(x + Facing.offsetsXForSide[i], y + Facing.offsetsYForSide[i], z + Facing.offsetsZForSide[i]);
	}

	@Override
	public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
		super.readFromNBT(par1NBTTagCompound);

		readCustomNBT(par1NBTTagCompound);
	}

	@Override
	public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
		super.writeToNBT(par1NBTTagCompound);

		writeCustomNBT(par1NBTTagCompound);
	}

	public void readCustomNBT(NBTTagCompound par1NBTTagCompound) {
		NBTTagList var2 = par1NBTTagCompound.getTagList("Items");
		inventorySlots = new ItemStack[getSizeInventory()];
		for (int var3 = 0; var3 < var2.tagCount(); ++var3) {
			NBTTagCompound var4 = (NBTTagCompound)var2.tagAt(var3);
			byte var5 = var4.getByte("Slot");
			if (var5 >= 0 && var5 < inventorySlots.length)
				inventorySlots[var5] = ItemStack.loadItemStackFromNBT(var4);
		}
	}

	public void writeCustomNBT(NBTTagCompound par1NBTTagCompound) {
		NBTTagList var2 = new NBTTagList();
		for (int var3 = 0; var3 < inventorySlots.length; ++var3) {
			if (inventorySlots[var3] != null) {
				NBTTagCompound var4 = new NBTTagCompound();
				var4.setByte("Slot", (byte)var3);
				inventorySlots[var3].writeToNBT(var4);
				var2.appendTag(var4);
			}
		}
		par1NBTTagCompound.setTag("Items", var2);
	}

	@Override
	public int getSizeInventory() {
		return inventorySlots.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		AspectList aspects = getAspects();
		if(inventorySlots[i] != null && inventorySlots[i].itemID == ConfigItems.itemJarFilled.itemID && aspects == null) {
			inventorySlots[i] = new ItemStack(ConfigBlocks.blockJar);
			onInventoryChanged();
		}
		
		return inventorySlots[i];
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		if (inventorySlots[i] != null) {
			ItemStack stackAt;

			if (inventorySlots[i].stackSize <= j) {
				stackAt = inventorySlots[i];
				inventorySlots[i] = null;
				return stackAt;
			} else {
				stackAt = inventorySlots[i].splitStack(j);

				if (inventorySlots[i].stackSize == 0)
					inventorySlots[i] = null;

				return stackAt;
			}
		}

		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		return getStackInSlot(i);
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		inventorySlots[i] = itemstack;
	}

	@Override
	public String getInvName() {
		return LibBlockNames.FUNNEL;
	}

	@Override
	public boolean isInvNameLocalized() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		return worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) != this ? false : entityplayer.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) <= 64;
	}

	@Override
	public void openChest() {
		// NO-OP
	}

	@Override
	public void closeChest() {
		// NO-OP
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return true;
	}

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound nbttagcompound = new NBTTagCompound();
		writeCustomNBT(nbttagcompound);
		return new Packet132TileEntityData(xCoord, yCoord, zCoord, -999, nbttagcompound);
	}

	@Override
	public void onDataPacket(INetworkManager manager, Packet132TileEntityData packet) {
		super.onDataPacket(manager, packet);
		readCustomNBT(packet.data);
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int var1) {
		return var1 == ForgeDirection.DOWN.ordinal() ? new int[0] : new int[] { 0 };
	}

	@Override
	public boolean canInsertItem(int i, ItemStack itemstack, int j) {
		return itemstack != null && itemstack.getItem() instanceof ItemJarFilled;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return j != ForgeDirection.DOWN.ordinal();
	}

	@Override
	public AspectList getAspects() {
		ItemStack stack = inventorySlots[0];
		return stack != null && stack.getItem() instanceof IEssentiaContainerItem ? ((IEssentiaContainerItem) stack.getItem()).getAspects(stack) : null;
	}

	@Override
	public void setAspects(AspectList paramAspectList) { }

	@Override
	public boolean doesContainerAccept(Aspect paramAspect) {
		return false;
	}

	@Override
	public int addToContainer(Aspect paramAspect, int paramInt) {
		return 0;
	}

	@Override
	public boolean takeFromContainer(Aspect paramAspect, int paramInt) {
		return false;
	}

	@Override
	public boolean takeFromContainer(AspectList paramAspectList) {
		return false;
	}

	@Override
	public boolean doesContainerContainAmount(Aspect paramAspect, int paramInt) {
		return false;
	}

	@Override
	public boolean doesContainerContain(AspectList paramAspectList) {
		return false;
	}

	@Override
	public int containerContains(Aspect paramAspect) {
		return 0;
	}

}
