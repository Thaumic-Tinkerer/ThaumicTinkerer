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
 * File Created @ [25 Oct 2013, 19:11:44 (GMT)]
 */
package thaumic.tinkerer.common.multipart;

import net.minecraft.block.Block;
import thaumcraft.common.config.ConfigBlocks;
import thaumic.tinkerer.common.ThaumicTinkerer;
import codechicken.lib.vec.Cuboid6;
import codechicken.multipart.minecraft.McMetaPart;

public class PartNitor extends McMetaPart {
    public PartNitor() {
        super(1);
    }

    @Override
    public Cuboid6 getBounds() {
        return new Cuboid6(0.3, 0.3, 0.3, 0.7, 0.7, 0.7);
    }

    @Override
    public boolean doesTick() {
        return true;
    }

    @Override
    public void update() {
        if (world().isRemote) {
            if (world().rand.nextInt(9 - ThaumicTinkerer.tcProxy.particleCount(3)) == 0)
                ThaumicTinkerer.tcProxy.wispFX3(world(), x() + 0.5F, y() + 0.5F, z() + 0.5F, x() + 0.3F + world().rand.nextFloat() * 0.4F, y() + 0.5F, z() + 0.3F + world().rand.nextFloat() * 0.4F, 0.5F, 4, true, -0.025F);
            if (world().rand.nextInt(15 - ThaumicTinkerer.tcProxy.particleCount(5)) == 0)
                ThaumicTinkerer.tcProxy.wispFX3(world(), x() + 0.5F, y() + 0.5F, z() + 0.5F, x() + 0.4F + world().rand.nextFloat() * 0.2F, y() + 0.5F, z() + 0.4F + world().rand.nextFloat() * 0.2F, 0.25F, 1, true, -0.02F);
        }
    }

    @Override
    public Block getBlock() {
        return ConfigBlocks.blockAiry;
    }

    @Override
    public int getMetadata() {
        return 1;
    }

    @Override
    public String getType() {
        return getBlock().getUnlocalizedName();
    }
}