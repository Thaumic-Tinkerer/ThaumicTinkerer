/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the ThaumicTinkerer Mod.
 * 
 * ThaumicTinkerer is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 * 
 * ThaumicTinkerer is a Derivative Work on Thaumcraft 3.
 * Thaumcraft 3 © Azanor 2012
 * (http://www.minecraftforum.net/topic/1585216-)
 * 
 * File Created @ [28 Jun 2013, 18:06:58 (GMT)]
 */
package vazkii.tinkerer.block;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import vazkii.tinkerer.lib.LibRenderIDs;
import vazkii.tinkerer.tile.TileEntityMagnet;

public class BlockMagnet extends BlockModContainer {

	public BlockMagnet(int par1) {
		super(par1, Material.iron);
        setBlockBounds(0F, 0F, 0F, 1F, 1F / 16F * 2F, 1F);
        setHardness(1.7F);
        setResistance(1F);
        setStepSound(soundWoodFootstep);
	}
	
	@Override
	public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9) {
		int meta = par1World.getBlockMetadata(par2, par3, par4);
		par1World.setBlockMetadataWithNotify(par2, par3, par4, meta == 0 ? 1 : 0, 2);
		if(!par1World.isRemote)
			par1World.playSoundEffect(par2, par3, par4, "random.click", 1F, 0.5F);
		return true;
	}
	
	@Override
	public int getRenderType() {
		return LibRenderIDs.idMagnet;
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityMagnet();
	}
}