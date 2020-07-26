package com.nekokittygames.thaumictinkerer.common.packets;

import com.nekokittygames.thaumictinkerer.common.tileentity.TileEntityEnchanter;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.UUID;

public class PacketStartEnchant implements IMessage {


    private BlockPos pos;
    private UUID playerID;

    public PacketStartEnchant() {

    }

    public PacketStartEnchant(TileEntityEnchanter enchanter, EntityPlayer player) {
        this.pos = enchanter.getPos();
        this.playerID = player.getUniqueID();
    }

    public PacketStartEnchant(BlockPos pos, EntityPlayer player) {
        this.pos = pos;
        this.playerID = player.getUniqueID();
    }

    public UUID getPlayerID() {
        return playerID;
    }

    public void setPlayerID(UUID playerID) {
        this.playerID = playerID;
    }

    public BlockPos getPos() {
        return pos;
    }

    public void setPos(BlockPos pos) {
        this.pos = pos;
    }

    @Override
    public void fromBytes(ByteBuf byteBuf) {
        pos = BlockPos.fromLong(byteBuf.readLong());
        playerID = new UUID(byteBuf.readLong(), byteBuf.readLong());
    }

    @Override
    public void toBytes(ByteBuf byteBuf) {
        byteBuf.writeLong(pos.toLong());
        byteBuf.writeLong(playerID.getMostSignificantBits());
        byteBuf.writeLong(playerID.getLeastSignificantBits());
    }

    public static class Handler implements IMessageHandler<PacketStartEnchant, IMessage> {

        @Override
        public IMessage onMessage(PacketStartEnchant packetAddEnchant, MessageContext messageContext) {
            FMLCommonHandler.instance().getWorldThread(messageContext.netHandler).addScheduledTask(() -> handle(packetAddEnchant, messageContext));
            return null;
        }

        private void handle(PacketStartEnchant packetAddEnchant, MessageContext ctx) {
            EntityPlayerMP playerEntity = ctx.getServerHandler().player;
            World world = playerEntity.getEntityWorld();
            if (world.isBlockLoaded(packetAddEnchant.getPos())) {
                TileEntity te = world.getTileEntity(packetAddEnchant.getPos());
                if (te instanceof TileEntityEnchanter) {
                    TileEntityEnchanter enchanter = (TileEntityEnchanter) te;
                    EntityPlayer player = world.getPlayerEntityByUUID(packetAddEnchant.playerID);
                    enchanter.setCheckSurroundings(true);
                    if(enchanter.checkLocation()) {
                        if (player != null) {
                            if (!enchanter.playerHasIngredients(enchanter.getEnchantmentCost(), player))
                                return;
                            enchanter.takeIngredients(enchanter.getEnchantmentCost(), player);
                        }
                        //if(enchanter.playerHasIngredients())
                        enchanter.setWorking(true);
                    }
                    else
                    {
                        if(player!=null)
                            player.sendMessage(new TextComponentTranslation("thaumictinkerer.enchanter.badmb"));
                    }
                }
            }
        }
    }
}
