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

import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import thaumcraft.api.ThaumcraftApi;
import vazkii.tinkerer.tile.TileEntityWardChest;

public class BlockWardChest extends BlockModContainer {

	protected BlockWardChest(int par1) {
		super(par1, Material.wood);
		setBlockUnbreakable();
		setHardness(6000F);
		disableStats();
        setStepSound(soundWoodFootstep);
        ThaumcraftApi.portableHoleBlackList.add(par1);
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
	public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)  {
        if (par1World.isRemote)
            return true;

        TileEntityWardChest chest = (TileEntityWardChest) par1World.getBlockTileEntity(par2, par3, par4);

        if (chest != null) {
        	String owner = chest.owner;
        	if(owner.equals(par5EntityPlayer.username)) {
        		par5EntityPlayer.displayGUIChest(chest);
        		par1World.playSoundEffect(par2, par3, par4, "thaumcraft.key", 1F, 1F);
        	} else {
        		par5EntityPlayer.addChatMessage("The Chest refuses to budge.");
        		par1World.playSoundEffect(par2, par3, par4, "random.chestclosed", 1F, 0.2F);
        	}
        }

        return true;
    }

	@Override
    public int getRenderType() {
        return 22;
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
