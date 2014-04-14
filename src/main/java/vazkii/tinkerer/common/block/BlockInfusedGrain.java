package vazkii.tinkerer.common.block;

import net.minecraft.block.BlockCrops;
import thaumcraft.api.aspects.Aspect;

/**
 * Created by pixlepix on 4/14/14.
 */
public class BlockInfusedGrain extends BlockCrops {

    Aspect aspect;

    public BlockInfusedGrain(Aspect aspect){
        super();
        this.aspect=aspect;
    }

}
