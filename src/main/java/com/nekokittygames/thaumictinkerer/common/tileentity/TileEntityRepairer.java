package com.nekokittygames.thaumictinkerer.common.tileentity;

import com.nekokittygames.thaumictinkerer.common.blocks.BlockRepairer;
import com.nekokittygames.thaumictinkerer.common.compat.TiConCompat;
import com.nekokittygames.thaumictinkerer.common.config.TTConfig;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;
import thaumcraft.api.aspects.IEssentiaTransport;
import thaumcraft.client.fx.FXDispatcher;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TileEntityRepairer extends TileEntityThaumicTinkerer implements ITickable, IAspectContainer, IEssentiaTransport {

    private ItemStackHandler inventory = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            sendUpdates();
        }

        public boolean isItemValidForSlot(int index, ItemStack stack) {
            return TileEntityRepairer.this.isItemValidForSlot(index, stack);
        }

        @Nonnull
        @Override
        public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
            if (!isItemValidForSlot(slot, stack))
                return stack;
            return super.insertItem(slot, stack, simulate);
        }
    };
    private int ticksExisted;
    private int dmgLastTick = 0;
    private boolean tookLastTick = true;

    public int getTicksExisted() {
        return ticksExisted;
    }

    public void setTicksExisted(int ticksExisted) {
        this.ticksExisted = ticksExisted;
    }

    public boolean isItemValidForSlot(int index, ItemStack stack) {
        Item item = stack.getItem();
        if (Loader.isModLoaded("tconstruct") && TTConfig.TiConCompatibility)
            return TiConCompat.isRepairableTiCon(stack) || item.isRepairable();
        return item.isRepairable();
    }

    public ItemStackHandler getInventory() {
        return inventory;
    }

    @Override
    public void writeExtraNBT(NBTTagCompound nbttagcompound) {
        nbttagcompound.setTag("inventory", inventory.serializeNBT());
    }

    @Override
    public void readExtraNBT(NBTTagCompound nbttagcompound) {
        inventory.deserializeNBT(nbttagcompound.getCompoundTag("inventory"));
    }

    @Override
    public boolean respondsToPulses() {
        return false;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
    }


    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return (T) inventory;
        } else {
            return super.getCapability(capability, facing);
        }
    }


    @Override
    public void update() {
        if (++ticksExisted % 10 == 0) {
            ItemStack stack = inventory.getStackInSlot(0);
            if (Loader.isModLoaded("tconstruct") && TTConfig.TiConCompatibility) {
                if (stack != ItemStack.EMPTY) {
                    if (TiConCompat.isTiConTool(stack) && TTConfig.TiConCompatibility) {
                        int damage = TiConCompat.getDamage(stack);
                        if (damage > 0) {
                            int essentia = drawEssentia();
                            TiConCompat.fixDamage(stack, essentia);
                            sendUpdates();
                            if (dmgLastTick != 0 && dmgLastTick != damage) {
                                FXDispatcher.INSTANCE.sparkle((float) (pos.getX() + 0.25 + Math.random() / 2F), (float) (pos.getY() + 1 + Math.random() / 2F), (float) (pos.getZ() + 0.25 + Math.random() / 2F), 1.0f, 1.0f, 0.0f);
                                tookLastTick = true;
                            } else
                                tookLastTick = false;

                        } else
                            tookLastTick = false;

                        dmgLastTick = stack == ItemStack.EMPTY ? 0 : TiConCompat.getDamage(stack);
                        return;
                    } else
                        tookLastTick = false;
                } else
                    tookLastTick = false;
            }

            int damage = stack.getItemDamage();
            if (damage > 0) {
                int essentia = drawEssentia();
                stack.setItemDamage(Math.max(0, stack.getItemDamage() - essentia));
                sendUpdates();
                if (dmgLastTick != 0 && dmgLastTick != damage) {
                    FXDispatcher.INSTANCE.sparkle((float) (pos.getX() + 0.25 + Math.random() / 2F), (float) (pos.getY() + 1 + Math.random() / 2F), (float) (pos.getZ() + 0.25 + Math.random() / 2F), 1.0f, 1.0f, 0.0f);
                    tookLastTick = true;
                } else
                    tookLastTick = false;

            } else
                tookLastTick = false;

            dmgLastTick = stack == ItemStack.EMPTY ? 0 : stack.getItemDamage();

        }


    }


    int drawEssentia() {
        EnumFacing facing = world.getBlockState(pos).getValue(BlockRepairer.FACING);
        TileEntity te = ThaumcraftApiHelper.getConnectableTile(this.world, this.pos, facing);
        if (te != null) {
            IEssentiaTransport ic = (IEssentiaTransport) te;
            if (!ic.canOutputTo(facing.getOpposite()))
                return 0;

            return ic.takeEssentia(Aspect.TOOL, 8, facing.getOpposite());
        }
        return 0;
    }

    @Override
    public AspectList getAspects() {
        ItemStack stack = inventory.getStackInSlot(0);
        if (stack == ItemStack.EMPTY)
            return null;
        int damage = stack.getItemDamage();
        if (Loader.isModLoaded("tconstruct") && TTConfig.TiConCompatibility)
            damage = TiConCompat.getDamage(stack);
        return new AspectList().add(Aspect.ENTROPY, damage);
    }

    @Override
    public void setAspects(AspectList aspectList) {

    }

    @Override
    public boolean doesContainerAccept(Aspect aspect) {
        return false;
    }

    @Override
    public int addToContainer(Aspect aspect, int i) {
        return 0;
    }

    @Override
    public boolean takeFromContainer(Aspect aspect, int i) {
        return false;
    }

    @Override
    public boolean takeFromContainer(AspectList aspectList) {
        return false;
    }

    @Override
    public boolean doesContainerContainAmount(Aspect aspect, int i) {
        return false;
    }

    @Override
    public boolean doesContainerContain(AspectList aspectList) {
        return false;
    }

    @Override
    public int containerContains(Aspect aspect) {
        return 0;
    }

    @Override
    public boolean isConnectable(EnumFacing enumFacing) {
        return enumFacing == world.getBlockState(pos).getValue(BlockRepairer.FACING);
    }

    @Override
    public boolean canInputFrom(EnumFacing enumFacing) {
        return false;
    }

    @Override
    public boolean canOutputTo(EnumFacing enumFacing) {
        return isConnectable(enumFacing);
    }

    @Override
    public void setSuction(Aspect aspect, int i) {

    }

    @Override
    public Aspect getSuctionType(EnumFacing enumFacing) {
        return isConnectable(enumFacing) ? Aspect.TOOL : null;
    }

    @Override
    public int getSuctionAmount(EnumFacing enumFacing) {
        return isConnectable(enumFacing) ? 128 : 0;
    }

    @Override
    public int takeEssentia(Aspect aspect, int i, EnumFacing enumFacing) {
        return 0;
    }

    @Override
    public int addEssentia(Aspect aspect, int i, EnumFacing enumFacing) {
        return 0;
    }

    @Override
    public Aspect getEssentiaType(EnumFacing enumFacing) {
        return null;
    }

    @Override
    public int getEssentiaAmount(EnumFacing enumFacing) {
        return 0;
    }

    @Override
    public int getMinimumSuction() {
        return 0;
    }

    public boolean isTookLastTick() {
        return tookLastTick;
    }

    public void setTookLastTick(boolean tookLastTick) {
        this.tookLastTick = tookLastTick;
    }
}
