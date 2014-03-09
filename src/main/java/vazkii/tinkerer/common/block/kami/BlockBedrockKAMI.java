package vazkii.tinkerer.common.block.kami;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import vazkii.tinkerer.common.dim.WorldProviderBedrock;
import vazkii.tinkerer.common.item.kami.tool.ItemIchorPickAdv;
import vazkii.tinkerer.common.lib.LibBlockIDs;

import java.util.ArrayList;
import java.util.Random;

public class BlockBedrockKAMI extends Block {

	public BlockBedrockKAMI() {
		super(7, Material.rock);
		Block.blocksList[7]=this;
		setStepSound(soundStoneFootstep);
		setResistance(6000000.0F);
		setUnlocalizedName("bedrock");
		disableStats();
		setCreativeTab(CreativeTabs.tabBlock);
		setTextureName("bedrock");
		setBlockUnbreakable();
	}

	@Override
	public void harvestBlock(World world, EntityPlayer entityPlayer, int par3, int par4, int par5, int par6) {
		if((world.provider.isSurfaceWorld() && par4<5) || (world.provider instanceof WorldProviderBedrock && par4>253)){
			if(entityPlayer.inventory.getCurrentItem() != null && entityPlayer.inventory.getCurrentItem().getItem() instanceof ItemIchorPickAdv){
				world.setBlock(par3, par4, par5, LibBlockIDs.idPortal);
			}
		}
	}

	public int idDropped(int par1, Random par2Random, int par3){
		return 0;
	}

	public int quantityDropped(Random par1Random){
		return 0;
	}

	@Override
	public float getBlockHardness(World par1World, int par2, int par3, int par4) {
		return -1;
	}

	@Override
	public boolean canEntityDestroy(World world, int x, int y, int z, Entity entity) {
		System.out.println(entity.getClass());
		return false;
	}

	@Override
	public float getPlayerRelativeBlockHardness(EntityPlayer entityPlayer, World world, int par3, int par4, int par5) {
		if((world.provider.isSurfaceWorld() && par4<5) || (world.provider instanceof WorldProviderBedrock && par4>1)){
			if(entityPlayer.inventory.getCurrentItem() != null && entityPlayer.inventory.getCurrentItem().getItem() instanceof ItemIchorPickAdv){
				return 5F;
			}
		}
		return super.getPlayerRelativeBlockHardness(entityPlayer, world, par3, par4, par5);
	}


	@Override
	public ArrayList<ItemStack> getBlockDropped(World world, int x, int y, int z, int metadata, int fortune) {
		return new ArrayList<ItemStack>();
	}
}
