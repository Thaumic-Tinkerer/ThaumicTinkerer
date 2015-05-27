package thaumic.tinkerer.common.dim;

import cpw.mods.fml.common.IWorldGenerator;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;

import java.util.Random;

public class OreClusterGenerator implements IWorldGenerator {

    public static int density;
    public static String[] blacklist = new String[]{"oreFirestone"};

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {

        if (world.provider instanceof WorldProviderBedrock) {
            for (int k = 0; k < density; k++) {
                int firstBlockXCoord = 16 * chunkX + random.nextInt(16);
                int firstBlockZCoord = 16 * chunkZ + random.nextInt(16);
                ItemStack itemStack = EnumOreFrequency.getRandomOre(random);
                for (int l = 0; l < 200; l++) {
                    int firstBlockYCoord = random.nextInt(245) + 6;
                    WorldGenMinable mineable = new WorldGenMinable(Block.getBlockFromItem(itemStack.getItem()), itemStack.getItemDamage(), random.nextInt(20), Blocks.bedrock);
                    mineable.generate(world, random, firstBlockXCoord, firstBlockYCoord, firstBlockZCoord);
                }
            }
        }
    }

}
