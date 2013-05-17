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
 * File Created @ [17 May 2013, 19:54:06 (GMT)]
 */
package vazkii.tinkerer.item;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.world.World;
import thaumcraft.api.ThaumcraftApi;
import vazkii.tinkerer.client.util.helper.IconHelper;
import vazkii.tinkerer.lib.LibFeatures;
import vazkii.tinkerer.util.helper.ModCreativeTab;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemDefaultEnchant extends ItemTool {

	Enchantment enchant;
	int enchantLevel;

	protected ItemDefaultEnchant(int par1, Enchantment enchant, int enchantLevel) {
		super(par1, ThaumcraftApi.toolMatThaumium.getDamageVsEntity() + 4, ThaumcraftApi.toolMatThaumium, new Block[0]);
		setMaxDamage(LibFeatures.ENCHANT_ITEM_MAX_DMG);
		setNoRepair();
		setCreativeTab(ModCreativeTab.INSTANCE);
		this.enchant = enchant;
		this.enchantLevel = enchantLevel;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		itemIcon = IconHelper.forItem(par1IconRegister, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List) {
		ItemStack stack = new ItemStack(par1, 1, 0);
		stack.addEnchantment(enchant, enchantLevel);
		par3List.add(stack);
	}

	@Override
    public boolean hitEntity(ItemStack par1ItemStack, EntityLiving par2EntityLiving, EntityLiving par3EntityLiving) {
        par1ItemStack.damageItem(1, par3EntityLiving);
        return true;
    }

	@Override
    public EnumAction getItemUseAction(ItemStack par1ItemStack) {
        return EnumAction.block;
    }

	@Override
    public int getMaxItemUseDuration(ItemStack par1ItemStack) {
        return 72000;
    }

	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
        par3EntityPlayer.setItemInUse(par1ItemStack, getMaxItemUseDuration(par1ItemStack));
        return par1ItemStack;
    }

	@Override
	public boolean canHarvestBlock(Block par1Block) {
		return true;
	}

	@Override
	public float getStrVsBlock(ItemStack stack, Block block, int meta) {
		return 1.2F;
	}

	@Override
	public void onCreated(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
		par1ItemStack.addEnchantment(enchant, enchantLevel);
	}

	@Override
	public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack) {
		return false;
	}
}