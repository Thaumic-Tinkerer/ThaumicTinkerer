package com.nekokittygames.thaumictinkerer.common.multiblocks;


import com.nekokittygames.thaumictinkerer.ThaumicTinkerer;
import com.nekokittygames.thaumictinkerer.common.helper.Mat2f;
import com.nekokittygames.thaumictinkerer.common.helper.TTVec2f;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Stream;

public class MultiblockManager {
    private static Map<ResourceLocation,Multiblock> multiblocks=new HashMap<>();
    public static void initMultiblocks() throws URISyntaxException, IOException {
    // todo: add nullcheck
        URI uri;
        try
        {
            uri = MultiblockManager.class.getResource("/assets/thaumictinkerer/multiblocks").toURI();
        }
        catch(Exception e)
        {
            ThaumicTinkerer.logger.error("Unable to load multiblocks",e);
            return;
        }

        Path myPath;
        if (uri.getScheme().equals("jar")) {
            FileSystem fileSystem = FileSystems.newFileSystem(uri, Collections.<String, Object>emptyMap());
            myPath = fileSystem.getPath("/assets/thaumictinkerer/multiblocks");
        } else {
            myPath = Paths.get(uri);
        }
        Stream<Path> walk = Files.walk(myPath);
        walk.forEach((path -> {
            if(path.toString().endsWith("json"))
            {
                Multiblock multiblock= null;
                try {
                    multiblock = new Multiblock(path);
                    multiblocks.put(multiblock.getId(),multiblock);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }));

        Mat2f tmp;
        FACING_ROTATIONS.put(EnumFacing.NORTH, (Mat2f) new Mat2f().setIdentity());
        tmp=new Mat2f();
        tmp.m00=0; tmp.m01=1;
        tmp.m10=-1; tmp.m11=0;
        FACING_ROTATIONS.put(EnumFacing.EAST,tmp);
        tmp=new Mat2f();
        tmp.m00=-1; tmp.m01=0;
        tmp.m10=0; tmp.m11=-1;
        FACING_ROTATIONS.put(EnumFacing.SOUTH,tmp);
        tmp=new Mat2f();
        tmp.m00=0; tmp.m01=-1;
        tmp.m10=1; tmp.m11=0;
        FACING_ROTATIONS.put(EnumFacing.WEST,tmp);
    }

    public static Map<EnumFacing,Mat2f> FACING_ROTATIONS=new HashMap<>();

    public static boolean checkMultiblock(World world, BlockPos keyBlock,ResourceLocation multiblock)
    {
        for(EnumFacing facing:EnumFacing.HORIZONTALS)
        {
            if(checkMultiblock(world, keyBlock, multiblock,facing))
                return true;
        }
        return false;
    }

    public static boolean checkMultiblockCombined(World world, BlockPos keyBlock,ResourceLocation multiblock)
    {
        for(EnumFacing facing:EnumFacing.HORIZONTALS)
        {
            if(checkOutputMultiblock(world, keyBlock, multiblock,facing))
                return true;
        }
        return false;
    }
    public static EnumFacing checkMultiblockFacing(World world, BlockPos keyBlock,ResourceLocation multiblock)
    {
        for(EnumFacing facing:EnumFacing.HORIZONTALS)
        {
            if(checkMultiblock(world, keyBlock, multiblock,facing))
                return facing;
        }
        return EnumFacing.UP;

    }



    public static boolean checkOutputMultiblock(World world, BlockPos keyBlock,ResourceLocation multiblockLocation,EnumFacing facing)
    {
        Multiblock multiblock=getMultiblock(multiblockLocation);
        if(multiblock==null)
            return false;
        Mat2f matrix=FACING_ROTATIONS.get(facing);
        boolean complete=true;
        for (Iterator<MultiblockLayer> it = multiblock.combinedIterator(); it.hasNext(); ) {
            MultiblockLayer layer = it.next();
            for(MultiblockBlock block:layer)
            {
                TTVec2f tmpPos=new TTVec2f(block.getxOffset(),block.getzOffset());
                tmpPos=mul(matrix,tmpPos);
                BlockPos posToCheck=keyBlock.add(new BlockPos(tmpPos.x,layer.getyLevel(),tmpPos.y));
                String blockType=block.getBlockName();
                MultiblockBlockType mBlockType=multiblock.getBlocks().get(blockType);
                boolean blockFound=false;
                if(blockType.equalsIgnoreCase("minecraft:air"))
                {
                    blockFound=world.isAirBlock(posToCheck);
                }
                else
                {
                    for(IBlockState blockState:mBlockType.getBlockTypes())
                    {
                        if(block.getExtraMeta()!=-1)
                            blockState=blockState.getBlock().getStateFromMeta(block.getExtraMeta());

                        if(blockState==world.getBlockState(posToCheck))
                        {
                            blockFound=true;
                        }
                    }}
                if(!blockFound)
                    return false;


            }
        }

        return true;
    }
    public static void outputMultiblock(World world,BlockPos keyBlock,ResourceLocation multiblockLocation,EnumFacing facing) throws Exception {
        Multiblock multiblock=getMultiblock(multiblockLocation);
        if(multiblock==null)
            return;
        Mat2f matrix=FACING_ROTATIONS.get(facing);
        boolean complete=true;
        for (Iterator<MultiblockLayer> it = multiblock.outputIterator(); it.hasNext(); ) {
            MultiblockLayer layer = it.next();
            if(layer==null)
                continue;
            for (MultiblockBlock block : layer) {
                TTVec2f tmpPos=new TTVec2f(block.getxOffset(),block.getzOffset());
                tmpPos=mul(matrix,tmpPos);
                BlockPos posToCheck=keyBlock.add(new BlockPos(tmpPos.x,layer.getyLevel(),tmpPos.y));
                String blockType=block.getBlockName();
                if(blockType.equalsIgnoreCase("minecraft:air"))
                {
                    world.setBlockToAir(posToCheck);
                    continue;
                }
                MultiblockBlockType mBlockType=multiblock.getBlocks().get(blockType);
                boolean blockFound=false;
                IBlockState state;
                if(mBlockType.getBlockTypes().size()<1)
                    throw new Exception("Invalid Output");
                state=mBlockType.getBlockTypes().get(0);
                if(block.getExtraMeta()!=-1)
                    state=state.getBlock().getStateFromMeta(block.getExtraMeta());
                world.setBlockState(posToCheck,state,3);
            }
        }
    }
    public static boolean checkMultiblock(World world, BlockPos keyBlock, ResourceLocation multiblockLocation, EnumFacing facing)
    {
        Multiblock multiblock=getMultiblock(multiblockLocation);
        if(multiblock==null)
            return false;
        Mat2f matrix=FACING_ROTATIONS.get(facing);
        boolean complete=true;
        for(MultiblockLayer layer:multiblock)
        {
            for(MultiblockBlock block:layer)
            {
                TTVec2f tmpPos=new TTVec2f(block.getxOffset(),block.getzOffset());
                tmpPos=mul(matrix,tmpPos);
                BlockPos posToCheck=keyBlock.add(new BlockPos(tmpPos.x,layer.getyLevel(),tmpPos.y));
                String blockType=block.getBlockName();
                MultiblockBlockType mBlockType=multiblock.getBlocks().get(blockType);
                boolean blockFound=false;
                if(blockType.equalsIgnoreCase("minecraft:air"))
                {
                    blockFound=world.isAirBlock(posToCheck);
                }
                else
                {
                for(IBlockState blockState:mBlockType.getBlockTypes())
                {
                    if(block.getExtraMeta()!=-1)
                        blockState=blockState.getBlock().getStateFromMeta(block.getExtraMeta());

                    if(blockState==world.getBlockState(posToCheck))
                    {
                       blockFound=true;
                    }
                }}
                if(!blockFound)
                    return false;


            }
        }

        return true;

    }


    public static TTVec2f mul(Mat2f matrix,TTVec2f vec)
    {
        TTVec2f rotatedPos=new TTVec2f((matrix.m00*vec.x)+(matrix.m01*vec.y),(matrix.m10*vec.x)+(matrix.m11*vec.y));
        return rotatedPos;
    }

    public static Multiblock getMultiblock(ResourceLocation location)
    {
        if(multiblocks.containsKey(location))
            return multiblocks.get(location);
        return null;
    }

    public static Multiblock getMultiblock(IBlockState blockstate)
    {
        for(Multiblock block:multiblocks.values())
        {
            if(block.getKeyBlock() == blockstate)
            {
                return block;
            }
        }
        return null;
    }

    public static void clearMultiblocks() {
        multiblocks.clear();
    }
}
