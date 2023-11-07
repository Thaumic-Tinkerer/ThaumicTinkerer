package com.nekokittygames.thaumictinkerer.common.items.Kami;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import com.nekokittygames.thaumictinkerer.common.items.TTItem;
import com.nekokittygames.thaumictinkerer.common.libs.LibItemNames;
import com.nekokittygames.thaumictinkerer.common.misc.ItemNBTHelper;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

import static com.nekokittygames.thaumictinkerer.common.items.ItemSoulMould.getEntityName;

public class ItemBlockTalisman extends TTItem implements IBauble {
    @Deprecated
    private static final String TAG_BLOCK_ID = "blockID";
    private static final String TAG_BLOCK_NAME = "blockName";
    private static final String TAG_BLOCK_META = "blockMeta";
    private static final String TAG_BLOCK_COUNT = "blockCount";

    public ItemBlockTalisman() {
        super(LibItemNames.BLOCK_TALISMAN);
        setMaxStackSize(1);
        setHasSubtypes(true);
        this.addPropertyOverride(new ResourceLocation("active"), new IItemPropertyGetter() {
            @Override
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
                if (stack.getMetadata() == 1) {
                    return 1.0F;
                }
                return 0.0F;
            }
        });
    }

    private static void setCount(ItemStack stack, int count) {
        ItemNBTHelper.setInt(stack, TAG_BLOCK_COUNT, count);
    }

    public static int remove(ItemStack stack, int count) {
        int current = getBlockCount(stack);
        setCount(stack, Math.max(current - count, 0));

        return Math.min(current, count);
    }

    @Deprecated
    public static int getBlockID(ItemStack stack) {
        return ItemNBTHelper.getInt(stack, TAG_BLOCK_ID, 0);
    }

    public static String getBlockName(ItemStack stack) {
        return ItemNBTHelper.getString(stack, TAG_BLOCK_NAME, "");
    }

    public static Block getBlock(ItemStack stack) {
        Block block = Block.getBlockFromName(getBlockName(stack));
        if (block == Blocks.AIR)
            block = Block.getBlockById(getBlockID(stack));

        return block;
    }

    public static int getBlockMeta(ItemStack stack) {
        return ItemNBTHelper.getInt(stack, TAG_BLOCK_META, 0);
    }

    public static int getBlockCount(ItemStack stack) {
        return ItemNBTHelper.getInt(stack, TAG_BLOCK_COUNT, 0);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World par2World, EntityPlayer par3EntityPlayer, @Nonnull EnumHand hand) {
        ItemStack par1ItemStack = par3EntityPlayer.getHeldItem(hand);
        if ((getBlock(par1ItemStack) != Blocks.AIR || getBlockID(par1ItemStack) != 0) && par3EntityPlayer.isSneaking()) {
            int dmg = par1ItemStack.getItemDamage();
            par1ItemStack.setItemDamage(~dmg & 1);
            par3EntityPlayer.playSound(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 0.3F, 0.1F);
        }
        return ActionResult.newResult(EnumActionResult.PASS, par1ItemStack);
    }

    @Nonnull
    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        IBlockState state = world.getBlockState(pos);
        ItemStack stack = player.getHeldItem(hand);

        if (Item.getItemFromBlock(state.getBlock()) != Items.AIR
                && setBlock(stack, state.getBlock(), state.getBlock().getMetaFromState(state))) {
            return EnumActionResult.SUCCESS;
        } else {
            Block bBlock = getBlock(stack);
            int bmeta = getBlockMeta(stack);

            if(bBlock == null)
                return EnumActionResult.PASS;

            TileEntity tile = world.getTileEntity(pos);
            if(tile != null && tile.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side)) {
                if(!world.isRemote) {
                    IItemHandler inv = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side);
                    ItemStack toAdd = new ItemStack(bBlock, 1, bmeta);
                    int maxSize = toAdd.getMaxStackSize();
                    toAdd.setCount(remove(stack, maxSize));
                    ItemStack remainder = ItemHandlerHelper.insertItemStacked(inv, toAdd, false);
                    if(!remainder.isEmpty())
                        add(stack, remainder.getCount());
                }
                return EnumActionResult.SUCCESS;
            } else {
                if(player.capabilities.isCreativeMode || getBlockCount(stack) > 0) {
                    ItemStack toUse = new ItemStack(bBlock, 1, bmeta);

                    ItemStack saveHeldItem = player.getHeldItem(hand);
                    player.setHeldItem(hand, toUse);
                    EnumActionResult result = Item.getItemFromBlock(bBlock).onItemUse(player, world, pos, hand, side, hitX, hitY, hitZ);
                    player.setHeldItem(hand, saveHeldItem);

                    if (result == EnumActionResult.SUCCESS) {
                        remove(stack, 1);
                        set(toUse, getBlockCount(stack));
                        return EnumActionResult.SUCCESS;
                    }
                }
            }
        }

        return EnumActionResult.PASS;
    }

    public static void set(ItemStack stack, String str) {
        set(stack, 0, str);
    }

    private static int ticks, count;

    private static String customString;

    private static final int maxTicks = 30;

    public static void set(ItemStack stack, int count) {
        set(stack, count, null);
    }

    public static void set(ItemStack stack, int count, String str) {
        stack = ItemStack.EMPTY;
        ItemBlockTalisman.count = count;
        ItemBlockTalisman.customString = str;
        ticks = stack.isEmpty() ? 0 : maxTicks;
    }

    private boolean setBlock(ItemStack stack, Block block, int meta) {
        if (getBlock(stack) == Blocks.AIR || getBlockCount(stack) == 0) {
            ItemNBTHelper.setString(stack, TAG_BLOCK_NAME, block.getRegistryName().toString());
            ItemNBTHelper.setInt(stack, TAG_BLOCK_META, meta);
            return true;
        }
        return false;
    }

    private void add(ItemStack stack, int count) {
        int current = getBlockCount(stack);
        setCount(stack, current + count);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack par1ItemStack, World world, List<String> stacks, ITooltipFlag flags) {
        Block block = getBlock(par1ItemStack);
        if (block != null && block != Blocks.AIR) {
            int count = getBlockCount(par1ItemStack);
            stacks.add(I18n.format(new ItemStack(block, 1, getBlockMeta(par1ItemStack)).getTranslationKey() + ".name") + " (x" + count + ")");
        }

        if (par1ItemStack.getItemDamage() == 1)
            stacks.add(I18n.format("ttmisc.active"));
        else stacks.add(I18n.format("ttmisc.inactive"));
    }

    @Override
    public EnumRarity getRarity(ItemStack itemstack) {
        return EnumRarity.EPIC;
    }

    @Override
    public BaubleType getBaubleType(ItemStack itemstack) {
        return BaubleType.RING;
    }

    @Override
    public void onWornTick(ItemStack itemstack, EntityLivingBase entity) {
        Block block = getBlock(itemstack);
        if(!entity.world.isRemote && itemstack.getItemDamage() == 1 && block != null && entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;
            int meta = getBlockMeta(itemstack);

            int highest = -1;
            int[] counts = new int[player.inventory.getSizeInventory() - player.inventory.armorInventory.size()];

            for(int i = 0; i < counts.length; i++) {
                ItemStack stack = player.inventory.getStackInSlot(i);
                if(stack.isEmpty()) {
                    continue;
                }

                if(Item.getItemFromBlock(block) == stack.getItem() && stack.getItemDamage() == meta) {
                    counts[i] = stack.getCount();
                    if(highest == -1)
                        highest = i;
                    else highest = counts[i] > counts[highest] && highest > 8 ? i : highest;
                }
            }

            if(highest == -1) {
				/*ItemStack heldItem = player.inventory.getItemStack();
				if(hasFreeSlot && (heldItem == null || Item.getItemFromBlock(block) == heldItem.getItem() || heldItem.getItemDamage() != meta)) {
					ItemStack stack = new ItemStack(block, remove(itemstack, 64), meta);
					if(stack.stackSize != 0)
						player.inventory.addItemStackToInventory(stack);
				}*/
                // Used to keep one stack, disabled for now
            } else {
                for(int i = 0; i < counts.length; i++) {
                    int count = counts[i];

                    // highest is used to keep one stack, disabled for now
                    if(i == highest || count == 0)
                        continue;

                    add(itemstack, count);
                    player.inventory.setInventorySlotContents(i, ItemStack.EMPTY);
                }

				/*int countInHighest = counts[highest];
				int maxSize = new ItemStack(block, 1, meta).getMaxStackSize();
				if(countInHighest < maxSize) {
					int missing = maxSize - countInHighest;
					ItemStack stackInHighest = player.inventory.getStackInSlot(highest);
					stackInHighest.stackSize += remove(itemstack, missing);
				}*/
                // Used to keep one stack, disabled for now
            }
        }
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
}