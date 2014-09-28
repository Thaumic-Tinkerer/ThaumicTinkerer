package thaumic.tinkerer.common.core.helper;

import net.minecraft.block.Block;

/**
 * Created by pixlepix on 9/28/14.
 */
public class BlockTuple {
    public Block block;
    public int meta;

    public BlockTuple(Block b, int meta) {
        this.block = b;
        this.meta = meta;
    }

    public BlockTuple(Block b) {
        this.block = b;
        this.meta = 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof BlockTuple)) {
            return false;
        }
        return ((BlockTuple) obj).block == block && ((BlockTuple) obj).meta == meta;
    }

    @Override
    public int hashCode() {
        return block.hashCode() + meta;
    }
}
