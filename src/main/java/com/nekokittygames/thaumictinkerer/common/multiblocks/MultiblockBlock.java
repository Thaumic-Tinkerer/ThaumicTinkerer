package com.nekokittygames.thaumictinkerer.common.multiblocks;

import com.google.gson.JsonObject;
import net.minecraft.client.util.JsonException;
import net.minecraft.util.JsonUtils;

public class MultiblockBlock {
    private String blockName;
    private int xOffset;
    private int zOffset;
    private int extraMeta;

    MultiblockBlock(JsonObject object) throws Exception {
        if (!object.has("x"))
            throw new JsonException("Layer object has no x offset");
        xOffset = JsonUtils.getInt(object, "x");
        if (!object.has("z"))
            throw new JsonException("Layer object has no z offset");
        zOffset = JsonUtils.getInt(object, "z");
        if (!object.has("block"))
            throw new JsonException("Layer object has no blockType");
        blockName = JsonUtils.getString(object, "block");
        if (object.has("meta")) {
            extraMeta = JsonUtils.getInt(object, "meta");
        } else
            extraMeta = -1;

    }

    int getExtraMeta() {
        return extraMeta;
    }

    public String getBlockName() {
        return blockName;

    }

    public int getxOffset() {
        return xOffset;
    }

    public int getzOffset() {
        return zOffset;
    }
}
