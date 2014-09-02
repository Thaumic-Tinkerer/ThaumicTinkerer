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
package thaumic.tinkerer.common.block.tile.tablet;

import appeng.api.movable.IMovableTile;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.common.eventhandler.Event;
import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import li.cil.oc.api.network.Arguments;
import li.cil.oc.api.network.Callback;
import li.cil.oc.api.network.Context;
import li.cil.oc.api.network.SimpleComponent;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;
import thaumcraft.common.lib.FakeThaumcraftPlayer;
import thaumic.tinkerer.common.ThaumicTinkerer;
import thaumic.tinkerer.common.block.BlockAnimationTablet;
import thaumic.tinkerer.common.lib.LibBlockNames;

import java.util.ArrayList;
import java.util.List;

@Optional.InterfaceList({
		@Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "OpenComputers"),
		@Optional.Interface(iface = "dan200.computercraft.api.peripheral.IPeripheral", modid = "ComputerCraft")
})
public class TileAnimationTablet extends TileEntity implements IInventory, IMovableTile, IPeripheral, SimpleComponent {

	private static final String TAG_LEFT_CLICK = "leftClick";
	private static final String TAG_REDSTONE = "redstone";
	private static final String TAG_PROGRESS = "progress";
	private static final String TAG_MOD = "mod";
	private static final String TAG_OWNER = "owner";

	private static final int[][] LOC_INCREASES = new int[][]{
			{ 0, -1 },
			{ 0, +1 },
			{ -1, 0 },
			{ +1, 0 }
	};

	private static final ForgeDirection[] SIDES = new ForgeDirection[]{
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

		if (stack != null) {
			if (swingProgress >= MAX_DEGREE)
				swingHit();

			swingMod = swingProgress <= 0 ? 0 : swingProgress >= MAX_DEGREE ? -SWING_SPEED : swingMod;
			swingProgress += swingMod;
			if (swingProgress < 0)
				swingProgress = 0;
		} else {
			swingMod = 0;
			swingProgress = 0;

			if (isBreaking)
				stopBreaking();
		}

		boolean detect = detect();
		if (!detect)
			stopBreaking();

		if (detect && isBreaking)
			continueBreaking();

		if ((!redstone || isBreaking) && detect && swingProgress == 0) {
			initiateSwing();
			worldObj.addBlockEvent(xCoord, yCoord, zCoord, ThaumicTinkerer.registry.getFirstBlockFromClass(BlockAnimationTablet.class), 0, 0);
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
		Block block = worldObj.getBlock(coords.posX, coords.posY, coords.posZ);

		player.setCurrentItemOrArmor(0, stack);
		//EntityPlayer realPlayer=MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(Owner);
		//NBTTagCompound data=realPlayer.getEntityData().getCompoundTag("PlayerPersisted");
		//player.getEntityData().setCompoundTag("PlayerPersisted",data);
		//NBTTagCompound cmp=player.getEntityData().getCompoundTag("PlayerPersisted");
		//System.out.println(cmp.getCompoundTag("TCResearch").getTagList("TCResearchList").tagCount());

		boolean done = false;

		if (leftClick) {
			Entity entity = detectedEntities.isEmpty() ? null : detectedEntities.get(worldObj.rand.nextInt(detectedEntities.size()));
			if (entity != null) {
				player.getAttributeMap().applyAttributeModifiers(stack.getAttributeModifiers()); // Set attack strenght
				player.attackTargetEntityWithCurrentItem(entity);
				done = true;
			} else if (!isBreaking) {
				if (block != Blocks.air && !block.isAir(worldObj, coords.posX, coords.posY, coords.posZ) && block.getBlockHardness(worldObj, coords.posX, coords.posY, coords.posZ) >= 0) {
					isBreaking = true;
					startBreaking(block, worldObj.getBlockMetadata(coords.posX, coords.posY, coords.posZ));
					done = true;
				}
			}
		} else {
			int side = SIDES[(getBlockMetadata() & 7) - 2].getOpposite().ordinal();

			if (!(block != Blocks.air && !block.isAir(worldObj, coords.posX, coords.posY, coords.posZ))) {
				coords.posY -= 1;
				side = ForgeDirection.UP.ordinal();
				block = worldObj.getBlock(coords.posX, coords.posY, coords.posZ);
			}

			try {
				ForgeEventFactory.onPlayerInteract(player, Action.RIGHT_CLICK_AIR, coords.posX, coords.posY, coords.posZ, side,worldObj);
				Entity entity = detectedEntities.isEmpty() ? null : detectedEntities.get(worldObj.rand.nextInt(detectedEntities.size()));
				done = entity != null && entity instanceof EntityLiving && (item.itemInteractionForEntity(stack, player, (EntityLivingBase) entity) || (!(entity instanceof EntityAnimal) || ((EntityAnimal) entity).interact(player)));

				if (!done)
					item.onItemUseFirst(stack, player, worldObj, coords.posX, coords.posY, coords.posZ, side, 0F, 0F, 0F);
				if (!done)
					done = block != null && block.onBlockActivated(worldObj, coords.posX, coords.posY, coords.posZ, player, side, 0F, 0F, 0F);
				if (!done)
					done = item.onItemUse(stack, player, worldObj, coords.posX, coords.posY, coords.posZ, side, 0F, 0F, 0F);
				if (!done) {
					item.onItemRightClick(stack, worldObj, player);
					done = true;
				}

			} catch (Throwable e) {
				e.printStackTrace();
				List list = worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(xCoord - 8, yCoord - 8, zCoord - 8, xCoord + 8, yCoord + 8, zCoord + 8));
				for (Object player : list) {
					((EntityPlayer) player).addChatComponentMessage(new ChatComponentText(EnumChatFormatting.RED + "Something went wrong with a Tool Dynamism Tablet! Check your FML log."));
					((EntityPlayer) player).addChatComponentMessage(new ChatComponentText(EnumChatFormatting.RED + "" + EnumChatFormatting.ITALIC + e.getMessage()));
				}
			}
		}

		if (done) {
			stack = player.getCurrentEquippedItem();
			if (stack == null || stack.stackSize == 0)
				setInventorySlotContents(0, null);
            if(stack!=getStackInSlot(0))
                setInventorySlotContents(0,stack);
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		}
		markDirty();
	}

