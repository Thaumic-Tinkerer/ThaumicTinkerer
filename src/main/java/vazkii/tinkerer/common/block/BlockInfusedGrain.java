package vazkii.tinkerer.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockCrops;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import vazkii.tinkerer.common.item.ItemInfusedSeeds;
import vazkii.tinkerer.common.item.ModItems;

import java.util.ArrayList;

/**
 * Created by pixlepix on 4/14/14.
 */
public class BlockInfusedGrain extends BlockCrops {

    Aspect aspect;

    public BlockInfusedGrain(Aspect aspect){
        super();
        this.aspect=aspect;
    }

    private IIcon[] icons;

    //Code based off vanilla potato code
    @Override
    public IIcon getIcon(int side, int meta) {
            if (meta < 7)
            {
                if (meta == 6)
                {
                    meta = 5;
                }
                return this.icons[meta >> 1];
            }
            else
            {
                return this.icons[3];
            }
    }
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister p_149651_1_)
    {
        this.icons = new IIcon[4];

        for (int i = 0; i < this.icons.length; ++i)
        {
            this.icons[i] = p_149651_1_.registerIcon(this.getTextureName() + "_stage_" + i);
        }
    }

    @Override
    public int damageDropped(int p_149692_1_) {
        return ItemInfusedSeeds.getMetaForAspect(aspect);
    }

    protected Item func_149866_i(){
        return ModItems.infusedSeeds;
    }

    protected Item func_149865_P()
    {
        return Items.wheat;
    }
}
