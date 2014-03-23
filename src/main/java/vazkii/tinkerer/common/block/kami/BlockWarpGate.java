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


import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import vazkii.tinkerer.client.core.helper.IconHelper;
import vazkii.tinkerer.client.lib.LibRenderIDs;
import vazkii.tinkerer.common.ThaumicTinkerer;
import vazkii.tinkerer.common.block.BlockModContainer;
import vazkii.tinkerer.common.block.tile.kami.TileWarpGate;
import vazkii.tinkerer.common.lib.LibGuiIDs;

import java.util.Random;

public class BlockWarpGate extends BlockModContainer {

	public static IIcon[] icons = new IIcon[3];
	Random random;

	public BlockWarpGate() {
		super(Material.rock);
		setHardness(5.0F);
		setResistance(2000.0F);

		random = new Random();
	}

	@Override
	public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9) {
		if(!par1World.isRemote) {
			TileEntity tile = par1World.getTileEntity(par2, par3, par4);
			if(tile != null) {
				par1World.markBlockForUpdate(par2,par3,par4);
				par5EntityPlayer.openGui(ThaumicTinkerer.instance, LibGuiIDs.GUI_ID_WARP_GATE, par1World, par2, par3, par4);
			}
		}

		return true;
	}

	@Override
    public void breakBlock(World par1World, int par2, int par3, int par4, Block par5, int par6) {
        TileWarpGate warpGate = (TileWarpGate) par1World.getTileEntity(par2, par3, par4);

        if (warpGate != null) {
            for (int j1 = 0; j1 < warpGate.getSizeInventory(); ++j1) {
                ItemStack itemstack = warpGate.getStackInSlot(j1);

                if (itemstack != null) {
                    float f = random.nextFloat() * 0.8F + 0.1F;
                    float f1 = random.nextFloat() * 0.8F + 0.1F;
                    EntityItem entityitem;

                    for (float f2 = random.nextFloat() * 0.8F + 0.1F; itemstack.stackSize > 0; par1World.spawnEntityInWorld(entityitem)) {
                        int k1 = random.nextInt(21) + 10;

                        if (k1 > itemstack.stackSize)
                            k1 = itemstack.stackSize;

                        itemstack.stackSize -= k1;
                        entityitem = new EntityItem(par1World, par2 + f, par3 + f1, par4 + f2, new ItemStack(itemstack.getItem(), k1, itemstack.getItemDamage()));
                        float f3 = 0.05F;
                        entityitem.motionX = (float)random.nextGaussian() * f3;
                        entityitem.motionY = (float)random.nextGaussian() * f3 + 0.2F;
                        entityitem.motionZ = (float)random.nextGaussian() * f3;

                        if (itemstack.hasTagCompound())
                            entityitem.getEntityItem().setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
                    }
                }
            }

            par1World.func_147453_f(par2, par3, par4, par5);

        }

        super.breakBlock(par1World, par2, par3, par4, par5, par6);
    }

	@Override
	public TileEntity createNewTileEntity(World world,int in) {
		return new TileWarpGate();
	}

	@Override
	public IIcon getIcon(int par1, int par2) {
		return icons[par1 == 1 ? 0 : 1];
	}

	@Override
	public void registerBlockIcons(IIconRegister par1IconRegister) {
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
