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
 * File Created @ [13 May 2013, 19:36:07 (GMT)]
 */
package vazkii.tinkerer.tile;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.packet.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;
import vazkii.tinkerer.block.ModBlocks;
import vazkii.tinkerer.lib.LibBlockNames;
import vazkii.tinkerer.network.PacketManager;
import vazkii.tinkerer.network.packet.PacketAnimationTabletSync;

public class TileEntityAnimationTablet extends TileEntity implements IInventory {

	private static final String TAG_LEFT_CLICK = "leftClick";
	private static final String TAG_REDSTONE = "redstone";
	private static final String TAG_PROGRESS = "progress";
	private static final String TAG_MOD = "mod";

	private static final int[][] LOC_INCREASES = new int[][] {
		{ 0, -1 },
		{ 0, +1 },
		{ -1, 0 },
		{ +1, 0 }
	};

	private static final int SWING_SPEED = 3;
	private static final int MAX_DEGREE = 45;

	List<Entity> detectedEntities;

	ItemStack[] inventorySlots = new ItemStack[1];
	public double ticksExisted = 0;

	public boolean leftClick = true;
	public boolean redstone = false;

	public int swingProgress = 0;
	private int swingMod = 0;

	@Override
	public void updateEntity() {
		ticksExisted++;

		if(getStackInSlot(0) != null) {
			if(swingProgress >= MAX_DEGREE)
				swingHit();

			swingMod = swingProgress <= 0 ? 0 : swingProgress >= MAX_DEGREE ? -SWING_SPEED : swingMod;
			swingProgress += swingMod;
			if(swingProgress < 0)
				swingProgress = 0;
		} else {
			swingMod = 0;
			swingProgress = 0;
		}

		if(!redstone && detect() && swingProgress == 0) {
			initiateSwing();
			worldObj.addBlockEvent(xCoord, yCoord, zCoord, ModBlocks.animationTablet.blockID, 0, 0);
		}
	}

	public void initiateSwing() {
		swingMod = SWING_SPEED;
		swingProgress = 1;
	}

	public void swingHit() {
		ChunkCoordinates coords = getTargetLoc();
		// XXX Still a test!
		worldObj.setBlockToAir(coords.posX, coords.posY, coords.posZ);
	}

	public boolean detect() {
		ChunkCoordinates coords = getTargetLoc();
		if(leftClick)
			return !worldObj.isAirBlock(coords.posX, coords.posY, coords.posZ);

		findEntities(coords);
		return !detectedEntities.isEmpty();
	}

	private void findEntities(ChunkCoordinates coords) {
		AxisAlignedBB boundingBox = AxisAlignedBB.getBoundingBox(coords.posX, coords.posY, coords.posZ, coords.posX + 1, coords.posY + 1, coords.posZ + 1);
		detectedEntities = worldObj.getEntitiesWithinAABB(Entity.class, boundingBox);
	}

	public ChunkCoordinates getTargetLoc() {
		ChunkCoordinates coords = new ChunkCoordinates(xCoord, yCoord, zCoord);

		int meta = getBlockMetadata();
		int[] increase = LOC_INCREASES[(meta & 7) - 2];
		coords.posX += increase[0];
		coords.posZ += increase[1];

		return coords;
	}

	@Override
	public boolean receiveClientEvent(int par1, int par2) {
		if(par1 == 0) {
			initiateSwing();
			return true;
		}

		return tileEntityInvalid;
	}

	@Override
	public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
		super.readFromNBT(par1NBTTagCompound);

		leftClick = par1NBTTagCompound.getBoolean(TAG_LEFT_CLICK);
		redstone = par1NBTTagCompound.getBoolean(TAG_REDSTONE);
		swingProgress = par1NBTTagCompound.getInteger(TAG_PROGRESS);
		swingMod = par1NBTTagCompound.getInteger(TAG_MOD);

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

        par1NBTTagCompound.setBoolean(TAG_LEFT_CLICK, leftClick);
        par1NBTTagCompound.setBoolean(TAG_REDSTONE, redstone);
        par1NBTTagCompound.setInteger(TAG_PROGRESS, swingProgress);
        par1NBTTagCompound.setInteger(TAG_MOD, swingMod);

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
		return LibBlockNames.TRANSMUTATOR_D;
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
	public boolean isStackValidForSlot(int i, ItemStack itemstack) {
		return true;
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
		return PacketManager.generatePacket(new PacketAnimationTabletSync(this));
	}

}
