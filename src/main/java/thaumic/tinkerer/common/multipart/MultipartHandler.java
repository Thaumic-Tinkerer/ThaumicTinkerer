
/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the ThaumicTinkerer Mod.
 *
 * ThaumicTinkerer is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * ThaumicTinkerer is a Derivative Work on Thaumcraft 4.
 * Thaumcraft 4 (c) Azanor 2012
 * (http://www.minecraftforum.net/topic/1585216-)
 *
 * File Created @ [29 Oct 2013, 14:23:16 (GMT)]
 */
package thaumic.tinkerer.common.multipart;

import net.minecraft.block.Block;
import thaumcraft.common.config.ConfigBlocks;
import codechicken.microblock.BlockMicroMaterial;
import codechicken.microblock.MicroMaterialRegistry;
import thaumic.tinkerer.common.ThaumicTinkerer;
import thaumic.tinkerer.common.block.quartz.BlockDarkQuartz;

public class MultipartHandler {
    public MultipartHandler() {
        ThaumicTinkerer.log.trace("Loading Multipart Handler class");
        registerMultipart(ConfigBlocks.blockCustomOre, 0);
        registerMultipart(ConfigBlocks.blockCustomOre, 7);
        registerMultipart(ConfigBlocks.blockWoodenDevice, 6);
        registerMultipart(ConfigBlocks.blockWoodenDevice, 7);
        registerMultipartMetadataLine(ConfigBlocks.blockMagicalLog, 1);
        registerMultipartMetadataLine(ConfigBlocks.blockMagicalLeaves, 1);
        registerMultipartMetadataLine(ConfigBlocks.blockCosmeticOpaque, 1);
        registerMultipartMetadataLine(ConfigBlocks.blockCosmeticSolid, 7);
        registerMultipartMetadataLine(ThaumicTinkerer.registry.getFirstBlockFromClass(BlockDarkQuartz.class), 2);
        //new RegisterBlockPart(ConfigBlocks.blockCandle, PartCandle.class, ConfigBlocks.blockCandle.getUnlocalizedName()).init();
        //(new RegisterBlockPart(ConfigBlocks.blockAiry, PartNitor.class, ConfigBlocks.blockAiry.getUnlocalizedName())).init();
    }

    private static void registerMultipartMetadataLine(Block block, int maxMeta) {
        for (int i = 0; i < maxMeta; i++)
            registerMultipart(block, i);
    }

    private static void registerMultipart(Block block, int meta) {
        MicroMaterialRegistry.registerMaterial(new BlockMicroMaterial(block, meta), block.getUnlocalizedName() + (meta == 0 ? "" : "_" + meta));
    }
}