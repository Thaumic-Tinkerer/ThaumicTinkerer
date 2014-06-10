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
 * File Created @ [Dec 25, 2013, 12:20:05 AM (GMT)]
 */
package vazkii.tinkerer.common.item.kami.armor;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import vazkii.tinkerer.client.core.handler.kami.ToolModeHUDHandler;
import vazkii.tinkerer.client.lib.LibResources;
import vazkii.tinkerer.common.research.IRegisterableResearch;

import java.util.List;

public abstract class ItemIchorclothArmorAdv extends ItemIchorclothArmor {

	public ItemIchorclothArmorAdv(int par2) {
		super(par2);
		setHasSubtypes(true);
		if (ticks())
			MinecraftForge.EVENT_BUS.register(this);
	}

	public ItemIchorclothArmorAdv() {
		this(0);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
		if (par3EntityPlayer.isSneaking()) {
			int dmg = par1ItemStack.getItemDamage();
			par1ItemStack.setItemDamage(~dmg & 1);
			par2World.playSoundAtEntity(par3EntityPlayer, "random.orb", 0.3F, 0.1F);

			ToolModeHUDHandler.setTooltip(StatCollector.translateToLocal("ttmisc.awakenedArmor" + par1ItemStack.getItemDamage()));

			return par1ItemStack;
		}

		return super.onItemRightClick(par1ItemStack, par2World, par3EntityPlayer);
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer par2EntityPlayer, List list, boolean par4) {
		super.addInformation(stack, par2EntityPlayer, list, par4);
		if (stack.getItemDamage() == 1)
			list.add(StatCollector.translateToLocal("ttmisc.awakenedArmor1"));
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
		return slot == 2 ? LibResources.MODEL_ARMOR_ICHOR_GEM_2 : LibResources.MODEL_ARMOR_ICHOR_GEM_1;
	}

	boolean ticks() {
		return false;
	}

	@SubscribeEvent
	public void onEntityUpdate(LivingUpdateEvent event) {
		if (event.entityLiving instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.entityLiving;

			ItemStack armor = player.getCurrentArmor(3 - armorType);
			if (armor != null && armor.getItem() == this)
				tickPlayer(player);
		}
	}

	void tickPlayer(EntityPlayer player) {
		// NO-OP
	}

	@Override
	public abstract String getItemName();

	@Override
	public IRegisterableResearch getResearchItem() {
		return null;
	}
}
