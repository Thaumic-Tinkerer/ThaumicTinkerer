/**
 * Author: thegreatunclean
 * License: Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 */

package thaumic.tinkerer.common.block.tile;

import appeng.api.movable.IMovableTile;
import net.minecraft.block.BlockHopper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.util.Facing;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.ForgeDirection;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;
import thaumcraft.api.aspects.IEssentiaContainerItem;
import thaumcraft.api.aspects.IEssentiaTransport;
import thaumcraft.api.visnet.VisNetHandler;
import thaumcraft.common.blocks.ItemJarFilled;
import thaumcraft.common.tiles.TileJarFillable;
import thaumcraft.common.tiles.TileJarFillableVoid;
import thaumic.tinkerer.common.lib.LibBlockNames;
import thaumic.tinkerer.common.ThaumicTinkerer;
import thaumic.tinkerer.common.item.kami.ItemKamiResource;

import java.util.ArrayList;
import java.util.Collection;

public class TileSynth extends TileEntity implements ISidedInventory, IAspectContainer {
	
	private ItemStack[] inventory = new ItemStack[2];
    
	private int ticks = 0;
	
    @Override
    public void updateEntity() {
	
		if (!(++ticks % 20 == 0)) { return; } ticks = 0;//update every 20 ticks
					
        ItemStack jarL = inventory[1];    //Left-hand jar
        ItemStack jarR = inventory[0];    //Right-hand jar
        
        if (!((jarL != null) && (jarL.getItem() instanceof ItemJarFilled)) ||
           (!((jarR != null) && (jarR.getItem() instanceof ItemJarFilled)))) {
           return; //Missing a jar or jars aren't actually jars.
        }
        
        if (worldObj.isRemote) { return; }
        
        ItemJarFilled itemL = (ItemJarFilled) jarL.getItem();
        ItemJarFilled itemR = (ItemJarFilled) jarR.getItem();
        
        AspectList aspectListL = itemL.getAspects(jarL);
        AspectList aspectListR = itemR.getAspects(jarR);
        
		if (! (aspectListL != null && aspectListL.size()==1 && aspectListR != null && aspectListR.size()==1)) {
			return; //One of them is null or (somehow) contains more/less than one aspect
        }
        
        Aspect aspectL = aspectListL.getAspects()[0];
        Aspect aspectR = aspectListR.getAspects()[0];
        
        Aspect result = combineAspects(aspectL, aspectR);
        if (result == null) { return; } //Invalid combo, bail
        
        TileEntity tile = worldObj.getTileEntity(xCoord, yCoord - 1, zCoord);//GET TO THE (C)HOPPA
		if (!(tile != null && tile instanceof TileEntityHopper)) { return;} //not a hopper
		TileEntityHopper tile_hopper = (TileEntityHopper) tile; 
		
		tile = getHopperFacing(tile_hopper.xCoord, tile_hopper.yCoord, tile_hopper.zCoord, tile_hopper.getBlockMetadata()); //jar
		
		if (!(tile != null && tile instanceof TileJarFillable)) { return; } //not a jar
		boolean voidJar = (tile instanceof TileJarFillableVoid); //is a void jar
		
		TileJarFillable bottomjar = (TileJarFillable) tile;
        //bottomjar either jar or void jar, try not to make assumptions about that
        
        if (!(bottomjar.doesContainerAccept( result ))) { return; } //bottomjar can't take this aspect.  Bail.
		
		
		int amount_to_add = 1;
		
		for (int i=0; i < ((TileEntityHopper)tile_hopper).getSizeInventory(); i++) {
			ItemStack itemstack = tile_hopper.getStackInSlot(i);
			if (itemstack != null && itemstack.getItem() instanceof ItemKamiResource && itemstack.getItemDamage() == 0) {
				//damage = 0 is ichor, damage = 0 is ender shard
				amount_to_add = 2; //double
			}
		}
		
		if (bottomjar.amount <= (64 - amount_to_add) || voidJar) {
			bottomjar.addToContainer(result, amount_to_add);
			itemL.setAspects(jarL, aspectListL.remove(aspectL,1));
			itemR.setAspects(jarR, aspectListR.remove(aspectR,1));
		}
		
		return;
        
    }
	
	//ripped from the funnel.
	private TileEntity getHopperFacing(int x, int y, int z, int meta) {
		int i = BlockHopper.getDirectionFromMetadata(meta);
		return worldObj.getTileEntity(x + Facing.offsetsXForSide[i], y + Facing.offsetsYForSide[i], z + Facing.offsetsZForSide[i]);
	}
    
    private Aspect combineAspects(Aspect lhs, Aspect rhs) {
        //Thaumcraft API doesn't provide a mechanism to easily match a pair
        //of aspects to the result, will probably have to do it myself.
        //Build reverse map on init or something.
		
		//This is an abomination and will be replaced soon.
		
		//Brute force solution for testing.
		ArrayList<Aspect> allCompoundAspects = Aspect.getCompoundAspects();
		for( Aspect aspect : allCompoundAspects) {
			
			Aspect[] components = aspect.getComponents();
			if ((components[0] == lhs && components[1] == rhs) ||
			   (components[1] == lhs && components[0] == rhs)) {
			   return aspect;
			}
		}
		
        return null;
    }
	
