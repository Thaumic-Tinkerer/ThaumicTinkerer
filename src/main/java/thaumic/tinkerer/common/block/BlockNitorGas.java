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
 * File Created @ [11 Sep 2013, 17:47:28 (GMT)]
 */
package thaumic.tinkerer.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import thaumic.tinkerer.common.ThaumicTinkerer;
import thaumic.tinkerer.common.core.handler.ConfigHandler;
import thaumic.tinkerer.common.item.ItemBrightNitor;
import thaumic.tinkerer.common.item.kami.armor.ItemGemLegs;
import thaumic.tinkerer.common.lib.LibBlockNames;

import java.util.List;
import java.util.Random;

public class BlockNitorGas extends BlockGas {

    public BlockNitorGas() {
        super();
    }

    @Override
    public int tickRate(World par1World) {
        return par1World.provider.dimensionId == -1 ? 60 : 20;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random) {
        if (par5Random.nextFloat() < 0.03F)
            ThaumicTinkerer.tcProxy.sparkle(par2 + 0.5F, par3 + 0.5F, par4 + 0.5F, 1F, 4, par5Random.nextFloat() / 2);
    }

    @Override
    public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random) {
        if (!par1World.isRemote) {
        	boolean remove=false;
            int dist = par1World.getBlockMetadata(par2, par3, par4) == 1 ? 6 : 1;
            List<EntityPlayer> players = par1World.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(par2 - dist, par3 - dist, par4 - dist, par2 + dist, par3 + dist, par4 + dist));
            if (players.isEmpty())
            {
                par1World.setBlockToAir(par2, par3, par4);
                remove=true;
            }
            else {
                boolean has = false;
                for (EntityPlayer player : players)
                    if (player.inventory.hasItem(ThaumicTinkerer.registry.getFirstItemFromClass(ItemBrightNitor.class)) || (ConfigHandler.enableKami && player.getCurrentArmor(1) != null && player.getCurrentArmor(1).getItem() == ThaumicTinkerer.registry.getFirstItemFromClass(ItemGemLegs.class))) {
                        has = true;
                        break;
                    }

                if (!has)
                {
                    par1World.setBlockToAir(par2, par3, par4);
                    remove=true;
                }
            }
            if(!remove)
            par1World.scheduleBlockUpdate(par2, par3, par4, this, tickRate(par1World));
        }
    }

    @Override
    public int getLightValue() {
        return 15;
    }

    @Override
    public int getLightValue(IBlockAccess world, int x, int y, int z) {
        return world.getBlockMetadata(x, y, z) == 1 ? 15 : 12;
    }

    @Override
    public void onBlockAdded(World par1World, int par2, int par3, int par4) {
        if (!par1World.isRemote)
            par1World.scheduleBlockUpdate(par2, par3, par4, this, tickRate(par1World));
    }

    @Override
    public String getBlockName() {
        return LibBlockNames.NITOR_GAS;
    }

    @Override
    public Class<? extends ItemBlock> getItemBlock() {
        return null;
    }

    @Override
    public Class<? extends TileEntity> getTileEntity() {
        return null;
    }
}