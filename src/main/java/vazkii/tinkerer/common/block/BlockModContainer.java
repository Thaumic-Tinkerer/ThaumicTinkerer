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
 * File Created @ [8 Sep 2013, 18:57:45 (GMT)]
 */
package vazkii.tinkerer.common.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import vazkii.tinkerer.client.core.helper.IconHelper;
import vazkii.tinkerer.common.core.handler.ModCreativeTab;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class BlockModContainer<T extends TileEntity> extends BlockContainer {

	protected BlockModContainer(Material par2Material) {
		super(par2Material);
		if(registerInCreative())
			setCreativeTab(ModCreativeTab.INSTANCE);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister par1IconRegister) {
		blockIcon = IconHelper.forBlock(par1IconRegister, this);
	}

	/** Does the block register in the creative inv? **/
	boolean registerInCreative() {
		return true;
    }

    @Override
	public abstract T createNewTileEntity(World world, int var2);

}
