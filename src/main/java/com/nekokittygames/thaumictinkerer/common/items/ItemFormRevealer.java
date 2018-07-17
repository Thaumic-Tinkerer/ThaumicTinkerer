package com.nekokittygames.thaumictinkerer.common.items;

import com.nekokittygames.thaumictinkerer.common.blocks.ModBlocks;
import com.nekokittygames.thaumictinkerer.common.libs.LibItemNames;
import com.nekokittygames.thaumictinkerer.common.tileentity.TileEntityExample;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemFormRevealer extends  TTItem{
    public ItemFormRevealer() {
        super(LibItemNames.FORM_REVEALER);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        worldIn.setBlockState(pos.up(), ModBlocks.example.getDefaultState(),3);
        TileEntity te=worldIn.getTileEntity(pos.up());
        if(te instanceof TileEntityExample)
        {
            TileEntityExample example= (TileEntityExample) te;
            example.setGuideBlockType(Blocks.BOOKSHELF.getDefaultState());
            example.setActivated(true);
            example.sendUpdates();
            return EnumActionResult.SUCCESS;
        }
        return EnumActionResult.PASS;
    }
}
