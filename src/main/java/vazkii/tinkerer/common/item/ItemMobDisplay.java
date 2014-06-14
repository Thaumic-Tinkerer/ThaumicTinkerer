package vazkii.tinkerer.common.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityList;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import vazkii.tinkerer.common.core.helper.EnumMobAspect;
import vazkii.tinkerer.common.core.helper.ItemNBTHelper;
import vazkii.tinkerer.common.lib.LibItemNames;
import vazkii.tinkerer.common.registry.ItemBase;
import vazkii.tinkerer.common.registry.ThaumicTinkererRecipe;
import vazkii.tinkerer.common.research.IRegisterableResearch;

import java.util.List;

/**
 * Created by Katrina on 11/03/14.
 */
public class ItemMobDisplay extends ItemBase {
	public static final String TAG_TYPE = "type";

	public ItemMobDisplay() {
		super();
		setHasSubtypes(true); // This allows the item to be marked as a metadata item.
		setMaxDamage(0); // This makes it so your item doesn't have the damage bar at the bottom of its icon, when "damaged" similar to the Tools.
	}

	@Override
	public boolean shouldDisplayInTab() {
		return false;
	}

	@Override
	public boolean shouldRegister() {
		return false;
	}

	@Override
	public IRegisterableResearch getResearchItem() {
		return null;
	}

	@Override
	public ThaumicTinkererRecipe getRecipeItem() {
		return null;
	}

	public EnumMobAspect getEntityType(ItemStack stack) {
		return EnumMobAspect.getMobAspectForType(ItemNBTHelper.getString(stack, TAG_TYPE, ""));
	}

	public void setEntityType(ItemStack stack, String type) {
		ItemNBTHelper.setString(stack, TAG_TYPE, type);
	}

	@Override
	public void getSubItems(Item par1Item, CreativeTabs par2CreativeTabs, List list) {
		super.getSubItems(par1Item, par2CreativeTabs, list);
		for (EnumMobAspect aspect : EnumMobAspect.values()) {
			Class aspClass = aspect.getEntityClass();
			String name = (String) EntityList.classToStringMapping.get(aspClass);
			ItemStack item = new ItemStack(this);
			this.setEntityType(item, name);
			list.add(item);

		}
	}

	@Override
	public String getItemName() {
		return LibItemNames.MOB_DISPLAY;
	}
}
