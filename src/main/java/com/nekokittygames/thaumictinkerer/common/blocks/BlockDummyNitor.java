package com.nekokittygames.thaumictinkerer.common.blocks;

import com.nekokittygames.thaumictinkerer.api.IDummyBlock;
import com.nekokittygames.thaumictinkerer.common.libs.LibBlockNames;
import net.minecraft.block.material.Material;

public class BlockDummyNitor extends TTBlock implements IDummyBlock {
    public BlockDummyNitor() {
        super(LibBlockNames.DUMMY_NITOR,Material.CIRCUITS);
    }

    @Override
    protected boolean isInCreativeTab() {
        return false;
    }

    @Override
    public String getReplacementBlock() {
        return "thaumcraft:nitor_yellow";
    }
}
