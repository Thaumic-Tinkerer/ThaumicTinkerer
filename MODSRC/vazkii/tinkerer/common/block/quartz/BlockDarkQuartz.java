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
 * File Created @ [8 Sep 2013, 15:59:00 (GMT)]
 */
package vazkii.tinkerer.common.block.quartz;

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import vazkii.tinkerer.client.core.helper.IconHelper;
import vazkii.tinkerer.common.block.BlockMod;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockDarkQuartz extends BlockMod {

	private static final String[] iconNames = new String[] {"darkQuartz0", "chiseledDarkQuartz0", "pillarDarkQuartz0", null, null};
	private Icon[] darkQuartzIcons;
	private Icon chiseledDarkQuartzIcon;
	private Icon pillarDarkQuartzIcon;
	private Icon darkQuartzTopIcon;

	public BlockDarkQuartz(int par1) {
		super(par1, Material.rock);
		setHardness(0.8F);
		setResistance(10F);
	}

	@Override
	@SideOnly(Side.CLIENT)
    public Icon getIcon(int par1, int par2) {
        if (par2 != 2 && par2 != 3 && par2 != 4) {
            if (par1 != 1 && (par1 != 0 || par2 != 1)) {
                if (par1 == 0)
                	return darkQuartzTopIcon;
                else {
                    if (par2 < 0 || par2 >= darkQuartzIcons.length)
                        par2 = 0;

                    return darkQuartzIcons[par2];
                }
            }
            else return par2 == 1 ? chiseledDarkQuartzIcon : darkQuartzTopIcon;
        }
        else return par2 == 2 && (par1 == 1 || par1 == 0) ? pillarDarkQuartzIcon : par2 == 3 && (par1 == 5 || par1 == 4) ? pillarDarkQuartzIcon : par2 == 4 && (par1 == 2 || par1 == 3) ? pillarDarkQuartzIcon : darkQuartzIcons[par2];
    }

	@Override
	public int onBlockPlaced(World par1World, int par2, int par3, int par4, int par5, float par6, float par7, float par8, int par9) {
        if (par9 == 2) {
            switch (par5) {
                case 0:
                case 1:
                    par9 = 2;
                    break;
                case 2:
                case 3:
                    par9 = 4;
                    break;
                case 4:
                case 5:
                    par9 = 3;
            }
        }

        return par9;
    }

	@Override
	public int damageDropped(int par1) {
		return par1 != 3 && par1 != 4 ? par1 : 2;
	}

	@Override
	protected ItemStack createStackedBlock(int par1) {
		return par1 != 3 && par1 != 4 ? super.createStackedBlock(par1) : new ItemStack(blockID, 1, 2);
	}

	@Override
	public int getRenderType() {
		return 39;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List) {
		par3List.add(new ItemStack(par1, 1, 0));
		par3List.add(new ItemStack(par1, 1, 1));
		par3List.add(new ItemStack(par1, 1, 2));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		darkQuartzIcons = new Icon[iconNames.length];

		for (int i = 0; i < darkQuartzIcons.length; ++i) {
			if (iconNames[i] == null)
				darkQuartzIcons[i] = darkQuartzIcons[i - 1];
			else darkQuartzIcons[i] = IconHelper.forName(par1IconRegister, iconNames[i]);
		}

		darkQuartzTopIcon = IconHelper.forName(par1IconRegister, "darkQuartz1");
		chiseledDarkQuartzIcon = IconHelper.forName(par1IconRegister, "chiseledDarkQuartz1");
		pillarDarkQuartzIcon = IconHelper.forName(par1IconRegister, "pillarDarkQuartz1");
	}
}
