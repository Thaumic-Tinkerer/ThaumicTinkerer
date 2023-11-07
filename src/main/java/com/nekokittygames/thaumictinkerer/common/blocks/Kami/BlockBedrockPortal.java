package com.nekokittygames.thaumictinkerer.common.blocks.Kami;

import com.nekokittygames.thaumictinkerer.ThaumicTinkerer;
import com.nekokittygames.thaumictinkerer.common.blocks.ModBlocks;
import com.nekokittygames.thaumictinkerer.common.config.TTConfig;
import com.nekokittygames.thaumictinkerer.common.misc.TeleporterBedrock;
import com.nekokittygames.thaumictinkerer.common.tileentity.Kami.TileBedrockPortal;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class BlockBedrockPortal extends BlockContainer
{

    public BlockBedrockPortal(String name, Material materialIn)
    {
        super(materialIn);
        setTranslationKey(name).setRegistryName(name).setCreativeTab(ThaumicTinkerer.getTab());
        setResistance(6000000.0F);
        setHardness(-2.0F);
    }

    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileBedrockPortal();
    }

    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState)
    {
    }

    public int quantityDropped(Random random)
    {
        return 0;
    }



    @Override
    public void onEntityCollision(World world, BlockPos pos, IBlockState state, Entity entity) {
        super.onEntityCollision(world, pos, state, entity);

        if (entity instanceof EntityPlayer && !world.isRemote) {
            if (entity.dimension != TTConfig.BedRockDimensionID) {

                int x = pos.getX();
                int y = pos.getY();
                int z = pos.getZ();

                BlockPos pos1 = pos.add(0, 251 - y, 0);
                BlockPos pos2 = pos.add(0, 252 - y, 0);
                BlockPos pos3 = pos.add(0, 253 - y, 0);
                BlockPos pos4 = pos.add(0, 254 - y, 0);
                BlockPos pos5 = pos.add(0, 255 - y, 0);

                entity.changeDimension(TTConfig.BedRockDimensionID, new TeleporterBedrock((WorldServer) world));
                entity.setPositionAndUpdate(x + 0.5, 251, z + 0.5);


                if (entity.world.getBlockState(pos1).getBlock() == Blocks.BEDROCK) {
                    entity.world.setBlockToAir(pos1);
                }
                if (entity.world.getBlockState(pos2).getBlock() == Blocks.BEDROCK) {
                    entity.world.setBlockToAir(pos2);
                }
                if (entity.world.getBlockState(pos3).getBlock() == Blocks.BEDROCK) {
                    entity.world.setBlockToAir(pos3);
                }
                if (entity.world.getBlockState(pos4).getBlock() == Blocks.BEDROCK) {
                    entity.world.setBlockToAir(pos4);
                }
                if (entity.world.getBlockState(pos5).getBlock() == Blocks.BEDROCK) {
                    entity.world.setBlockState(pos5, ModBlocks.bedrock_portal.getDefaultState());
                }
            } else{

                entity.changeDimension(0, new TeleporterBedrock((WorldServer) world));
                entity.setPositionAndUpdate(world.getSpawnPoint().getX(), world.getSpawnPoint().getY() + 3, world.getSpawnPoint().getZ());
            }
        }
    }

    public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
        return MapColor.BLACK;
    }

    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.MODEL;
    }

}