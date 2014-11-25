package appeng.api.recipes;

import net.minecraft.item.ItemStack;

import java.util.Arrays;
import java.util.List;

public class ResolverResultSet
{

	public final String name;
	public final List<ItemStack> results;

	public ResolverResultSet(String myName, ItemStack... set) {
		results = Arrays.asList( set );
		name = myName;
	}

}
