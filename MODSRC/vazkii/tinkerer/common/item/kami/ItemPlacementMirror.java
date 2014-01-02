package vazkii.tinkerer.common.item.kami;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import vazkii.tinkerer.client.core.handler.kami.ToolModeHUDHandler;
import vazkii.tinkerer.client.core.helper.IconHelper;
import vazkii.tinkerer.client.core.proxy.TTClientProxy;
import vazkii.tinkerer.common.core.helper.ItemNBTHelper;
import vazkii.tinkerer.common.item.ItemMod;
import vazkii.tinkerer.common.item.ModItems;
import vazkii.tinkerer.common.item.kami.tool.ToolHandler;

public class ItemPlacementMirror extends ItemMod {

	private static final String TAG_BLOCK_ID = "blockID";
	private static final String TAG_BLOCK_META = "blockMeta";
	private static final String TAG_SIZE = "size";
	
	Icon[] icons = new Icon[2];
	
	public ItemPlacementMirror(int par1) {
		super(par1);
		setMaxStackSize(1);
	}
	
	@Override
	public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10) {
		int id = par3World.getBlockId(par4, par5, par6);
		int meta = par3World.getBlockMetadata(par4, par5, par6);
		
		if(par2EntityPlayer.isSneaking()) {
			if(Block.blocksList[id] != null && Block.blocksList[id].getRenderType() == 0)
				setBlock(par1ItemStack, id, meta);
		} else placeAllBlocks(par1ItemStack, par2EntityPlayer);
		
		return true;
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
		if(par3EntityPlayer.isSneaking()) {
			int size = getSize(par1ItemStack);
			int newSize = size == 11 ? 3 : size + 2;
			setSize(par1ItemStack, newSize);
			ToolModeHUDHandler.setTooltip(newSize + " x " + newSize);
			par2World.playSoundAtEntity(par3EntityPlayer, "random.orb", 0.3F, 0.1F);
		}
		
		return par1ItemStack;
	}
	
	public void placeAllBlocks(ItemStack stack, EntityPlayer player) {
		ChunkCoordinates[] blocksToPlace = getBlocksToPlace(stack, player);
		if(!hasBlocks(stack, player, blocksToPlace))
			return;
		
		ItemStack stackToPlace = new ItemStack(getBlockID(stack), 1, getBlockMeta(stack));
		for(ChunkCoordinates coords : blocksToPlace)
			placeBlockAndConsume(player, stackToPlace, coords);
		player.worldObj.playSoundAtEntity(player, "thaumcraft:wand", 1F, 1F);
	}
	
	private void placeBlockAndConsume(EntityPlayer player, ItemStack blockToPlace, ChunkCoordinates coords) {
		player.worldObj.setBlock(coords.posX, coords.posY, coords.posZ, blockToPlace.itemID, blockToPlace.getItemDamage(), 1 | 2);
		
		if(player.capabilities.isCreativeMode)
			return;
		
		List<ItemStack> talismansToCheck = new ArrayList();
		for(int i = 0; i < player.inventory.getSizeInventory(); i++) {
			ItemStack stackInSlot = player.inventory.getStackInSlot(i);
			if(stackInSlot != null && stackInSlot.itemID == blockToPlace.itemID && stackInSlot.getItemDamage() == blockToPlace.getItemDamage()) {
				stackInSlot.stackSize--;
				if(stackInSlot.stackSize == 0)
					player.inventory.setInventorySlotContents(i, null);
				
				return;
			}
			
			if(stackInSlot != null && stackInSlot.itemID == ModItems.blockTalisman.itemID)
				talismansToCheck.add(stackInSlot);
		}
		
		for(ItemStack talisman : talismansToCheck) {
			int id = ItemBlockTalisman.getBlockID(talisman);
			int meta = ItemBlockTalisman.getBlockMeta(talisman);
			
			if(id == blockToPlace.itemID && meta == blockToPlace.getItemDamage()) {
				ItemBlockTalisman.remove(talisman, 1);
				return;
			}
		}
	}
	
	public static boolean hasBlocks(ItemStack stack, EntityPlayer player, ChunkCoordinates[] blocks) {
		if(player.capabilities.isCreativeMode)
			return true;
		
		int required = blocks.length;
		int current = 0;
		
		ItemStack reqStack = new ItemStack(getBlockID(stack), 1, getBlockMeta(stack));
		List<ItemStack> talismansToCheck = new ArrayList();
		for(int i = 0; i < player.inventory.getSizeInventory(); i++) {
			ItemStack stackInSlot = player.inventory.getStackInSlot(i);
			if(stackInSlot != null && stackInSlot.itemID == reqStack.itemID && stackInSlot.getItemDamage() == reqStack.getItemDamage()) {
				current += stackInSlot.stackSize;
				if(current >= required)
					return true;
			}
			if(stackInSlot != null && stackInSlot.itemID == ModItems.blockTalisman.itemID)
				talismansToCheck.add(stackInSlot);
		}
		
		for(ItemStack talisman : talismansToCheck) {
			int id = ItemBlockTalisman.getBlockID(talisman);
			int meta = ItemBlockTalisman.getBlockMeta(talisman);
			
			if(id == reqStack.itemID && meta == reqStack.getItemDamage()) {
				current += ItemBlockTalisman.getBlockCount(talisman);
				
				if(current >= required)
					return true;
			}
		}
		
		return false;
	}
	
	public static ChunkCoordinates[] getBlocksToPlace(ItemStack stack, EntityPlayer player) {
		List<ChunkCoordinates> coords = new ArrayList();
		MovingObjectPosition pos = ToolHandler.raytraceFromEntity(player.worldObj, player, true, 5);
		if(pos != null) {
			int id = player.worldObj.getBlockId(pos.blockX, pos.blockY, pos.blockZ);
			if(Block.blocksList[id] != null && Block.blocksList[id].isBlockReplaceable(player.worldObj, pos.blockX, pos.blockY, pos.blockZ))
				pos.blockY--;
			
			ForgeDirection dir = ForgeDirection.getOrientation(pos.sideHit);
            int rotation = MathHelper.floor_double(player.rotationYaw * 4F / 360F + 0.5D) & 3;
			int range = (getSize(stack) ^ 1) / 2;
			
			boolean topOrBottom = dir == ForgeDirection.UP || dir == ForgeDirection.DOWN;
			
			int xOff = !(dir == ForgeDirection.WEST || dir == ForgeDirection.EAST) ? topOrBottom ? (player.rotationPitch > 75 || (rotation & 1) == 0 ? range : 0) : range : 0;
			int yOff = topOrBottom ? (player.rotationPitch > 75 ? 0 : range) : range;
			int zOff = !(dir == ForgeDirection.SOUTH || dir == ForgeDirection.NORTH) ? topOrBottom ? (player.rotationPitch > 75 || (rotation & 1) == 1 ? range : 0) : range : 0;
			
			for(int x = -xOff; x < xOff + 1; x++)
				for(int y = 0; y < yOff * 2 + 1; y++) {
					for(int z = -zOff; z < zOff + 1; z++) {
						int xp = pos.blockX + x + dir.offsetX;
						int yp = pos.blockY + y + dir.offsetY;
						int zp =  pos.blockZ + z + dir.offsetZ;
						
						int id1 = player.worldObj.getBlockId(xp, yp, zp);
						if(Block.blocksList[id1] == null || Block.blocksList[id1].isAirBlock(player.worldObj, xp, yp, zp) || Block.blocksList[id1].isBlockReplaceable(player.worldObj, xp, yp, zp))
							coords.add(new ChunkCoordinates(xp, yp,zp));
					}
				}

		}
		
		return coords.toArray(new ChunkCoordinates[0]);
	}
	
	private void setBlock(ItemStack stack, int id, int meta) {
		ItemNBTHelper.setInt(stack, TAG_BLOCK_ID, id);
		ItemNBTHelper.setInt(stack, TAG_BLOCK_META, meta);
	}
	
	private static void setSize(ItemStack stack, int size) {
		ItemNBTHelper.setInt(stack, TAG_SIZE, size | 1);
	}

	public static int getSize(ItemStack stack) {
		return ItemNBTHelper.getInt(stack, TAG_SIZE, 3) | 1;
	}
	
	public static int getBlockID(ItemStack stack) {
		return ItemNBTHelper.getInt(stack, TAG_BLOCK_ID, 0);
	}

	public static int getBlockMeta(ItemStack stack) {
		return ItemNBTHelper.getInt(stack, TAG_BLOCK_META, 0);
	}
	
	@Override
	public void registerIcons(IconRegister par1IconRegister) {
		icons[0] = IconHelper.forItem(par1IconRegister, this, 0);
		icons[1] = IconHelper.forItem(par1IconRegister, this, 1);
	}
	
	@Override
	public Icon getIconFromDamageForRenderPass(int par1, int par2) {
		return icons[par2 & 1];
	}
	
	@Override
	public boolean requiresMultipleRenderPasses() {
		return true;
	}
	
	@Override
	public EnumRarity getRarity(ItemStack par1ItemStack) {
		return TTClientProxy.kamiRarity;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		int id = getBlockID(par1ItemStack);
		int size = getSize(par1ItemStack);
		par3List.add(size + " x " + size);
		if(id != 0 && Block.blocksList[id] != null)
			par3List.add(StatCollector.translateToLocal(new ItemStack(Block.blocksList[id], 1, getBlockMeta(par1ItemStack)).getUnlocalizedName() + ".name"));
	}

}
