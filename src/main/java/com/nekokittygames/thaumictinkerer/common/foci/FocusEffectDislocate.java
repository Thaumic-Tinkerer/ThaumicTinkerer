package com.nekokittygames.thaumictinkerer.common.foci;

import com.nekokittygames.thaumictinkerer.common.utils.ItemNBTHelper;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.casters.FocusEffect;
import thaumcraft.api.casters.Trajectory;
import thaumcraft.common.items.casters.ItemCaster;
import thaumcraft.common.lib.utils.BlockUtils;

import javax.annotation.Nullable;
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
        if (this.getPackage().getCaster().getHeldItemMainhand() != ItemStack.EMPTY && this.getPackage().getCaster().getHeldItemMainhand().getItem() instanceof ItemCaster) {
            casterStack = this.getPackage().getCaster().getHeldItemMainhand();
        } else if (this.getPackage().getCaster().getHeldItemOffhand() != ItemStack.EMPTY && this.getPackage().getCaster().getHeldItemOffhand().getItem() instanceof ItemCaster) {
            casterStack = this.getPackage().getCaster().getHeldItemOffhand();
        }
        if (casterStack == null)
            return false;
        if (casterStack == ItemStack.EMPTY)
            return false;

        ItemStack focus = ((ItemCaster) casterStack.getItem()).getFocusStack(casterStack);
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
                }
                if (blockState.getBlock().canPlaceBlockOnSide(getPackage().world, pos, rayTraceResult.sideHit)) {
                    if (!getPackage().world.isRemote) {
                        getPackage().world.setBlockState(pos, stateStored, 1 | 2);
                        NBTTagCompound tileCmp = getStackTileEntity(focus);
                        if (tileCmp != null && !tileCmp.hasNoTags()) {
                            TileEntity tile1 = TileEntity.create(getPackage().world, tileCmp);
                            Objects.requireNonNull(tile1).setPos(pos);
                            getPackage().world.setTileEntity(pos, tile1);
                        }
                        stateStored.getBlock().onBlockPlacedBy(getPackage().world, pos, stateStored, getPackage().getCaster(), new ItemStack(stateStored.getBlock()));
                        clearPickedBlock(focus);
                    }

                }
            } else if (!blockState.getBlock().isAir(blockState, getPackage().world, pos) && !BlockUtils.isPortableHoleBlackListed(blockState) && !getPackage().world.isRemote) {
                storePickedBlock(blockState, tileEntity, focus);
                if (tileEntity != null)
                    getPackage().world.removeTileEntity(pos);
                getPackage().world.setBlockToAir(pos);
            }
        }
        return false;
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

    }

    @Override
    public int getComplexity() {
        return 1;
    }

    @Override
    public Aspect getAspect() {
        return Aspect.AIR;
    }

    @Override
    public String getKey() {
        return "dislocation";
    }

    @Override
    public String getResearch() {
        return "FIRSTSTEPS";
    }
}
