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
 * File Created @ [2 Jul 2013, 19:46:26 (GMT)]
 */
package vazkii.tinkerer.item;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.world.World;
import thaumcraft.api.IVisRepairable;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.common.aura.AuraManager;
import vazkii.tinkerer.client.util.helper.IconHelper;
import vazkii.tinkerer.util.helper.ModCreativeTab;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemScythe extends ItemSword implements IVisRepairable {

	public ItemScythe(int par1) {
		super(par1, ThaumcraftApi.toolMatThaumium);
		setCreativeTab(ModCreativeTab.INSTANCE);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		itemIcon = IconHelper.forItem(par1IconRegister, this);
	}

	@Override
	public void doRepair(ItemStack stack, Entity e) {
		 if (AuraManager.decreaseClosestAura(e.worldObj, e.posX, e.posY, e.posZ, 1))
		 	stack.damageItem(-1, (EntityLiving) e);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack par1ItemStack) {
		return EnumRarity.rare;
	}

	@Override
	public boolean onBlockDestroyed(ItemStack par1ItemStack, World par2World, int id, int par4, int par5, int par6, EntityLiving par7EntityLiving) {
		boolean target = false;

		if(id == Block.tallGrass.blockID) {
			for(int i = 0; i < 3; i++)
				for(int j = 0; j < 3; j++) {
					int x = par4 + i - 1;
					int y = par5;
					int z = par6 + j - 1;
					int id1 = par2World.getBlockId(x, y, z);
					int meta = par2World.getBlockMetadata(x, y, z);
					if(id1 == Block.tallGrass.blockID && !par2World.isRemote) {
						ArrayList<ItemStack> drops = Block.tallGrass.getBlockDropped(par2World, x, y, z, meta, 0);
						for(ItemStack item : drops) {
							EntityItem entityItem = new EntityItem(par2World, x + 0.5, y + 0.5, z + 0.5, item);
							par2World.spawnEntityInWorld(entityItem);
						}
						par2World.setBlockToAir(x, y, z);
					}
				}

			target = true;
		}

		if(Block.blocksList[id].isLeaves(par2World, par4, par5, par6)) {
			Block block = Block.blocksList[id];
			for(int i = 0; i < 3; i++)
				for(int j = 0; j < 3; j++)
					for(int k = 0; k < 3; k++) {
						int x = par4 + i - 1;
						int y = par5 + j - 1;
						int z = par6 + k - 1;
						int id1 = par2World.getBlockId(x, y, z);
						int meta = par2World.getBlockMetadata(x, y, z);
						if(id1 == id && block.isLeaves(par2World, x, y, z) && !par2World.isRemote) {
							ArrayList<ItemStack> drops = block.getBlockDropped(par2World, x, y, z, meta, 0);
							for(ItemStack item : drops) {
								EntityItem entityItem = new EntityItem(par2World, x + 0.5, y + 0.5, z + 0.5, item);
								par2World.spawnEntityInWorld(entityItem);
							}
							par2World.setBlockToAir(x, y, z);
						}
				}

			target = true;
		}

        if (Block.blocksList[id].getBlockHardness(par2World, par4, par5, par6) != 0 && !target)
            par1ItemStack.damageItem(1, par7EntityLiving);

		return true;
	}

	@Override
	public float getStrVsBlock(ItemStack par1ItemStack, Block par2Block) {
		return par2Block.blockMaterial == Material.leaves ? 8F : super.getStrVsBlock(par1ItemStack, par2Block);
	}
}
