package com.nekokittygames.thaumictinkerer.api;

import com.nekokittygames.thaumictinkerer.api.rendering.IMultiBlockPreviewRenderer;
import net.minecraft.block.BlockChest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ThaumicTinkererAPI {
    public static ArrayList<String> AnimationTabletBlacklist=new ArrayList<>();

    static
    {
        AnimationTabletBlacklist.add(BlockChest.class.getName());
    }

}
