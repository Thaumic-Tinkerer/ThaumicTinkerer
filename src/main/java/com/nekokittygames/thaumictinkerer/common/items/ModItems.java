package com.nekokittygames.thaumictinkerer.common.items;

import com.nekokittygames.thaumictinkerer.ThaumicTinkerer;
import com.nekokittygames.thaumictinkerer.common.items.Kami.*;
import com.nekokittygames.thaumictinkerer.common.items.Kami.Tools.*;
import com.nekokittygames.thaumictinkerer.common.items.Kami.ichorpouch.IchorPouch;
import com.nekokittygames.thaumictinkerer.common.libs.LibMisc;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import thaumcraft.api.ThaumcraftMaterials;

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
    public static final ItemSpellbindingCloth spellbinding_cloth=nullz();
    public static final ItemBloodSword blood_sword=nullz();
    public static final ItemMobAspect mob_aspect=nullz();
    public static final ItemKamiResource kamiresource=new ItemKamiResource();
    public static final IchoriumAxe ichorium_axe = null;
    public static final IchoriumSword ichorium_sword = null;
    public static final IchoriumShovel ichorium_shovel = null;
    public static final IchoriumPick ichorium_pick= null;
    public static final IchoriumAxeAdv ichorium_axe_adv = null;
    public static final IchoriumSwordAdv ichorium_sword_adv = null;
    public static final IchoriumShovelAdv ichorium_shovel_adv = null;
    public static final IchoriumPickAdv ichorium_pick_adv= null;
    public static final Item ichor_helm = null;
    public static final Item ichor_chest = null;
    public static final Item ichor_legs = null;
    public static final Item ichor_boots = null;
    public static final Item kami_helm = null;
    public static final Item kami_chest = null;
    public static final Item kami_legs = null;
    public static final Item kami_boots = null;
    public static final Item block_talisman = null;
    public static final Item ichor_pouch = null;
public static final Item revealing_helm = null;
public static final ItemProtoclay proto_clay = null;
    //public static final ItemFormRevealer form_revealer = nullz();

    @Mod.EventBusSubscriber(modid = LibMisc.MOD_ID)
    public static class RegistrationHandler {
        public static final Set<Item> ITEMS = new HashSet<>();
        public static final Item.ToolMaterial MATERIAL_ICHORIUM= EnumHelper.addToolMaterial("ICHOR", 4, -1, 10F, 5F, 25);
        public static final ItemArmor.ArmorMaterial MATERIAL_ICHOR = EnumHelper.addArmorMaterial("ichor", LibMisc.MOD_ID + ":ichor", -1, new int[]{3, 6, 8, 3}, 20, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 3.0F);
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
                    new ItemSpellbindingCloth(),
                    new ItemBloodSword(),
                    new ItemMobAspect(),
                    new ItemKamiResource(),
                    new ItemProtoclay(),
                    new IchorPouch(),
                    new IchoriumAxe("ichorium_axe"),
                    new IchoriumSword("ichorium_sword"),
                    new IchoriumShovel("ichorium_shovel"),
                    new IchoriumPick("ichorium_pick"),
                    new IchoriumPickAdv("ichorium_pick_adv"),
                    new IchoriumAxeAdv("ichorium_axe_adv"),
                    new IchoriumShovelAdv("ichorium_shovel_adv"),
                    new IchoriumSwordAdv("ichorium_sword_adv", ThaumicTinkerer.getTab(), MATERIAL_ICHORIUM),
                    new IchorArmor("ichor_helm", MATERIAL_ICHOR, 1, EntityEquipmentSlot.HEAD),
                    new IchorArmor("ichor_chest", MATERIAL_ICHOR, 1, EntityEquipmentSlot.CHEST),
                    new IchorArmor("ichor_legs", MATERIAL_ICHOR, 2, EntityEquipmentSlot.LEGS),
                    new IchorArmor("ichor_boots", MATERIAL_ICHOR, 1, EntityEquipmentSlot.FEET),
                    new KamiArmor("kami_helm", MATERIAL_ICHOR, 1, EntityEquipmentSlot.HEAD),
                    new KamiArmor("kami_chest", MATERIAL_ICHOR, 1, EntityEquipmentSlot.CHEST),
                    new KamiArmor("kami_legs", MATERIAL_ICHOR, 2, EntityEquipmentSlot.LEGS),
                    new KamiArmor("kami_boots", MATERIAL_ICHOR, 1, EntityEquipmentSlot.FEET),
                    new ItemBlockTalisman(),
                    new ItemRevealingHelm("revealing_helm", ThaumcraftMaterials.ARMORMAT_THAUMIUM, 1, EntityEquipmentSlot.HEAD, ThaumicTinkerer.getTab())
                   // new ItemFormRevealer()
            };
            final IForgeRegistry<Item> registry = event.getRegistry();

            for (final Item item : items) {
                registry.register(item);
                ITEMS.add(item);
            }


        }
    }
}
