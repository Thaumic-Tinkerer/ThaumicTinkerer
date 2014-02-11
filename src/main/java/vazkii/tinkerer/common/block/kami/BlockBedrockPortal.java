package vazkii.tinkerer.common.block.kami;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import vazkii.tinkerer.common.block.tile.kami.TileBedrockPortal;
import vazkii.tinkerer.common.core.handler.ModCreativeTab;

import java.util.List;

public class BlockBedrockPortal extends Block{

	public BlockBedrockPortal(int id) {
		super(id, Material.portal);
		setStepSound(soundStoneFootstep);
		setResistance(6000000.0F);
		disableStats();
		setCreativeTab(ModCreativeTab.INSTANCE);
	}

	@Override
	public TileEntity createTileEntity(World world, int metadata) {
		return new TileBedrockPortal();
	}


	@Override
	public void addCollisionBoxesToList(World par1World, int par2, int par3, int par4, AxisAlignedBB par5AxisAlignedBB, List par6List, Entity entity) {
		super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, entity);

		System.out.println("Hello");
		entity.travelToDimension(19);

	}
}
