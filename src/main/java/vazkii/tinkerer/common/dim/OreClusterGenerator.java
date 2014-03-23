package vazkii.tinkerer.common.dim;

import cpw.mods.fml.common.IWorldGenerator;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.oredict.OreDictionary;
import vazkii.tinkerer.common.block.ModBlocks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class OreClusterGenerator implements IWorldGenerator{

	public static int density;
	public static String[] blacklist = new String[]{"oreFirestone"};
	ArrayList<ItemStack> oreIds=null;

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		if(oreIds==null){
			oreIds=getOreIds();
		}
		if (world.provider instanceof WorldProviderBedrock) {
			for (int k = 0; k < density; k++) {
				int firstBlockXCoord = 16 * chunkX + random.nextInt(16);
				int firstBlockZCoord = 16 * chunkZ + random.nextInt(16);
				ItemStack itemStack = oreIds.get(random.nextInt(oreIds.size()));
				for(int l=0; l<200; l++){
					int firstBlockYCoord = random.nextInt(245)+6;
					WorldGenMinable mineable = new WorldGenMinable(Block.getBlockFromItem(itemStack.getItem()),itemStack.getItemDamage(), random.nextInt(20), ModBlocks.bedrock);
					mineable.generate(world, random, firstBlockXCoord, firstBlockYCoord, firstBlockZCoord);
				}
			}
		}
	}

	private ArrayList<ItemStack> getOreIds(){

		Random r = new Random();
		ArrayList<ItemStack> result = new ArrayList<ItemStack>();
		for(String s: OreDictionary.getOreNames()){
			if(s.contains("ore") && !Arrays.asList(blacklist).contains(s) && !OreDictionary.getOres(s).isEmpty() && OreDictionary.getOres(s).get(0).getItem() instanceof ItemBlock){
				result.add((OreDictionary.getOres(s).get(0)));
			}
		}
		return result;
	}

}
