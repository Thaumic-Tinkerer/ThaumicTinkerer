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
 * File Created @ [9 Sep 2013, 19:27:13 (GMT)]
 */
package vazkii.tinkerer.common.item.foci;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.items.wands.ItemWandCasting;
import vazkii.tinkerer.common.ThaumicTinkerer;

public class ItemFocusFlight extends ItemModFocus {

	private static final AspectList visUsage = new AspectList().add(Aspect.AIR, 15);
	
	public ItemFocusFlight(int i) {
		super(i);
	}
	
	@Override
	public ItemStack onFocusRightClick(ItemStack itemstack, World world, EntityPlayer p, MovingObjectPosition movingobjectposition) {
		ItemWandCasting wand = (ItemWandCasting)itemstack.getItem();
		
		if (wand.consumeAllVis(itemstack, p, getVisCost(), true)) {
			Vec3 vec = p.getLookVec();
			double force = 1 / 1.5;
			p.motionX = vec.xCoord * force;
			p.motionY = vec.yCoord * force;
			p.motionZ = vec.zCoord * force;
			p.fallDistance = 0F;

			for(int i = 0; i < 5; i++)
				ThaumicTinkerer.tcProxy.smokeSpiral(world, p.posX, p.posY - p.motionY, p.posZ, 2F, (int) Math.random() * 360, (int) p.posY);
			world.playSoundAtEntity(p, "thaumcraft:wind", 0.4F, 1F);
		}

		if(world.isRemote)
			p.swingItem();

		return itemstack;
	}
	
	@Override
	public String getSortingHelper(ItemStack itemstack) {
		return "FLIGHT";
	}
	
	@Override
	public int getFocusColor() {
		return 0x9EF2FF;
	}
	
	@Override
	boolean hasOrnament() {
		return true;
	}
	
	@Override
	public AspectList getVisCost() {
		return visUsage;
	}
}