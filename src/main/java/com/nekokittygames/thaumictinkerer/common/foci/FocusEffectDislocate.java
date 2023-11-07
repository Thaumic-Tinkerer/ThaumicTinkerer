package com.nekokittygames.thaumictinkerer.common.foci;

import com.google.common.collect.Lists;
import com.nekokittygames.thaumictinkerer.ThaumicTinkerer;
import com.nekokittygames.thaumictinkerer.api.ThaumicTinkererAPI;
import com.nekokittygames.thaumictinkerer.common.utils.ItemNBTHelper;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.BlockSnapshot;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.casters.FocusEffect;
import thaumcraft.api.casters.ICaster;
import thaumcraft.api.casters.Trajectory;
import thaumcraft.common.lib.utils.BlockUtils;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("deprecation")
public class FocusEffectDislocate extends FocusEffect {

    private static final String TAG_AVAILABLE = "available";
    private static final String TAG_TILE_CMP = "tileCmp";
    private static final String TAG_BLOCK_NAME = "blockName";
    private static final String TAG_BLOCK_META = "blockMeta";


    @Override
    public boolean execute(RayTraceResult rayTraceResult, @Nullable Trajectory trajectory, float v, int i) {
        if (rayTraceResult == null)
            return false;
        if (rayTraceResult.typeOfHit != RayTraceResult.Type.BLOCK)
            return false;

            BlockPos pos = rayTraceResult.getBlockPos();

            IBlockState blockState = this.getPackage().world.getBlockState(pos);
            TileEntity tileEntity = this.getPackage().world.getTileEntity(pos);
            ItemStack casterStack = ItemStack.EMPTY;
            if (this.getPackage().getCaster().getHeldItemMainhand() != ItemStack.EMPTY && this.getPackage().getCaster().getHeldItemMainhand().getItem() instanceof ICaster) {
                casterStack = this.getPackage().getCaster().getHeldItemMainhand();
            } else if (this.getPackage().getCaster().getHeldItemOffhand() != ItemStack.EMPTY && this.getPackage().getCaster().getHeldItemOffhand().getItem() instanceof ICaster) {
                casterStack = this.getPackage().getCaster().getHeldItemOffhand();
            }
            if (casterStack == null)
                return false;
            if (casterStack == ItemStack.EMPTY)
                return false;

            ItemStack focus = ((ICaster) casterStack.getItem()).getFocusStack(casterStack);
            if (getPackage().getCaster() instanceof EntityPlayer && ((EntityPlayer) getPackage().getCaster()).canPlayerEdit(pos, rayTraceResult.sideHit, casterStack)) {
                IBlockState stateStored = getStoredState(focus);
                if (stateStored != null) {
                    switch (rayTraceResult.sideHit) {
                        case UP:
                            pos = pos.up();
                            break;
                        case DOWN:
                            pos = pos.down();
                            break;
                        case NORTH:
                            pos = pos.north();
                            break;
                        case EAST:
                            pos = pos.east();
                            break;
                        case SOUTH:
                            pos = pos.south();
                            break;
                        case WEST:
                            pos = pos.west();
                            break;
                        default:
                            break;
                    }
                    if (blockState.getBlock().canPlaceBlockOnSide(getPackage().world, pos, rayTraceResult.sideHit) && !getPackage().world.isRemote) {
                        //getPackage().world.setBlockState(pos, stateStored, 1 | 2);
                        NBTTagCompound tileCmp = getStackTileEntity(focus);
                        if(PlaceBlock(getPackage().world, (EntityPlayerMP) getPackage().getCaster(),pos,rayTraceResult.sideHit,EnumHand.MAIN_HAND,stateStored,tileCmp)==EnumActionResult.SUCCESS)
                            clearPickedBlock(focus);

                    }
                } else if (!blockState.getBlock().isAir(blockState, getPackage().world, pos) && !BlockUtils.isPortableHoleBlackListed(blockState) && !ThaumicTinkererAPI.getDislocationBlacklist().contains(blockState.getBlock().getClass().getName()) && !getPackage().world.isRemote) {
                    storePickedBlock(blockState, tileEntity, focus);

                    if (BreakBlock(getPackage().world, (EntityPlayerMP) getPackage().getCaster(), pos) == EnumActionResult.FAIL)
                        clearPickedBlock(focus);
                }
            }
        return false;
    }

