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
 * File Created @ [Dec 29, 2013, 6:01:31 PM (GMT)]
 */
package vazkii.tinkerer.common.item.kami.tool;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import vazkii.tinkerer.common.ThaumicTinkerer;
import vazkii.tinkerer.common.block.kami.BlockBedrockPortal;
import vazkii.tinkerer.common.core.handler.ConfigHandler;
import vazkii.tinkerer.common.dim.WorldProviderBedrock;

public final class ToolHandler {

	public static Material[] materialsPick = new Material[]{ Material.rock, Material.iron, Material.ice, Material.glass, Material.piston, Material.anvil };
	public static Material[] materialsShovel = new Material[]{ Material.grass, Material.ground, Material.sand, Material.snow, Material.craftedSnow, Material.clay };
	public static Material[] materialsAxe = new Material[]{ Material.coral, Material.leaves, Material.plants, Material.wood };

	public static int getMode(ItemStack tool) {
		return tool.getItemDamage();
	}

	public static int getNextMode(int mode) {
		return mode == 2 ? 0 : mode + 1;
	}

	public static void changeMode(ItemStack tool) {
		int mode = getMode(tool);
		tool.setItemDamage(getNextMode(mode));
	}

	public static boolean isRightMaterial(Material material, Material[] materialsListing) {
		for (Material mat : materialsListing)
			if (material == mat)
				return true;

		return false;
	}

	public static void removeBlocksInIteration(EntityPlayer player, World world, int x, int y, int z, int xs, int ys, int zs, int xe, int ye, int ze, Block block, Material[] materialsListing, boolean silk, int fortune) {
		float blockHardness = (block == null) ? 1.0f : block.getBlockHardness(world, x, y, z);
		for (int x1 = xs; x1 < xe; x1++) {
			for (int y1 = ys; y1 < ye; y1++) {
				for (int z1 = zs; z1 < ze; z1++) {
					if (x != x1 && y != y1 && z != z1) {
						ToolHandler.removeBlockWithDrops(player, world, x1 + x, y1 + y, z1 + z, x, y, z, block, materialsListing, silk, fortune, blockHardness);

					}
				}
			}
		}
	}

	public static void removeBlockWithDrops(EntityPlayer player, World world, int x, int y, int z, int bx, int by, int bz, Block block, Material[] materialsListing, boolean silk, int fortune, float blockHardness) {
		if (!world.blockExists(x, y, z))
			return;

		Block blk = world.getBlock(x, y, z);

		if (block != null && blk != block)
			return;

		int meta = world.getBlockMetadata(x, y, z);
		Material mat = world.getBlock(x, y, z).getMaterial();
		if (blk != null && !blk.isAir(world, x, y, z) && ((blk.getPlayerRelativeBlockHardness(player, world, x, y, z) != 0 || (blk == Blocks.bedrock && (y <= 253 && world.provider instanceof WorldProviderBedrock))))) {
			if (!blk.canHarvestBlock(player, meta) || !isRightMaterial(mat, materialsListing))
				return;
			if (ConfigHandler.bedrockDimensionID != 0 && block == Blocks.bedrock && ((world.provider.isSurfaceWorld() && y < 5) || (y > 253 && world.provider instanceof WorldProviderBedrock))) {
				world.setBlock(x, y, z, ThaumicTinkerer.TTRegistry.getFirstBlockFromClass(BlockBedrockPortal.class));
			}
			if (ConfigHandler.bedrockDimensionID != 0 && world.provider.dimensionId == ConfigHandler.bedrockDimensionID && blk == Blocks.bedrock && y <= 253) {
				world.setBlock(x, y, z, Blocks.air);
			}
			if (!player.capabilities.isCreativeMode && blk != Blocks.bedrock) {
				int localMeta = world.getBlockMetadata(x, y, z);
				if (blk.removedByPlayer(world, player, x, y, z)) {
					blk.onBlockDestroyedByPlayer(world, x, y, z, localMeta);
				}
				blk.harvestBlock(world, player, x, y, z, localMeta);
				blk.onBlockHarvested(world, x, y, z, localMeta, player);
			} else {
				world.setBlockToAir(x, y, z);
			}
		}
	}

	public static String getToolModeStr(IAdvancedTool tool, ItemStack stack) {
		return StatCollector.translateToLocal("ttmisc.mode." + tool.getType() + "." + ToolHandler.getMode(stack));
	}

	/**
	 * @author mDiyo
	 */
	public static MovingObjectPosition raytraceFromEntity(World world, Entity player, boolean par3, double range) {
		float f = 1.0F;
		float f1 = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * f;
		float f2 = player.prevRotationYaw + (player.rotationYaw - player.prevRotationYaw) * f;
		double d0 = player.prevPosX + (player.posX - player.prevPosX) * f;
		double d1 = player.prevPosY + (player.posY - player.prevPosY) * f;
		if (!world.isRemote && player instanceof EntityPlayer)
			d1 += 1.62D;
		double d2 = player.prevPosZ + (player.posZ - player.prevPosZ) * f;
		Vec3 vec3 = world.getWorldVec3Pool().getVecFromPool(d0, d1, d2);
		float f3 = MathHelper.cos(-f2 * 0.017453292F - (float) Math.PI);
		float f4 = MathHelper.sin(-f2 * 0.017453292F - (float) Math.PI);
		float f5 = -MathHelper.cos(-f1 * 0.017453292F);
		float f6 = MathHelper.sin(-f1 * 0.017453292F);
		float f7 = f4 * f5;
		float f8 = f3 * f5;
		double d3 = range;
		if (player instanceof EntityPlayerMP)
			d3 = ((EntityPlayerMP) player).theItemInWorldManager.getBlockReachDistance();
		Vec3 vec31 = vec3.addVector(f7 * d3, f6 * d3, f8 * d3);
		return world.rayTraceBlocks(vec3, vec31, par3);
	}

}
