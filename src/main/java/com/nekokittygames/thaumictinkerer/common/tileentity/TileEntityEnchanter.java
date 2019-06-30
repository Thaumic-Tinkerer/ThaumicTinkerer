package com.nekokittygames.thaumictinkerer.common.tileentity;

import com.nekokittygames.thaumictinkerer.ThaumicTinkerer;
import com.nekokittygames.thaumictinkerer.common.blocks.ModBlocks;
import com.nekokittygames.thaumictinkerer.common.config.TTConfig;
import com.nekokittygames.thaumictinkerer.common.helper.Tuple4Int;
import com.nekokittygames.thaumictinkerer.common.multiblocks.MultiblockManager;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.apache.commons.lang3.ArrayUtils;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.blocks.BlocksTC;
import thaumcraft.api.items.ItemsTC;
import thaumcraft.client.fx.FXDispatcher;
import thaumcraft.common.items.resources.ItemCrystalEssence;
import thaumcraft.common.lib.SoundsTC;
import thaumcraft.common.lib.utils.InventoryUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

public class TileEntityEnchanter extends TileEntityThaumicTinkerer implements ITickable {
    private static final ResourceLocation MULTIBLOCK_LOCATION = new ResourceLocation("thaumictinkerer", "osmotic_enchanter");
    private static final String TAG_ENCHANTS = "enchantsIntArray";
    private static final String TAG_LEVELS = "levelsIntArray";
    private static final String TAG_CACHED_ENCHANTS = "cachedEnchants";
    private static final String TAG_WORKING = "working";
    private static final String TAG_PROGRESS = "progress";
    private List<Integer> enchantments = new ArrayList<>();
    private List<Integer> levels = new ArrayList<>();

