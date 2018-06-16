package com.nekokittygames.thaumictinkerer.common.tileentity;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistry;

import javax.annotation.Nullable;

public abstract class TileEntityCamoflage extends TileEntity {

    private IBlockState blockCopy;

    public IBlockState getBlockCopy() {
        return blockCopy;
    }

    public void setBlockCopy(IBlockState blockCopy) {
        this.blockCopy = blockCopy;
    }


    @Override
    public void readFromNBT(NBTTagCompound compound) {
        readExtraNBT(compound);
        super.readFromNBT(compound);
    }

    private void readExtraNBT(NBTTagCompound compound) {
        if(compound.hasKey("blockName")) {
            Block blk = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(compound.getString("blockName")));
            blockCopy=blk.getStateFromMeta(compound.getInteger("blockMeta"));
        }
    }
    public void sendUpdates()
    {
        world.markBlockRangeForRenderUpdate(pos, pos);
        world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
        world.scheduleBlockUpdate(pos,this.getBlockType(),0,0);
        markDirty();
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        NBTTagCompound cmp=super.getUpdateTag();
        writeExtraNBT(cmp);
        return cmp;
    }

    @Override
    public void handleUpdateTag(NBTTagCompound tag) {
        super.handleUpdateTag(tag);
        readExtraNBT(tag);
    }

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(this.pos, 5, this.getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        super.onDataPacket(net, pkt);
        readExtraNBT(pkt.getNbtCompound());
        sendUpdates();
    }
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        writeExtraNBT(compound);
        return super.writeToNBT(compound);
    }

    private void writeExtraNBT(NBTTagCompound compound) {
        if(blockCopy!=null) {
            compound.setString("blockName", blockCopy.getBlock().getRegistryName().toString());
            compound.setInteger("blockMeta", blockCopy.getBlock().getMetaFromState(blockCopy));
        }

    }
}
