package vazkii.tinkerer.common.item.kami;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import vazkii.tinkerer.client.core.handler.kami.ToolModeHUDHandler;
import vazkii.tinkerer.client.core.helper.IconHelper;
import vazkii.tinkerer.client.core.proxy.TTClientProxy;
import vazkii.tinkerer.common.core.helper.ItemNBTHelper;
import vazkii.tinkerer.common.item.ItemMod;
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
		int meta = par3World.getBlockId(par5, par6, par7);
		
		if(par2EntityPlayer.isSneaking()) {
			if(Block.blocksList[id] != null && Block.blocksList[id].getRenderType() == 0)
				setBlock(par1ItemStack, id, meta);
		} else placeAllBlocks(par1ItemStack, par2EntityPlayer);
		
		return true;
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
		int size = getSize(par1ItemStack);
		int newSize = size == 11 ? 3 : size + 2;
		setSize(par1ItemStack, newSize);
		ToolModeHUDHandler.setTooltip(newSize + " x " + newSize);
		
		return par1ItemStack;
	}
	
	public void placeAllBlocks(ItemStack stack, EntityPlayer player) {
		// TODO
	}
	
	public static ChunkCoordinates[] getBlocksToPlace(ItemStack stack, EntityPlayer player) {
		return new ChunkCoordinates[0];
	}
	
	private void setBlock(ItemStack stack, int id, int meta) {
		ItemNBTHelper.setInt(stack, TAG_BLOCK_ID, id);
		ItemNBTHelper.setInt(stack, TAG_BLOCK_META, meta);
	}
	
	private static void setSize(ItemStack stack, int size) {
		ItemNBTHelper.setInt(stack, TAG_SIZE, size | 1);
	}

	private static int getSize(ItemStack stack) {
		return ItemNBTHelper.getInt(stack, TAG_SIZE, 3) | 1;
	}
	
	private static int getBlockID(ItemStack stack) {
		return ItemNBTHelper.getInt(stack, TAG_BLOCK_ID, 0);
	}

	private static int getBlockMeta(ItemStack stack) {
		return ItemNBTHelper.getInt(stack, TAG_BLOCK_META, 0);
	}
	
	@Override
	public void registerIcons(IconRegister par1IconRegister) {
		super.registerIcons(par1IconRegister);
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

}
