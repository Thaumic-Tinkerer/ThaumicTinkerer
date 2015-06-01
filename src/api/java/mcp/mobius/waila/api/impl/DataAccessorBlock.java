package mcp.mobius.waila.api.impl;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3i;
import net.minecraft.world.World;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.utils.NBTUtil;

public class DataAccessorBlock implements IWailaDataAccessor {

	public World world;
	public EntityPlayer player;
	public MovingObjectPosition mop;
	public Vec3 renderingvec = null;
	public Block block;
	//public int blockID;
	public IBlockState blockState;
	public TileEntity entity;
	public NBTTagCompound remoteNbt = null;
	public long timeLastUpdate = System.currentTimeMillis();
	public double partialFrame;
	public ItemStack stack;
	
	public static DataAccessorBlock instance = new DataAccessorBlock();

	public void set(World _world, EntityPlayer _player, MovingObjectPosition _mop) {
		this.set(_world, _player, _mop, null, 0.0);
	}
	
	public void set(World _world, EntityPlayer _player, MovingObjectPosition _mop, Entity viewEntity, double partialTicks) {
		this.world    = _world;
		this.player   = _player;
		this.mop      = _mop;
		//this.blockID  = world.getBlockId(_mop.blockX, _mop.blockY, _mop.blockZ);
		//this.block    = Block.blocksList[this.blockID];
		this.block      = world.getBlockState(_mop.getBlockPos()).getBlock();
		this.blockState = world.getBlockState(_mop.getBlockPos());
		this.entity   = world.getTileEntity(_mop.getBlockPos());
		
		try{ this.stack = new ItemStack(this.block, 1, this.block.getMetaFromState(this.blockState)); } catch (Exception e) {}
			
		
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
	public Block getBlock() {
		return this.block;
	}

	//@Override
	//public int getBlockID() {
	//	return this.blockID;
	//}	
	
	@Override
	public int getMetadata() {
		return this.block.getMetaFromState(this.blockState);
	}

	public void setBlock(Block block) {
		this.block = block;
	}

	//public void setBlockID(int val) {
	//	this.blockID = val;
	//}	
	
	/*
	public void setMetadata(IBlockState val) {
		this.blockState = val;
	}
	*/	
	
	@Override
	public TileEntity getTileEntity() {
		return this.entity;
	}

	@Override
	public BlockPos getPosition() {
		return this.mop.getBlockPos();
	}
	
	@Override
	public MovingObjectPosition getMOP(){
		return this.mop;
	}

	@Override
	public NBTTagCompound getNBTData() {
		if (this.entity == null) return null;
		
		if (this.isTagCorrect(this.remoteNbt))
			return this.remoteNbt;

		NBTTagCompound tag = new NBTTagCompound();		
		try{
			this.entity.writeToNBT(tag);
		} catch (Exception e){}
		return tag;		
	}

	@Override
	public int getNBTInteger(NBTTagCompound tag, String keyname){
		return NBTUtil.getNBTInteger(tag, keyname);
	}
	
	private boolean isTagCorrect(NBTTagCompound tag){
		if (tag == null){
			this.timeLastUpdate = System.currentTimeMillis() - 250;
			return false;
		}
		
		int x = tag.getInteger("WailaX");
		int y = tag.getInteger("WailaY");
		int z = tag.getInteger("WailaZ");
		
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
	public Vec3 getRenderingPosition() {
		return this.renderingvec;
	}

	@Override
	public double getPartialFrame() {
		return this.partialFrame;
	}
	
	@Override
	public EnumFacing getSide(){
		return this.getMOP().sideHit;
	}
	
	public boolean isTimeElapsed(long time){
		return System.currentTimeMillis() - this.timeLastUpdate >= time;
	}
	
	public void resetTimer(){
		this.timeLastUpdate = System.currentTimeMillis();
	}

	@Override
	public ItemStack getStack() {
		return this.stack;
	}

	@Override
	public IBlockState getBlockState() {
		return this.blockState;
	}
	
}
