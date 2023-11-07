package com.nekokittygames.thaumictinkerer.common.items.Kami.ichorpouch;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import com.nekokittygames.thaumictinkerer.ThaumicTinkerer;
import com.nekokittygames.thaumictinkerer.common.items.TTItem;
import com.nekokittygames.thaumictinkerer.common.libs.LibItemNames;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import thaumcraft.common.items.casters.ItemFocusPouch;

public class IchorPouch extends ItemFocusPouch implements IBauble {
    public IchorPouch() {
        super();
        setTranslationKey(LibItemNames.ICHOR_POUCH);
        setCreativeTab(ThaumicTinkerer.getTab());
    }

    @Override
    public EnumRarity getRarity(ItemStack par1ItemStack) {
        return EnumRarity.EPIC;
    }

    @Override
    public BaubleType getBaubleType(ItemStack itemstack) {
        return BaubleType.BELT;
    }

    @Override
    public void onWornTick(ItemStack itemstack, EntityLivingBase player) {

    }

    @Override
    public void onEquipped(ItemStack itemstack, EntityLivingBase player) {

    }

    @Override
    public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {

    }

    @Override
    public boolean canEquip(ItemStack itemstack, EntityLivingBase player) {
        return true;
    }

    @Override
    public boolean canUnequip(ItemStack itemstack, EntityLivingBase player) {
        return true;
    }

    public NonNullList<ItemStack> getInventory(ItemStack item) {
        NonNullList<ItemStack> stackList = NonNullList.withSize(117, ItemStack.EMPTY);
        if (item.hasTagCompound()) {
            ItemStackHelper.loadAllItems(item.getTagCompound(), stackList);
        }
        return stackList;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        if (!worldIn.isRemote && handIn == EnumHand.MAIN_HAND) {
            playerIn.openGui(ThaumicTinkerer.instance, 0, worldIn, 0, 0, 0);
        }
        return new ActionResult(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
    }

    public void setInventory(ItemStack item, NonNullList<ItemStack> stackList) {
        if (item.getTagCompound() == null) item.setTagCompound(new NBTTagCompound());
        ItemStackHelper.saveAllItems(item.getTagCompound(), stackList);
    }
}
