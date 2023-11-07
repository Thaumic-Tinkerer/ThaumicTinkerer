package com.nekokittygames.thaumictinkerer.common.items.Kami.Tools;


import net.minecraft.block.Block;
import net.minecraft.block.BlockCommandBlock;
import net.minecraft.block.BlockStructure;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.common.lib.utils.BlockUtils;
import thaumcraft.common.lib.utils.Utils;

import javax.annotation.Nullable;
import java.util.List;

public class IchoriumAxeAdv extends IchoriumAxe implements IAdvancedTool{

    public IchoriumAxeAdv(String name) {

        super(name);
        this.addPropertyOverride(new ResourceLocation("ichoriumaxeadv:awaken"), new IItemPropertyGetter() {
            @Override
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
                if (stack.getTagCompound() != null && stack.getTagCompound().getInteger("awaken") != 0) {
                    if (stack.getTagCompound() != null && stack.getTagCompound().getInteger("awaken") == 2) {
                        return 2.0F;
                    }
                    return 1.0F;
                }
                return 0.0F;
            }
        });

        
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return true;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving) {
        boolean ret = super.onBlockDestroyed(stack, worldIn, state, pos, entityLiving);
        if (stack.getTagCompound() != null && stack.getTagCompound().getInteger("awaken") == 1) {
            if (!(entityLiving instanceof EntityPlayer) || worldIn.isRemote) {
                return ret;
            }

            EntityPlayer player = (EntityPlayer) entityLiving;
            EnumFacing facing = entityLiving.getHorizontalFacing();

            if (entityLiving.rotationPitch < -45.0F) {
                facing = EnumFacing.UP;
            } else if (entityLiving.rotationPitch > 45.0F) {
                facing = EnumFacing.DOWN;
            }

            boolean yAxis = facing.getAxis() == EnumFacing.Axis.Y;
            boolean xAxis = facing.getAxis() == EnumFacing.Axis.X;

            for (int i = -2; i <= 2; ++i) {
                for (int j = -2; j <= 2 && !stack.isEmpty(); ++j) {
                    if (i == 0 && j == 0) {
                        continue;
                    }

                    BlockPos pos1;
                    if (yAxis) {
                        pos1 = pos.add(i, 0, j);
                    } else if (xAxis) {
                        pos1 = pos.add(0, i, j);
                    } else {
                        pos1 = pos.add(i, j, 0);
                    }

                    //:Replicate logic of PlayerInteractionManager.tryHarvestBlock(pos1)
                    IBlockState state1 = worldIn.getBlockState(pos1);
                    float f = state1.getBlockHardness(worldIn, pos1);
                    if (f >= 0F && (state1.getMaterial().equals(Material.WOOD) || state1.getMaterial().equals(Material.CORAL) || state1.getMaterial().equals(Material.LEAVES) || state1.getMaterial().equals(Material.PLANTS))) {
                        BlockEvent.BreakEvent event = new BlockEvent.BreakEvent(worldIn, pos1, state1, player);
                        MinecraftForge.EVENT_BUS.post(event);
                        if (!event.isCanceled()) {
                            Block block = state1.getBlock();
                            if ((block instanceof BlockCommandBlock || block instanceof BlockStructure) && !player.canUseCommandBlock()) {
                                worldIn.notifyBlockUpdate(pos1, state1, state1, 3);
                                continue;
                            }
                            TileEntity tileentity = worldIn.getTileEntity(pos1);
                            if (tileentity != null) {
                                Packet<?> pkt = tileentity.getUpdatePacket();
                                if (pkt != null) {
                                    ((EntityPlayerMP) player).connection.sendPacket(pkt);
                                }
                            }

                            boolean canHarvest = block.canHarvestBlock(worldIn, pos1, player);
                            boolean destroyed = block.removedByPlayer(state1, worldIn, pos1, player, canHarvest);
                            if (destroyed) {
                                block.breakBlock(worldIn, pos1, state1);
                            }
                            if (canHarvest && destroyed) {
                                block.harvestBlock(worldIn, player, pos1, state1, tileentity, stack);
                                stack.damageItem(1, player);
                            }
                        }
                    }
                }
            }
        }return ret;
    }
    public boolean onBlockStartBreak(ItemStack stack, BlockPos pos, EntityPlayer player){
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        World world = player.getEntityWorld();
        if (stack.getTagCompound() != null && stack.getTagCompound().getInteger("awaken") == 2) {
            IBlockState block = world.getBlockState(pos);
            if (Utils.isWoodLog(world, pos)) {
                if(block.getBlock() != Blocks.AIR) {
                    BlockUtils.breakFurthestBlock(world, pos, block, player);
                    block = world.getBlockState(pos);
                    while(BlockUtils.breakFurthestBlock(world, pos, block, player)){
                        BlockUtils.breakFurthestBlock(world, pos, block, player);
                    }
                }
                List<EntityItem> items = world.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(x - 5, y - 1, z - 5, x + 5, y + 64, z + 5));
                for (EntityItem item : items) {
                    item.setPosition(x + 0.5, y + 0.5, z + 0.5);
                    item.ticksExisted += 20;
                }
            }
        }
            return false;
    }

    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if (player.isSneaking()) {
            NBTTagCompound nbtTagCompound = stack.getTagCompound();

            if (nbtTagCompound == null) {
                nbtTagCompound = new NBTTagCompound();
                stack.setTagCompound(nbtTagCompound);
            }

            nbtTagCompound.setInteger("awaken", (nbtTagCompound.getInteger("awaken") + 1) % 3);
            return new ActionResult<>(EnumActionResult.SUCCESS, stack);
        }
        return new ActionResult<>(EnumActionResult.PASS, stack);
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        if (stack.getTagCompound() != null && stack.getTagCompound().getInteger("awaken") == 1){
            tooltip.add(TextFormatting.RED +
                    I18n.translateToLocal("tip.awakenaxe.name1"));}
        else if (stack.getTagCompound() != null && stack.getTagCompound().getInteger("awaken") == 2){
            tooltip.add(TextFormatting.BLUE +
                    I18n.translateToLocal("tip.awakenaxe.name2"));}
        else{tooltip.add(TextFormatting.DARK_GREEN +
                I18n.translateToLocal("tip.awakenaxe.name0"));}
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public EnumRarity getRarity(ItemStack itemstack) {
        return EnumRarity.EPIC;
    }


    @Override
    public String getType() {
        return "axe";
    }
}