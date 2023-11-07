package com.nekokittygames.thaumictinkerer.common.compat.Tconstruct.fluid;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;


public class TTFluid extends Fluid{

    int mapColor = 0xFFFFFFFF;
    static float overlayAlpha = 0.2F;
    static SoundEvent emptySound = SoundEvents.ITEM_BUCKET_EMPTY;
    static SoundEvent fillSound = SoundEvents.ITEM_BUCKET_FILL;
    static Material material = Material.WATER;

    public TTFluid(String fluidName, ResourceLocation still, ResourceLocation flowing){
        super(fluidName, still, flowing);
        this.block = null;
        FluidRegistry.registerFluid(this);
        FluidRegistry.addBucketForFluid(this);
    }

    @Override
    public Fluid setBlock(Block block){
        return null;
    }

    @Override
    public int getColor(){
        return mapColor;
    }

    public TTFluid setColor(int parColor){
        mapColor = parColor;
        return this;
    }

    public float getAlpha(){
        return overlayAlpha;
    }

    public TTFluid setAlpha(float parOverlayAlpha){
        overlayAlpha = parOverlayAlpha;
        return this;
    }

    @Override
    public TTFluid setEmptySound(SoundEvent parSound){
        emptySound = parSound;
        return this;
    }

    @Override
    public SoundEvent getEmptySound(){
        return emptySound;
    }

    @Override
    public TTFluid setFillSound(SoundEvent parSound){
        fillSound = parSound;
        return this;
    }

    @Override
    public SoundEvent getFillSound(){
        return fillSound;
    }

    public TTFluid setMaterial(Material parMaterial){
        material = parMaterial;
        return this;
    }

    public Material getMaterial(){
        return material;
    }
}