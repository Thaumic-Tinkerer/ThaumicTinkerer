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
 * File Created @ [25 Oct 2013, 21:25:50 (GMT)]
 */
package thaumic.tinkerer.common.network;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import thaumic.tinkerer.common.ThaumicTinkerer;
import thaumic.tinkerer.common.core.handler.kami.KamiArmorHandler;
import thaumic.tinkerer.common.item.foci.ItemFocusHeal;
import thaumic.tinkerer.common.item.foci.ItemFocusSmelt;
import thaumic.tinkerer.common.item.kami.armor.ItemGemBoots;
import thaumic.tinkerer.common.item.kami.armor.ItemGemChest;
import thaumic.tinkerer.common.network.packet.kami.PacketToggleArmor;

public class PlayerTracker {

    @SubscribeEvent
    public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {

        if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER) {
            ItemFocusHeal.playerHealData.put(event.player.getGameProfile().getName(), 0);
            ThaumicTinkerer.netHandler.sendTo(new PacketToggleArmor(KamiArmorHandler.getArmorStatus(event.player)), (EntityPlayerMP) event.player);
        }
    }

    @SubscribeEvent
    public void onPlayerLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        EntityPlayer player = event.player;
        String username = player.getGameProfile().getName();
        ItemFocusSmelt.playerData.remove(username);
        ItemFocusHeal.playerHealData.remove(username);

        ItemGemChest.playersWithFlight.remove(username + ":false");
        ItemGemChest.playersWithFlight.remove(username + ":true");

        ItemGemBoots.playersWith1Step.remove(username);

    }
}
