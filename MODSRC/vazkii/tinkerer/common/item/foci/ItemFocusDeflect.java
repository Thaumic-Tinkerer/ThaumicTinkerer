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
 * File Created @ [Dec 8, 2013, 6:26:09 PM (GMT)]
 */
package vazkii.tinkerer.common.item.foci;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.config.Config;

public class ItemFocusDeflect extends ItemModFocus {

	AspectList visUsage = new AspectList().add(Aspect.ORDER, 15).add(Aspect.AIR, 5);
	
	public ItemFocusDeflect(int par1) {
		super(par1);
	}
	
	@Override
	public void onUsingFocusTick(ItemStack paramItemStack, EntityPlayer paramEntityPlayer, int paramInt) {
		// TODO
	}
	
	@Override
	public boolean isVisCostPerTick() {
		return true;
	}
	
	@Override
	public boolean acceptsEnchant(int paramInt) {
		return paramInt == Config.enchFrugal.effectId;
	}

	@Override
	public int getFocusColor() {
		return 0xFFFFFF;
	}

	@Override
	public AspectList getVisCost() {
		return visUsage;
	}

}
