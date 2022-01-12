/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the ThaumicTinkerer Mod.
 *
 * ThaumicTinkerer is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * ThaumicTinkerer is a Derivative Work on Thaumcraft 4.
 * Thaumcraft 4 (c) Azanor 2012
 * (http://www.minecraftforum.net/topic/1585216-)
 *
 * File Created @ [9 Sep 2013, 22:19:25 (GMT)]
 */
package thaumic.tinkerer.common.item.foci;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.research.ResearchPage;
import thaumcraft.common.config.Config;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.items.wands.ItemWandCasting;
import thaumic.tinkerer.client.core.helper.IconHelper;
import thaumic.tinkerer.common.ThaumicTinkerer;
import thaumic.tinkerer.common.core.helper.ItemNBTHelper;
import thaumic.tinkerer.common.lib.LibItemNames;
import thaumic.tinkerer.common.lib.LibResearch;
import thaumic.tinkerer.common.registry.ThaumicTinkererInfusionRecipe;
import thaumic.tinkerer.common.registry.ThaumicTinkererRecipe;
import thaumic.tinkerer.common.research.IRegisterableResearch;
import thaumic.tinkerer.common.research.ResearchHelper;
import thaumic.tinkerer.common.research.TTResearchItem;

public class ItemFocusDislocation extends ItemModFocus {

  private static final String TAG_AVAILABLE = "available";
  private static final String TAG_TILE_CMP = "tileCmp";
  @Deprecated private static final String TAG_BLOCK_ID = "blockID";
  private static final String TAG_BLOCK_NAME = "blockName";
  private static final String TAG_BLOCK_META = "blockMeta";
  private static final AspectList visUsage = new AspectList()
                                                 .add(Aspect.ENTROPY, 500)
                                                 .add(Aspect.ORDER, 500)
                                                 .add(Aspect.EARTH, 100);
  private static final AspectList visUsageTile = new AspectList()
                                                     .add(Aspect.ENTROPY, 2500)
                                                     .add(Aspect.ORDER, 2500)
                                                     .add(Aspect.EARTH, 500);
  private static final AspectList visUsageSpawner =
      new AspectList()
          .add(Aspect.ENTROPY, 10000)
          .add(Aspect.ORDER, 10000)
          .add(Aspect.EARTH, 5000);
  private static ArrayList<Block> blacklist = new ArrayList<Block>();
  private IIcon ornament;

  private static AspectList getCost(TileEntity tile) {
    return tile == null                    ? visUsage
    : tile instanceof TileEntityMobSpawner ? visUsageSpawner
                                           : visUsageTile;
  }

  @Override
  @SideOnly(Side.CLIENT)
  public void registerIcons(IIconRegister par1IconRegister) {
    super.registerIcons(par1IconRegister);
    ornament = IconHelper.forItem(par1IconRegister, this, "Orn");
  }

  @Override
  public ItemStack onFocusRightClick(ItemStack itemstack, World world,
                                     EntityPlayer player,
                                     MovingObjectPosition mop) {
    if (mop == null)
      return itemstack;

    Block block = world.getBlock(mop.blockX, mop.blockY, mop.blockZ);
    int meta = world.getBlockMetadata(mop.blockX, mop.blockY, mop.blockZ);
    TileEntity tile = world.getTileEntity(mop.blockX, mop.blockY, mop.blockZ);
    ItemWandCasting wand = (ItemWandCasting)itemstack.getItem();

    if (player.canPlayerEdit(mop.blockX, mop.blockY, mop.blockZ, mop.sideHit,
                             itemstack)) {
      ItemStack stack = getPickedBlock(itemstack);
      if (stack != null) {
        if (mop.sideHit == 0)
          --mop.blockY;
        if (mop.sideHit == 1)
          ++mop.blockY;
        if (mop.sideHit == 2)
          --mop.blockZ;
        if (mop.sideHit == 3)
          ++mop.blockZ;
        if (mop.sideHit == 4)
          --mop.blockX;
        if (mop.sideHit == 5)
          ++mop.blockX;

        if (block.canPlaceBlockOnSide(world, mop.blockX, mop.blockY, mop.blockZ,
                                      mop.sideHit)) {
          if (!world.isRemote) {
            world.setBlock(mop.blockX, mop.blockY, mop.blockZ,
                           ((ItemBlock)stack.getItem()).field_150939_a,
                           stack.getItemDamage(), 1 | 2);
            ((ItemBlock)stack.getItem())
                .field_150939_a.onBlockPlacedBy(world, mop.blockX, mop.blockY,
                                                mop.blockZ, player, itemstack);
            NBTTagCompound tileCmp = getStackTileEntity(itemstack);
            if (tileCmp != null && !tileCmp.hasNoTags()) {
              TileEntity tile1 = TileEntity.createAndLoadEntity(tileCmp);
              tile1.xCoord = mop.blockX;
              tile1.yCoord = mop.blockY;
              tile1.zCoord = mop.blockZ;
              world.setTileEntity(mop.blockX, mop.blockY, mop.blockZ, tile1);
            }
          } else
            player.swingItem();
          clearPickedBlock(itemstack);

          for (int i = 0; i < 8; i++) {
            float x = (float)(mop.blockX + Math.random());
            float y = (float)(mop.blockY + Math.random()) + 0.65F;
            float z = (float)(mop.blockZ + Math.random());
            ThaumicTinkerer.tcProxy.burst(world, x, y, z, 0.2F);
          }
          world.playSoundAtEntity(player, "thaumcraft:wand", 0.5F, 1F);
        }
      } else if (!blacklist.contains(block) &&
                 !ThaumcraftApi.portableHoleBlackList.contains(block) &&
                 block != null &&
                 block.getBlockHardness(world, mop.blockX, mop.blockY,
                                        mop.blockZ) != -1F &&
                 wand.consumeAllVis(itemstack, player, getCost(tile), true,
                                    false)) {
        if (!world.isRemote) {
          try {
            storePickedBlock(itemstack, block, (short)meta, tile);
            world.removeTileEntity(mop.blockX, mop.blockY, mop.blockZ);
            world.setBlock(mop.blockX, mop.blockY, mop.blockZ, Blocks.air, 0,
                           1 | 2);
          } catch (Exception e) {
            ItemStack pickedBlock = getPickedBlock(itemstack);
            String pickedBlockName =
                (pickedBlock == null
                     ? "a null block (how?)"
                     : "'" + getPickedBlock(itemstack).getUnlocalizedName()) +
                "'";
            ThaumicTinkerer.log.error(
                "Attempting to use Wand Focus: Dislocation on " +
                pickedBlockName + " resulted in an exception being thrown.");
            if (tile != null) {
              ThaumicTinkerer.log.debug(
                  "Tile entity's NBT is as follows: " +
                  getStackTileEntity(itemstack).toString());
            }
            ThaumicTinkerer.log.debug("Exception details:");
            for (Object line : e.getStackTrace()) {
              ThaumicTinkerer.log.debug(line.toString());
            }
            ThaumicTinkerer.log.debug("(End stack trace)");
          }
        }

        for (int i = 0; i < 8; i++) {
          float x = (float)(mop.blockX + Math.random());
          float y = (float)(mop.blockY + Math.random());
          float z = (float)(mop.blockZ + Math.random());
          ThaumicTinkerer.tcProxy.burst(world, x, y, z, 0.2F);
        }
        world.playSoundAtEntity(player, block.stepSound.getBreakSound(), 1F,
                                1F);
        world.playSoundAtEntity(player, "thaumcraft:wand", 0.5F, 1F);

        if (world.isRemote)
          player.swingItem();
      }
    }

    return itemstack;
  }

  @Override
  public String getSortingHelper(ItemStack itemstack) {
    return "DISLOCATION" + getUniqueKey(itemstack);
  }

  public String getUniqueKey(ItemStack itemstack) {
    ItemStack stack = getPickedBlock(itemstack);
    if (stack == null)
      return "";
    String name = stack.getUnlocalizedName();
    int datahash = 0;
    if (stack.getTagCompound() != null) {
      datahash = stack.getTagCompound().hashCode();
    }
    return String.format("%s-%d", name, datahash);
  }

  public ItemStack getPickedBlock(ItemStack stack) {
    ItemStack focus;
    if (stack.getItem() instanceof ItemWandCasting) {
      ItemWandCasting wand = (ItemWandCasting)stack.getItem();
      focus = wand.getFocusItem(stack);
    } else {
      focus = stack;
    }
    return (ItemNBTHelper.getBoolean(focus, TAG_AVAILABLE, false))
        ? getPickedBlockStack(stack)
        : null;
  }

  public ItemStack getPickedBlockStack(ItemStack stack) {
    ItemStack focus;
    if (stack.getItem() instanceof ItemWandCasting) {
      ItemWandCasting wand = (ItemWandCasting)stack.getItem();
      focus = wand.getFocusItem(stack);
    } else {
      focus = stack;
    }
    String name = ItemNBTHelper.getString(focus, TAG_BLOCK_NAME, "");
    Block block = Block.getBlockFromName(name);
    if (block == Blocks.air) {
      int id = ItemNBTHelper.getInt(focus, TAG_BLOCK_ID, 0);
      block = Block.getBlockById(id);
    }
    int meta = ItemNBTHelper.getInt(focus, TAG_BLOCK_META, 0);
    ItemStack stck;
    // if(block instanceof BlockReed)
    //{
    //    stck=new ItemStack(Items.reeds,1,meta);
    // }
    stck = new ItemStack(new ItemBlock(block), 1, meta);
    return stck;
  }

  public NBTTagCompound getStackTileEntity(ItemStack stack) {
    ItemStack focus;
    if (stack.getItem() instanceof ItemWandCasting) {
      ItemWandCasting wand = (ItemWandCasting)stack.getItem();
      focus = wand.getFocusItem(stack);
    } else {
      focus = stack;
    }
    return ItemNBTHelper.getCompound(focus, TAG_TILE_CMP, true);
  }

  private void storePickedBlock(ItemStack stack, Block block, short meta,
                                TileEntity tile) {
    ItemWandCasting wand = (ItemWandCasting)stack.getItem();
    ItemStack focus = wand.getFocusItem(stack);
    String blockName = Block.blockRegistry.getNameForObject(block);
    ItemNBTHelper.setString(focus, TAG_BLOCK_NAME, blockName);
    ItemNBTHelper.setInt(focus, TAG_BLOCK_META, meta);
    NBTTagCompound cmp = new NBTTagCompound();
    if (tile != null)
      tile.writeToNBT(cmp);
    ItemNBTHelper.setCompound(focus, TAG_TILE_CMP, cmp);
    ItemNBTHelper.setBoolean(focus, TAG_AVAILABLE, true);
    wand.setFocus(stack, focus);
  }

  private void clearPickedBlock(ItemStack stack) {
    ItemWandCasting wand = (ItemWandCasting)stack.getItem();
    ItemStack focus = wand.getFocusItem(stack);
    ItemNBTHelper.setBoolean(focus, TAG_AVAILABLE, false);
    wand.setFocus(stack, focus);
  }

  @Override
  public int getFocusColor(ItemStack stack) {
    return 0xFFB200;
  }

  @Override
  public IIcon getOrnament(ItemStack stack) {
    return ornament;
  }

  @Override
  public AspectList getVisCost(ItemStack stack) {
    return visUsage;
  }

  static {
    blacklist.add(Blocks.piston_extension);
    blacklist.add(Blocks.piston_head);
  }

  @Override
  public String getItemName() {
    return LibItemNames.FOCUS_DISLOCATION;
  }

  @Override
  public IRegisterableResearch getResearchItem() {
    if (!Config.allowMirrors) {
      return null;
    }
    return (TTResearchItem) new TTResearchItem(
               LibResearch.KEY_FOCUS_DISLOCATION,
               new AspectList()
                   .add(Aspect.ELDRITCH, 2)
                   .add(Aspect.MAGIC, 1)
                   .add(Aspect.EXCHANGE, 1),
               -5, -5, 2, new ItemStack(this))
        .setSecondary()
        .setParents(LibResearch.KEY_FOCUS_FLIGHT)
        .setConcealed()
        .setPages(
            new ResearchPage("0"), new ResearchPage("1"),
            ResearchHelper.infusionPage(LibResearch.KEY_FOCUS_DISLOCATION));
  }

  @Override
  public ThaumicTinkererRecipe getRecipeItem() {
    return new ThaumicTinkererInfusionRecipe(
        LibResearch.KEY_FOCUS_DISLOCATION, new ItemStack(this), 8,
        new AspectList()
            .add(Aspect.ELDRITCH, 20)
            .add(Aspect.DARKNESS, 10)
            .add(Aspect.VOID, 25)
            .add(Aspect.MAGIC, 20)
            .add(Aspect.TAINT, 5),
        new ItemStack(Items.ender_pearl), new ItemStack(Items.quartz),
        new ItemStack(Items.quartz), new ItemStack(Items.quartz),
        new ItemStack(Items.quartz),
        new ItemStack(ConfigItems.itemResource, 1, 6),
        new ItemStack(ConfigItems.itemResource, 1, 6),
        new ItemStack(ConfigItems.itemResource, 1, 6),
        new ItemStack(Items.diamond));
  }
}
