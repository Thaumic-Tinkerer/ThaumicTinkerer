package vazkii.tinkerer.common.dim;

import net.minecraft.world.biome.BiomeGenBase;

public class BiomeGenBedrock extends BiomeGenBase{

	public BiomeGenBedrock(int par1)
	{
		super(par1);
		this.minHeight = 0F;
		this.maxHeight = 1F;
		this.spawnableMonsterList.clear();
		this.spawnableCreatureList.clear();
		this.topBlock = 7;
		this.fillerBlock = 7;
		this.setBiomeName("Bedrock");
	}

}
