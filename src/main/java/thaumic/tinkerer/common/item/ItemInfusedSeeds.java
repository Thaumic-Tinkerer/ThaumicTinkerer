package thaumic.tinkerer.common.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFarmland;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.config.ConfigItems;
import thaumic.tinkerer.client.core.helper.IconHelper;
import thaumic.tinkerer.common.ThaumicTinkerer;
import thaumic.tinkerer.common.block.BlockInfusedFarmland;
import thaumic.tinkerer.common.block.BlockInfusedGrain;
import thaumic.tinkerer.common.lib.LibBlockNames;
import thaumic.tinkerer.common.lib.LibItemNames;
import thaumic.tinkerer.common.lib.LibResearch;
import thaumic.tinkerer.common.registry.ITTinkererItem;
import thaumic.tinkerer.common.registry.ThaumicTinkererInfusionRecipe;
import thaumic.tinkerer.common.registry.ThaumicTinkererRecipe;
import thaumic.tinkerer.common.registry.ThaumicTinkererRecipeMulti;
import thaumic.tinkerer.common.research.IRegisterableResearch;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pixlepix on 4/14/14.
 */
public class ItemInfusedSeeds extends ItemSeeds implements ITTinkererItem {

    public ItemInfusedSeeds() {
        super(Blocks.wheat, Blocks.farmland);
    }

