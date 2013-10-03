/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the ThaumicTinkerer Mod.
 *
 * ThaumicTinkerer is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * ThaumicTinkerer is a Derivative Work on Thaumcraft 3.
 * Thaumcraft 3 � Azanor 2012
 * (http://www.minecraftforum.net/topic/1585216-)
 *
 * File Created @ [7 May 2013, 17:07:48 (GMT)]
 */
package vazkii.tinkerer.block;

import net.minecraft.block.BlockStairs;
import vazkii.tinkerer.util.helper.ModCreativeTab;

/**
 * BlockETStairs
 *
 * Elemental Tinkerer Implementation of BlockStairs.
 *
 * @author Vazkii
 */
public class BlockDarkQuartzStairs extends BlockStairs {

	protected BlockDarkQuartzStairs(int par1) {
		super(par1, ModBlocks.darkQuartz, 0);
		setCreativeTab(ModCreativeTab.INSTANCE);
	}
}
