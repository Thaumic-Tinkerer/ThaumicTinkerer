package thaumic.tinkerer.common.compat;

import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import thaumic.tinkerer.common.ThaumicTinkerer;
import thaumic.tinkerer.common.item.foci.ItemFocusDeflect;
import vazkii.botania.api.internal.IManaBurst;
import vazkii.botania.api.mana.ILens;
import vazkii.botania.api.mana.ILensEffect;

import java.util.List;

/**
 * Created by Katrina on 30/01/2015.
 */
public class BotaniaFunctions {


    public static void AddBotaniaClasses( )
    {
        ItemFocusDeflect.DeflectBlacklist.add(IManaBurst.class);
    }

    public static boolean isEntityHarmless(Entity entity) {
        if(entity instanceof IManaBurst) {
            ItemStack lens = ((IManaBurst)entity).getSourceLens();
            if(lens.getItemDamage()!=8 && lens.getItemDamage()!=11)
                return true;
            else
                return false;
        }
        else
            return true;

    }
}
