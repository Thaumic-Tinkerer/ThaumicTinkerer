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
 * File Created @ [8 Sep 2013, 22:21:13 (GMT)]
 */
package vazkii.tinkerer.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.world.World;
import vazkii.tinkerer.common.ThaumicTinkerer;

import java.util.Random;

public class BlockGaseousShadow extends BlockGas {

	public BlockGaseousShadow() {
		super();
		setLightOpacity(215);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random) {
		if (par5Random.nextFloat() < 0.0075F)
			ThaumicTinkerer.tcProxy.wispFX2(par1World, par2 + 0.5, par3 + 0.5, par4 + 0.5, 0.125F, 5, true, -0.02F);
	}

	@Override
	public void placeParticle(World world, int par2, int par3, int par4) {
		ThaumicTinkerer.tcProxy.wispFX2(world, par2 + 0.5, par3 + 0.5, par4 + 0.5, 0.125F, 5, true, -0.02F);
	}
}