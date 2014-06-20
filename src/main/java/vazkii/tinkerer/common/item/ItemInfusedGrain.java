package vazkii.tinkerer.common.item;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import thaumcraft.api.aspects.Aspect;
import vazkii.tinkerer.common.block.ModBlocks;

import java.util.List;

/**
 * Created by pixlepix on 4/22/14.
 */
public class ItemInfusedGrain extends Item {

    @Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
        super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);
        par3List.add(getAspect(par1ItemStack).getName());
    }

    public static int getMetaForAspect(Aspect aspect){
        for(PRIMAL_ASPECT_ENUM e:PRIMAL_ASPECT_ENUM.values()){
            if(aspect==e.aspect){
                return e.ordinal();
            }
        }
        return 0;
    }

    public Aspect getAspect(ItemStack stack){
        return PRIMAL_ASPECT_ENUM.values()[stack.getItemDamage()].aspect;
    }

    @Override
    public boolean getHasSubtypes() {
        return true;
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tab, List l) {
        for(PRIMAL_ASPECT_ENUM primal:PRIMAL_ASPECT_ENUM.values()){
            l.add(new ItemStack(item, 1, primal.ordinal()));
        }
    }

    private enum PRIMAL_ASPECT_ENUM{
        AIR(Aspect.AIR),
        FIRE(Aspect.FIRE),
        EARTH(Aspect.EARTH),
        WATER(Aspect.WATER);
        Aspect aspect;
        PRIMAL_ASPECT_ENUM(Aspect a){
            this.aspect=a;
        }
    }

}
