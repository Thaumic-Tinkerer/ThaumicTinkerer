package mcp.mobius.waila.api;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public interface IWailaDataAccessorServer {
	public int            getDimension();
	public World          getWorld();
	public Block          getBlock();
	public IBlockState    getBlockState();	
	int                   getMetadata();
	public BlockPos       getBlockPos();
	public EntityPlayerMP getPlayer();
	public TileEntity     getTileEntity();
	public Vec3           getHitVector();
	public EnumFacing     getHitFace();
}
