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
 * File Created @ [14 Sep 2013, 01:07:25 (GMT)]
 */
package vazkii.tinkerer.common.block.tile;

import appeng.api.movable.IMovableTile;
import cpw.mods.fml.common.network.PacketDispatcher;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.items.wands.ItemWandCasting;
import vazkii.tinkerer.common.core.helper.Tuple4Int;
import vazkii.tinkerer.common.enchantment.core.EnchantmentManager;
import vazkii.tinkerer.common.lib.LibBlockNames;
import vazkii.tinkerer.common.lib.LibFeatures;

import java.util.ArrayList;
import java.util.List;
public class TileEnchanter extends TileEntity implements ISidedInventory, IMovableTile {

	private static final String TAG_ENCHANTS = "enchants";
	private static final String TAG_LEVELS = "levels";
	private static final String TAG_TOTAL_ASPECTS = "totalAspects";
	private static final String TAG_CURRENT_ASPECTS = "currentAspects";
	private static final String TAG_WORKING = "working";

	public List<Integer> enchantments = new ArrayList();
	public List<Integer> levels = new ArrayList();

	public AspectList totalAspects = new AspectList();
	public AspectList currentAspects = new AspectList();

	public boolean working = false;

	private List<Tuple4Int> pillars = new ArrayList();

	ItemStack[] inventorySlots = new ItemStack[2];

	public void clearEnchants() {
		enchantments.clear();
		levels.clear();
	}

	public void appendEnchant(int enchant) {
		enchantments.add(enchant);
	}

	public void appendLevel(int level) {
		levels.add(level);
	}

	public void removeEnchant(int index) {
		enchantments.remove(index);
	}

	public void removeLevel(int index) {
		levels.remove(index);
	}

	public void setEnchant(int index, int enchant) {
		enchantments.set(index, enchant);
	}

	public void setLevel(int index, int level) {
		levels.set(index, level);
	}

	@Override
	public void updateEntity() {
		if(getStackInSlot(0) == null) {
			enchantments.clear();
			levels.clear();
		}

		if(working) {
			ItemStack tool = getStackInSlot(0);
			if(tool == null) {
				working = false;
				return;
			}

			checkPillars();

			if(!working) // Pillar check
				return;

			enchantItem : {
				for(Aspect aspect : LibFeatures.PRIMAL_ASPECTS) {
					int currentAmount = currentAspects.getAmount(aspect);
					int totalAmount = totalAspects.getAmount(aspect);

					if(currentAmount < totalAmount)
						break enchantItem;
	 			}

				working = false;
				currentAspects = new AspectList();
				totalAspects = new AspectList();

				for(int i = 0; i < enchantments.size(); i++) {
					int enchant = enchantments.get(i);
					int level = levels.get(i);

					if(!worldObj.isRemote)
						tool.addEnchantment(Enchantment.enchantmentsList[enchant], level);
				}

				enchantments.clear();
				levels.clear();
				worldObj.playSoundEffect(xCoord, yCoord, zCoord, "thaumcraft:wand", 1F, 1F);
				PacketDispatcher.sendPacketToAllPlayers(getDescriptionPacket());
				return;
			}

			ItemStack wand = getStackInSlot(1);

			if(wand != null && wand.getItem() instanceof ItemWandCasting && !((ItemWandCasting) wand.getItem()).isStaff(wand)) {
				ItemWandCasting wandItem = (ItemWandCasting) wand.getItem();
				AspectList wandAspects = wandItem.getAllVis(wand);

				int missing, onWand;
				List<Aspect> aspectsThatCanGet = new ArrayList();

				for(Aspect aspect : LibFeatures.PRIMAL_ASPECTS) {
					missing = totalAspects.getAmount(aspect) - currentAspects.getAmount(aspect);
					onWand = wandAspects.getAmount(aspect);

					if(missing > 0 && onWand >= 100)
						aspectsThatCanGet.add(aspect);
				}

				int i = aspectsThatCanGet.isEmpty() ? 0 : worldObj.rand.nextInt(aspectsThatCanGet.size());
				Aspect aspect = aspectsThatCanGet.isEmpty() ? null : aspectsThatCanGet.get(i);

				if(aspect != null) {
					wandItem.consumeAllVisCrafting(wand, null, new AspectList().add(aspect, 1), true);
					currentAspects.add(aspect, 1);
					Tuple4Int p = pillars.get(i);
					if(worldObj.rand.nextBoolean()) {
						Thaumcraft.proxy.blockRunes(worldObj, p.i1, p.i4 - 0.75, p.i3, 0.3F + worldObj.rand.nextFloat() * 0.7F, 0.0F, 0.3F + worldObj.rand.nextFloat() * 0.7F, 15, worldObj.rand.nextFloat() / 8F);
						Thaumcraft.proxy.blockRunes(worldObj, xCoord, yCoord + 0.25, zCoord, 0.3F + worldObj.rand.nextFloat() * 0.7F, 0.0F, 0.3F + worldObj.rand.nextFloat() * 0.7F, 15, worldObj.rand.nextFloat() / 8F);
						if(worldObj.rand.nextInt(5) == 0)
							worldObj.playSoundEffect(p.i1, p.i2, p.i3, "thaumcraft:brain", 0.5F, 1F);
					}

					return;
				}
			}
		}
	}

