package com.nekokittygames.thaumictinkerer.common.multiblocks;

import com.google.common.base.Predicate;
import com.nekokittygames.thaumictinkerer.common.helper.OreDictHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.BlockSnapshot;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.oredict.OreDictionary;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aura.AuraHelper;
import thaumcraft.common.entities.EntitySpecialItem;
import thaumcraft.common.lib.events.ServerEvents;
import thaumcraft.common.lib.network.PacketHandler;
import thaumcraft.common.lib.network.fx.PacketFXBlockBamf;
import thaumcraft.common.lib.utils.BlockUtils;
import thaumcraft.common.lib.utils.InventoryUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

@Mod.EventBusSubscriber
public class TTServerEvents {
    public static HashMap<Integer, LinkedBlockingQueue<TTServerEvents.VirtualSwapper>> swapList = new HashMap();
    @SubscribeEvent
    public static void worldTick(TickEvent.WorldTickEvent event) {
        if (event.side != Side.CLIENT) {
           if(event.phase!= TickEvent.Phase.START)
           {
               tickBlockSwap(event.world);
           }
        }
    }



    private static void tickBlockSwap(World world) {
        int dim = world.provider.getDimension();
        LinkedBlockingQueue<TTServerEvents.VirtualSwapper> queue = (LinkedBlockingQueue)swapList.get(dim);
        LinkedBlockingQueue<TTServerEvents.VirtualSwapper> queue2 = new LinkedBlockingQueue();
        if (queue != null) {
            while(true) {
                if (queue.isEmpty()) {
                    swapList.put(dim, queue2);
                    break;
                }

                TTServerEvents.VirtualSwapper vs = (TTServerEvents.VirtualSwapper)queue.poll();
                if (vs != null) {
                    IBlockState bs = world.getBlockState(vs.pos);
                    boolean allow = bs.getBlockHardness(world, vs.pos) >= 0.0F;
                    if (vs.source != null && vs.source instanceof IBlockState && (IBlockState)vs.source != bs ||
                            vs.source != null && vs.source instanceof Material && (Material)vs.source != bs.getMaterial() ||
                            vs.source!=null && vs.source instanceof Class<?> && (Class<?>)vs.source!=bs.getBlock().getClass() ||
                            vs.source!=null && vs.source instanceof String && !OreDictHelper.oreDictCheck(bs,(String)vs.source))  {
                        allow = false;
                    }

                    if (vs.visCost > 0.0F && AuraHelper.getVis(world, vs.pos) < vs.visCost) {
                        allow = false;
                    }

                    if (world.canMineBlockBody(vs.player, vs.pos) && allow && (vs.target == null || vs.target.isEmpty() || !vs.target.isItemEqual(new ItemStack(bs.getBlock(), 1, bs.getBlock().getMetaFromState(bs)))) && !ForgeEventFactory.onPlayerBlockPlace(vs.player, new BlockSnapshot(world, vs.pos, bs), EnumFacing.UP, EnumHand.MAIN_HAND).isCanceled() && vs.allowSwap.apply(new ServerEvents.SwapperPredicate(world, vs.player, vs.pos))) {
                        int slot;
                        if (vs.consumeTarget && vs.target != null && !vs.target.isEmpty()) {
                            slot = InventoryUtils.getPlayerSlotFor(vs.player, vs.target);
                        } else {
                            slot = 1;
                        }

                        if (vs.player.capabilities.isCreativeMode) {
                            slot = 1;
                        }

                        boolean matches = false;
                        if (vs.source instanceof Material) {
                            matches = bs.getMaterial() == (Material)vs.source;
                        }

                        if (vs.source instanceof IBlockState) {
                            matches = bs == (IBlockState)vs.source;
                        }
                        if(vs.source instanceof Class<?>) {
                            matches = bs.getBlock().getClass().equals(vs.source);
                        }
                        if(vs.source instanceof  String) {
                            matches= OreDictionary.getOres((String) vs.source).contains(new ItemStack(bs.getBlock(),1,bs.getBlock().getMetaFromState(bs)));
                        }
                        if ((vs.source == null || matches) && slot >= 0) {
                            if (!vs.player.capabilities.isCreativeMode) {
                                if (vs.consumeTarget) {
                                    vs.player.inventory.decrStackSize(slot, 1);
                                }

                                if (vs.pickup) {
                                    List<ItemStack> ret = new ArrayList();
                                    if (vs.silk && bs.getBlock().canSilkHarvest(world, vs.pos, bs, vs.player)) {
                                        ItemStack itemstack = BlockUtils.getSilkTouchDrop(bs);
                                        if (itemstack != null && !itemstack.isEmpty()) {
                                            ((List)ret).add(itemstack);
                                        }
                                    } else {
                                        ret = bs.getBlock().getDrops(world, vs.pos, bs, vs.fortune);
                                    }

                                    if (((List)ret).size() > 0) {
                                        Iterator var16 = ((List)ret).iterator();

                                        while(var16.hasNext()) {
                                            ItemStack is = (ItemStack)var16.next();
                                            if (!vs.player.inventory.addItemStackToInventory(is)) {
                                                world.spawnEntity(new EntityItem(world, (double)vs.pos.getX() + 0.5D, (double)vs.pos.getY() + 0.5D, (double)vs.pos.getZ() + 0.5D, is));
                                            }
                                        }
                                    }
                                }

                                if (vs.visCost > 0.0F) {
                                    ThaumcraftApi.internalMethods.drainVis(world, vs.pos, vs.visCost, false);
                                }
                            }

                            if (vs.target != null && !vs.target.isEmpty()) {
                                Block tb = Block.getBlockFromItem(vs.target.getItem());
                                if (tb != null && tb != Blocks.AIR) {
                                    world.setBlockState(vs.pos, tb.getStateFromMeta(vs.target.getItemDamage()), 3);
                                } else {
                                    world.setBlockToAir(vs.pos);
                                    EntitySpecialItem entityItem = new EntitySpecialItem(world, (double)vs.pos.getX() + 0.5D, (double)vs.pos.getY() + 0.1D, (double)vs.pos.getZ() + 0.5D, vs.target.copy());
                                    entityItem.motionY = 0.0D;
                                    entityItem.motionX = 0.0D;
                                    entityItem.motionZ = 0.0D;
                                    world.spawnEntity(entityItem);
                                }
                            } else {
                                world.setBlockToAir(vs.pos);
                            }
                            world.markBlocksDirtyVertical(vs.pos.getX(),vs.pos.getZ(),vs.pos.getY()-1,vs.pos.getY()+1);
                            if (vs.fx) {
                                PacketHandler.INSTANCE.sendToAllAround(new PacketFXBlockBamf(vs.pos, vs.color, true, vs.fancy, (EnumFacing)null), new NetworkRegistry.TargetPoint(world.provider.getDimension(), (double)vs.pos.getX(), (double)vs.pos.getY(), (double)vs.pos.getZ(), 32.0D));
                            }

                            if (vs.lifespan > 0) {
                                for(int xx = -1; xx <= 1; ++xx) {
                                    for(int yy = -1; yy <= 1; ++yy) {
                                        for(int zz = -1; zz <= 1; ++zz) {
                                            matches = false;
                                            if (vs.source instanceof Material) {
                                                IBlockState bb = world.getBlockState(vs.pos.add(xx, yy, zz));
                                                matches = bb.getBlock().getMaterial(bb) == vs.source;
                                            }

                                            if (vs.source instanceof IBlockState) {
                                                matches = world.getBlockState(vs.pos.add(xx, yy, zz)) == vs.source;
                                            }
                                            if(vs.source instanceof Class<?>) {
                                                IBlockState bb = world.getBlockState(vs.pos.add(xx, yy, zz));
                                                matches=bb.getBlock().getClass() == vs.source;
                                            }
                                            if(vs.source instanceof  String) {
                                                IBlockState bb = world.getBlockState(vs.pos.add(xx,yy,zz));
                                                matches= OreDictionary.getOres((String) vs.source).contains(new ItemStack(bb.getBlock(),1,bb.getBlock().getMetaFromState(bb)));
                                            }

                                            if ((xx != 0 || yy != 0 || zz != 0) && matches && BlockUtils.isBlockExposed(world, vs.pos.add(xx, yy, zz))) {
                                                queue2.offer(new TTServerEvents.VirtualSwapper(vs.pos.add(xx, yy, zz), vs.source, vs.target, vs.consumeTarget, vs.lifespan - 1, vs.player, vs.fx, vs.fancy, vs.color, vs.pickup, vs.silk, vs.fortune, vs.allowSwap, vs.visCost));
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }


    public static void addSwapper(World world, BlockPos pos, Object source, ItemStack target, boolean consumeTarget, int life, EntityPlayer player, boolean fx, boolean fancy, int color, boolean pickup, boolean silk, int fortune, Predicate<ServerEvents.SwapperPredicate> allowSwap, float visCost) {
        int dim = world.provider.getDimension();
        LinkedBlockingQueue<TTServerEvents.VirtualSwapper> queue = (LinkedBlockingQueue)swapList.get(dim);
        if (queue == null) {
            swapList.put(dim, new LinkedBlockingQueue());
            queue = (LinkedBlockingQueue)swapList.get(dim);
        }

        queue.offer(new TTServerEvents.VirtualSwapper(pos, source, target, consumeTarget, life, player, fx, fancy, color, pickup, silk, fortune, allowSwap, visCost));
        swapList.put(dim, queue);
    }

    public static class VirtualSwapper {
        int color;
        boolean fancy;
        Predicate<ServerEvents.SwapperPredicate> allowSwap;
        int lifespan = 0;
        BlockPos pos;
        Object source;
        ItemStack target;
        EntityPlayer player = null;
        boolean fx;
        boolean silk;
        boolean pickup;
        boolean consumeTarget;
        int fortune;
        float visCost;

        VirtualSwapper(BlockPos pos, Object source, ItemStack t, boolean consumeTarget, int life, EntityPlayer p, boolean fx, boolean fancy, int color, boolean pickup, boolean silk, int fortune, Predicate<ServerEvents.SwapperPredicate> allowSwap, float cost) {
            this.pos = pos;
            this.source = source;
            this.target = t;
            this.lifespan = life;
            this.player = p;
            this.consumeTarget = consumeTarget;
            this.fx = fx;
            this.fancy = fancy;
            this.allowSwap = allowSwap;
            this.silk = silk;
            this.fortune = fortune;
            this.pickup = pickup;
            this.color = color;
            this.visCost = cost;
        }
    }

    public static class SwapperPredicate {
        public World world;
        public EntityPlayer player;
        public BlockPos pos;

        public SwapperPredicate(World world, EntityPlayer player, BlockPos pos) {
            this.world = world;
            this.player = player;
            this.pos = pos;
        }
    }

    public static class BreakData {
        float strength = 0.0F;
        float durabilityCurrent = 1.0F;
        float durabilityMax = 1.0F;
        IBlockState source;
        BlockPos pos;
        EntityPlayer player = null;
        boolean fx;
        boolean silk;
        int fortune;
        float visCost;

        public BreakData(float strength, float durabilityCurrent, float durabilityMax, BlockPos pos, IBlockState source, EntityPlayer player, boolean fx, boolean silk, int fortune, float vis) {
            this.strength = strength;
            this.source = source;
            this.pos = pos;
            this.player = player;
            this.fx = fx;
            this.silk = silk;
            this.fortune = fortune;
            this.durabilityCurrent = durabilityCurrent;
            this.durabilityMax = durabilityMax;
            this.visCost = vis;
        }
    }

}