    @Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
        super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);
        par3List.add(getAspect(par1ItemStack).getName());
    }

    public static Aspect getAspect(ItemStack stack) {
        AspectList aspectList = new AspectList();
        if (stack.getTagCompound() == null) {
            stack.setTagCompound(new NBTTagCompound());
        }
        aspectList.readFromNBT(stack.getTagCompound());

        return aspectList.size() == 0 ? null : aspectList.getAspects()[0];
    }

    public static void setAspect(ItemStack stack, Aspect aspect) {
        stack.setTagCompound(new NBTTagCompound());
        AspectList aspectList = new AspectList().add(aspect, 1);
        aspectList.writeToNBT(stack.getTagCompound());
    }

    @Override
    public boolean getHasSubtypes() {
        return true;
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tab, List l) {
        for (Aspect primal : Aspect.getPrimalAspects()) {
            ItemStack itemStack = new ItemStack(item, 1);
            setAspect(itemStack, primal);
            l.add(itemStack);
        }
    }

    private IIcon[] icons;

    @Override
    public void registerIcons(IIconRegister par1IconRegister) {
        icons = new IIcon[7];
        icons[0] = IconHelper.forName(par1IconRegister, "seed_aer");
        icons[1] = IconHelper.forName(par1IconRegister, "seed_ignis");
        icons[2] = IconHelper.forName(par1IconRegister, "seed_aqua");
        icons[3] = IconHelper.forName(par1IconRegister, "seed_terra");

        icons[4] = IconHelper.forName(par1IconRegister, "seed_ordo");
        icons[5] = IconHelper.forName(par1IconRegister, "seed_perditio");

        icons[6] = IconHelper.forName(par1IconRegister, "seed_complex");
    }

    @Override
    public IIcon getIconIndex(ItemStack stack) {
        return getAspect(stack) == null ? icons[0] : icons[BlockInfusedGrain.getNumberFromAspectForTexture(getAspect(stack))];
    }

    @Override
    public ArrayList<Object> getSpecialParameters() {
        return null;
    }

    @Override
    public String getItemName() {
        return LibItemNames.INFUSED_SEEDS;
    }

    @Override
    public boolean shouldRegister() {
        return true;
    }

    @Override
    public boolean shouldDisplayInTab() {
        return true;
    }

    @Override
    public IRegisterableResearch getResearchItem() {
        return null;
    }

    @Override
    public ThaumicTinkererRecipe getRecipeItem() {
        return new ThaumicTinkererRecipeMulti(
                new ThaumicTinkererInfusionRecipe(LibResearch.KEY_POTIONS + 0, new ItemStack(this, 1, 0), 5, new AspectList().add(Aspect.CROP, 32).add(Aspect.HARVEST, 32), new ItemStack(Items.wheat_seeds), new ItemStack(ConfigItems.itemShard, 1, 0), new ItemStack(ConfigItems.itemShard, 1, 0), new ItemStack(ConfigItems.itemShard, 1, 0), new ItemStack(ConfigItems.itemShard, 1, 0)),

                new ThaumicTinkererInfusionRecipe(LibResearch.KEY_POTIONS + 1, new ItemStack(this, 1, 1), 5, new AspectList().add(Aspect.CROP, 32).add(Aspect.HARVEST, 32), new ItemStack(Items.wheat_seeds), new ItemStack(ConfigItems.itemShard, 1, 1), new ItemStack(ConfigItems.itemShard, 1, 1), new ItemStack(ConfigItems.itemShard, 1, 1), new ItemStack(ConfigItems.itemShard, 1, 1)),

                new ThaumicTinkererInfusionRecipe(LibResearch.KEY_POTIONS + 2, new ItemStack(this, 1, 2), 5, new AspectList().add(Aspect.CROP, 32).add(Aspect.HARVEST, 32), new ItemStack(Items.wheat_seeds), new ItemStack(ConfigItems.itemShard, 1, 3), new ItemStack(ConfigItems.itemShard, 1, 3), new ItemStack(ConfigItems.itemShard, 1, 3), new ItemStack(ConfigItems.itemShard, 1, 3)),

                new ThaumicTinkererInfusionRecipe(LibResearch.KEY_POTIONS + 3, new ItemStack(this, 1, 3), 5, new AspectList().add(Aspect.CROP, 32).add(Aspect.HARVEST, 32), new ItemStack(Items.wheat_seeds), new ItemStack(ConfigItems.itemShard, 1, 2), new ItemStack(ConfigItems.itemShard, 1, 2), new ItemStack(ConfigItems.itemShard, 1, 2), new ItemStack(ConfigItems.itemShard, 1, 2)),


                new ThaumicTinkererInfusionRecipe(LibResearch.KEY_POTIONS + 4, new ItemStack(this, 1, 4), 5, new AspectList().add(Aspect.CROP, 32).add(Aspect.HARVEST, 32), new ItemStack(Items.wheat_seeds), new ItemStack(ConfigItems.itemShard, 1, 4), new ItemStack(ConfigItems.itemShard, 1, 4), new ItemStack(ConfigItems.itemShard, 1, 4), new ItemStack(ConfigItems.itemShard, 1, 4)),
                new ThaumicTinkererInfusionRecipe(LibResearch.KEY_POTIONS + 5, new ItemStack(this, 1, 5), 5, new AspectList().add(Aspect.CROP, 32).add(Aspect.HARVEST, 32), new ItemStack(Items.wheat_seeds), new ItemStack(ConfigItems.itemShard, 1, 5), new ItemStack(ConfigItems.itemShard, 1, 5), new ItemStack(ConfigItems.itemShard, 1, 5), new ItemStack(ConfigItems.itemShard, 1, 5))
        );
    }

    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World world, int x, int y, int z, int par7, float par8, float par9, float par10) {
        if (par7 != 1) {
            return false;
        } else if (world.getBlock(x, y, z) instanceof BlockFarmland && par2EntityPlayer.canPlayerEdit(x, y, z, par7, par1ItemStack) && par2EntityPlayer.canPlayerEdit(x, y + 1, z, par7, par1ItemStack)) {

            world.setBlock(x, y, z, ThaumicTinkerer.registry.getFirstBlockFromClass(BlockInfusedFarmland.class));
            world.setBlock(x, y + 1, z, ThaumicTinkerer.registry.getFirstBlockFromClass(BlockInfusedGrain.class));
            BlockInfusedGrain.setAspect(world, x, y + 1, z, getAspect(par1ItemStack));
            par1ItemStack.stackSize--;
            return true;
        } else {
            return false;
        }
    }

}