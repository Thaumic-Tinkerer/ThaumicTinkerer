package com.nekokittygames.thaumictinkerer.common.misc;

import com.mojang.authlib.GameProfile;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntityCommandBlock;
import net.minecraft.util.NonNullList;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;


// Fake player a mix of Lemonszz and Shadows-of-Fire
public class ThaumicFakePlayer extends FakePlayer {

    private final @Nonnull
    WorldServer originalWorld;

    public ThaumicFakePlayer(WorldServer world, GameProfile name) {
        super(world, name);
        this.connection = new FakeNetHandler(this);
        this.originalWorld = super.getServerWorld();
    }

    @Override
    public void displayGUIChest(IInventory chestInventory) {
    }

    @Override
    public void displayGuiCommandBlock(TileEntityCommandBlock commandBlock) {
    }

    @Override
    public void sendAllContents(Container containerToSend, NonNullList<ItemStack> itemsList) {
    }

    @Override
    public float getCooledAttackStrength(float adjustTicks) {
        return 1;
    }

    @Override
    public void onUpdate() {
        this.interactionManager.updateBlockRemoving();
    }

    @Override
    public float getEyeHeight() {
        return 0;
    }

    @Override
    protected void onNewPotionEffect(@Nonnull PotionEffect p_70670_1_) {
    }

    @Override
    protected void onChangedPotionEffect(@Nonnull PotionEffect p_70695_1_, boolean p_70695_2_) {
    }

    @Override
    protected void onFinishedPotionEffect(@Nonnull PotionEffect p_70688_1_) {
    }

    @Override
    protected void playEquipSound(@Nullable ItemStack stack) {
    }

    @Override
    public WorldServer getServerWorld() {
        return originalWorld;
    }
}
