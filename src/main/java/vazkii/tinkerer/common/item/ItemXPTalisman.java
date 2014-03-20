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
 * File Created @ [28 Sep 2013, 18:27:56 (GMT)]
 */
package vazkii.tinkerer.common.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import vazkii.tinkerer.client.core.helper.IconHelper;
import vazkii.tinkerer.common.ThaumicTinkerer;
import vazkii.tinkerer.common.core.helper.ItemNBTHelper;
import vazkii.tinkerer.common.lib.LibFeatures;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemXPTalisman extends ItemMod {

	private static final String TAG_XP = "xp";
	Icon enabledIcon;

	public ItemXPTalisman() {
		super();
		setMaxStackSize(1);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
		if(par3EntityPlayer.isSneaking()) {
			if(getXP(par1ItemStack) < LibFeatures.XP_TALISMAN_MAX_XP) {
	 			int dmg = par1ItemStack.getItemDamage();
				par1ItemStack.setItemDamage(~dmg & 1);
				par2World.playSoundAtEntity(par3EntityPlayer, "random.orb", 0.3F, 0.1F);
			}
		} else if(getXP(par1ItemStack) >= LibFeatures.XP_TALISMAN_ENCHANTING_BOTTLE_COST) {
			boolean has = par3EntityPlayer.inventory.consumeInventoryItem(Item.glassBottle.itemID);
			if(has) {
				if(!par3EntityPlayer.inventory.addItemStackToInventory(new ItemStack(Item.expBottle, 1)) && !par2World.isRemote)
					par3EntityPlayer.dropItem(Item.expBottle.itemID, 1);
				int xp = getXP(par1ItemStack);
				setXP(par1ItemStack, xp - LibFeatures.XP_TALISMAN_ENCHANTING_BOTTLE_COST);
				par2World.playSoundAtEntity(par3EntityPlayer, "random.orb", 0.1F, (float) (0.1F + Math.random() / 2F));
				for(int i = 0; par2World.isRemote && i < 6; i++)
					ThaumicTinkerer.tcProxy.sparkle((float) (par3EntityPlayer.posX + (Math.random() - 0.5)), (float) (par3EntityPlayer.posY + Math.random() - 0.5), (float) (par3EntityPlayer.posZ + (Math.random() - 0.5)), 3);
			}
		}

		return par1ItemStack;
	}

	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5) {
		if(par1ItemStack.getItemDamage() == 1 && !par2World.isRemote) {
			int r = LibFeatures.XP_TALISMAN_RANGE;
			int currentXP = getXP(par1ItemStack);
			int xpToAdd = 0;
			int maxXP = LibFeatures.XP_TALISMAN_MAX_XP - currentXP; // Max, to prevent overflow.
			if(maxXP <= 0) {
				par1ItemStack.setItemDamage(0);
				return; // Can't take any XP.
			}

			AxisAlignedBB boundingBox = AxisAlignedBB.getBoundingBox(par3Entity.posX - r, par3Entity.posY - r, par3Entity.posZ - r, par3Entity.posX + r, par3Entity.posY + r, par3Entity.posZ + r);
			List<EntityXPOrb> orbs = par2World.getEntitiesWithinAABB(EntityXPOrb.class, boundingBox);

			for(EntityXPOrb orb : orbs) {
				if(!orb.isDead) {
					int xp = orb.getXpValue();
					if(xpToAdd + xp <= maxXP) {
						xpToAdd += xp;
						consumeXPOrb(orb);
					}

					maxXP -= xpToAdd;

					if(maxXP <= 0)
						break;
				}
			}

			setXP(par1ItemStack, Math.min(LibFeatures.XP_TALISMAN_MAX_XP, currentXP + xpToAdd));
		}
	}

	private void consumeXPOrb(EntityXPOrb orb) {
		orb.setDead();
		orb.worldObj.playSoundAtEntity(orb, "thaumcraft:zap", orb.getXpValue() / 10F, 1F);
		ThaumicTinkerer.tcProxy.wispFX(orb.worldObj, orb.posX, orb.posY, orb.posZ, orb.getXpValue() / 5F, 0.1F, 0.9F, 0.1F);
	}

	@Override
	public boolean getShareTag() {
		return true;
	}

	@Override
    @SideOnly(Side.CLIENT)
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		par3List.add("XP: " + getXP(par1ItemStack));
		if(getXP(par1ItemStack) >= LibFeatures.XP_TALISMAN_MAX_XP)
			par3List.add(StatCollector.translateToLocal("ttmisc.full"));
		else if(par1ItemStack.getItemDamage() == 0)
			par3List.add(StatCollector.translateToLocal("ttmisc.notAbsorbing"));
		else par3List.add(StatCollector.translateToLocal("ttmisc.absorbing"));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		itemIcon = IconHelper.forItem(par1IconRegister, this, 0);
		enabledIcon = IconHelper.forItem(par1IconRegister, this, 1);
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

	public static boolean hasCmp(ItemStack stack) {
		return ItemNBTHelper.detectNBT(stack);
	}

	public static int getXP(ItemStack stack) {
		return ItemNBTHelper.getInt(stack, TAG_XP, 0);
	}

	public static void setXP(ItemStack stack, int xp) {
		ItemNBTHelper.setInt(stack, TAG_XP, xp);
	}

}