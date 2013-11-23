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
 * File Created @ [9 Sep 2013, 22:19:25 (GMT)]
 */
package vazkii.tinkerer.common.item.foci;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.Icon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.config.Config;
import thaumcraft.common.items.wands.ItemWandCasting;
import vazkii.tinkerer.client.core.helper.IconHelper;
import vazkii.tinkerer.common.ThaumicTinkerer;
import vazkii.tinkerer.common.core.helper.ItemNBTHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemFocusDislocation extends ItemModFocus {

	private static final String TAG_AVAILABLE = "available";
	private static final String TAG_TILE_CMP = "tileCmp";
	private static final String TAG_BLOCK_ID = "blockID";
	private static final String TAG_BLOCK_META = "blockMeta";

	private Icon ornament;

	private static final AspectList visUsage = new AspectList().add(Aspect.ENTROPY, 500).add(Aspect.ORDER, 500).add(Aspect.EARTH, 100);
	private static final AspectList visUsageTile = new AspectList().add(Aspect.ENTROPY, 2500).add(Aspect.ORDER, 2500).add(Aspect.EARTH, 500);
	private static final AspectList visUsageSpawner = new AspectList().add(Aspect.ENTROPY, 10000).add(Aspect.ORDER, 10000).add(Aspect.EARTH, 5000);

	public ItemFocusDislocation(int i) {
		super(i);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		super.registerIcons(par1IconRegister);
		ornament = IconHelper.forItem(par1IconRegister, this, "Orn");
	}

	@Override
	public ItemStack onFocusRightClick(ItemStack itemstack, World world, EntityPlayer player, MovingObjectPosition mop) {
		if(mop == null)
			return itemstack;

		int id = world.getBlockId(mop.blockX, mop.blockY, mop.blockZ);
		int meta = world.getBlockMetadata(mop.blockX, mop.blockY, mop.blockZ);
		TileEntity tile = world.getBlockTileEntity(mop.blockX, mop.blockY, mop.blockZ);
		ItemWandCasting wand = (ItemWandCasting)itemstack.getItem();

		if(player.canPlayerEdit(mop.blockX, mop.blockY, mop.blockZ, mop.sideHit, itemstack)) {
			ItemStack stack = getPickedBlock(itemstack);
			if(stack != null) {
	            if (mop.sideHit == 0)
	                --mop.blockY;
	            if (mop.sideHit == 1)
	                ++mop.blockY;
	            if (mop.sideHit == 2)
	                --mop.blockZ;
	            if (mop.sideHit == 3)
	                ++mop.blockZ;
	            if (mop.sideHit == 4)
	                --mop.blockX;
	            if (mop.sideHit == 5)
	                ++mop.blockX;

				if(Block.blocksList[stack.itemID].canPlaceBlockOnSide(world, mop.blockX, mop.blockY, mop.blockZ, mop.sideHit, stack)) {
					if(!world.isRemote) {
						world.setBlock(mop.blockX, mop.blockY, mop.blockZ, stack.itemID, stack.getItemDamage(), 1 | 2);
						Block.blocksList[stack.itemID].onBlockPlacedBy(world, mop.blockX, mop.blockY, mop.blockZ, player, itemstack);
						NBTTagCompound tileCmp = getStackTileEntity(itemstack);
						if(tileCmp != null && !tileCmp.getTags().isEmpty()) {
							TileEntity tile1 = TileEntity.createAndLoadEntity(tileCmp);
							tile1.xCoord = mop.blockX;
							tile1.yCoord = mop.blockY;
							tile1.zCoord = mop.blockZ;
							world.setBlockTileEntity(mop.blockX, mop.blockY, mop.blockZ, tile1);
						}
					} else player.swingItem();
					clearPickedBlock(itemstack);

					for(int i = 0; i < 8; i++) {
						float x = (float) (mop.blockX + Math.random());
						float y = (float) (mop.blockY + Math.random()) + 0.65F;
						float z = (float) (mop.blockZ + Math.random());
						ThaumicTinkerer.tcProxy.burst(world, x, y, z, 0.2F);
					}
					world.playSoundAtEntity(player, "thaumcraft:wand", 0.5F, 1F);
				}
			} else if(!ThaumcraftApi.portableHoleBlackList.contains(id) && Item.itemsList[id] != null && Block.blocksList[id] != null && Block.blocksList[id].getBlockHardness(world, mop.blockX, mop.blockY, mop.blockZ) != -1F && wand.consumeAllVis(itemstack, player, getCost(tile), true)) {
				if(!world.isRemote) {
					world.removeBlockTileEntity(mop.blockX, mop.blockY, mop.blockZ);
					world.setBlock(mop.blockX, mop.blockY, mop.blockZ, 0, 0, 1 | 2);
				}

				storePickedBlock(itemstack, (short) id, (short) meta, tile);

				for(int i = 0; i < 8; i++) {
					float x = (float) (mop.blockX + Math.random());
					float y = (float) (mop.blockY + Math.random());
					float z = (float) (mop.blockZ + Math.random());
					ThaumicTinkerer.tcProxy.burst(world, x, y, z, 0.2F);
				}
				world.playSoundAtEntity(player, Block.blocksList[id].stepSound.getBreakSound(), 1F, 1F);
				world.playSoundAtEntity(player, "thaumcraft:wand", 0.5F, 1F);

				if(world.isRemote)
					player.swingItem();
			}
		}

		return itemstack;
	}

	private static AspectList getCost(TileEntity tile) {
		return tile == null ? visUsage : tile instanceof TileEntityMobSpawner ? visUsageSpawner : visUsageTile;
	}

	@Override
	public String getSortingHelper(ItemStack itemstack) {
		return "DISLOCATION";
	}

	public ItemStack getPickedBlock(ItemStack stack) {
		return stack.hasTagCompound() && stack.getTagCompound().hasKey(TAG_AVAILABLE) && stack.getTagCompound().getBoolean(TAG_AVAILABLE) ? getPickedBlockStack(stack) : null;
	}

	public ItemStack getPickedBlockStack(ItemStack stack) {
		int id = ItemNBTHelper.getInt(stack, TAG_BLOCK_ID, 0);
		int meta = ItemNBTHelper.getInt(stack, TAG_BLOCK_META, 0);
		return new ItemStack(id, 1, meta);
	}

	public NBTTagCompound getStackTileEntity(ItemStack stack) {
		return ItemNBTHelper.getCompound(stack, TAG_TILE_CMP, true);
	}

	private void storePickedBlock(ItemStack stack, int id, short meta, TileEntity tile) {
		ItemNBTHelper.setInt(stack, TAG_BLOCK_ID, id);
		ItemNBTHelper.setInt(stack, TAG_BLOCK_META, meta);
		NBTTagCompound cmp = new NBTTagCompound();
		if(tile != null)
			tile.writeToNBT(cmp);
		ItemNBTHelper.setCompound(stack, TAG_TILE_CMP, cmp);
		ItemNBTHelper.setBoolean(stack, TAG_AVAILABLE, true);
	}

	private void clearPickedBlock(ItemStack stack) {
		ItemNBTHelper.setBoolean(stack, TAG_AVAILABLE, false);
	}

	@Override
	public int getFocusColor() {
		return 0xFFB200;
	}

	@Override
	public Icon getOrnament() {
		return ornament;
	}

	@Override
	public AspectList getVisCost() {
		return visUsage;
	}
	
	@Override
	public boolean acceptsEnchant(int paramInt) {
		return super.acceptsEnchant(paramInt) && paramInt != Config.enchPotency.effectId;
	} 
}
