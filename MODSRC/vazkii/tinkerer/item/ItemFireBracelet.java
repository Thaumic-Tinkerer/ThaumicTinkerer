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
 * File Created @ [2 May 2013, 11:50:56 (GMT)]
 */
package vazkii.tinkerer.item;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import vazkii.tinkerer.ThaumicTinkerer;
import vazkii.tinkerer.client.util.helper.IconHelper;
import vazkii.tinkerer.lib.LibFeatures;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemFireBracelet extends ItemMod {

	Icon disabledIcon;

	public ItemFireBracelet(int par1) {
		super(par1);
		setMaxStackSize(1);
		setMaxDamage(LibFeatures.FIRE_BRACELET_CHARGES + 1);
	}

	@Override
	public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10) {
		if(par1ItemStack.getItemDamage() != LibFeatures.FIRE_BRACELET_CHARGES) {
			int id = par3World.getBlockId(par4, par5, par6);
			int meta = par3World.getBlockMetadata(par4, par5, par6);
			ItemStack stack = new ItemStack(id, 1, meta);
			ItemStack result = FurnaceRecipes.smelting().getSmeltingResult(stack);
			
			boolean did = false;
			
			if(result != null && result.getItem() instanceof ItemBlock) {
				par3World.setBlock(par4, par5, par6, result.itemID, result.getItemDamage(), 2);
				par1ItemStack.damageItem(1, par2EntityPlayer);
				par3World.playSoundAtEntity(par2EntityPlayer, "fire.ignite", 0.6F, 1F); 
				par3World.playSoundAtEntity(par2EntityPlayer, "fire.fire", 1F, 1F);
				if(par3World.isRemote)
					par2EntityPlayer.swingItem();
				for(int i = 0; i < 25; i++) {
					double x = par4 + Math.random();
					double y = par5 + Math.random();
					double z = par6 + Math.random();
					
					ThaumicTinkerer.tcProxy.wispFX2(par3World, x, y, z, (float) Math.random() / 2F, 4, true, (float) -Math.random() / 10F);
				}
				
				did = true;
			}
			
			if(par1ItemStack.getItemDamage() == LibFeatures.FIRE_BRACELET_CHARGES)
				par3World.playSoundAtEntity(par2EntityPlayer, "thaumcraft.brain", 1F, 1F);
			
			return did;
		}
		return false;
	}
	

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		itemIcon = IconHelper.forItem(par1IconRegister, this, 1);
		disabledIcon = IconHelper.forItem(par1IconRegister, this, 0);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIconFromDamage(int par1) {
		return par1 == LibFeatures.FIRE_BRACELET_CHARGES ? disabledIcon : itemIcon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack par1ItemStack) {
		return EnumRarity.uncommon;
	}
}
