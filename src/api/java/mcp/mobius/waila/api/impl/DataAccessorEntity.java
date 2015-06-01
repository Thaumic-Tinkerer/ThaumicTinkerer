package mcp.mobius.waila.api.impl;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import mcp.mobius.waila.api.IWailaEntityAccessor;
import mcp.mobius.waila.utils.NBTUtil;

public class DataAccessorEntity implements IWailaEntityAccessor {

	public World world;
	public EntityPlayer player;
	public MovingObjectPosition mop;
	public Vec3 renderingvec = null;
	public Entity entity;
	public NBTTagCompound remoteNbt = null;
	public long timeLastUpdate = System.currentTimeMillis();
	public double partialFrame;
	
	public static DataAccessorEntity instance = new DataAccessorEntity();

	public void set(World _world, EntityPlayer _player, MovingObjectPosition _mop) {
		this.set(_world, _player, _mop, null, 0.0);
	}
	
	public void set(World _world, EntityPlayer _player, MovingObjectPosition _mop, EntityLivingBase viewEntity, double partialTicks) {
		this.world    = _world;
		this.player   = _player;
		this.mop      = _mop;
		this.entity   = _mop.entityHit;
		
		if (viewEntity != null){
			double px = viewEntity.lastTickPosX + (viewEntity.posX - viewEntity.lastTickPosX) * partialTicks;
			double py = viewEntity.lastTickPosY + (viewEntity.posY - viewEntity.lastTickPosY) * partialTicks;
			double pz = viewEntity.lastTickPosZ + (viewEntity.posZ - viewEntity.lastTickPosZ) * partialTicks;
			BlockPos pos = _mop.getBlockPos();
			this.renderingvec = new Vec3(pos.getX() - px, pos.getY() - py, pos.getZ() - pz);			
			this.partialFrame = partialTicks;
		}
	}	
	
	@Override
	public World getWorld() {
		return this.world;
	}

	@Override
	public EntityPlayer getPlayer() {
		return this.player;
	}

	@Override
	public Entity getEntity() {
		return this.entity;
	}

	@Override
	public MovingObjectPosition getMOP() {
		return this.mop;
	}

	@Override
	public NBTTagCompound getNBTData() {
		if (this.entity == null) return null;
		
		if (this.isTagCorrect(this.remoteNbt))
			return this.remoteNbt;

		NBTTagCompound tag = new NBTTagCompound();
		this.entity.writeToNBT(tag);
		return tag;
	}

	private boolean isTagCorrect(NBTTagCompound tag){
		if (tag == null){
			this.timeLastUpdate = System.currentTimeMillis() - 250;
			return false;
		}
		
		int x = tag.getInteger("x");
		int y = tag.getInteger("y");
		int z = tag.getInteger("z");
		
		BlockPos posNBT = new BlockPos(x, y, z);
		BlockPos posMOP = this.mop.getBlockPos();
		
		if (posNBT.equals(posMOP))
			return true;
		else {
			this.timeLastUpdate = System.currentTimeMillis() - 250;			
			return false;
		}
	}	
	
	@Override
	public int getNBTInteger(NBTTagCompound tag, String keyname){
		return NBTUtil.getNBTInteger(tag, keyname);
	}

	@Override
	public Vec3 getRenderingPosition() {
		return this.renderingvec;
	}

	@Override
	public double getPartialFrame() {
		return this.partialFrame;
	}

	public boolean isTimeElapsed(long time){
		return System.currentTimeMillis() - this.timeLastUpdate >= time;
	}
	
	public void resetTimer(){
		this.timeLastUpdate = System.currentTimeMillis();
	}	
	
}
