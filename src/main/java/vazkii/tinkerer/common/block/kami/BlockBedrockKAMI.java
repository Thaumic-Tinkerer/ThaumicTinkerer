package vazkii.tinkerer.common.block.kami;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import vazkii.tinkerer.common.block.ModBlocks;
import vazkii.tinkerer.common.dim.WorldProviderBedrock;
import vazkii.tinkerer.common.item.kami.tool.ItemIchorPickAdv;
import vazkii.tinkerer.common.lib.LibBlockIDs;

import java.util.ArrayList;
import java.util.Random;

public class BlockBedrockKAMI extends Block {

	public BlockBedrockKAMI() {
		super(Material.rock);
		//Block.blocksList[7]=this;
		setStepSound(Block.soundTypeStone);
		setResistance(6000000.0F);
		disableStats();
		setCreativeTab(CreativeTabs.tabBlock);
		setBlockUnbreakable();
	}

	@Override
	public void harvestBlock(World world, EntityPlayer entityPlayer, int par3, int par4, int par5, int par6) {
		if((world.provider.isSurfaceWorld() && par4<5) || (world.provider instanceof WorldProviderBedrock && par4>253)){
			if(entityPlayer.inventory.getCurrentItem() != null && entityPlayer.inventory.getCurrentItem().getItem() instanceof ItemIchorPickAdv){
				world.setBlock(par3, par4, par5, ModBlocks.portal);
			}
		}
	}

	/**
	 * Returns the quantity of items to drop on block destruction.
	 */
	public int quantityDropped(Random par1Random)
	{
		return 0;
	}

	public int idDropped(Random par1Random)
	{
		return 0;
	}


	@Override
	public float getBlockHardness(World par1World, int par2, int par3, int par4) {
		return -1;
	}

    @Override
    public boolean canEntityDestroy(IBlockAccess world, int x, int y, int z, Entity entity) {
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
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
		return new ArrayList<ItemStack>();
	}
}
