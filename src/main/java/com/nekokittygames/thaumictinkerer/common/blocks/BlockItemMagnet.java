package com.nekokittygames.thaumictinkerer.common.blocks;

import com.nekokittygames.thaumictinkerer.common.libs.LibBlockNames;
import com.nekokittygames.thaumictinkerer.common.tileentity.TileEntityItemMagnet;
import com.nekokittygames.thaumictinkerer.common.tileentity.TileEntityMagnet;
import com.nekokittygames.thaumictinkerer.common.tileentity.TileEntityMobMagnet;
import net.minecraft.block.state.IBlockState;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockItemMagnet extends BlockMagnet<TileEntityItemMagnet> {
    public BlockItemMagnet() {
        super(LibBlockNames.MAGNET);

    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileEntityItemMagnet();
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        TileEntity tileentity = worldIn.getTileEntity(pos);
        if (tileentity instanceof TileEntityItemMagnet) {
            TileEntityItemMagnet itemmagnet= (TileEntityItemMagnet) tileentity;
            if(itemmagnet.getInventory().getStackInSlot(0)!= ItemStack.EMPTY) {
                InventoryHelper.spawnItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), itemmagnet.getInventory().getStackInSlot(0));

            }
            worldIn.updateComparatorOutputLevel(pos, this);
        }
        super.breakBlock(worldIn, pos, state);
    }
}
