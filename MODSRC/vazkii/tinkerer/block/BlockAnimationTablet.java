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
 * File Created @ [13 May 2013, 14:44:11 (GMT)]
 */
package vazkii.tinkerer.block;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import vazkii.tinkerer.ThaumicTinkerer;
import vazkii.tinkerer.client.util.helper.IconHelper;
import vazkii.tinkerer.lib.LibGuiIDs;
import vazkii.tinkerer.tile.TileEntityAnimationTablet;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockAnimationTablet extends BlockModContainer {

	Icon iconBottom;
	Icon iconTop;
	Icon iconSides;

	Random random;
	
	public BlockAnimationTablet(int par1) {
		super(par1, Material.iron);
		setBlockBounds(0F, 0F, 0F, 1F, 1F / 16F * 2F, 1F);
		setHardness(3F);
		setResistance(50F);
		setStepSound(soundMetalFootstep);
		
		random = new Random();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		iconBottom = IconHelper.forBlock(par1IconRegister, this, 0);
		iconTop = IconHelper.forBlock(par1IconRegister, this, 1);
		iconSides = IconHelper.forBlock(par1IconRegister, this, 2);
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
	}
	
	@Override
    public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6) {
        TileEntityAnimationTablet tablet = (TileEntityAnimationTablet) par1World.getBlockTileEntity(par2, par3, par4);

        if (tablet != null) {
        	if(tablet.getIsBreaking()) {
        		ChunkCoordinates coords = tablet.getTargetLoc();
        		par1World.destroyBlockInWorldPartially(tablet.getFakePlayer().entityId, coords.posX, coords.posY, coords.posZ, -1);
        	}
        	
            for (int j1 = 0; j1 < tablet.getSizeInventory(); ++j1) {
                ItemStack itemstack = tablet.getStackInSlot(j1);

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
	public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5) {
		boolean power = par1World.isBlockIndirectlyGettingPowered(par2, par3, par4) || par1World.isBlockIndirectlyGettingPowered(par2, par3 + 1, par4);
		int meta = par1World.getBlockMetadata(par2, par3, par4);
		boolean on = (meta & 8) != 0;

		if (power && !on) {
			par1World.scheduleBlockUpdate(par2, par3, par4, blockID, tickRate(par1World));
			par1World.setBlockMetadataWithNotify(par2, par3, par4, meta | 8, 4);
		} else if (!power && on)
			par1World.setBlockMetadataWithNotify(par2, par3, par4, meta & 7, 4);
	}

	@Override
	public int tickRate(World par1World) {
		return 1;
	}

	@Override
	public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random) {
		TileEntity tile = par1World.getBlockTileEntity(par2, par3, par4);
		if(tile != null && tile instanceof TileEntityAnimationTablet) {
			TileEntityAnimationTablet tablet = (TileEntityAnimationTablet) tile;
			if(tablet.redstone && tablet.swingProgress == 0) {
				tablet.findEntities(tablet.getTargetLoc());
				tablet.initiateSwing();
				par1World.addBlockEvent(par2, par3, par4, ModBlocks.animationTablet.blockID, 0, 0);
			}
		}
	}

	@Override
	public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9) {
		if(!par1World.isRemote) {
			TileEntity tile = par1World.getBlockTileEntity(par2, par3, par4);
			if(tile != null)
				par5EntityPlayer.openGui(ThaumicTinkerer.modInstance, LibGuiIDs.ID_ANIMATION_TABLET, par1World, par2, par3, par4);
		}

		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int par1, int par2) {
		return par1 == ForgeDirection.UP.ordinal() ? iconTop : par1 == ForgeDirection.DOWN.ordinal() ? iconBottom : iconSides;
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
		return new TileEntityAnimationTablet();
	}
}
