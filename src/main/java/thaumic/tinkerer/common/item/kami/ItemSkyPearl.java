/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the ThaumicTinkerer Mod.
 *
 * ThaumicTinkerer is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * ThaumicTinkerer is a Derivative Work on Thaumcraft 4.
 * Thaumcraft 4 (c) Azanor 2012
 * (http://www.minecraftforum.net/topic/1585216-)
 *
 * File Created @ [Jan 10, 2014, 5:26:45 PM (GMT)]
 */
package thaumic.tinkerer.common.item.kami;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.codechicken.lib.vec.Vector3;
import thaumic.tinkerer.common.ThaumicTinkerer;
import thaumic.tinkerer.common.block.kami.BlockWarpGate;
import thaumic.tinkerer.common.core.helper.ItemNBTHelper;
import thaumic.tinkerer.common.core.helper.MiscHelper;
import thaumic.tinkerer.common.core.proxy.TTCommonProxy;
import thaumic.tinkerer.common.lib.LibItemNames;
import thaumic.tinkerer.common.lib.LibResearch;
import thaumic.tinkerer.common.registry.ItemKamiBase;
import thaumic.tinkerer.common.registry.ThaumicTinkererInfusionRecipe;
import thaumic.tinkerer.common.registry.ThaumicTinkererRecipe;
import thaumic.tinkerer.common.research.IRegisterableResearch;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class ItemSkyPearl extends ItemKamiBase {

    public static final String TAG_X = "x";
    public static final String TAG_Y = "y";
    public static final String TAG_Z = "z";
    public static final String TAG_DIM = "dim";

    public ItemSkyPearl() {
        super();
        setMaxStackSize(1);
    }

    public static void addInfo(ItemStack stack, int dim, Vector3 pos, List<String> list, boolean simpleMode) {
        if (isAttuned(stack)) {
            int x = getX(stack);
            int y = getY(stack);
            int z = getZ(stack);
            list.add("X: " + x);
            if (!simpleMode)
                list.add("Y: " + y);
            list.add("Z: " + z);
            if (getDim(stack) != dim) {
                if (!simpleMode)
                    list.add(EnumChatFormatting.RED + StatCollector.translateToLocal("ttmisc.differentDim"));
            } else
                list.add(EnumChatFormatting.BLUE + StatCollector.translateToLocal("ttmisc.distance") + ": " + new BigDecimal(MiscHelper.pointDistanceSpace(x, simpleMode ? 0 : y, z, pos.x, simpleMode ? 0 : pos.y, pos.z)).setScale(2, RoundingMode.UP).toString() + "m");
        }
    }

    public static void setValues(ItemStack stack, int x, int y, int z, int dim) {
        ItemNBTHelper.setInt(stack, TAG_X, x);
        ItemNBTHelper.setInt(stack, TAG_Y, y);
        ItemNBTHelper.setInt(stack, TAG_Z, z);
        ItemNBTHelper.setInt(stack, TAG_DIM, dim);
    }

    public static boolean isAttuned(ItemStack stack) {
        return ItemNBTHelper.detectNBT(stack) && ItemNBTHelper.getInt(stack, TAG_Y, -1) != -1;
    }

    public static int getX(ItemStack stack) {
        if (!isAttuned(stack))
            return 0;

        return ItemNBTHelper.getInt(stack, TAG_X, 0);
    }

    public static int getY(ItemStack stack) {
        if (!isAttuned(stack))
            return 0;

        return ItemNBTHelper.getInt(stack, TAG_Y, 0);
    }

    public static int getZ(ItemStack stack) {
        if (!isAttuned(stack))
            return 0;

        return ItemNBTHelper.getInt(stack, TAG_Z, 0);
    }

    public static int getDim(ItemStack stack) {
        if (!isAttuned(stack))
            return 0;

        return ItemNBTHelper.getInt(stack, TAG_DIM, 0);
    }

    @Override
    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10) {
        Block block = par3World.getBlock(par4, par5, par6);
        if (block == ThaumicTinkerer.registry.getFirstBlockFromClass(BlockWarpGate.class) && !isAttuned(par1ItemStack)) {
            setValues(par1ItemStack, par4, par5, par6, par2EntityPlayer.dimension);
            par3World.playSoundAtEntity(par2EntityPlayer, "random.orb", 0.3F, 0.1F);
        }

        return true;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
        if (par3EntityPlayer.isSneaking() && isAttuned(par1ItemStack)) {
            par2World.playSoundAtEntity(par3EntityPlayer, "random.orb", 0.3F, 0.1F);
            ItemNBTHelper.setInt(par1ItemStack, TAG_Y, -1);
        }

        return par1ItemStack;
    }

    @Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
        addInfo(par1ItemStack, par2EntityPlayer.dimension, Vector3.fromEntityCenter(par2EntityPlayer), par3List, false);
    }

    @Override
    public boolean hasEffect(ItemStack par1ItemStack) {
        return isAttuned(par1ItemStack);
    }

    @Override
    public EnumRarity getRarity(ItemStack par1ItemStack) {
        return TTCommonProxy.kamiRarity;
    }

    @Override
    public String getItemName() {
        return LibItemNames.SKY_PEARL;
    }

    @Override
    public IRegisterableResearch getResearchItem() {
        return null;
    }

    @Override
    public ThaumicTinkererRecipe getRecipeItem() {
        return new ThaumicTinkererInfusionRecipe(LibResearch.KEY_SKY_PEARL, LibResearch.KEY_WARP_GATE, new ItemStack(ThaumicTinkerer.registry.getFirstItemFromClass(ItemSkyPearl.class), 2), 6, new AspectList().add(Aspect.TRAVEL, 32).add(Aspect.ELDRITCH, 32).add(Aspect.FLIGHT, 32).add(Aspect.AIR, 16), new ItemStack(Items.ender_pearl),
                new ItemStack(ThaumicTinkerer.registry.getFirstItemFromClass(ItemKamiResource.class)), new ItemStack(ThaumicTinkerer.registry.getFirstItemFromClass(ItemKamiResource.class), 1, 7), new ItemStack(Blocks.lapis_block), new ItemStack(Items.diamond));

    }
}