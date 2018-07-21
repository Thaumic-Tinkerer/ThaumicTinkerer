package com.nekokittygames.thaumictinkerer.client.rendering;

import com.nekokittygames.thaumictinkerer.client.libs.LibClientMisc;
import com.nekokittygames.thaumictinkerer.common.blocks.ModBlocks;
import com.nekokittygames.thaumictinkerer.common.items.ModItems;
import com.nekokittygames.thaumictinkerer.common.libs.LibMisc;
import com.nekokittygames.thaumictinkerer.common.utils.IVariant;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Mod.EventBusSubscriber(value = Side.CLIENT, modid = LibMisc.MOD_ID)
public class ModelManager {
    public static final ModelManager INSTANCE = new ModelManager();

    private static final String FLUID_MODEL_PATH = LibClientMisc.RESOURCE_PREFIX + "fluid";

    private ModelManager() {

    }

    /**
     * Register this mod's {@link Fluid}, {@link Block} and {@link Item} models.
     *
     * @param event The event
     */
    @SubscribeEvent
    public static void registerAllModels(final ModelRegistryEvent event) {
        //INSTANCE.registerFluidModels();
        INSTANCE.registerBlockModels();
        INSTANCE.registerItemModels();
    }

    private void registerBlockModels() {

        //registerBlockItemModel(ModBlocks.funnel.getDefaultState().withProperty(BlockFunnel.JAR, false));
        OBJLoader.INSTANCE.addDomain("thaumictinkerer");
        ModBlocks.RegistrationHandler.ITEM_BLOCKS.stream().filter(item -> !itemsRegistered.contains(item)).forEach(this::registerItemModel);

    }
    private final StateMapperBase propertyStringMapper = new StateMapperBase() {
        @Override
        protected ModelResourceLocation getModelResourceLocation(final IBlockState state) {
            return new ModelResourceLocation("minecraft:air");
        }
    };

    private void registerBlockItemModel(IBlockState state) {
        final Block block = state.getBlock();
        final Item item = Item.getItemFromBlock(block);

        if (item != Items.AIR) {
            final ResourceLocation registryName = Objects.requireNonNull(block.getRegistryName());
            ModelResourceLocation mrl=new ModelResourceLocation(registryName, propertyStringMapper.getPropertyString(state.getProperties()));
            registerItemModel(item, mrl);
        }
    }

    /**
     * The {@link Item}s that have had models registered so far.
     */
    private final Set<Item> itemsRegistered = new HashSet<>();

    private void registerItemModels() {

        ModItems.RegistrationHandler.ITEMS.stream().filter(item -> !itemsRegistered.contains(item)).forEach(this::registerItemModel);
    }


    private <T extends IVariant> void registerVariantItemModels(final Item item, final String variantName, final T[] values) {
        for (final T value : values) {

            registerItemModelForMeta(item, value.getMeta(), variantName + "=" + value.getName());
        }
    }
    /**
     * Register a single model for an {@link Item}.
     * <p>
     * Uses the registry name as the domain/path and {@code "inventory"} as the variant.
     *
     * @param item The Item
     */
    private void registerItemModel(final Item item) {
        final ResourceLocation registryName = Objects.requireNonNull(item.getRegistryName());
        registerItemModel(item, registryName.toString());
    }

    /**
     * Register a single model for an {@link Item}.
     * <p>
     * Uses {@code modelLocation} as the domain/path and {@link "inventory"} as the variant.
     *
     * @param item          The Item
     * @param modelLocation The model location
     */
    private void registerItemModel(final Item item, final String modelLocation) {
        final ModelResourceLocation fullModelLocation = new ModelResourceLocation(modelLocation, "inventory");
        registerItemModel(item, fullModelLocation);
    }

    /**
     * Register a single model for an {@link Item}.
     * <p>
     * Uses {@code fullModelLocation} as the domain, path and variant.
     *
     * @param item              The Item
     * @param fullModelLocation The full model location
     */
    private void registerItemModel(final Item item, final ModelResourceLocation fullModelLocation) {
        ModelBakery.registerItemVariants(item, fullModelLocation); // Ensure the custom model is loaded and prevent the default model from being loaded
        registerItemModel(item, stack -> fullModelLocation);
    }

    /**
     * Register an {@link ItemMeshDefinition} for an {@link Item}.
     *
     * @param item           The Item
     * @param meshDefinition The ItemMeshDefinition
     */
    private void registerItemModel(final Item item, final ItemMeshDefinition meshDefinition) {
        itemsRegistered.add(item);
        ModelLoader.setCustomMeshDefinition(item, meshDefinition);
    }


    /**
     * Register a model for a metadata value an {@link Item}.
     * <p>
     * Uses the registry name as the domain/path and {@code variant} as the variant.
     *
     * @param item     The Item
     * @param metadata The metadata
     * @param variant  The variant
     */
    private void registerItemModelForMeta(final Item item, final int metadata, final String variant) {
        registerItemModelForMeta(item, metadata, new ModelResourceLocation(item.getRegistryName(), variant));
    }

    /**
     * Register a model for a metadata value of an {@link Item}.
     * <p>
     * Uses {@code modelResourceLocation} as the domain, path and variant.
     *
     * @param item                  The Item
     * @param metadata              The metadata
     * @param modelResourceLocation The full model location
     */
    private void registerItemModelForMeta(final Item item, final int metadata, final ModelResourceLocation modelResourceLocation) {
        itemsRegistered.add(item);
        ModelLoader.setCustomModelResourceLocation(item, metadata, modelResourceLocation);
    }
}
