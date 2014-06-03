package vazkii.tinkerer.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import net.minecraft.entity.player.EntityPlayer;

/**
 * Created by Katrina on 03/06/14.
 */
public abstract class AbstractPacket implements IMessage {
    public abstract void handleClientSide(EntityPlayer entityPlayer);
    public abstract void handleServerSide(EntityPlayer entityPlayer);
}
