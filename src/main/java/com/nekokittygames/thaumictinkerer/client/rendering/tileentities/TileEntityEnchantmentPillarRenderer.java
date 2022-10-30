/*
 * Copyright (c) 2020. Katrina Knight
 */

package com.nekokittygames.thaumictinkerer.client.rendering.tileentities;

import com.nekokittygames.thaumictinkerer.common.blocks.BlockEnchantmentPillar;
import com.nekokittygames.thaumictinkerer.common.blocks.ModBlocks;
import com.nekokittygames.thaumictinkerer.common.tileentity.TileEntityEnchantmentPillar;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import thaumcraft.client.fx.FXDispatcher;

/**
 * TESR for the enchantment pillar
 */
public class TileEntityEnchantmentPillarRenderer extends TileEntitySpecialRenderer<TileEntityEnchantmentPillar> {


    /**
     * Renders the enchantment pillar
     *
     * @param te           {@link TileEntityEnchantmentPillar} entity
     * @param x            xPos of the block
     * @param y            yPos of the block
     * @param z            zPos of the block
     * @param partialTicks udpate ticks
     * @param destroyStage stage of the block destruction
     * @param alpha        alpha amount of the block
     */
    @Override
    public void render(TileEntityEnchantmentPillar te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        super.render(te, x, y, z, partialTicks, destroyStage, alpha);
        IBlockState state = te.getWorld().getBlockState(te.getPos());

        if (state.getBlock() == ModBlocks.enchantment_pillar && state.getValue(BlockEnchantmentPillar.Top)) {
            BlockPos pos = te.getPos();
            World world = te.getWorld();
            float xoffset = 0.0f;
            float zoffset = 0.0f;
            switch (state.getValue(BlockEnchantmentPillar.Direction)) {
                case 0:
                    xoffset += 0.3f;
                    zoffset += 0.3f;
                    break;
                case 1:
                    xoffset += 0.3f;
                    break;
                case 2:
                    xoffset += 0.3f;
                    zoffset -= 0.3f;
                    break;
                case 3:
                    zoffset -= 0.3f;
                    break;
                case 4:
                    xoffset -= 0.3f;
                    zoffset -= 0.3f;
                    break;
                case 5:
                    xoffset -= 0.3f;
                    break;
                case 6:
                    xoffset -= 0.3f;
                    zoffset += 0.3f;
                    break;
                case 7:
                    zoffset += 0.3f;
                    break;
                default:
                    break;
            }
            FXDispatcher.INSTANCE.drawNitorFlames((double) ((float) pos.getX() + 0.5F + xoffset) + world.rand.nextGaussian() * 0.025D, (double) ((float) pos.getY() + 1.15F) + world.rand.nextGaussian() * 0.025D, (double) ((float) pos.getZ() + 0.5F + zoffset) + world.rand.nextGaussian() * 0.025D, world.rand.nextGaussian() * 0.0025D, (double) world.rand.nextFloat() * 0.06D, world.rand.nextGaussian() * 0.0025D, MapColor.GOLD.colorValue, 0);
        }
    }
}
