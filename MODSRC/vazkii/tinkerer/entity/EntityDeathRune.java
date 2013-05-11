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
 * File Created @ [11 May 2013, 22:43:15 (GMT)]
 */
package vazkii.tinkerer.entity;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import vazkii.tinkerer.ThaumicTinkerer;
import vazkii.tinkerer.lib.LibItemNames;

public class EntityDeathRune extends Entity implements IInventory {

	private static final String TAG_SLOTS_SIZE = "slots";
	private static final String TAG_PLAYER_NAME = "playerName";
	
	ItemStack[] inventorySlots;
	String username;
	
	public EntityDeathRune(World par1World) {
		super(par1World);
	}
	
	public EntityDeathRune(EntityPlayer player) {
		super(player.worldObj);
		int size = player.inventory.getSizeInventory();
		inventorySlots = new ItemStack[size + (size % 9)];
		
		for(int i = 0; i < player.inventory.getSizeInventory(); i++) {
			ItemStack stackAt = player.inventory.getStackInSlot(i);
			if(stackAt != null) {
				inventorySlots[i] = stackAt.copy();
				player.inventory.setInventorySlotContents(i, null);
			}
		}
	}
	
	@Override
	protected void entityInit() {
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		List<EntityPlayer> nearbyPlayers = worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(posX - 8, posY - 4, posZ - 8, posX + 8, posY + 4, posZ + 8));
		for(EntityPlayer player : nearbyPlayers) {
			if(player.username.equals(username) && player.isEntityAlive())
				restockPlayer(player);
		}
		
		for(int i = 0; i < 2; i++) {
			double x = posX + width / 2 + (Math.random() - 0.5) * 4;
			double y = posY + width / 2 + (Math.random() - 0.5) * 4;
			double z = posZ - height / 2 + (Math.random() - 0.5) * 4;
			Vec3 vector = worldObj.getWorldVec3Pool().getVecFromPool(x, y, z);

			ThaumicTinkerer.proxy.sigilLightning(worldObj, this, vector);
		}
	}
	
	private void restockPlayer(EntityPlayer player) {
		boolean hasSomething = false;
		
		for(int i = 0; i < Math.min(inventorySlots.length, player.inventory.getSizeInventory()); i++) {
			ItemStack playerStack = player.inventory.getStackInSlot(i);
			ItemStack restockStack = getStackInSlot(i).copy();
			if(restockStack != null) {
				hasSomething = true;
				boolean remove = false;
				if(playerStack == null) {
					player.inventory.setInventorySlotContents(i, restockStack);
					remove = true;
				} else if(player.inventory.addItemStackToInventory(restockStack))
					remove = true;
				
				if(remove)
					setInventorySlotContents(i, null);
			}
		}
		
		if(!hasSomething)
			setDead();
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
		inventorySlots = new ItemStack[par1NBTTagCompound.getInteger(TAG_SLOTS_SIZE)];
		username = par1NBTTagCompound.getString(TAG_PLAYER_NAME);
		
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
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
        par1NBTTagCompound.setInteger(TAG_SLOTS_SIZE, inventorySlots.length);
        par1NBTTagCompound.setString(TAG_PLAYER_NAME, username);
        
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
		return LibItemNames.DEATH_RUNE_D;
	}

	@Override
	public boolean isInvNameLocalized() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 0;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		return entityplayer.getDistanceSq(posX + 0.5D, posY + 0.5D, posZ + 0.5D) <= 64;
	}

	@Override
	public void openChest() {
	}

	@Override
	public void closeChest() {
	}

	@Override
	public void onInventoryChanged() {
	}

	@Override
	public boolean isStackValidForSlot(int i, ItemStack itemstack) {
		return false;
	}
}
