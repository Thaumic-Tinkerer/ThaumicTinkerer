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
package thaumic.tinkerer.common.core.proxy;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;
import dan200.computercraft.api.ComputerCraftAPI;
import li.cil.oc.api.Driver;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.world.ChunkDataEvent;
import thaumcraft.api.aspects.IEssentiaTransport;
import thaumcraft.api.wands.WandCap;
import thaumcraft.api.wands.WandRod;
import thaumcraft.common.tiles.*;
import thaumic.tinkerer.common.ThaumicTinkerer;
import thaumic.tinkerer.common.block.tile.TileFunnel;
import thaumic.tinkerer.common.block.tile.TileRepairer;
import thaumic.tinkerer.common.block.tile.transvector.TileTransvectorInterface;
import thaumic.tinkerer.common.compat.EnderIO;
import thaumic.tinkerer.common.compat.FumeTool;
import thaumic.tinkerer.common.core.handler.ConfigHandler;
import thaumic.tinkerer.common.core.handler.ModCreativeTab;
import thaumic.tinkerer.common.core.handler.kami.DimensionalShardDropHandler;
import thaumic.tinkerer.common.core.handler.kami.KamiArmorHandler;
import thaumic.tinkerer.common.core.handler.kami.KamiDimensionHandler;
import thaumic.tinkerer.common.core.handler.kami.SoulHeartHandler;
import thaumic.tinkerer.common.core.helper.AspectCropLootManager;
import thaumic.tinkerer.common.core.helper.BonemealEventHandler;
import thaumic.tinkerer.common.core.helper.NumericAspectHelper;
import thaumic.tinkerer.common.enchantment.ModEnchantments;
import thaumic.tinkerer.common.enchantment.core.EnchantmentManager;
import thaumic.tinkerer.common.item.SpellClothCraftingHandler;
import thaumic.tinkerer.common.item.foci.ItemFocusDeflect;
import thaumic.tinkerer.common.item.kami.wand.CapIchor;
import thaumic.tinkerer.common.item.kami.wand.RodIchorcloth;
import thaumic.tinkerer.common.lib.LibMisc;
import thaumic.tinkerer.common.network.GuiHandler;
import thaumic.tinkerer.common.network.PlayerTracker;
import thaumic.tinkerer.common.network.packet.*;
import thaumic.tinkerer.common.network.packet.kami.PacketSoulHearts;
import thaumic.tinkerer.common.network.packet.kami.PacketToggleArmor;
import thaumic.tinkerer.common.network.packet.kami.PacketWarpGateButton;
import thaumic.tinkerer.common.network.packet.kami.PacketWarpGateTeleport;
import thaumic.tinkerer.common.peripheral.OpenComputers.*;
import thaumic.tinkerer.common.peripheral.PeripheralHandler;
import thaumic.tinkerer.common.potion.ModPotions;
import thaumic.tinkerer.common.research.ResearchHelper;

public class TTCommonProxy {


    public static EnumRarity kamiRarity;
    public WandCap capIchor;
    public WandRod rodIchor;
    public Item.ToolMaterial toolMaterialIchor;

    public void preInit(FMLPreInitializationEvent event) {
        toolMaterialIchor = EnumHelper.addToolMaterial("ICHOR", 4, -1, 10F, 5F, 25);
        ModCreativeTab.INSTANCE = new ModCreativeTab();
        ConfigHandler.loadConfig(event.getSuggestedConfigurationFile());
        //ModItems.initItems();

        NumericAspectHelper.init();
        ThaumicTinkerer.registry.preInit();
        capIchor = new CapIchor();
        rodIchor = new RodIchorcloth();
        
        ModCreativeTab.INSTANCE.addWand();
        
        if (Loader.isModLoaded("ComputerCraft")) {
            initCCPeripherals();
        }
        registerVersionChecker();

        kamiRarity = EnumHelper.addEnum(new Class[][]{{EnumRarity.class, EnumChatFormatting.class, String.class}}, EnumRarity.class, "KAMI", EnumChatFormatting.LIGHT_PURPLE, "Kami");
    }

    public void init(FMLInitializationEvent event) {
        ModEnchantments.initEnchantments();
        EnchantmentManager.initEnchantmentData();
        ModPotions.initPotions();
        ThaumicTinkerer.registry.init();
        NetworkRegistry.INSTANCE.registerGuiHandler(ThaumicTinkerer.instance, new GuiHandler());
        registerPackets();
        FMLCommonHandler.instance().bus().register(new PlayerTracker());
        MinecraftForge.EVENT_BUS.register(new BonemealEventHandler());
        FMLCommonHandler.instance().bus().register(new SpellClothCraftingHandler());

        if (ConfigHandler.enableKami) {
            MinecraftForge.EVENT_BUS.register(new DimensionalShardDropHandler());
            MinecraftForge.EVENT_BUS.register(new KamiDimensionHandler());
            MinecraftForge.EVENT_BUS.register(new SoulHeartHandler());
        }

        if (Loader.isModLoaded("OpenComputers")) {
            initOpenCDrivers();
        }
        if(Loader.isModLoaded("EnderIO"))
        {
            InitEnderIO();
        }

        if (Loader.isModLoaded("ForgeMultipart")) {
            ThaumicTinkerer.log.trace("Attempting to load Multiparts");
            try {
                Class clazz = Class.forName("thaumic.tinkerer.common.multipart.MultipartHandler");
                clazz.newInstance();
            } catch (Throwable e) {
                ThaumicTinkerer.log.error("Error registering multiparts", e);
            }
        } else {
            ThaumicTinkerer.log.info("Skipping TC Multipart integration");
        }
    }

