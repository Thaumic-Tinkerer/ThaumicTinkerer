/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the ThaumicTinkerer Mod.
 *
 * ThaumicTinkerer is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * ThaumicTinkerer is a Derivative Work on Thaumcraft 3.
 * Thaumcraft 3 © Azanor 2012
 * (http://www.minecraftforum.net/topic/1585216-)
 *
 * File Created @ [24 Apr 2013, 22:59:12 (GMT)]
 */
package vazkii.tinkerer.util.helper;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import thaumcraft.api.aura.AuraNode;
import thaumcraft.common.aura.AuraManager;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public final class MiscHelper {

	@SideOnly(Side.CLIENT)
	public static Minecraft getMc() {
		return Minecraft.getMinecraft();
	}

	@SideOnly(Side.CLIENT)
	public static EntityClientPlayerMP getClientPlayer() {
		return getMc().thePlayer;
	}

	public static String stackAsString(ItemStack stack) {
		return stack == null ? "null" : stack.itemID + ":" + stack.getItemDamage();
	}

	public static AuraNode getClosestNode(World world, double x, double y, double z) {
		List<Integer> nodes = AuraManager.getAurasWithin(world, x, y, z);

		double closestDist = Double.MAX_VALUE;
		AuraNode closestNode = null;

		for(int i : nodes) {
			AuraNode node = AuraManager.getNode(i);

			float value = node.baseLevel / 4F;

			double distX = node.xPos - x;
			double distY = node.yPos - y;
			double distZ = node.zPos - z;

			double dist = distX * distX + distY * distY + distZ * distZ;
			if(value * value >= dist && dist < closestDist) {
				closestDist = dist;
				closestNode = node;
			}
		}

		return closestNode;
	}
}
