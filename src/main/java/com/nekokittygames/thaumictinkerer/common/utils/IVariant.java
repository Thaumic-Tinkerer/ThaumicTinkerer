package com.nekokittygames.thaumictinkerer.common.utils;

import net.minecraft.util.IStringSerializable;

public interface IVariant extends IStringSerializable {

    /**
     * Get the metadata value of this variant.
     *
     * @return The metadata value
     */
    int getMeta();
}