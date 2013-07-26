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
 * File Created @ [26 Jul 2013, 02:13:54 (GMT)]
 */
package vazkii.tinkerer.block;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import vazkii.tinkerer.tile.TileEntityInterface;

public class BlockInterface extends BlockModContainer {

    public BlockInterface(int par1) {
        super(par1, Material.iron);
    }

    @Override
    public TileEntity createNewTileEntity(World world) {
        return new TileEntityInterface();
    }


}
