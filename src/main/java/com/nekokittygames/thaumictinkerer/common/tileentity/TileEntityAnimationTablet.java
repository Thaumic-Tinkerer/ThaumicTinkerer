package com.nekokittygames.thaumictinkerer.common.tileentity;

import com.mojang.authlib.GameProfile;
import com.nekokittygames.thaumictinkerer.common.libs.LibMisc;
import com.nekokittygames.thaumictinkerer.common.misc.FakePlayerUtils;
import com.nekokittygames.thaumictinkerer.common.misc.ThaumicFakePlayer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCommandBlock;
import net.minecraft.block.BlockStructure;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.function.Consumer;

public class TileEntityAnimationTablet extends TileEntityThaumicTinkerer implements ITickable, Consumer<ItemStack> {

    private static final int SWING_SPEED = 3;
    private static final int MAX_DEGREE = 45;
    private boolean rightClick;
    private int progress;
    private int swingMod = 3;
    private EnumFacing facing;
    private boolean active;
    private int ticksExisted;
    private float curBlockDamageMP;
    private WeakReference<ThaumicFakePlayer> player;
    private boolean isRemoving;
    private ItemStack currentItemHittingBlock;
    private BlockPos currentBlock;
    List<Entity> detectedEntities = new ArrayList<>();
    private ItemStackHandler inventory = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            sendUpdates();
        }

        boolean isItemValidForSlot(int index, ItemStack stack) {
            return TileEntityAnimationTablet.this.isItemValidForSlot(index, stack);
        }

        @Nonnull
        @Override
        public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
            if (!isItemValidForSlot(slot, stack))
                return stack;
            return super.insertItem(slot, stack, simulate);
        }
    };

    public int getTicksExisted() {
        return ticksExisted;
    }

    public void setTicksExisted(int ticksExisted) {
        this.ticksExisted = ticksExisted;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public EnumFacing getFacing() {
        return facing;
    }

    public void setFacing(EnumFacing facing) {
        this.facing = facing;
    }

    public boolean isRightClick() {
        return rightClick;
    }

    public void setRightClick(boolean rightClick) {
        if (this.rightClick != rightClick) {
            resetAll();
        }
        this.rightClick = rightClick;
        sendUpdates();
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    private boolean isItemValidForSlot(int index, ItemStack stack) {
        return true;
    }

    public ItemStackHandler getInventory() {
        return inventory;
    }


    @Override
    public void writeExtraNBT(NBTTagCompound nbttagcompound) {
        super.writeExtraNBT(nbttagcompound);
        nbttagcompound.setTag("inventory", inventory.serializeNBT());
        nbttagcompound.setBoolean("rightClick", rightClick);
        nbttagcompound.setInteger("progress", progress);
        nbttagcompound.setInteger("swingMod", swingMod);
        nbttagcompound.setInteger("facing", facing.getIndex());
        nbttagcompound.setBoolean("active", active);
        if (currentItemHittingBlock != null) {
            nbttagcompound.setTag("currentItemHittingBlock", currentItemHittingBlock.serializeNBT());
            nbttagcompound.setTag("currentBlock", NBTUtil.createPosTag(currentBlock));
        }
        nbttagcompound.setBoolean("isRemoving", isRemoving);
    }

    @Override
    public void readExtraNBT(NBTTagCompound nbttagcompound) {
        super.readExtraNBT(nbttagcompound);
        inventory.deserializeNBT(nbttagcompound.getCompoundTag("inventory"));
        rightClick = nbttagcompound.getBoolean("rightClick");
        progress = nbttagcompound.getInteger("progress");
        swingMod = nbttagcompound.getInteger("swingMod");
        facing = EnumFacing.values()[nbttagcompound.getInteger("facing")];
        active = nbttagcompound.getBoolean("active");
        if (nbttagcompound.hasKey("currentItemHittingBlock")) {
            currentItemHittingBlock = new ItemStack(nbttagcompound.getCompoundTag("currentItemHittingBlock"));
            currentBlock = NBTUtil.getPosFromTag(nbttagcompound.getCompoundTag("currentBlock"));
        }
        isRemoving = nbttagcompound.getBoolean("isRemoving");
    }

    @Override
    public boolean respondsToPulses() {
        return false;
    }

    @Override
    public boolean canRedstoneConnect() {
        return true;
    }

    @Override
    public void activateOnPulse() {
        // Empty
    }


    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        if (facing != EnumFacing.DOWN)
            return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
        else
            return super.hasCapability(capability, facing);
    }


    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (facing != EnumFacing.DOWN && capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return (T) inventory;
        } else {
            return super.getCapability(capability, facing);
        }
    }


    private boolean isHittingPosition(BlockPos pos, ThaumicFakePlayer player) {
        ItemStack itemstack = player.getHeldItemMainhand();
        boolean flag = this.currentItemHittingBlock.isEmpty() && itemstack.isEmpty();

        if (!this.currentItemHittingBlock.isEmpty() && !itemstack.isEmpty()) {
            flag = !this.currentItemHittingBlock.getItem().shouldCauseBlockBreakReset(this.currentItemHittingBlock, itemstack);
        }

        return pos.equals(this.currentBlock) && flag;
    }

    private void resetAll() {
        progress = 0;
        isRemoving = false;
        swingMod = 3;
        curBlockDamageMP = 0.0f;

    }

    private void initiateSwing() {
        swingMod = SWING_SPEED;
        progress = 1;
    }

    private boolean detect() {
        return !world.isAirBlock(getBlockTarget()) || detectEntity().size()>0;
    }


    private AxisAlignedBB getBlockBounding() {
        BlockPos newPos = this.getPos().offset(facing);
        return new AxisAlignedBB(newPos);
    }
    private List<Entity> detectEntity() {
        AxisAlignedBB bounding=getBlockBounding();
        detectedEntities = world.getEntitiesWithinAABB(Entity.class,bounding);
        return detectedEntities;
    }

    @Override
    public void update() {

        if (!world.isRemote && player == null) {
            MinecraftServer worldServer = FMLCommonHandler.instance().getMinecraftServerInstance();
            player = new WeakReference<>(FakePlayerUtils.get(worldServer.getWorld(this.world.provider.getDimension()), new GameProfile(LibMisc.MOD_UUID, LibMisc.MOD_F_NAME)));
        }

        ticksExisted++;
        ItemStack stack = inventory.getStackInSlot(0);
        tick(stack);


        active = getRedstonePowered();

        boolean detect = detect();
        if (!detect && isRemoving)
            stopBreaking();
        if (detect && isRemoving && !world.isRemote) {
            Objects.requireNonNull(player.get()).interactionManager.updateBlockRemoving();
            continueBreaking();
        }


        if ((active || isRemoving) && detect && progress == 0)
            initiateSwing();
    }

    private void tick(ItemStack stack) {
        if (stack != ItemStack.EMPTY) {
            if (!world.isRemote && progress >= MAX_DEGREE) {
                swingHit();
            }

            swingMod = progress <= 0 ? 0 : progress >= MAX_DEGREE ? -SWING_SPEED : swingMod;
            progress += swingMod;
            if (progress < 0)
                progress = 0;
            sendUpdates();
        } else {
            resetAll();
        }
    }

    private BlockPos getTargetBlock() {
        Vec3d base;
        Vec3d look;
        Vec3d target;
        RayTraceResult trace;
        RayTraceResult traceEntity;
        RayTraceResult toUse;
        base = new Vec3d(Objects.requireNonNull(player.get()).posX, Objects.requireNonNull(player.get()).posY, Objects.requireNonNull(player.get()).posZ);
        look = Objects.requireNonNull(player.get()).getLookVec();
        target = base.add(new Vec3d(look.x * 5, look.y * 5, look.z * 5));
        trace = world.rayTraceBlocks(base, target, false, false, true);
        traceEntity = FakePlayerUtils.traceEntities(player.get(), base, target, world);
        toUse = trace == null ? traceEntity : trace;
        return toUse.getBlockPos();
    }

    private void continueBreaking() {
        BlockPos targetPos = getTargetBlock();
        if (!rightClick) {
            if (!world.getBlockState(targetPos).getBlock().isAir(world.getBlockState(targetPos), world, pos)) {
                this.curBlockDamageMP += world.getBlockState(targetPos).getPlayerRelativeBlockHardness(Objects.requireNonNull(player.get()), player.get().world, targetPos);

                //if (this.curBlockDamageMP >= 1.0f) {
                //    player.get().interactionManager

                //this.curBlockDamageMP = 0;
                //}

            } else {
                this.curBlockDamageMP = 0;
            }
            if (this.isRemoving && this.isHittingPosition(targetPos, Objects.requireNonNull(player.get()))) {
                IBlockState iblockstate = world.getBlockState(targetPos);

                if (iblockstate.getMaterial() == Material.AIR) {
                    this.isRemoving = false;
                }
                this.curBlockDamageMP += iblockstate.getPlayerRelativeBlockHardness(player.get(), world, targetPos);
                if (this.curBlockDamageMP >= 1.0F) {
                    this.isRemoving = false;
                    player.get().interactionManager.blockRemoving(this.currentBlock);
                    this.onPlayerDestroyBlock(this.currentBlock, player.get());
                    this.curBlockDamageMP = 0.0F;
                }

                world.sendBlockBreakProgress(player.get().getEntityId(), this.currentBlock, (int) (this.curBlockDamageMP * 10.0F) - 1);
            }

        }
    }
    public static void LookAt(double px, double py, double pz , Entity entity) {
        double dirx = entity.getPosition().getX() - px;
        double diry = entity.getPosition().getY() - py;
        double dirz = entity.getPosition().getZ() - pz;

        double len = Math.sqrt(dirx * dirx + diry * diry + dirz * dirz);

        dirx /= len;
        diry /= len;
        dirz /= len;

        double pitch = Math.asin(diry);
        double yaw = Math.atan2(dirz, dirx);

        //to degree
        pitch = pitch * 180.0 / Math.PI;
        yaw = yaw * 180.0 / Math.PI;

        yaw += 90f;
        entity.rotationPitch = (float) pitch;
        entity.rotationYaw = (float)yaw;
    }

        private void swingHit() {

        Vec3d base;
        Vec3d look;
        Vec3d target;
        RayTraceResult trace;
        RayTraceResult traceEntity;
        RayTraceResult toUse = null;
        FakePlayerUtils.setupFakePlayerForUse(getPlayer(), this.pos, facing, this.inventory.getStackInSlot(0).copy(), false);
        base = new Vec3d(Objects.requireNonNull(player.get()).posX, player.get().posY, player.get().posZ);

        look = player.get().getLookVec();
        target = base.add(new Vec3d(look.x * 5, look.y * 5, look.z * 5));
        trace = world.rayTraceBlocks(base, target, false, false, true);
        traceEntity = FakePlayerUtils.traceEntities(player.get(), base, target, world);
        if(traceEntity ==null && trace==null)
            return;
        toUse = traceEntity == null ?  trace: traceEntity;
        BlockPos targetPos = toUse.getBlockPos();
        if (trace != null && traceEntity != null) {
            double d1 = trace.hitVec.distanceTo(base);
            double d2 = traceEntity.hitVec.distanceTo(base);
            toUse = traceEntity.typeOfHit == RayTraceResult.Type.ENTITY && d1 > d2 ? traceEntity : trace;
        }

        if (toUse.typeOfHit== RayTraceResult.Type.BLOCK && world.getBlockState(targetPos) == world.getBlockState(pos)) return;
        if (!rightClick) {
            if(detectEntity().size()>0)
            {
                Random rand=new Random();
                Objects.requireNonNull(player.get()).attackTargetEntityWithCurrentItem(detectedEntities.get(rand.nextInt(detectedEntities.size())));
                FakePlayerUtils.cleanupFakePlayerFromUse(getPlayer(), player.get().getHeldItemMainhand(), this.inventory.getStackInSlot(0), this);
                return;
            }
            leftClick(toUse, getTargetBlock());
            //world.sendBlockBreakProgress(fakePlayer.getEntityId(),);

        } else {
            if(detectEntity().size()>0)
            {
                Random rand=new Random();
                Entity entity=detectedEntities.get(rand.nextInt(detectedEntities.size()));
                if (FakePlayerUtils.processUseEntity(player.get(), entity, toUse, CPacketUseEntity.Action.INTERACT_AT)) {
                    FakePlayerUtils.cleanupFakePlayerFromUse(getPlayer(), player.get().getHeldItemMainhand(), this.inventory.getStackInSlot(0), this);
                    return;
                }
                else if (FakePlayerUtils.processUseEntity(player.get(), entity, null, CPacketUseEntity.Action.INTERACT)) {
                    FakePlayerUtils.cleanupFakePlayerFromUse(getPlayer(), player.get().getHeldItemMainhand().copy(), this.inventory.getStackInSlot(0), this);
                    return;
                }
                return;
            }
            rightClick(toUse);
        }
    }

    private void rightClick(RayTraceResult toUse) {
        FakePlayerUtils.setupFakePlayerForUse(getPlayer(), this.pos, facing, this.inventory.getStackInSlot(0).copy(), false);
        ItemStack result = this.inventory.getStackInSlot(0);
        result = FakePlayerUtils.rightClickInDirection(getPlayer(), this.world, this.pos, facing, world.getBlockState(pos), toUse);
        FakePlayerUtils.cleanupFakePlayerFromUse(getPlayer(), result, this.inventory.getStackInSlot(0), this);
    }

    private void leftClick(RayTraceResult toUse, BlockPos targetPos) {
        if (!this.isRemoving || !this.isHittingPosition(targetPos, player.get())) {

            ItemStack itm = FakePlayerUtils.leftClickInDirection(getPlayer(), this.world, this.pos, facing, world.getBlockState(pos), toUse);
            if(toUse.typeOfHit== RayTraceResult.Type.BLOCK) {
                IBlockState iblockstate = world.getBlockState(targetPos);
                this.inventory.setStackInSlot(0, itm.copy());
                boolean flag = iblockstate.getMaterial() != Material.AIR;
                if (flag && iblockstate.getPlayerRelativeBlockHardness(player.get(), world, targetPos) >= 1.0F) {
                    this.onPlayerDestroyBlock(targetPos, player.get());
                } else {
                    this.isRemoving = true;
                    this.currentBlock = targetPos;
                    this.currentItemHittingBlock = player.get().getHeldItemMainhand();
                    this.curBlockDamageMP = 0.0F;
                    world.sendBlockBreakProgress(player.get().getEntityId(), this.currentBlock, (int) (this.curBlockDamageMP * 10.0F) - 1);
                }
            }
        }
    }

    private void stopBreaking() {
        this.isRemoving = false;
        if (!world.isRemote) {
            world.sendBlockBreakProgress(Objects.requireNonNull(player.get()).getEntityId(), this.currentBlock, -1);
        }
    }

    private void onPlayerDestroyBlock(BlockPos targetPos, ThaumicFakePlayer player) {
        ItemStack stack = player.getHeldItemMainhand();
        if (!stack.isEmpty() && stack.getItem().onBlockStartBreak(stack, targetPos, player)) {
            return;
        }

        IBlockState iblockstate = world.getBlockState(targetPos);
        Block block = iblockstate.getBlock();

        if ((block instanceof BlockCommandBlock || block instanceof BlockStructure) && !player.canUseCommandBlock()) {
            return;
        } else if (iblockstate.getMaterial() == Material.AIR) {
            return;
        } else {
            world.playEvent(2001, targetPos, Block.getStateId(iblockstate));

            this.currentBlock = new BlockPos(this.currentBlock.getX(), -1, this.currentBlock.getZ());
            ItemStack itemstack1 = player.getHeldItemMainhand();
            ItemStack copyBeforeUse = itemstack1.copy();

            if (!itemstack1.isEmpty()) {
                itemstack1.onBlockDestroyed(world, iblockstate, targetPos, player);

                if (itemstack1.isEmpty()) {
                    net.minecraftforge.event.ForgeEventFactory.onPlayerDestroyItem(player, copyBeforeUse, EnumHand.MAIN_HAND);
                    player.setHeldItem(EnumHand.MAIN_HAND, ItemStack.EMPTY);
                }
            }
        }

        boolean flag = block.removedByPlayer(iblockstate, world, targetPos, player, false);

        if (flag) {
            block.onPlayerDestroy(world, targetPos, iblockstate);
        }
    }


    private ThaumicFakePlayer getPlayer() {
        return player.get();
    }

    private BlockPos getBlockTarget() {
        BlockPos newPos = this.getPos().offset(facing);
        if (isRightClick() && world.isAirBlock(newPos) )
            newPos = newPos.offset(EnumFacing.DOWN);
        return newPos;
    }

    @Override
    public void accept(ItemStack itemStack) {
        this.inventory.setStackInSlot(0, itemStack);
    }
}
