package com.nekokittygames.thaumictinkerer.common.blocks;

import com.nekokittygames.thaumictinkerer.common.libs.LibBlockNames;
import com.nekokittygames.thaumictinkerer.common.tileentity.TileEntityItemMagnet;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockItemMagnet extends BlockMagnet<TileEntityItemMagnet> {
    public BlockItemMagnet() {
        super(LibBlockNames.MAGNET);

    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileEntityItemMagnet();
    }
}
