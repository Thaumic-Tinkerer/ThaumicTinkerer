package thaumic.tinkerer.common.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import thaumcraft.api.aspects.Aspect;
import thaumic.tinkerer.client.core.helper.IconHelper;
import thaumic.tinkerer.common.lib.LibItemNames;
import thaumic.tinkerer.common.registry.ItemBase;
import thaumic.tinkerer.common.registry.ThaumicTinkererRecipe;
import thaumic.tinkerer.common.research.IRegisterableResearch;

import java.util.List;

/**
 * Created by pixlepix on 4/22/14.
 */
public class ItemInfusedGrain extends ItemBase {

    private IIcon[] icons;

    public static int getMetaForAspect(Aspect aspect) {
        for (PRIMAL_ASPECT_ENUM e : PRIMAL_ASPECT_ENUM.values()) {
            if (aspect == e.aspect) {
                return e.ordinal();
            }
        }
        return 0;
    }

    @Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
        super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);
        par3List.add(getAspect(par1ItemStack).getName());
    }

    public Aspect getAspect(ItemStack stack) {
        return PRIMAL_ASPECT_ENUM.values()[stack.getItemDamage()].aspect;
    }

    @Override
    public boolean getHasSubtypes() {
        return true;
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tab, List l) {
        for (PRIMAL_ASPECT_ENUM primal : PRIMAL_ASPECT_ENUM.values()) {
            l.add(new ItemStack(item, 1, primal.ordinal()));
        }
    }

    @Override
    public void registerIcons(IIconRegister par1IconRegister) {
        icons = new IIcon[4];
        icons[0] = IconHelper.forName(par1IconRegister, "fruit_aer");
        icons[1] = IconHelper.forName(par1IconRegister, "fruit_ignis");
        icons[2] = IconHelper.forName(par1IconRegister, "fruit_terra");
        icons[3] = IconHelper.forName(par1IconRegister, "fruit_aqua");
    }

    @Override
    public IIcon getIconFromDamage(int par1) {
        return icons[par1];
    }

    @Override
    public String getItemName() {
        return LibItemNames.INFUSED_GRAIN;
    }

    @Override
    public ThaumicTinkererRecipe getRecipeItem() {
        return null;
    }

    @Override
    public IRegisterableResearch getResearchItem() {
        return null;
    }

    private enum PRIMAL_ASPECT_ENUM {
        AIR(Aspect.AIR),
        FIRE(Aspect.FIRE),
        EARTH(Aspect.EARTH),
        WATER(Aspect.WATER);
        Aspect aspect;

        PRIMAL_ASPECT_ENUM(Aspect a) {
            this.aspect = a;
        }
    }

}
