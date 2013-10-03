/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the ThaumicTinkerer Mod.
 *
 * ThaumicTinkerer is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * ThaumicTinkerer is a Derivative Work on Thaumcraft 3.
 * Thaumcraft 3 � Azanor 2012
 * (http://www.minecraftforum.net/topic/1585216-)
 *
 * File Created @ [28 Apr 2013, 16:50:31 (GMT)]
 */
package vazkii.tinkerer.block;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import vazkii.tinkerer.ThaumicTinkerer;
import vazkii.tinkerer.client.util.helper.IconHelper;
import vazkii.tinkerer.lib.LibGuiIDs;
import vazkii.tinkerer.tile.TileEntityTransmutator;
import vazkii.tinkerer.util.handler.ConfigurationHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockTransmutator extends BlockModContainer {

	Icon[] icons = new Icon[6];

	Random random;

	public BlockTransmutator(int par1) {
		super(par1, Material.wood);
        setBlockBounds(0F, 0F, 0F, 1F, 1F / 16F * 6F, 1F);
        setHardness(1.7F);
        setResistance(1F);
        setStepSound(soundWoodFootstep);

        random = new Random();
	}

	@Override
	public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9) {
		if(!par1World.isRemote) {
			TileEntity tile = par1World.getBlockTileEntity(par2, par3, par4);
			if(tile != null) {
				if(ConfigurationHandler.enableTransmutator)
					par5EntityPlayer.openGui(ThaumicTinkerer.modInstance, LibGuiIDs.ID_TRANSMUTATOR, par1World, par2, par3, par4);
				else par5EntityPlayer.addChatMessage("The Arcane Transmutator is disabled on this server.");
			}
		}

		return true;
	}

	@Override
    public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6) {
        TileEntityTransmutator transmutator = (TileEntityTransmutator) par1World.getBlockTileEntity(par2, par3, par4);

        if (transmutator != null) {
            for (int j1 = 0; j1 < transmutator.getSizeInventory(); ++j1) {
                ItemStack itemstack = transmutator.getStackInSlot(j1);

                if (itemstack != null) {
                    float f = random.nextFloat() * 0.8F + 0.1F;
                    float f1 = random.nextFloat() * 0.8F + 0.1F;
                    EntityItem entityitem;

                    for (float f2 = random.nextFloat() * 0.8F + 0.1F; itemstack.stackSize > 0; par1World.spawnEntityInWorld(entityitem)) {
                        int k1 = random.nextInt(21) + 10;

                        if (k1 > itemstack.stackSize)
                            k1 = itemstack.stackSize;

                        itemstack.stackSize -= k1;
                        entityitem = new EntityItem(par1World, par2 + f, par3 + f1, par4 + f2, new ItemStack(itemstack.itemID, k1, itemstack.getItemDamage()));
                        float f3 = 0.05F;
                        entityitem.motionX = (float)random.nextGaussian() * f3;
                        entityitem.motionY = (float)random.nextGaussian() * f3 + 0.2F;
                        entityitem.motionZ = (float)random.nextGaussian() * f3;

                        if (itemstack.hasTagCompound())
                            entityitem.getEntityItem().setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
                    }
                }
            }

            par1World.func_96440_m(par2, par3, par4, par5);
        }

        super.breakBlock(par1World, par2, par3, par4, par5, par6);
    }

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		for(int i = 0; i < icons.length; i++)
			icons[i] = IconHelper.forBlock(par1IconRegister, this, i);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int par1, int par2) {
		return icons[par1];
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityTransmutator();
	}

}