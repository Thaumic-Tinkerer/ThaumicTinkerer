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
 * File Created @ [26 Jul 2013, 15:27:45 (GMT)]
 */
package vazkii.tinkerer.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.ReflectionHelper;

public class ItemIceGlobe extends ItemMod {

	public ItemIceGlobe(int par1) {
		super(par1);

		setMaxStackSize(1);
	}

	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
		if(!(entity instanceof EntityLiving))
			return false;

		EntityLiving living = (EntityLiving) entity;

		if(!living.func_104002_bU()) { // Check if presistanceRequired
			player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
			ReflectionHelper.setPrivateValue(EntityLiving.class, living, true, 72); // Set presistanceRequired to true
			if(!player.worldObj.isRemote)
				player.worldObj.playSoundAtEntity(entity, "mob.zombie.remedy", 1F, 1F);
		} else {
			if(player.worldObj.isRemote)
				player.addChatMessage("This entity is already preserved.");
		}

		return true;
	}
}
