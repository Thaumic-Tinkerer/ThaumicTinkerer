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
 * File Created @ [16 Sep 2013, 23:36:41 (GMT)]
 */
package vazkii.tinkerer.client.fx;

import java.awt.Color;

import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.client.fx.FXEssentiaTrail;

public class FXAspectTrail extends FXEssentiaTrail {

	public FXAspectTrail(World par1World, double par2, double par4, double par6, double tx, double ty, double tz,  Aspect aspect) {
		super(par1World, par2, par4, par6, tx, ty, tz, 1);
		
		Color color = new Color(aspect.getColor());
		particleRed = (float) color.getRed() / 255F;
		particleGreen = (float) color.getGreen() / 255F;
		particleBlue = (float) color.getBlue() / 255F;
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		/*motionX *= 2;
		motionY *= 2;
		motionZ *= 2;*/
	}

}
