package com.nekokittygames.thaumictinkerer.common.misc;

import com.google.common.base.Predicates;
import com.google.common.collect.Maps;
import com.mojang.authlib.GameProfile;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;


// Fake Player utils, rewritten thanks to code from https://github.com/Shadows-of-Fire/ClickMachine/ with permission from Shadows of Fire.
@Mod.EventBusSubscriber
public class FakePlayerUtils {
    private static Map<GameProfile, ThaumicFakePlayer> fakePlayers = Maps.newHashMap();

    public static ThaumicFakePlayer get(WorldServer world, GameProfile username) {
        if (!fakePlayers.containsKey(username)) {
            ThaumicFakePlayer fakePlayer = new ThaumicFakePlayer(world, username);
            fakePlayers.put(username, fakePlayer);
        }

        return fakePlayers.get(username);
    }

    /**
     * Sets up for a fake player to be usable to right click things.  This player will be put at the center of the using side.
     *
     * @param player    The player.
     * @param pos       The position of the using tile entity.
     * @param direction The direction to use in.
     * @param toHold    The stack the player will be using.  Should probably come from an ItemStackHandler or similar.
     */
    public static void setupFakePlayerForUse(ThaumicFakePlayer player, BlockPos pos, EnumFacing direction, ItemStack toHold, boolean sneaking) {
        player.inventory.mainInventory.set(player.inventory.currentItem, toHold);
        float pitch = direction == EnumFacing.UP ? -90 : direction == EnumFacing.DOWN ? 90 : 45;
        float yaw = direction == EnumFacing.SOUTH ? 0 : direction == EnumFacing.WEST ? 90 : direction == EnumFacing.NORTH ? 180 : -90;
        Vec3i sideVec = direction.getDirectionVec();
        EnumFacing.Axis a = direction.getAxis();
        EnumFacing.AxisDirection ad = direction.getAxisDirection();


        double x = a == EnumFacing.Axis.X && ad == EnumFacing.AxisDirection.NEGATIVE ? -.5 : .5 + sideVec.getX() / 1.9D;
        double y = sideVec.getY() / 1.9D;
        double z = a == EnumFacing.Axis.Z && ad == EnumFacing.AxisDirection.NEGATIVE ? -.5 : .5 + sideVec.getZ() / 1.9D;
        player.setLocationAndAngles(pos.getX() + x, pos.getY() + y, pos.getZ() + z, yaw, pitch);
        if (!toHold.isEmpty())
            player.getAttributeMap().applyAttributeModifiers(toHold.getAttributeModifiers(EntityEquipmentSlot.MAINHAND));
        player.setSneaking(sneaking);
    }


    /**
     * Cleans up the fake player after use.
     *
     * @param player      The player.
     * @param resultStack The stack that was returned from right/leftClickInDirection.
     * @param oldStack    The previous stack, from before use.
     */
    public static void cleanupFakePlayerFromUse(ThaumicFakePlayer player, ItemStack resultStack, ItemStack oldStack, Consumer<ItemStack> stackCallback) {
        if (!oldStack.isEmpty())
            player.getAttributeMap().removeAttributeModifiers(oldStack.getAttributeModifiers(EntityEquipmentSlot.MAINHAND));
        player.inventory.mainInventory.set(player.inventory.currentItem, ItemStack.EMPTY);
        stackCallback.accept(resultStack);
        if (!player.inventory.isEmpty()) player.inventory.dropAllItems();
        player.setSneaking(false);
    }


