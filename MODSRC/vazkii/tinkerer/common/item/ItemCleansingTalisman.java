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
 * File Created @ [11 Sep 2013, 15:45:16 (GMT)]
 */
package vazkii.tinkerer.common.item;

import java.util.Collection;
import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.Icon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import vazkii.tinkerer.client.core.helper.IconHelper;
import vazkii.tinkerer.common.core.helper.ItemNBTHelper;
import vazkii.tinkerer.common.lib.LibFeatures;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemCleansingTalisman extends ItemMod {

	private static final String TAG_ENABLED = "enabled";

	private Icon enabledIcon;

	public ItemCleansingTalisman(int par1) {
		super(par1);
		setMaxStackSize(1);
		setMaxDamage(LibFeatures.CLEANSING_TALISMAN_USES);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
		if(par3EntityPlayer.isSneaking()) {
			flipEnabled(par1ItemStack);
			par2World.playSoundAtEntity(par3EntityPlayer, "random.orb", 0.3F, 0.1F);
		}

		return par1ItemStack;
	}

	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5) {
		if(isEnabled(par1ItemStack) && !par2World.isRemote) {
			if(par3Entity.ticksExisted % 20 == 0) {
				if (par3Entity instanceof EntityPlayer) {
					EntityPlayer player = (EntityPlayer)par3Entity;
					boolean removed = false;

					Collection<PotionEffect> potions = player.getActivePotionEffects();

					if(player.isBurning()) {
						player.extinguish();
						removed = true;
					} else for(PotionEffect potion : potions) {
						int id = potion.getPotionID();
						if(Potion.potionTypes[id].isBadEffect()) {
							player.removePotionEffect(id);
							removed = true;
							break;
						}
					}

					if(removed) {
						par1ItemStack.damageItem(1, player);
						par2World.playSoundAtEntity(player, "thaumcraft:wand", 0.3F, 0.1F);
					}
				}
			}
		}
	}

	public static boolean isEnabled(ItemStack stack) {
		return ItemNBTHelper.getBoolean(stack, TAG_ENABLED, false);
	}

	public static void flipEnabled(ItemStack stack) {
		ItemNBTHelper.setBoolean(stack, TAG_ENABLED, !isEnabled(stack));
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
		if(isEnabled(par1ItemStack))
			par3List.add(StatCollector.translateToLocal("ttmisc.active"));
		else par3List.add(StatCollector.translateToLocal("ttmisc.inactive"));
	}

	@Override
	public Icon getIcon(ItemStack stack, int pass) {
		return isEnabled(stack) ? enabledIcon : itemIcon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack par1ItemStack) {
		return EnumRarity.uncommon;
	}
}
