package com.nekokittygames.thaumictinkerer.common.items;

import com.nekokittygames.thaumictinkerer.common.blocks.ModBlocks;
import com.nekokittygames.thaumictinkerer.common.libs.LibItemNames;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemEnergeticNitor extends TTItem {
    public ItemEnergeticNitor() {
        super(LibItemNames.ENERGETIC_NITOR);
        this.setMaxStackSize(1);
    }

    public static void setBlock(BlockPos pos, World world) {
        if ((world.isAirBlock(pos) || world.getBlockState(pos).equals(ModBlocks.nitor_vapor)) && !world.isRemote) {
            world.setBlockState(pos, ModBlocks.nitor_vapor.getDefaultState());
        }
    }

    @Override
    public void onUpdate(ItemStack par1ItemStack, World world, Entity entity, int par4, boolean par5) {
        BlockPos pos = new BlockPos(entity.posX, entity.posY, entity.posZ).up();
        setBlock(pos, world);
    }
}
