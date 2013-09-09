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
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.items.wands.ItemWandCasting;
import thaumcraft.common.items.wands.foci.ItemFocusTrade;
import vazkii.tinkerer.client.core.helper.IconHelper;
import vazkii.tinkerer.common.ThaumicTinkerer;
import vazkii.tinkerer.common.core.handler.ModCreativeTab;
import vazkii.tinkerer.common.core.helper.ItemNBTHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemFocusDislocation extends ItemFocusTrade {

	private static final String TAG_AVAILABLE = "available";
	private static final String TAG_TILE_CMP = "tileCmp";

	private Icon ornament;

	private static final AspectList visUsage = new AspectList().add(Aspect.ENTROPY, 15000).add(Aspect.ORDER, 15000).add(Aspect.EARTH, 7000);

	public ItemFocusDislocation(int i) {
		super(i);
		setCreativeTab(ModCreativeTab.INSTANCE);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		icon = IconHelper.forItem(par1IconRegister, this);
		ornament = IconHelper.forItem(par1IconRegister, this, "Orn");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIconFromDamageForRenderPass(int par1, int renderPass) {
		return icon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses() {
		return false;
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

				if(Block.blocksList[stack.itemID].canPlaceBlockOnSide(world, mop.blockX, mop.blockY, mop.blockZ, ForgeDirection.getOrientation(mop.sideHit).getOpposite().ordinal(), stack)) {
					world.setBlock(mop.blockX, mop.blockY, mop.blockZ, stack.itemID, stack.getItemDamage(), 1 | 2);
					Block.blocksList[stack.itemID].onBlockPlacedBy(world, mop.blockX, mop.blockY, mop.blockZ, player, itemstack);
					NBTTagCompound tileCmp = ItemNBTHelper.getCompound(itemstack, TAG_TILE_CMP, true);
					if(tileCmp != null && !tileCmp.getTags().isEmpty()) {
						TileEntity tile1 = TileEntity.createAndLoadEntity(tileCmp);
						tile1.xCoord = mop.blockX;
						tile1.yCoord = mop.blockY;
						tile1.zCoord = mop.blockZ;
						world.setBlockTileEntity(mop.blockX, mop.blockY, mop.blockZ, tile1);
					}
					if(world.isRemote)
						player.swingItem();
					clearPickedBlock(itemstack);

					for(int i = 0; i < 8; i++) {
						float x = (float) (mop.blockX + Math.random());
						float y = (float) (mop.blockY + Math.random()) + 0.65F;
						float z = (float) (mop.blockZ + Math.random());
						ThaumicTinkerer.tcProxy.burst(world, x, y, z, 0.2F);
					}
					world.playSoundAtEntity(player, "thaumcraft:wand", 0.5F, 1F);
				}
			} else if(!ThaumcraftApi.portableHoleBlackList.contains(id) && Block.blocksList[id] != null && Block.blocksList[id].getBlockHardness(world, mop.blockX, mop.blockY, mop.blockZ) != -1F && wand.consumeAllVis(itemstack, player, getVisCost(), true)) {
				world.removeBlockTileEntity(mop.blockX, mop.blockY, mop.blockZ);
				world.setBlock(mop.blockX, mop.blockY, mop.blockZ, 0, 0, 1 | 2);
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

	@Override
	public ItemStack getPickedBlock(ItemStack stack) {
		return stack.hasTagCompound() && stack.getTagCompound().hasKey(TAG_AVAILABLE) && stack.getTagCompound().getBoolean(TAG_AVAILABLE) ? super.getPickedBlock(stack) : null;
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
}
