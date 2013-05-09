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
 * File Created @ [27 Apr 2013, 14:59:05 (GMT)]
 */
package vazkii.tinkerer.item;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.common.items.wands.ItemWandTrade;
import vazkii.tinkerer.ThaumicTinkerer;
import vazkii.tinkerer.client.util.helper.IconHelper;
import vazkii.tinkerer.lib.LibFeatures;
import vazkii.tinkerer.util.helper.ItemNBTHelper;
import vazkii.tinkerer.util.helper.ModCreativeTab;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemWandDislocation extends ItemWandTrade {

	private static String TAG_TILE_CMP = "tileEntityCMP";
	private static String TAG_AVAILABLE = "available";

	public ItemWandDislocation(int i) {
		super(i);
		setMaxDamage(250);
		setCreativeTab(ModCreativeTab.INSTANCE);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		itemIcon = IconHelper.forItem(par1IconRegister, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIconFromDamage(int par1) {
		return itemIcon;
	}

	@Override
	public boolean onItemUseFirst(ItemStack arg0, EntityPlayer arg1, World arg2, int arg3, int arg4, int arg5, int arg6, float arg7, float arg8, float arg9) {
		int id = arg2.getBlockId(arg3, arg4, arg5);
		int meta = arg2.getBlockMetadata(arg3, arg4, arg5);
		TileEntity tile = arg2.getBlockTileEntity(arg3, arg4, arg5);

		if(arg1.canPlayerEdit(arg3, arg4, arg5, arg6, arg0)) {
			if(getPickedBlock(arg0) != null) {
				ItemStack stack = getPickedBlock(arg0);
	            if (arg6 == 0)
	                --arg4;
	            if (arg6 == 1)
	                ++arg4;
	            if (arg6 == 2)
	                --arg5;
	            if (arg6 == 3)
	                ++arg5;
	            if (arg6 == 4)
	                --arg3;
	            if (arg6 == 5)
	                ++arg3;

				if(Block.blocksList[stack.itemID].canPlaceBlockOnSide(arg2, arg3, arg4, arg5, ForgeDirection.getOrientation(arg6).getOpposite().ordinal(), stack)) {
					arg2.setBlock(arg3, arg4, arg5, stack.itemID, stack.getItemDamage(), 1 | 2);
					NBTTagCompound tileCmp = ItemNBTHelper.getCompound(arg0, TAG_TILE_CMP, true);
					if(tileCmp != null && !tileCmp.getTags().isEmpty()) {
						TileEntity tile1 = TileEntity.createAndLoadEntity(tileCmp);
						tile1.xCoord = arg3;
						tile1.yCoord = arg4;
						tile1.zCoord = arg5;
						arg2.setBlockTileEntity(arg3, arg4, arg5, tile1);
					}
					if(arg2.isRemote)
						arg1.swingItem();
					clearPickedBlock(arg0);
					arg0.damageItem(LibFeatures.WAND_DISLOCATION_VIS, arg1);

					for(int i = 0; i < 8; i++) {
						float x = (float) (arg3 + Math.random());
						float y = (float) (arg4 + Math.random()) + 0.65F;
						float z = (float) (arg5 + Math.random());
						ThaumicTinkerer.tcProxy.burst(arg2, x, y, z, 0.2F);
					}
					arg2.playSoundAtEntity(arg1, "thaumcraft.wand", 0.5F, 1F);
				}
			} else if(!ThaumcraftApi.portableHoleBlackList.contains(id) && Block.blocksList[id] != null && Block.blocksList[id].getBlockHardness(arg2, arg3, arg4, arg5) != -1F) {
				arg2.removeBlockTileEntity(arg3, arg4, arg5);
				arg2.setBlock(arg3, arg4, arg5, 0, 0, 1 | 2);
				storePickedBlock(arg0, (short) id, (short) meta, tile);

				for(int i = 0; i < 8; i++) {
					float x = (float) (arg3 + Math.random());
					float y = (float) (arg4 + Math.random());
					float z = (float) (arg5 + Math.random());
					ThaumicTinkerer.tcProxy.burst(arg2, x, y, z, 0.2F);
				}
				arg2.playSoundAtEntity(arg1, Block.blocksList[id].stepSound.getBreakSound(), 1F, 1F);
				arg2.playSoundAtEntity(arg1, "thaumcraft.wand", 0.5F, 1F);

				if(arg2.isRemote)
					arg1.swingItem();
				arg0.damageItem(LibFeatures.WAND_DISLOCATION_VIS, arg1);
			}
		}

		return false;
	}

	@Override
	public boolean onBlockStartBreak(ItemStack itemstack, int x, int y, int z, EntityPlayer player) {
		return true; // Don't allow for the wand of trade behaviour
	}

	@Override
	public ItemStack getPickedBlock(ItemStack stack) {
		return stack.hasTagCompound() && stack.getTagCompound().hasKey(TAG_AVAILABLE) &&
				stack.getTagCompound().getBoolean(TAG_AVAILABLE) ? super.getPickedBlock(stack) : null;
	}

	private void storePickedBlock(ItemStack stack, short id, short meta, TileEntity tile) {
		super.storePickedBlock(stack, id, meta);
		NBTTagCompound cmp = new NBTTagCompound();
		if(tile != null)
			tile.writeToNBT(cmp);
		ItemNBTHelper.setCompound(stack, TAG_TILE_CMP, cmp);
		ItemNBTHelper.setBoolean(stack, TAG_AVAILABLE, true);
	}

	private void clearPickedBlock(ItemStack stack) {
		ItemNBTHelper.setBoolean(stack, TAG_AVAILABLE, false);
	}
}
