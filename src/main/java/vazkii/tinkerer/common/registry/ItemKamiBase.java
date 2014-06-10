package vazkii.tinkerer.common.registry;

import vazkii.tinkerer.common.core.handler.ConfigHandler;
import vazkii.tinkerer.common.research.IRegisterableResearch;

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
	public IRegisterableResearch getResearchItem() {
		return null;
	}
}
