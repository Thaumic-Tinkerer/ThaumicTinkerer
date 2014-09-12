/**
 * Author: thegreatunclean
 * License: Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * To go live change 'shouldDisplayInTab' and 'getResearchItem'
 */
package thaumic.tinkerer.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.util.MathHelper;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.research.ResearchPage;
import thaumcraft.common.config.ConfigItems;

import thaumic.tinkerer.client.core.helper.IconHelper;
import thaumic.tinkerer.common.ThaumicTinkerer;
import thaumic.tinkerer.common.block.tile.TileSynth;
import thaumic.tinkerer.common.block.BlockFunnel;
import thaumic.tinkerer.common.lib.LibBlockNames;
import thaumic.tinkerer.common.lib.LibResearch;
import thaumic.tinkerer.common.registry.ThaumicTinkererArcaneRecipe;
import thaumic.tinkerer.common.registry.ThaumicTinkererRecipe;
import thaumic.tinkerer.common.research.IRegisterableResearch;
import thaumic.tinkerer.common.research.ResearchHelper;
import thaumic.tinkerer.common.research.TTResearchItem;

import java.util.ArrayList;
import java.util.Random;

public class BlockSynth extends BlockModContainer {

	@SideOnly(Side.CLIENT)
	IIcon sideIcon, topIcon, topIcon_r;
	
	Random random;
	
    public BlockSynth() {
        super(Material.rock);
        setHardness(3.0F);
        setResistance(8.0F);
        setBlockBounds(0F,0F,0F,1F,1/8F,1F);
		
		random = new Random();
    }
	
