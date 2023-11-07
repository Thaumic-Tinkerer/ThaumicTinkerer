package com.nekokittygames.thaumictinkerer.common.compat.Tconstruct;

import net.minecraft.nbt.NBTTagCompound;
import slimeknights.tconstruct.library.traits.AbstractTraitLeveled;
import slimeknights.tconstruct.library.utils.TagUtil;

public class TraitWritableAdv extends AbstractTraitLeveled {
    public TraitWritableAdv(int levels) {
        super("writableadv", String.valueOf(levels), 0xFF0DCE53, 3, 1);
    }

    public void applyModifierEffect(NBTTagCompound rootCompound) {
        NBTTagCompound toolTag = TagUtil.getToolTag(rootCompound);
        int modifiers = toolTag.getInteger("FreeModifiers") + this.levels * 2;
        toolTag.setInteger("FreeModifiers", Math.max(0, modifiers));
        TagUtil.setToolTag(rootCompound, toolTag);
    }
}