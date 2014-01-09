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
 * File Created @ [Dec 27, 2013, 6:25:36 PM (GMT)]
 */
package vazkii.tinkerer.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import vazkii.tinkerer.common.block.tile.TileCamo;
import cpw.mods.fml.common.network.PacketDispatcher;

public abstract class BlockCamo extends BlockModContainer<TileCamo> {
	private int renderType = 0;
	@Override
	public int getRenderType() {
		return renderType;
	}
	public void setRenderType(int value) {
		renderType = value;
	}

	protected BlockCamo(int par1, Material par2Material) {
		super(par1, par2Material);
	}

	@Override
	public Icon getBlockTexture(IBlockAccess world, int x, int y, int z, int side) {
        TileEntity tile = world.getBlockTileEntity(x, y, z);
        int meta = world.getBlockMetadata(x, y, z);

        if (tile instanceof TileCamo) {
            TileCamo camo = (TileCamo) tile;
            if (camo.camo > 0 && camo.camo < 4096) {
                Block block = Block.blocksList[camo.camo];
                if (block != null) {
		    int rendertype = block.getRenderType();
		    if (rendertype == 0 || rendertype == 31 || rendertype == 39)
                        return block.getIcon(side, camo.camoMeta);
		}
            }
        }

        return getIconFromSideAfterCheck(tile, meta, side);
    }

	@Override
	public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9) {
        TileEntity tile = par1World.getBlockTileEntity(par2, par3, par4);

        if (tile instanceof TileCamo) {
        	TileCamo camo = (TileCamo) tile;
        	ItemStack currentStack = par5EntityPlayer.getCurrentEquippedItem();

        	if(currentStack == null)
        		currentStack = new ItemStack(0, 1, 0);

        	boolean doChange = true;
		int rendertype = 0;
        	checkChange : {
            	if(currentStack.itemID != 0) {
            		if(currentStack.itemID >= 4096) {
            			doChange = false;
            			break checkChange;
            		}

                    Block block = Block.blocksList[currentStack.itemID];
                    if(block == null || block == this || block.blockMaterial == Material.air)
                    	doChange = false;
		    else {
			rendertype = block.getRenderType();
                        if (rendertype != 0 && rendertype != 31 && rendertype != 39) 
			    doChange = false;
		    }
            	}
        	}

        	if(doChange) {
// rendertype will be initialized
			if (rendertype == 31) {
				int metadata = 0;
				switch (par6) {
					case 0:
					case 1:
						metadata = 0;
						break;
					case 2:
					case 3:
						metadata = 8;
						break;
					case 4:
					case 5:
						metadata = 4;
						break;
				}
            			camo.camoMeta = (currentStack.getItemDamage() & 3)| metadata;
				par1World.setBlockMetadataWithNotify(par2, par3, par4, metadata, 2);
			} else if (rendertype == 39) {
				int metadata = currentStack.getItemDamage();
				if (2 == metadata) {
					switch (par6) {
						case 0:
						case 1:
							metadata = 2;
							break;
						case 2:
						case 3:
							metadata = 4;
							break;
						case 4:
						case 5:
							metadata = 3;
							break;
					}
				}
				par1World.setBlockMetadataWithNotify(par2, par3, par4, metadata, 2);
				camo.camoMeta = metadata;
			} else {
            			camo.camoMeta = currentStack.getItemDamage();
			}
            	camo.camo = currentStack.itemID;
            	PacketDispatcher.sendPacketToAllInDimension(camo.getDescriptionPacket(), par1World.provider.dimensionId);
		setRenderType(rendertype);

        		return true;
        	}
        }

        return false;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	public Icon getIconFromSideAfterCheck(TileEntity tile, int meta, int side) {
		return blockIcon;
	}

}
