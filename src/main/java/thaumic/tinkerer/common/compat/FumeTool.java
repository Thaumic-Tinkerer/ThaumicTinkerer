package thaumic.tinkerer.common.compat;

import cpw.mods.fml.common.Optional;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.turtle.*;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.event.TextureStitchEvent;
import thaumic.tinkerer.common.ThaumicTinkerer;
import thaumic.tinkerer.common.block.BlockGas;
import thaumic.tinkerer.common.item.ItemGasRemover;

/**
 * Created by Katrina on 04/02/2015.
 */
@Optional.InterfaceList({
        @Optional.Interface(iface = "dan200.computercraft.api.turtle.ITurtleUpgrade", modid = "ComputerCraft")
})
public class FumeTool implements ITurtleUpgrade {

    @SideOnly(Side.CLIENT)
    public static IIcon icon;

    @Override
    @Optional.Method(modid = "ComputerCraft")
    public int getUpgradeID() {
        return 171;
    }

    @Override
    @Optional.Method(modid = "ComputerCraft")
    public String getUnlocalisedAdjective() {
        return "ttcomputer.dissipator";
    }


    @Override
    @Optional.Method(modid = "ComputerCraft")
    public TurtleUpgradeType getType() {
        return TurtleUpgradeType.Tool;
    }

    @Override
    @Optional.Method(modid = "ComputerCraft")
    public ItemStack getCraftingItem() {
        return new ItemStack(ThaumicTinkerer.registry.getFirstItemFromClass(ItemGasRemover.class));
    }

    @Override
    @Optional.Method(modid = "ComputerCraft")
    public IPeripheral createPeripheral(ITurtleAccess turtle, TurtleSide side) {
        return null;
    }


    @Override
    @Optional.Method(modid = "ComputerCraft")
    public TurtleCommandResult useTool(ITurtleAccess turtle, TurtleSide side,
                           TurtleVerb verb, int direction) {

        if(verb==TurtleVerb.Dig)
        {
            ChunkCoordinates pos=turtle.getPosition();
            int xs = (int) pos.posX;
            int ys = (int) pos.posY;
            int zs = (int) pos.posZ;

            for(int x = xs - 3; x < xs + 3; x++)
                for(int y = ys - 3; y < ys + 3; y++)
                    for(int z = zs - 3; z < zs + 3; z++) {
                        Block block = turtle.getWorld().getBlock(x, y, z);
                        if(block != null && block instanceof BlockGas) {
                            BlockGas gas = (BlockGas) block;
                            gas.placeParticle(turtle.getWorld(), x, y, z);
                            turtle.getWorld().setBlock(x, y, z, Blocks.air, 0, 1 | 2);
                        }
                    }

            //turtle.getWorld().playSoundAtEntity(turtle., "thaumcraft.wand", 0.2F, 1F);
            return TurtleCommandResult.success();
        }
        return TurtleCommandResult.failure();
    }

    @Override
    @Optional.Method(modid = "ComputerCraft")
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(ITurtleAccess turtle, TurtleSide side) {
        return icon;
    }

    @Override
    @Optional.Method(modid = "ComputerCraft")
    public void update(ITurtleAccess turtle, TurtleSide side) {

    }

    @SubscribeEvent
    @Optional.Method(modid = "ComputerCraft")
    public void registerIcons(TextureStitchEvent evt) {
        if (evt.map.getTextureType() == 1) icon = evt.map.registerIcon("ttinkerer:gasRemover");
    }
}
