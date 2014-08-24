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
package thaumic.tinkerer.common.item;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.research.ResearchPage;
import thaumcraft.common.config.ConfigItems;
import thaumic.tinkerer.client.core.helper.IconHelper;
import thaumic.tinkerer.common.ThaumicTinkerer;
import thaumic.tinkerer.common.core.helper.ItemNBTHelper;
import thaumic.tinkerer.common.item.quartz.ItemDarkQuartz;
import thaumic.tinkerer.common.lib.LibFeatures;
import thaumic.tinkerer.common.lib.LibItemNames;
import thaumic.tinkerer.common.lib.LibResearch;
import thaumic.tinkerer.common.registry.ItemBase;
import thaumic.tinkerer.common.registry.ThaumicTinkererInfusionRecipe;
import thaumic.tinkerer.common.registry.ThaumicTinkererRecipe;
import thaumic.tinkerer.common.research.IRegisterableResearch;
import thaumic.tinkerer.common.research.ResearchHelper;
import thaumic.tinkerer.common.research.TTResearchItem;

import java.util.List;

public class ItemXPTalisman extends ItemBase implements IBauble {

	private static final String TAG_XP = "xp";
	IIcon enabledIcon;

	public ItemXPTalisman() {
		super();
		setMaxStackSize(1);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
		if (par3EntityPlayer.isSneaking()) {
			if (getXP(par1ItemStack) < LibFeatures.XP_TALISMAN_MAX_XP) {
				int dmg = par1ItemStack.getItemDamage();
				par1ItemStack.setItemDamage(~dmg & 1);
				par2World.playSoundAtEntity(par3EntityPlayer, "random.orb", 0.3F, 0.1F);
			}
		} else if (getXP(par1ItemStack) >= LibFeatures.XP_TALISMAN_ENCHANTING_BOTTLE_COST) {
			boolean has = par3EntityPlayer.inventory.consumeInventoryItem(Items.glass_bottle);
			if (has) {
				if (!par3EntityPlayer.inventory.addItemStackToInventory(new ItemStack(Items.experience_bottle, 1)) && !par2World.isRemote)
					par3EntityPlayer.dropItem(Items.experience_bottle, 1);
				int xp = getXP(par1ItemStack);
				setXP(par1ItemStack, xp - LibFeatures.XP_TALISMAN_ENCHANTING_BOTTLE_COST);
				par2World.playSoundAtEntity(par3EntityPlayer, "random.orb", 0.1F, (float) (0.1F + Math.random() / 2F));
				for (int i = 0; par2World.isRemote && i < 6; i++)
					ThaumicTinkerer.tcProxy.sparkle((float) (par3EntityPlayer.posX + (Math.random() - 0.5)), (float) (par3EntityPlayer.posY + Math.random() - 0.5), (float) (par3EntityPlayer.posZ + (Math.random() - 0.5)), 3);
			}
		}

		return par1ItemStack;
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
		if (getXP(par1ItemStack) >= LibFeatures.XP_TALISMAN_MAX_XP)
			par3List.add(StatCollector.translateToLocal("ttmisc.full"));
		else if (par1ItemStack.getItemDamage() == 0)
			par3List.add(StatCollector.translateToLocal("ttmisc.notAbsorbing"));
		else par3List.add(StatCollector.translateToLocal("ttmisc.absorbing"));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister par1IconRegister) {
		itemIcon = IconHelper.forItem(par1IconRegister, this, 0);
		enabledIcon = IconHelper.forItem(par1IconRegister, this, 1);
	}

	@Override
	public boolean shouldDisplayInTab() {
		return true;
	}

	@Override
	public IRegisterableResearch getResearchItem() {
		return (TTResearchItem) new TTResearchItem(LibResearch.KEY_XP_TALISMAN, new AspectList().add(Aspect.GREED, 1).add(Aspect.MAGIC, 1).add(Aspect.MAN, 1), 4, -1, 2, new ItemStack(this, 1, 1)).setParents("JARBRAIN", LibResearch.KEY_SPELL_CLOTH).setConcealed()
				.setPages(new ResearchPage("0"), ResearchHelper.infusionPage(LibResearch.KEY_XP_TALISMAN)).setSecondary();

	}

	@Override
	public ThaumicTinkererRecipe getRecipeItem() {
		return new ThaumicTinkererInfusionRecipe(LibResearch.KEY_XP_TALISMAN, new ItemStack(this), 6, new AspectList().add(Aspect.GREED, 20).add(Aspect.EXCHANGE, 10).add(Aspect.BEAST, 10).add(Aspect.MECHANISM, 5), new ItemStack(Items.gold_ingot),
                new ItemStack(Items.quartz), new ItemStack(ThaumicTinkerer.registry.getFirstItemFromClass(ItemDarkQuartz.class)), new ItemStack(ConfigItems.itemZombieBrain), new ItemStack(Items.diamond));
    }

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int par1) {
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

	@Override
	public BaubleType getBaubleType(ItemStack itemstack) {
		return BaubleType.AMULET;
	}

	@Override
	public void onWornTick(ItemStack par1ItemStack, EntityLivingBase player) {
		World par2World = player.worldObj;
		if (par1ItemStack.getItemDamage() == 1 && !par2World.isRemote) {
			int r = LibFeatures.XP_TALISMAN_RANGE;
			int currentXP = getXP(par1ItemStack);
			int xpToAdd = 0;
			int maxXP = LibFeatures.XP_TALISMAN_MAX_XP - currentXP; // Max, to prevent overflow.
			if (maxXP <= 0) {
				par1ItemStack.setItemDamage(0);
				return; // Can't take any XP.
			}

			AxisAlignedBB boundingBox = AxisAlignedBB.getBoundingBox(player.posX - r, player.posY - r, player.posZ - r, player.posX + r, player.posY + r, player.posZ + r);
			List<EntityXPOrb> orbs = par2World.getEntitiesWithinAABB(EntityXPOrb.class, boundingBox);

			for (EntityXPOrb orb : orbs) {
				if (!orb.isDead) {
					int xp = orb.getXpValue();
					if (xpToAdd + xp <= maxXP) {
						xpToAdd += xp;
						consumeXPOrb(orb);
					}

					maxXP -= xpToAdd;

					if (maxXP <= 0)
						break;
				}
			}

			setXP(par1ItemStack, Math.min(LibFeatures.XP_TALISMAN_MAX_XP, currentXP + xpToAdd));
		}
	}

	@Override
	public void onEquipped(ItemStack itemstack, EntityLivingBase player) {

	}

	@Override
	public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {

	}

	@Override
	public boolean canEquip(ItemStack itemstack, EntityLivingBase player) {
		return true;
	}

	@Override
	public boolean canUnequip(ItemStack itemstack, EntityLivingBase player) {
		return true;
	}

	@Override
	public String getItemName() {
		return LibItemNames.XP_TALISMAN;
	}
}