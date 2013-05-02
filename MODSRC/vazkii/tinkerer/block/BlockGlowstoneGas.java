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
 * File Created @ [25 Apr 2013, 11:57:15 (GMT)]
 */
package vazkii.tinkerer.block;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import vazkii.tinkerer.ThaumicTinkerer;
import vazkii.tinkerer.client.util.helper.IconHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockGlowstoneGas extends BlockMod {

	public BlockGlowstoneGas(int par1) {
		super(par1, Material.air);
		setLightValue(0.85F);
		setBlockBounds(0, 0, 0, 0, 0, 0);
		setTickRandomly(true);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		blockIcon = IconHelper.emptyTexture(par1IconRegister);
	}

	@Override
	public int getRenderType() {
		return -1;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random) {
		if(par5Random.nextFloat() < 0.0075F)
			ThaumicTinkerer.tcProxy.sparkle(par2 + 0.5F, par3 + 0.5F, par4 + 0.5F, 1F, 1, par5Random.nextFloat() / 2);
	}

	@Override
	public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random) {
		int meta = par1World.getBlockMetadata(par2, par3, par4);
		if(meta != 0) {
			setAt(par1World, par2 - 1, par3, par4, meta - 1);
			setAt(par1World, par2 + 1, par3, par4, meta - 1);

			setAt(par1World, par2, par3 - 1, par4, meta - 1);
			setAt(par1World, par2, par3 + 1, par4, meta - 1);

			setAt(par1World, par2, par3, par4 - 1, meta - 1);
			setAt(par1World, par2, par3, par4 + 1, meta - 1);

			// Just in case...
			par1World.setBlockMetadataWithNotify(par2, par3, par4, 0, 2);

			ThaumicTinkerer.tcProxy.sparkle(par2 + 0.5F, par3 + 0.5F, par4 + 0.5F, 1);
		}
	}

	private void setAt(World world, int x, int y, int z, int meta) {
		if(world.isAirBlock(x, y, z) && world.getBlockId(x, y, z) != blockID) {
			if(!world.isRemote)
				world.setBlock(x, y, z, blockID, meta, 2);
			world.scheduleBlockUpdate(x, y, z, blockID, 10);
		}
	}

	@Override
	public boolean isBlockNormalCube(World world, int x, int y, int z) {
		return false;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	boolean registerInCreative() {
		return false;
	}

	@Override
	public boolean canDragonDestroy(World world, int x, int y, int z) {
		return false;
	}

	@Override
	public boolean canCollideCheck(int par1, boolean par2) {
		return false;
	}

	@Override
	public boolean canBeReplacedByLeaves(World world, int x, int y, int z) {
		return true;
	}

	@Override
	public boolean canDropFromExplosion(Explosion par1Explosion) {
		return false;
	}

	@Override
	public ArrayList<ItemStack> getBlockDropped(World world, int x, int y, int z, int metadata, int fortune) {
		return new ArrayList(); // Empty List
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4) {
		return null;
	}

	@Override
	public boolean isAirBlock(World world, int x, int y, int z) {
		return true;
	}
}