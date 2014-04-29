package vazkii.tinkerer.common.item;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.common.config.ConfigAspects;
import thaumcraft.common.config.ConfigItems;
import vazkii.tinkerer.common.block.ModBlocks;
import vazkii.tinkerer.common.potion.ModPotions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pixlepix on 4/19/14.
 */
public class ItemInfusedPotion extends ItemPotion {


    public String getItemStackDisplayName(ItemStack par1ItemStack){
        String s = "thaumictinkerer.item.infusedPotion." + getAspect(par1ItemStack).getName();
        return StatCollector.translateToLocal(s).trim();
    }

    @Override
    public List getEffects(ItemStack par1ItemStack) {
        return getEffects(par1ItemStack.getItemDamage());
    }

    @Override
    public List getEffects(int par1) {
        List r = new ArrayList();
        switch(par1){
            case 0:
                r.add(new PotionEffect(ModPotions.potionAirId, 18000));
                break;
            case 1:

                r.add(new PotionEffect(ModPotions.potionFireId, 18000));
                break;
            case 2:

                r.add(new PotionEffect(ModPotions.potionEarthId, 18000));
                break;
            case 3:

                r.add(new PotionEffect(ModPotions.potionWaterId, 18000));
                break;
        }

        return r;
    }

    @Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
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

    public Block getCropBlock(ItemStack stack){
        Aspect a=getAspect(stack);
        if (a==Aspect.FIRE){
            return ModBlocks.grainFire;
        }
        if (a==Aspect.WATER){
            return ModBlocks.grainWater;
        }
        if (a==Aspect.EARTH){
            return ModBlocks.grainEarth;
        }
        return ModBlocks.grainAir;


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