	@Override
	public void onInventoryChanged() {
		super.onInventoryChanged();
		if(!worldObj.isRemote && !working) {
			enchantments.clear();
			levels.clear();
			PacketDispatcher.sendPacketToAllInDimension(getDescriptionPacket(), worldObj.provider.dimensionId);
		}
	}

	public boolean checkPillars() {
		if(pillars.isEmpty()) {
			if(!assignPillars()) {
				working = false;
				currentAspects = new AspectList();
				return false;
			}
			return true;
		}

		for(int i = 0; i < pillars.size(); i++) {
			Tuple4Int pillar = pillars.get(i);
			int pillarHeight = findPillar(pillar.i1, pillar.i2, pillar.i3);
			if(pillarHeight == -1) {
				pillars.clear();
				return checkPillars();
			} else if(pillarHeight != pillar.i4)
				pillar.i4 = pillarHeight;
		}

		return true;
	}

	public boolean assignPillars() {
		int y = yCoord;
		for(int x = xCoord - 4; x <= xCoord + 4; x++)
			for(int z = zCoord - 4; z <= zCoord + 4; z++) {
				int height = findPillar(x, y, z);
				if(height != -1)
					pillars.add(new Tuple4Int(x, y, z, height));

				if(pillars.size() == 6)
					return true;
			}

		pillars.clear();
		return false;
	}

	public int findPillar(int x, int y, int z) {
		int obsidianFound = 0;
		for(int i = 0; true; i++) {
			if(y + i >= 256)
				return -1;

			int id = worldObj.getBlockId(x, y + i, z);
			int meta = worldObj.getBlockMetadata(x, y + i, z);
			if(id == ConfigBlocks.blockCosmeticSolid.blockID && meta == 0) {
				++obsidianFound;
				continue;
			}
			if(id == ConfigBlocks.blockAiry.blockID && meta == 1) {
				if(obsidianFound >= 2 && obsidianFound < 13)
					return y + i;
				return -1;
			}

			return -1;
		}
	}

	public void updateAspectList() {
		totalAspects = new AspectList();
		for(int i = 0; i < enchantments.size(); i++) {
			int enchant = enchantments.get(i);
			int level = levels.get(i);

			AspectList aspects = EnchantmentManager.enchantmentData.get(enchant).get(level).aspects;
			for(Aspect aspect : aspects.getAspectsSorted())
				totalAspects.add(aspect, aspects.getAmount(aspect));
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
		working = par1NBTTagCompound.getBoolean(TAG_WORKING);
		currentAspects.readFromNBT(par1NBTTagCompound.getCompoundTag(TAG_CURRENT_ASPECTS));
		totalAspects.readFromNBT(par1NBTTagCompound.getCompoundTag(TAG_TOTAL_ASPECTS));

		NBTTagList enchants = par1NBTTagCompound.getTagList(TAG_ENCHANTS);
		enchantments.clear();
		for(int i = 0; i < enchants.tagCount(); i++)
			enchantments.add(((NBTTagInt) enchants.tagAt(i)).data);

		NBTTagList levels = par1NBTTagCompound.getTagList(TAG_LEVELS);
		this.levels.clear();
		for(int i = 0; i < levels.tagCount(); i++)
			this.levels.add(((NBTTagInt) levels.tagAt(i)).data);

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
    	NBTTagList enchants = new NBTTagList();
    	for(int enchant : enchantments)
    		enchants.appendTag(new NBTTagInt("", enchant));
    	NBTTagList levels = new NBTTagList();
    	for(int level : this.levels)
    		levels.appendTag(new NBTTagInt("", level));

    	NBTTagCompound totalAspectsCmp = new NBTTagCompound();
    	totalAspects.writeToNBT(totalAspectsCmp);

    	NBTTagCompound currentAspectsCmp = new NBTTagCompound();
    	currentAspects.writeToNBT(currentAspectsCmp);

    	par1NBTTagCompound.setBoolean(TAG_WORKING, working);
    	par1NBTTagCompound.setCompoundTag(TAG_TOTAL_ASPECTS, totalAspectsCmp);
    	par1NBTTagCompound.setCompoundTag(TAG_CURRENT_ASPECTS, currentAspectsCmp);
    	par1NBTTagCompound.setTag(TAG_ENCHANTS, enchants);
    	par1NBTTagCompound.setTag(TAG_LEVELS, levels);

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
		return LibBlockNames.ENCHANTER;
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
		return false;
	}

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        super.onDataPacket(net, pkt);
        readCustomNBT(pkt.func_148857_g());
    }

    @Override
	public Packet getDescriptionPacket() {
		NBTTagCompound nbttagcompound = new NBTTagCompound();
		writeCustomNBT(nbttagcompound);
        return new S35PacketUpdateTileEntity(xCoord,yCoord,zCoord,-999,nbttagcompound);
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
