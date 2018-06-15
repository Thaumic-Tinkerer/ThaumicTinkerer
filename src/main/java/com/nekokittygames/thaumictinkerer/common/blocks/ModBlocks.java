package com.nekokittygames.thaumictinkerer.common.blocks;

import com.google.common.base.Preconditions;
import com.nekokittygames.thaumictinkerer.client.misc.LibClientMisc;
import com.nekokittygames.thaumictinkerer.common.libs.LibBlockNames;
import com.nekokittygames.thaumictinkerer.common.libs.LibMisc;
import com.nekokittygames.thaumictinkerer.common.tileentity.TileEntityFunnel;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.HashSet;
import java.util.Set;

import static com.nekokittygames.thaumictinkerer.common.utils.MiscUtils.Null;

@SuppressWarnings("WeakerAccess")
@GameRegistry.ObjectHolder(LibMisc.MOD_ID)
public class ModBlocks {

    public static final BlockFunnel funnel=Null();


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
                    new BlockFunnel()
            };
            registry.registerAll(blocks);
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
                new ItemBlock(funnel)
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
        }

        private static void registerTileEntity(Class<? extends TileEntity> clazz, String name) {
            GameRegistry.registerTileEntity(clazz, new ResourceLocation("thaumictinkerer",name));
        }
    }
}
