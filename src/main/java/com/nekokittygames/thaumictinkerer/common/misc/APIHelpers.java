package com.nekokittygames.thaumictinkerer.common.misc;

import com.mojang.authlib.GameProfile;
import com.nekokittygames.thaumictinkerer.api.ThaumicTinkererAPI;
import com.nekokittygames.thaumictinkerer.common.libs.LibMisc;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;

import java.lang.ref.WeakReference;

public class APIHelpers {

    public static boolean canDislocateBlock(World world, Block block, BlockPos pos)
    {
        //
        if(world.getBlockState(pos).getBlockHardness(world,pos) != -1 && ThaumicTinkererAPI.getDislocationBlacklist().stream().noneMatch(s -> s.equalsIgnoreCase(block.getClass().getName())))
        {
            MinecraftServer worldServer = FMLCommonHandler.instance().getMinecraftServerInstance();
            return world.isBlockModifiable(FakePlayerUtils.get(worldServer.getWorld(world.provider.getDimension()), new GameProfile(LibMisc.MOD_UUID, LibMisc.MOD_F_NAME)),pos);
        }
        return false;
    }
}
