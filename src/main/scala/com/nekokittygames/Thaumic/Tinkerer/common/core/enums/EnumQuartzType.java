package com.nekokittygames.Thaumic.Tinkerer.common.core.enums;

import net.minecraft.util.IStringSerializable;

/**
 * Created by Katrina on 18/05/2015.
 */
public enum EnumQuartzType implements IStringSerializable {
    CHISEL,
    PILLAR;

    private EnumQuartzType() {
    }

    public String getName() {
        return this.name().toLowerCase();
    }

    public String toString() {
        return this.getName();
    }
}
