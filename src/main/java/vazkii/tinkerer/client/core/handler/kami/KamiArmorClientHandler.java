package vazkii.tinkerer.client.core.handler.kami;

import vazkii.tinkerer.common.ThaumicTinkerer;
import vazkii.tinkerer.common.network.packet.kami.PacketToggleArmor;
import cpw.mods.fml.client.FMLClientHandler;

/**
 * Created by Katrina on 02/03/14.
 */
public class KamiArmorClientHandler {

    public static boolean ArmorEnabled=true;



    public static void SetStatus(boolean status)
    {
        if(FMLClientHandler.instance().getClient().currentScreen == null){
            ArmorEnabled=status;
            ThaumicTinkerer.packetPipeline.sendToServer(new PacketToggleArmor(status));
        }
    }
}
