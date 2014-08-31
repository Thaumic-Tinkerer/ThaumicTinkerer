/*
*
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
* File Created @ [25 Oct 2013, 17:26:07 (GMT)]
*/
package thaumic.tinkerer.common.multipart;

import java.util.Arrays;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import thaumcraft.common.config.ConfigBlocks;
import codechicken.lib.vec.Cuboid6;
import codechicken.multipart.IRandomDisplayTick;
import codechicken.multipart.minecraft.McMetaPart;

public class PartCandle extends McMetaPart implements IRandomDisplayTick {
    public PartCandle(int meta) {
        super(meta);
    }

    public PartCandle() {
        super();
    }

    @Override
    public Cuboid6 getBounds() {
        return new Cuboid6(0.375, 0, 0.375, 0.625, 0.5, 0.625);
    }

    @Override
    public void randomDisplayTick(Random arg0) {
        getBlock().randomDisplayTick(world(), x(), y(), z(), arg0);
    }

    @Override
    public Block getBlock() {
        return ConfigBlocks.blockCandle;
    }

    @Override
    public Iterable<ItemStack> getDrops() {
        return Arrays.asList(new ItemStack(getBlock(), 1, meta));
    }

    @Override
    public String getType() {
        return getBlock().getUnlocalizedName();
    }
}