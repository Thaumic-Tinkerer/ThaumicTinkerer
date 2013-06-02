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
 * File Created @ [1 Jun 2013, 22:50:03 (GMT)]
 */
package vazkii.tinkerer.block.eldritch;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import thaumcraft.common.Config;
import vazkii.tinkerer.block.BlockModContainer;
import vazkii.tinkerer.lib.LibRenderIDs;
import vazkii.tinkerer.tile.TileEntityFluxCollector;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockFluxCollector extends BlockModContainer {


	public BlockFluxCollector(int par1) {
		super(par1, Material.iron);
		setHardness(Block.obsidian.blockHardness);
        setBlockBounds(0.0625F, 0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int par1, int par2) {
		return Config.blockCosmeticSolid.getIcon(0, 0);
	}

	@Override
    public int getRenderType() {
        return LibRenderIDs.idFluxCollector;
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
		return new TileEntityFluxCollector();
	}

}