    /**
     * Uses whatever the player happens to be holding in the given direction.
     *
     * @param player      The player.
     * @param world       The world of the calling tile entity.  It may be a bad idea to use {@link FakePlayer#getEntityWorld()}.
     * @param pos         The pos of the calling tile entity.
     * @param side        The direction to use in.
     * @param sourceState The state of the calling tile entity, so we don't click ourselves.
     * @return The remainder of whatever the player was holding.  This should be set back into the tile's stack handler or similar.
     */
    public static ItemStack rightClickInDirection(ThaumicFakePlayer player, World world, BlockPos pos, EnumFacing side, IBlockState sourceState, RayTraceResult toUse) {

        if (toUse == null) return player.getHeldItemMainhand();

        ItemStack itemstack = player.getHeldItemMainhand();
        if (toUse.typeOfHit == RayTraceResult.Type.ENTITY) {
            if (processUseEntity(player, toUse.entityHit, toUse, CPacketUseEntity.Action.INTERACT_AT))
                return player.getHeldItemMainhand();
            else if (processUseEntity(player, toUse.entityHit, null, CPacketUseEntity.Action.INTERACT))
                return player.getHeldItemMainhand();
        } else if (toUse.typeOfHit == RayTraceResult.Type.BLOCK) {
            BlockPos blockpos = toUse.getBlockPos();
            IBlockState state = world.getBlockState(blockpos);
            if (!state.equals(sourceState) && state.getMaterial() != Material.AIR) {
                float f = (float) (toUse.hitVec.x - pos.getX());
                float f1 = (float) (toUse.hitVec.y - pos.getY());
                float f2 = (float) (toUse.hitVec.z - pos.getZ());
                EnumActionResult enumactionresult = player.interactionManager.processRightClickBlock(player, world, itemstack, EnumHand.MAIN_HAND, blockpos, toUse.sideHit, f, f1, f2);
                if (enumactionresult == EnumActionResult.SUCCESS) return player.getHeldItemMainhand();
            }
        }

        if (toUse.typeOfHit == RayTraceResult.Type.MISS) {
            for (int i = 1; i <= 5; i++) {
                IBlockState state = world.getBlockState(pos.offset(side, i));
                if (!state.equals(sourceState) && state.getMaterial() != Material.AIR) {
                    player.interactionManager.processRightClickBlock(player, world, itemstack, EnumHand.MAIN_HAND, pos.offset(side, i), toUse.sideHit, 0, 0, 0);
                    return player.getHeldItemMainhand();
                }
            }
        }

        if (itemstack.isEmpty() && toUse.typeOfHit == RayTraceResult.Type.MISS)
            ForgeHooks.onEmptyClick(player, EnumHand.MAIN_HAND);
        if (!itemstack.isEmpty())
            player.interactionManager.processRightClick(player, world, itemstack, EnumHand.MAIN_HAND);
        return player.getHeldItemMainhand();
    }


    /**
     * Attacks with whatever the player happens to be holding in the given direction.
     *
     * @param player      The player.
     * @param world       The world of the calling tile entity.  It may be a bad idea to use {@link FakePlayer#getEntityWorld()}.
     * @param pos         The pos of the calling tile entity.
     * @param side        The direction to attack in.
     * @param sourceState The state of the calling tile entity, so we don't click ourselves.
     * @return The remainder of whatever the player was holding.  This should be set back into the tile's stack handler or similar.
     */
    public static ItemStack leftClickInDirection(ThaumicFakePlayer player, World world, BlockPos pos, EnumFacing side, IBlockState sourceState, RayTraceResult toUse) {


        if (toUse == null) return player.getHeldItemMainhand();

        ItemStack itemstack = player.getHeldItemMainhand();
        if (toUse.typeOfHit == RayTraceResult.Type.ENTITY) {
            if (processUseEntity(player, toUse.entityHit, null, CPacketUseEntity.Action.ATTACK))
                return player.getHeldItemMainhand();
        } else if (toUse.typeOfHit == RayTraceResult.Type.BLOCK) {
            BlockPos blockpos = toUse.getBlockPos();
            IBlockState state = world.getBlockState(blockpos);
            if (!state.equals(sourceState) && state.getMaterial() != Material.AIR) {
                player.interactionManager.onBlockClicked(blockpos, toUse.sideHit);
                return player.getHeldItemMainhand();
            }
        }

        if (toUse.typeOfHit == RayTraceResult.Type.MISS) {
            for (int i = 1; i <= 5; i++) {
                IBlockState state = world.getBlockState(pos.offset(side, i));
                if (!state.equals(sourceState) && state.getMaterial() != Material.AIR) {
                    player.interactionManager.onBlockClicked(pos.offset(side, i), side.getOpposite());
                    return player.getHeldItemMainhand();
                }
            }
        }

        if (itemstack.isEmpty() && toUse.typeOfHit == RayTraceResult.Type.MISS)
            ForgeHooks.onEmptyLeftClick(player);
        return player.getHeldItemMainhand();
    }


