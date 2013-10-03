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
 * File Created @ [12 Sep 2013, 01:29:47 (GMT)]
 */
package vazkii.tinkerer.common.core.helper;

import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.client.codechicken.core.vec.Vector3;

public final class MiscHelper {

	public static MinecraftServer server() {
		return MinecraftServer.getServer();
	}

	public static void setEntityMotionFromVector(Entity entity, Vector3 originalPosVector, float modifier) {
		Vector3 entityVector = Vector3.fromEntityCenter(entity);
		Vector3 finalVector = originalPosVector.copy().subtract(entityVector);

		if(finalVector.mag() > 1)
			finalVector.normalize();

		entity.motionX = finalVector.x * modifier;
		entity.motionY = finalVector.y * modifier;
		entity.motionZ = finalVector.z * modifier;
	}

	public static AspectList multiplyAspectList(AspectList list, double multiplier) {
		AspectList newList = list.copy();
		if(multiplier == 1)
			return newList;

		for(Aspect aspect : newList.aspects.keySet())
			newList.aspects.put(aspect, (int) ((double) newList.aspects.get(aspect) * multiplier));

		return newList;
	}

	public static void printCurrentStackTrace(String message) {
		if(message != null)
			System.out.println(message);

		for(StackTraceElement element : Thread.currentThread().getStackTrace())
			System.out.println(element);
	}

}
