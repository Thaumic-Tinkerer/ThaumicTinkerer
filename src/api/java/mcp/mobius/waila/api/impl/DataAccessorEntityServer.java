package mcp.mobius.waila.api.impl;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import mcp.mobius.waila.api.IWailaEntityAccessorServer;

public class DataAccessorEntityServer implements IWailaEntityAccessorServer{

	int            dim;
	World          world;
	EntityPlayerMP player;
	Vec3           hitVector;
	EnumFacing     face;
	Entity         entity;
	
	public DataAccessorEntityServer(int dim_, World world_, EntityPlayerMP player_, Vec3 hitVector_, EnumFacing face_, Entity entity_){
		this.dim = dim_;
		this.world = world_;
		this.player = player_;
		this.hitVector = hitVector_;
		this.entity = entity_;
	}	
	
	@Override
	public int getDimension() {
		return this.dim;
	}

	@Override
	public World getWorld() {
		return this.world;
	}

	@Override
	public EntityPlayerMP getPlayer() {
		return this.player;
	}

	@Override
	public Entity getEntity() {
		return this.entity;
	}

	@Override
	public Vec3 getHitVector() {
		return this.hitVector;
	}

	@Override
	public EnumFacing getHitFace() {
		return this.face;
	}	
	
}
