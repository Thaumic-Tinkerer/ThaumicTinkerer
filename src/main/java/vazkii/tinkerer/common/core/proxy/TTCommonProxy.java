/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the ThaumicTinkerer Mod.
 *
 * ThaumicTinkerer is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * ThaumicTinkerer is a Derivative Work on Thaumcraft 4.
 * Thaumcraft 4 (c) Azanor 2012
 * (http://www.minecraftforum.net/topic/1585216-)
 *
 * File Created @ [4 Sep 2013, 16:29:41 (GMT)]
 */
package vazkii.tinkerer.common.core.proxy;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import dan200.computer.api.ComputerCraftAPI;
import dan200.computer.api.IPeripheralHandler;
import dan200.turtle.api.TurtleAPI;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import thaumcraft.common.tiles.*;
import vazkii.tinkerer.common.ThaumicTinkerer;
import vazkii.tinkerer.common.block.ModBlocks;
import vazkii.tinkerer.common.block.tile.TileFunnel;
import vazkii.tinkerer.common.block.tile.TileRepairer;
import vazkii.tinkerer.common.block.tile.peripheral.PeripheralHandler;
import vazkii.tinkerer.common.block.tile.transvector.TileTransvectorInterface;
import vazkii.tinkerer.common.compat.FumeTool;
import vazkii.tinkerer.common.core.handler.ConfigHandler;
import vazkii.tinkerer.common.core.handler.kami.DimensionalShardDropHandler;
import vazkii.tinkerer.common.core.handler.kami.SoulHeartHandler;
import vazkii.tinkerer.common.enchantment.ModEnchantments;
import vazkii.tinkerer.common.enchantment.core.EnchantmentManager;
import vazkii.tinkerer.common.item.ModItems;
import vazkii.tinkerer.common.network.GuiHandler;
import vazkii.tinkerer.common.network.PlayerTracker;
import vazkii.tinkerer.common.potion.ModPotions;
import vazkii.tinkerer.common.research.ModRecipes;
import vazkii.tinkerer.common.research.ModResearch;

public class TTCommonProxy {

	public void preInit(FMLPreInitializationEvent event) {
		ConfigHandler.loadConfig(event.getSuggestedConfigurationFile());

		ModBlocks.initBlocks();
		ModItems.initItems();

		initCCPeripherals();
	}

	public void init(FMLInitializationEvent event) {
		ModEnchantments.initEnchantments();
		EnchantmentManager.initEnchantmentData();
		ModPotions.initPotions();
		ModBlocks.initTileEntities();
		NetworkRegistry.instance().registerGuiHandler(ThaumicTinkerer.instance, new GuiHandler());
		GameRegistry.registerPlayerTracker(new PlayerTracker());


		if(ConfigHandler.enableKami) {
			MinecraftForge.EVENT_BUS.register(new DimensionalShardDropHandler());
			MinecraftForge.EVENT_BUS.register(new SoulHeartHandler());
		}
	}

	public void postInit(FMLPostInitializationEvent event) {
		ModRecipes.initRecipes();
		ModResearch.initResearch();
	}

	protected void initCCPeripherals() {
		IPeripheralHandler handler = new PeripheralHandler();

		Class[] peripheralClasses = new Class[] {
				TileAlembic.class, TileCentrifuge.class, TileCrucible.class, TileFunnel.class,
				TileInfusionMatrix.class, TileJarFillable.class, TileJarNode.class, TileNode.class,
				TileRepairer.class, TileTubeFilter.class, TileTransvectorInterface.class, TileWandPedestal.class,
				TileDeconstructionTable.class, TileJarBrain.class, TileSensor.class, TileArcaneBore.class
		};
        // DUMMY CHANGE
        for(Class clazz : peripheralClasses)
			ComputerCraftAPI.registerExternalPeripheral(clazz, handler);
			
		TurtleAPI.registerUpgrade(new FumeTool());
	}
    public  boolean isOn(EntityPlayer entityPlayer)
    {
        EntityPlayerMP player=(EntityPlayerMP)entityPlayer;
        if(player.getEntityData().hasKey(EntityPlayer.PERSISTED_NBT_TAG))
            if(player.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG).hasKey("ThaumicTinkerer"))
                return player.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG).getCompoundTag("ThaumicTinkerer").getBoolean("GemArmorStatus");
        return true;
    }
	public boolean isClient() {
		return false;
	}
    public void setGemArmor(boolean status,EntityPlayer entityPlayer)
    {
        EntityPlayerMP player=(EntityPlayerMP)entityPlayer;
        NBTTagCompound base= player.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG);
        if(base==null)
            base=new NBTTagCompound();
        NBTTagCompound persisted= base.getCompoundTag("ThaumicTinkerer");
        if(persisted==null)
            persisted=new NBTTagCompound();
        persisted.setBoolean("GemArmorStatus",status);
        base.setCompoundTag("ThaumicTinkerer",persisted);
        player.getEntityData().setCompoundTag(EntityPlayer.PERSISTED_NBT_TAG,base);
    }
	public EntityPlayer getClientPlayer() {
		return null;
	}

	public void shadowSparkle(World world, float x, float y, float z, int size) {
		// NO-OP
	}
}
