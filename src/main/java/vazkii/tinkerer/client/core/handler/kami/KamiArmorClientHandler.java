package vazkii.tinkerer.client.core.handler.kami;

import cpw.mods.fml.common.network.PacketDispatcher;
import vazkii.tinkerer.common.ThaumicTinkerer;
import vazkii.tinkerer.common.network.PacketManager;
import vazkii.tinkerer.common.network.packet.kami.PacketToggleArmor;

/**
 * Created by Katrina on 02/03/14.
 */
public class KamiArmorClientHandler {

    public static boolean ArmorEnabled=true;



    public static void SetStatus(boolean status)
    {
        ArmorEnabled=status;
        PacketDispatcher.sendPacketToServer(PacketManager.buildPacket(new PacketToggleArmor(status)));
    }
}
