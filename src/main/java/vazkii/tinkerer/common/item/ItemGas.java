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
 * File Created @ [8 Sep 2013, 22:12:14 (GMT)]
 */
package vazkii.tinkerer.common.item;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.research.ResearchPage;
import vazkii.tinkerer.common.block.ModBlocks;
import vazkii.tinkerer.common.lib.LibItemNames;
import vazkii.tinkerer.common.lib.LibResearch;
import vazkii.tinkerer.common.registry.ItemBase;
import vazkii.tinkerer.common.research.IRegisterableResearch;
import vazkii.tinkerer.common.research.ResearchHelper;
import vazkii.tinkerer.common.research.TTResearchItem;

import java.util.ArrayList;

public class ItemGas extends ItemBase {

	private Block setBlock;

	public ItemGas(Block setBlock) {
		super();
		this.setBlock = setBlock;
	}

	public ItemGas() {
		this(ModBlocks.gaseousShadow);
	}

	@Override
	public ArrayList<Object> getSpecialParameters() {
		ArrayList<Object> result = new ArrayList<Object>();
		result.add(ModBlocks.gaseousShadow);
		return result;
	}

	@Override
	public boolean shouldDisplayInTab() {
		return true;
	}

	@Override
	public IRegisterableResearch getResearchItem() {
		if (setBlock == ModBlocks.gaseousShadow) {
			IRegisterableResearch research = (TTResearchItem) new TTResearchItem(LibResearch.KEY_GASEOUS_SHADOW, new AspectList().add(Aspect.DARKNESS, 2).add(Aspect.AIR, 1).add(Aspect.MOTION, 4), -1, -5, 2, new ItemStack(this)).setSecondary().setParents(LibResearch.KEY_GASEOUS_LIGHT);
			research.setPages(new ResearchPage("0"), ResearchHelper.crucibleRecipePage(LibResearch.KEY_GASEOUS_SHADOW));
			return research;
		}
		if (setBlock == ModBlocks.gaseousLight) {
			IRegisterableResearch research = (TTResearchItem) new TTResearchItem(LibResearch.KEY_GASEOUS_LIGHT, new AspectList().add(Aspect.LIGHT, 2).add(Aspect.AIR, 1), 0, -3, 1, new ItemStack(this)).setParents("NITOR");
			research.setPages(new ResearchPage("0"), ResearchHelper.crucibleRecipePage(LibResearch.KEY_GASEOUS_LIGHT));
			return research;
		}
		return null;
	}

	@Override
	public String getItemName() {
		return setBlock == ModBlocks.gaseousShadow ? LibItemNames.GASEOUS_SHADOW : LibItemNames.GASEOUS_LIGHT;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
		int x = (int) par3EntityPlayer.posX;
		int y = (int) par3EntityPlayer.posY + 1;
		int z = (int) par3EntityPlayer.posZ;
		boolean air = par2World.isAirBlock(x, y, z);

		if (!par3EntityPlayer.capabilities.isCreativeMode)
			par1ItemStack.stackSize--;

		par2World.playSoundAtEntity(par3EntityPlayer, "random.pop", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

		if (air) {
			if (!par2World.isRemote)
				par2World.setBlock(x, y, z, setBlock, 4, 2);
			else par3EntityPlayer.swingItem();
			par2World.scheduleBlockUpdate(x, y, z, setBlock, 10);
		}

		return par1ItemStack;
	}

}