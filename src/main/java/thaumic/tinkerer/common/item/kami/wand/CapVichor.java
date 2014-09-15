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
package thaumic.tinkerer.common.item.kami.wand;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import thaumcraft.api.wands.WandCap;
import thaumic.tinkerer.client.lib.LibResources;
import thaumic.tinkerer.common.ThaumicTinkerer;
import thaumic.tinkerer.common.item.kami.ItemKamiResource;

public class CapVichor extends WandCap {

	ResourceLocation res = new ResourceLocation(LibResources.MODEL_CAP_VICHOR);

	public CapVichor() {
		super("VICHOR", 0.6F, new ItemStack(ThaumicTinkerer.registry.getFirstItemFromClass(ItemKamiResource.class), 1, 8), 10);
	}

    public CapVichor(String s) {
        super(s, 0.6F, new ItemStack(ThaumicTinkerer.registry.getFirstItemFromClass(ItemKamiResource.class), 1, 8), 10);
    }

	@Override
	public ResourceLocation getTexture() {
		return res;
	}

}