	//Store orientation on block place as a piece of metadata.
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
	}
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    } 
    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess access, int par2, int par3, int par4, int par5) {
        return true;
    }
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

    @Override
    @SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconReg) {
		topIcon = IconHelper.forBlock(iconReg, this, 0);
		sideIcon = IconHelper.forBlock(iconReg, this, 1);
		topIcon_r = IconHelper.forBlock(iconReg, this, 2); //90 deg rotatated top
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int par1, int par2) {
		/*	par1 is side, 0 = bottom, 1=  top, 2-5 = sides
			par2 is meta. 2 = 0deg, 3 = 180, 4 = 270, 5 = 90
		*/
		
		/*
		switch (par1) {
			case 0:
			case 1:
				return (par2 > 3 ? topIcon_r : topIcon); break;
			case 2:
			case 3:
			case 4:
			case 5:
				return sideIcon;
			default:
				return null;
		}
		*/
		return (par1 > 1 ? sideIcon : par2 < 4 ? topIcon : topIcon_r);
	}
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4) {
		return AxisAlignedBB.getBoundingBox(par2, par3, par4, par2 + 1, par3 + 1, par4 + 1);
	}

    
    @Override
    public void breakBlock(World world, int x_coord, int y_coord, int z_coord, Block par5, int par6) {
		//thaumcraft.common.blocks.BlockJarItem is empty jar, no label
		//thaumcraft.common.blocks.ItemJarFilled is empty jar with label
        TileSynth synth = (TileSynth) world.getTileEntity(x_coord, y_coord, z_coord);
        
        if (synth != null) {
			for (int i=0; i < synth.getSizeInventory(); i++) {
				ItemStack slot = synth.getStackInSlot(i);
                if (slot != null) {
					float f = random.nextFloat() * 0.8F + 0.1F;
					float f1 = random.nextFloat() * 0.8F + 0.1F;
					
                    //Construct entity to drop
                    EntityItem entityitem;
					
					for (float f2 = random.nextFloat()*0.8F + 0.1F; slot.stackSize > 0; world.spawnEntityInWorld(entityitem)) {
						int k1 = random.nextInt(21) + 10;
						
						if (k1 > slot.stackSize) { k1 = slot.stackSize; }
						
						slot.stackSize -= k1;
						
						entityitem = new EntityItem(world, x_coord+f, y_coord+f1, z_coord+f2,
						             new ItemStack(slot.getItem(), k1, slot.getItemDamage()));
						float f3 = 0.05F;
						
						entityitem.motionX = (float) random.nextGaussian() * f3;
						entityitem.motionY = (float) random.nextGaussian() * f3 + 0.2F;
						entityitem.motionZ = (float) random.nextGaussian() * f3;
						
						if (slot.hasTagCompound()) {
							entityitem.getEntityItem().setTagCompound((NBTTagCompound) slot.getTagCompound().copy());
						}
					}
				}
                 
            }
			
            world.func_147453_f(x_coord, y_coord, z_coord, par5);
        }
		dropBlockAsItem(world, x_coord, y_coord, z_coord, 0, 0);
        super.breakBlock(world, x_coord, y_coord, z_coord, par5, par6);
    }
	@Override
	public boolean onBlockActivated(World world, int x_coord, int y_coord, int z_coord, EntityPlayer player, int par6, float par7, float par8, float par9) {		
		
		
		TileSynth synth = (TileSynth) world.getTileEntity(x_coord, y_coord, z_coord);
		ItemStack playerStack = player.getCurrentEquippedItem();
		int blockside = sideOfClick(world, x_coord,y_coord,z_coord, par7, par8, par9); //1 for lhs, 0 for rhs
			
		if (playerStack != null && synth.canInsertItem(blockside, playerStack,1)) {
			//go for insert
			synth.setInventorySlotContents(blockside, playerStack.splitStack(1));
			if (playerStack.stackSize <= 0) {
				player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
			}
			synth.markDirty();
			return true;
		}
		
		if (playerStack == null && synth.getStackInSlot(blockside) != null) {
			//go for extract
			ItemStack stack = synth.getStackInSlot(blockside);
			if (!player.inventory.addItemStackToInventory(stack)) {
				player.dropPlayerItemWithRandomChoice(stack, false);
			}
			synth.setInventorySlotContents(blockside, null);
			synth.markDirty();
			return true;
		}
		
		return false;
	}
	
	//returns 0 for rhs, 1 for lhs
	private int sideOfClick(World world, int xc, int yc, int zc, float x, float y, float z){
		switch(world.getBlockMetadata(xc,yc,zc)) {
			case 2: //no rotation
				return (x >= 0.5 ? 1 : 0); //lhs, rhs
			case 3: //180
				return (x >= 0.5 ? 0 : 1);
			case 4: //90 cw, right-facing
				return (z >= 0.5 ? 1 : 0);
			case 5: //270 cw, left-facing
				return (z >= 0.5 ? 0 : 1);
			default:
				return 1; //not really sure how you'd get here.
		}
	}
				
    
    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileSynth();
    }
    @Override
    public ArrayList<Object> getSpecialParameters() {
        return null;
    }
    @Override
    public String getBlockName() {
        return LibBlockNames.SYNTH;
    }
	@Override
	public boolean canPlaceBlockAt(World world, int xc, int yc, int zc){
		return world.getBlock(xc, yc-1, zc) == Block.getBlockFromName("hopper");
	}
	@Override
	public void onNeighborBlockChange(World world, int xc, int yc, int zc, Block block) {
		if (world.getBlock(xc, yc-1, zc) != Block.getBlockFromName("hopper")) {
			dropBlockAsItem(world, xc, yc, zc, 0, 0);
			world.setBlockToAir(xc, yc, zc);
		}
		return;
	}
	@Override
	public boolean shouldRegister() {
		return true;
	}
	@Override
	public boolean shouldDisplayInTab() {
		return false;
	}
	@Override
	public Class<? extends ItemBlock> getItemBlock() {
		return null;
	}
	@Override
	public Class<? extends TileEntity> getTileEntity() {
		return TileSynth.class;
	}
	@Override
	public ThaumicTinkererRecipe getRecipeItem() {
		return new ThaumicTinkererArcaneRecipe(LibResearch.KEY_SYNTH, LibResearch.KEY_SYNTH, new ItemStack(this), new AspectList().add(Aspect.ORDER, 1).add(Aspect.ENTROPY,1),
			"DFD",
			'D', new ItemStack(Blocks.diamond_block),
			'F', new ItemStack(ThaumicTinkerer.registry.getFirstBlockFromClass(BlockFunnel.class)));
	}
	public IRegisterableResearch getResearchItem() {
		return null;
		/*
		return (IRegisterableResearch) new TTResearchItem(LibResearch.KEY_SYNTH,
                                                      new AspectList().add(Aspect.TOOL,2).add(Aspect.CRAFT,1).add(Aspect.ORDER,1).add(Aspect.MAGIC,1),
                                                      1,-9,
                                                      3,
                                                      new ItemStack(this))
                                                      .setConcealed().setParents(LibResearch.KEY_FUNNEL)
													  .setPages(new ResearchPage("0"),
																ResearchHelper.arcaneRecipePage(LibResearch.KEY_SYNTH),
																new ResearchPage(LibResearch.KEY_ICHOR, "1")).setSecondary(); //hidden until ichor is done
		*/
	}

}