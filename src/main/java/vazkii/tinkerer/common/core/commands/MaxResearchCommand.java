package vazkii.tinkerer.common.core.commands;

import thaumcraft.api.aspects.Aspect;
import thaumcraft.common.Thaumcraft;
import vazkii.tinkerer.common.core.handler.ConfigHandler;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;

public class MaxResearchCommand extends CommandBase {

	@Override
	public String getCommandName() {
		return "maxresearch";
	}

	@Override
	public String getCommandUsage(ICommandSender icommandsender) {
		return "max research adds 99 to all aspects of the player using it";
	}

	@Override
	public void processCommand(ICommandSender icommandsender, String[] astring) {
		if(icommandsender instanceof EntityPlayer)
		{
			EntityPlayer player=(EntityPlayer)icommandsender;
		for(Aspect as:Aspect.aspects.values())
		{
			Thaumcraft.proxy.getResearchManager().completeAspect(player, as, (short) 99);
		}
		player.addChatMessage("Added 99 research to all aspects");
		}
		

	}


	@Override
	public boolean canCommandSenderUseCommand(ICommandSender par1iCommandSender) {
		if(ConfigHandler.enableDebugCommands)
			return true;
		return false;
	}

	@Override
	public int compareTo(Object o) {
		return 0;
	}

	

}
