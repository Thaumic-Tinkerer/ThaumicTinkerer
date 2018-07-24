package com.nekokittygames.thaumictinkerer.common.tileentity;

import com.nekokittygames.thaumictinkerer.common.config.TTConfig;
import com.nekokittygames.thaumictinkerer.common.helper.Tuple4Int;
import com.nekokittygames.thaumictinkerer.common.multiblocks.MultiblockManager;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import thaumcraft.api.blocks.BlocksTC;
import thaumcraft.common.blocks.essentia.BlockJarItem;
import thaumcraft.common.config.ConfigBlocks;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class TileEntityEnchanter extends TileEntityThaumicTinkerer implements ITickable {
    private static final String TAG_ENCHANTS = "enchantsIntArray";
    private static final String TAG_LEVELS = "levelsIntArray";
    private static final String TAG_WORKING = "working";
    public static final ResourceLocation MULTIBLOCK_LOCATION=new ResourceLocation("thaumictinkerer","osmotic_enchanter");

    public List<Integer> enchantments = new ArrayList();
    public List<Integer> levels = new ArrayList();


    public boolean working = false;

    // old stytle multiblock
    private List<Tuple4Int> pillars = new ArrayList();

    public void clearEnchants() {
        enchantments.clear();
        levels.clear();
    }

    public void appendEnchant(int enchant) {
        enchantments.add(enchant);
    }

    public void appendLevel(int level) {
        levels.add(level);
    }

    public void removeEnchant(int index) {
        enchantments.remove(index);
    }

    public void removeLevel(int index) {
        levels.remove(index);
    }

    public void setEnchant(int index, int enchant) {
        enchantments.set(index, enchant);
    }

    public void setLevel(int index, int level) {
        levels.set(index, level);
    }


    private ItemStackHandler inventory= new ItemStackHandler(1)
    {
        @Override
        protected void onContentsChanged(int slot)
        {
            super.onContentsChanged(slot);
            sendUpdates();
        }

        public boolean isItemValidForSlot(int index, ItemStack stack)
        {
            return TileEntityEnchanter.this.isItemValidForSlot(index, stack);
        }

        @Nonnull
        @Override
        public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
            if(!isItemValidForSlot(slot,stack))
                return stack;
            return super.insertItem(slot, stack, simulate);
        }
    };

    public boolean isItemValidForSlot(int index, ItemStack stack)
    {
        Item item=stack.getItem();
        return item.isEnchantable(stack);
    }

    public ItemStackHandler getInventory() {
        return inventory;
    }


    @Override
    public void writeExtraNBT(NBTTagCompound nbttagcompound) {
        nbttagcompound.setTag("inventory",inventory.serializeNBT());
    }

    @Override
    public void readExtraNBT(NBTTagCompound nbttagcompound) {
        inventory.deserializeNBT(nbttagcompound.getCompoundTag("inventory"));
    }





    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
            return capability== CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ||super.hasCapability(capability, facing);
    }


    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if(capability==CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
        {
            return (T)inventory;
        }
        else
        {
            return super.getCapability(capability,facing);
        }
    }

    @Override
    public void update() {
        if(inventory.getStackInSlot(0)==ItemStack.EMPTY)
        {
            clearEnchants();
        }
        if(working)
        {
            ItemStack tool=inventory.getStackInSlot(0);
            if(tool==ItemStack.EMPTY)
            {
                working=false;
                return;
            }

            checkStructure();

            if (!working) // Pillar check
                return;
        }
    }

    private void checkStructure() {
        if(TTConfig.ClassicEnchanter)
        {
            checkPillars();
        }
        else
        {
            if(!MultiblockManager.checkMultiblockCombined(world,getPos(),MULTIBLOCK_LOCATION))
                working=false;
        }
    }

    public boolean checkPillars() {
        if (pillars.isEmpty()) {
            if (assignPillars()) {
                working = false;
                return false;
            }
            return true;

        }

        for (int i = 0; i < pillars.size(); i++) {
            Tuple4Int pillar = pillars.get(i);
            int pillarHeight = findPillar(new BlockPos(pillar.i1, pillar.i2, pillar.i3));
            if (pillarHeight == -1) {
                pillars.clear();
                return checkPillars();
            } else if (pillarHeight != pillar.i4)
                pillar.i4 = pillarHeight;
        }

        return true;
    }

    public boolean assignPillars() {
        int y = pos.getY();
        for (int x = pos.getX() - 4; x <= pos.getX() + 4; x++)
            for (int z = pos.getZ() - 4; z <= pos.getZ() + 4; z++) {
                int height = findPillar(new BlockPos(x,y,z));
                if (height != -1)
                    pillars.add(new Tuple4Int(x, y, z, height));

                if (pillars.size() == 6)
                    return false;
            }

        pillars.clear();
        return true;
    }

    public int findPillar(BlockPos pillarPos) {
        int obsidianFound = 0;
        for (int i = 0; true; i++) {
            if (pillarPos.getY() + i >= 256)
                return -1;

            IBlockState id = world.getBlockState(pillarPos.up(i));

            if (id.getBlock()== Blocks.OBSIDIAN) {
                ++obsidianFound;
                continue;
            }
            if (BlocksTC.nitor.containsValue(id.getBlock()) ) {
                if (obsidianFound >= 2 && obsidianFound < 13)
                    return pillarPos.getY() + i;
                return -1;
            }

            return -1;
        }
    }
}
