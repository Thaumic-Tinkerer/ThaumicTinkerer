/**
 * This class was created by <TheWhiteWolves>. It's distributed as
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
 * File Created @ [20 May 2013, 19:35:05 (GMT)]
 */
package vazkii.tinkerer.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import thaumcraft.common.aura.AuraManager;
import vazkii.tinkerer.client.util.helper.IconHelper;
import vazkii.tinkerer.lib.LibFeatures;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemCleansingTalisman extends ItemMod {

	private Icon enabledIcon;
	private long timer = 0L;

	private static final int[] POTIONS_TO_REMOVE = new int[] {
		Potion.blindness.id,
		Potion.confusion.id,
		Potion.poison.id,
		Potion.weakness.id,
		Potion.moveSlowdown.id,
		Potion.wither.id
	};

	public ItemCleansingTalisman(int par1) {
		super(par1);
		setMaxStackSize(1);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
		if(par3EntityPlayer.isSneaking()) {
			int damage = par1ItemStack.getItemDamage();
			par1ItemStack.setItemDamage(damage == 0 ? 1 : 0);
			par2World.playSoundAtEntity(par3EntityPlayer, "random.orb", 0.3F, 0.1F);
		}

		return par1ItemStack;
	}

	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5) {
		if(par1ItemStack.getItemDamage() == 1 && !par2World.isRemote) {
			if(timer < System.currentTimeMillis()) {
				timer = System.currentTimeMillis() + 1000L;

				if (par3Entity instanceof EntityPlayer) {
					EntityPlayer player = (EntityPlayer)par3Entity;
					boolean removed = false;
					if(player.isBurning()) {
						player.extinguish();
						removed = true;
					} else for(int potion : POTIONS_TO_REMOVE) {
						if(player.isPotionActive(potion)) {
							player.removePotionEffect(potion);
							removed = true;
							break;
						}
					}

					if(removed) {
						AuraManager.decreaseClosestAura(par2World, player.posX, player.posY, player.posZ, LibFeatures.CLEANSING_TALISMAN_VIS);
						par2World.playSoundAtEntity(player, "thaumcraft.wand", 0.3F, 0.1F);
					}
				}
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		itemIcon = IconHelper.forItem(par1IconRegister, this, 0);
		enabledIcon = IconHelper.forItem(par1IconRegister, this, 1);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		if(par1ItemStack.getItemDamage() == 0)
			par3List.add(EnumChatFormatting.RED + "Inactive");
		else par3List.add(EnumChatFormatting.GREEN + "Active");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIconFromDamage(int par1) {
		return par1 == 1 ? enabledIcon : itemIcon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack par1ItemStack) {
		return EnumRarity.uncommon;
	}
}
