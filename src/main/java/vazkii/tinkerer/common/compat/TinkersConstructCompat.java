
package vazkii.tinkerer.common.compat;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import tconstruct.library.tools.AbilityHelper;
import tconstruct.library.tools.ToolCore;
import vazkii.tinkerer.common.core.helper.ItemNBTHelper;

public class TinkersConstructCompat {

	private static final String TAG_INFINITOOL = "InfiTool";
	private static final String TAG_BROKEN = "Broken";
	private static final String TAG_DAMAGE = "Damage";
	private static final String TAG_DURABILITY = "TotalDurability";
	private static final String TAG_CHARGE = "charge";
	private static final String TAG_ENERGY = "Energy";
	private static final String TAG_REPAIRCOUNT = "RepairCount";

	public static boolean isTConstructTool(ItemStack stack) {
		if (stack == null)
			return false;
		Item item = stack.getItem();

		if (item instanceof ToolCore) {
			return !(ItemNBTHelper.verifyExistance(stack, TAG_ENERGY) || ItemNBTHelper.verifyExistance(stack, TAG_CHARGE));
		} else
			return false;
	}

	public static int getDamage(ItemStack stack) {
		if (stack == null)
			return -1;
		if (!isTConstructTool(stack))
			return -1;

		if (ItemNBTHelper.verifyExistance(stack, TAG_ENERGY) || ItemNBTHelper.verifyExistance(stack, TAG_CHARGE))
			return -1;
		if (ItemNBTHelper.getNBT(stack).hasKey(TAG_INFINITOOL)) {
			NBTTagCompound InfiniTool = ItemNBTHelper.getNBT(stack).getCompoundTag(TAG_INFINITOOL);
			if (InfiniTool.hasKey(TAG_BROKEN) && InfiniTool.getBoolean(TAG_BROKEN)) {
				if (InfiniTool.hasKey(TAG_DURABILITY))
					return InfiniTool.getInteger(TAG_DURABILITY);
				else
					return -1;
			}
			if (InfiniTool.hasKey(TAG_DAMAGE)) {
				return InfiniTool.getInteger(TAG_DAMAGE);
			}
		}
		return -1;
	}

	public static boolean fixDamage(ItemStack stack, int amount) {
		if (stack == null)
			return false;
		if (!isTConstructTool(stack))
			return false;

		if (ItemNBTHelper.verifyExistance(stack, TAG_ENERGY) || ItemNBTHelper.verifyExistance(stack, TAG_CHARGE))
			return false;
		if (ItemNBTHelper.getNBT(stack).hasKey(TAG_INFINITOOL)) {
			NBTTagCompound InfiniTool = ItemNBTHelper.getNBT(stack).getCompoundTag(TAG_INFINITOOL);
			InfiniTool.setBoolean("Broken", false);
			int damage = InfiniTool.getInteger("Damage");
			int itemsUsed = 0;
			int increase = calculateIncrease(stack, amount);
			int repair = InfiniTool.getInteger("RepairCount");
			repair += itemsUsed;
			InfiniTool.setInteger("RepairCount", repair);

			damage -= increase;
			if (damage < 0)
				damage = 0;
			InfiniTool.setInteger("Damage", damage);

			AbilityHelper.damageTool(stack, 0, null, true);
			ItemNBTHelper.setCompound(stack, TAG_INFINITOOL, InfiniTool);
			AbilityHelper.damageTool(stack, 0, null, true);
		}
		return false;
	}

	private static int calculateIncrease(ItemStack tool, int amount) {
		NBTTagCompound tags = tool.getTagCompound().getCompoundTag("InfiTool");
		int increase = amount * 2;

		int modifiers = tags.getInteger("Modifiers");
		float mods = 1.0f;
		if (modifiers == 2)
			mods = 1.0f;
		else if (modifiers == 1)
			mods = 0.75f;
		else if (modifiers == 0)
			mods = 0.5f;

		increase *= mods;

		int repair = tags.getInteger("RepairCount");
		float repairCount = (100 - repair) / 100f;
		if (repairCount < 0.5f)
			repairCount = 0.5f;
		increase *= repairCount;
		increase /= ((ToolCore) tool.getItem()).getRepairCost();
		return increase;
	}
}

