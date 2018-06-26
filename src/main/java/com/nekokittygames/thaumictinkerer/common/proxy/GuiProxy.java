package com.nekokittygames.thaumictinkerer.common.proxy;

import com.nekokittygames.thaumictinkerer.client.gui.GuiMobMagnet;
import com.nekokittygames.thaumictinkerer.common.containers.MagnetContainer;
import com.nekokittygames.thaumictinkerer.common.tileentity.TileEntityMobMagnet;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import javax.annotation.Nullable;

public class GuiProxy implements IGuiHandler {
    @Nullable
    @Override
    public Object getServerGuiElement(int i, EntityPlayer entityPlayer, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof TileEntityMobMagnet) {
            return new MagnetContainer(entityPlayer.inventory, (TileEntityMobMagnet) te);
        }
        return null;
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int i, EntityPlayer entityPlayer, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof TileEntityMobMagnet) {
            TileEntityMobMagnet magnet= (TileEntityMobMagnet) te;
            return new GuiMobMagnet(magnet,new MagnetContainer(entityPlayer.inventory,magnet));
        }
        return null;
    }
}
