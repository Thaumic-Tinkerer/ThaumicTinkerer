package com.nekokittygames.thaumictinkerer.common.misc;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.EnumPacketDirection;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.*;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.common.FMLCommonHandler;

import javax.annotation.Nonnull;
import java.util.Set;

public class FakeNetHandler extends NetHandlerPlayServer {
    FakeNetHandler(EntityPlayerMP p_i1530_3_) {
        super(FMLCommonHandler.instance().getMinecraftServerInstance(), new NetworkManager(EnumPacketDirection.CLIENTBOUND), p_i1530_3_);
    }


    @Override
    public @Nonnull
    NetworkManager getNetworkManager() {

        return super.netManager;
    }

    @Override
    public void processInput(@Nonnull CPacketInput p_147358_1_) {
        // Empty
    }

    @Override
    public void processPlayer(@Nonnull CPacketPlayer p_147347_1_) {
        // Empty
    }

    @Override
    public void setPlayerLocation(double p_147364_1_, double p_147364_3_, double p_147364_5_, float p_147364_7_, float p_147364_8_) {
        // Empty
    }

    @Override
    public void processPlayerDigging(@Nonnull CPacketPlayerDigging p_147345_1_) {
        // Empty
    }

    @Override
    public void onDisconnect(@Nonnull ITextComponent p_147231_1_) {
        // Empty
    }

    @Override
    public void sendPacket(@Nonnull Packet<?> p_147359_1_) {
        // Empty
    }

    @Override
    public void processHeldItemChange(@Nonnull CPacketHeldItemChange p_147355_1_) {
        // Empty
    }

    @Override
    public void processChatMessage(@Nonnull CPacketChatMessage p_147354_1_) {
        // Empty
    }

    @Override
    public void handleAnimation(@Nonnull CPacketAnimation packetIn) {
        // Empty

    }

    @Override
    public void processEntityAction(@Nonnull CPacketEntityAction p_147357_1_) {
        // Empty
    }

    @Override
    public void processUseEntity(@Nonnull CPacketUseEntity p_147340_1_) {
        // Empty
    }

    @Override
    public void processClientStatus(@Nonnull CPacketClientStatus p_147342_1_) {
        // Empty
    }

    @Override
    public void processCloseWindow(@Nonnull CPacketCloseWindow p_147356_1_) {
        // Empty
    }

    @Override
    public void processClickWindow(@Nonnull CPacketClickWindow p_147351_1_) {
        // Empty
    }

    @Override
    public void processEnchantItem(@Nonnull CPacketEnchantItem p_147338_1_) {
        // Empty
    }

    @Override
    public void processCreativeInventoryAction(@Nonnull CPacketCreativeInventoryAction p_147344_1_) {
        // Empty
    }

    @Override
    public void processConfirmTransaction(@Nonnull CPacketConfirmTransaction p_147339_1_) {
        // Empty
    }

    @Override
    public void processUpdateSign(@Nonnull CPacketUpdateSign p_147343_1_) {
        // Empty
    }

    @Override
    public void processKeepAlive(@Nonnull CPacketKeepAlive p_147353_1_) {
        // Empty
    }

    @Override
    public void processPlayerAbilities(@Nonnull CPacketPlayerAbilities p_147348_1_) {
        // Empty
    }

    @Override
    public void processTabComplete(@Nonnull CPacketTabComplete p_147341_1_) {
        // Empty
    }

    @Override
    public void processClientSettings(@Nonnull CPacketClientSettings p_147352_1_) {
        // Empty
    }

    @Override
    public void handleSpectate(@Nonnull CPacketSpectate packetIn) {
        // Empty
    }

    @Override
    public void handleResourcePackStatus(@Nonnull CPacketResourcePackStatus packetIn) {
        // Empty
    }

    @Override
    public void update() {
        // Empty
    }

    @Override
    public void disconnect(@Nonnull ITextComponent textComponent) {
        // Empty
    }

    @Override
    public void processVehicleMove(@Nonnull CPacketVehicleMove packetIn) {
        // Empty
    }

    @Override
    public void processConfirmTeleport(@Nonnull CPacketConfirmTeleport packetIn) {
        // Empty
    }

    @Override
    public void setPlayerLocation(double x, double y, double z, float yaw, float pitch, @Nonnull Set<SPacketPlayerPosLook.EnumFlags> relativeSet) {
        // Empty
    }

    @Override
    public void processTryUseItemOnBlock(@Nonnull CPacketPlayerTryUseItemOnBlock packetIn) {
        // Empty
    }

    @Override
    public void processTryUseItem(@Nonnull CPacketPlayerTryUseItem packetIn) {
        // Empty
    }

    @Override
    public void processSteerBoat(@Nonnull CPacketSteerBoat packetIn) {
        // Empty
    }

    @Override
    public void processCustomPayload(@Nonnull CPacketCustomPayload packetIn) {
        // Empty
    }

}
