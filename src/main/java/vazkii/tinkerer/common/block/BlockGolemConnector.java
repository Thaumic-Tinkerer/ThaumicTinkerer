package vazkii.tinkerer.common.block;

import cpw.mods.fml.common.Loader;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.research.ResearchPage;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.config.ConfigItems;
import vazkii.tinkerer.common.block.tile.TileCamo;
import vazkii.tinkerer.common.block.tile.TileGolemConnector;
import vazkii.tinkerer.common.lib.LibBlockNames;
import vazkii.tinkerer.common.lib.LibResearch;
import vazkii.tinkerer.common.registry.ThaumicTinkererArcaneRecipe;
import vazkii.tinkerer.common.registry.ThaumicTinkererRecipe;
import vazkii.tinkerer.common.research.IRegisterableResearch;
import vazkii.tinkerer.common.research.ResearchHelper;
import vazkii.tinkerer.common.research.TTResearchItem;

import java.util.ArrayList;

public class BlockGolemConnector extends BlockCamo {

	protected BlockGolemConnector() {
		super(Material.wood);
	}

	@Override
	public TileCamo createNewTileEntity(World world, int meta) {
		// TODO Auto-generated method stub
		return new TileGolemConnector();
	}

	@Override
	public boolean isOpaqueCube() {
		return true;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return true;
	}

	@Override
	public ArrayList<Object> getSpecialParameters() {
		return null;
	}

	@Override
	public String getBlockName() {
		return LibBlockNames.GOLEMCONNECTOR;
	}

	@Override
	public boolean shouldRegister() {
		return Loader.isModLoaded("ComputerCraft");
	}

	@Override
	public boolean shouldDisplayInTab() {
		return true;
	}

	@Override
	public IRegisterableResearch getResearchItem() {
		return (IRegisterableResearch) new TTResearchItem(LibResearch.KEY_GOLEMCONNECTOR, new AspectList().add(Aspect.ORDER, 1).add(Aspect.TRAVEL, 2).add(Aspect.TOOL, 1), 1, 0, 0, new ItemStack(ModBlocks.golemConnector)).setParents(LibResearch.KEY_PERIPHERALS).setParentsHidden("GOLEMBELL").setConcealed().setRound()
				.setPages(new ResearchPage("0"), ResearchHelper.arcaneRecipePage(LibResearch.KEY_GOLEMCONNECTOR), new ResearchPage("1"), ResearchHelper.arcaneRecipePage(LibResearch.KEY_INTERFACE + "1"), new ResearchPage("2"), new ResearchPage("3"));
	}

	@Override
	public ThaumicTinkererRecipe getRecipeItem() {
		return new ThaumicTinkererArcaneRecipe(LibResearch.KEY_GOLEMCONNECTOR, LibResearch.KEY_GOLEMCONNECTOR, new ItemStack(ModBlocks.golemConnector), new AspectList().add(Aspect.AIR, 20).add(Aspect.ORDER, 5).add(Aspect.ENTROPY, 15),
				"WFW", "sIs", "WFW",
				'I', new ItemStack(ConfigItems.itemGolemBell),
				's', new ItemStack(Items.ender_pearl),
				'W', new ItemStack(ConfigBlocks.blockMagicalLog),
				'F', new ItemStack(Blocks.redstone_block));
	}
}