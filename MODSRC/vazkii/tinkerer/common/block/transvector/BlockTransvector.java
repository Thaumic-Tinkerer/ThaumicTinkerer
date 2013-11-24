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
 * File Created @ [Nov 24, 2013, 6:54:05 PM (GMT)]
 */
package vazkii.tinkerer.common.block.transvector;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import vazkii.tinkerer.common.block.BlockModContainer;
import vazkii.tinkerer.common.block.tile.transvector.TileTransvector;
import cpw.mods.fml.common.network.PacketDispatcher;

public abstract class BlockTransvector extends BlockModContainer<TileTransvector> {

	protected BlockTransvector(int par1, Material par2Material) {
		super(par1, par2Material);
	}

	@Override
	public Icon getBlockTexture(IBlockAccess world, int x, int y, int z, int side) {
        TileEntity tile = world.getBlockTileEntity(x, y, z);
        int meta = world.getBlockMetadata(x, y, z);

        if (tile instanceof TileTransvector) {
            TileTransvector transvector = (TileTransvector) tile;
            if (transvector.camo > 0 && transvector.camo < 4096) {
                Block block = Block.blocksList[transvector.camo];
                if (block != null && block.renderAsNormalBlock())
                    return block.getIcon(side, transvector.camoMeta);
            }
        }

        return getIconFromSideAfterCheck(tile, meta, side);
    }

	@Override
	public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9) {
        TileEntity tile = par1World.getBlockTileEntity(par2, par3, par4);

        if (tile instanceof TileTransvector) {
        	TileTransvector transvector = (TileTransvector) tile;
        	ItemStack currentStack = par5EntityPlayer.getCurrentEquippedItem();

        	if(currentStack == null)
        		currentStack = new ItemStack(0, 1, 0);

        	boolean doChange = true;
        	checkChange : {
            	if(currentStack.itemID != 0) {
            		if(currentStack.itemID >= 4096) {
            			doChange = false;
            			break checkChange;
            		}

                    Block block = Block.blocksList[currentStack.itemID];
                    if(block == null || !block.renderAsNormalBlock() || block == this)
                    	doChange = false;

            	}
        	}


        	if(doChange) {
            	transvector.camo = currentStack.itemID;
            	transvector.camoMeta = currentStack.getItemDamage();
            	PacketDispatcher.sendPacketToAllInDimension(transvector.getDescriptionPacket(), par1World.provider.dimensionId);

        		return true;
        	}
        }

        return false;
	}

	public Icon getIconFromSideAfterCheck(TileEntity tile, int meta, int side) {
		return blockIcon;
	}

}
