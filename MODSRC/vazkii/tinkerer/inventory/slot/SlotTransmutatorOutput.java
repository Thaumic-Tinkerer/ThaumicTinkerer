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
 * File Created @ [30 Apr 2013, 22:11:57 (GMT)]
 */
package vazkii.tinkerer.inventory.slot;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import thaumcraft.api.EnumTag;
import thaumcraft.api.IAspectSource;
import thaumcraft.api.ObjectTags;
import thaumcraft.common.aura.AuraManager;
import thaumcraft.common.lib.ThaumcraftCraftingManager;
import vazkii.tinkerer.tile.TileEntityTransmutator;

public class SlotTransmutatorOutput extends SlotPureOutput {

	TileEntityTransmutator transmutator;

	public SlotTransmutatorOutput(TileEntityTransmutator transmutator, int par2, int par3, int par4) {
		super(transmutator, par2, par3, par4);
		this.transmutator = transmutator;
	}

	@Override
	public void onPickupFromSlot(EntityPlayer par1EntityPlayer, ItemStack par2ItemStack) {
		ObjectTags tags = ThaumcraftCraftingManager.getObjectTags(transmutator.getStackInSlot(0));
		int value = SlotTransmutator.getTotalAspectValue(tags);
		int cost = value == 1 ? 0 : value / 2;

		AuraManager.decreaseClosestAura(transmutator.worldObj, transmutator.xCoord, transmutator.yCoord, transmutator.zCoord, cost);

		for(EnumTag tag : tags.getAspects()) {
			IAspectSource source = transmutator.foundTags.getSource(tag);
			if(source != null)
				source.takeFromSource(tag, tags.getAmount(tag));
		}

		par1EntityPlayer.worldObj.playSoundAtEntity(par1EntityPlayer, "thaumcraft.wand", 0.3F, 1F);
	}

}
