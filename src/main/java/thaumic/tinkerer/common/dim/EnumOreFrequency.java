package thaumic.tinkerer.common.dim;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by pixlepix on 3/31/14.
 * <p/>
 * Numbers here used with permission of Vazkii
 */
public enum EnumOreFrequency {

    ALUMINUM("oreAluminum", 617),
    AMBER("oreAmber", 161),
    APATITE("oreApatite", 269),
    BLUE_TOPAZ("oreBlueTopaz", 238),
    CERTUS_QUARTZ("oreCertusQuartz", 234),
    CHIMERITE("oreChimerite", 270),
    CINNABAR("oreCinnabar", 172),
    COAL("oreCoal", 2648),
    COPPER("oreCopper", 603),
    DIAMOND("oreDiamond", 67),
    EMERALD("oreEmerald", 48),
    DARK_IRON("oreFzDarkIron", 61),
    GOLD("oreGold", 164),
    AIR("oreInfusedAir", 94),
    EARTH("oreInfusedEarth", 35),
    ENTROPY("oreInfusedEntropy", 53),
    FIRE("oreInfusedFire", 42),
    ORDER("oreInfusedOrder", 31),
    WATER("oreInfusedWater", 27),
    IRON("oreIron", 1503),
    LAPIS("oreLapis", 57),
    LEAD("oreLead", 335),
    NICKEL("oreNickel", 72),
    PERIDOT("orePeridot", 79),
    REDSTONE("oreRedstone", 364),
    RUBY("oreRuby", 57),
    SALT("oreSaltpeter", 768),
    SAPPHIRE("oreSapphire", 70),
    SILVER("oreSilver", 416),
    SULFUR("oreSulfur", 105),
    TIN("oreTin", 507),
    URANIUM("oreUranium", 112),
    VINETUM("oreVinteum", 392),
	YELLORITE("oreYellorite", 188);

    public int freq;
    public String name;
    EnumOreFrequency(String name, int freq) {
        this.name = name;
        this.freq = freq;
    }

    public static int getSum() {
        int total = 0;
        for (EnumOreFrequency e : EnumOreFrequency.values()) {
            if (e.isValid()) {
                total += e.freq;
            }
        }
        return total;
    }

    public static ArrayList<EnumOreFrequency> getValidOres() {
        ArrayList<EnumOreFrequency> result = new ArrayList<EnumOreFrequency>();
        for (EnumOreFrequency e : EnumOreFrequency.values()) {
            if (e.isValid()) {
                result.add(e);
            }
        }
        return result;
    }

    public static ItemStack getRandomOre(Random rand) {
        int randInt = rand.nextInt(getSum());

        for (EnumOreFrequency e : getValidOres()) {
            randInt -= e.freq;
            if (randInt < 0) {
                return OreDictionary.getOres(e.name).get(0);
            }
        }
        return new ItemStack(Blocks.iron_ore);
    }

    public boolean isValid() {
        return !Arrays.asList(OreClusterGenerator.blacklist).contains(name) && !OreDictionary.getOres(name).isEmpty() && OreDictionary.getOres(name).get(0).getItem() instanceof ItemBlock;
    }

}
