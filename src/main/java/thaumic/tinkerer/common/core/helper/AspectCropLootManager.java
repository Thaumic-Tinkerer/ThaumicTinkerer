package thaumic.tinkerer.common.core.helper;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.entities.ItemSpawnerEgg;
import thaumcraft.common.items.ItemWispEssence;
import thaumic.tinkerer.common.ThaumicTinkerer;
import thaumic.tinkerer.common.block.quartz.BlockDarkQuartz;
import thaumic.tinkerer.common.item.ItemInfusedGrain;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

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
                return pair.getKey();
            }
        }
        return null;
    }

    public static void addAspectLoot(Aspect aspect, ItemStack stack) {
        addAspectLoot(aspect, stack, 1);
    }

    public static void addAspectLoot(Aspect aspect, String target) {
        for (String ore : OreDictionary.getOreNames()) {
            if (ore.toUpperCase().contains(target.toUpperCase())) {
                for (ItemStack stack : OreDictionary.getOres(ore)) {
                    addAspectLoot(aspect, stack);
                }
            }
        }
    }


    public static void addAspectLoot(Aspect aspect, ItemStack stack, int power) {
        lootMap.get(aspect).put(stack, 1);
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

        addAspectLoot(Aspect.ELDRITCH, new ItemStack(Items.ender_pearl, 4), 2);
        addAspectLoot(Aspect.ELDRITCH, new ItemStack(Items.ender_eye, 4), 1);

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
        addAspectLoot(Aspect.UNDEAD, new ItemStack(Items.bone, 32));

        addAspectLoot(Aspect.CRAFT, new ItemStack(ThaumicTinkerer.registry.getFirstBlockFromClass(BlockDarkQuartz.class), 32));
        addAspectLoot(Aspect.CRAFT, new ItemStack(ConfigBlocks.blockStoneDevice, 16));

        addAspectLoot(Aspect.HUNGER, new ItemStack(Items.wheat, 32));

        addAspectLoot(Aspect.COLD, new ItemStack(Items.snowball, 16));
        addAspectLoot(Aspect.COLD, "rodBlizz");

        addAspectLoot(Aspect., "sapling");
    }

}
