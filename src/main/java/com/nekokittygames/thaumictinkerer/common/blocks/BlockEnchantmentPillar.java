package com.nekokittygames.thaumictinkerer.common.blocks;

import com.nekokittygames.thaumictinkerer.ThaumicTinkerer;
import com.nekokittygames.thaumictinkerer.common.libs.LibBlockNames;
import com.nekokittygames.thaumictinkerer.common.tileentity.TileEntityEnchanter;
import com.nekokittygames.thaumictinkerer.common.tileentity.TileEntityEnchantmentPillar;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import thaumcraft.api.blocks.BlocksTC;

import java.util.Random;

public class BlockEnchantmentPillar extends TTTileEntity<TileEntityEnchantmentPillar> {
    public static final PropertyBool Top = PropertyBool.create("top");
    public static final PropertyInteger Direction = PropertyInteger.create("direction", 0, 7);

    public BlockEnchantmentPillar() {
        super(LibBlockNames.ENCHANTMENT_PILLAR, Material.ROCK, false);
        setDefaultState(this.getBlockState().getBaseState().withProperty(Top, false).withProperty(Direction, 0));
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileEntityEnchantmentPillar();
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, Top, Direction);
    }


    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(Direction) + (state.getValue(Top) ? 8 : 0);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        int curMeta = meta;
        IBlockState state = this.blockState.getBaseState();
        if (curMeta >= 8) {
            state = state.withProperty(Top, true);
            curMeta -= 8;
        } else {
            state = state.withProperty(Top, false);
        }
        return state.withProperty(Direction, curMeta);
    }

    @Override
    protected boolean isInCreativeTab() {
        return false;
    }

    public EnumBlockRenderType getRenderType(IBlockState state) {
        if (!state.getValue(Top))
            return EnumBlockRenderType.MODEL;
        else
            return EnumBlockRenderType.INVISIBLE;
    }

    private BlockPos IsEnchanterPos(World world,BlockPos pos)
    {
        if(world.getBlockState(pos).getBlock()==ModBlocks.osmotic_enchanter)
            return pos;
        if(world.getBlockState(pos.down(1)).getBlock()==ModBlocks.osmotic_enchanter)
            return pos.down(1);
        return null;
    }
    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        BlockPos enchanterLoc=null;
        enchanterLoc=IsEnchanterPos(worldIn,pos.south(3));
        if(enchanterLoc==null)
            enchanterLoc = IsEnchanterPos(worldIn,pos.west(3));
        if(enchanterLoc==null)
            enchanterLoc = IsEnchanterPos(worldIn,pos.north(3));
        if(enchanterLoc==null)
            enchanterLoc = IsEnchanterPos(worldIn,pos.east(3));
        if(enchanterLoc==null)
            enchanterLoc = IsEnchanterPos(worldIn, pos.south(2).west(2));
        if(enchanterLoc==null)
            enchanterLoc = IsEnchanterPos(worldIn, pos.north(2).west(2));
        if(enchanterLoc==null)
            enchanterLoc = IsEnchanterPos(worldIn, pos.south(2).east(2));
        if(enchanterLoc==null)
            enchanterLoc = IsEnchanterPos(worldIn, pos.north(2).east(2));

        ThaumicTinkerer.logger.info("Enchanter Pos Found - "+ (enchanterLoc != null ? enchanterLoc.toString() : "null"));
        if(enchanterLoc!=null)
        {
            TileEntity te=worldIn.getTileEntity(enchanterLoc);
            if(te instanceof TileEntityEnchanter)
            {
                ((TileEntityEnchanter)te).BreakPillars();
            }
        }
    }

    @NotNull
    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(BlocksTC.stoneArcane);
    }
}
