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
 * File Created @ [3 Jul 2013, 13:49:03 (GMT)]
 */
package vazkii.tinkerer.util.handler;

import vazkii.tinkerer.item.ModItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;

public class RainwaterBottleHandler {

	@ForgeSubscribe
	public void onPlayerInteract(PlayerInteractEvent event) {
		boolean rightEvent = event.action == Action.RIGHT_CLICK_AIR;
		ItemStack stack = event.entityPlayer.getCurrentEquippedItem();
		boolean correctStack = stack != null && stack.itemID == Item.glassBottle.itemID;
		boolean raining = event.entityPlayer.worldObj.getWorldInfo().isRaining();
		boolean canCatch = event.entityPlayer.worldObj.canBlockSeeTheSky((int) event.entityPlayer.posX, (int) event.entityPlayer.posY, (int) event.entityPlayer.posZ);
		
		if(rightEvent && correctStack && raining && canCatch) {
			if(stack.stackSize == 1 && !event.entityPlayer.inventory.hasItem(ModItems.rainwaterBottle.itemID)) {
				stack.itemID = ModItems.rainwaterBottle.itemID;
			} else {
				ItemStack stack1 = new ItemStack(ModItems.rainwaterBottle);

				if(!event.entityPlayer.inventory.addItemStackToInventory(stack1))
					event.entityPlayer.dropPlayerItem(stack1);
				
				stack.stackSize--;
				if(stack.stackSize == 0)
					event.entityPlayer.inventory.setInventorySlotContents(event.entityPlayer.inventory.currentItem, null);
			}
		}
	}
	
}
