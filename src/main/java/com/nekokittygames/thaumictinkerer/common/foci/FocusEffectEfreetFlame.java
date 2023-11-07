package com.nekokittygames.thaumictinkerer.common.foci;

import com.google.common.collect.Lists;
import com.nekokittygames.thaumictinkerer.ThaumicTinkerer;
import com.nekokittygames.thaumictinkerer.api.ThaumicTinkererAPI;
import com.nekokittygames.thaumictinkerer.common.packets.PacketHandler;
import com.nekokittygames.thaumictinkerer.common.utils.ItemNBTHelper;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.BlockSnapshot;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.casters.FocusEffect;
import thaumcraft.api.casters.ICaster;
import thaumcraft.api.casters.Trajectory;
import thaumcraft.common.items.casters.ItemCaster;
import thaumcraft.common.lib.events.ServerEvents;
import thaumcraft.common.lib.utils.BlockUtils;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("deprecation")
public class FocusEffectEfreetFlame extends FocusEffect {


@Override
    public boolean execute(RayTraceResult target, Trajectory trajectory, float finalPower, int num) {
        if (target.typeOfHit == RayTraceResult.Type.BLOCK) {
            ItemStack casterStack = ItemStack.EMPTY;
            getPackage().getCaster().getHeldItemMainhand();
            if (getPackage().getCaster().getHeldItemMainhand().getItem() instanceof ItemCaster) {
                casterStack = getPackage().getCaster().getHeldItemMainhand();
            } else {
                getPackage().getCaster().getHeldItemOffhand();
                if (getPackage().getCaster().getHeldItemOffhand().getItem() instanceof ItemCaster) {
                    casterStack = getPackage().getCaster().getHeldItemOffhand();
                }
            }

            BlockPos pos = target.getBlockPos();

            if (casterStack.isEmpty())
                return false;
            if(ReplaceBlock(getPackage().world, (EntityPlayerMP) getPackage().getCaster(),pos,target.sideHit,EnumHand.MAIN_HAND,pos.getX(), pos.getY(), pos.getZ())==EnumActionResult.SUCCESS)
            return true;
        }
        return false;
    }

    private static EnumActionResult ReplaceBlock(World world, EntityPlayerMP entityPlayerMP, BlockPos pos, EnumFacing side, EnumHand hand, float hitX, float hitY, float hitZ)
    {
        IBlockState state = world.getBlockState(pos);
            ItemStack toSmelt = state.getBlock().getPickBlock(state, new RayTraceResult(RayTraceResult.Type.BLOCK, new Vec3d(hitX, hitY, hitZ), side, pos), world, pos, entityPlayerMP);
            ItemStack result = FurnaceRecipes.instance().getSmeltingResult(toSmelt);
            if(result.getItem() instanceof ItemBlock) {
                IBlockState toPlace = ((ItemBlock) result.getItem()).getBlock().getStateForPlacement(world, pos, side, hitX, hitY, hitZ, result.getItemDamage(), entityPlayerMP, hand);
                if (world.setBlockState(pos, toPlace, 3)) {
                    world.playSound(entityPlayerMP, pos, new SoundEvent(new ResourceLocation("block.fire.ignite")), SoundCategory.BLOCKS, 1, 1);
                    if (!world.isRemote) {
                        return EnumActionResult.FAIL;
                    }
                    return EnumActionResult.SUCCESS;
                }
            }
        return EnumActionResult.PASS;
    }

    @Override
    public void renderParticleFX(World world, double v, double v1, double v2, double v3, double v4, double v5) {
        // Empty
    }

    @Override
    public int getComplexity() {
        return 5;
    }

    @Override
    public Aspect getAspect() {
        return Aspect.FIRE;
    }

    @Override
    public String getKey() {
        return "thaumictinkerer.efreetflame";
    }

    @Override
    public String getResearch() {
        return "TT_EFREET_FLAME";
    }
}
