package com.nekokittygames.thaumictinkerer.common.blocks;

import com.nekokittygames.thaumictinkerer.ThaumicTinkerer;
import com.nekokittygames.thaumictinkerer.common.libs.LibBlockNames;
import com.nekokittygames.thaumictinkerer.common.multiblocks.MultiblockManager;
import com.nekokittygames.thaumictinkerer.common.tileentity.TileEntityEnchanter;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import thaumcraft.api.casters.IInteractWithCaster;
import thaumcraft.api.crafting.IDustTrigger;

import java.util.ArrayList;
import java.util.List;

public class BlockEnchanter extends TTTileEntity<TileEntityEnchanter> {
    public BlockEnchanter() {
        super(LibBlockNames.OSMOTIC_ENCHANTER, Material.ROCK, true);
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileEntityEnchanter();
    }


    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
// Only execute on the server
        if (world.isRemote) {
            return true;
        }
        TileEntity te = world.getTileEntity(pos);
        if (!(te instanceof TileEntityEnchanter)) {
            return false;
        }
        player.openGui(ThaumicTinkerer.instance, 0, world, pos.getX(), pos.getY(), pos.getZ());
        /*
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
            */
        return super.onBlockActivated(world, pos, state, player, hand, facing, hitX, hitY, hitZ);
    }

    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }

    @Override
    public boolean isTranslucent(IBlockState state) {
        return true;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

}
