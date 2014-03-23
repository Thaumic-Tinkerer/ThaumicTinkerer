package vazkii.tinkerer.common.dim;

import net.minecraft.block.Block;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import net.minecraft.world.gen.FlatLayerInfo;
import net.minecraft.world.gen.feature.WorldGenLakes;
import net.minecraft.world.gen.structure.MapGenStronghold;
import net.minecraft.world.gen.structure.MapGenStructure;
import vazkii.tinkerer.common.block.ModBlocks;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class ChunkProviderBedrock implements IChunkProvider {

	//Basic on ChunkProviderFlat code
	private World worldObj;
	private Random random;
	private final byte[] cachedBlockIDs = new byte[256];
	private final byte[] cachedBlockMetadata = new byte[256];
	private final List structureGenerators = new ArrayList();
	private WorldGenLakes waterLakeGenerator;
	private WorldGenLakes lavaLakeGenerator;

	private OreClusterGenerator generator;

	public ChunkProviderBedrock(World par1World, long par2, boolean par4) {
		this.worldObj = par1World;
		this.random = new Random(par2);
		generator = new OreClusterGenerator();

		FlatLayerInfo flatlayerinfo = new FlatLayerInfo(256, ModBlocks.bedrock);

		for (int j = flatlayerinfo.getMinY(); j < flatlayerinfo.getMinY() + flatlayerinfo.getLayerCount(); ++j) {
			this.cachedBlockIDs[j] = (byte) (Block.getIdFromBlock(flatlayerinfo.func_151536_b()) & 255);
			this.cachedBlockMetadata[j] = (byte) flatlayerinfo.getFillBlockMeta();
		}

	}

	/**
	 * loads or generates the chunk at the chunk location specified
	 */
	public Chunk loadChunk(int par1, int par2) {
		return this.provideChunk(par1, par2);
	}

	/**
	 * Will return back a chunk, if it doesn't exist and its not a MP client it will generates all the blocks for the
	 * specified chunk from the map seed and chunk seed
	 */
	public Chunk provideChunk(int par1, int par2) {
		Chunk chunk = new Chunk(this.worldObj, par1, par2);

		for (int k = 0; k < this.cachedBlockIDs.length; ++k) {
			int l = k >> 4;
			ExtendedBlockStorage extendedblockstorage = chunk.getBlockStorageArray()[l];

			if (extendedblockstorage == null) {
				extendedblockstorage = new ExtendedBlockStorage(k, !this.worldObj.provider.hasNoSky);
				chunk.getBlockStorageArray()[l] = extendedblockstorage;
			}

			for (int i1 = 0; i1 < 16; ++i1) {
				for (int j1 = 0; j1 < 16; ++j1) {
					extendedblockstorage.func_150818_a(i1, k & 15, j1, Block.getBlockById(this.cachedBlockIDs[k] & 255));
					extendedblockstorage.setExtBlockMetadata(i1, k & 15, j1, this.cachedBlockMetadata[k]);
				}
			}
		}

		chunk.generateSkylightMap();
		BiomeGenBase[] abiomegenbase = this.worldObj.getWorldChunkManager().loadBlockGeneratorData((BiomeGenBase[]) null, par1 * 16, par2 * 16, 16, 16);
		byte[] abyte = chunk.getBiomeArray();

		for (int k1 = 0; k1 < abyte.length; ++k1) {
			abyte[k1] = (byte) abiomegenbase[k1].biomeID;
		}

		Iterator iterator = this.structureGenerators.iterator();

		while (iterator.hasNext()) {
			MapGenStructure mapgenstructure = (MapGenStructure) iterator.next();
            mapgenstructure.func_151539_a(this, this.worldObj, par1, par2, new Block[]{
                    Blocks.air, Blocks.air, Blocks.air, Blocks.air, Blocks.air, Blocks.air, Blocks.air, Blocks.air
            });
		}

		chunk.generateSkylightMap();
		return chunk;
	}

	/**
	 * Checks to see if a chunk exists at x, y
	 */
	public boolean chunkExists(int par1, int par2) {
		return true;
	}

	/**
	 * Populates chunk with ores etc etc
	 */
	public void populate(IChunkProvider par1IChunkProvider, int par2, int par3) {
		int k = par2 * 16;
		int l = par3 * 16;
		BiomeGenBase biomegenbase = this.worldObj.getBiomeGenForCoords(k + 16, l + 16);
		boolean flag = false;
		this.random.setSeed(this.worldObj.getSeed());
		long i1 = this.random.nextLong() / 2L * 2L + 1L;
		long j1 = this.random.nextLong() / 2L * 2L + 1L;
		this.random.setSeed((long) par2 * i1 + (long) par3 * j1 ^ this.worldObj.getSeed());

		int k1;
		int l1;
		int i2;

		if (this.generator != null) {
			l1 = k + this.random.nextInt(16) + 8;
			k1 = this.random.nextInt(128);
			i2 = l + this.random.nextInt(16) + 8;
			this.generator.generate(this.random, par2, par3, this.worldObj, this, this);
		}

	}

	/**
	 * Two modes of operation: if passed true, save all Chunks in one go.  If passed false, save up to two chunks.
	 * Return true if all chunks have been saved.
	 */
	public boolean saveChunks(boolean par1, IProgressUpdate par2IProgressUpdate) {
		return true;
	}

	/**
	 * Save extra data not associated with any Chunk.  Not saved during autosave, only during world unload.  Currently
	 * unimplemented.
	 */
	public void saveExtraData() {
	}

	/**
	 * Unloads chunks that are marked to be unloaded. This is not guaranteed to unload every such chunk.
	 */
	public boolean unloadQueuedChunks() {
		return false;
	}

	/**
	 * Returns if the IChunkProvider supports saving.
	 */
	public boolean canSave() {
		return true;
	}

	/**
	 * Converts the instance data to a readable string.
	 */
	public String makeString() {
		return "Bedrock";
	}

	/**
	 * Returns a list of creatures of the specified type that can spawn at the given location.
	 */
	public List getPossibleCreatures(EnumCreatureType par1EnumCreatureType, int par2, int par3, int par4) {
		BiomeGenBase biomegenbase = this.worldObj.getBiomeGenForCoords(par2, par4);
		return biomegenbase == null ? null : biomegenbase.getSpawnableList(par1EnumCreatureType);
	}

    @Override
    public ChunkPosition func_147416_a(World var1, String var2, int var3, int var4, int var5) {
		if ("Stronghold".equals(var2)) {
			Iterator iterator = this.structureGenerators.iterator();

			while (iterator.hasNext()) {
				MapGenStructure mapgenstructure = (MapGenStructure) iterator.next();

				if (mapgenstructure instanceof MapGenStronghold) {
                    return mapgenstructure.func_151545_a(var1, var3, var4, var5);
				}
			}
		}

		return null;
	}

	public int getLoadedChunkCount() {
		return 0;
	}

	public void recreateStructures(int par1, int par2) {
		Iterator iterator = this.structureGenerators.iterator();

		while (iterator.hasNext()) {
			MapGenStructure mapgenstructure = (MapGenStructure) iterator.next();
            mapgenstructure.func_151539_a(this, this.worldObj, par1, par2, new Block[]{
                    Blocks.air, Blocks.air, Blocks.air, Blocks.air, Blocks.air, Blocks.air, Blocks.air, Blocks.air
            });
		}
	}

}