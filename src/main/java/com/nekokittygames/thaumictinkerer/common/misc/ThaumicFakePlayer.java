package com.nekokittygames.thaumictinkerer.common.misc;

import com.mojang.authlib.GameProfile;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityCommandBlock;
import net.minecraft.util.NonNullList;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayer;


// Fake player a mix of Lemonszz and Shadows-of-Fire
public class ThaumicFakePlayer extends FakePlayer {


    public ThaumicFakePlayer(WorldServer world, GameProfile name) {
        super(world, name);
    }

    @Override
    public void displayGUIChest(IInventory chestInventory){}
    @Override
    public void displayGuiCommandBlock(TileEntityCommandBlock commandBlock){}
    @Override
    public void sendAllContents(Container containerToSend, NonNullList<ItemStack> itemsList) { }

    @Override
    public float getCooledAttackStrength(float adjustTicks)
    {
        return 1;
    }

    @Override
    public float getEyeHeight()
    {
        return 0;
    }
}
