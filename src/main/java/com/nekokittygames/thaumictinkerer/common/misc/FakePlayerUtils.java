package com.nekokittygames.thaumictinkerer.common.misc;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCommandBlock;
import net.minecraft.block.BlockStructure;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.world.BlockEvent;

public class FakePlayerUtils {


    public static boolean proccessRightClick(FakePlayer fakePlayer, World world, BlockPos targetPos, EnumFacing facing)
    {
        EnumHand hand=EnumHand.MAIN_HAND;
        ItemStack itemstack = fakePlayer.getHeldItem(hand);
        if(itemstack==ItemStack.EMPTY)
            return false;
        fakePlayer.markPlayerActive();
        double reachDist = fakePlayer.getEntityAttribute(EntityPlayer.REACH_DISTANCE).getAttributeValue();
        float hitX=0.5f;
        float hitY=0.5f;
        float hitZ=0.5f;
        switch (facing)
        {
            case DOWN:
                hitY-=0.5;
                break;
            case UP:
                hitY+=0.5;
                break;
            case NORTH:
                hitZ+=0.5;
                break;
            case SOUTH:
                hitZ-=0.5;
                break;
            case EAST:
                hitX+=0.5;
                break;
            case WEST:
                hitX-=0.5;
                break;
        }

        net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock event = net.minecraftforge.common.ForgeHooks
                .onRightClickBlock(fakePlayer, hand, targetPos, facing, net.minecraftforge.common.ForgeHooks.rayTraceEyeHitVec(fakePlayer, reachDist + 1));
        if (event.isCanceled()) return false;

        EnumActionResult ret = itemstack.onItemUseFirst(fakePlayer, world, targetPos, hand, facing, hitX, hitY, hitZ);
        if (ret != EnumActionResult.PASS) return false;

        boolean bypass = fakePlayer.getHeldItemMainhand().doesSneakBypassUse(world, targetPos, fakePlayer) && fakePlayer.getHeldItemOffhand().doesSneakBypassUse(world, targetPos, fakePlayer);
        EnumActionResult result = EnumActionResult.PASS;

        if (!fakePlayer.isSneaking() || bypass || event.getUseBlock() == net.minecraftforge.fml.common.eventhandler.Event.Result.ALLOW) {
            IBlockState iblockstate = world.getBlockState(targetPos);
            if(event.getUseBlock() != net.minecraftforge.fml.common.eventhandler.Event.Result.DENY)
                if (iblockstate.getBlock().onBlockActivated(world, targetPos, iblockstate, fakePlayer, hand, facing, hitX, hitY, hitZ))
                {
                    result = EnumActionResult.SUCCESS;
                }
        }
        if (itemstack.isEmpty())
        {
            return false;
        }
        if (itemstack.getItem() instanceof ItemBlock && !fakePlayer.canUseCommandBlock())
        {
            Block block = ((ItemBlock)itemstack.getItem()).getBlock();

            if (block instanceof BlockCommandBlock || block instanceof BlockStructure)
            {
                return false;
            }
        }
        if (result != EnumActionResult.SUCCESS && event.getUseItem() != net.minecraftforge.fml.common.eventhandler.Event.Result.DENY
                || result == EnumActionResult.SUCCESS && event.getUseItem() == net.minecraftforge.fml.common.eventhandler.Event.Result.ALLOW) {
            ItemStack copyBeforeUse = itemstack.copy();
            result = itemstack.onItemUse(fakePlayer, world, targetPos, hand, facing, hitX, hitY, hitZ);
            if (itemstack.isEmpty()) net.minecraftforge.event.ForgeEventFactory.onPlayerDestroyItem(fakePlayer, copyBeforeUse, hand);
        } return result==EnumActionResult.SUCCESS;
    }
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
