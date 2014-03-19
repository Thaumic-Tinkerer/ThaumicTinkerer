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
 * File Created @ [9 Sep 2013, 15:51:34 (GMT)]
 */
package vazkii.tinkerer.common.block.tile.tablet;

import appeng.api.movable.IMovableTile;
import cpw.mods.fml.common.network.PacketDispatcher;
import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import net.minecraft.block.Block;
import net.minecraft.command.PlayerSelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.network.packet.Packet3Chat;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.FakePlayer;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.event.Event;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;
import thaumcraft.common.lib.FakeThaumcraftPlayer;
import vazkii.tinkerer.common.block.ModBlocks;
import vazkii.tinkerer.common.lib.LibBlockNames;

import java.util.ArrayList;
import java.util.List;

public class TileAnimationTablet extends TileEntity implements IInventory, IPeripheral, IMovableTile {

	private static final String TAG_LEFT_CLICK = "leftClick";
	private static final String TAG_REDSTONE = "redstone";
	private static final String TAG_PROGRESS = "progress";
	private static final String TAG_MOD = "mod";
    private static final String TAG_OWNER = "owner";

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
    //public String Owner;
	FakeThaumcraftPlayer player;

	@Override
	public void updateEntity() {
			player = new TabletFakePlayer(this);//,Owner);

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
        //EntityPlayer realPlayer=MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(Owner);
        //NBTTagCompound data=realPlayer.getEntityData().getCompoundTag("PlayerPersisted");
        //player.getEntityData().setCompoundTag("PlayerPersisted",data);
        //NBTTagCompound cmp=player.getEntityData().getCompoundTag("PlayerPersisted");
        //System.out.println(cmp.getCompoundTag("TCResearch").getTagList("TCResearchList").tagCount());

		boolean done = false;

		if(leftClick) {
			Entity entity = detectedEntities.isEmpty() ? null : detectedEntities.get(worldObj.rand.nextInt(detectedEntities.size()));
			if(entity != null) {
				player.getAttributeMap().applyAttributeModifiers(stack.getAttributeModifiers()); // Set attack strenght
				player.attackTargetEntityWithCurrentItem(entity);
				done = true;
			} else if(!isBreaking){
				if(id != 0 && !Block.blocksList[id].isAirBlock(worldObj, coords.posX, coords.posY, coords.posZ) && Block.blocksList[id].getBlockHardness(worldObj, coords.posX, coords.posY, coords.posZ) >= 0) {
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
				done = entity != null && entity instanceof EntityLiving && (item.itemInteractionForEntity(stack, player, (EntityLivingBase) entity) || (entity instanceof EntityAnimal ? ((EntityAnimal) entity).interact(player) : true));

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
				Packet3Chat packet = new Packet3Chat(new ChatMessageComponent().addText(EnumChatFormatting.RED + "Something went wrong with a Tool Dynamism Tablet! Check your FML log."));
				Packet3Chat packet1 = new Packet3Chat(new ChatMessageComponent().addText(EnumChatFormatting.RED + "" + EnumChatFormatting.ITALIC + e.getMessage()));

				PacketDispatcher.sendPacketToAllAround(xCoord, yCoord, zCoord, 16, worldObj.provider.dimensionId, packet);
				PacketDispatcher.sendPacketToAllAround(xCoord, yCoord, zCoord, 16, worldObj.provider.dimensionId, packet1);
			}
		}

		if(done) {
			stack = player.getCurrentEquippedItem();
			if(stack == null || stack.stackSize == 0)
				setInventorySlotContents(0, null);

			PacketDispatcher.sendPacketToAllPlayers(getDescriptionPacket());
		}
        onInventoryChanged();
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
            var4 = var3.getPlayerRelativeBlockHardness(player, worldObj,coords.posX, coords.posY, coords.posZ) * var1;
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

	public boolean getIsBreaking() {
		return isBreaking;
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

		swingProgress = par1NBTTagCompound.getInteger(TAG_PROGRESS);

        //if(par1NBTTagCompound.hasKey(TAG_OWNER))
        //    Owner=par1NBTTagCompound.getString(TAG_OWNER);
        //else
        //    Owner="";
		readCustomNBT(par1NBTTagCompound);
	}

	@Override
	public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
		super.writeToNBT(par1NBTTagCompound);

        par1NBTTagCompound.setInteger(TAG_PROGRESS, swingProgress);
        par1NBTTagCompound.setInteger(TAG_MOD, swingMod);
        //par1NBTTagCompound.setString(TAG_OWNER,Owner);
        writeCustomNBT(par1NBTTagCompound);
	}

	public void readCustomNBT(NBTTagCompound par1NBTTagCompound) {
		leftClick = par1NBTTagCompound.getBoolean(TAG_LEFT_CLICK);
		redstone = par1NBTTagCompound.getBoolean(TAG_REDSTONE);

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
        par1NBTTagCompound.setBoolean(TAG_LEFT_CLICK, leftClick);
        par1NBTTagCompound.setBoolean(TAG_REDSTONE, redstone);

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

        		if(!worldObj.isRemote)
        			PacketDispatcher.sendPacketToAllPlayers(getDescriptionPacket());

                return stackAt;
            } else {
                stackAt = inventorySlots[par1].splitStack(par2);

                if (inventorySlots[par1].stackSize == 0)
                    inventorySlots[par1] = null;

        		if(!worldObj.isRemote)
        			PacketDispatcher.sendPacketToAllPlayers(getDescriptionPacket());

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

		if(!worldObj.isRemote)
			PacketDispatcher.sendPacketToAllPlayers(getDescriptionPacket());
	}

	@Override
	public String getInvName() {
		return LibBlockNames.ANIMATION_TABLET;
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
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
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
	public String getType() {
		return "tt_animationTablet";
	}

	@Override
	public String[] getMethodNames() {
		return new String[]{ "getRedstone", "setRedstone", "getLeftClick", "setLeftClick", "getRotation", "setRotation", "hasItem", "trigger" };
	}

	@Override
	public Object[] callMethod(IComputerAccess computer, ILuaContext context, int method, Object[] arguments) throws Exception {
		switch(method) {
			case 0 : return new Object[]{ redstone };
			case 1 : {
				boolean redstone = (Boolean) arguments[0];
				this.redstone = redstone;
				PacketDispatcher.sendPacketToAllPlayers(getDescriptionPacket());
				return null;
			}
			case 2 : return new Object[]{ leftClick };
			case 3 : {
				boolean leftClick = (Boolean) arguments[0];
				this.leftClick = leftClick;
				PacketDispatcher.sendPacketToAllPlayers(getDescriptionPacket());
				return null;
			}
			case 4 : return new Object[] { getBlockMetadata() - 2 };
			case 5 : {
				int rotation = (int) ((Double) arguments[0]).doubleValue();

				if(rotation > 3)
					throw new Exception("Invalid value: " + rotation + ".");

				worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, rotation + 2, 1 | 2);
				return null;
			}
			case 6 : return new Object[] { getStackInSlot(0) != null };
			case 7 : {
				if(swingProgress != 0)
					return new Object[] { false };

				findEntities(getTargetLoc());
				initiateSwing();
				worldObj.addBlockEvent(xCoord, yCoord, zCoord, ModBlocks.animationTablet.blockID, 0, 0);

				return new Object[] { true };
			}
		}
		return null;
	}


	@Override
	public void attach(IComputerAccess computer) {
		// NO-OP
	}

	@Override
	public void detach(IComputerAccess computer) {
		// NO-OP
	}

    @Override
    public boolean equals(IPeripheral other) {
        return this.equals((Object)other);
    }

    @Override
	public boolean prepareToMove() {
		stopBreaking();
		return true;
	}

	@Override
	public void doneMoving() {

	}
}
