package vazkii.tinkerer.common.potion;

import net.minecraft.tileentity.TileEntity;
import vazkii.tinkerer.common.ThaumicTinkerer;

/**
 * Created by pixlepix on 4/20/14.
 */
public class TileForcefield extends TileEntity {

	public int ticks = 60;

	@Override
	public void updateEntity() {
		if (ticks < 0) {
			worldObj.setBlockToAir(xCoord, yCoord, zCoord);
		}
		ticks--;

		ThaumicTinkerer.tcProxy.blockSparkle(worldObj, xCoord, yCoord, zCoord, 255, 1);
	}
}
