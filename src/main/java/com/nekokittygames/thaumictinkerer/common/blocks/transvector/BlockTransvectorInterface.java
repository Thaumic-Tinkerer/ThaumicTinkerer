package com.nekokittygames.thaumictinkerer.common.blocks.transvector;

import com.nekokittygames.thaumictinkerer.common.blocks.TTCamoBlock;
import com.nekokittygames.thaumictinkerer.common.libs.LibBlockNames;
import com.nekokittygames.thaumictinkerer.common.tileentity.transvector.TileEntityTransvectorInterface;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockTransvectorInterface extends TTCamoBlock<TileEntityTransvectorInterface> {
    public BlockTransvectorInterface() {
        super(LibBlockNames.TRANSVECTOR_INTERFACE,Material.WOOD, true);
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileEntityTransvectorInterface();
    }
}
