package com.nekokittygames.thaumictinkerer.api;

import net.minecraft.block.BlockChest;

import java.util.ArrayList;

public class ThaumicTinkererAPI {
    public static ArrayList<String> AnimationTabletBlacklist = new ArrayList<>();

    static {
        AnimationTabletBlacklist.add(BlockChest.class.getName());
    }

}
