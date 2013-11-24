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
 * File Created @ [Nov 24, 2013, 2:49:01 PM (GMT)]
 */
package vazkii.tinkerer.common.item.foci;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.config.Config;
import thaumcraft.common.items.wands.ItemWandCasting;

public class ItemFocusEnderChest extends ItemModFocus {

	private static final AspectList visUsage = new AspectList().add(Aspect.ENTROPY, 100).add(Aspect.ORDER, 100);
	
	public ItemFocusEnderChest(int par1) {
		super(par1);
	}
	
	@Override
	public ItemStack onFocusRightClick(ItemStack stack, World world, EntityPlayer p, MovingObjectPosition pos) {
		ItemWandCasting wand = (ItemWandCasting) stack.getItem();
		
		if(wand.consumeAllVis(stack, p, visUsage, true)) {
			p.displayGUIChest(p.getInventoryEnderChest());
			world.playSoundAtEntity(p, "mob.endermen.portal", 1F, 1F);
		}
		
		return stack;
	}
	
	@Override
	public boolean acceptsEnchant(int paramInt) {
		return paramInt == Config.enchFrugal.effectId;
	}

	@Override
	public int getFocusColor() {
		return 0x132223;
	}
	
	@Override
	boolean hasDepth() {
		return true;
	}
	
	@Override
	public String getSortingHelper(ItemStack paramItemStack) {
		return "ENDERCHEST";
	}

	@Override
	public AspectList getVisCost() {
		return visUsage;
	}

}
