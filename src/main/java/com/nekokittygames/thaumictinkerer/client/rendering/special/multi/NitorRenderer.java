package com.nekokittygames.thaumictinkerer.client.rendering.special.multi;

import com.nekokittygames.thaumictinkerer.api.rendering.IMultiBlockPreviewRenderer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import thaumcraft.client.fx.FXDispatcher;
import thaumcraft.common.blocks.misc.BlockNitor;

public class NitorRenderer implements IMultiBlockPreviewRenderer {
    @Override
    public void render(BlockPos pos, double x, double y, double z, World world, IBlockState blockState) {
        BlockNitor nitor = (BlockNitor) blockState.getBlock();
        FXDispatcher.INSTANCE.drawNitorFlames((double) ((float) pos.getX() + 0.5F) + world.rand.nextGaussian() * 0.025D, (double) ((float) pos.getY() + 0.45F) + world.rand.nextGaussian() * 0.025D, (double) ((float) pos.getZ() + 0.5F) + world.rand.nextGaussian() * 0.025D, world.rand.nextGaussian() * 0.0025D, (double) world.rand.nextFloat() * 0.06D, world.rand.nextGaussian() * 0.0025D, nitor.getMapColor(blockState, world, pos).colorValue, 0);
    }
}
