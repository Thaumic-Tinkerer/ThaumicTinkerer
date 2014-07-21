package thaumic.tinkerer.common.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import thaumcraft.api.IScribeTools;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.config.ConfigItems;
import thaumic.tinkerer.common.lib.LibItemNames;
import thaumic.tinkerer.common.lib.LibResearch;
import thaumic.tinkerer.common.registry.*;
import thaumic.tinkerer.common.research.IRegisterableResearch;

public class ItemInfusedInkwell extends ItemBase implements IScribeTools {

	public ItemInfusedInkwell() {
		super();

		setMaxDamage(800);
		maxStackSize = 1;
		canRepair = true;
		setHasSubtypes(false);

	}

	@Override
	public boolean shouldDisplayInTab() {
		return false;
	}

	@Override
	public IRegisterableResearch getResearchItem() {
		return null;
	}

	@Override
	public ThaumicTinkererRecipe getRecipeItem() {
		return new ThaumicTinkererRecipeMulti(new ThaumicTinkererCraftingBenchRecipe(LibResearch.KEY_INFUSED_INKWELL + 0, new ItemStack(this),
				"QQQ", "QCQ", "QQQ",
				'Q', new ItemStack(Items.dye, 1, 0),
				'C', new ItemStack(this, 1, 32767)),
				new ThaumicTinkererInfusionRecipe(LibResearch.KEY_INFUSED_INKWELL, new ItemStack(this), 2, new AspectList().add(Aspect.VOID, 8).add(Aspect.DARKNESS, 8), new ItemStack(ConfigItems.itemInkwell),
						new ItemStack(ConfigItems.itemShard, 1, 0), new ItemStack(ConfigBlocks.blockJar), new ItemStack(ConfigItems.itemResource, 1, 3))
		);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int par1) {
		return ConfigItems.itemInkwell.getIconFromDamage(par1);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack par1ItemStack, int pass) {
		return true;
	}

	@Override
	public String getItemName() {
		return LibItemNames.INFUSED_INKWELL;
	}

}
