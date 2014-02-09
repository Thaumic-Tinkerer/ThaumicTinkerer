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
	
	public static boolean isTConstructTool(ItemStack stack)
	{	
		if(stack==null)
			return false;
		Item item=stack.getItem();
		
		if(item instanceof ToolCore)
			return true;
		else
			return false;
	}
	
	public static int getDamage(ItemStack stack)
	{
		if(stack==null)
			return -1;
		if(!isTConstructTool(stack))
			return -1;
		
		if(ItemNBTHelper.verifyExistance(stack, TAG_ENERGY) || ItemNBTHelper.verifyExistance(stack, TAG_CHARGE))
			return -1;
		if(ItemNBTHelper.getNBT(stack).hasKey(TAG_INFINITOOL))
		{
			NBTTagCompound InfiniTool=ItemNBTHelper.getNBT(stack).getCompoundTag(TAG_INFINITOOL);
			if(InfiniTool.hasKey(TAG_BROKEN) && InfiniTool.getBoolean(TAG_BROKEN))
			{
				if(InfiniTool.hasKey(TAG_DURABILITY))
					return InfiniTool.getInteger(TAG_DURABILITY);
				else
					return -1;
			}
			if(InfiniTool.hasKey(TAG_DAMAGE))
			{
				return InfiniTool.getInteger(TAG_DAMAGE);
			}
		}
		return -1;
	}
	
	
	
	public static boolean fixDamage(ItemStack stack,int amount)
	{
		if(stack==null)
			return false;
		if(!isTConstructTool(stack))
			return false;
		
		if(ItemNBTHelper.verifyExistance(stack, TAG_ENERGY) || ItemNBTHelper.verifyExistance(stack, TAG_CHARGE))
			return false;
		if(ItemNBTHelper.getNBT(stack).hasKey(TAG_INFINITOOL))
		{
			NBTTagCompound InfiniTool=ItemNBTHelper.getNBT(stack).getCompoundTag(TAG_INFINITOOL);
			if(InfiniTool.hasKey(TAG_BROKEN) && InfiniTool.getBoolean(TAG_BROKEN))
			{
				InfiniTool.setBoolean(TAG_BROKEN, false);
				ItemNBTHelper.setCompound(stack, TAG_INFINITOOL, InfiniTool);
			}
			if(!InfiniTool.hasKey(TAG_DAMAGE))
				return false;
			int fDamage=InfiniTool.getInteger(TAG_DAMAGE);
			if(fDamage-amount<0)
				fDamage=0;
			InfiniTool.setInteger(TAG_DAMAGE, fDamage);
			ItemNBTHelper.setCompound(stack, TAG_INFINITOOL, InfiniTool);
		}
		return false;
	}
}
