package com.nekokittygames.thaumictinkerer.common.multiblocks;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.JsonException;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.ArrayList;
import java.util.List;

public class MultiblockBlockType {

    private String blockName;
    private List<IBlockState> blockTypes=new ArrayList<>();

    public MultiblockBlockType(JsonObject object) throws Exception {
        if(!object.has("name"))
            throw new Exception("Multiblock json malformed, missing name for object "+object.toString());
        blockName=object.get("name").getAsString();
        if(!object.has("block"))
            throw new Exception("Multiblock json malformed, missing block array for object "+object.toString());
        JsonElement blocksObject= object.get("block");
        if(blocksObject.isJsonArray())
        {
            JsonArray blocks= (JsonArray) blocksObject;
            for(JsonElement block:blocks)
            {
                addBlock(block);
            }
        }
        else
            addBlock(blocksObject);

    }

    private void addBlock(JsonElement blocksObject) throws Exception {
        IBlockState state;
        if(blocksObject.isJsonObject())
        {
            JsonObject blockObj= (JsonObject) blocksObject;
            if(!blockObj.has("blockType"))
                throw new Exception("Multiblock json malformed, missing block array for object "+blockObj.toString());
            Block block=ForgeRegistries.BLOCKS.getValue(new ResourceLocation(blockObj.get("blockType").getAsString()));
            int meta=0;
            if(blockObj.has("blockMeta"))
                meta=blockObj.get("blockMeta").getAsInt();
            state=block.getStateFromMeta(meta);
        }
        else
        {
            Block block=ForgeRegistries.BLOCKS.getValue(new ResourceLocation(blocksObject.getAsString()));
            state=block.getStateFromMeta(0);
        }
        blockTypes.add(state);
    }

    public String getBlockName() {
        return blockName;
    }

    public void setBlockName(String blockName) {
        this.blockName = blockName;
    }

    public List<IBlockState> getBlockTypes() {
        return blockTypes;
    }

}
