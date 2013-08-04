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
 * File Created @ [4 Aug 2013, 16:35:17 (GMT)]
 */
package vazkii.tinkerer.item;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.world.World;
import thaumcraft.api.IVisRepairable;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.common.aura.AuraManager;
import vazkii.tinkerer.client.util.helper.IconHelper;
import vazkii.tinkerer.util.handler.EntityInteractionHandler;
import vazkii.tinkerer.util.helper.ModCreativeTab;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemMuramasa extends ItemSword implements IVisRepairable {

	public ItemMuramasa(int par1) {
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
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
		return par1ItemStack;
	}
	
	@Override
	public int getDamageVsEntity(Entity par1Entity) {
		EntityPlayer last = EntityInteractionHandler.getLastInteractingPlayer();
		return last != null ? Math.min(20, Math.max(1, last.getMaxHealth() - last.getHealth())) : super.getDamageVsEntity(par1Entity);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldRotateAroundWhenRendering() {
		return true;
	}
}
