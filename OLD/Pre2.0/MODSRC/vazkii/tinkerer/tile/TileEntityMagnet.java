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
 * File Created @ [28 Jun 2013, 18:22:16 (GMT)]
 */
package vazkii.tinkerer.tile;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.ForgeDirection;
import thaumcraft.client.codechicken.core.vec.Vector3;
import vazkii.tinkerer.ThaumicTinkerer;

public class TileEntityMagnet extends TileEntity {

	@Override
	public void updateEntity() {
		int redstone = 0;
		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS)
			redstone = Math.max(redstone, worldObj.getIndirectPowerLevelTo(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ, dir.ordinal()));

		if(redstone > 0) {
			double x1 = xCoord + 0.5;
			double y1 = yCoord + 0.5;
			double z1 = zCoord + 0.5;

			boolean blue = getBlockMetadata() == 0;
    		int speedMod = blue ? 1 : -1;
    		double range = redstone / 2;

    		AxisAlignedBB boundingBox = AxisAlignedBB.getBoundingBox(x1 - range, yCoord, z1 - range, x1 + range, y1 + range, z1 + range);
    		List<EntityItem> items = worldObj.getEntitiesWithinAABB(EntityItem.class, boundingBox);

    		for(EntityItem item : items) {
    			double x2 = item.posX;
    			double y2 = item.posY;
    			double z2 = item.posZ;

    			float distanceSqrd = blue ? (float) ((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2) + (z1 - z2) * (z1 - z2)) : 1.1F;

    			if(distanceSqrd > 1) {
        			setEntityMotionFromVector(item, x1, y1, z1, speedMod * 0.25F);
    				ThaumicTinkerer.tcProxy.sparkle((float) x2, (float) y2, (float) z2, blue ? 2 : 4);
    			}
    		}
    	}
	}

	private static void setEntityMotionFromVector(Entity entity, double x, double y, double z, float modifier) {
		Vector3 originalPosVector = new Vector3(x, y, z);
		Vector3 entityVector = Vector3.fromEntityCenter(entity);
		Vector3 finalVector = originalPosVector.subtract(entityVector).normalize();
		entity.motionX = finalVector.x * modifier;
		entity.motionY = finalVector.y * modifier;
		entity.motionZ = finalVector.z * modifier;
	}

}
