/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the ThaumicTinkerer Mod.
 * 
 * ThaumicTinkerer is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 * 
 * ThaumicTinkerer is a Derivative Work on Thaumcraft 4.
 * Thaumcraft 4 (c) Azanor 2012
 * (http://www.minecraftforum.net/topic/1585216-)
 * 
 * File Created @ [Jan 10, 2014, 3:54:41 PM (GMT)]
 */
package vazkii.tinkerer.common.block.kami;


import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import vazkii.tinkerer.client.core.helper.IconHelper;
import vazkii.tinkerer.client.lib.LibRenderIDs;
import vazkii.tinkerer.common.block.BlockModContainer;
import vazkii.tinkerer.common.block.tile.kami.TileWarpGate;

public class BlockWarpGate extends BlockModContainer {

	public static Icon[] icons = new Icon[3];
	
	public BlockWarpGate(int par1) {
		super(par1, Material.rock);
		setHardness(5.0F);
		setResistance(2000.0F);
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileWarpGate();
	}
	
	@Override
	public Icon getIcon(int par1, int par2) {
		return icons[par1 == 1 ? 0 : 1];
	}
	
	@Override
	public void registerIcons(IconRegister par1IconRegister) {
		for(int i = 0; i < icons.length; i++)
			icons[i] = IconHelper.forBlock(par1IconRegister, this, i);
	}
	
	@Override
	public int getRenderType() {
		return LibRenderIDs.idWarpGate;
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

}
