/*
 * Copyright (c) 2022. Katrina Knight
 */

package com.nekokittygames.thaumictinkerer.common.tileentity;

import com.nekokittygames.thaumictinkerer.ThaumicTinkerer;
import com.nekokittygames.thaumictinkerer.api.MobAspect;
import com.nekokittygames.thaumictinkerer.api.MobAspects;
import com.nekokittygames.thaumictinkerer.common.items.ItemMobAspect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.client.fx.FXDispatcher;
import thaumcraft.common.tiles.crafting.TilePedestal;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TileEntitySummon extends TileEntityThaumicTinkerer implements ITickable {

    private int ticksTillNext=150;
    private int currentTicks=0;
    List<TilePedestal> pedestals=new ArrayList<>();
    @Override
    public boolean respondsToPulses() {
        return false;
    }


    AspectList getAspects() {
        AspectList list=new AspectList();
        for(TilePedestal pedestal : pedestals) {
            Aspect aspect=ItemMobAspect.getAspectType(pedestal.getStackInSlot(0));
            if(aspect!=null) {
                list.add(aspect,1);
            }
        }
        return list;
    }

    List<TilePedestal> getPedastels(){
        ArrayList<TilePedestal> pedastels=new ArrayList<>();
        for (int radius = 1; radius < 6; radius++) {
            for (int x = pos.getX() - radius; x < pos.getX() + radius; x++) {
                for (int z = pos.getZ() - radius; z < pos.getZ() + radius; z++) {
                    TileEntity tile = world.getTileEntity(new BlockPos(x, pos.getY(), z));
                    if (!pedastels.contains(tile) && tile instanceof TilePedestal && ((TilePedestal) tile).getStackInSlot(0) != null && ((TilePedestal) tile).getStackInSlot(0).getItem() instanceof ItemMobAspect) {
                            pedastels.add((TilePedestal) tile);
                    }
                }
            }
        }
        return pedastels;
    }

    @Override
    public void update() {
        currentTicks++;
        if(300-currentTicks==ticksTillNext) {
            ticksTillNext= (int) Math.floor(ticksTillNext/2.0f);
            if(ticksTillNext<=1) {
                ticksTillNext=150;
                currentTicks=0;
            }
            for(int i=0;i<pedestals.size();i++) {
                for (int j = 0; j < pedestals.size(); j++) {
                    for (int k = 0; k < pedestals.size(); k++) {
                        TilePedestal ped1 = (TilePedestal) pedestals.get(i);
                        TilePedestal ped2 = (TilePedestal) pedestals.get(j);
                        TilePedestal ped3 = (TilePedestal) pedestals.get(k);
                        if ((ped1 != ped2) && (ped2 != ped3) && (ped1 != ped3)) {
                            ShowSparks(ped1);
                            ShowSparks(ped2);
                            ShowSparks(ped3);
                        }
                    }
                }
            }
        }
        if(currentTicks==0){
            if(world.getRedstonePowerFromNeighbors(pos)>0)
                return;
            pedestals=getPedastels();
            AspectList aspects=getAspects();
            MobAspect aspect= MobAspects.getByAspects(aspects);
            if(aspect!=null) {
                if (!world.isRemote) {
                    ResourceLocation entityToSpawn=aspect.getEntityName();
                    ThaumicTinkerer.logger.info("Spawning a "+entityToSpawn.toString());
                    spawnMob(entityToSpawn);
                    FXDispatcher.INSTANCE.burst(pos.getX()+ .5,pos.getY()+1,pos.getZ()+ .5,20f);
                    for(TilePedestal pedastel : pedestals) {
                        FXDispatcher.INSTANCE.drawSlash(pedastel.getPos().getX()+.5,pos.getY()+1,pos.getZ()+.5,pedastel.getPos().getX()+1,pos.getY()+2,pos.getZ()+1,60);
                        pedastel.setInventorySlotContentsFromInfusion(0,ItemStack.EMPTY);
                    }
                }
            }

        }
    }

    private void spawnMob(ResourceLocation entityToSpawn) {
        Entity spawn= EntityList.createEntityByIDFromName(entityToSpawn,world);
        if(spawn!=null) {
            spawn.setLocationAndAngles(pos.getX() + .5, pos.getY() + 1, pos.getZ() + .5, 0, 0);
            world.spawnEntity(spawn);
            ((EntityLiving) spawn).onInitialSpawn(world.getDifficultyForLocation(pos), null);
            ((EntityLiving) spawn).playLivingSound();
        }
    }

    private void ShowSparks(TilePedestal pedastal) {
        ItemStack stack= pedastal.getStackInSlot(0);
        Aspect aspect=ItemMobAspect.getAspectType(stack);
        if(aspect!=null) {
            Color color = new Color(aspect.getColor());



            FXDispatcher.INSTANCE.arcLightning(
                    pos.getX()+0.5f, pos.getY(), pos.getZ()+0.5f,
                    pedastal.getPos().getX()+0.5f, pedastal.getPos().getY()+1, pedastal.getPos().getZ()+0.5f, color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, 0.1f);
        }
    }

}
