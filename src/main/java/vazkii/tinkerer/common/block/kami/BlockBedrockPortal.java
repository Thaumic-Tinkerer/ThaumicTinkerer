package vazkii.tinkerer.common.block.kami;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import vazkii.tinkerer.common.ThaumicTinkerer;
import vazkii.tinkerer.common.core.handler.ModCreativeTab;

public class BlockBedrockPortal extends Block{

	@Override
	public void setBlockBoundsForItemRender() {
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
	}

	public BlockBedrockPortal(int id) {
		super(id, Material.portal);
		setStepSound(soundStoneFootstep);
		setResistance(6000000.0F);
		disableStats();
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
		setCreativeTab(ModCreativeTab.INSTANCE);
	}

	@Override
	public void onEntityCollidedWithBlock(World par1World, int par2, int par3, int par4, Entity entity) {
		super.onEntityCollidedWithBlock(par1World, par2, par3, par4, entity);
		System.out.println("Hello");
		if(entity != null){
			entity.setPosition(0, 100, 0);
			entity.travelToDimension(ThaumicTinkerer.dimID);
		}
	}

}
