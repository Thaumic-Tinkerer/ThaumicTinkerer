package thaumic.tinkerer.common.core.helper;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameData;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.commons.lang3.text.WordUtils;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.entities.ItemSpawnerEgg;
import thaumcraft.common.items.ItemWispEssence;
import thaumic.tinkerer.common.ThaumicTinkerer;
import thaumic.tinkerer.common.block.quartz.BlockDarkQuartz;
import thaumic.tinkerer.common.item.ItemBrightNitor;
import thaumic.tinkerer.common.item.ItemInfusedGrain;

import java.util.*;

/**
 * Created by pixlepix on 8/7/14.
 */
public class AspectCropLootManager {

    private static HashMap<Aspect, HashMap<ItemStack, Integer>> lootMap = new HashMap<Aspect, HashMap<ItemStack, Integer>>();

    public static ItemStack getLootForAspect(Aspect aspect) {
        HashMap<ItemStack, Integer> aspectHashmap = lootMap.get(aspect);
        //Find total value of the possible ItemStacks for the aspect
        int sum = 0;

        for (Integer i : aspectHashmap.values()) {
            sum += i;
        }

        Random rand = new Random();
        int randInt = rand.nextInt(sum);
        for (Map.Entry<ItemStack, Integer> pair : aspectHashmap.entrySet()) {
            randInt -= pair.getValue();
            if (randInt <= 0) {
                return pair.getKey().copy();
            }
        }
        return null;
    }

    public static void addAspectLoot(Aspect aspect, ItemStack stack) {
        addAspectLoot(aspect, stack, 1);
    }

    public static void addAspectLoot(Aspect aspect, String target) {

        addAspectLoot(aspect, target, 1);
        for (String ore : OreDictionary.getOreNames()) {
            if (ore.contains(WordUtils.capitalizeFully(target)) || ore.contains(target)) {
                for (ItemStack stack : OreDictionary.getOres(ore)) {
                    addAspectLoot(aspect, stack);
                }
            }
        }
    }

    public static void addAspectLoot(Aspect aspect, String target, int count) {
        for (String ore : OreDictionary.getOreNames()) {
            if (ore.contains(WordUtils.capitalizeFully(target)) || ore.contains(target)) {
                for (ItemStack stack : OreDictionary.getOres(ore)) {
                    ItemStack newStack = stack.copy();
                    newStack.stackSize = count;
                    addAspectLoot(aspect, newStack);
                }
            }
        }
    }


    public static void addAspectLoot(Aspect aspect, ItemStack stack, int power) {
        lootMap.get(aspect).put(stack, power);
    }

