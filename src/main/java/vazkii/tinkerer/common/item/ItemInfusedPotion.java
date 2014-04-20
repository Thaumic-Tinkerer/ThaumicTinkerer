package vazkii.tinkerer.common.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import vazkii.tinkerer.common.potion.ModPotions;

/**
 * Created by pixlepix on 4/19/14.
 */
public class ItemInfusedPotion extends Item {


    @Override
    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10) {
        par2EntityPlayer.addPotionEffect(new PotionEffect(ModPotions.potionEarthId, 400000));
        return super.onItemUse(par1ItemStack, par2EntityPlayer, par3World, par4, par5, par6, par7, par8, par9, par10);
    }
}
