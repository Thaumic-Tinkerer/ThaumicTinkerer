package vazkii.tinkerer.common.dim;

import net.minecraft.entity.Entity;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class TeleporterBedrock extends Teleporter {
	public TeleporterBedrock(WorldServer p_i1963_1_) {
		super(p_i1963_1_);
	}

	@Override
	public void removeStalePortalLocations(long par1) {
		super.removeStalePortalLocations(par1);
	}

	@Override
	public boolean makePortal(Entity par1Entity) {
		return true;
	}

	@Override
	public boolean placeInExistingPortal(Entity par1Entity, double par2, double par4, double par6, float par8) {
		return true;
	}

	@Override
	public void placeInPortal(Entity par1Entity, double par2, double par4, double par6, float par8) {
	}
}