    /**
     * Traces for an entity.
     *
     * @param player The player.
     * @param world  The world of the calling tile entity.
     * @return A ray trace result that will likely be of type entity, but may be type block, or null.
     */
    public static RayTraceResult traceEntities(ThaumicFakePlayer player, Vec3d base, Vec3d target, World world) {
        Entity pointedEntity = null;
        RayTraceResult result = null;
        Vec3d vec3d3 = null;
        AxisAlignedBB search = new AxisAlignedBB(base.x, base.y, base.z, target.x, target.y, target.z).grow(.5, .5, .5);
        List<Entity> list = world.getEntitiesInAABBexcluding(player, search, Predicates.and(EntitySelectors.NOT_SPECTATING, entity -> entity != null && entity.canBeCollidedWith()));
        double d2 = 5;

        for (Entity entity1 : list) {
            AxisAlignedBB aabb = entity1.getEntityBoundingBox().grow(entity1.getCollisionBorderSize());
            RayTraceResult raytraceresult = aabb.calculateIntercept(base, target);

            if (aabb.contains(base)) {
                if (d2 >= 0.0D) {
                    pointedEntity = entity1;
                    vec3d3 = raytraceresult == null ? base : raytraceresult.hitVec;
                    d2 = 0.0D;
                }
            } else if (raytraceresult != null) {
                double d3 = base.distanceTo(raytraceresult.hitVec);

                if (d3 < d2 || d2 == 0.0D) {
                    if (entity1.getLowestRidingEntity() == player.getLowestRidingEntity() && !entity1.canRiderInteract()) {
                        if (d2 == 0.0D) {
                            pointedEntity = entity1;
                            vec3d3 = raytraceresult.hitVec;
                        }
                    } else {
                        pointedEntity = entity1;
                        vec3d3 = raytraceresult.hitVec;
                        d2 = d3;
                    }
                }
            }
        }

        if (pointedEntity != null && base.distanceTo(vec3d3) > 5) {
            pointedEntity = null;
            //noinspection ConstantConditions
            result = new RayTraceResult(RayTraceResult.Type.MISS, vec3d3, null, new BlockPos(vec3d3));
        }

        if (pointedEntity != null) {
            result = new RayTraceResult(pointedEntity, vec3d3);
        }

        return result;
    }

    /**
     * Processes the using of an entity from the server side.
     *
     * @param player The player.
     * @param entity The entity to interact with.
     * @param result The actual ray trace result, only necessary if using {@link CPacketUseEntity.Action#INTERACT_AT}
     * @param action The type of interaction to perform.
     * @return If the entity was used.
     */
    private static boolean processUseEntity(ThaumicFakePlayer player, Entity entity, @Nullable RayTraceResult result, CPacketUseEntity.Action action) {
        if (entity != null) {
            boolean flag = player.canEntityBeSeen(entity);
            double d0 = 36.0D;

            if (!flag) d0 = 9.0D;

            if (player.getDistanceSq(entity) < d0) {
                if (action == CPacketUseEntity.Action.INTERACT) {
                    return player.interactOn(entity, EnumHand.MAIN_HAND) == EnumActionResult.SUCCESS;
                } else if (action == CPacketUseEntity.Action.INTERACT_AT) {
                    if (ForgeHooks.onInteractEntityAt(player, entity, Objects.requireNonNull(result).hitVec, EnumHand.MAIN_HAND) != null)
                        return false;
                    return entity.applyPlayerInteraction(player, result.hitVec, EnumHand.MAIN_HAND) == EnumActionResult.SUCCESS;
                } else if (action == CPacketUseEntity.Action.ATTACK) {
                    if (entity instanceof EntityItem || entity instanceof EntityXPOrb || entity instanceof EntityArrow || entity.equals(player))
                        return false;
                    player.attackTargetEntityWithCurrentItem(entity);
                    return true;
                }
            }
        }
        return false;
    }

    @SubscribeEvent
    public static void unloadWorld(WorldEvent.Unload e) {
        fakePlayers.entrySet().removeIf(entry -> entry.getValue().world == e.getWorld());
    }
}
