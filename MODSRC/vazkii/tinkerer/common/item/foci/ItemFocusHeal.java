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

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.config.Config;
import thaumcraft.common.items.wands.ItemWandCasting;
import vazkii.tinkerer.common.ThaumicTinkerer;

public class ItemFocusHeal extends ItemModFocus {

	private static final AspectList visUsage = new AspectList().add(Aspect.EARTH, 50).add(Aspect.WATER, 50);
	
	public ItemFocusHeal(int par1) {
		super(par1);
	}
	
	@Override
	public ItemStack onFocusRightClick(ItemStack itemstack, World world, EntityPlayer p, MovingObjectPosition movingobjectposition) {
		ItemWandCasting wand = (ItemWandCasting)itemstack.getItem();

		if(p.shouldHeal()) {
			if (wand.consumeAllVis(itemstack, p, getVisCost(), true)) {
				p.heal(1 + EnchantmentHelper.getEnchantmentLevel(Config.enchPotency.effectId, wand.getFocusItem(itemstack)));
			
				world.playSoundAtEntity(p, "thaumcraft:upgrade", 0.4F, 1F);
				
				for (int i = 0; i < 32; ++i) {
		            ThaumicTinkerer.tcProxy.sparkle((float)p.posX + p.worldObj.rand.nextFloat() - 0.5F, (float)p.posY + p.worldObj.rand.nextFloat(), (float)p.posZ + p.worldObj.rand.nextFloat() - 0.5F, 3);
		        }
			}
		}
		
		if(world.isRemote)
			p.swingItem();

		return itemstack;
	}
	
	@Override
	boolean hasDepth() {
		return true;
	}
	
	@Override
	public boolean isVisCostPerTick() {
		return false;
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