    public static void populateLootMap() {
        for (Aspect a : Aspect.aspects.values()) {
            lootMap.put(a, new HashMap<ItemStack, Integer>());
        }
        addAspectLoot(Aspect.AIR, new ItemStack(ThaumicTinkerer.registry.getFirstItemFromClass(ItemInfusedGrain.class), 1, ItemInfusedGrain.getMetaForAspect(Aspect.AIR)));
        addAspectLoot(Aspect.FIRE, new ItemStack(ThaumicTinkerer.registry.getFirstItemFromClass(ItemInfusedGrain.class), 1, ItemInfusedGrain.getMetaForAspect(Aspect.FIRE)));
        addAspectLoot(Aspect.EARTH, new ItemStack(ThaumicTinkerer.registry.getFirstItemFromClass(ItemInfusedGrain.class), 1, ItemInfusedGrain.getMetaForAspect(Aspect.EARTH)));
        addAspectLoot(Aspect.WATER, new ItemStack(ThaumicTinkerer.registry.getFirstItemFromClass(ItemInfusedGrain.class), 1, ItemInfusedGrain.getMetaForAspect(Aspect.WATER)));

        addAspectLoot(Aspect.ORDER, new ItemStack(Blocks.glass, 64));
        addAspectLoot(Aspect.ENTROPY, new ItemStack(Blocks.sand, 64));

        addAspectLoot(Aspect.ELDRITCH, new ItemStack(Items.ender_pearl, 4), 10);
        addAspectLoot(Aspect.ELDRITCH, new ItemStack(Items.ender_eye, 4), 5);
        addAspectLoot(Aspect.ELDRITCH, "bucketEnder");


        addAspectLoot(Aspect.TREE, "log");

        for (Aspect tag : Aspect.aspects.values()) {
            ItemStack i = new ItemStack(ConfigItems.itemWispEssence, 1, 0);
            ((ItemWispEssence) ConfigItems.itemWispEssence).setAspects(i, new AspectList().add(tag, 2));
            addAspectLoot(Aspect.AURA, i);
        }

        for (int i = 0; i < 24; i++) {
            addAspectLoot(Aspect.BEAST, new ItemStack(Items.spawn_egg, 1, i));
        }

        addAspectLoot(Aspect.MIND, new ItemStack(Items.paper, 64), 15);
        addAspectLoot(Aspect.MIND, new ItemStack(Items.book, 32), 10);
        addAspectLoot(Aspect.MIND, new ItemStack(Blocks.bookshelf, 16), 5);
        for (Enchantment enchant : Enchantment.enchantmentsBookList) {
            addAspectLoot(Aspect.MIND, Items.enchanted_book.getEnchantedItemStack(new EnchantmentData(enchant, 1)), 1);
        }

        addAspectLoot(Aspect.FLESH, new ItemStack(ConfigItems.itemResource, 16, 5), 4);
        addAspectLoot(Aspect.FLESH, new ItemStack(ConfigItems.itemResource, 16, 5), 1);

        addAspectLoot(Aspect.UNDEAD, new ItemStack(Items.rotten_flesh, 32));

        addAspectLoot(Aspect.CRAFT, new ItemStack(ThaumicTinkerer.registry.getFirstBlockFromClass(BlockDarkQuartz.class), 32));
        addAspectLoot(Aspect.CRAFT, new ItemStack(ConfigBlocks.blockStoneDevice, 16));

        addAspectLoot(Aspect.HUNGER, new ItemStack(Items.nether_wart, 16));

        addAspectLoot(Aspect.COLD, new ItemStack(Items.snowball, 16));
        addAspectLoot(Aspect.COLD, "rodBlizz");

        addAspectLoot(Aspect.PLANT, "sapling");

        for (int i = 0; i < 12; i++) {
            addAspectLoot(Aspect.MAN, new ItemStack(ConfigItems.itemGolemCore, i));
        }

        addAspectLoot(Aspect.ARMOR, new ItemStack(Items.diamond_boots));
        addAspectLoot(Aspect.ARMOR, new ItemStack(Items.diamond_leggings));
        addAspectLoot(Aspect.ARMOR, new ItemStack(Items.diamond_chestplate));
        addAspectLoot(Aspect.ARMOR, new ItemStack(Items.diamond_helmet));

        addAspectLoot(Aspect.MINE, new ItemStack(ConfigItems.itemPickThaumium));
        addAspectLoot(Aspect.HARVEST, new ItemStack(ConfigItems.itemHoeThaumium));
        addAspectLoot(Aspect.WEAPON, new ItemStack(ConfigItems.itemSwordThaumium));
        addAspectLoot(Aspect.TOOL, new ItemStack(ConfigItems.itemShovelThaumium));
        addAspectLoot(Aspect.TOOL, new ItemStack(ConfigItems.itemAxeThaumium));
        addAspectLoot(Aspect.TRAVEL, new ItemStack(ConfigBlocks.blockCosmeticSolid, 8, 7));

        addAspectLoot(Aspect.SLIME, new ItemStack(Items.slime_ball, 16));
        addAspectLoot(Aspect.SLIME, "slime");

        addAspectLoot(Aspect.GREED, new ItemStack(Items.gold_ingot, 4));

        addAspectLoot(Aspect.LIGHT, new ItemStack(Items.glowstone_dust, 16), 5);
        addAspectLoot(Aspect.LIGHT, new ItemStack(ConfigItems.itemResource, 4, 1));
        addAspectLoot(Aspect.LIGHT, new ItemStack(ThaumicTinkerer.registry.getFirstItemFromClass(ItemBrightNitor.class)));

        addAspectLoot(Aspect.MECHANISM, new ItemStack(Blocks.piston, 8));
        addAspectLoot(Aspect.MECHANISM, "gear");

        addAspectLoot(Aspect.CROP, new ItemStack(Items.wheat, 32));

        addAspectLoot(Aspect.METAL, new ItemStack(Items.iron_ingot, 4), 100);
        addAspectLoot(Aspect.METAL, "iron");


        addAspectLoot(Aspect.DEATH, new ItemStack(Items.bone, 32));

        addAspectLoot(Aspect.MOTION, new ItemStack(Blocks.rail), 10);

        addAspectLoot(Aspect.MOTION, new ItemStack(Blocks.activator_rail));


        addAspectLoot(Aspect.CLOTH, new ItemStack(Blocks.wool, 16), 30);
        addAspectLoot(Aspect.CLOTH, new ItemStack(Items.string, 15), 10);
        for (int i = 0; i < 16; i++) {
            addAspectLoot(Aspect.CLOTH, new ItemStack(Blocks.wool, 4, i));
        }

        addAspectLoot(Aspect.EXCHANGE, "ingotCopper", 4);
        addAspectLoot(Aspect.EXCHANGE, new ItemStack(ConfigBlocks.blockCustomOre, 4));

        addAspectLoot(Aspect.ENERGY, new ItemStack(ConfigItems.itemResource, 12));

        addAspectLoot(Aspect.MAGIC, "shard");

        addAspectLoot(Aspect.HEAL, new ItemStack(Items.golden_apple));

        addAspectLoot(Aspect.HEAL, new ItemStack(Blocks.cake));

        addAspectLoot(Aspect.SENSES, new ItemStack(Items.dye, 20, 4));

        addAspectLoot(Aspect.SOUL, new ItemStack(Blocks.soul_sand, 64), 2);
        addAspectLoot(Aspect.SOUL, new ItemStack(Blocks.netherrack, 64), 2);
        addAspectLoot(Aspect.SOUL, new ItemStack(Blocks.nether_brick));

        addAspectLoot(Aspect.WEATHER, new ItemStack(Blocks.air));
        addAspectLoot(Aspect.WEATHER, "cloud", 100);

        addAspectLoot(Aspect.DARKNESS, new ItemStack(Blocks.obsidian, 10));

        addAspectLoot(Aspect.VOID, new ItemStack(Items.bucket));
        addAspectLoot(Aspect.VOID, "bucket");

        addAspectLoot(Aspect.POISON, new ItemStack(ConfigItems.itemResource, 16, 3));

        addAspectLoot(Aspect.LIFE, new ItemStack(Items.egg, 8));

        addAspectLoot(Aspect.TRAP, new ItemStack(Blocks.web, 4));

        addAspectLoot(Aspect.TAINT, new ItemStack(ConfigItems.itemResource, 4, 11));

        addAspectLoot(Aspect.CRYSTAL, new ItemStack(Items.diamond));

    }

}
