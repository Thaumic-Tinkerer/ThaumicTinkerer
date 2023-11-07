package com.nekokittygames.thaumictinkerer.common.dim;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.MapGenBase;
import net.minecraft.world.gen.MapGenCaves;
import net.minecraft.world.gen.MapGenRavine;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.MapGenMineshaft;
import net.minecraft.world.gen.structure.MapGenStronghold;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.gen.structure.MapGenVillage;
import net.minecraftforge.event.terraingen.InitMapGenEvent;
import net.minecraftforge.event.terraingen.TerrainGen;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.BooleanSupplier;

public class ChunkGeneratorBedrock implements IChunkGenerator {
    private static final IBlockState BEDROCK = Blocks.BEDROCK.getDefaultState();

    private final List<Tuple<? extends MapGenBase, BooleanSupplier>> mapGen = new ArrayList<>();

    private final List<Tuple<? extends WorldGenerator, BooleanSupplier>> worldGen = new ArrayList<>();

    private final World world;

    private final long seed;

    private Random rng;

    private OreClusterGenerator generator;

    public ChunkGeneratorBedrock(World world, long seed) {
        addMapGen(TerrainGen.getModdedMapGen((MapGenBase)new MapGenCaves(), InitMapGenEvent.EventType.CAVE), () -> false);
        addMapGen(TerrainGen.getModdedMapGen((MapGenBase)new MapGenRavine(), InitMapGenEvent.EventType.RAVINE), () -> false);
        addMapGen(TerrainGen.getModdedMapGen((MapGenBase)new MapGenMineshaft(), InitMapGenEvent.EventType.MINESHAFT), () -> false);
        addMapGen(TerrainGen.getModdedMapGen((MapGenBase)new MapGenStronghold(), InitMapGenEvent.EventType.STRONGHOLD), () -> false);
        addMapGen(TerrainGen.getModdedMapGen((MapGenBase)new MapGenVillage(), InitMapGenEvent.EventType.VILLAGE), () -> false);
        this.world = world;
        this.seed = seed;
        this.generator = new OreClusterGenerator();
    }

    public void setBlocksInChunk(int chunkX, int chunkZ, ChunkPrimer primer) {
        int height = 256;
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                for (int y = 0; y < height; y++)
                    primer.setBlockState(x, y, z, BEDROCK);
            }
        }
    }

    public Chunk generateChunk(int chunkX, int chunkZ) {
        this.rng = new Random(chunkX * 341873128712L + chunkZ * 132897987541L + this.seed);
        ChunkPrimer primer = new ChunkPrimer();
        setBlocksInChunk(chunkX, chunkZ, primer);
        Biome[] biomes = this.world.getBiomeProvider().getBiomesForGeneration(null, chunkX << 4, chunkZ << 4, 16, 16);
        for (int xD = 0; xD < 16; xD++) {
            int x = chunkX << 4 | xD;
            for (int zD = 0; zD < 16; zD++) {
                int z = chunkZ << 4 | zD;
                biomes[xD << 4 | zD].genTerrainBlocks(this.world, this.rng, primer, x, z, 0.0D);
            }
        }
        for (Tuple<? extends MapGenBase, BooleanSupplier> gen : this.mapGen) {
            if (((BooleanSupplier)gen.getSecond()).getAsBoolean())
                ((MapGenBase)gen.getFirst()).generate(this.world, chunkX, chunkZ, primer);
        }
        Chunk chunk = new Chunk(this.world, primer, chunkX, chunkZ);
        chunk.generateSkylightMap();
        return chunk;
    }

    @Override
    public void populate(int par2, int par3) {
        this.rng.setSeed(this.world.getSeed());
        long i1 = this.rng.nextLong() / 2L * 2L + 1L;
        long j1 = this.rng.nextLong() / 2L * 2L + 1L;
        this.rng.setSeed((long) par2 * i1 + (long) par3 * j1 ^ this.world.getSeed());

        if (this.generator != null) {
            this.generator.generate(this.rng, par2, par3, this.world, this, world.getChunkProvider());
        }

    }

    public boolean generateStructures
            (Chunk chunk, int chunkX, int chunkZ) {
        return false;
    }

    @Override
    public List<Biome.SpawnListEntry> getPossibleCreatures(EnumCreatureType enumCreatureType, BlockPos blockPos) {
        return null;
    }

    @Nullable
    @Override
    public BlockPos getNearestStructurePos(World world, String s, BlockPos blockPos, boolean b) {
        return null;
    }

    public void recreateStructures(Chunk chunk, int chunkX, int chunkZ) {
        for (Tuple<? extends MapGenBase, BooleanSupplier> t : this.mapGen) {
            if (t.getFirst() instanceof MapGenStructure)
                ((MapGenBase)t.getFirst()).generate(this.world, chunkX, chunkZ, null);
        }
    }

    @Override
    public boolean isInsideStructure(World world, String s, BlockPos blockPos) {
        return false;
    }


    private void addMapGen(MapGenBase gen, BooleanSupplier active) {
        Tuple<MapGenBase, BooleanSupplier> t = new Tuple(gen, active);
        this.mapGen.add(t);
    }
}