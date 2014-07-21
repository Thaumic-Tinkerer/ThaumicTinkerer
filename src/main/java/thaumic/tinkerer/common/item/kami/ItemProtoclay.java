/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the ThaumicTinkerer Mod.
 *
 * ThaumicTinkerer is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * ThaumicTinkerer is a Derivative Work on Thaumcraft 4.
 * Thaumcraft 4 (c) Azanor 2012
 * (http://www.minecraftforum.net/topic/1585216-)
 *
 * File Created @ [Jan 9, 2014, 10:01:41 PM (GMT)]
 */
package thaumic.tinkerer.common.item.kami;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.research.ResearchPage;
import thaumic.tinkerer.client.core.proxy.TTClientProxy;
import thaumic.tinkerer.common.ThaumicTinkerer;
import thaumic.tinkerer.common.item.kami.tool.IAdvancedTool;
import thaumic.tinkerer.common.item.kami.tool.ToolHandler;
import thaumic.tinkerer.common.lib.LibItemNames;
import thaumic.tinkerer.common.lib.LibResearch;
import thaumic.tinkerer.common.registry.ItemKamiBase;
import thaumic.tinkerer.common.registry.ThaumicTinkererInfusionRecipe;
import thaumic.tinkerer.common.registry.ThaumicTinkererRecipe;
import thaumic.tinkerer.common.research.IRegisterableResearch;
import thaumic.tinkerer.common.research.KamiResearchItem;
import thaumic.tinkerer.common.research.ResearchHelper;

public class ItemProtoclay extends ItemKamiBase {

	public ItemProtoclay() {
		super();
		setMaxStackSize(1);
	}

	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5) {
		if (!(par3Entity instanceof EntityPlayer))
			return;

		EntityPlayer player = (EntityPlayer) par3Entity;
		ItemStack currentStack = player.getCurrentEquippedItem();
		if (currentStack == null || !(currentStack.getItem() instanceof IAdvancedTool))
			return;
		IAdvancedTool tool = (IAdvancedTool) currentStack.getItem();

		if (tool.getType().equals("sword"))
			return;

		MovingObjectPosition pos = ToolHandler.raytraceFromEntity(par2World, par3Entity, true, 4.5F);
		String typeToFind = "";

		if (player.isSwingInProgress && pos != null) {
			Block block = par2World.getBlock(pos.blockX, pos.blockY, pos.blockZ);

			if (block != null) {
				Material mat = block.getMaterial();
				if (ToolHandler.isRightMaterial(mat, ToolHandler.materialsPick))
					typeToFind = "pick";
				else if (ToolHandler.isRightMaterial(mat, ToolHandler.materialsShovel))
					typeToFind = "shovel";
				else if (ToolHandler.isRightMaterial(mat, ToolHandler.materialsAxe))
					typeToFind = "axe";
			}
		}

		if (tool.getType().equals(typeToFind) || typeToFind.isEmpty())
			return;

		for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
			ItemStack stackInSlot = player.inventory.getStackInSlot(i);
			if (stackInSlot != null && stackInSlot.getItem() instanceof IAdvancedTool && stackInSlot != currentStack) {
				IAdvancedTool toolInSlot = (IAdvancedTool) stackInSlot.getItem();
				if (toolInSlot.getType().equals(typeToFind)) {
					player.inventory.setInventorySlotContents(player.inventory.currentItem, stackInSlot);
					player.inventory.setInventorySlotContents(i, currentStack);
					break;
				}
			}
		}
	}

	@Override
	public EnumRarity getRarity(ItemStack par1ItemStack) {
		return TTClientProxy.kamiRarity;
	}

	@Override
	public String getItemName() {
		return LibItemNames.PROTOCLAY;
	}

	@Override
	public IRegisterableResearch getResearchItem() {
		return (IRegisterableResearch) new KamiResearchItem(LibResearch.KEY_PROTOCLAY, new AspectList().add(Aspect.TOOL, 2).add(Aspect.MINE, 1).add(Aspect.MAN, 1).add(Aspect.MECHANISM, 1), 12, 17, 5, new ItemStack(this)).setParents(LibResearch.KEY_ICHOR_PICK_GEM).setParentsHidden(LibResearch.KEY_ICHOR_SHOVEL_GEM)
				.setPages(new ResearchPage("0"), ResearchHelper.infusionPage(LibResearch.KEY_PROTOCLAY));

	}

	@Override
	public ThaumicTinkererRecipe getRecipeItem() {
		return new ThaumicTinkererInfusionRecipe(LibResearch.KEY_PROTOCLAY, new ItemStack(this), 4, new AspectList().add(Aspect.MINE, 16).add(Aspect.TOOL, 16), new ItemStack(Items.clay_ball),

				new ItemStack(Blocks.dirt), new ItemStack(Blocks.stone), new ItemStack(Blocks.log), new ItemStack(ThaumicTinkerer.registry.getFirstItemFromClass(ItemKamiResource.class), 1, 7));

	}
}
