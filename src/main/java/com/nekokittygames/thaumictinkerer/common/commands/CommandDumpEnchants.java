package com.nekokittygames.thaumictinkerer.common.commands;

import com.google.common.collect.Lists;
import com.nekokittygames.thaumictinkerer.common.libs.LibMisc;
import com.nekokittygames.thaumictinkerer.common.multiblocks.MultiblockManager;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

import javax.annotation.Nullable;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;

public class CommandDumpEnchants extends CommandBase {
    private final List<String> aliases;
    public CommandDumpEnchants(){
        aliases = Lists.newArrayList(LibMisc.MOD_ID, "DUMPENCHANTS", "dumpenchants");
    }
    @Override
    public String getName() {
        return "dumpenchants";
    }

    @Override
    public List<String> getAliases() {
        return aliases;
    }

    @Override
    public String getUsage(ICommandSender iCommandSender) {
        return "dumpenchants";
    }

    @Override
    public void execute(MinecraftServer minecraftServer, ICommandSender iCommandSender, String[] strings) throws CommandException {
        for(Enchantment enchantment:Enchantment.REGISTRY)
        {
            iCommandSender.sendMessage(new TextComponentString("Enchantment ID: "+Enchantment.REGISTRY.getIDForObject(enchantment)));
            iCommandSender.sendMessage(new TextComponentString("Enchantment location: "+Enchantment.REGISTRY.getNameForObject(enchantment)));
            iCommandSender.sendMessage(new TextComponentString("Enchantment name: "+enchantment.getName()));
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
