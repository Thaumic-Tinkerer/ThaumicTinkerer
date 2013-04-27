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
 * File Created @ [27 Apr 2013, 18:23:29 (GMT)]
 */
package vazkii.tinkerer.item;

import java.util.List;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import thaumcraft.common.entities.golems.EntityGolemBase;
import vazkii.tinkerer.util.handler.EntityInteractionHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemNametag extends ItemMod {

	public ItemNametag(int par1) {
		super(par1);
	}

	@Override
	public boolean itemInteractionForEntity(ItemStack par1ItemStack, EntityLiving par2EntityLiving) {
		if(par2EntityLiving instanceof EntityGolemBase) {
			EntityGolemBase golem = (EntityGolemBase) par2EntityLiving;
			EntityPlayer player = EntityInteractionHandler.getLastInteractingPlayer();
			if(!par1ItemStack.hasDisplayName()) {
				if(player.worldObj.isRemote)
					player.addChatMessage("This name tag is blank. You need to name it using an anvil.");
				return true;
			}

			if(golem.getOwnerName().equals(player.username)) {
				golem.func_94058_c(par1ItemStack.getDisplayName());
				golem.playLivingSound();
				/*if(player.worldObj.isRemote)
					player.swingItem();*/
				par1ItemStack.stackSize--;
			} else if(player.worldObj.isRemote)
				player.addChatMessage("That golem isn't yours.");
		}

		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		if(!par1ItemStack.hasDisplayName())
			par3List.add("Blank");
	}

}
