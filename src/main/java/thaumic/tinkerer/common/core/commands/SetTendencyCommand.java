package thaumic.tinkerer.common.core.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MovingObjectPosition;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.common.lib.Utils;
import thaumic.tinkerer.common.block.tile.TileInfusedGrain;
import thaumic.tinkerer.common.core.handler.ConfigHandler;

/**
 * Created by pixlepix on 8/16/14.
 */
public class SetTendencyCommand extends CommandBase {
    @Override
    public String getCommandName() {
        return "setCropTendency";
    }

    @Override
    public String getCommandUsage(ICommandSender var1) {
        return "/setCropTendency <Aspect> <Count>";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (sender instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) sender;
            MovingObjectPosition pos = Utils.getTargetBlock(player.worldObj, player, true);
            if (player.worldObj.getTileEntity(pos.blockX, pos.blockY, pos.blockZ) instanceof TileInfusedGrain) {
                TileInfusedGrain tile = (TileInfusedGrain) player.worldObj.getTileEntity(pos.blockX, pos.blockY, pos.blockZ);
                for (Aspect a : Aspect.getPrimalAspects()) {
                    if (args[0].toUpperCase().equals(a.getName().toUpperCase())) {
                        try {
                            tile.primalTendencies.merge(a, Integer.parseInt(args[1]));
                            tile.reduceSaturatedAspects();
                        } catch (NumberFormatException e) {
                            sender.addChatMessage(new ChatComponentText("Invalid number"));
                        }
                    }
                }
            }

        }
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender par1iCommandSender) {
        return ConfigHandler.enableDebugCommands;
    }
}
