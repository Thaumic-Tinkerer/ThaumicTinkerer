package com.nekokittygames.thaumictinkerer.client.proxy;

import com.nekokittygames.thaumictinkerer.common.libs.LibMisc;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.registries.IForgeRegistry;

public class ProxyBlock {
    private static ModelResourceLocation fluidQuicksilverLocation = new ModelResourceLocation(new ResourceLocation(LibMisc.MOD_ID, "fluid_quicksilver"), "fluid");
    
    public static void setupBlocksClient(IForgeRegistry<Block> forgeRegistry) {
        Block fluidQuicksilverBlock = forgeRegistry.getValue(new ResourceLocation(LibMisc.MOD_ID, "fluid_quicksilver"));
        Item fluidQuicksilverItem = Item.getItemFromBlock(fluidQuicksilverBlock);
        ModelBakery.registerItemVariants(fluidQuicksilverItem, new ResourceLocation[0]);
        ModelLoader.setCustomMeshDefinition(fluidQuicksilverItem, new ItemMeshDefinition() {
            @Override
            public ModelResourceLocation getModelLocation(ItemStack stack) {
                return ProxyBlock.fluidQuicksilverLocation;
            }
        });
        ModelLoader.setCustomStateMapper(fluidQuicksilverBlock, new StateMapperBase() {
            @Override
            protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
                return ProxyBlock.fluidQuicksilverLocation;
            }
        });
    }
}