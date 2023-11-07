package com.nekokittygames.thaumictinkerer.common.utils;


import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;

public abstract class ContainerPlayerInv extends Container {

    InventoryPlayer playerInv;

    public ContainerPlayerInv(InventoryPlayer playerInv) {
        this.playerInv = playerInv;
    }

    public void initPlayerInv() {
        int ys = getInvYStart();
        int xs = getInvXStart();

        for (int x = 0; x < 3; ++x)
            for (int y = 0; y < 9; ++y)
                addSlotToContainer(new Slot(playerInv, y + x * 9 + 9, xs + y * 18, ys + x * 18));

        for (int x = 0; x < 9; ++x)
            addSlotToContainer(new Slot(playerInv, x, xs + x * 18, ys + 58));
    }

    public int getInvYStart() {
        return 84;
    }

    public int getInvXStart() {
        return 8;
    }
}