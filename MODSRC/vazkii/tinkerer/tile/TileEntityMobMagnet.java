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
 * File Created @ [6 Jul 2013, 15:13:00 (GMT)]
 */

package vazkii.tinkerer.tile;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.packet.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.ForgeDirection;
import thaumcraft.client.codechicken.core.vec.Vector3;
import vazkii.tinkerer.ThaumicTinkerer;
import vazkii.tinkerer.lib.LibBlockNames;
import vazkii.tinkerer.lib.LibMisc;
import vazkii.tinkerer.network.PacketManager;
import vazkii.tinkerer.network.packet.PacketMobMagnetSync;
import vazkii.tinkerer.util.helper.ItemNBTHelper;

public class TileEntityMobMagnet extends TileEntity implements IInventory {

	ItemStack[] inventorySlots = new ItemStack[1];
	private static final String TAG_PATTERN = "pattern";
	private static final String NON_ASSIGNED = "Blank";

	public boolean adult = true;

	@Override
	public void updateEntity() {
		int redstone = 0;
		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS)
			redstone = Math.max(redstone, worldObj.getIndirectPowerLevelTo(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ, dir.ordinal()));

		if(redstone > 0) {
			double x1 = xCoord + 0.5;
			double y1 = yCoord + 0.5;
			double z1 = zCoord + 0.5;

			boolean blue = getBlockMetadata() == 0;
			int speedMod = blue ? 1 : -1;
			double range = redstone / 2;

			AxisAlignedBB boundingBox = AxisAlignedBB.getBoundingBox(x1 - range, yCoord, z1 - range, x1 + range, y1 + range, z1 + range);
			List<EntityLiving> entities = worldObj.getEntitiesWithinAABB(EntityLiving.class, boundingBox);

			for(EntityLiving entity : entities) {
				if(!(entity instanceof EntityPlayer)) {

					if(inventorySlots[0] != null) {		// Filter Exists
						// Fill in Filter effects here
						String pattern = ItemNBTHelper.getString(inventorySlots[0], TAG_PATTERN, NON_ASSIGNED);
						if(entity.getEntityName().equals(pattern)) {
							if(entity instanceof EntityAgeable) {
								if(adult && !(entity.isChild())) {
									moveEntity(blue, entity, x1, y1, z1, speedMod);
								}
								else if(!adult && entity.isChild()) {
									moveEntity(blue, entity, x1, y1, z1, speedMod);
								}
							}
							else {
								moveEntity(blue, entity, x1, y1, z1, speedMod);
							}
						}
						else {
							return;
						}
					}
					else {
						moveEntity(blue, entity, x1, y1, z1, speedMod);
					}
				}
			}
		}
	}

	private static void moveEntity(boolean blue, EntityLiving entity, double x1, double y1, double z1, int speedMod) {
		double x2 = entity.posX;
		double y2 = entity.posY;
		double z2 = entity.posZ;

		float distanceSqrd = blue ? (float) ((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2) + (z1 - z2) * (z1 - z2)) : 1.1F;

		if(distanceSqrd > 1) {
			setEntityMotionFromVector(entity, x1, y1, z1, speedMod * 0.25F);
			ThaumicTinkerer.tcProxy.sparkle((float) x2, (float) y2, (float) z2, blue ? 2 : 4);
		}
	}

	private static void setEntityMotionFromVector(Entity entity, double x, double y, double z, float modifier) {
		Vector3 originalPosVector = new Vector3(x, y, z);
		Vector3 entityVector = Vector3.fromEntityCenter(entity);
		Vector3 finalVector = originalPosVector.subtract(entityVector).normalize();
		entity.motionX = finalVector.x * modifier;
		entity.motionY = finalVector.y * modifier;
		entity.motionZ = finalVector.z * modifier;
	}

	@Override
	public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
		super.readFromNBT(par1NBTTagCompound);

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
		return LibBlockNames.MOB_MAGNET_D;
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
	public boolean isStackValidForSlot(int i, ItemStack itemstack) {
		return true;
	}

	@Override
	public Packet getDescriptionPacket() {
		return PacketManager.generatePacket(new PacketMobMagnetSync(this));
	}
}
