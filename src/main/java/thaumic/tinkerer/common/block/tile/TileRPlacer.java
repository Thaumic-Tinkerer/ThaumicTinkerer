package thaumic.tinkerer.common.block.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.common.util.ForgeDirection;
import thaumic.tinkerer.common.lib.LibBlockNames;

/**
 * Created by nekosune on 30/06/14.
 */
public class TileRPlacer extends TileCamo implements IInventory{
    private static final String TAG_ORIENTATION = "orientation";
    private static final String TAG_BLOCKS = "blocks";
    public int orientation;
    ItemStack[] inventorySlots = new ItemStack[1];
    public int blocks=1;

    @Override
    public void readCustomNBT(NBTTagCompound cmp) {
        super.readCustomNBT(cmp);

        orientation = cmp.getInteger(TAG_ORIENTATION);
        blocks=cmp.getInteger(TAG_BLOCKS);
        NBTTagList var2 = cmp.getTagList("Items", Constants.NBT.TAG_COMPOUND);
        inventorySlots = new ItemStack[getSizeInventory()];
        for (int var3 = 0; var3 < var2.tagCount(); ++var3) {
            NBTTagCompound var4 = var2.getCompoundTagAt(var3);
            byte var5 = var4.getByte("Slot");
            if (var5 >= 0 && var5 < inventorySlots.length)
                inventorySlots[var5] = ItemStack.loadItemStackFromNBT(var4);
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound par1nbtTagCompound) {
        super.writeToNBT(par1nbtTagCompound);
    }

    @Override
    public void readFromNBT(NBTTagCompound par1nbtTagCompound) {
        super.readFromNBT(par1nbtTagCompound);
    }

    @Override
    public void writeCustomNBT(NBTTagCompound cmp) {
        super.writeCustomNBT(cmp);

        cmp.setInteger(TAG_ORIENTATION, orientation);
        cmp.setInteger(TAG_BLOCKS,blocks);
        NBTTagList var2 = new NBTTagList();
        for (int var3 = 0; var3 < inventorySlots.length; ++var3) {
            if (inventorySlots[var3] != null) {
                NBTTagCompound var4 = new NBTTagCompound();
                var4.setByte("Slot", (byte) var3);
                inventorySlots[var3].writeToNBT(var4);
                var2.appendTag(var4);
            }
        }
        cmp.setTag("Items", var2);
    }

    @Override
    public int getSizeInventory() {
        return inventorySlots.length;
    }

    @Override
    public ItemStack getStackInSlot(int var1) {
        return inventorySlots[var1];
    }

    @Override
    public ItemStack decrStackSize(int par1, int par2) {
        if (inventorySlots[par1] != null) {
            ItemStack stackAt;

            if (inventorySlots[par1].stackSize <= par2) {
                stackAt = inventorySlots[par1];
                inventorySlots[par1] = null;

                if (!worldObj.isRemote)
                    worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);

                return stackAt;
            } else {
                stackAt = inventorySlots[par1].splitStack(par2);

                if (inventorySlots[par1].stackSize == 0)
                    inventorySlots[par1] = null;

                if (!worldObj.isRemote)
                    worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);

                return stackAt;
            }
        }

        return null;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int i) {
        return getStackInSlot(i);
    }

    @Override
    public void setInventorySlotContents(int i, ItemStack itemstack) {
        inventorySlots[i] = itemstack;

        if (!worldObj.isRemote)
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
    }

    @Override
    public String getInventoryName() {
        return LibBlockNames.REMOTE_PLACER;
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer entityplayer) {
        return worldObj.getTileEntity(xCoord, yCoord, zCoord) == this && entityplayer.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) <= 64;
    }

    @Override
    public void openInventory() {

    }

    @Override
    public void closeInventory() {

    }

    @Override
    public boolean isItemValidForSlot(int var1, ItemStack var2) {
        return var2.getItem() instanceof ItemBlock;
    }
    @Override
    public S35PacketUpdateTileEntity getDescriptionPacket() {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        writeCustomNBT(nbttagcompound);
        return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, -999, nbttagcompound);
    }

    @Override
    public void onDataPacket(NetworkManager manager, S35PacketUpdateTileEntity packet) {
        super.onDataPacket(manager, packet);
        readCustomNBT(packet.func_148857_g());
    }

    public void receiveRedstonePulse() {
        if(worldObj.isRemote)
            return;
        if(this.inventorySlots[0]!=null)
        {
            int x,y,z=0;
            x=0;
            y=0;
            switch(orientation)
            {
                case 0:
                    x=this.xCoord;
                    y=this.yCoord-this.blocks;
                    z=this.zCoord;
                    break;
                case 1:
                    x=this.xCoord;
                    y=this.yCoord+this.blocks;
                    z=this.zCoord;
                    break;
                case 2:
                    x=this.xCoord;
                    y=this.yCoord;
                    z=this.zCoord-this.blocks;
                    break;
                case 3:
                    x=this.xCoord;
                    y=this.yCoord;
                    z=this.zCoord+this.blocks;
                    break;
                case 4:
                    x=this.xCoord-this.blocks;
                    y=this.yCoord;
                    z=this.zCoord;
                    break;
                case 5:
                    x=this.xCoord-this.blocks;
                    y=this.yCoord;
                    z=this.zCoord;
                    break;
            }
            //if(this.inventorySlots[0].getItem() instanceof ItemBlock) {
                if (this.worldObj.getBlock(x, y, z) == Blocks.air) {
                    //if (this.worldObj.setBlock(x, y, z, ((ItemBlock) this.inventorySlots[0].getItem()).field_150939_a, this.inventorySlots[0].getItemDamage(), 1 | 2)) {
                      //  this.decrStackSize(0, 1);
                       // markDirty();
                    //Block block = worldObj.getBlock(x, y, z);
                    boolean done=false;
                    FakePlayer player=FakePlayerFactory.getMinecraft((WorldServer)worldObj);
                    Item item=inventorySlots[0].getItem();
                    ItemStack stack=inventorySlots[0];
                    if (!done)
                        item.onItemUseFirst(stack, player, worldObj, x, y, z, ForgeDirection.OPPOSITES[orientation], 0F, 0F, 0F);
                    if (!done)
                        done = item.onItemUse(stack, player, worldObj, x, y, z, ForgeDirection.OPPOSITES[orientation], 0F, 0F, 0F);
                    if (!done) {
                        item.onItemRightClick(stack, worldObj, player);
                        done = true;
                    }

                    }
                //}
            //}

        }
    }
}
