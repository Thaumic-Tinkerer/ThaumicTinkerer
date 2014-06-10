package vazkii.tinkerer.common.registry;

import vazkii.tinkerer.common.core.handler.ConfigHandler;
import vazkii.tinkerer.common.research.TTResearchItem;

public abstract class ItemKamiBase extends ItemBase {
	@Override
	public boolean shouldRegister() {
		return ConfigHandler.enableKami;
	}

	@Override
	public boolean shouldDisplayInTab() {
		return true;
	}

	@Override
	public TTResearchItem getResearchItem() {
		return null;
	}
}
