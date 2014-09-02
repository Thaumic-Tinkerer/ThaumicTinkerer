package thaumic.tinkerer.common.core.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.common.Thaumcraft;
import thaumic.tinkerer.common.core.handler.ConfigHandler;

public class MaxResearchCommand extends CommandBase {

	@Override
	public String getCommandName() {
		return "maxresearch";
	}

	@Override
	public String getCommandUsage(ICommandSender icommandsender) {
        return "Adds 99 to all aspects of the player using it";
    }

	@Override
	public void processCommand(ICommandSender icommandsender, String[] astring) {
		if (icommandsender instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) icommandsender;
			for (Aspect as : Aspect.aspects.values()) {
				Thaumcraft.proxy.getResearchManager().completeAspect(player, as, (short) 99);
			}
			player.addChatComponentMessage(new ChatComponentText("Added 99 research to all aspects"));
		}

	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender par1iCommandSender) {
		return ConfigHandler.enableDebugCommands;
	}

	@Override
	public int compareTo(Object o) {
		return 0;
	}

}
