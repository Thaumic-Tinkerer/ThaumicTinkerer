package vazkii.tinkerer.common.block.kami;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import vazkii.tinkerer.common.dim.WorldProviderBedrock;
import vazkii.tinkerer.common.item.kami.tool.ItemIchorPickAdv;
import vazkii.tinkerer.common.lib.LibBlockIDs;

import java.util.ArrayList;

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
	}

	@Override
	public void harvestBlock(World world, EntityPlayer entityPlayer, int par3, int par4, int par5, int par6) {
		if((world.provider.isSurfaceWorld() && par4<5) || (world.provider instanceof WorldProviderBedrock && par4>253)){
			System.out.println(1);
			if(entityPlayer.inventory.getCurrentItem() != null && entityPlayer.inventory.getCurrentItem().getItem() instanceof ItemIchorPickAdv){
				world.setBlock(par3, par4, par5, LibBlockIDs.idPortal);
			}
		}
	}



	@Override
	public float getPlayerRelativeBlockHardness(EntityPlayer entityPlayer, World world, int par3, int par4, int par5) {
		if((world.provider.isSurfaceWorld() && par4<5) || world.provider instanceof WorldProviderBedrock){
			return entityPlayer.inventory.getCurrentItem() != null && entityPlayer.inventory.getCurrentItem().getItem() instanceof ItemIchorPickAdv ? 3F : -1;
		}
		return -1;
	}

	@Override
	public ArrayList<ItemStack> getBlockDropped(World world, int x, int y, int z, int metadata, int fortune) {
		return new ArrayList<ItemStack>();
	}
}
