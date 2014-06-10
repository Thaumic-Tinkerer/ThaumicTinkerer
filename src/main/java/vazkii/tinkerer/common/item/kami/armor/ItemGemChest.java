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
 * File Created @ [Dec 26, 2013, 4:18:27 PM (GMT)]
 */
package vazkii.tinkerer.common.item.kami.armor;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.research.ResearchPage;
import vazkii.tinkerer.client.model.kami.ModelWings;
import vazkii.tinkerer.common.ThaumicTinkerer;
import vazkii.tinkerer.common.core.handler.ConfigHandler;
import vazkii.tinkerer.common.item.foci.ItemFocusDeflect;
import vazkii.tinkerer.common.lib.LibItemNames;
import vazkii.tinkerer.common.lib.LibResearch;
import vazkii.tinkerer.common.research.IRegisterableResearch;
import vazkii.tinkerer.common.research.KamiResearchItem;
import vazkii.tinkerer.common.research.ResearchHelper;

import java.util.ArrayList;
import java.util.List;

public class ItemGemChest extends ItemIchorclothArmorAdv {

	public static List<String> playersWithFlight = new ArrayList();

	public ItemGemChest(int par2) {
		super(par2);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot) {
		return new ModelWings();
	}

	@Override
	boolean ticks() {
		return true;
	}

	@Override
	void tickPlayer(EntityPlayer player) {
		ItemStack armor = player.getCurrentArmor(2);
		if (armor.getItemDamage() == 1 || !ThaumicTinkerer.proxy.armorStatus(player))
			return;

		ItemFocusDeflect.protectFromProjectiles(player);
	}

	@Override
	public String getItemName() {
		return LibItemNames.ICHOR_LEGS_GEM;
	}

	@Override
	public IRegisterableResearch getResearchItem() {
		return (IRegisterableResearch) new KamiResearchItem(LibResearch.KEY_ICHORCLOTH_CHEST_GEM, new AspectList().add(Aspect.AIR, 2).add(Aspect.MOTION, 1).add(Aspect.FLIGHT, 1).add(Aspect.ELDRITCH, 1), 17, 7, 5, new ItemStack(this)).setParents(LibResearch.KEY_ICHORCLOTH_ARMOR)
				.setPages(new ResearchPage("0"), ResearchHelper.infusionPage(LibResearch.KEY_ICHORCLOTH_CHEST_GEM));

	}

	@SubscribeEvent
	public void updatePlayerFlyStatus(LivingUpdateEvent event) {
		if (event.entityLiving instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.entityLiving;

			ItemStack armor = player.getCurrentArmor(3 - armorType);
			if (armor != null && armor.getItem() == this)
				tickPlayer(player);

			if (playersWithFlight.contains(playerStr(player))) {
				if (shouldPlayerHaveFlight(player))
					player.capabilities.allowFlying = true;
				else {
					if (!player.capabilities.isCreativeMode) {
						player.capabilities.allowFlying = false;
						player.capabilities.isFlying = false;
						player.capabilities.disableDamage = false;
					}
					playersWithFlight.remove(playerStr(player));
				}
			} else if (shouldPlayerHaveFlight(player)) {
				playersWithFlight.add(playerStr(player));
				player.capabilities.allowFlying = true;
			}
		}
	}

	public static String playerStr(EntityPlayer player) {
		return player.getGameProfile().getName() + ":" + player.worldObj.isRemote;
	}

	private static boolean shouldPlayerHaveFlight(EntityPlayer player) {
		ItemStack armor = player.getCurrentArmor(2);
		return armor != null && armor.getItem() == ThaumicTinkerer.registryItems.getFirstItemFromClass(ItemGemChest.class) && ThaumicTinkerer.proxy.armorStatus(player) && armor.getItemDamage() == 0 && ConfigHandler.enableFlight;
	}

}
