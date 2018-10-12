package com.nekokittygames.thaumictinkerer.common.misc;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCommandBlock;
import net.minecraft.block.BlockStructure;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.world.BlockEvent;

public class FakePlayerUtils {


    public static boolean tryHarvestBlock(FakePlayer fakePlayer, World world, BlockPos targetPos) {
        Block targetBlock = world.getBlockState(targetPos).getBlock();
        ItemStack itemInUse = fakePlayer.getHeldItemMainhand();
        boolean preCancel = false;
        IBlockState state = world.getBlockState(targetPos);
        BlockEvent.BreakEvent event = new BlockEvent.BreakEvent(world, targetPos, state, fakePlayer);
        event.setCanceled(preCancel);
        MinecraftForge.EVENT_BUS.post(event);
        if (event.isCanceled()) {
            return false;
        } else {
            TileEntity tileentity = world.getTileEntity(targetPos);
            if ((targetBlock instanceof BlockCommandBlock || targetBlock instanceof BlockStructure) && !fakePlayer.canUseCommandBlock()) {
                world.notifyBlockUpdate(targetPos, state, state, 3);

                return false;
            }
            if (!itemInUse.isEmpty() && itemInUse.getItem().onBlockStartBreak(itemInUse, targetPos, fakePlayer)) {

                return false;
            }
            world.playEvent(fakePlayer, 2001, targetPos, Block.getStateId(state));
            boolean flag1 = false;
            ItemStack itemstack1 = fakePlayer.getHeldItemMainhand();
            ItemStack itemstack2 = itemstack1.isEmpty() ? ItemStack.EMPTY : itemstack1.copy();
            boolean flag = state.getBlock().canHarvestBlock(world, targetPos, fakePlayer);
            if (!itemstack1.isEmpty()) {
                itemstack1.onBlockDestroyed(world, state, targetPos, fakePlayer);
                if (itemstack1.isEmpty())
                    net.minecraftforge.event.ForgeEventFactory.onPlayerDestroyItem(fakePlayer, itemstack2, EnumHand.MAIN_HAND);

            }
            flag1 = state.getBlock().removedByPlayer(state, world, targetPos, fakePlayer, flag);
            if (flag1)
                state.getBlock().onBlockDestroyedByPlayer(world, targetPos, state);
            if (flag1 && flag) {
                state.getBlock().harvestBlock(world, fakePlayer, targetPos, state, tileentity, itemstack2);
            }
            return flag1;
        }
    }
}
