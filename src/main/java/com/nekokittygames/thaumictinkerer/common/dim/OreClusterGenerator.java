package com.nekokittygames.thaumictinkerer.common.dim;

import net.minecraft.block.Block;
import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

public class OreClusterGenerator implements IWorldGenerator {

    public static int density = 1;
    public static String[] blacklist = new String[]{"oreFirestone"};

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {

        if (world.provider instanceof BedrockWorldProvider) {
            for (int k = 0; k < density; k++) {
                int firstBlockXCoord = 16 * chunkX + random.nextInt(16);
                int firstBlockZCoord = 16 * chunkZ + random.nextInt(16);
                ItemStack itemStack = EnumOreFrequency.getRandomOre(random);
                for (int l = 0; l < 200; l++) {
                    int firstBlockYCoord = random.nextInt(245) + 6;
                    BlockPos pos = new BlockPos(firstBlockXCoord, firstBlockYCoord, firstBlockZCoord);

//                    if (state.getBlock().equals(Block.getBlockFromItem(itemStack.getItem()))) {
//                        WorldGenMinable mineable = new WorldGenMinable(state, random.nextInt(20), BlockMatcher.forBlock(Blocks.BEDROCK));
                        WorldGenMinable mineable = new WorldGenMinable(Block.getBlockFromItem(itemStack.getItem()).getStateFromMeta(itemStack.getItemDamage()), random.nextInt(20), BlockMatcher.forBlock(Blocks.BEDROCK));
                        mineable.generate(world, random, pos);
//                    }
                }
            }
        }
    }
}