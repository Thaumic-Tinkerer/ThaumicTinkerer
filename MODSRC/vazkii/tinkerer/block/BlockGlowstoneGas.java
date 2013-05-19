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
 * File Created @ [19 May 2013, 16:10:50 (GMT)]
 */
package vazkii.tinkerer.block;

import java.util.Random;

import net.minecraft.world.World;
import vazkii.tinkerer.ThaumicTinkerer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockGlowstoneGas extends BlockGas {

	public BlockGlowstoneGas(int par1) {
		super(par1);
		setLightValue(0.85F);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random) {
		if(par5Random.nextFloat() < 0.0075F)
			ThaumicTinkerer.tcProxy.sparkle(par2 + 0.5F, par3 + 0.5F, par4 + 0.5F, 1F, 1, par5Random.nextFloat() / 2);
	}
	
	@Override
	public void placeParticle(World world, int par2, int par3, int par4) {
		ThaumicTinkerer.tcProxy.sparkle(par2 + 0.5F, par3 + 0.5F, par4 + 0.5F, 1);
	}
}
