/**
x * This class was created by <Vazkii>. It's distributed as
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
 * File Created @ [Nov 30, 2013, 5:39:09 PM (GMT)]
 */
package vazkii.tinkerer.common.block.tile;

import appeng.api.movable.IMovableTile;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.network.PacketDispatcher;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;
import thaumcraft.api.aspects.IEssentiaTransport;
import vazkii.tinkerer.common.ThaumicTinkerer;
import vazkii.tinkerer.common.compat.TinkersConstructCompat;
import vazkii.tinkerer.common.lib.LibBlockNames;

import java.util.HashMap;
import java.util.Map;

public class TileRepairer extends TileEntity implements ISidedInventory, IAspectContainer, IEssentiaTransport, IMovableTile {

	int dmgLastTick = 0;
	public int ticksExisted = 0;
	public boolean tookLastTick = true;
	private static final Map<Aspect, Integer> repairValues = new HashMap();

	static {
		repairValues.put(Aspect.TOOL, 8);
		repairValues.put(Aspect.CRAFT, 5);
		repairValues.put(Aspect.ORDER, 3);
	}

	ItemStack[] inventorySlots = new ItemStack[1];

	@Override
	public void updateEntity() {
		if(++ticksExisted % 10 == 0) {
			if(Loader.isModLoaded("TConstruct"))
			{
				if(inventorySlots[0] != null)
				{
					if(TinkersConstructCompat.isTConstructTool(inventorySlots[0]))
					{
						int dmg=TinkersConstructCompat.getDamage(inventorySlots[0]);
						if( dmg > 0) {
							int essentia = drawEssentia();
							TinkersConstructCompat.fixDamage(inventorySlots[0], Math.max(0, dmg - essentia));
							onInventoryChanged();
							if(dmgLastTick != 0 && dmgLastTick != dmg) {
								ThaumicTinkerer.tcProxy.sparkle((float) (xCoord + 0.25 + Math.random() / 2F), (float) (yCoord + 1 + Math.random() / 2F), (float) (zCoord + 0.25 + Math.random() / 2F), 0);
								tookLastTick = true;
							} else tookLastTick = false;
						} else tookLastTick=false;
						return ;
					}
				}
			}
			if(inventorySlots[0] != null && inventorySlots[0].getItemDamage() > 0) {
				int essentia = drawEssentia();
				int dmg = inventorySlots[0].getItemDamage();
				inventorySlots[0].setItemDamage(Math.max(0, dmg - essentia));
				onInventoryChanged();

				if(dmgLastTick != 0 && dmgLastTick != dmg) {
					ThaumicTinkerer.tcProxy.sparkle((float) (xCoord + 0.25 + Math.random() / 2F), (float) (yCoord + 1 + Math.random() / 2F), (float) (zCoord + 0.25 + Math.random() / 2F), 0);
					tookLastTick = true;
				} else tookLastTick = false;
			} else tookLastTick = false;

			dmgLastTick = inventorySlots[0] == null ? 0 : inventorySlots[0].getItemDamage();
		}
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
	public void onInventoryChanged() {
		PacketDispatcher.sendPacketToAllInDimension(getDescriptionPacket(), worldObj.provider.dimensionId);
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int var1) {
		return new int[] { 0 };
	}

	@Override
	public boolean canInsertItem(int i, ItemStack itemstack, int j) {
		if(Loader.isModLoaded("TConstruct"))
		{
			if(TinkersConstructCompat.isTConstructTool(itemstack))
			{
				return itemstack!=null;
			}
		}
		return itemstack != null && itemstack.getItem().isRepairable();
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return true;
	}

	int drawEssentia() {
		ForgeDirection orientation = getOrientation();
		TileEntity tile = ThaumcraftApiHelper.getConnectableTile(worldObj, xCoord, yCoord, zCoord, orientation);

		if (tile != null) {
			IEssentiaTransport ic = (IEssentiaTransport) tile;

			if (!ic.canOutputTo(orientation.getOpposite()))
				return 0;

			for(Aspect aspect : repairValues.keySet())
				if (ic.getSuction(orientation.getOpposite()).getAmount(aspect) < getSuction(orientation).getAmount(aspect) && ic.takeVis(aspect, 1) == 1)
					return repairValues.get(aspect);

			return 0;
		}

		return 0;
	}

	ForgeDirection getOrientation() {
		return ForgeDirection.getOrientation(getBlockMetadata());
	}

	@Override
	public AspectList getAspects() {
		ItemStack stack = inventorySlots[0];
		if(stack == null)
			return null;
		else return new AspectList().add(Aspect.ENTROPY, stack.getItemDamage());
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

	@Override
	public boolean isConnectable(ForgeDirection paramForgeDirection) {
		return paramForgeDirection == getOrientation();
	}

	@Override
	public boolean canInputFrom(ForgeDirection paramForgeDirection) {
		return false;
	}

	@Override
	public boolean canOutputTo(ForgeDirection paramForgeDirection) {
		return isConnectable(paramForgeDirection);
	}

	@Override
	public void setSuction(AspectList paramAspectList) {
	}

	@Override
	public void setSuction(Aspect paramAspect, int paramInt) { }

	@Override
	public AspectList getSuction(ForgeDirection paramForgeDirection) {
		AspectList list = new AspectList();
		for(Aspect aspect : repairValues.keySet())
			list.add(aspect, 256 + repairValues.get(aspect));

		return list;
	}

	@Override
	public int takeVis(Aspect paramAspect, int paramInt) {
		return 0;
	}

	@Override
	public AspectList getEssentia(ForgeDirection paramForgeDirection) {
		return null;
	}

	@Override
	public int getMinimumSuction() {
		return 0;
	}

	@Override
	public boolean renderExtendedTube() {
		return false;
	}

	@Override
	public boolean prepareToMove() {
		return true;
	}

	@Override
	public void doneMoving() {

	}

}
