package com.nekokittygames.thaumictinkerer.common.blocks;

import com.google.common.base.Preconditions;
import com.nekokittygames.thaumictinkerer.common.blocks.transvector.BlockTransvectorDislocator;
import com.nekokittygames.thaumictinkerer.common.blocks.transvector.BlockTransvectorInterface;
import com.nekokittygames.thaumictinkerer.common.libs.LibBlockNames;
import com.nekokittygames.thaumictinkerer.common.libs.LibMisc;
import com.nekokittygames.thaumictinkerer.common.misc.IOreDict;
import com.nekokittygames.thaumictinkerer.common.tileentity.*;
import com.nekokittygames.thaumictinkerer.common.tileentity.transvector.TileEntityTransvectorDislocator;
import com.nekokittygames.thaumictinkerer.common.tileentity.transvector.TileEntityTransvectorInterface;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.HashSet;
import java.util.Set;

import static com.nekokittygames.thaumictinkerer.common.utils.MiscUtils.nullz;

@SuppressWarnings("WeakerAccess")
@GameRegistry.ObjectHolder(LibMisc.MOD_ID)
public class ModBlocks {

    public static final BlockFunnel funnel = nullz();
    public static final BlockDissimulation dissimulation = nullz();
    public static final BlockTransvectorInterface transvector_interface = nullz();
    public static final BlockTransvectorDislocator transvector_dislocator = nullz();
    public static final BlockRepairer repairer = nullz();
    public static final BlockItemMagnet magnet = nullz();
    public static final BlockMobMagnet mob_magnet = nullz();
    public static final BlockNitorVapor nitor_vapor = nullz();
    public static final BlockExample example = nullz();
    public static final BlockEnchanter osmotic_enchanter = nullz();
    public static final BlockEnchantmentPillar enchantment_pillar = nullz();
    public static final BlockBlackQuartz black_quartz_block = nullz();
    public static final BlockAnimationTablet animation_tablet = nullz();
    public static final BlockDummyNitor dummy_nitor=nullz();

    @Mod.EventBusSubscriber(modid = LibMisc.MOD_ID)
    public static class RegistrationHandler {
        public static final Set<ItemBlock> ITEM_BLOCKS = new HashSet<>();

        /**
         * Register this mod's {@link Block}s.
         *
         * @param event The event
         */
        @SubscribeEvent
        public static void registerBlocks(final RegistryEvent.Register<Block> event) {
            final IForgeRegistry<Block> registry = event.getRegistry();

            final Block[] blocks = {
                    new BlockFunnel(),
                    new BlockDissimulation(),
                    new BlockTransvectorInterface(),
                    new BlockTransvectorDislocator(),
                    new BlockRepairer(),
                    new BlockItemMagnet(),
                    new BlockMobMagnet(),
                    new BlockNitorVapor(),
                    new BlockExample(),
                    new BlockEnchanter(),
                    new BlockEnchantmentPillar(),
                    new BlockBlackQuartz(),
                    new BlockAnimationTablet(),
                    new BlockDummyNitor(),
            };
            for(Block block:blocks) {
                registry.register(block);
                if(block instanceof IOreDict)
                    OreDictionary.registerOre(((IOreDict)block).getOreDictName(),block);
            }
            registerTileEntities();
        }

        /**
         * Register this mod's {@link ItemBlock}s.
         *
         * @param event The event
         */
        @SubscribeEvent
        public static void registerItemBlocks(final RegistryEvent.Register<Item> event) {
            final ItemBlock[] items = {
                    new ItemBlock(funnel),
                    new ItemBlock(dissimulation),
                    new ItemBlock(transvector_interface),
                    new ItemBlock(transvector_dislocator),
                    new ItemBlock(repairer),
                    new ItemBlock(magnet),
                    new ItemBlock(mob_magnet),
                    //new ItemBlock(nitor_vapor),
                    //new ItemBlock(example),
                    new ItemBlock(osmotic_enchanter),
                    new ItemBlock(enchantment_pillar),
                    new ItemBlock(animation_tablet),
                    new ItemBlock(black_quartz_block),
                    new ItemBlock(dummy_nitor),
            };
            final IForgeRegistry<Item> registry = event.getRegistry();

            for (final ItemBlock item : items) {
                final Block block = item.getBlock();
                final ResourceLocation registryName = Preconditions.checkNotNull(block.getRegistryName(), "Block %s has null registry name", block);
                registry.register(item.setRegistryName(registryName));
                ITEM_BLOCKS.add(item);
            }
        }

        private static void registerTileEntities() {
            registerTileEntity(TileEntityFunnel.class, LibBlockNames.FUNNEL);
            registerTileEntity(TileEntityDissimulation.class, LibBlockNames.DISSIMULATION);
            registerTileEntity(TileEntityTransvectorInterface.class, LibBlockNames.TRANSVECTOR_INTERFACE);
            registerTileEntity(TileEntityTransvectorDislocator.class, LibBlockNames.TRANSVECTOR_DISLOCATOR);
            registerTileEntity(TileEntityRepairer.class, LibBlockNames.REPAIRER);
            registerTileEntity(TileEntityItemMagnet.class, LibBlockNames.MAGNET);
            registerTileEntity(TileEntityMobMagnet.class, LibBlockNames.MOB_MAGNET);
            registerTileEntity(TileEntityExample.class, LibBlockNames.EXAMPLE);
            registerTileEntity(TileEntityEnchanter.class, LibBlockNames.OSMOTIC_ENCHANTER);
            registerTileEntity(TileEntityEnchantmentPillar.class, LibBlockNames.ENCHANTMENT_PILLAR);
            registerTileEntity(TileEntityAnimationTablet.class, LibBlockNames.ANIMATION_TABLET);
        }

        private static void registerTileEntity(Class<? extends TileEntity> clazz, String name) {
            GameRegistry.registerTileEntity(clazz, new ResourceLocation("thaumictinkerer", name).toString());
        }
    }
}
