package com.nekokittygames.thaumictinkerer.api.rendering;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IMultiBlockPreviewRenderer {

    void render(BlockPos pos, double x, double y, double z, World world, IBlockState blockState);
}
