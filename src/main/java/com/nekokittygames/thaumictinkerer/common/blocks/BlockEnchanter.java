package com.nekokittygames.thaumictinkerer.common.blocks;

import com.nekokittygames.thaumictinkerer.common.libs.LibBlockNames;
import com.nekokittygames.thaumictinkerer.common.multiblocks.Multiblock;
import com.nekokittygames.thaumictinkerer.common.multiblocks.MultiblockManager;
import com.nekokittygames.thaumictinkerer.common.tileentity.TileEntityEnchanter;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class BlockEnchanter extends TTTileEntity<TileEntityEnchanter> {
    public BlockEnchanter() {
        super(LibBlockNames.OSMOTIC_ENCHANTER,Material.ROCK,true);
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileEntityEnchanter();
    }


    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

            if (MultiblockManager.checkMultiblock(worldIn,pos,new ResourceLocation("thaumictinkerer:osmotic_enchanter")))
            {
                playerIn.sendStatusMessage(new TextComponentString("Complete"),true);
                try {
                    MultiblockManager.outputMultiblock(worldIn,pos,new ResourceLocation("thaumictinkerer:osmotic_enchanter"),MultiblockManager.checkMultiblockFacing(worldIn,pos,new ResourceLocation("thaumictinkerer:osmotic_enchanter")));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else
            {
                playerIn.sendStatusMessage(new TextComponentString("InComplete"),true);
            }
        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
    }
}
