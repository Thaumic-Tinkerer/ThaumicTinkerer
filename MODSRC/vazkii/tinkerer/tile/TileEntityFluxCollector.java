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
 * File Created @ [1 Jun 2013, 22:59:38 (GMT)]
 */
package vazkii.tinkerer.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.packet.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import thaumcraft.api.EnumTag;
import thaumcraft.api.ObjectTags;
import thaumcraft.api.aura.AuraNode;
import thaumcraft.common.Config;
import thaumcraft.common.aura.AuraManager;
import vazkii.tinkerer.lib.LibBlockNames;
import vazkii.tinkerer.lib.LibFeatures;
import vazkii.tinkerer.network.PacketManager;
import vazkii.tinkerer.network.packet.PacketFluxCollectorSync;
import vazkii.tinkerer.util.helper.ItemNBTHelper;
import vazkii.tinkerer.util.helper.MiscHelper;
import cpw.mods.fml.common.network.PacketDispatcher;

public class TileEntityFluxCollector extends TileEntity implements ISidedInventory, net.minecraftforge.common.ISidedInventory {

	private static final String TAG_ASPECT = "aspect";
	private static final String TAG_TICKS_EXISTED = "ticksExisted";

	ItemStack[] inventorySlots = new ItemStack[2];

	public double ticksExisted = 0;

	public int aspect = -1;

	@Override
	public void updateEntity() {
		if(getStackInSlot(1) != null) {
			ItemStack stack = getStackInSlot(1);
			aspect = stack.getItemDamage();
			PacketDispatcher.sendPacketToAllPlayers(getDescriptionPacket());
			setInventorySlotContents(1, null);
		}

		if(ticksExisted % LibFeatures.FLUX_CONDENSER_INTERVAL == 0) {
			ItemStack jar = getStackInSlot(0);
			if(aspect >= 0 && jar != null) {
				EnumTag tag = EnumTag.get(aspect);
				AuraNode node = MiscHelper.getClosestNode(worldObj, xCoord, yCoord, zCoord);
				if(node != null && node.level > 0) {
					ObjectTags flux = node.flux;
					if(flux != null && flux.getAmount(tag) > 0) {
						flux.getAmount(tag);

						boolean emptyJar = jar.itemID == Config.blockJar.blockID;
						boolean can = emptyJar;
						if(!can) {
							NBTTagCompound cmp = jar.getTagCompound();
							int aspect = cmp.getByte("tag");
							int jarAmount = cmp.getByte("amount");

							can = aspect == this.aspect && jarAmount < 64;
						}

						if(can) {
							node.flux.reduceAmount(tag, 1);
							if(worldObj.rand.nextInt(LibFeatures.FLUX_CONDENSER_VIS_CHANCE) == 0)
								node.level--;
							AuraManager.addToAuraUpdateList(node);

							if(emptyJar) {
								jar.itemID = Config.itemJarFilled.itemID;
								ItemNBTHelper.setByte(jar, "tag", (byte) aspect);
								ItemNBTHelper.setByte(jar, "amount", (byte) 1);
							} else ItemNBTHelper.setByte(jar, "amount", (byte) (ItemNBTHelper.getByte(jar, "amount", (byte) 0) + 1));
						}
					}
				}
			}
		}

		++ticksExisted;
	}

	@Override
	public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
		super.readFromNBT(par1NBTTagCompound);

		aspect = par1NBTTagCompound.getInteger(TAG_ASPECT);
		ticksExisted = par1NBTTagCompound.getInteger(TAG_TICKS_EXISTED);

		NBTTagList var2 = par1NBTTagCompound.getTagList("Items");
		inventorySlots = new ItemStack[getSizeInventory()];
		for (int var3 = 0; var3 < var2.tagCount(); ++var3) {
			NBTTagCompound var4 = (NBTTagCompound)var2.tagAt(var3);
			byte var5 = var4.getByte("Slot");
			if (var5 >= 0 && var5 < inventorySlots.length)
				inventorySlots[var5] = ItemStack.loadItemStackFromNBT(var4);
		}
	}

	@Override
    public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
        super.writeToNBT(par1NBTTagCompound);

        par1NBTTagCompound.setInteger(TAG_ASPECT, aspect);
        par1NBTTagCompound.setDouble(TAG_TICKS_EXISTED, ticksExisted);

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
		if(i >= inventorySlots.length)
			return null;
		return inventorySlots[i];
	}

	@Override
	public ItemStack decrStackSize(int par1, int par2) {
		if (inventorySlots[par1] != null) {
            ItemStack stackAt;

            if (inventorySlots[par1].stackSize <= par2) {
                stackAt = inventorySlots[par1];
                inventorySlots[par1] = null;
                return stackAt;
            } else {
                stackAt = inventorySlots[par1].splitStack(par2);

                if (inventorySlots[par1].stackSize == 0)
                    inventorySlots[par1] = null;

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
		return LibBlockNames.FLUX_COLLECTOR_D;
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
	public Packet getDescriptionPacket() {
		return PacketManager.generatePacket(new PacketFluxCollectorSync(this));
	}

	@Override
	public boolean isStackValidForSlot(int i, ItemStack itemstack) {
		return false;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int var1) {
		return new int[0];
	}

	@Override
	public boolean canInsertItem(int i, ItemStack itemstack, int j) {
		return false;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return canInsertItem(i, itemstack, j);
	}

	@Override
	@Deprecated
	public int getStartInventorySide(ForgeDirection side) {
		return 0;
	}

	@Override
	@Deprecated
	public int getSizeInventorySide(ForgeDirection side) {
		return 0;
	}
}
