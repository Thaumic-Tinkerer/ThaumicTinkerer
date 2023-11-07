package com.nekokittygames.thaumictinkerer.common.recipes;

import com.nekokittygames.thaumictinkerer.common.helper.OreDictHelper;
import com.nekokittygames.thaumictinkerer.common.multiblocks.TTServerEvents;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.oredict.OreDictionary;
import thaumcraft.api.blocks.BlocksTC;
import thaumcraft.api.capabilities.ThaumcraftCapabilities;
import thaumcraft.api.crafting.IDustTrigger;
import thaumcraft.api.crafting.Part;
import thaumcraft.common.blocks.IBlockFacingHorizontal;
import thaumcraft.common.container.InventoryFake;
import thaumcraft.common.lib.crafting.Matrix;
import thaumcraft.common.lib.events.ServerEvents;
import thaumcraft.common.lib.events.ToolEvents;
import thaumcraft.common.lib.utils.BlockUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TTDustTriggerMultiblock implements IDustTrigger {

    Part[][][] blueprint;
    String research;
    int ySize;
    int xSize;
    int zSize;

    public TTDustTriggerMultiblock(String research, Part[][][] blueprint) {
        this.blueprint = blueprint;
        this.research = research;
        this.ySize = this.blueprint.length;
        this.xSize = this.blueprint[0].length;
        this.zSize = this.blueprint[0][0].length;
    }

    public Placement getValidFace(World world, EntityPlayer player, BlockPos pos, EnumFacing face) {
        if (this.research != null && !ThaumcraftCapabilities.getKnowledge(player).isResearchKnown(this.research)) {
            return null;
        } else {
            for(int yy = -this.ySize; yy <= 0; ++yy) {
                for(int xx = -this.xSize; xx <= 0; ++xx) {
                    for(int zz = -this.zSize; zz <= 0; ++zz) {
                        BlockPos p2 = pos.add(xx, yy, zz);
                        EnumFacing f = this.fitMultiblock(world, p2);
                        if (f != null) {
                            return new Placement(xx, yy, zz, f);
                        }
                    }
                }
            }

            return null;
        }
    }

    private EnumFacing fitMultiblock(World world, BlockPos pos) {
        EnumFacing[] var3 = EnumFacing.HORIZONTALS;
        int var4 = var3.length;

        label67:
        for (EnumFacing face : var3) {
            for (int y = 0; y < this.ySize; ++y) {
                TTMatrix matrix = new TTMatrix(this.blueprint[y]);
                matrix.Rotate90DegRight(3 - face.getHorizontalIndex());

                for (int x = 0; x < matrix.rows; ++x) {
                    for (int z = 0; z < matrix.cols; ++z) {
                        if (matrix.matrix[x][z] != null) {
                            IBlockState bsWo = world.getBlockState(pos.add(x, -y + (this.ySize - 1), z));
                            Object source = matrix.matrix[x][z].getSource();
                            if (source instanceof Block && bsWo.getBlock() != (Block) source ||
                                    source instanceof Material && bsWo.getMaterial() != (Material) source ||
                                    source instanceof ItemStack && (bsWo.getBlock() != Block.getBlockFromItem(((ItemStack) source).getItem()) || bsWo.getBlock().getMetaFromState(bsWo) != ((ItemStack) source).getItemDamage()) ||
                                    source instanceof IBlockState && bsWo != source ||
                                    source instanceof Class<?> && !bsWo.getBlock().getClass().equals(source) ||
                                    source instanceof String && !OreDictHelper.oreDictCheck(bsWo,(String)source)) {
                                continue label67;
                            }
                        }
                    }
                }
            }

            return face;
        }

        return null;
    }

    public List<BlockPos> sparkle(World world, EntityPlayer player, BlockPos pos, Placement placement) {
        BlockPos p2 = pos.add(placement.xOffset, placement.yOffset, placement.zOffset);
        ArrayList<BlockPos> list = new ArrayList();

        for(int y = 0; y < this.ySize; ++y) {
            TTMatrix matrix = new TTMatrix(this.blueprint[y]);
            matrix.Rotate90DegRight(3 - placement.facing.getHorizontalIndex());

            for(int x = 0; x < matrix.rows; ++x) {
                for(int z = 0; z < matrix.cols; ++z) {
                    if (matrix.matrix[x][z] != null) {
                        BlockPos p3 = p2.add(x, -y + (this.ySize - 1), z);
                        if (matrix.matrix[x][z].getSource() != null && BlockUtils.isBlockExposed(world, p3)) {
                            list.add(p3);
                        }
                    }
                }
            }
        }

        return list;
    }

    public void execute(final World world, final EntityPlayer player, BlockPos pos, Placement placement, EnumFacing side) {
        if (!world.isRemote) {
            FMLCommonHandler.instance().firePlayerCraftingEvent(player, new ItemStack(BlocksTC.infernalFurnace), new InventoryFake(1));
            BlockPos p2 = pos.add(placement.xOffset, placement.yOffset, placement.zOffset);

            for(int y = 0; y < this.ySize; ++y) {
                TTMatrix matrix = new TTMatrix(this.blueprint[y]);
                matrix.Rotate90DegRight(3 - placement.facing.getHorizontalIndex());

                for(int x = 0; x < matrix.rows; ++x) {
                    for(int z = 0; z < matrix.cols; ++z) {
                        if (matrix.matrix[x][z] != null && matrix.matrix[x][z].getTarget() != null) {
                            final ItemStack targetObject;
                            if (matrix.matrix[x][z].getTarget() instanceof Block) {
                                int meta = 0;
                                EnumFacing side2 = side;
                                if ((Block) matrix.matrix[x][z].getTarget() instanceof IBlockFacingHorizontal) {
                                    if (side.getHorizontalIndex() < 0) {
                                        side2 = player.getHorizontalFacing().getOpposite();
                                    }

                                    IBlockState state = ((Block) matrix.matrix[x][z].getTarget()).getDefaultState().withProperty(IBlockFacingHorizontal.FACING, matrix.matrix[x][z].getApplyPlayerFacing() ? side2 : (matrix.matrix[x][z].isOpp() ? placement.facing.getOpposite() : placement.facing));
                                    meta = ((Block) matrix.matrix[x][z].getTarget()).getMetaFromState(state);
                                }

                                targetObject = new ItemStack((Block) matrix.matrix[x][z].getTarget(), 1, meta);
                            } else if (matrix.matrix[x][z].getTarget() instanceof ItemStack) {
                                targetObject = ((ItemStack) matrix.matrix[x][z].getTarget()).copy();
                            } else {
                                targetObject = null;
                            }

                            final BlockPos p3 = p2.add(x, -y + (this.ySize - 1), z);
                            final Object sourceObject;
                            if (matrix.matrix[x][z].getSource() instanceof Block) {
                                sourceObject = world.getBlockState(p3);
                            } else if (matrix.matrix[x][z].getSource() instanceof Material) {
                                sourceObject = (Material) matrix.matrix[x][z].getSource();
                            } else if (matrix.matrix[x][z].getSource() instanceof IBlockState) {
                                sourceObject = (IBlockState) matrix.matrix[x][z].getSource();
                            } else if(matrix.matrix[x][z].getSource() instanceof Class<?>) {
                                sourceObject = (Class<?>) matrix.matrix[x][z].getSource();
                            } else if(matrix.matrix[x][z].getSource() instanceof String) {
                                sourceObject = (String)matrix.matrix[x][z].getSource();
                        } else {
                                sourceObject = null;
                            }

                            ToolEvents.addBlockedBlock(world, p3);
                            ServerEvents.addRunnableServer(world, new Runnable() {
                                public void run() {
                                    TTServerEvents.addSwapper(world, p3, sourceObject, targetObject, false, 0, player, true, false, -9999, false, false, 0, ServerEvents.DEFAULT_PREDICATE, 0.0F);
                                    ToolEvents.clearBlockedBlock(world, p3);
                                }
                            }, matrix.matrix[x][z].getPriority());
                        }
                    }
                }
            }
        }

    }
}