	@Override
	public void markDirty() {
		super.markDirty();
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
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
		NBTTagList var2 = par1NBTTagCompound.getTagList("Items", Constants.NBT.TAG_COMPOUND);
		inventory = new ItemStack[getSizeInventory()];
		for (int var3 = 0; var3 < var2.tagCount(); ++var3) {
			NBTTagCompound var4 = var2.getCompoundTagAt(var3);
			byte var5 = var4.getByte("Slot");
			if (var5 >= 0 && var5 < inventory.length)
				inventory[var5] = ItemStack.loadItemStackFromNBT(var4);
		}
	}

	public void writeCustomNBT(NBTTagCompound par1NBTTagCompound) {
		NBTTagList var2 = new NBTTagList();
		for (int var3 = 0; var3 < inventory.length; ++var3) {
			if (inventory[var3] != null) {
				NBTTagCompound var4 = new NBTTagCompound();
				var4.setByte("Slot", (byte) var3);
				inventory[var3].writeToNBT(var4);
				var2.appendTag(var4);
			}
		}
		par1NBTTagCompound.setTag("Items", var2);
	}

	@Override
	public int getSizeInventory() { return inventory.length; }
    @Override
    public ItemStack getStackInSlot(int i) { return inventory[i]; }
    @Override
    public ItemStack getStackInSlotOnClosing(int i){ return getStackInSlot(i); }
    @Override
    public void setInventorySlotContents(int i, ItemStack itemstack) { inventory[i] = itemstack; }
    
    @Override
    public ItemStack decrStackSize(int position, int amount) {
        if (inventory[position] == null) { return null; }
        
        ItemStack slot;
        
        if (inventory[position].stackSize <= amount) {//Less then limit, send everything
            slot = inventory[position];
            inventory[position] = null;
            return slot;
        } else { //Over limit, send some
            slot = inventory[position].splitStack(amount);
            if (inventory[position].stackSize == 0) {
                inventory[position] = null;
            }
            return slot;
        }
    }
    
    @Override
    public String getInventoryName() {
        return LibBlockNames.SYNTH;
    }
    
    @Override
    public boolean hasCustomInventoryName() { return false; }
    @Override
    public int getInventoryStackLimit() { return inventory.length; }
    
    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return ((worldObj.getTileEntity(xCoord, yCoord, zCoord) == this) &&
                (player.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) <= 64));
    }

    	@Override
	public void openInventory() {
        return;
	}

	@Override
	public void closeInventory() {
        return;
	}
    
	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return (itemstack != null && itemstack.getItem() instanceof ItemJarFilled);
	}
	@Override
	public boolean canInsertItem(int i, ItemStack itemstack, int j) {
		return (itemstack != null && inventory[i] == null && itemstack.getItem() instanceof ItemJarFilled);
	}
    
	@Override
	public S35PacketUpdateTileEntity getDescriptionPacket() {
		NBTTagCompound nbttagcompound = new NBTTagCompound();
		writeCustomNBT(nbttagcompound);
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, -999, nbttagcompound);
	}

	@Override
	public void onDataPacket(NetworkManager manager, S35PacketUpdateTileEntity packet) {
		super.onDataPacket(manager, packet);
		readCustomNBT(packet.func_148857_g());
	}
    
    @Override
    public AspectList getAspects() {
		//Cleanup if at all possible.  too many nested ifs.
		AspectList total = new AspectList();	
		
		for (ItemStack item : inventory) {
			if (item != null) {
				if (item.getItem() instanceof ItemJarFilled) {
					AspectList temp = ((ItemJarFilled)item.getItem()).getAspects(item);
					if (temp != null) {
						for (Aspect as : temp.getAspects()) {
							if (as != null && temp.getAmount(as) > 0) {
								total.add(as, temp.getAmount(as));
							}
						}
					}
				}
			}
		}
		
		
		return (total.size() > 0 ? total : null);
    }
		
	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return false;
	}
	@Override
	public int[] getAccessibleSlotsFromSide(int var1) {
		return new int[]{ 0 };
	}
	
	ForgeDirection getOrientation() {
		return ForgeDirection.getOrientation(getBlockMetadata());
	}
	
	@Override //IAspectContainer
	public int addToContainer(Aspect as, int amount) {
		return 0;
	}
	@Override //IAspectContainer
	public boolean doesContainerAccept(Aspect as) {
		return false;
	}
	@Override //IAspectContainer
	public void setAspects(AspectList list) {
		return;
	}
	@Override //IAspectContainer
	public boolean takeFromContainer(Aspect as, int amount) {
		return false;
	}
	@Override //IAspectContainer
	public boolean takeFromContainer(AspectList list) {
		return false;
	}
	@Override //IAspectContainer
	public boolean doesContainerContain(AspectList list) {
		return false;
	}
	@Override //IAspectContainer
	public boolean doesContainerContainAmount(Aspect as, int amount) {
		return false;
	}
	@Override //IAspectContainer
	public int containerContains(Aspect as) {
		return 0;
	}
}