    private List<Integer> cachedEnchantments = new ArrayList<>();
    private int progress;
    private int cooldown;
    private boolean working = false;
    // old stytle multiblock
    private List<Tuple4Int> pillars = new ArrayList<>();
    private Vec3d[] points = new Vec3d[]{
            new Vec3d(-1.2, 2.15, -1.2),    // 0
            new Vec3d(-2.2, 2.15, 0.5),    // 1
            new Vec3d(-1.2, 2.15, 2.2),   // 2
            new Vec3d(0.5, 2.15, 3.2),   // 3
            new Vec3d(2.2, 2.15, 2.2),  // 4
            new Vec3d(3.2, 2.15, 0.5),   // 5
            new Vec3d(2.2, 2.15, -1.2),   // 6
            new Vec3d(0.5, 2.15, -2.2)     // 7
    };
    private Color c = new Color(MapColor.GOLD.colorValue);
    private ItemStackHandler inventory = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            TileEntityEnchanter.this.onInventoryChanged(getStackInSlot(slot));
            sendUpdates();

        }

        boolean isItemValidForSlot(int index, ItemStack stack) {
            return TileEntityEnchanter.this.isItemValidForSlot(index, stack);
        }

        @Nonnull
        @Override
        public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
            if (!isItemValidForSlot(slot, stack))
                return stack;
            return super.insertItem(slot, stack, simulate);
        }
    };

    private static boolean canApply(ItemStack itemStack, Enchantment enchantment, List<Enchantment> currentEnchants) {
        return canApply(itemStack, enchantment, currentEnchants, true);
    }

    private static boolean canApply(ItemStack itemStack, Enchantment enchantment, List<Enchantment> currentEnchants, boolean checkConflicts) {
        if (ArrayUtils.contains(TTConfig.blacklistedEnchants, Enchantment.getEnchantmentID(enchantment)))
            return false;
        if (!enchantment.canApply(itemStack) || !Objects.requireNonNull(enchantment.type).canEnchantItem(itemStack.getItem()) || currentEnchants.contains(enchantment))
            return false;
        if (EnchantmentHelper.getEnchantments(itemStack).keySet().contains(enchantment))
            return false;
        if (checkConflicts) {
            for (Enchantment curEnchant : currentEnchants)
                if (!curEnchant.isCompatibleWith(enchantment))
                    return false;
        }
        return true;
    }

    public List<Integer> getEnchantments() {
        return enchantments;
    }

    public List<Integer> getLevels() {
        return levels;
    }

    public List<Integer> getCachedEnchantments() {
        return cachedEnchantments;
    }

    public boolean isWorking() {
        return working;
    }

    public void setWorking(boolean working) {
        this.working = working;
        sendUpdates();
    }

    public List<Tuple4Int> getPillars() {
        return pillars;
    }

    public void clearEnchants() {
        enchantments.clear();
        levels.clear();

    }

    public void appendEnchant(int enchant) {
        enchantments.add(enchant);
        refreshEnchants();
        sendUpdates();
    }

    public void appendLevel(int level) {
        levels.add(level);
    }

    public void removeEnchant(int index) {
        enchantments.remove(index);
        refreshEnchants();
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

    public int getCooldown() {
        return cooldown;
    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }

    private void onInventoryChanged(ItemStack stackInSlot) {

        refreshEnchants();
    }

    public void BreakPillars()
    {
        if(world.getBlockState(pos.north(3)).getBlock()==ModBlocks.enchantment_pillar)
            world.setBlockState(pos.north(3),BlocksTC.stoneArcane.getDefaultState());
        if(world.getBlockState(pos.north(3).up(1)).getBlock()==ModBlocks.enchantment_pillar)
            world.setBlockState(pos.north(3).up(1),BlocksTC.stoneArcane.getDefaultState());
        world.setBlockState(pos.north(3).up(2),BlocksTC.nitor.get(EnumDyeColor.YELLOW).getDefaultState());

        if(world.getBlockState(pos.east(3)).getBlock()==ModBlocks.enchantment_pillar)
            world.setBlockState(pos.east(3),BlocksTC.stoneArcane.getDefaultState());
        if(world.getBlockState(pos.east(3).up(1)).getBlock()==ModBlocks.enchantment_pillar)
            world.setBlockState(pos.east(3).up(1),BlocksTC.stoneArcane.getDefaultState());
        world.setBlockState(pos.east(3).up(2),BlocksTC.nitor.get(EnumDyeColor.YELLOW).getDefaultState());

        if(world.getBlockState(pos.south(3)).getBlock()==ModBlocks.enchantment_pillar)
            world.setBlockState(pos.south(3),BlocksTC.stoneArcane.getDefaultState());
        if(world.getBlockState(pos.south(3).up(1)).getBlock()==ModBlocks.enchantment_pillar)
            world.setBlockState(pos.south(3).up(1),BlocksTC.stoneArcane.getDefaultState());
        world.setBlockState(pos.south(3).up(2),BlocksTC.nitor.get(EnumDyeColor.YELLOW).getDefaultState());

        if(world.getBlockState(pos.west(3)).getBlock()==ModBlocks.enchantment_pillar)
            world.setBlockState(pos.west(3),BlocksTC.stoneArcane.getDefaultState());
        if(world.getBlockState(pos.west(3).up(1)).getBlock()==ModBlocks.enchantment_pillar)
            world.setBlockState(pos.west(3).up(1),BlocksTC.stoneArcane.getDefaultState());
        world.setBlockState(pos.west(3).up(2),BlocksTC.nitor.get(EnumDyeColor.YELLOW).getDefaultState());

        if(world.getBlockState(pos.north(2).east(2)).getBlock()==ModBlocks.enchantment_pillar)
            world.setBlockState(pos.north(2).east(2),BlocksTC.stoneArcane.getDefaultState());
        if(world.getBlockState(pos.north(2).east(2).up(1)).getBlock()==ModBlocks.enchantment_pillar)
            world.setBlockState(pos.north(2).east(2).up(1),BlocksTC.stoneArcane.getDefaultState());
        world.setBlockState(pos.north(2).east(2).up(2),BlocksTC.nitor.get(EnumDyeColor.YELLOW).getDefaultState());

        if(world.getBlockState(pos.south(2).east(2)).getBlock()==ModBlocks.enchantment_pillar)
            world.setBlockState(pos.south(2).east(2),BlocksTC.stoneArcane.getDefaultState());
        if(world.getBlockState(pos.south(2).east(2).up(1)).getBlock()==ModBlocks.enchantment_pillar)
            world.setBlockState(pos.south(2).east(2).up(1),BlocksTC.stoneArcane.getDefaultState());
        world.setBlockState(pos.south(2).east(2).up(2),BlocksTC.nitor.get(EnumDyeColor.YELLOW).getDefaultState());

        if(world.getBlockState(pos.north(2).west(2)).getBlock()==ModBlocks.enchantment_pillar)
            world.setBlockState(pos.north(2).west(2),BlocksTC.stoneArcane.getDefaultState());
        if(world.getBlockState(pos.north(2).west(2).up(1)).getBlock()==ModBlocks.enchantment_pillar)
            world.setBlockState(pos.north(2).west(2).up(1),BlocksTC.stoneArcane.getDefaultState());
        world.setBlockState(pos.north(2).west(2).up(2),BlocksTC.nitor.get(EnumDyeColor.YELLOW).getDefaultState());

        if(world.getBlockState(pos.south(2).west(2)).getBlock()==ModBlocks.enchantment_pillar)
            world.setBlockState(pos.south(2).west(2),BlocksTC.stoneArcane.getDefaultState());
        if(world.getBlockState(pos.south(2).west(2).up(1)).getBlock()==ModBlocks.enchantment_pillar)
            world.setBlockState(pos.south(2).west(2).up(1),BlocksTC.stoneArcane.getDefaultState());
        world.setBlockState(pos.south(2).west(2).up(2),BlocksTC.nitor.get(EnumDyeColor.YELLOW).getDefaultState());
    }

    public boolean checkLocation()
    {
        boolean valid;
        valid = world.getBlockState(pos.north(3)).getBlock() == ModBlocks.enchantment_pillar;
        valid = valid && world.getBlockState(pos.east(3)).getBlock()== ModBlocks.enchantment_pillar;
        valid = valid && world.getBlockState(pos.south(3)).getBlock()== ModBlocks.enchantment_pillar;
        valid = valid && world.getBlockState(pos.west(3)).getBlock()== ModBlocks.enchantment_pillar;

        valid = valid && world.getBlockState(pos.north(2).east(2)).getBlock()== ModBlocks.enchantment_pillar;
        valid = valid && world.getBlockState(pos.south(2).east(2)).getBlock()== ModBlocks.enchantment_pillar;
        valid = valid && world.getBlockState(pos.south(2).west(2)).getBlock()== ModBlocks.enchantment_pillar;
        valid = valid && world.getBlockState(pos.north(2).west(2)).getBlock()== ModBlocks.enchantment_pillar;

        valid = valid && world.getBlockState(pos.north(3).up(1)).getBlock()== ModBlocks.enchantment_pillar;
        valid = valid && world.getBlockState(pos.east(3).up(1)).getBlock()== ModBlocks.enchantment_pillar;
        valid = valid && world.getBlockState(pos.south(3).up(1)).getBlock()== ModBlocks.enchantment_pillar;
        valid = valid && world.getBlockState(pos.west(3).up(1)).getBlock()== ModBlocks.enchantment_pillar;

        valid = valid && world.getBlockState(pos.north(2).east(2).up(1)).getBlock()== ModBlocks.enchantment_pillar;
        valid = valid && world.getBlockState(pos.south(2).east(2).up(1)).getBlock()== ModBlocks.enchantment_pillar;
        valid = valid && world.getBlockState(pos.south(2).west(2).up(1)).getBlock()== ModBlocks.enchantment_pillar;
        valid = valid && world.getBlockState(pos.north(2).west(2).up(1)).getBlock()== ModBlocks.enchantment_pillar;

        valid = valid && world.getBlockState(pos.down(1)).getBlock()==ModBlocks.black_quartz_block;
        valid = valid && world.getBlockState(pos.down(1).north(1)).getBlock()==ModBlocks.black_quartz_block;
        valid = valid && world.getBlockState(pos.down(1).north(2)).getBlock()==ModBlocks.black_quartz_block;
        valid = valid && world.getBlockState(pos.down(1).south(1)).getBlock()==ModBlocks.black_quartz_block;
        valid = valid && world.getBlockState(pos.down(1).south(2)).getBlock()==ModBlocks.black_quartz_block;
        valid = valid && world.getBlockState(pos.down(1).north(1).east(1)).getBlock()==ModBlocks.black_quartz_block;
        valid = valid && world.getBlockState(pos.down(1).north(1).west(1)).getBlock()==ModBlocks.black_quartz_block;
        valid = valid && world.getBlockState(pos.down(1).south(1).east(1)).getBlock()==ModBlocks.black_quartz_block;
        valid = valid && world.getBlockState(pos.down(1).south(1).west(1)).getBlock()==ModBlocks.black_quartz_block;

        valid = valid && world.getBlockState(pos.down(1).north(3)).getBlock()==BlocksTC.stoneArcaneBrick;
        valid = valid && world.getBlockState(pos.down(1).north(3).east(1)).getBlock()==BlocksTC.stoneArcaneBrick;
        valid = valid && world.getBlockState(pos.down(1).north(3).west(1)).getBlock()==BlocksTC.stoneArcaneBrick;

        valid = valid && world.getBlockState(pos.down(1).north(2).east(1)).getBlock()==BlocksTC.stoneArcaneBrick;
        valid = valid && world.getBlockState(pos.down(1).north(2).east(2)).getBlock()==BlocksTC.stoneArcaneBrick;
        valid = valid && world.getBlockState(pos.down(1).north(2).west(1)).getBlock()==BlocksTC.stoneArcaneBrick;
        valid = valid && world.getBlockState(pos.down(1).north(2).west(2)).getBlock()==BlocksTC.stoneArcaneBrick;

        valid = valid && world.getBlockState(pos.down(1).north(1).east(2)).getBlock()==BlocksTC.stoneArcaneBrick;
        valid = valid && world.getBlockState(pos.down(1).north(1).east(3)).getBlock()==BlocksTC.stoneArcaneBrick;
        valid = valid && world.getBlockState(pos.down(1).north(1).west(2)).getBlock()==BlocksTC.stoneArcaneBrick;
        valid = valid && world.getBlockState(pos.down(1).north(1).west(3)).getBlock()==BlocksTC.stoneArcaneBrick;

        valid = valid && world.getBlockState(pos.down(1).east(3)).getBlock()==BlocksTC.stoneArcaneBrick;
        valid = valid && world.getBlockState(pos.down(1).west(3)).getBlock()==BlocksTC.stoneArcaneBrick;

        valid = valid && world.getBlockState(pos.down(1).south(3)).getBlock()==BlocksTC.stoneArcaneBrick;
        valid = valid && world.getBlockState(pos.down(1).south(3).east(1)).getBlock()==BlocksTC.stoneArcaneBrick;
        valid = valid && world.getBlockState(pos.down(1).south(3).west(1)).getBlock()==BlocksTC.stoneArcaneBrick;

        valid = valid && world.getBlockState(pos.down(1).south(2).east(1)).getBlock()==BlocksTC.stoneArcaneBrick;
        valid = valid && world.getBlockState(pos.down(1).south(2).east(2)).getBlock()==BlocksTC.stoneArcaneBrick;
        valid = valid && world.getBlockState(pos.down(1).south(2).west(1)).getBlock()==BlocksTC.stoneArcaneBrick;
        valid = valid && world.getBlockState(pos.down(1).south(2).west(2)).getBlock()==BlocksTC.stoneArcaneBrick;

        valid = valid && world.getBlockState(pos.down(1).south(1).east(2)).getBlock()==BlocksTC.stoneArcaneBrick;
        valid = valid && world.getBlockState(pos.down(1).south(1).east(3)).getBlock()==BlocksTC.stoneArcaneBrick;
        valid = valid && world.getBlockState(pos.down(1).south(1).west(2)).getBlock()==BlocksTC.stoneArcaneBrick;
        valid = valid && world.getBlockState(pos.down(1).south(1).west(3)).getBlock()==BlocksTC.stoneArcaneBrick;
        return valid;

    }
    public void refreshEnchants() {
        List<Enchantment> enchantmentObjects = getAvailableEnchants(enchantments.stream().map(Enchantment::getEnchantmentByID).collect(Collectors.toList()));
        cachedEnchantments = enchantmentObjects.stream().map(Enchantment::getEnchantmentID).collect(Collectors.toList());
    }

    private boolean isItemValidForSlot(int index, ItemStack stack) {
        Item item = stack.getItem();
        return item.isEnchantable(stack) && !stack.isItemEnchanted();
    }

    public ItemStackHandler getInventory() {
        return inventory;
    }

    @Override
    public void writeExtraNBT(NBTTagCompound nbttagcompound) {
        nbttagcompound.setTag("inventory", inventory.serializeNBT());
        nbttagcompound.setIntArray(TAG_ENCHANTS, enchantments.stream().mapToInt(i -> i).toArray());
        nbttagcompound.setIntArray(TAG_LEVELS, levels.stream().mapToInt(i -> i).toArray());
        nbttagcompound.setIntArray(TAG_CACHED_ENCHANTS, cachedEnchantments.stream().mapToInt(i -> i).toArray());
        nbttagcompound.setInteger(TAG_PROGRESS, progress);
        nbttagcompound.setBoolean(TAG_WORKING, working);
    }

    @Override
    public void readExtraNBT(NBTTagCompound nbttagcompound) {
        inventory.deserializeNBT(nbttagcompound.getCompoundTag("inventory"));
        if (nbttagcompound.hasKey(TAG_ENCHANTS)) {
            enchantments.clear();
            levels.clear();
            cachedEnchantments.clear();
            int[] enchantmentArray = nbttagcompound.getIntArray(TAG_ENCHANTS);
            Arrays.stream(enchantmentArray).forEach(i -> enchantments.add(i));
            int[] levelsArray = nbttagcompound.getIntArray(TAG_LEVELS);
            Arrays.stream(levelsArray).forEach(i -> levels.add(i));
            int[] cachedEnchantmentArray = nbttagcompound.getIntArray(TAG_CACHED_ENCHANTS);
            Arrays.stream(cachedEnchantmentArray).forEach(i -> cachedEnchantments.add(i));
        }
        if (nbttagcompound.hasKey(TAG_PROGRESS)) {
            progress = nbttagcompound.getInteger(TAG_PROGRESS);
        }
        if (nbttagcompound.hasKey(TAG_WORKING))
            working = nbttagcompound.getBoolean(TAG_WORKING);
    }

    @Override
    public boolean respondsToPulses() {
        return false;
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
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
        if (inventory.getStackInSlot(0) == ItemStack.EMPTY) {
            clearEnchants();
        }

        if (cooldown > 0)
            cooldown--;

        if (working) {
            ItemStack tool = inventory.getStackInSlot(0);
            if (tool == ItemStack.EMPTY) {
                working = false;
                progress = 0;
                world.playSound( (double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, SoundsTC.craftfail, SoundCategory.BLOCKS, 0.5F, 1.0F,false);
                return;
            }

            checkStructure();

            if (!working) // Pillar check
            {
                progress = 0;
                return;

            }

            progress++;
            float percenDone = (((progress / (20f * 15f)) * 100f) / 75f) * 100f;
            if (world.isRemote && !TTConfig.ClassicEnchanter) {
                newEnchant(percenDone);
            }

            if(!TTConfig.ClassicEnchanter)
                playCorrectSound(percenDone);

            if (progress > 20 * 15) {
                if (!world.isRemote) {
                    for (int i = 0; i < enchantments.size(); i++) {
                        tool.addEnchantment(Objects.requireNonNull(Enchantment.getEnchantmentByID(enchantments.get(i))), levels.get(i));
                    }
                }

                world.playSound( (double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, SoundsTC.wand, SoundCategory.BLOCKS, 0.5F, 1.0F,false);
                progress = 0;
                working = false;
                cooldown = 28;
                clearEnchants();
                sendUpdates();
            }

        }
    }

    private void playCorrectSound(float percentDone) {
        Vec3d curPos = new Vec3d(getPos().getX(), getPos().getY(), getPos().getZ());
        Vec3d targetPos;
        // test
        if (percentDone >= 0.0f && percentDone <= 0.5f) {
            targetPos=curPos.add(points[0]);
            world.playSound((double) targetPos.x + 0.5D, (double) targetPos.y + 0.5D, (double) targetPos.z + 0.5D, SoundsTC.jacobs, SoundCategory.BLOCKS, 0.5F, 1.0F, true);
        }
        if (percentDone >= 12.4f && percentDone <=12.7f){
            targetPos=curPos.add(points[1]);
            world.playSound((double) targetPos.x + 0.5D, (double) targetPos.y + 0.5D, (double) targetPos.z + 0.5D, SoundsTC.jacobs, SoundCategory.BLOCKS, 0.5F, 1.0F, true);
        }
        if (percentDone >= 24.7f && percentDone <=25.4f){
            targetPos=curPos.add(points[2]);
            world.playSound((double) targetPos.x + 0.5D, (double) targetPos.y + 0.5D, (double) targetPos.z + 0.5D, SoundsTC.jacobs, SoundCategory.BLOCKS, 0.5F, 1.0F, true);
        }
        if (percentDone >= 37.3f && percentDone <=37.6f){
            targetPos=curPos.add(points[3]);
            world.playSound((double) targetPos.x + 0.5D, (double) targetPos.y + 0.5D, (double) targetPos.z + 0.5D, SoundsTC.jacobs, SoundCategory.BLOCKS, 0.5F, 1.0F, true);
        }
        if (percentDone >= 49.7f && percentDone <=50.1f){
            targetPos=curPos.add(points[4]);
            world.playSound((double) targetPos.x + 0.5D, (double) targetPos.y + 0.5D, (double) targetPos.z + 0.5D, SoundsTC.jacobs, SoundCategory.BLOCKS, 0.5F, 1.0F, true);
        }
        if (percentDone >= 61.3f && percentDone <=61.7f){
            targetPos=curPos.add(points[5]);
            world.playSound((double) targetPos.x + 0.5D, (double) targetPos.y + 0.5D, (double) targetPos.z + 0.5D, SoundsTC.jacobs, SoundCategory.BLOCKS, 0.5F, 1.0F, true);
        }
        if (percentDone >= 74.6f && percentDone <=75.1f){
            targetPos=curPos.add(points[6]);
            world.playSound((double) targetPos.x + 0.5D, (double) targetPos.y + 0.5D, (double) targetPos.z + 0.5D, SoundsTC.jacobs, SoundCategory.BLOCKS, 0.5F, 1.0F, true);
        }
        if (percentDone >= 87.5f && percentDone <=88.0f){
            targetPos=curPos.add(points[7]);
            world.playSound((double) targetPos.x + 0.5D, (double) targetPos.y + 0.5D, (double) targetPos.z + 0.5D, SoundsTC.jacobs, SoundCategory.BLOCKS, 0.5F, 1.0F, true);
        }
        if (percentDone >= 99.6f && percentDone <=100.3f)
            world.playSound( (double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, SoundsTC.brain, SoundCategory.BLOCKS, 0.25F, 1.0F,false);
    }
    private void newEnchant(float percenDone) {

        if (percenDone >= 0) {
            arcPoints(0, 1);
        }
        if (percenDone >= 12.5) {
            arcPoints(1, 2);
        }
        if (percenDone >= 25) {
            arcPoints(2, 3);
        }
        if (percenDone >= 37.5) {
            arcPoints(3, 4);
        }
        if (percenDone >= 50) {
            arcPoints(4, 5);
        }
        if (percenDone >= 62.5) {
            arcPoints(5, 6);
        }
        if (percenDone >= 75) {
            arcPoints(6, 7);
        }

        if (percenDone >= 87.5) {

            arcPoints(7, 0);
        }
        if (percenDone >= 100) {
            for (Vec3d point : points) {
                Vec3d curPos = new Vec3d(getPos().getX(), getPos().getY(), getPos().getZ());
                Vec3d originPos = curPos.add(point);
                FXDispatcher.INSTANCE.arcLightning(originPos.x, originPos.y, originPos.z, getPos().getX() + 0.5f, getPos().up().getY() + 0.5f, getPos().getZ() + 0.5f, this.c.getRed() / 255.0F, this.c.getGreen() / 255.0F, this.c.getBlue() / 255.0F, 0.5f);
            }
        }
    }

    public boolean playerHasIngredients(List<ItemStack> stacks, EntityPlayer player) {
        for (ItemStack stack : stacks) {
            if (!InventoryUtils.isPlayerCarryingAmount(player, stack, false))
                return false;
        }
        return true;
    }

    public void takeIngredients(List<ItemStack> stacks, EntityPlayer player) {
        for (ItemStack stack : stacks) {
            InventoryUtils.consumePlayerItem(player, stack, true, false);
        }
    }

    private void arcPoints(int start, int end) {
        Vec3d curPos = new Vec3d(getPos().getX(), getPos().getY(), getPos().getZ());
        Vec3d originPos = curPos.add(points[start]);
        Vec3d nextPos = curPos.add(points[end]);
        FXDispatcher.INSTANCE.arcLightning(originPos.x, originPos.y, originPos.z, nextPos.x, nextPos.y, nextPos.z, this.c.getRed() / 255.0F, this.c.getGreen() / 255.0F, this.c.getBlue() / 255.0F, 1f);
    }

    private void checkStructure() {
        if (TTConfig.ClassicEnchanter) {
            checkPillars();
        } else {
            if (!MultiblockManager.checkMultiblockCombined(world, getPos(), MULTIBLOCK_LOCATION))
                working = false;
        }
    }

    private boolean checkPillars() {
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

    private boolean assignPillars() {
        int y = pos.getY();
        for (int x = pos.getX() - 4; x <= pos.getX() + 4; x++)
            for (int z = pos.getZ() - 4; z <= pos.getZ() + 4; z++) {
                int height = findPillar(new BlockPos(x, y, z));
                if (height != -1)
                    pillars.add(new Tuple4Int(x, y, z, height));

                if (pillars.size() == 6)
                    return false;
            }

        pillars.clear();
        return true;
    }

    private int findPillar(BlockPos pillarPos) {
        int obsidianFound = 0;
        for (int i = 0; true; i++) {
            if (pillarPos.getY() + i >= 256)
                return -1;

            IBlockState id = world.getBlockState(pillarPos.up(i));

            if (id.getBlock() == Blocks.OBSIDIAN) {
                ++obsidianFound;
                continue;
            }
            if (BlocksTC.nitor.containsValue(id.getBlock())) {
                if (obsidianFound >= 2 && obsidianFound < 13)
                    return pillarPos.getY() + i;
                return -1;
            }

            return -1;
        }
    }

    private List<Enchantment> getValidEnchantments() {
        List<Enchantment> enchantments = new ArrayList<>();
        if (inventory.getStackInSlot(0) == ItemStack.EMPTY)
            return enchantments;
        ItemStack item = inventory.getStackInSlot(0);
        for (Enchantment enchantment : Enchantment.REGISTRY) {
            if (item.getItem().getItemEnchantability(item) != 0 && canApply(item, enchantment, enchantments, false))
                enchantments.add(enchantment);

        }
        return enchantments;
    }

    private List<Enchantment> getAvailableEnchants(List<Enchantment> currentEnchants) {
        List<Enchantment> enchantments = new ArrayList<>();
        if (inventory.getStackInSlot(0) == ItemStack.EMPTY)
            return enchantments;
        ItemStack item = inventory.getStackInSlot(0);
        List<Enchantment> valid = getValidEnchantments();
        for (Enchantment validEnchant : valid) {
            if (item.getItem().getItemEnchantability(item) != 0 && canApply(item, validEnchant, enchantments, false) && canApply(item, validEnchant, currentEnchants))
                enchantments.add(validEnchant);

        }
        return enchantments;
    }

    public List<ItemStack> getEnchantmentCost() {
        List<ItemStack> costs = new ArrayList<>();
        Map<Aspect, Integer> costItems = new HashMap<>();
        List<Enchantment> enchantmentObjects = enchantments.stream().map(Enchantment::getEnchantmentByID).collect(Collectors.toList());
        for (Enchantment enchantment : enchantmentObjects) {
            switch (Objects.requireNonNull(enchantment.type)) {
                case ARMOR:
                    addOneTo(costItems, Aspect.PROTECT);
                    break;
                case ARMOR_FEET:
                    addOneTo(costItems, Aspect.PROTECT);
                    addOneTo(costItems, Aspect.MOTION);
                    break;
                case ARMOR_CHEST:
                    addOneTo(costItems, Aspect.PROTECT);
                    addOneTo(costItems, Aspect.LIFE);
                    break;
                case ARMOR_LEGS:
                    addOneTo(costItems, Aspect.PROTECT);
                    break;
                case ARMOR_HEAD:
                    addOneTo(costItems, Aspect.PROTECT);
                    addOneTo(costItems, Aspect.MIND);
                    break;
                case DIGGER:
                    addOneTo(costItems, Aspect.ENTROPY);
                    addOneTo(costItems, Aspect.TOOL);
                    break;
                case BREAKABLE:
                    addOneTo(costItems, Aspect.ENTROPY);
                    break;
                case WEARABLE:
                    addOneTo(costItems, Aspect.MAN);
                    break;
                case WEAPON:
                    addOneTo(costItems, Aspect.DEATH);
                    addOneTo(costItems,Aspect.AVERSION);
                    break;
                case BOW:
                    addOneTo(costItems, Aspect.DEATH);
                    break;
                case FISHING_ROD:
                    addOneTo(costItems, Aspect.BEAST);
                    addOneTo(costItems, Aspect.WATER);
                    break;
                default:
                    break;
            }
        }


        for (Aspect item : costItems.keySet()) {
            ItemStack crystal = new ItemStack(ItemsTC.crystalEssence,costItems.get(item));
            ((ItemCrystalEssence) crystal.getItem()).setAspects(crystal, new AspectList().add(item, 1));
            costs.add(crystal);
        }
        return costs;
    }

    private void addOneTo(Map<Aspect, Integer> costItems, Aspect crystalEssence) {
        if (costItems.containsKey(crystalEssence))
            costItems.put(crystalEssence, costItems.get(crystalEssence) + 1);
        else
            costItems.put(crystalEssence, 1);
    }

    public int getProgress() {
        return progress;
    }
}
