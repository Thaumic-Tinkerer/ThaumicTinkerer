package thaumic.tinkerer.common.registry;

import thaumic.tinkerer.common.core.handler.ConfigHandler;
import thaumic.tinkerer.common.research.IRegisterableResearch;

public abstract class ItemKamiBase extends ItemBase {
    @Override
    public boolean shouldRegister() {
        return ConfigHandler.enableKami;
    }

    @Override
    public boolean shouldDisplayInTab() {
        return ConfigHandler.enableKami;
    }

    @Override
    public IRegisterableResearch getResearchItem() {
        return null;
    }

    @Override
    public ThaumicTinkererRecipe getRecipeItem() {
        return null;
    }
}
