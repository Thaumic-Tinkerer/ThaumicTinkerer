package com.nekokittygames.thaumictinkerer.common.items;

import com.nekokittygames.thaumictinkerer.common.blocks.ModBlocks;
import com.nekokittygames.thaumictinkerer.common.libs.LibItemNames;
import com.nekokittygames.thaumictinkerer.common.multiblocks.Multiblock;
import com.nekokittygames.thaumictinkerer.common.multiblocks.MultiblockBlock;
import com.nekokittygames.thaumictinkerer.common.multiblocks.MultiblockLayer;
import com.nekokittygames.thaumictinkerer.common.multiblocks.MultiblockManager;
import com.nekokittygames.thaumictinkerer.common.tileentity.TileEntityExample;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemFormRevealer extends TTItem {
    public ItemFormRevealer() {
        super(LibItemNames.FORM_REVEALER);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        Multiblock tmp = MultiblockManager.getMultiblock(worldIn.getBlockState(pos));
        if (tmp == null)
            return EnumActionResult.PASS;
        for (MultiblockLayer layer : tmp) {
            for (MultiblockBlock block : layer) {
                int x = pos.getX() + block.getxOffset();
                int y = pos.getY() + layer.getyLevel();
                int z = pos.getZ() + block.getzOffset();
                BlockPos newPos = new BlockPos(x, y, z);
                if (worldIn.isAirBlock(newPos)) {
                    worldIn.setBlockState(newPos, ModBlocks.example.getDefaultState(), 3);
                    TileEntity te = worldIn.getTileEntity(newPos);
                    if (te instanceof TileEntityExample) {
                        TileEntityExample example = (TileEntityExample) te;
                        example.getGuideBlockType().addAll(tmp.getBlocks().get(block.getBlockName()).getBlockTypes());
                        example.setActivated(true);
                        example.sendUpdates();

                    }
                }
            }
        }


        return EnumActionResult.SUCCESS;
    }
}
