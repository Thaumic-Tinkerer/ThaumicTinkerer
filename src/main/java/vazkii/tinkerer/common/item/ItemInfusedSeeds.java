package vazkii.tinkerer.common.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;
import thaumcraft.api.aspects.Aspect;

/**
 * Created by pixlepix on 4/14/14.
 */
public class ItemInfusedSeeds extends ItemSeeds {

    public ItemInfusedSeeds(Block p_i45352_1_, Block p_i45352_2_) {
        super(p_i45352_1_, p_i45352_2_);
    }

    public Aspect getAspect(ItemStack stack){
        return PRIMAL_ASPECT_ENUM.values()[stack.getItemDamage()].aspect;
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

}
