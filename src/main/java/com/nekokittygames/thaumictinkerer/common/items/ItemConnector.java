package com.nekokittygames.thaumictinkerer.common.items;

import com.nekokittygames.thaumictinkerer.common.libs.LibItemNames;
import com.nekokittygames.thaumictinkerer.common.tileentity.transvector.TileEntityTransvector;
import com.nekokittygames.thaumictinkerer.common.utils.ItemNBTHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class ItemConnector extends TTItem {
    private static final String TAG_X_TARGET = "xt";
    private static final String TAG_Y_TARGET = "yt";
    private static final String TAG_Z_TARGET = "zt";

    private static final String TAG_X_SOURCE = "xs";
    private static final String TAG_Y_SOURCE = "ys";
    private static final String TAG_Z_SOURCE = "zs";

    public ItemConnector() {
        super(LibItemNames.CONNECTOR);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

        TileEntity tile=worldIn.getTileEntity(pos);
        ItemStack stack=player.getHeldItem(hand);
        BlockPos target=getTarget(stack);
        if(target==null)
        {
            if((tile instanceof TileEntityTransvector))
            {
                setTarget(stack,pos);
                if(!worldIn.isRemote) {
                    worldIn.playSound(player, pos, SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.MASTER, 0.8f, 1f);

                    player.sendStatusMessage(new TextComponentTranslation("ttmisc.connector.set"),true);
                }
                return EnumActionResult.SUCCESS;

            }
        }
        else
        {
            TileEntity te2=worldIn.getTileEntity(target);
            if(!(te2 instanceof TileEntityTransvector))
            {
                clearTarget(stack);
                if(!worldIn.isRemote)
                    player.sendStatusMessage(new TextComponentTranslation("ttmisc.connector.notpresent"),true);
                return EnumActionResult.FAIL;
            }
            TileEntityTransvector trans= (TileEntityTransvector) te2;
            if(tile instanceof TileEntityTransvector)
            {
                if(!worldIn.isRemote)
                    player.sendStatusMessage(new TextComponentTranslation("ttmisc.connector.interffail"),true);
                return EnumActionResult.FAIL;
            }
            if(!trans.setTilePos(pos))
            {
                if(!worldIn.isRemote)
                    player.sendStatusMessage(new TextComponentTranslation("ttmisc.connector.toofar"),true);
                return EnumActionResult.FAIL;
            }
            clearTarget(stack);


            if(!worldIn.isRemote)
            {
                player.sendStatusMessage(new TextComponentTranslation("ttmisc.connector.complete"),true);
                worldIn.playSound(player,pos, SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.MASTER,0.8f,1f);
            }

        }

        return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
    }


    public static BlockPos getTarget(ItemStack stack)
    {
        NBTTagCompound cmp=ItemNBTHelper.getItemTag(stack);
        if(cmp.hasKey(TAG_X_TARGET))
        {
            return new BlockPos(cmp.getInteger(TAG_X_TARGET),cmp.getInteger(TAG_Y_TARGET),cmp.getInteger(TAG_Z_TARGET));
        }
        else
            return null;
    }
    public static void setTarget(ItemStack stack,BlockPos pos)
    {
        NBTTagCompound cmp=ItemNBTHelper.getItemTag(stack);
        cmp.setInteger(TAG_X_TARGET,pos.getX());
        cmp.setInteger(TAG_Y_TARGET,pos.getY());
        cmp.setInteger(TAG_Z_TARGET,pos.getZ());
    }

    public static  void clearTarget(ItemStack stack)
    {
        NBTTagCompound cmp=ItemNBTHelper.getItemTag(stack);
        cmp.removeTag(TAG_X_TARGET);
        cmp.removeTag(TAG_Y_TARGET);
        cmp.removeTag(TAG_Z_TARGET);
    }
}
