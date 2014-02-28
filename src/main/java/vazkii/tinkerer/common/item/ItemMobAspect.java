package vazkii.tinkerer.common.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import thaumcraft.api.aspects.Aspect;
import vazkii.tinkerer.common.core.helper.NumericAspectHelper;

import java.util.List;

public class ItemMobAspect extends Item {

	public static final int aspectCount=100;

	public ItemMobAspect(int par1) {
		super(par1);
		setMaxStackSize(1);
	}

	@Override
	public void addInformation(ItemStack itemStack, EntityPlayer entityPlayer, List list, boolean par4) {
		list.add(getAspect(itemStack).getName());
	}

	public Aspect getAspect(ItemStack item){
		return NumericAspectHelper.getAspect(item.getItemDamage()%aspectCount);
	}

	public static ItemStack getStackFromAspect(Aspect a){
		ItemStack result = new ItemStack(ModItems.mobAspect);
		result.setItemDamage(NumericAspectHelper.getNumber(a));
		return result;
	}

	public boolean isCondensed(ItemStack item){
		return item.getItemDamage()>=aspectCount && item.getItemDamage()<aspectCount*2;
	}

	public boolean isInfused(ItemStack item){
		return item.getItemDamage()>=aspectCount*2;
	}

}
