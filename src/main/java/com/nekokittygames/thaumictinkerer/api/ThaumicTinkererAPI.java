package com.nekokittygames.thaumictinkerer.api;

import net.minecraft.block.BlockChest;

import java.util.ArrayList;

/**
 * API for Thaumic Tinkerer
 */
public class ThaumicTinkererAPI {
    private static ArrayList<String> AnimationTabletBlacklist = new ArrayList<>();

    /**
     * Gets the blacklist for the Animation tablet
     *
     * @return the array of blacklisted item names
     */
    public static ArrayList<String> getAnimationTabletBlacklist() {
        return AnimationTabletBlacklist;
    }

    static {
        AnimationTabletBlacklist.add(BlockChest.class.getName());
    }

}
