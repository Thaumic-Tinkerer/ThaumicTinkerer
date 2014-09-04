package thaumic.tinkerer.common.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.research.ResearchPage;
import thaumic.tinkerer.client.core.helper.IconHelper;
import thaumic.tinkerer.common.ThaumicTinkerer;
import thaumic.tinkerer.common.lib.LibItemNames;
import thaumic.tinkerer.common.lib.LibResearch;
import thaumic.tinkerer.common.potion.ModPotions;
import thaumic.tinkerer.common.registry.ITTinkererItem;
import thaumic.tinkerer.common.registry.ThaumicTinkererCrucibleRecipe;
import thaumic.tinkerer.common.registry.ThaumicTinkererRecipe;
import thaumic.tinkerer.common.registry.ThaumicTinkererRecipeMulti;
import thaumic.tinkerer.common.research.IRegisterableResearch;
import thaumic.tinkerer.common.research.ResearchHelper;
import thaumic.tinkerer.common.research.TTResearchItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pixlepix on 4/19/14.
 */
public class ItemInfusedPotion extends ItemPotion implements ITTinkererItem {

	public String getItemStackDisplayName(ItemStack par1ItemStack) {
		String s = "item.infusedPotion." + getAspect(par1ItemStack).getName() + ".name";
		return StatCollector.translateToLocal(s).trim();
	}

	@Override
	public List getEffects(ItemStack par1ItemStack) {
		return getEffects(par1ItemStack.getItemDamage());
	}

	@Override
	public List getEffects(int par1) {
		List r = new ArrayList();
		switch (par1) {
			case 0:
				r.add(new PotionEffect(ModPotions.potionAirId, 3600));
				break;
			case 1:

				r.add(new PotionEffect(ModPotions.potionFireId, 3600));
				break;
			case 2:

				r.add(new PotionEffect(ModPotions.potionEarthId, 3600));
				break;
			case 3:

				r.add(new PotionEffect(ModPotions.potionWaterId, 3600));
				break;
		}

		return r;
	}

    private IIcon[] icons;

    @Override
    public void registerIcons(IIconRegister par1IconRegister) {
        icons = new IIcon[4];
        icons[0] = IconHelper.forName(par1IconRegister, "potion_aer");
        icons[1] = IconHelper.forName(par1IconRegister, "potion_ignis");
        icons[2] = IconHelper.forName(par1IconRegister, "potion_terra");
        icons[3] = IconHelper.forName(par1IconRegister, "potion_aqua");
    }

    @Override
    public IIcon getIconFromDamage(int par1) {
        return icons[par1];
    }

    @Override
    public IIcon getIconFromDamageForRenderPass(int par1, int par2) {
        //Override default potion overlay behavior
        return icons[par1];
    }

    @Override
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		par3List.add(getAspect(par1ItemStack).getName());
	}

	public static int getMetaForAspect(Aspect aspect) {
		for (PRIMAL_ASPECT_ENUM e : PRIMAL_ASPECT_ENUM.values()) {
			if (aspect == e.aspect) {
				return e.ordinal();
			}
		}
		return 0;
	}

	public Aspect getAspect(ItemStack stack) {
		return PRIMAL_ASPECT_ENUM.values()[stack.getItemDamage()].aspect;
	}

	@Override
	public boolean getHasSubtypes() {
		return true;
	}

	@Override
	public void getSubItems(Item item, CreativeTabs tab, List l) {
		for (PRIMAL_ASPECT_ENUM primal : PRIMAL_ASPECT_ENUM.values()) {
			l.add(new ItemStack(item, 1, primal.ordinal()));
		}
	}

	@Override
	public ArrayList<Object> getSpecialParameters() {
		return null;
	}

	@Override
	public String getItemName() {
		return LibItemNames.INFUSED_POTION;
	}

	@Override
	public boolean shouldRegister() {
		return true;
	}

	@Override
	public boolean shouldDisplayInTab() {
		return true;
	}

	@Override
	public IRegisterableResearch getResearchItem() {
        return (TTResearchItem) new TTResearchItem(LibResearch.KEY_POTIONS, new AspectList().add(Aspect.WATER, 5).add(Aspect.ENTROPY, 5), 7, -5, 2, ItemInfusedSeeds.getStackFromAspect(Aspect.FIRE)).setParents(LibResearch.KEY_FIRE_PERDITIO, LibResearch.KEY_FIRE_ORDO, LibResearch.KEY_FIRE_IGNIS, LibResearch.KEY_FIRE_TERRA, LibResearch.KEY_FIRE_AER, LibResearch.KEY_FIRE_AQUA).setParentsHidden("INFUSION").setConcealed()
                .setPages(new ResearchPage("0"), new ResearchPage("1"), new ResearchPage("2"), new ResearchPage("3"), new ResearchPage("4"), new ResearchPage("5"), ResearchHelper.infusionPage(LibResearch.KEY_POTIONS, 4), ResearchHelper.crucibleRecipePage(LibResearch.KEY_POTIONS + "POT0"), ResearchHelper.crucibleRecipePage(LibResearch.KEY_POTIONS + "POT1"), ResearchHelper.crucibleRecipePage(LibResearch.KEY_POTIONS + "POT2"), ResearchHelper.crucibleRecipePage(LibResearch.KEY_POTIONS + "POT3"));
    }

	@Override
	public ThaumicTinkererRecipe getRecipeItem() {
		return new ThaumicTinkererRecipeMulti(
                new ThaumicTinkererCrucibleRecipe(LibResearch.KEY_POTIONS + "POT0", new ItemStack(this, 1, 0), new ItemStack(ThaumicTinkerer.registry.getFirstItemFromClass(ItemInfusedGrain.class), 1, 0), new AspectList().add(Aspect.AURA, 5).add(Aspect.AIR, 5)),

                new ThaumicTinkererCrucibleRecipe(LibResearch.KEY_POTIONS + "POT1", new ItemStack(this, 1, 1), new ItemStack(ThaumicTinkerer.registry.getFirstItemFromClass(ItemInfusedGrain.class), 1, 1), new AspectList().add(Aspect.AURA, 5).add(Aspect.FIRE, 5)),

                new ThaumicTinkererCrucibleRecipe(LibResearch.KEY_POTIONS + "POT2", new ItemStack(this, 1, 2), new ItemStack(ThaumicTinkerer.registry.getFirstItemFromClass(ItemInfusedGrain.class), 1, 2), new AspectList().add(Aspect.AURA, 5).add(Aspect.EARTH, 5)),

                new ThaumicTinkererCrucibleRecipe(LibResearch.KEY_POTIONS + "POT3", new ItemStack(this, 1, 3), new ItemStack(ThaumicTinkerer.registry.getFirstItemFromClass(ItemInfusedGrain.class), 1, 3), new AspectList().add(Aspect.AURA, 5).add(Aspect.WATER, 5))
        );
	}

	private enum PRIMAL_ASPECT_ENUM {
		AIR(Aspect.AIR),
		FIRE(Aspect.FIRE),
		EARTH(Aspect.EARTH),
		WATER(Aspect.WATER);
		Aspect aspect;

		PRIMAL_ASPECT_ENUM(Aspect a) {
			this.aspect = a;
		}
	}
}
