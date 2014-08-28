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
 * File Created @ [4 Sep 2013, 16:53:24 (GMT)]
 */
package thaumic.tinkerer.common.core.handler;

import cpw.mods.fml.common.Loader;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import thaumcraft.common.config.Config;
import thaumic.tinkerer.common.dim.OreClusterGenerator;
import thaumic.tinkerer.common.lib.LibEnchantIDs;
import thaumic.tinkerer.common.lib.LibEnchantNames;

import java.io.File;

public final class ConfigHandler {

    private static Configuration config;

    private static final String CATEGORY_POTIONS = "potions";
    private static final String CATEGORY_ENCHANTMENTS = "enchantments";

    private static final String CATEGORY_KAMI_ITEMS = "item.kami";
    private static final String CATEGORY_KAMI_BLOCKS = "block.kami";
    private static final String CATEGORY_KAMI_GENERAL = "general.kami";

    public static boolean enableKami = false;
    public static boolean enableFlight = true;
    public static boolean useTootlipIndicators = true;
    public static boolean enableSurvivalShareTome = true;
    public static boolean enableEasymodeResearch = false;
    public static boolean enableDebugCommands = false;
    public static boolean useOreDictMetal = true;
    public static boolean repairTConTools = false;

    public static boolean showPlacementMirrorBlocks = true;
    public static int netherDimensionID = -1;
    public static int endDimensionID = 1;
    public static int bedrockDimensionID = 19;

    public static boolean enableCake = true;

    public static boolean enableFire = true;

    public static boolean cropsAllowBonemeal = true;

