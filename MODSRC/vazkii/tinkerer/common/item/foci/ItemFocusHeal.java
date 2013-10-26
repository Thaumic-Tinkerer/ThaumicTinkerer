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
 * File Created @ [26 Oct 2013, 12:04:52 (GMT)]
 */
package vazkii.tinkerer.common.item.foci;

import net.minecraft.item.ItemStack;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;

public class ItemFocusHeal extends ItemModFocus {

	private static final AspectList visUsage = new AspectList().add(Aspect.EARTH, 10).add(Aspect.WATER, 10);
	
	public ItemFocusHeal(int par1) {
		super(par1);
	}
	
	@Override
	boolean hasDepth() {
		return true;
	}
	
	@Override
	public boolean isVisCostPerTick() {
		return true;
	}
	
	@Override
	public String getSortingHelper(ItemStack paramItemStack) {
		return "HEAL";
	}

	@Override
	public int getFocusColor() {
		return 0xFD00D6;
	}

	@Override
	public AspectList getVisCost() {
		return visUsage;
	}

}
