package vazkii.tinkerer.common.potion;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import vazkii.tinkerer.common.ThaumicTinkerer;

import java.util.Random;

/**
 * Created by pixlepix on 4/19/14.
 */
public class BlockForcefield extends Block {
    public BlockForcefield() {
        super(Material.air);
    }

    @Override
    public int getRenderType() {
        return -1;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public TileEntity createTileEntity(World world, int metadata) {
        return new TileForcefield();
    }

    @Override
    public boolean hasTileEntity(int metadata) {
        return true;
    }
}
