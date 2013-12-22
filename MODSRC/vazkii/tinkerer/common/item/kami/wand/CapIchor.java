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
 * File Created @ [Dec 22, 2013, 7:10:45 PM (GMT)]
 */
package vazkii.tinkerer.common.item.kami.wand;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import thaumcraft.api.wands.WandCap;
import vazkii.tinkerer.client.lib.LibResources;
import vazkii.tinkerer.common.item.ModItems;

public class CapIchor extends WandCap {
	
	ResourceLocation res = new ResourceLocation(LibResources.MODEL_CAP_ICHOR);

	public CapIchor() {
		super("ichor", 0.85F, new ItemStack(ModItems.kamiResource, 1, 4), 10);
	}
	
	@Override
	public ResourceLocation getTexture() {
		return res;
	}

}
