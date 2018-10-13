package com.nekokittygames.thaumictinkerer.common.packets;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler {

    private static int packetId = 0;

    public static SimpleNetworkWrapper INSTANCE = null;

    public PacketHandler() {
    }

    public static int nextID() {
        return packetId++;
    }

    public static void registerMessages(String channelName) {
        INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(channelName);
        registerMessages();
    }

    public static void registerMessages() {
        // Register messages which are sent from the client to the server here:
        INSTANCE.registerMessage(PacketMobMagnet.Handler.class, PacketMobMagnet.class, nextID(), Side.SERVER);
        INSTANCE.registerMessage(PacketAddEnchant.Handler.class,PacketAddEnchant.class,nextID(),Side.SERVER);
        INSTANCE.registerMessage(PacketRemoveEnchant.Handler.class,PacketRemoveEnchant.class,nextID(),Side.SERVER);
        INSTANCE.registerMessage(PacketIncrementEnchantLevel.Handler.class,PacketIncrementEnchantLevel.class,nextID(),Side.SERVER);
        INSTANCE.registerMessage(PacketStartEnchant.Handler.class,PacketStartEnchant.class,nextID(),Side.SERVER);
        INSTANCE.registerMessage(PacketTabletButton.Handler.class,PacketTabletButton.class,nextID(),Side.SERVER);
    }

}
