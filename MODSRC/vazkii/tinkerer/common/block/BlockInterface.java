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
 * File Created @ [8 Sep 2013, 18:57:25 (GMT)]
 */
package vazkii.tinkerer.common.block;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import vazkii.tinkerer.common.block.tile.TileInterface;

public class BlockInterface extends BlockModContainer {

    public BlockInterface(int par1) {
        super(par1, Material.iron);
        setHardness(3F);
        setResistance(10F);
    }

    @Override
    public TileEntity createNewTileEntity(World world) {
        return new TileInterface();
    }

}