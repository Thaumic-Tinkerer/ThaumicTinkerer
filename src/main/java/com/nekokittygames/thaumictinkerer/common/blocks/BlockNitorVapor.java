package com.nekokittygames.thaumictinkerer.common.blocks;

import com.nekokittygames.thaumictinkerer.common.items.Kami.KamiArmor;
import com.nekokittygames.thaumictinkerer.common.items.ModItems;
import com.nekokittygames.thaumictinkerer.common.libs.LibBlockNames;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.client.fx.FXDispatcher;

import java.util.List;
import java.util.Random;

public class BlockNitorVapor extends BlockGas {

    public BlockNitorVapor() {
        super(LibBlockNames.ENERGETIC_NITOR_VAPOR);
        this.setTickRandomly(true);
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
        //return super.getBlockFaceShape(worldIn, state, pos, face);
    }


//@Override
    //public int tickRate(World world) {
    //    return world.provider.getDimensionType() == DimensionType.OVERWORLD ? 60 : 20;
    //}

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
        if (rand.nextFloat() < 0.03F)
            FXDispatcher.INSTANCE.sparkle(pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, 1F, 4, rand.nextFloat() / 2);
    }


    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random random) {


        if (!world.isRemote) {
            List<EntityPlayer> players = world.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(pos.getX() - 1, pos.getY() - 1, pos.getZ() - 1, pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1));

            if (players.isEmpty()) world.setBlockToAir(pos);
            else if (players.stream().noneMatch(p -> p.inventory.hasItemStack(new ItemStack(ModItems.energetic_nitor)) || (p.getItemStackFromSlot(EntityEquipmentSlot.LEGS).getItem() instanceof KamiArmor) && p.getItemStackFromSlot(EntityEquipmentSlot.LEGS).getItemDamage() != 1))
                world.setBlockToAir(pos);

            //world.scheduleBlockUpdate(pos, this, tickRate(world), 0);

        }
    }

    @Override
    protected boolean isInCreativeTab() {
        return false;
    }

    @Override
    public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
        return 15;
    }

    @Override
    public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
        if (!world.isRemote)
            world.scheduleBlockUpdate(pos, this, tickRate(world), 0);
    }
}
