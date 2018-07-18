package com.nekokittygames.thaumictinkerer.common.multiblocks;

import com.nekokittygames.thaumictinkerer.ThaumicTinkerer;
import com.sun.nio.zipfs.JarFileSystemProvider;
import net.minecraft.block.state.IBlockState;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class MultiblockManager {
    private static List<Multiblock> multiblocks=new ArrayList<>();
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
                    multiblocks.add(multiblock);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }));
    }



    public static Multiblock getMultiblock(IBlockState blockstate)
    {
        for(Multiblock block:multiblocks)
        {
            if(block.getKeyBlock() == blockstate)
            {
                return block;
            }
        }
        return null;
    }
}
