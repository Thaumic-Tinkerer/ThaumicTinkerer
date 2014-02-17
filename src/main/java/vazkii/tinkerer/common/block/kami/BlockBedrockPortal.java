package vazkii.tinkerer.common.block.kami;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.Facing;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import vazkii.tinkerer.client.core.helper.IconHelper;
import vazkii.tinkerer.common.ThaumicTinkerer;
import vazkii.tinkerer.common.block.tile.kami.TileBedrockPortal;
import vazkii.tinkerer.common.core.handler.ModCreativeTab;
import vazkii.tinkerer.common.dim.TeleporterBedrock;
import vazkii.tinkerer.common.dim.WorldProviderBedrock;
import vazkii.tinkerer.common.lib.LibBlockIDs;

import java.util.Random;

public class BlockBedrockPortal extends Block {

	public BlockBedrockPortal(int id) {
		super(id, Material.portal);
		setStepSound(soundStoneFootstep);
		setResistance(6000000.0F);
		disableStats();
		setCreativeTab(ModCreativeTab.INSTANCE);
	}

	@SideOnly(Side.CLIENT)
	private Icon icon;

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {

		icon = IconHelper.forName(iconRegister, "portal");

	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int par1, int meta) {
		return icon;
	}


	@Override
	public float getBlockHardness(World par1World, int par2, int par3, int par4) {
		return -1;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4) {
		return null;
	}

	@Override
	public boolean canHarvestBlock(EntityPlayer player, int meta) {
		return false;
	}

	@Override
	public TileEntity createTileEntity(World world, int metadata) {
		return new TileBedrockPortal();
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5) {
		if (par5 != 1 && par5 != 0 && !super.shouldSideBeRendered(par1IBlockAccess, par2, par3, par4, par5)) {
			return false;
		} else {
			int i1 = par2 + Facing.offsetsXForSide[Facing.oppositeSide[par5]];
			int j1 = par3 + Facing.offsetsYForSide[Facing.oppositeSide[par5]];
			int k1 = par4 + Facing.offsetsZForSide[Facing.oppositeSide[par5]];
			boolean flag = (par1IBlockAccess.getBlockMetadata(i1, j1, k1) & 8) != 0;
			return flag ? (par5 == 0 || (par5 == 1 && super.shouldSideBeRendered(par1IBlockAccess, par2, par3, par4, par5) ? true : true)) : (par5 == 1 ? true : (par5 == 0 && super.shouldSideBeRendered(par1IBlockAccess, par2, par3, par4, par5) ? true : true));
		}
	}

	@Override
	public void onEntityCollidedWithBlock(World par1World, int par2, int par3, int par4, Entity entity) {
		super.onEntityCollidedWithBlock(par1World, par2, par3, par4, entity);

		if(entity.worldObj.provider.isSurfaceWorld()){

			if(entity instanceof EntityPlayer && !par1World.isRemote){

				FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().transferPlayerToDimension((EntityPlayerMP) entity, ThaumicTinkerer.dimID, new TeleporterBedrock((WorldServer) par1World));

				if(entity.worldObj.getBlockId(par2, 251, par4) == 7){
					entity.worldObj.setBlock(par2, 251, par4, 0);
				}
				if(entity.worldObj.getBlockId(par2, 252, par4) == 7){
					entity.worldObj.setBlock(par2, 252, par4, 0);
				}
				if(entity.worldObj.getBlockId(par2, 253, par4) == 7){
					entity.worldObj.setBlock(par2, 253, par4, 0);
				}if(entity.worldObj.getBlockId(par2, 254, par4) == 7){
					entity.worldObj.setBlock(par2, 254, par4, LibBlockIDs.idPortal);
				}
				((EntityPlayerMP) entity).playerNetServerHandler.setPlayerLocation(par2 + 0.5, 252, par4 + 0.5, 0, 0);
			}
		}else if(entity.worldObj.provider instanceof WorldProviderBedrock){
			if(entity instanceof EntityPlayer && !par1World.isRemote){

				FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().transferPlayerToDimension((EntityPlayerMP) entity, 0, new TeleporterBedrock((WorldServer) par1World));

				Random rand = new Random();

				int x = (int) entity.posX + rand.nextInt(100);
				int z = (int) entity.posZ + rand.nextInt(100);

				x-=50;
				z-=50;

				int y=120;

				while(entity.worldObj.getBlockId(x, y, z) == 0 || Block.blocksList[entity.worldObj.getBlockId(x, y, z)].isAirBlock(par1World, x, y, z)){
					y--;
				}


				((EntityPlayerMP) entity).playerNetServerHandler.setPlayerLocation(x + 0.5, y + 3, z + 0.5, 0, 0);
			}
		}



	}

	public void travelToDimension(int par1, Entity e)
	{
		if (!e.worldObj.isRemote && !e.isDead)
		{
			e.worldObj.theProfiler.startSection("changeDimension");
			MinecraftServer minecraftserver = MinecraftServer.getServer();
			int j = e.dimension;
			WorldServer worldserver = minecraftserver.worldServerForDimension(j);
			WorldServer worldserver1 = minecraftserver.worldServerForDimension(par1);
			e.dimension = par1;

			if (j == 1 && par1 == 1)
			{
				worldserver1 = minecraftserver.worldServerForDimension(0);
				e.dimension = 0;
			}

			e.worldObj.removeEntity(e);
			e.isDead = false;
			e.worldObj.theProfiler.startSection("reposition");
			minecraftserver.getConfigurationManager().transferEntityToWorld(e, j, worldserver, worldserver1, new TeleporterBedrock(worldserver));
			e.worldObj.theProfiler.endStartSection("reloading");
			Entity entity = EntityList.createEntityByName(EntityList.getEntityString(e), worldserver1);

			if (entity != null)
			{
				entity.copyDataFrom(e, true);

				if (j == 1 && par1 == 1)
				{
					ChunkCoordinates chunkcoordinates = worldserver1.getSpawnPoint();
					chunkcoordinates.posY = e.worldObj.getTopSolidOrLiquidBlock(chunkcoordinates.posX, chunkcoordinates.posZ);
					entity.setLocationAndAngles((double)chunkcoordinates.posX, (double)chunkcoordinates.posY, (double)chunkcoordinates.posZ, entity.rotationYaw, entity.rotationPitch);
				}

				worldserver1.spawnEntityInWorld(entity);
			}

			e.isDead = true;
			e.worldObj.theProfiler.endSection();
			worldserver.resetUpdateEntityTick();
			worldserver1.resetUpdateEntityTick();
			e.worldObj.theProfiler.endSection();
		}
	}



}