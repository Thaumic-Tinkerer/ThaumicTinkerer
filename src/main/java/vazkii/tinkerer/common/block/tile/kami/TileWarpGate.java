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
 * File Created @ [Jan 10, 2014, 3:56:13 PM (GMT)]
 */
package vazkii.tinkerer.common.block.tile.kami;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChunkCoordinates;
import net.minecraftforge.common.util.Constants;
import vazkii.tinkerer.common.ThaumicTinkerer;
import vazkii.tinkerer.common.item.kami.ItemSkyPearl;
import vazkii.tinkerer.common.lib.LibBlockNames;
import vazkii.tinkerer.common.lib.LibGuiIDs;

import java.util.List;

public class TileWarpGate extends TileEntity implements IInventory {

	private static final String TAG_LOCKED = "locked";

	public boolean locked = false;
	boolean teleportedThisTick = false;
	ItemStack[] inventorySlots = new ItemStack[10];

	@Override
	public void updateEntity() {
		List<EntityPlayer> players = worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(xCoord, yCoord + 1, zCoord, xCoord + 1, yCoord + 1.5, zCoord + 1));

		EntityPlayer clientPlayer = ThaumicTinkerer.proxy.getClientPlayer();
		for (EntityPlayer player : players)
			if (player != null && player == clientPlayer && player.isSneaking()) {
				player.openGui(ThaumicTinkerer.instance, LibGuiIDs.GUI_ID_WARP_GATE_DESTINATIONS, worldObj, xCoord, yCoord, zCoord);
				break;
			}

		teleportedThisTick = false;
	}

	public void teleportPlayer(EntityPlayer player, int index) {
		if (teleportedThisTick)
			return;

		ItemStack stack = index < getSizeInventory() ? getStackInSlot(index) : null;
		if (stack != null && ItemSkyPearl.isAttuned(stack)) {
			int x = ItemSkyPearl.getX(stack);
			int y = ItemSkyPearl.getY(stack);
			int z = ItemSkyPearl.getZ(stack);

			if (teleportPlayer(player, new ChunkCoordinates(x, y, z)))
				teleportedThisTick = true;
		}
	}

	public static boolean teleportPlayer(EntityPlayer player, ChunkCoordinates coords) {
		int x = coords.posX;
		int y = coords.posY;
		int z = coords.posZ;

		TileEntity tile = player.worldObj.getTileEntity(x, y, z);
		if (tile != null && tile instanceof TileWarpGate) {
			TileWarpGate destGate = (TileWarpGate) tile;
			if (!destGate.locked) {
				player.worldObj.playSoundAtEntity(player, "thaumcraft:wand", 1F, 1F);

				for (int i = 0; i < 20; i++)
					ThaumicTinkerer.tcProxy.sparkle((float) player.posX + player.worldObj.rand.nextFloat() - 0.5F, (float) player.posY + player.worldObj.rand.nextFloat(), (float) player.posZ + player.worldObj.rand.nextFloat() - 0.5F, 6);

				player.mountEntity(null);
				if (player instanceof EntityPlayerMP)
					((EntityPlayerMP) player).playerNetServerHandler.setPlayerLocation(x + 0.5, y + 1.6, z + 0.5, player.rotationYaw, player.rotationPitch);

				for (int i = 0; i < 20; i++)
					ThaumicTinkerer.tcProxy.sparkle((float) player.posX + player.worldObj.rand.nextFloat() - 0.5F, (float) player.posY + player.worldObj.rand.nextFloat(), (float) player.posZ + player.worldObj.rand.nextFloat() - 0.5F, 6);

				player.worldObj.playSoundAtEntity(player, "thaumcraft:wand", 1F, 0.1F);
				return true;
			} else if (!player.worldObj.isRemote)
				player.addChatMessage(new ChatComponentTranslation("ttmisc.noTeleport"));
		} else if (!player.worldObj.isRemote)
			player.addChatMessage(new ChatComponentTranslation("ttmisc.noDest"));

		return false;
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
		locked = par1NBTTagCompound.getBoolean(TAG_LOCKED);

		NBTTagList var2 = par1NBTTagCompound.getTagList("Items", Constants.NBT.TAG_COMPOUND);
		inventorySlots = new ItemStack[getSizeInventory()];
		for (int var3 = 0; var3 < var2.tagCount(); ++var3) {
			NBTTagCompound var4 = var2.getCompoundTagAt(var3);
			byte var5 = var4.getByte("Slot");
			if (var5 >= 0 && var5 < inventorySlots.length)
				inventorySlots[var5] = ItemStack.loadItemStackFromNBT(var4);
		}
	}

	public void writeCustomNBT(NBTTagCompound par1NBTTagCompound) {
		par1NBTTagCompound.setBoolean(TAG_LOCKED, locked);

		NBTTagList var2 = new NBTTagList();
		for (int var3 = 0; var3 < inventorySlots.length; ++var3) {
			if (inventorySlots[var3] != null) {
				NBTTagCompound var4 = new NBTTagCompound();
				var4.setByte("Slot", (byte) var3);
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
	public String getInventoryName() {
		return LibBlockNames.WARP_GATE;
	}

	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		return worldObj.getTileEntity(xCoord, yCoord, zCoord) == this && entityplayer.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) <= 64;
	}

	@Override
	public void openInventory() {

	}

	@Override
	public void closeInventory() {

	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return itemstack.getItem() == ThaumicTinkerer.TTRegistry.getFirstItemFromClass(ItemSkyPearl.class);
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

}
