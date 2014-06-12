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
 * File Created @ [9 Sep 2013, 15:52:53 (GMT)]
 */
package vazkii.tinkerer.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.research.ResearchPage;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.items.wands.ItemWandCasting;
import vazkii.tinkerer.client.core.helper.IconHelper;
import vazkii.tinkerer.common.ThaumicTinkerer;
import vazkii.tinkerer.common.block.tile.tablet.TileAnimationTablet;
import vazkii.tinkerer.common.lib.LibBlockNames;
import vazkii.tinkerer.common.lib.LibGuiIDs;
import vazkii.tinkerer.common.lib.LibResearch;
import vazkii.tinkerer.common.registry.ThaumicTinkererArcaneRecipe;
import vazkii.tinkerer.common.registry.ThaumicTinkererRecipe;
import vazkii.tinkerer.common.research.IRegisterableResearch;
import vazkii.tinkerer.common.research.ResearchHelper;
import vazkii.tinkerer.common.research.TTResearchItem;

import java.util.ArrayList;
import java.util.Random;

public class BlockAnimationTablet extends BlockModContainer {

	IIcon iconBottom;
	IIcon iconTop;
	IIcon iconSides;

	Random random;

	public BlockAnimationTablet() {
		super(Material.iron);
		setBlockBounds(0F, 0F, 0F, 1F, 1F / 16F * 2F, 1F);
		setHardness(3F);
		setResistance(50F);
		setStepSound(soundTypeMetal);

		random = new Random();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister par1IconRegister) {
		iconBottom = IconHelper.forBlock(par1IconRegister, this, 0);
		iconTop = IconHelper.forBlock(par1IconRegister, this, 1);
		iconSides = IconHelper.forBlock(par1IconRegister, this, 2);
	}

	@Override
	public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLiving, ItemStack par6ItemStack) {
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
		//TileAnimationTablet tablet = (TileAnimationTablet) par1World.getTileEntity(par2, par3, par4);
		//if(par5EntityLiving instanceof  EntityPlayer) {
		//    tablet.Owner = ((EntityPlayer) par5EntityLiving).username;
		//}
	}

	@Override
	public void breakBlock(World par1World, int par2, int par3, int par4, Block par5, int par6) {
		TileAnimationTablet tablet = (TileAnimationTablet) par1World.getTileEntity(par2, par3, par4);

		if (tablet != null) {
			if (tablet.getIsBreaking()) {
				//ChunkCoordinates coords = tablet.getTargetLoc();
				//par1World.destroyBlockInWorldPartially(tablet.getFakePlayer().entityId, coords.posX, coords.posY, coords.posZ, -1);
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
						entityitem = new EntityItem(par1World, par2 + f, par3 + f1, par4 + f2, new ItemStack(itemstack.getItem(), k1, itemstack.getItemDamage()));
						float f3 = 0.05F;
						entityitem.motionX = (float) random.nextGaussian() * f3;
						entityitem.motionY = (float) random.nextGaussian() * f3 + 0.2F;
						entityitem.motionZ = (float) random.nextGaussian() * f3;

						if (itemstack.hasTagCompound())
							entityitem.getEntityItem().setTagCompound((NBTTagCompound) itemstack.getTagCompound().copy());
					}
				}
			}

			//Look here if something breaks in 1.7
			par1World.func_147453_f(par2, par3, par4, par5);
		}

		super.breakBlock(par1World, par2, par3, par4, par5, par6);
	}

	@Override
	public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, Block par5) {
		if (par1World.isRemote)
			return;

		boolean power = par1World.isBlockIndirectlyGettingPowered(par2, par3, par4) || par1World.isBlockIndirectlyGettingPowered(par2, par3 + 1, par4);
		int meta = par1World.getBlockMetadata(par2, par3, par4);
		boolean on = (meta & 8) != 0;

		if (power && !on) {
			par1World.scheduleBlockUpdate(par2, par3, par4, this, tickRate(par1World));
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
		TileEntity tile = par1World.getTileEntity(par2, par3, par4);
		if (tile != null && tile instanceof TileAnimationTablet) {
			TileAnimationTablet tablet = (TileAnimationTablet) tile;
			if (tablet.redstone && tablet.swingProgress == 0) {
				tablet.findEntities(tablet.getTargetLoc());
				tablet.initiateSwing();
				par1World.addBlockEvent(par2, par3, par4, ModBlocks.animationTablet, 0, 0);
			}
		}
	}

	@Override
	public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9) {
		if (!par1World.isRemote) {
			TileEntity tile = par1World.getTileEntity(par2, par3, par4);
			if (tile != null) {
				TileAnimationTablet tablet = (TileAnimationTablet) tile;
				if (par5EntityPlayer.getCurrentEquippedItem() != null && par5EntityPlayer.getCurrentEquippedItem().getItem() instanceof ItemWandCasting) {
					int meta = par1World.getBlockMetadata(par2, par3, par4);
					boolean activated = (meta & 8) != 0;
					if (!activated && !tablet.getIsBreaking() && tablet.swingProgress == 0) {
						par1World.setBlockMetadataWithNotify(par2, par3, par4, meta == 5 ? 2 : meta + 1, 1 | 2);
						par1World.playSoundEffect(par2, par3, par4, "thaumcraft:tool", 0.6F, 1F);
					} else
						par5EntityPlayer.addChatMessage(new ChatComponentTranslation("ttmisc.animationTablet.notRotatable"));
					// Rare chance this might happen, but better to cope for it.

					return true;
				} else {
					//if(tablet.Owner==null || tablet.Owner.equals(""))
					//    tablet.Owner=par5EntityPlayer.username;
					par5EntityPlayer.openGui(ThaumicTinkerer.instance, LibGuiIDs.GUI_ID_TABLET, par1World, par2, par3, par4);
				}
			}
		}

		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int par1, int par2) {
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
	public TileEntity createNewTileEntity(World world, int par2) {
		return new TileAnimationTablet();
	}

	@Override
	public ArrayList<Object> getSpecialParameters() {
		return null;
	}

	@Override
	public String getBlockName() {
		return LibBlockNames.ANIMATION_TABLET;
	}

	@Override
	public boolean shouldRegister() {
		return true;
	}

	@Override
	public boolean shouldDisplayInTab() {
		return true;
	}

	@Override
	public IRegisterableResearch getResearchItem() {
		return (IRegisterableResearch) new TTResearchItem(LibResearch.KEY_ANIMATION_TABLET, new AspectList().add(Aspect.MECHANISM, 2).add(Aspect.METAL, 1).add(Aspect.MOTION, 1).add(Aspect.ENERGY, 1), -8, 2, 4, new ItemStack(ModBlocks.animationTablet)).setParents(LibResearch.KEY_MAGNETS)
				.setPages(new ResearchPage("0"), ResearchHelper.arcaneRecipePage(LibResearch.KEY_ANIMATION_TABLET));

	}

	@Override
	public ThaumicTinkererRecipe getRecipeItem() {
		return new ThaumicTinkererArcaneRecipe(LibResearch.KEY_ANIMATION_TABLET, LibResearch.KEY_ANIMATION_TABLET, new ItemStack(ModBlocks.animationTablet), new AspectList().add(Aspect.AIR, 25).add(Aspect.ORDER, 15).add(Aspect.FIRE, 10),
				"GIG", "ICI",
				'G', new ItemStack(Items.gold_ingot),
				'I', new ItemStack(Items.iron_ingot),
				'C', new ItemStack(ConfigItems.itemGolemCore, 1, 100));

	}
}
