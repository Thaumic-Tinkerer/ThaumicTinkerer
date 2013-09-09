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
 * File Created @ [9 Sep 2013, 16:57:06 (GMT)]
 */
package vazkii.tinkerer.common.network;

import java.io.Serializable;

import net.minecraft.network.INetworkManager;
import cpw.mods.fml.common.network.Player;

public interface IPacket extends Serializable {

	public void handle(INetworkManager manager, Player player);
}
