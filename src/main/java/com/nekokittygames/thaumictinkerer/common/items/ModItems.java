package com.nekokittygames.thaumictinkerer.common.items;

import com.nekokittygames.thaumictinkerer.common.items.baubles.ItemCleaningTalisman;
import com.nekokittygames.thaumictinkerer.common.libs.LibMisc;
import net.minecraft.item.Item;
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
public class ModItems {

    public static final ItemShareBook share_book=Null();
    public static final ItemCleaningTalisman cleaning_talisman=Null();
    public static final ItemBlackQuartz black_quartz=Null();
    public static final ItemConnector connector=Null();
    public static final ItemSoulMould soul_mould=Null();
    public static final ItemEnergeticNitor energetic_nitor = Null();
    public static final ItemFormRevealer form_revealer=Null();

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
                    new ItemCleaningTalisman(),
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
