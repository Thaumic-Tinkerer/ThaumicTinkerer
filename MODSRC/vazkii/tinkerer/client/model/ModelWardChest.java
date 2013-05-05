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
 * File Created @ [5 May 2013, 21:38:17 (GMT)]
 */
package vazkii.tinkerer.client.model;

import vazkii.tinkerer.lib.LibMisc;
import net.minecraft.client.model.ModelChest;

public class ModelWardChest extends ModelChest {
	
	@Override
	public void renderAll() {
        chestKnob.render(LibMisc.MODEL_DEFAULT_RENDER_SCALE);
        chestBelow.render(LibMisc.MODEL_DEFAULT_RENDER_SCALE);
        chestLid.render(LibMisc.MODEL_DEFAULT_RENDER_SCALE);
	}

}
