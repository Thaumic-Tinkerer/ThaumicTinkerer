package com.nekokittygames.thaumictinkerer.common.multiblocks;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.shader.Shader;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.*;

public class Multiblock implements Iterable<MultiblockLayer>{
    private Map<Integer,MultiblockLayer> layers=new HashMap<>();
    private List<Integer> layerIndices=new ArrayList<>();
    private Map<String, MultiblockBlockType> blocks=new HashMap<>();

    private Map<Integer,MultiblockLayer> output=new HashMap<>();
    private int topY=-100;
    private int bottomY=100;
    private String name;
    private IBlockState keyBlock;
    private ResourceLocation id;

    public ResourceLocation getId() {
        return id;
    }

    public void setId(ResourceLocation id) {
        this.id = id;
    }

    public Map<Integer, MultiblockLayer> getLayers() {
        return layers;
    }

    public List<Integer> getLayerIndices() {
        return layerIndices;
    }

    public Map<String, MultiblockBlockType> getBlocks() {
        return blocks;
    }

    public int getTopY() {
        return topY;
    }

    public int getBottomY() {
        return bottomY;
    }

    public String getName() {
        return name;
    }

    public IBlockState getKeyBlock() {
        return keyBlock;
    }

    public Multiblock(Path filename) throws IOException {
        InputStream in = Files.newInputStream(filename);
        try {
            JsonReader reader=new JsonReader(new InputStreamReader(in,"UTF-8"));
            Gson gson = new Gson();
            // Read from File to String
            JsonObject jsonObject = new JsonObject();
            JsonParser parser = new JsonParser();
            jsonObject=parser.parse(reader).getAsJsonObject();

            // get Multiblock name
            if(!jsonObject.has("name"))
                throw new Exception("Multiblock json malformed, missing name ");
            this.name=jsonObject.get("name").getAsString();

            if(!jsonObject.has("id"))
                throw new Exception("Multiblock json malformed, missing id");
            this.id=new ResourceLocation(jsonObject.get("id").getAsString());

            if(!jsonObject.has("keyBlock"))
                throw new Exception("Multiblock json malformed, missing keyBlock");
            Block keyBlock= ForgeRegistries.BLOCKS.getValue(new ResourceLocation(JsonUtils.getString(jsonObject,"keyBlock")));
            if(keyBlock instanceof BlockAir)
                return;
            int meta=0;
            if(jsonObject.has("keyMeta"))
                meta=JsonUtils.getInt(jsonObject,"keyMeta");
            this.keyBlock=keyBlock.getStateFromMeta(meta);

            if(!jsonObject.has("blocks"))
            {
                throw new Exception("Multiblock json malformed, missing blocks array ");
            }
            JsonElement blocksElement=jsonObject.get("blocks");
            JsonArray blocksArray=blocksElement.getAsJsonArray();
            for(JsonElement blockElement:blocksArray)
            {
                MultiblockBlockType block=new MultiblockBlockType(blockElement.getAsJsonObject());
                blocks.put(block.getBlockName(),block);
            }

            if(!jsonObject.has("layers"))
            {
                throw new Exception("Multiblock json malformed, missing layers array ");
            }
            JsonElement layersElement=jsonObject.get("layers");
            JsonArray layersArray=layersElement.getAsJsonArray();
            for(JsonElement layerElement:layersArray)
            {
                MultiblockLayer layer=new MultiblockLayer(layerElement.getAsJsonObject());
                AddLayer(layer.getyLevel(),layer);
            }
            if(jsonObject.has("output"))
            {
                JsonElement outputsElement=jsonObject.get("output");
                JsonArray outputArray=outputsElement.getAsJsonArray();
                for(JsonElement outputElement:outputArray)
                {
                    MultiblockLayer layer=new MultiblockLayer(outputElement.getAsJsonObject());
                    AddOutputLayer(layer.getyLevel(),layer);
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void AddOutputLayer(int yLevel,MultiblockLayer layer)
    {
        output.put(yLevel,layer);
        if(!layerIndices.contains(yLevel))
            layerIndices.add(yLevel);
        if(yLevel>topY)
            topY=yLevel;
        if(yLevel<bottomY)
            bottomY=yLevel;
    }
    public void AddLayer(int yLevel,MultiblockLayer layer)
    {
        layers.put(yLevel,layer);
        layerIndices.add(yLevel);
        layerIndices.sort(Comparator.reverseOrder());
        if(yLevel>topY)
            topY=yLevel;
        if(yLevel<bottomY)
            bottomY=yLevel;
    }

    @NotNull
    @Override
    public Iterator<MultiblockLayer> iterator() {
        return new Iterator<MultiblockLayer>() {
            private int current=bottomY-1;
            @Override
            public boolean hasNext() {
                return current<topY;
            }

            @Override
            public MultiblockLayer next() {
                while (!layerIndices.contains(++current)) {
                    ;
                }
                return layers.get(current);
            }
        };
    }

    public Iterator<MultiblockLayer> outputIterator()
    {
        return new Iterator<MultiblockLayer>() {
            private int current=bottomY-1;
            @Override
            public boolean hasNext() {
                return current<topY;
            }

            @Override
            public MultiblockLayer next() {
                while (!output.containsKey(++current) && current<topY) {
                    ;
                }
                if(!output.containsKey(current))
                    return null;
                return output.get(current);
            }
        };
    }


}
