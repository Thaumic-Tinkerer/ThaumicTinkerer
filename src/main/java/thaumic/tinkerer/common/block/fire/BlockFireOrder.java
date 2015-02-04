package thaumic.tinkerer.common.block.fire;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.research.ResearchPage;
import thaumcraft.common.config.ConfigItems;
import thaumic.tinkerer.common.core.handler.ConfigHandler;
import thaumic.tinkerer.common.core.helper.BlockTuple;
import thaumic.tinkerer.common.lib.LibBlockNames;
import thaumic.tinkerer.common.lib.LibResearch;
import thaumic.tinkerer.common.registry.ThaumicTinkererCrucibleRecipe;
import thaumic.tinkerer.common.registry.ThaumicTinkererRecipe;
import thaumic.tinkerer.common.research.IRegisterableResearch;
import thaumic.tinkerer.common.research.ResearchHelper;
import thaumic.tinkerer.common.research.TTResearchItem;

import java.util.ArrayList;
import java.util.HashMap;

public class BlockFireOrder extends BlockFireBase {

    public HashMap<BlockTuple, BlockTuple> fireResults = new HashMap<BlockTuple, BlockTuple>();
    private HashMap<Block, Block> oreDictinaryOresCache;

    public BlockFireOrder() {
        super();
        fireResults.put(new BlockTuple(Blocks.stone), null);
        fireResults.put(new BlockTuple(Blocks.glass), null);
        fireResults.put(new BlockTuple(Blocks.sand), null);
        fireResults.put(new BlockTuple(Blocks.gravel), null);

    }

    @Override
    public String getBlockName() {
        return LibBlockNames.BLOCK_FIRE_ORDER;
    }

    @Override
    public IRegisterableResearch getResearchItem() {
        if(!ConfigHandler.enableFire)
            return null;
        return (TTResearchItem) new TTResearchItem(LibResearch.KEY_FIRE_ORDO, new AspectList().add(Aspect.FIRE, 5).add(Aspect.ORDER, 5), 3, -3, 2, new ItemStack(this)).setParents(LibResearch.KEY_BRIGHT_NITOR).setConcealed()
                .setPages(new ResearchPage("0"), ResearchHelper.crucibleRecipePage(LibResearch.KEY_FIRE_ORDO)).setSecondary();
    }

    @Override
    public ThaumicTinkererRecipe getRecipeItem() {
        if(!ConfigHandler.enableFire)
            return null;
        return new ThaumicTinkererCrucibleRecipe(LibResearch.KEY_FIRE_ORDO, new ItemStack(this), new ItemStack(ConfigItems.itemShard, 1, 4), new AspectList().add(Aspect.FIRE, 5).add(Aspect.MAGIC, 5).add(Aspect.ORDER, 5));
    }

    @Override
    public int getDecayChance(World world, int x, int y, int z) {
        int dropSize = world.getBlock(x, y, z).getDrops(world, x, y, z, world.getBlockMetadata(x, y, z), 0).size();
        if (dropSize == 2) {
            return 2;
        }
        if (dropSize >= 3) {
            return 1;
        }
        return 3;
    }

    public HashMap<Block, Block> getOreDictionaryOres() {
        if (oreDictinaryOresCache == null) {
            HashMap<Block, Block> result = new HashMap<Block, Block>();
            for (String ore : OreDictionary.getOreNames()) {
                if (ore.startsWith("ore")) {
                    for (String block : OreDictionary.getOreNames()) {
                        if (block.startsWith("block") && block.substring(5).equalsIgnoreCase(ore.substring(3))) {
                            if (OreDictionary.getOres(block).size() > 0 && OreDictionary.getOres(ore).size() > 0) {
                                result.put(((ItemBlock) OreDictionary.getOres(ore).get(0).getItem()).field_150939_a, ((ItemBlock) OreDictionary.getOres(block).get(0).getItem()).field_150939_a);
                            }
                        }
                    }
                }
            }

            oreDictinaryOresCache = result;
        }
        return oreDictinaryOresCache;
    }

    @Override
    public HashMap<BlockTuple, BlockTuple> getBlockTransformation() {
        return fireResults;
    }

    @Override
    public boolean isTransmutationResult(BlockTuple block, World w, int x, int y, int z) {
        return getBlockTransformation(w, x, y, z).values().contains(block);
    }

    public ItemStack getBlockCraftingResult(World w, ItemStack itemStack) {
        InventoryCrafting blockCraftInventory = new InventoryCrafting(new Container() {
            @Override
            public boolean canInteractWith(EntityPlayer entityPlayer) {
                return false;
            }
        }, 3, 3);

        for (int i = 0; i < 9; i++) {
            blockCraftInventory.setInventorySlotContents(i, itemStack);
        }

        return CraftingManager.getInstance().findMatchingRecipe(blockCraftInventory, w);
    }

    private boolean allEqual(ArrayList<ItemStack> list) {
        for (ItemStack o : list) {
            if (!o.isItemEqual(list.get(0))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public HashMap<BlockTuple, BlockTuple> getBlockTransformation(World w, int x, int y, int z) {

        Block block = w.getBlock(x, y, z);
        int meta = w.getBlockMetadata(x, y, z);
        if (!fireResults.containsKey(new BlockTuple(block, meta))) {
            Block result = null;
            int resultMeta = 0;

            ArrayList<ItemStack> drops = block.getDrops(w, x, y, z, meta, 0);
            if (drops.size() > 0 && (drops.size() == 1 || allEqual(drops))) {

                ItemStack stack = drops.get(0);

                ItemStack blockStack = getBlockCraftingResult(w, stack);
                if (blockStack == null) {
                    ItemStack ingotStack = FurnaceRecipes.smelting().getSmeltingResult(stack);
                    blockStack = getBlockCraftingResult(w, ingotStack);
                }
                if (blockStack != null && Block.getBlockFromItem(blockStack.getItem()) != null) {
                    result = Block.getBlockFromItem(blockStack.getItem());
                    resultMeta = blockStack.getItemDamage();
                }
            }

            fireResults.put(new BlockTuple(block, meta), new BlockTuple(result, resultMeta));
        }
        return fireResults;
    }

    public boolean isTransmutationTarget(BlockTuple block, World w, int x, int y, int z) {
        return getBlockTransformation(w, x, y, z).keySet().contains(block) && getBlockTransformation().get(block) != null && getBlockTransformation().get(block).block != null;
    }
}
