package com.nekokittygames.thaumictinkerer.common.proxy;

import com.nekokittygames.thaumictinkerer.common.items.ModItems;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import static com.nekokittygames.thaumictinkerer.ThaumicTinkerer.instance;

public class CommonProxy implements ITTProxy {


    @Override
    public void registerRenderers() {
        // Empty
    }

    @SuppressWarnings("deprecation")
    @Override
    public String localize(String translationKey, Object... args) {
        return I18n.translateToLocalFormatted(translationKey, args);
    }

    @Override
    public void init(FMLInitializationEvent event) {
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiProxy());

    }

    @Override
    public void preInit(FMLPreInitializationEvent event) {

        // Empty
    }
}
