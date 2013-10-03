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
 * File Created @ [4 May 2013, 21:34:53 (GMT)]
 */
package vazkii.tinkerer.block;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.common.Config;
import thaumcraft.common.items.wands.ItemWandCasting;
import vazkii.tinkerer.lib.LibRenderIDs;
import vazkii.tinkerer.tile.TileEntityWardChest;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockWardChest extends BlockModContainer {

	Random random;

	protected BlockWardChest(int par1) {
		super(par1, Material.wood);
		setBlockUnbreakable();
		setHardness(6000F);
		disableStats();
        setStepSound(soundWoodFootstep);
        setBlockBounds(0.0625F, 0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
        ThaumcraftApi.portableHoleBlackList.add(par1);

        random = new Random();
	}

	@Override
	public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLiving par5EntityLiving, ItemStack par6ItemStack) {
        byte b0 = 0;
        int l1 = MathHelper.floor_double(par5EntityLiving.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;

        if (l1 == 0)
            b0 = 2;

        if (l1 == 1)
            b0 = 5;

        if (l1 == 2)
            b0 = 3;

        if (l1 == 3)
            b0 = 4;

        par1World.setBlockMetadataWithNotify(par2, par3, par4, b0, 2);
        TileEntityWardChest chest = (TileEntityWardChest) par1World.getBlockTileEntity(par2, par3, par4);
        if (par6ItemStack.hasDisplayName())
            chest.customName = par6ItemStack.getDisplayName();
        if(par5EntityLiving instanceof EntityPlayer)
        	chest.owner = ((EntityPlayer) par5EntityLiving).username;
        else chest.owner = "";
	}

	@Override
    public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6) {
        TileEntityWardChest chest = (TileEntityWardChest) par1World.getBlockTileEntity(par2, par3, par4);

        if (chest != null) {
            for (int j1 = 0; j1 < chest.getSizeInventory(); ++j1) {
                ItemStack itemstack = chest.getStackInSlot(j1);

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
	public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)  {
        if (par1World.isRemote)
            return true;

        TileEntityWardChest chest = (TileEntityWardChest) par1World.getBlockTileEntity(par2, par3, par4);

        effect : {
            if (chest != null) {
            	String owner = chest.owner;

            	if(owner.equals(par5EntityPlayer.username)) {
                    if(par5EntityPlayer.getCurrentEquippedItem() != null && par5EntityPlayer.getCurrentEquippedItem().getItem() instanceof ItemWandCasting) {
                    	if(!par1World.isRemote) {
                    		int meta = par1World.getBlockMetadata(par2, par3, par4);
                    		dropBlockAsItem(par1World, par2, par3, par4, meta, par6);
                    		par1World.playAuxSFX(2001, par2, par3, par4, blockID + (meta << 12));
                    		par1World.setBlock(par2, par3, par4, 0, 0, 1 & 2);
                    		break effect;
                    	} else par5EntityPlayer.swingItem();
                    }

            		par5EntityPlayer.displayGUIChest(chest);
            		par1World.playSoundEffect(par2, par3, par4, "thaumcraft.key", 1F, 1F);
            	} else {
            		par5EntityPlayer.addChatMessage("The Chest refuses to budge.");
            		par1World.playSoundEffect(par2, par3, par4, "thaumcraft.doorfail", 1F, 0.2F);
            	}
            }
        }


        return true;
    }

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int par1, int par2) {
		return Config.blockWooden.getIcon(par1, 0);
	}

	@Override
    public int getRenderType() {
        return LibRenderIDs.idWardChest;
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
		return new TileEntityWardChest();
	}

}
