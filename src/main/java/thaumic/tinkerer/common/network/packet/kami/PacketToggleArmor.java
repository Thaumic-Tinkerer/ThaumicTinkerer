package thaumic.tinkerer.common.network.packet.kami;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import thaumic.tinkerer.client.core.proxy.TTClientProxy;
import thaumic.tinkerer.common.ThaumicTinkerer;

/**
 * Created by Katrina on 28/02/14.
 */
public class PacketToggleArmor implements IMessage, IMessageHandler<PacketToggleArmor, IMessage> {
	private static final long serialVersionUID = -1247633508013055777L;
	public boolean armorStatus;

	public PacketToggleArmor(boolean status) {
		armorStatus = status;
	}

	public PacketToggleArmor() {
		super();
	}

	@Override
	public void fromBytes(ByteBuf byteBuf) {
		armorStatus = byteBuf.getBoolean(0);
	}

	@Override
	public void toBytes(ByteBuf byteBuf) {
		byteBuf.writeBoolean(armorStatus);
	}

	@Override
	public IMessage onMessage(PacketToggleArmor message, MessageContext ctx) {
		EntityPlayer player;
		if (ctx.side.isClient())
			player = TTClientProxy.getPlayer();
		else {
			player = ctx.getServerHandler().playerEntity;
		}
		if (player instanceof EntityPlayer) {

			ThaumicTinkerer.proxy.setArmor(player, message.armorStatus);

		}
		return null;
	}
}
