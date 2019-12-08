package com.nekokittygames.thaumictinkerer.common.items;

import com.nekokittygames.thaumictinkerer.common.libs.LibItemNames;
import com.nekokittygames.thaumictinkerer.common.libs.LibOreDict;
import com.nekokittygames.thaumictinkerer.common.misc.IOreDict;

public class ItemBlackQuartz extends TTItem implements IOreDict {
    public ItemBlackQuartz() {
        super(LibItemNames.BLACK_QUARTZ);
    }

    @Override
    public String getOreDictName() {
        return LibOreDict.BLACK_QUARTZ;
    }
}
