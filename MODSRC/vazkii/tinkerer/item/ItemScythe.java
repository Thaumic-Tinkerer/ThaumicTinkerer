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

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
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
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
		return par1ItemStack;
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack) {
		return EnumAction.none;
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
			for(int i = -1; i < 1; i++)
				for(int j = -1; j < 1; j++) {
					int id1 = par2World.getBlockId(par4 + i, par5, par6 + j);
					if(id1 == Block.tallGrass.blockID)
						par2World.setBlockToAir(par4 + i, par5, par6 + j);
				}
			
			target = true;
		}
		
		if(Block.blocksList[id].isLeaves(par2World, par4, par5, par6)) {
			for(int i = -1; i < 1; i++)
				for(int j = -1; j < 1; j++) {
					int id1 = par2World.getBlockId(par4 + i, par5, par6 + j);
					if(id1 == id && Block.blocksList[id].isLeaves(par2World, par4 + i, par5, par6 + j))
						par2World.setBlockToAir(par4 + i, par5, par6 + j);
				}
			
			target = true;
		}
		
        if (Block.blocksList[id].getBlockHardness(par2World, par4, par5, par6) != 0 && !target)
            par1ItemStack.damageItem(1, par7EntityLiving);
        
		return true;
	}
}
