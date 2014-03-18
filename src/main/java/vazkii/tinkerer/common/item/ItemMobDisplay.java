package vazkii.tinkerer.common.item;

import cpw.mods.fml.common.registry.EntityRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import vazkii.tinkerer.common.core.helper.EnumMobAspect;
import vazkii.tinkerer.common.core.helper.ItemNBTHelper;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Katrina on 11/03/14.
 */
public class ItemMobDisplay extends ItemMod {
    public static final String TAG_TYPE="type";


    public ItemMobDisplay(int par1) {
        super(par1);
        setHasSubtypes(true); // This allows the item to be marked as a metadata item.
        setMaxDamage(0); // This makes it so your item doesn't have the damage bar at the bottom of its icon, when "damaged" similar to the Tools.
    }


    public EnumMobAspect getEntityType(ItemStack stack)
    {
        return EnumMobAspect.getMobAspectForType(ItemNBTHelper.getString(stack, TAG_TYPE, ""));
    }
    public void setEntityType(ItemStack stack,String type)
    {
        ItemNBTHelper.setString(stack, TAG_TYPE, type);
    }
    @Override
    public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List list) {
        super.getSubItems(par1, par2CreativeTabs, list);
        for(EnumMobAspect aspect:EnumMobAspect.values())
        {
            Class aspClass=aspect.getEntityClass();
            String name= (String) EntityList.classToStringMapping.get(aspClass);
            ItemStack item=new ItemStack(this);
            this.setEntityType(item,name);
            list.add(item);

        }
    }
}
