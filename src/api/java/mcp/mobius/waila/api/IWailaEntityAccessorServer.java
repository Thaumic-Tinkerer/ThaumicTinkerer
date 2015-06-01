package mcp.mobius.waila.api;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public interface IWailaEntityAccessorServer {
	public int            getDimension();
	public World          getWorld();
	public EntityPlayerMP getPlayer();
	public Entity         getEntity();
	public Vec3           getHitVector();
	public EnumFacing     getHitFace();
}
