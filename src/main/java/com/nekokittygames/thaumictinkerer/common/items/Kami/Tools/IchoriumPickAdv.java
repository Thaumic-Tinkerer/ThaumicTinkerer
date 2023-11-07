package com.nekokittygames.thaumictinkerer.common.items.Kami.Tools;

import com.nekokittygames.thaumictinkerer.common.blocks.ModBlocks;
import com.nekokittygames.thaumictinkerer.common.config.TTConfig;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCommandBlock;
import net.minecraft.block.BlockStructure;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
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
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class IchoriumPickAdv extends IchoriumPick implements IAdvancedTool
{
    public IchoriumPickAdv(String name) {

        super(name);
        this.addPropertyOverride(new ResourceLocation("ichoriumpickadv:awaken"), new IItemPropertyGetter() {
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
    public float getDestroySpeed(ItemStack stack, IBlockState state) {
        return state.getBlock().equals(Blocks.BEDROCK) ? Float.MAX_VALUE : super.getDestroySpeed(stack, state);
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return true;
    }

    @Override
    public boolean canHarvestBlock(IBlockState blockIn) {
        return blockIn.getBlock().equals(Blocks.BEDROCK) || super.canHarvestBlock(blockIn);
    }

    public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, EntityPlayer player) {
        World world = player.world;
        int y = pos.getY();

        IBlockState state = world.getBlockState(pos);
        state.getBlock().equals(ModBlocks.bedrock_portal);

        if (y <= 253 && world.getBlockState(pos).getBlock().equals(Blocks.BEDROCK) && player.dimension == TTConfig.BedRockDimensionID) {
            world.setBlockToAir(pos);
        }
        if (world.getBlockState(pos).getBlock() == Blocks.BEDROCK && ((player.dimension == 0 && y < 3) || (y > 253 && player.dimension == TTConfig.BedRockDimensionID)))
        {
            world.setBlockState(pos, ModBlocks.bedrock_portal.getDefaultState());
        }return false;
   }

    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        ItemStack itemstack = player.getHeldItem(hand);

        if (!player.canPlayerEdit(pos.offset(facing), facing, itemstack))
        {
            return EnumActionResult.FAIL;
        }
        else
        {
            IBlockState iblockstate = worldIn.getBlockState(pos);
            Block block = iblockstate.getBlock();

            if (block == ModBlocks.bedrock_portal)
            {
                IBlockState iblockstate1 = Blocks.BEDROCK.getDefaultState();

                if (!worldIn.isRemote)
                {
                    worldIn.setBlockState(pos, iblockstate1, 11);
                }

                return EnumActionResult.SUCCESS;
            }
            else
            {
                return EnumActionResult.PASS;
            }
        }
    }

    @Override
    public EnumRarity getRarity(ItemStack itemstack) {
        return EnumRarity.EPIC;
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

                    IBlockState state1 = worldIn.getBlockState(pos1);
                    float f = state1.getBlockHardness(worldIn, pos1);
                    if (f >= 0F|| (f>=-1F && player.dimension ==TTConfig.BedRockDimensionID)) {
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
        }
        else if (stack.getTagCompound() != null && stack.getTagCompound().getInteger("awaken") == 2) {
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

            boolean x = facing == EnumFacing.UP;
            boolean y = facing == EnumFacing.DOWN;
            boolean z = facing == EnumFacing.NORTH;
            boolean w = facing == EnumFacing.SOUTH;
            boolean r = facing == EnumFacing.WEST;
            for (int k = 0; k <= 9 && !stack.isEmpty(); ++k) {
                if (k == 0) {
                    continue;
                }
                BlockPos pos1;
                if (x) {
                    pos1 = pos.add(0, k, 0);
                } else if (y) {
                    pos1 = pos.add(0, -k, 0);
                } else if (z){
                    pos1 = pos.add(0, 0, -k);
                } else if (w) {
                    pos1 = pos.add(0, 0, k);
                } else if (r) {
                    pos1 = pos.add(-k, 0, 0);
                } else {
                    pos1 = pos.add(k, 0, 0);
                }

                IBlockState state1 = worldIn.getBlockState(pos1);
                float f = state1.getBlockHardness(worldIn, pos1);
                if (f >= 0F|| (f>=-1F && player.dimension ==TTConfig.BedRockDimensionID)) {
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
        return ret;
    }

    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if (player.isSneaking())     {
            NBTTagCompound nbtTagCompound = stack.getTagCompound();

            if (nbtTagCompound == null)
            {
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
                    I18n.translateToLocal("tip.awakenpick.name1"));}
        else if (stack.getTagCompound() != null && stack.getTagCompound().getInteger("awaken") == 2){
            tooltip.add(TextFormatting.BLUE +
                    I18n.translateToLocal("tip.awakenpick.name2"));}
        else{tooltip.add(TextFormatting.DARK_GREEN +
                I18n.translateToLocal("tip.awakenpick.name0"));}
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }


    @Override
    public String getType() {
        return "pick";
    }
}