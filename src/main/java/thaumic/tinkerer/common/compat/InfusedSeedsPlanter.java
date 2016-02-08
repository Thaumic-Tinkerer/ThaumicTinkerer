package thaumic.tinkerer.common.compat;

import com.enderio.core.common.compat.ICompat;
import com.enderio.core.common.util.BlockCoord;
import crazypants.enderio.machine.farm.TileFarmStation;
import crazypants.enderio.machine.farm.farmers.IHarvestResult;
import crazypants.enderio.machine.farm.farmers.PlantableFarmer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFarmland;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;
import thaumic.tinkerer.common.ThaumicTinkerer;
import thaumic.tinkerer.common.block.BlockInfusedFarmland;
import thaumic.tinkerer.common.block.BlockInfusedGrain;
import thaumic.tinkerer.common.block.tile.TileInfusedGrain;
import thaumic.tinkerer.common.item.ItemInfusedSeeds;

/**
 * Created by katsw on 02/02/2016.
 */
public class InfusedSeedsPlanter extends PlantableFarmer implements ICompat{


    @Override
    public boolean canPlant(ItemStack stack) {
        return stack.getItem()==ThaumicTinkerer.registry.getFirstItemFromClass(ItemInfusedSeeds.class);
    }

    @Override
    protected boolean canPlant(World worldObj, BlockCoord bc, IPlantable plantable) {
        return (super.canPlant(worldObj, bc, plantable) && plantable==ThaumicTinkerer.registry.getFirstItemFromClass(ItemInfusedSeeds.class) )|| worldObj.getBlock(bc.x,bc.y-1,bc.z)==ThaumicTinkerer.registry.getFirstBlockFromClass(BlockInfusedFarmland.class);
    }

    @Override
    public boolean canHarvest(TileFarmStation farm, BlockCoord bc, Block block, int meta) {
        return super.canHarvest(farm, bc, block, meta) && block==ThaumicTinkerer.registry.getFirstBlockFromClass(BlockInfusedGrain.class);
    }

    @Override
    public IHarvestResult harvestBlock(TileFarmStation farm, BlockCoord bc, Block block, int meta) {
        return super.harvestBlock(farm, bc, block, meta);
    }

    @Override
    public boolean prepareBlock(TileFarmStation farm, BlockCoord bc, Block block, int meta) {

        boolean ready= super.prepareBlock(farm, bc, block, meta);
        return ready;
    }

    @Override
    protected boolean plant(TileFarmStation farm, World worldObj, BlockCoord bc, IPlantable plantable) {

            ItemStack seeds=farm.getSeedTypeInSuppliesFor(bc);
            if(seeds!=null) {
                if (farm.getBlock(bc.getLocation(ForgeDirection.DOWN)) instanceof BlockFarmland) {

                    BlockCoord soil = bc.getLocation(ForgeDirection.DOWN);
                    farm.getWorldObj().setBlock(soil.x, soil.y, soil.z, ThaumicTinkerer.registry.getFirstBlockFromClass(BlockInfusedFarmland.class));

                }
            }
       super.plant(farm, worldObj, bc, plantable);
        if(seeds!=null) {
            BlockInfusedGrain.setAspect(farm.getWorldObj(), bc.x, bc.y, bc.z, ItemInfusedSeeds.getAspect(seeds));
            ((TileInfusedGrain) farm.getWorldObj().getTileEntity(bc.x, bc.y, bc.z)).primalTendencies = ItemInfusedSeeds.getAspectTendencies(seeds);
        }
        return true;
    }
}
