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
 * File Created @ [Dec 27, 2013, 1:03:05 AM (GMT)]
 */
package vazkii.tinkerer.common.item.kami;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;

import org.lwjgl.util.vector.Vector2f;

import vazkii.tinkerer.common.block.ModBlocks;
import vazkii.tinkerer.common.item.ItemBrightNitor;
import vazkii.tinkerer.common.item.ModItems;

public class ItemGemLegs extends ItemIchorclothArmorAdv {

	public ItemGemLegs(int par1, int par2) {
		super(par1, par2);
	}
	
	@Override
	boolean ticks() {
		return true;
	}
	
	@Override
	void tickPlayer(EntityPlayer player) {
		ItemBrightNitor.meta = 1;
		ModItems.brightNitor.onUpdate(null, player.worldObj, player, 0, false);
		ItemBrightNitor.meta = 0;
		
		int x = (int) Math.floor(player.posX);
		int y = (int) player.posY + 1;
		int z = (int) Math.floor(player.posZ);

        float yaw =  MathHelper.wrapAngleTo180_float(player.rotationYaw + 90F) * (float) Math.PI / 180F;
        Vector2f lookVector = (Vector2f) new Vector2f((float) Math.cos(yaw), (float) Math.sin(yaw)).normalise();
        Vector2f newVector = new Vector2f(lookVector.x, lookVector.y);

        for(int i = 0; i < 5; i++) {
        	Vector2f.add(newVector, lookVector, newVector);
        	
    		int x1 = x + (int) newVector.x;
    		int z1 = z + (int) newVector.y;
    		ItemBrightNitor.setBlock(x1, y, z1, player.worldObj);
        }
	}
}
