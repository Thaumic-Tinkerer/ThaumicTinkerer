package com.nekokittygames.thaumictinkerer.common.blocks;

import com.nekokittygames.thaumictinkerer.ThaumicTinkerer;
import com.nekokittygames.thaumictinkerer.common.libs.LibBlockNames;
import com.nekokittygames.thaumictinkerer.common.tileentity.TileEntityMobMagnet;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockMobMagnet extends BlockMagnet<TileEntityMobMagnet> {

    public BlockMobMagnet() {
        super(LibBlockNames.MOB_MAGNET);
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileEntityMobMagnet();
    }


}
