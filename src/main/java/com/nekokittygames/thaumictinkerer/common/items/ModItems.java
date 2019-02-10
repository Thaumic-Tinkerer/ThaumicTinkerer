package com.nekokittygames.thaumictinkerer.common.items;

import com.nekokittygames.thaumictinkerer.common.libs.LibMisc;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.HashSet;
import java.util.Set;

import static com.nekokittygames.thaumictinkerer.common.utils.MiscUtils.nullz;

@SuppressWarnings("WeakerAccess")
@GameRegistry.ObjectHolder(LibMisc.MOD_ID)
public class ModItems {

    public static final ItemShareBook share_book = nullz();
    public static final ItemBlackQuartz black_quartz = nullz();
    public static final ItemConnector connector = nullz();
    public static final ItemSoulMould soul_mould = nullz();
    public static final ItemEnergeticNitor energetic_nitor = nullz();
    public static final ItemFormRevealer form_revealer = nullz();

    @Mod.EventBusSubscriber(modid = LibMisc.MOD_ID)
    public static class RegistrationHandler {
        public static final Set<Item> ITEMS = new HashSet<>();

        /**
         * Register this mod's {@link Item}s.
         *
         * @param event The event
         */
        @SubscribeEvent
        public static void registerItems(final RegistryEvent.Register<Item> event) {
            final Item[] items = {
                    new ItemShareBook(),
                    new ItemBlackQuartz(),
                    new ItemConnector(),
                    new ItemSoulMould(),
                    new ItemEnergeticNitor(),
                    new ItemFormRevealer()
            };
            final IForgeRegistry<Item> registry = event.getRegistry();

            for (final Item item : items) {
                registry.register(item);
                ITEMS.add(item);
            }

        }
    }
}