    protected void registerPackets() {
        ThaumicTinkerer.netHandler.registerMessage(PacketSoulHearts.class, PacketSoulHearts.class, 142 + 0, Side.CLIENT);
        ThaumicTinkerer.netHandler.registerMessage(PacketToggleArmor.class, PacketToggleArmor.class, 142 + 1, Side.CLIENT);
        ThaumicTinkerer.netHandler.registerMessage(PacketToggleArmor.class, PacketToggleArmor.class, 142 + 2, Side.SERVER);
        ThaumicTinkerer.netHandler.registerMessage(PacketWarpGateButton.class, PacketWarpGateButton.class, 142 + 3, Side.SERVER);
        ThaumicTinkerer.netHandler.registerMessage(PacketWarpGateTeleport.class, PacketWarpGateTeleport.class, 142 + 4, Side.SERVER);
        ThaumicTinkerer.netHandler.registerMessage(PacketEnchanterAddEnchant.class, PacketEnchanterAddEnchant.class, 142 + 5, Side.SERVER);
        ThaumicTinkerer.netHandler.registerMessage(PacketEnchanterStartWorking.class, PacketEnchanterStartWorking.class, 142 + 6, Side.SERVER);
        ThaumicTinkerer.netHandler.registerMessage(PacketMobMagnetButton.class, PacketMobMagnetButton.class, 142 + 7, Side.SERVER);
        ThaumicTinkerer.netHandler.registerMessage(PacketTabletButton.class, PacketTabletButton.class, 142 + 8, Side.SERVER);
        ThaumicTinkerer.netHandler.registerMessage(PacketPlacerButton.class, PacketPlacerButton.class, 142 + 9, Side.SERVER);
    }

    public void registerVersionChecker() {
        NBTTagCompound compound = new NBTTagCompound();
        compound.setString("curseProjectName", "75598-thaumic-tinkerer");
        compound.setString("curseFilenameParser", "ThaumicTinkerer-[].jar");
        compound.setString("modDisplayName", "Thaumic Tinkerer");
        FMLInterModComms.sendRuntimeMessage(LibMisc.MOD_ID, "VersionChecker", "addCurseCheck", compound);
    }

    public void postInit(FMLPostInitializationEvent event) {
        ResearchHelper.initResearch();
        ThaumicTinkerer.registry.postInit();
        AspectCropLootManager.populateLootMap();
        ItemFocusDeflect.setupBlackList();
    }

    @Optional.Method(modid = "EnderIO")
    protected void InitEnderIO()
    {
        EnderIO.registerPlanters();
    }
    @Optional.Method(modid = "ComputerCraft")
    protected void initCCPeripherals() {
        PeripheralHandler handler = new PeripheralHandler();

        Class[] peripheralClasses = new Class[]{
                TileAlembic.class, TileCentrifuge.class, TileCrucible.class, TileFunnel.class,
                TileInfusionMatrix.class, TileJarFillable.class, TileJarNode.class, TileNode.class,
                TileRepairer.class, TileTubeFilter.class, TileTransvectorInterface.class, TileWandPedestal.class,
                TileDeconstructionTable.class, TileJarBrain.class, TileSensor.class, TileArcaneBore.class, IEssentiaTransport.class
        };
        handler.registerPeripheralProvider();

        ComputerCraftAPI.registerTurtleUpgrade(new FumeTool());
    }

    @Optional.Method(modid = "OpenComputers")
    public void initOpenCDrivers() {
        Driver.add(new DriverIAspectContainer());
        Driver.add(new DriverArcaneEar());
        Driver.add(new DriverBrainInAJar());
        Driver.add(new DriverDeconstructor());
        Driver.add(new DriverEssentiaTransport());
        Driver.add(new DriverArcaneBore());

    }

    public boolean isClient() {
        return false;
    }

    public boolean armorStatus(EntityPlayer player) {
        return KamiArmorHandler.getArmorStatus(player);
    }

    public void setArmor(EntityPlayer player, boolean status) {
        KamiArmorHandler.setArmorStatus(player, status);
    }

    public EntityPlayer getClientPlayer() {
        return null;
    }

    public void shadowSparkle(World world, float x, float y, float z, int size) {
        // NO-OP
    }
}