    public static void loadConfig(File configFile) {
        config = new Configuration(configFile);

        new ConfigCategory(CATEGORY_POTIONS);
        new ConfigCategory(CATEGORY_ENCHANTMENTS);
        new ConfigCategory(CATEGORY_KAMI_ITEMS);
        new ConfigCategory(CATEGORY_KAMI_BLOCKS);
        new ConfigCategory(CATEGORY_KAMI_GENERAL);

        String comment = "These will only be used if KAMI is loaded. (KAMI is a separate download you can find in the Thaumic Tinkerer thread)";
        config.addCustomCategoryComment(CATEGORY_KAMI_ITEMS, comment);
        config.addCustomCategoryComment(CATEGORY_KAMI_BLOCKS, comment);
        config.addCustomCategoryComment(CATEGORY_KAMI_GENERAL, comment);

        config.load();

        Property propEnableKami = config.get(Configuration.CATEGORY_GENERAL, "kami.forceenabled", true);
        propEnableKami.comment = "Set to true to enable all kami stuff (note, either this OR the kami mod file will work)";
        enableKami = Loader.isModLoaded("ThaumicTinkererKami") || propEnableKami.getBoolean(true);

        Property propEnableTooltips = config.get(Configuration.CATEGORY_GENERAL, "tooltipIndicators.enabled", true);
        propEnableTooltips.comment = "Set to false to disable the [TT] tooltips in the thauminomicon.";
        useTootlipIndicators = propEnableTooltips.getBoolean(true);

        Property propEnableSurvivalShareTome = config.get(Configuration.CATEGORY_GENERAL, "shareTome.survival.enabled", true);
        propEnableSurvivalShareTome.comment = "Set to false to disable the crafting recipe for the Tome of Research Sharing.";
        enableSurvivalShareTome = propEnableSurvivalShareTome.getBoolean(true);

        Property propEasymodeResearch = config.get(Configuration.CATEGORY_GENERAL, "research.easymode.enabled", false);
        propEasymodeResearch.comment = "Set to true to enable Easy Research (getting research notes = instant discovery). For those who don't like research. (DEPRECATED: Please use thaumcraft.cfg to edit this now, all this does is alter that)";
        enableEasymodeResearch = propEasymodeResearch.getBoolean(false);
        Config.researchDifficulty = (enableEasymodeResearch) ? -1 : Config.researchDifficulty;

        Property propDebugCommands = config.get(Configuration.CATEGORY_GENERAL, "debugCommands.enabled", false);
        propDebugCommands.comment = "Set to true to enable debugging commands.";
        enableDebugCommands = propDebugCommands.getBoolean(false);

        Property propEnableFlight = config.get(Configuration.CATEGORY_GENERAL, "modFlight.enabled", true);
        propEnableFlight.comment = "Set to true to enable flight in this mod.";
        enableFlight = propEnableFlight.getBoolean(true);

        Property propRepairTCon = config.get(Configuration.CATEGORY_GENERAL, "repairTconTools.enabled", false);
        propRepairTCon.comment = "Can Thaumic Tinkerer repair Tinkers Construct tools.";
        repairTConTools = propRepairTCon.getBoolean(false);

        Property propOreDict = config.get(Configuration.CATEGORY_GENERAL, "oreDictMetal.enabled", true);
        propOreDict.comment = "Set to false to disable usage of ore dictionary metals (tin and copper).";
        useOreDictMetal = propOreDict.getBoolean(true);

        Property propImbuedFire = config.get(Configuration.CATEGORY_GENERAL, "imbuedFire.enabled", true);
        enableFire = propImbuedFire.getBoolean(true);

        Property propImbuedFireCake = config.get(Configuration.CATEGORY_GENERAL, "imbuedFire.cake.enabled", true);
        propImbuedFireCake.comment = "Set to false to disable imbued fire making cake. For those people who don't like cake";
        enableCake = propImbuedFireCake.getBoolean(true);

        Property propCropsAllowBonemeal = config.get(Configuration.CATEGORY_GENERAL, "cropsAllowBonemeal.enabled", false);
        propCropsAllowBonemeal.comment = "Allows crops to be grown using bonemeal. Useful for debug purposes.";
        cropsAllowBonemeal = propCropsAllowBonemeal.getBoolean(false);


        if (enableKami) {
            Property propDimensionID = config.get(CATEGORY_KAMI_GENERAL, "Bedrock dimension id", -19);
            propDimensionID.comment = "Set to the dimension id wished for bedrock dimension, or 0 to disable";
            bedrockDimensionID = propDimensionID.getInt(-19);

            Property oreBlacklist = config.get(CATEGORY_KAMI_GENERAL, "Bedrock dimension ore Blacklist", new String[]{"oreFirestone"});
            oreBlacklist.comment = "These ores will not be spawned in the bedrock dimension";
            OreClusterGenerator.blacklist = oreBlacklist.getStringList();

            Property propOreDensity = config.get(Configuration.CATEGORY_GENERAL, "Bedrock Dimension ore density", 1);
            propOreDensity.comment = "The number of verticle veins of ore per chunk. Default: 1";
            OreClusterGenerator.density = propOreDensity.getInt(1);

            Property propShowPlacementMirrorBlocks = config.get(CATEGORY_KAMI_GENERAL, "placementMirror.blocks.show", true);
            propShowPlacementMirrorBlocks.comment = "Set to false to remove the phantom blocks displayed by the Worldshaper's Seeing Glass.";
            showPlacementMirrorBlocks = propShowPlacementMirrorBlocks.getBoolean(true);

            Property propNetherID = config.get(CATEGORY_KAMI_GENERAL, "dimension.nether.id", -1);
            propNetherID.comment = "The Dimension ID for the Nether, leave at -1 if you don't modify it with another mod/plugin.";
            netherDimensionID = propNetherID.getInt(-1);

            Property propEndID = config.get(CATEGORY_KAMI_GENERAL, "dimension.end.id", 1);
            propEndID.comment = "The Dimension ID for the End, leave at 1 if you don't modify it with another mod/plugin.";
            endDimensionID = propEndID.getInt(1);
        }

        LibEnchantIDs.idAscentBoost = loadEnchant(LibEnchantNames.ASCENT_BOOST, LibEnchantIDs.idAscentBoost);
        LibEnchantIDs.idSlowFall = loadEnchant(LibEnchantNames.SLOW_FALL, LibEnchantIDs.idSlowFall);
        LibEnchantIDs.idAutoSmelt = loadEnchant(LibEnchantNames.AUTO_SMELT, LibEnchantIDs.idAutoSmelt);
        LibEnchantIDs.idDesintegrate = loadEnchant(LibEnchantNames.DESINTEGRATE, LibEnchantIDs.idDesintegrate);
        LibEnchantIDs.idQuickDraw = loadEnchant(LibEnchantNames.QUICK_DRAW, LibEnchantIDs.idQuickDraw);
        LibEnchantIDs.idVampirism = loadEnchant(LibEnchantNames.VAMPIRISM, LibEnchantIDs.idVampirism);

        LibEnchantIDs.focusedStrike = loadEnchant(LibEnchantNames.focusedStrike, LibEnchantIDs.focusedStrike);
        LibEnchantIDs.dispersedStrikes = loadEnchant(LibEnchantNames.dispersedStrikes, LibEnchantIDs.dispersedStrikes);
        LibEnchantIDs.finalStrike = loadEnchant(LibEnchantNames.finalStrike, LibEnchantIDs.finalStrike);
        LibEnchantIDs.valiance = loadEnchant(LibEnchantNames.valiance, LibEnchantIDs.valiance);
        LibEnchantIDs.pounce = loadEnchant(LibEnchantNames.pounce, LibEnchantIDs.pounce);
        LibEnchantIDs.shockwave = loadEnchant(LibEnchantNames.shockwave, LibEnchantIDs.shockwave);
        LibEnchantIDs.shatter = loadEnchant(LibEnchantNames.shatter, LibEnchantIDs.shatter);
        LibEnchantIDs.tunnel = loadEnchant(LibEnchantNames.tunnel, LibEnchantIDs.tunnel);

        config.save();
    }

    private static int loadEnchant(String label, int deafultID) {
        return config.get(CATEGORY_ENCHANTMENTS, "id_enchant." + label, deafultID).getInt(deafultID);
    }
}
