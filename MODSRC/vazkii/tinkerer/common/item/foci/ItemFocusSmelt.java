package vazkii.tinkerer.common.item.foci;

import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;

public class ItemFocusSmelt extends ItemModFocus {

	private static final AspectList visUsage = new AspectList().add(Aspect.FIRE, 12).add(Aspect.ENTROPY, 2);
	
	public ItemFocusSmelt(int par1) {
		super(par1);
	}
	
	@Override
	boolean hasOrnament() {
		return true;
	}

	@Override
	public int getFocusColor() {
		return 0xFF0000;
	}

	@Override
	public AspectList getVisCost() {
		return visUsage;
	}

}
