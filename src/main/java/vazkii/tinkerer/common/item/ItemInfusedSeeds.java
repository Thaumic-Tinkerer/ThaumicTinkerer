package vazkii.tinkerer.common.item;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import thaumcraft.api.aspects.Aspect;
import vazkii.tinkerer.common.block.ModBlocks;

import java.util.List;

/**
 * Created by pixlepix on 4/14/14.
 */
public class ItemInfusedSeeds extends ItemSeeds {

    public ItemInfusedSeeds() {
        super(Blocks.wheat, Blocks.farmland);
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
        if (a==Aspect.AIR){
            return ModBlocks.grainAir;
        }
        if (a==Aspect.ORDER){
            return ModBlocks.grainOrder;
        }
        return ModBlocks.grainChaos;

    }

    @Override
    public boolean getHasSubtypes() {
        return true;
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tab, List l) {
        super.getSubItems(item, tab, l);
        for(PRIMAL_ASPECT_ENUM primal:PRIMAL_ASPECT_ENUM.values()){
            l.add(new ItemStack(item, 1, primal.ordinal()));
        }
    }

    private enum PRIMAL_ASPECT_ENUM{
        AIR(Aspect.AIR),
        FIRE(Aspect.FIRE),
        EARTH(Aspect.EARTH),
        WATER(Aspect.WATER),
        ORDER(Aspect.ORDER),
        CHAOS(Aspect.ENTROPY);
        Aspect aspect;
        PRIMAL_ASPECT_ENUM(Aspect a){
            this.aspect=a;
        }
    }
    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
        if (par7 != 1)
        {
            return false;
        }
        else if (par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7, par1ItemStack) && par2EntityPlayer.canPlayerEdit(par4, par5 + 1, par6, par7, par1ItemStack))
        {
            if (par3World.getBlock(par4, par5, par6).canSustainPlant(par3World, par4, par5, par6, ForgeDirection.UP, this) && par3World.isAirBlock(par4, par5 + 1, par6))
            {
                par3World.setBlock(par4, par5 + 1, par6, getCropBlock(par1ItemStack));
                --par1ItemStack.stackSize;
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            return false;
        }
    }

}
