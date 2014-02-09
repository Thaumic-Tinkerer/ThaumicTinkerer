package vazkii.tinkerer.common.compat;

import tconstruct.library.tools.ToolCore;
import vazkii.tinkerer.common.core.helper.ItemNBTHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TinkersConstructCompat {

	private static final String TAG_INFINITOOL = "InfiTool";
	private static final String TAG_BROKEN = "Broken";
	private static final String TAG_DAMAGE = "Damage";
	private static final String TAG_DURABILITY = "TotalDurability";
	private static final String TAG_CHARGE = "charge";
	private static final String TAG_ENERGY = "Energy";
	
	public boolean isTConstructTool(ItemStack stack)
	{	
		Item item=stack.getItem();
		
		if(item instanceof ToolCore)
			return true;
		else
			return false;
	}
	
	public int getDamage(ItemStack stack)
	{
		if(!isTConstructTool(stack))
			return -1;
		
		if(ItemNBTHelper.verifyExistance(stack, TAG_ENERGY) || ItemNBTHelper.verifyExistance(stack, TAG_CHARGE))
			return -1;
		
		return -1;
	}
	
	
	
	public boolean fixDamage(ItemStack item,int amount)
	{
		return false;
		
	}
}
