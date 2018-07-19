package com.nekokittygames.thaumictinkerer.common.multiblocks;


import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.lwjgl.util.vector.Matrix;
import org.lwjgl.util.vector.Matrix2f;
import org.lwjgl.util.vector.Vector2f;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class MultiblockManager {
    private static Map<ResourceLocation,Multiblock> multiblocks=new HashMap<>();
    public static void initMultiblocks() throws URISyntaxException, IOException {
        URI uri = MultiblockManager.class.getResource("/assets/thaumictinkerer/multiblocks").toURI();
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

        Matrix2f tmp;
        FACING_ROTATIONS.put(EnumFacing.NORTH, (Matrix2f) new Matrix2f().setIdentity());
        tmp=new Matrix2f();
        tmp.m00=0; tmp.m01=1;
        tmp.m10=-1; tmp.m11=0;
        FACING_ROTATIONS.put(EnumFacing.EAST,tmp);
        tmp=new Matrix2f();
        tmp.m00=-1; tmp.m01=0;
        tmp.m10=0; tmp.m11=-1;
        FACING_ROTATIONS.put(EnumFacing.SOUTH,tmp);
        tmp=new Matrix2f();
        tmp.m00=0; tmp.m01=-1;
        tmp.m10=1; tmp.m11=0;
        FACING_ROTATIONS.put(EnumFacing.WEST,tmp);
    }

    public static Map<EnumFacing,Matrix2f> FACING_ROTATIONS=new HashMap<>();

    public static boolean checkMultiblock(World world, BlockPos keyBlock,ResourceLocation multiblock)
    {
        for(EnumFacing facing:EnumFacing.HORIZONTALS)
        {
            if(checkMultiblock(world, keyBlock, multiblock,facing))
                return true;
        }
        return false;
    }
    public static boolean checkMultiblock(World world, BlockPos keyBlock, ResourceLocation multiblockLocation, EnumFacing facing)
    {
        Multiblock multiblock=getMultiblock(multiblockLocation);
        if(multiblock==null)
            return false;
        Matrix2f matrix=FACING_ROTATIONS.get(facing);
        boolean complete=true;
        for(MultiblockLayer layer:multiblock)
        {
            for(MultiblockBlock block:layer)
            {
                Vector2f tmpPos=new Vector2f(block.getxOffset(),block.getzOffset());
                tmpPos=mul(matrix,tmpPos);
                BlockPos posToCheck=keyBlock.add(new BlockPos(tmpPos.x,layer.getyLevel(),tmpPos.y));
                String blockType=block.getBlockName();
                MultiblockBlockType mBlockType=multiblock.getBlocks().get(blockType);
                boolean blockFound=false;
                for(IBlockState blockState:mBlockType.getBlockTypes())
                {
                    if(blockState==world.getBlockState(posToCheck))
                    {
                       blockFound=true;
                    }
                }
                if(!blockFound)
                    return false;


            }
        }

        return true;

    }


    public static Vector2f mul(Matrix2f matrix,Vector2f vec)
    {
        Vector2f rotatedPos=new Vector2f();
        rotatedPos.x=(matrix.m00*vec.x)+(matrix.m01*vec.y);
        rotatedPos.y=(matrix.m10*vec.x)+(matrix.m11*vec.y);
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
}
