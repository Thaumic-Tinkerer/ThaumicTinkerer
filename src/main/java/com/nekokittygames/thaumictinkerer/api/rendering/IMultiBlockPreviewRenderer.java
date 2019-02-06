package com.nekokittygames.thaumictinkerer.api.rendering;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Provides a callback to render a block in the multiblock preview
 */
public interface IMultiBlockPreviewRenderer {

    /**
     * Called by the multiblock preview renderer to render the block
     *
     * @param pos        position of the block in world
     * @param x          xPos of block
     * @param y          yPos of block
     * @param z          zPos of block
     * @param world      current World object
     * @param blockState current state of the block
     */
    void render(BlockPos pos, double x, double y, double z, World world, IBlockState blockState);
}
