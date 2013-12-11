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
 * File Created @ [Dec 11, 2013, 10:31:09 PM (GMT)]
 */
package vazkii.tinkerer.common.block;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import vazkii.tinkerer.client.core.helper.IconHelper;
import vazkii.tinkerer.common.block.tile.TileAspectAnalyzer;

public class BlockAspectAnalyzer extends BlockModContainer {

	Icon[] icons = new Icon[5];
	Random random;
	
	protected BlockAspectAnalyzer(int par1) {
		super(par1, Material.wood);
		setHardness(1.7F);
		setResistance(1F);
		setStepSound(soundWoodFootstep);

		random = new Random();
	}
	
	@Override
	public void registerIcons(IconRegister par1IconRegister) {
		for(int i = 0; i < 5; i++)
			icons[i] = IconHelper.forBlock(par1IconRegister, this, i);
	}
	
	@Override
	public Icon getIcon(int par1, int par2) {
		return icons[par1 == 0 || par1 == 1 ? 0 : par1 - 1];
	}

	public TileEntity createNewTileEntity(World world) {
		return new TileAspectAnalyzer();
	}

}
