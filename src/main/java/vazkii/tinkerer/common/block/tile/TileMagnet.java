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
 * File Created @ [12 Sep 2013, 17:10:55 (GMT)]
 */
package vazkii.tinkerer.common.block.tile;

import appeng.api.movable.IMovableTile;
import dan200.computer.api.IComputerAccess;
import dan200.computer.api.ILuaContext;
import dan200.computer.api.IPeripheral;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;
import thaumcraft.client.codechicken.core.vec.Vector3;
import vazkii.tinkerer.common.ThaumicTinkerer;
import vazkii.tinkerer.common.core.helper.MiscHelper;

import java.util.List;

public class TileMagnet extends TileEntity implements IPeripheral, IMovableTile {

	@Override
	public void updateEntity() {
		int redstone = 0;
		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS)
			redstone = Math.max(redstone, worldObj.getIndirectPowerLevelTo(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ, dir.ordinal()));

		if(redstone > 0) {
			double x1 = xCoord + 0.5;
			double y1 = yCoord + 0.5;
			double z1 = zCoord + 0.5;

			boolean blue = (getBlockMetadata() & 1) == 0;
    		int speedMod = blue ? 1 : -1;
    		double range = redstone / 2;

    		AxisAlignedBB boundingBox = AxisAlignedBB.getBoundingBox(x1 - range, yCoord, z1 - range, x1 + range, y1 + range, z1 + range);
    		List<Entity> entities = worldObj.selectEntitiesWithinAABB(Entity.class, boundingBox, getEntitySelector());

    		for(Entity entity : entities) {
    			double x2 = entity.posX;
    			double y2 = entity.posY;
    			double z2 = entity.posZ;

    			float distanceSqrd = blue ? (float) ((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2) + (z1 - z2) * (z1 - z2)) : 1.1F;

    			if(distanceSqrd > 1) {
        			MiscHelper.setEntityMotionFromVector(entity, new Vector3(x1, y1, z1), speedMod * 0.25F);
    				ThaumicTinkerer.tcProxy.sparkle((float) x2, (float) y2, (float) z2, blue ? 2 : 4);
    			}
    		}
    	}
	}

	IEntitySelector getEntitySelector() {
		return new IEntitySelector() {

			@Override
			public boolean isEntityApplicable(Entity entity) {
				return entity instanceof EntityItem;
			}

		};
	}

	@Override
	public String getType() {
		return "tt_magnet";
	}

	@Override
	public String[] getMethodNames() {
		return new String[] { "isPulling", "setPulling", "getSignal" };
	}

	@Override
	public Object[] callMethod(IComputerAccess computer, ILuaContext context, int method, Object[] arguments) throws Exception {
		switch(method) {
			case 0 : return new Object[] { (getBlockMetadata() & 1) == 0 };
			case 1 : {
				boolean pull = (Boolean) arguments[0];
				int meta = (getBlockMetadata() & 2) + (pull ? 0 : 1);

				worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, meta, 1 | 2);
				return null;
			}
			case 2 : {
				int redstone = 0;
				for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS)
					redstone = Math.max(redstone, worldObj.getIndirectPowerLevelTo(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ, dir.ordinal()));

				return new Object[] { redstone };
			}
		}

		return null;
	}

	@Override
	public boolean canAttachToSide(int side) {
		return true;
	}

	@Override
	public void attach(IComputerAccess computer) {
		// NO-OP
	}

	@Override
	public void detach(IComputerAccess computer) {
		// NO-OP
	}

	@Override
	public boolean prepareToMove() {
		return true;
	}

	@Override
	public void doneMoving() {

	}

}
