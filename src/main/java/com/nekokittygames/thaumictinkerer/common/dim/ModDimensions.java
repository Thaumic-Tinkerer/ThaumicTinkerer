package com.nekokittygames.thaumictinkerer.common.dim;

import com.nekokittygames.thaumictinkerer.common.config.TTConfig;
import com.nekokittygames.thaumictinkerer.common.libs.LibMisc;
import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;

public class ModDimensions {

    public static DimensionType testDimensionType;

    public static void init() {
        registerDimensionTypes();
        registerDimensions();
    }

    private static void registerDimensionTypes() {
        testDimensionType = DimensionType.register(LibMisc.MOD_ID, "_test", TTConfig.BedRockDimensionID, BedrockWorldProvider.class, false);
    }

    private static void registerDimensions() {
        DimensionManager.registerDimension(TTConfig.BedRockDimensionID, testDimensionType);
    }
}