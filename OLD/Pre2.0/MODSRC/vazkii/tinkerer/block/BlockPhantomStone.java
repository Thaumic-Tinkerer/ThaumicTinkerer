/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the ThaumicTinkerer Mod.
 *
 * ThaumicTinkerer is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * ThaumicTinkerer is a Derivative Work on Thaumcraft 3.
 * Thaumcraft 3 � Azanor 2012
 * (http://www.minecraftforum.net/topic/1585216-)
 *
 * File Created @ [27 May 2013, 12:27:14 (GMT)]
 */
package vazkii.tinkerer.block;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import thaumcraft.common.Config;
import thaumcraft.common.blocks.BlockWarded;
import thaumcraft.common.items.wands.ItemWandCasting;
import thaumcraft.common.tiles.TileOwned;
import vazkii.tinkerer.util.helper.ModCreativeTab;

public class BlockPhantomStone extends BlockWarded {

	public BlockPhantomStone(int par1) {
		super(par1);
		if(Config.wardedStone)
			setCreativeTab(ModCreativeTab.INSTANCE);
	}

	@Override
	public boolean canCreatureSpawn(EnumCreatureType type, World world, int x, int y, int z) {
		return false;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean isBlockNormalCube(World world, int x, int y, int z) {
		return false;
	}

	@Override
	public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)  {
		TileOwned tile = (TileOwned) par1World.getBlockTileEntity(par2, par3, par4);

		if (tile != null) {
			String owner = tile.owner;

			if(owner.equals(par5EntityPlayer.username)) {
				if(par5EntityPlayer.getCurrentEquippedItem() != null && par5EntityPlayer.getCurrentEquippedItem().getItem() instanceof ItemWandCasting) {
					if(!par1World.isRemote) {
						int id = par1World.getBlockId(par2, par3, par4);
						int meta = par1World.getBlockMetadata(par2, par3, par4);
						par1World.spawnEntityInWorld(new EntityItem(par1World, par2 + 0.5, par3 + 0.5, par4 + 0.5, new ItemStack(id, 1, meta)));
						par1World.playAuxSFX(2001, par2, par3, par4, blockID + (meta << 12));
						tile.safeToRemove = true;
						par1World.setBlock(par2, par3, par4, 0, 0, 1 & 2);
					} else par5EntityPlayer.swingItem();
					return true;
				}
			}
		}

		return false;
	}

	@Override
	public void addCollisionBoxesToList(World par1World, int par2, int par3, int par4, AxisAlignedBB par5AxisAlignedBB, List par6List, Entity par7Entity) {
		TileOwned tile = (TileOwned) par1World.getBlockTileEntity(par2, par3, par4);
		if(!(par7Entity instanceof EntityPlayer && ((EntityPlayer) par7Entity).username.equals(tile.owner)))
			super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
	}
}
