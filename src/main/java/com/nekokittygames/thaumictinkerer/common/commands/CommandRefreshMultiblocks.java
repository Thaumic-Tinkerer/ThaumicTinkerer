package com.nekokittygames.thaumictinkerer.common.commands;

import com.google.common.collect.Lists;
import com.nekokittygames.thaumictinkerer.common.libs.LibMisc;
import com.nekokittygames.thaumictinkerer.common.multiblocks.Multiblock;
import com.nekokittygames.thaumictinkerer.common.multiblocks.MultiblockManager;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;

public class CommandRefreshMultiblocks extends CommandBase {
    private final List<String> aliases;
    public CommandRefreshMultiblocks(){
        aliases = Lists.newArrayList(LibMisc.MOD_ID, "REFRESHMULTI", "refreshmulti");
    }
    @Override
    public String getName() {
        return "refreshmulti";
    }

    @Override
    public List<String> getAliases() {
        return aliases;
    }

    @Override
    public String getUsage(ICommandSender iCommandSender) {
        return "refreshmulti";
    }

    @Override
    public void execute(MinecraftServer minecraftServer, ICommandSender iCommandSender, String[] strings) throws CommandException {
        MultiblockManager.clearMultiblocks();
        try {
            MultiblockManager.initMultiblocks();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return true;
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        return Collections.emptyList();
    }
}
