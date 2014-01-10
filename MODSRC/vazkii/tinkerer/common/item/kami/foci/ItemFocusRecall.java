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
 * File Created @ [Jan 10, 2014, 9:20:05 PM (GMT)]
 */
package vazkii.tinkerer.common.item.kami.foci;

import net.minecraft.block.Block;
import net.minecraft.util.Icon;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import vazkii.tinkerer.common.item.foci.ItemModFocus;

public class ItemFocusRecall extends ItemModFocus {

	AspectList cost = new AspectList().add(Aspect.AIR, 40).add(Aspect.EARTH, 40).add(Aspect.ORDER, 40);
	
	public ItemFocusRecall(int par1) {
		super(par1);
	}
	
	@Override
	protected boolean hasDepth() {
		return true;
	}

	@Override
	public int getFocusColor() {
		return 0x9CF8FF;
	}

	@Override
	public AspectList getVisCost() {
		return cost;
	}

}
