package com.nekokittygames.thaumictinkerer.api;

import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockPistonExtension;
import net.minecraft.block.BlockPistonMoving;
import thaumcraft.common.blocks.basic.BlockPillar;

import java.util.ArrayList;

/**
 * API for Thaumic Tinkerer
 */
public class ThaumicTinkererAPI {
    private static final ArrayList<String> AnimationTabletBlacklist = new ArrayList<>();
    private static final ArrayList<String> DislocationBlacklist = new ArrayList<>();

    /**
     * Gets the blacklist for the Animation tablet
     *
     * @return the array of blacklisted item names
     */
    public static ArrayList<String> getAnimationTabletBlacklist() {
        return AnimationTabletBlacklist;
    }

    public static ArrayList<String> getDislocationBlacklist() { return DislocationBlacklist;}
    static {
        AnimationTabletBlacklist.add(BlockChest.class.getName());

        DislocationBlacklist.add(BlockPistonMoving.class.getName());
        DislocationBlacklist.add(BlockPistonExtension.class.getName());
        DislocationBlacklist.add(BlockPillar.class.getName());
    }

}
