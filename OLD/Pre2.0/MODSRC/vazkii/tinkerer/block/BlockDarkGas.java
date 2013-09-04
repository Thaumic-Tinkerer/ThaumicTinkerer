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
 * File Created @ [19 May 2013, 16:17:59 (GMT)]
 */
package vazkii.tinkerer.block;

import java.util.Random;

import net.minecraft.world.World;
import vazkii.tinkerer.ThaumicTinkerer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockDarkGas extends BlockGas {

	public BlockDarkGas(int par1) {
		super(par1);
		setLightOpacity(215);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random) {
		if(par5Random.nextFloat() < 0.0075F)
			ThaumicTinkerer.tcProxy.wispFX2(par1World, par2 + 0.5, par3 + 0.5, par4 + 0.5, 0.125F, 5, true, -0.02F);
	}

	@Override
	public void placeParticle(World world, int par2, int par3, int par4) {
		ThaumicTinkerer.tcProxy.wispFX2(world, par2 + 0.5, par3 + 0.5, par4 + 0.5, 0.125F, 5, true, -0.02F);
	}
}