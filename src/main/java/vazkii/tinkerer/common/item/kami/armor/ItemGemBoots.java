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
 * File Created @ [Dec 27, 2013, 4:12:03 PM (GMT)]
 */
package vazkii.tinkerer.common.item.kami.armor;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import vazkii.tinkerer.common.ThaumicTinkerer;

import java.util.ArrayList;
import java.util.List;

public class ItemGemBoots extends ItemIchorclothArmorAdv {

	public static List<String> playersWith1Step = new ArrayList();

	public ItemGemBoots(int par2) {
		super(par2);
	}

	@Override
	boolean ticks() {
		return true;
	}

	@Override
	void tickPlayer(EntityPlayer player) {
        ItemStack armor = player.getCurrentArmor(0);
        if(!ThaumicTinkerer.proxy.armorStatus(player) || armor.getItemDamage() == 1)
            return;
		player.addPotionEffect(new PotionEffect(Potion.digSpeed.id, 2, 1, true));

		if(player.worldObj.isRemote)
			player.stepHeight = player.isSneaking() ? 0.5F : 1F;
		if((player.onGround || player.capabilities.isFlying) && player.moveForward > 0F)
			player.moveFlying(0F, 1F, player.capabilities.isFlying ? 0.075F : 0.15F);
		player.jumpMovementFactor = player.isSprinting() ? 0.05F : 0.04F;
		player.fallDistance = 0F;

		int x = (int) player.posX;
        int y = (int) player.posY - 1;
        int z = (int) player.posZ;
        if(player.worldObj.getBlock(x, y, z) == Blocks.dirt)
                player.worldObj.setBlock(x, y, z, Blocks.grass, 0, 2);
	}

	@SubscribeEvent
	public void onPlayerJump(LivingJumpEvent event) {
		if(event.entityLiving instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.entityLiving;
			boolean hasArmor = player.getCurrentArmor(0) != null && player.getCurrentArmor(0).getItem()== this;

			if(hasArmor && ThaumicTinkerer.proxy.armorStatus(player) && player.getCurrentArmor(0).getItemDamage()==0)
				 player.motionY += 0.3;
		}
	}

	@SubscribeEvent(priority = EventPriority.HIGH)
	public void onLivingUpdate(LivingUpdateEvent event) {
		if(event.entityLiving instanceof EntityPlayer && event.entityLiving.worldObj.isRemote) {
			EntityPlayer player = (EntityPlayer) event.entityLiving;

			boolean highStepListed = playersWith1Step.contains(player.getGameProfile().getName());
			boolean hasHighStep = player.getCurrentArmor(0) != null && player.getCurrentArmor(0).getItem() == this;

			if( !highStepListed && (hasHighStep && ThaumicTinkerer.proxy.armorStatus(player) && player.getCurrentArmor(0).getItemDamage()==0))
				playersWith1Step.add(player.getGameProfile().getName());

			if((!hasHighStep || !ThaumicTinkerer.proxy.armorStatus(player) || player.getCurrentArmor(0).getItemDamage()==1)&& highStepListed) {
				playersWith1Step.remove(player.getGameProfile().getName());
				player.stepHeight = 0.5F;
			}
		}
	}

}