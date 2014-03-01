package vazkii.tinkerer.common.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import thaumcraft.api.aspects.Aspect;
import vazkii.tinkerer.common.core.helper.NumericAspectHelper;

import java.util.List;

public class ItemMobAspect extends Item {

	//Real value is 16
	//Padding room included
	//To prevent corruption
	public static final int aspectCount=20;

	public ItemMobAspect(int par1) {
		super(par1);
		setMaxStackSize(1);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		if(isCondensed(stack)){
			return super.getUnlocalizedName(stack)+".condensed";
		}
		if (isInfused(stack)){
			return super.getUnlocalizedName(stack)+".infused";
		}
		return super.getUnlocalizedName(stack);
	}
	@Override
	public void addInformation(ItemStack itemStack, EntityPlayer entityPlayer, List list, boolean par4) {
		list.add(getAspect(itemStack).getName());
	}

	public Aspect getAspect(ItemStack item){
		if(item==null){
			return null;
		}
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
