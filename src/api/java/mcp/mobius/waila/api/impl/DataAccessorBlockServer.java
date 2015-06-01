package mcp.mobius.waila.api.impl;

import net.minecraft.block.Block;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import mcp.mobius.waila.api.IWailaDataAccessorServer;

public class DataAccessorBlockServer implements IWailaDataAccessorServer{

	int            dim;
	World          world;
	BlockPos       pos;
	EntityPlayerMP player;
	Vec3           hitVector;
	EnumFacing     face;
	IBlockState    state;
	Block          block;
	TileEntity     tileEntity;
	
	public DataAccessorBlockServer(int dim_, World world_, BlockPos pos_, EntityPlayerMP player_, Vec3 hitVector_, EnumFacing face_, TileEntity tileEntity_){
		this.dim = dim_;
		this.world = world_;
		this.pos = pos_;
		this.player = player_;
		this.hitVector = hitVector_;
		this.face = face_;
		this.tileEntity = tileEntity_;		
		this.state = this.world.getBlockState(this.pos);
		this.block = this.state.getBlock();
	}
	
	@Override
	public int getDimension() {
		return this.dim;
	}

	@Override
	public int getMetadata() {
		return this.block.getMetaFromState(this.state);
	}

	@Override
	public BlockPos getBlockPos() {
		return this.pos;
	}

	@Override
	public EntityPlayerMP getPlayer() {
		return this.player;
	}

	@Override
	public IBlockState getBlockState() {
		return this.state;
	}

	@Override
	public Block getBlock() {
		return this.block;
	}

	@Override
	public TileEntity getTileEntity() {
		return this.tileEntity;
	}

	@Override
	public Vec3 getHitVector() {
		return this.hitVector;
	}

	@Override
	public EnumFacing getHitFace() {
		return this.face;
	}

	@Override
	public World getWorld() {
		return this.world;
	}

}
