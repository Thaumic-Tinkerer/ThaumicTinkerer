package com.nekokittygames.thaumictinkerer.common.multiblocks;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.util.JsonUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MultiblockLayer implements Iterable<MultiblockBlock> {

    public List<MultiblockBlock> blocks=new ArrayList<>();
    public int getyLevel() {
        return yLevel;
    }

    public void setyLevel(int yLevel) {
        this.yLevel = yLevel;
    }

    private int yLevel;

    public MultiblockLayer(JsonObject object) throws Exception {
        if(!object.has("y"))
            throw new Exception("Layer object has no y level");
        yLevel= JsonUtils.getInt(object,"y");
        if(!object.has("blocks"))
            throw new Exception("Layer object has no blocks");
        JsonArray blockArray=JsonUtils.getJsonArray(object,"blocks");
        for(JsonElement blockElement:blockArray)
        {
            blocks.add(new MultiblockBlock(blockElement.getAsJsonObject()));
        }
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @NotNull
    @Override
    public Iterator<MultiblockBlock> iterator() {
        return blocks.iterator();
    }
}