    private static EnumActionResult PlaceBlock(World world, EntityPlayerMP entityPlayerMP, BlockPos pos, EnumFacing side, EnumHand hand,IBlockState state, NBTTagCompound tileCmp)
    {
        world.captureBlockSnapshots = true;
        world.setBlockState(pos,state,1 | 2);
        if (tileCmp != null && !tileCmp.isEmpty()) {
            TileEntity tile1 = TileEntity.create(world, tileCmp);
            Objects.requireNonNull(tile1).setPos(pos);
            world.setTileEntity(pos, tile1);
        }
        world.captureBlockSnapshots=false;
        BlockEvent.PlaceEvent placeEvent = null;
        List<BlockSnapshot> blockSnapshots = (List)world.capturedBlockSnapshots.clone();
        world.capturedBlockSnapshots.clear();
        if (blockSnapshots.size() > 1) {
            placeEvent = ForgeEventFactory.onPlayerMultiBlockPlace(entityPlayerMP, blockSnapshots, side, hand);
        } else if (blockSnapshots.size() == 1) {
            placeEvent = ForgeEventFactory.onPlayerBlockPlace(entityPlayerMP, (BlockSnapshot)blockSnapshots.get(0), side, hand);
        }

        Iterator var18;
        BlockSnapshot snap;
        EnumActionResult ret;
        if (placeEvent != null && ((BlockEvent.PlaceEvent)placeEvent).isCanceled()) {
            ret = EnumActionResult.FAIL;

            for(var18 = Lists.reverse(blockSnapshots).iterator(); var18.hasNext(); world.restoringBlockSnapshots = false) {
                snap = (BlockSnapshot)var18.next();
                world.restoringBlockSnapshots = true;
                snap.restore(true, false);
            }
        } else {
            int updateFlag;
            IBlockState oldBlock;
            IBlockState newBlock;
            for(var18 = blockSnapshots.iterator(); var18.hasNext(); world.markAndNotifyBlock(snap.getPos(), (Chunk)null, oldBlock, newBlock, updateFlag)) {
                snap = (BlockSnapshot)var18.next();
                updateFlag = snap.getFlag();
                oldBlock = snap.getReplacedBlock();
                newBlock = world.getBlockState(snap.getPos());
                if (!newBlock.getBlock().hasTileEntity(newBlock)) {
                    newBlock.getBlock().onBlockAdded(world, snap.getPos(), newBlock);
                }
            }

            ret=EnumActionResult.SUCCESS;
        }
        return ret;
    }

    private static EnumActionResult BreakBlock(World world, EntityPlayerMP entityPlayer, BlockPos pos) {
        boolean preCancelEvent = false;
        if(world.getBlockState(pos).getBlockHardness(world,pos) == -1)
            preCancelEvent=true;

        IBlockState state = world.getBlockState(pos);
        BlockEvent.BreakEvent event = new BlockEvent.BreakEvent(world, pos, state, entityPlayer);
        event.setCanceled(preCancelEvent);
        MinecraftForge.EVENT_BUS.post(event);
        if (event.isCanceled()) {

            return EnumActionResult.FAIL;
        }
        world.removeTileEntity(pos);
        world.setBlockToAir(pos);
        return EnumActionResult.SUCCESS;
    }

    private void storePickedBlock(IBlockState blockState, TileEntity tileEntity, ItemStack focus) {
        ResourceLocation blockName = blockState.getBlock().getRegistryName();

        int metadata = blockState.getBlock().getMetaFromState(blockState);
        ItemNBTHelper.setString(focus, TAG_BLOCK_NAME, Objects.requireNonNull(blockName).toString());
        ItemNBTHelper.setInteger(focus, TAG_BLOCK_META, metadata);
        NBTTagCompound cmp = new NBTTagCompound();
        if (tileEntity != null) {
            tileEntity.writeToNBT(cmp);
        }
        ThaumicTinkerer.logger.info("Storing a "+blockName.toString()+" with metadata "+metadata+" With NBT: "+cmp.toString());
        ItemNBTHelper.getItemTag(focus).setTag(TAG_TILE_CMP, cmp);
        ItemNBTHelper.setBool(focus, TAG_AVAILABLE, true);

    }

    private void clearPickedBlock(ItemStack focus) {
        ItemNBTHelper.setBool(focus, TAG_AVAILABLE, false);
        ItemNBTHelper.getItemTag(focus).removeTag(TAG_TILE_CMP);
        ItemNBTHelper.getItemTag(focus).removeTag(TAG_BLOCK_NAME);
        ItemNBTHelper.getItemTag(focus).removeTag(TAG_BLOCK_META);
    }

    private NBTTagCompound getStackTileEntity(ItemStack focus) {
        if (ItemNBTHelper.getItemTag(focus).hasKey(TAG_TILE_CMP)) {
            NBTTagCompound cmp = ItemNBTHelper.getItemTag(focus).getCompoundTag(TAG_TILE_CMP);
            if (cmp.getSize() != 0)
                return cmp;
        }
        return null;

    }

    private IBlockState getStoredState(ItemStack focus) {
        if (!ItemNBTHelper.getBool(focus, TAG_AVAILABLE, false))
            return null;
        ResourceLocation location = new ResourceLocation(ItemNBTHelper.getString(focus, TAG_BLOCK_NAME, "minecraft:air"));
        int metadata = ItemNBTHelper.getInteger(focus, TAG_BLOCK_META, 0);
        IBlockState state;
        Block block = ForgeRegistries.BLOCKS.getValue(location);
        state = Objects.requireNonNull(block).getStateFromMeta(metadata);
        return state;
    }

    @Override
    public void renderParticleFX(World world, double v, double v1, double v2, double v3, double v4, double v5) {
        // Empty
    }

    @Override
    public int getComplexity() {
        return 20;
    }

    @Override
    public Aspect getAspect() {
        return Aspect.DESIRE;
    }

    @Override
    public String getKey() {
        return "thaumictinkerer.dislocation";
    }

    @Override
    public String getResearch() {
        return "TT_DISLOCATE";
    }
}