	// Copied from ItemInWorldManager, seems to do the trick.
	private void stopBreaking() {
		isBreaking = false;
		ChunkCoordinates coords = getTargetLoc();
		worldObj.destroyBlockInWorldPartially(player.getEntityId(), coords.posX, coords.posY, coords.posZ, -1);
	}

	// Copied from ItemInWorldManager, seems to do the trick.
	private void startBreaking(Block block, int meta) {
		int side = SIDES[(getBlockMetadata() & 7) - 2].getOpposite().ordinal();
		ChunkCoordinates coords = getTargetLoc();

		PlayerInteractEvent event = ForgeEventFactory.onPlayerInteract(player, Action.LEFT_CLICK_BLOCK, coords.posX, coords.posY, coords.posZ, side,worldObj);
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

		if (var5 >= 1F) {
			tryHarvestBlock(coords.posX, coords.posY, coords.posZ);
			stopBreaking();
		} else {
			int var7 = (int) (var5 * 10);
			worldObj.destroyBlockInWorldPartially(player.getEntityId(), coords.posX, coords.posY, coords.posZ, var7);
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
		Block block = worldObj.getBlock(coords.posX, coords.posY, coords.posZ);

		if (block == Blocks.air)
			stopBreaking();
		else {
			var4 = block.getPlayerRelativeBlockHardness(player, worldObj, coords.posX, coords.posY, coords.posZ) * var1;
			var5 = (int) (var4 * 10);

			if (var5 != durabilityRemainingOnBlock) {
				worldObj.destroyBlockInWorldPartially(player.getEntityId(), coords.posX, coords.posY, coords.posZ, var5);
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

		Block block = worldObj.getBlock(par1, par2, par3);
		int var5 = worldObj.getBlockMetadata(par1, par2, par3);
		//worldObj.playAuxSFXAtEntity(player, 2001, par1, par2, par3, var4 + (var5 << 12));
		boolean var6;

		boolean var8 = false;
		if (block != null)
			var8 = block.canHarvestBlock(player, var5);

		worldObj.loadedEntityList.size();
		if (stack != null)
			stack.getItem().onBlockDestroyed(stack, worldObj, block, par1, par2, par3, player);

		var6 = removeBlock(par1, par2, par3);
		if (var6 && var8)
			block.harvestBlock(worldObj, player, par1, par2, par3, var5);

		return var6;
	}

	// Copied from ItemInWorldManager, seems to do the trick.
	private boolean removeBlock(int par1, int par2, int par3) {
		Block var4 = worldObj.getBlock(par1, par2, par3);
		int var5 = worldObj.getBlockMetadata(par1, par2, par3);

		if (var4 != null)
			var4.onBlockHarvested(worldObj, par1, par2, par3, var5, player);

		boolean var6 = var4 != null && var4.removedByPlayer(worldObj, player, par1, par2, par3);

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
		if (meta == 0) {
            ThaumicTinkerer.log.error("Metadata of a Tool Dynamism tablet is in an invalid state. This is a critical error.");
			return coords;
		}
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
		if (par1 == 0) {
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
		par1NBTTagCompound.setBoolean(TAG_LEFT_CLICK, leftClick);
		par1NBTTagCompound.setBoolean(TAG_REDSTONE, redstone);

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
	public ItemStack decrStackSize(int par1, int par2) {
		if (inventorySlots[par1] != null) {
			ItemStack stackAt;

			if (inventorySlots[par1].stackSize <= par2) {
				stackAt = inventorySlots[par1];
				inventorySlots[par1] = null;

				if (!worldObj.isRemote)
					worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);

				return stackAt;
			} else {
				stackAt = inventorySlots[par1].splitStack(par2);

				if (inventorySlots[par1].stackSize == 0)
					inventorySlots[par1] = null;

				if (!worldObj.isRemote)
					worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);

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

		if (!worldObj.isRemote)
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	@Override
	public String getInventoryName() {
		return LibBlockNames.ANIMATION_TABLET;
	}

	@Override
	public boolean hasCustomInventoryName() {
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
		return worldObj.getTileEntity(xCoord, yCoord, zCoord) == this && entityplayer.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) <= 64;
	}

	@Override
	public void openInventory() {

	}

	@Override
	public void closeInventory() {

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
	public String getType() {
		return "tt_animationTablet";
	}

	@Override
	public String[] getMethodNames() {
		return new String[]{ "getRedstone", "setRedstone", "getLeftClick", "setLeftClick", "getRotation", "setRotation", "hasItem", "trigger" };
	}

	@Override
	@Optional.Method(modid = "ComputerCraft")
	public Object[] callMethod(IComputerAccess computer, ILuaContext context, int method, Object[] arguments) throws Exception {
		switch (method) {
			case 0:
				return new Object[]{ redstone };
			case 1:
				return setRedstoneImplementation((Boolean) arguments[0]);
			case 2:
				return new Object[]{ leftClick };
			case 3:
				return setLeftClickImplementation((Boolean) arguments[0]);
			case 4:
				return new Object[]{ getBlockMetadata() - 2 };
			case 5:
				return setRotationImplementation((Double) arguments[0]);
			case 6:
				return new Object[]{ getStackInSlot(0) != null };
			case 7:
				return triggerImplementation();
		}
		return null;
	}

	private Object[] triggerImplementation() {
		if (swingProgress != 0)
			return new Object[]{ false };

		findEntities(getTargetLoc());
		initiateSwing();
		worldObj.addBlockEvent(xCoord, yCoord, zCoord, ThaumicTinkerer.registry.getFirstBlockFromClass(BlockAnimationTablet.class), 0, 0);

		return new Object[]{ true };
	}

	private Object[] setRotationImplementation(Double argument) throws Exception {
		int rotation = (int) argument.doubleValue();

		if (rotation > 3)
			throw new Exception("Invalid value: " + rotation + ".");

		worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, rotation + 2, 1 | 2);
		return null;
	}

	private Object[] setLeftClickImplementation(Boolean argument) {
		this.leftClick = argument;
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		return null;
	}

	private Object[] setRedstoneImplementation(Boolean argument) {
		this.redstone = argument;
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		return null;
	}

	@Override
	@Optional.Method(modid = "ComputerCraft")
	public void attach(IComputerAccess computer) {
		// NO-OP
	}

	@Override
	@Optional.Method(modid = "ComputerCraft")
	public void detach(IComputerAccess computer) {
		// NO-OP
	}

	@Override
	@Optional.Method(modid = "ComputerCraft")
	public boolean equals(IPeripheral other) {
		return this.equals((Object) other);
	}

	@Override
	public boolean prepareToMove() {
		stopBreaking();
		return true;
	}

	@Override
	public void doneMoving() {

	}

	@Override
	public String getComponentName() {
		return getType();
	}

	@Callback(doc = "function():boolean -- Returns Whether tablet is redstone activated")
	@Optional.Method(modid = "OpenComputers")
	public Object[] getRedstone(Context context, Arguments args) throws Exception {
		return new Object[]{ redstone };
	}

	@Callback(doc = "function(boolean):Nil -- Sets Whether tablet is redstone activated")
	@Optional.Method(modid = "OpenComputers")
	public Object[] setRedstone(Context context, Arguments args) throws Exception {
		setRedstoneImplementation(args.checkBoolean(0));
		return new Object[]{ redstone };
	}

	@Callback(doc = "function():boolean -- Returns Whether tablet Left clicks")
	@Optional.Method(modid = "OpenComputers")
	public Object[] getLeftClick(Context context, Arguments args) throws Exception {
		return new Object[]{ leftClick };
	}

	@Callback(doc = "function(boolean):Nil -- Sets Whether tablet Left Clicks")
	@Optional.Method(modid = "OpenComputers")
	public Object[] setLeftClick(Context context, Arguments args) throws Exception {
		setLeftClickImplementation(args.checkBoolean(0));
		return new Object[]{ leftClick };
	}

	// TODO {"hasItem", "trigger" };
	@Callback(doc = "function():number -- Returns tablet Rotation")
	@Optional.Method(modid = "OpenComputers")
	public Object[] getRotation(Context context, Arguments args) throws Exception {
		return new Object[]{ getBlockMetadata() - 2 };
	}

	@Callback(doc = "function(number):Nil -- Sets tablet rotation")
	@Optional.Method(modid = "OpenComputers")
	public Object[] setRotation(Context context, Arguments args) throws Exception {
		setRotationImplementation((double) args.checkInteger(0));
		return new Object[]{ getBlockMetadata() - 2 };
	}

	@Callback(doc = "function():boolean -- Returns wether tablet has an item or not")
	@Optional.Method(modid = "OpenComputers")
	public Object[] hasItem(Context context, Arguments args) throws Exception {
		return new Object[]{ getStackInSlot(0) != null };
	}

	@Callback(doc = "function():Nil -- Triggers tablets swing")
	@Optional.Method(modid = "OpenComputers")
	public Object[] trigger(Context context, Arguments args) throws Exception {
		return triggerImplementation();
	}
}
