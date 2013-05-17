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

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet3Chat;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.FakePlayer;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.event.Event;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;
import vazkii.tinkerer.block.ModBlocks;
import vazkii.tinkerer.lib.LibBlockNames;
import vazkii.tinkerer.network.PacketManager;
import vazkii.tinkerer.network.packet.PacketAnimationTabletSync;
import cpw.mods.fml.common.network.PacketDispatcher;

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

	private static final ForgeDirection[] SIDES = new ForgeDirection[] {
		ForgeDirection.NORTH,
		ForgeDirection.SOUTH,
		ForgeDirection.WEST,
		ForgeDirection.EAST
	};

	private static final int SWING_SPEED = 3;
	private static final int MAX_DEGREE = 45;

	List<Entity> detectedEntities = new ArrayList();

	ItemStack[] inventorySlots = new ItemStack[1];
	public double ticksExisted = 0;

	public boolean leftClick = true;
	public boolean redstone = false;

	public int swingProgress = 0;
	private int swingMod = 0;

	private boolean isBreaking = false;
	private int initialDamage = 0;
	private int curblockDamage = 0;
	private int durabilityRemainingOnBlock;

	FakePlayer player;

	@Override
	public void updateEntity() {
		if(player == null)
			player = new TabletFakePlayer(this);

		player.onUpdate();

		ticksExisted++;

		ItemStack stack = getStackInSlot(0);

		if(stack != null) {
			if(swingProgress >= MAX_DEGREE)
				swingHit();

			swingMod = swingProgress <= 0 ? 0 : swingProgress >= MAX_DEGREE ? -SWING_SPEED : swingMod;
			swingProgress += swingMod;
			if(swingProgress < 0)
				swingProgress = 0;
		} else {
			swingMod = 0;
			swingProgress = 0;

			if(isBreaking)
				stopBreaking();
		}

		boolean detect = detect();
		if(!detect)
			stopBreaking();

		if(detect && isBreaking)
			continueBreaking();

		if((!redstone || isBreaking) && detect && swingProgress == 0) {
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
		ItemStack stack = getStackInSlot(0);
		Item item = stack.getItem();
		int id = worldObj.getBlockId(coords.posX, coords.posY, coords.posZ);

		player.setCurrentItemOrArmor(0, stack);

		boolean done = false;

		if(leftClick) {
			Entity entity = detectedEntities.isEmpty() ? null : detectedEntities.get(worldObj.rand.nextInt(detectedEntities.size()));
			if(entity != null) {
				stack.getDamageVsEntity(entity);
				player.attackTargetEntityWithCurrentItem(entity);
				done = true;
			} else if(!isBreaking){
				if(id != 0 && !Block.blocksList[id].isAirBlock(worldObj, coords.posX, coords.posY, coords.posZ)) {
					isBreaking = true;
					startBreaking(Block.blocksList[id], worldObj.getBlockMetadata(coords.posX, coords.posY, coords.posZ));
					done = true;
				}
			}
		} else {
			int side = SIDES[(getBlockMetadata() & 7) - 2].getOpposite().ordinal();

			if(!(id != 0 && !Block.blocksList[id].isAirBlock(worldObj, coords.posX, coords.posY, coords.posZ))) {
				coords.posY -= 1;
				side = ForgeDirection.UP.ordinal();
				id = worldObj.getBlockId(coords.posX, coords.posY, coords.posZ);
			}

			try {
				ForgeEventFactory.onPlayerInteract(player, Action.RIGHT_CLICK_AIR, coords.posX, coords.posY, coords.posZ, side);
				Entity entity = detectedEntities.isEmpty() ? null : detectedEntities.get(worldObj.rand.nextInt(detectedEntities.size()));
				done = entity != null && entity instanceof EntityLiving && (item.itemInteractionForEntity(stack, (EntityLiving) entity) || entity.interact(player));

				if(!done)
					item.onItemUseFirst(stack, player, worldObj, coords.posX, coords.posY, coords.posZ, side, 0F, 0F, 0F);
				if(!done)
					done = Block.blocksList[id] != null && Block.blocksList[id].onBlockActivated(worldObj, coords.posX, coords.posY, coords.posZ, player, side, 0F, 0F, 0F);
				if(!done)
					done = item.onItemUse(stack, player, worldObj, coords.posX, coords.posY, coords.posZ, side, 0F, 0F, 0F);
				if(!done) {
					stack = item.onItemRightClick(stack, worldObj, player);
					done = true;
				}
			} catch(Throwable e) {
				e.printStackTrace();
				Packet3Chat packet = new Packet3Chat(EnumChatFormatting.RED + "Something went wrong with a Tool Dynamism Tablet! Check your FML log.");
				Packet3Chat packet1 = new Packet3Chat(EnumChatFormatting.RED + "" + EnumChatFormatting.ITALIC + e.getMessage());

				PacketDispatcher.sendPacketToAllAround(xCoord, yCoord, zCoord, 16, worldObj.getWorldInfo().getDimension(), packet);
				PacketDispatcher.sendPacketToAllAround(xCoord, yCoord, zCoord, 16, worldObj.getWorldInfo().getDimension(), packet1);
			}
		}

		if(done) {
			setInventorySlotContents(0, stack.stackSize == 0 ? null : stack);
			PacketDispatcher.sendPacketToAllPlayers(getDescriptionPacket());
		}
	}

	// Copied from ItemInWorldManager, seems to do the trick.
	private void stopBreaking() {
		isBreaking = false;
		ChunkCoordinates coords = getTargetLoc();
		worldObj.destroyBlockInWorldPartially(player.entityId, coords.posX, coords.posY, coords.posZ, -1);
	}

	// Copied from ItemInWorldManager, seems to do the trick.
	private void startBreaking(Block block, int meta) {
		int side = 	SIDES[(getBlockMetadata() & 7) - 2].getOpposite().ordinal();
		ChunkCoordinates coords = getTargetLoc();

		PlayerInteractEvent event = ForgeEventFactory.onPlayerInteract(player, Action.LEFT_CLICK_BLOCK, coords.posX, coords.posY, coords.posZ, side);
        if (event.isCanceled()) {
        	stopBreaking();
            return;
        }

		initialDamage = curblockDamage;
        float var5 = 1F;

        if (block != null) {
            if (event.useBlock != Event.Result.DENY)
                block.onBlockClicked(worldObj, coords.posX, coords.posY, coords.posZ, player);
            var5 = block.getPlayerRelativeBlockHardness(player, worldObj, coords.posX, coords.posY, coords.posZ);
        }

        if (event.useItem == Event.Result.DENY) {
        	stopBreaking();
            return;
        }

        if(var5 >= 1F) {
            tryHarvestBlock(coords.posX, coords.posY, coords.posZ);
            stopBreaking();
        } else {
            int var7 = (int) (var5 * 10);
            worldObj.destroyBlockInWorldPartially(player.entityId, coords.posX, coords.posY, coords.posZ, var7);
            durabilityRemainingOnBlock = var7;
        }
	}

	// Copied from ItemInWorldManager, seems to do the trick.
	private void continueBreaking() {
		++curblockDamage;
        int var1;
        float var4;
        int var5;
		ChunkCoordinates coords = getTargetLoc();

        var1 = curblockDamage - initialDamage;
        int var2 = worldObj.getBlockId(coords.posX, coords.posY, coords.posZ);

        if (var2 == 0)
        	stopBreaking();
        else {
            Block var3 = Block.blocksList[var2];
            var4 = var3.getPlayerRelativeBlockHardness(player, worldObj,coords.posX, coords.posY, coords.posZ) * var1 + 1F;
            var5 = (int) (var4 * 10);

            if (var5 != durabilityRemainingOnBlock) {
                worldObj.destroyBlockInWorldPartially(player.entityId, coords.posX, coords.posY, coords.posZ, var5);
                durabilityRemainingOnBlock = var5;
            }

            if (var4 >= 1F) {
                tryHarvestBlock(coords.posX, coords.posY, coords.posZ);
                stopBreaking();
            }
        }
	}

	// Copied from ItemInWorldManager, seems to do the trick.
    public boolean tryHarvestBlock(int par1, int par2, int par3) {
        ItemStack stack = getStackInSlot(0);
        if (stack != null && stack.getItem().onBlockStartBreak(stack, par1, par2, par3, player))
        	return false;

        int var4 = worldObj.getBlockId(par1, par2, par3);
        int var5 = worldObj.getBlockMetadata(par1, par2, par3);
        worldObj.playAuxSFXAtEntity(player, 2001, par1, par2, par3, var4 + (var5 << 12));
        boolean var6 = false;

        boolean var8 = false;
        Block block = Block.blocksList[var4];
        if (block != null)
            var8 = block.canHarvestBlock(player, var5);

        worldObj.loadedEntityList.size();
        if (stack != null)
            stack.onBlockDestroyed(worldObj, var4, par1, par2, par3, player);

        var6 = removeBlock(par1, par2, par3);
        if (var6 && var8)
        	Block.blocksList[var4].harvestBlock(worldObj, player, par1, par2, par3, var5);

        return var6;
    }

	// Copied from ItemInWorldManager, seems to do the trick.
    private boolean removeBlock(int par1, int par2, int par3) {
        Block var4 = Block.blocksList[worldObj.getBlockId(par1, par2, par3)];
        int var5 = worldObj.getBlockMetadata(par1, par2, par3);

        if (var4 != null)
            var4.onBlockHarvested(worldObj, par1, par2, par3, var5, player);

        boolean var6 = var4 != null && var4.removeBlockByPlayer(worldObj, player, par1, par2, par3);

        if (var4 != null && var6)
            var4.onBlockDestroyedByPlayer(worldObj, par1, par2, par3, var5);

        return var6;
    }

	public boolean detect() {
		ChunkCoordinates coords = getTargetLoc();
		findEntities(coords);
		return !worldObj.isAirBlock(coords.posX, coords.posY, coords.posZ) || !detectedEntities.isEmpty();
	}

	public void findEntities(ChunkCoordinates coords) {
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
		return LibBlockNames.ANIMATION_TABLET_D;
	}

	@Override
	public boolean isInvNameLocalized() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
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