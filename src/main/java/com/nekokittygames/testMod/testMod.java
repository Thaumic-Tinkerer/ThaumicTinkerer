package com.nekokittygames.testMod;

import com.nekokittygames.testMod.Block.BlockBlock;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by katsw on 24/05/2015.
 */
@Mod(modid = "tesmp",name="temp",version="1.0")
public class testMod {


    public static BlockBlock blockBlock;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        blockBlock=new BlockBlock();
        GameRegistry.registerBlock(blockBlock,"blockblock");
        blockBlock.setUnlocalizedName("blockblockblock");
        blockBlock.setCreativeTab(CreativeTabs.tabFood);
    }
}
