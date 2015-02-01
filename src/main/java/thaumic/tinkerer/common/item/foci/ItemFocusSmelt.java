package thaumic.tinkerer.common.item.foci;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.MovingObjectPosition;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.research.ResearchPage;
import thaumcraft.api.wands.FocusUpgradeType;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.items.wands.ItemWandCasting;
import thaumcraft.common.lib.utils.BlockUtils;
import thaumic.tinkerer.common.ThaumicTinkerer;
import thaumic.tinkerer.common.lib.LibItemNames;
import thaumic.tinkerer.common.lib.LibResearch;
import thaumic.tinkerer.common.registry.ThaumicTinkererArcaneRecipe;
import thaumic.tinkerer.common.registry.ThaumicTinkererRecipe;
import thaumic.tinkerer.common.research.IRegisterableResearch;
import thaumic.tinkerer.common.research.ResearchHelper;
import thaumic.tinkerer.common.research.TTResearchItem;

import java.util.HashMap;
import java.util.Map;

public class ItemFocusSmelt extends ItemModFocus {

    private static final AspectList visUsage = new AspectList().add(Aspect.FIRE, 45).add(Aspect.ENTROPY, 12);
    public static Map<String, SmeltData> playerData = new HashMap();
	
	private int soundCooldown = 3; // In ticks

    public ItemFocusSmelt() {
        super();
    }

    @Override
    public String getItemName() {
        return LibItemNames.FOCUS_SMELT;
    }

    @Override
    public IRegisterableResearch getResearchItem() {
        return (TTResearchItem) new TTResearchItem(LibResearch.KEY_FOCUS_SMELT, new AspectList().add(Aspect.FIRE, 2).add(Aspect.ENERGY, 1).add(Aspect.MAGIC, 1), -2, -2, 2, new ItemStack(this)).setParents("FOCUSEXCAVATION").setConcealed()
                .setPages(new ResearchPage("0"), ResearchHelper.arcaneRecipePage(LibResearch.KEY_FOCUS_SMELT));

    }

    @Override
    public ThaumicTinkererRecipe getRecipeItem() {
        return new ThaumicTinkererArcaneRecipe(LibResearch.KEY_FOCUS_SMELT, LibResearch.KEY_FOCUS_SMELT, new ItemStack(this), new AspectList().add(Aspect.FIRE, 10).add(Aspect.ORDER, 5).add(Aspect.ENTROPY, 6),
                "FNE",
                'F', new ItemStack(ConfigItems.itemFocusFire),
                'E', new ItemStack(ConfigItems.itemFocusExcavation),
                'N', new ItemStack(ConfigItems.itemResource, 1, 1));
    }

    @Override
    public boolean isUseItem(ItemStack stack) {
        return true;
    }

    @Override
    public void onUsingFocusTick(ItemStack stack, EntityPlayer p, int time) {
        ItemWandCasting wand = (ItemWandCasting) stack.getItem();
        if (!wand.consumeAllVis(stack, p, visUsage, false, false))
            return;

        MovingObjectPosition pos = BlockUtils.getTargetBlock(p.worldObj, p, false);

        if (pos != null) {
            Block block = p.worldObj.getBlock(pos.blockX, pos.blockY, pos.blockZ);
            int meta = p.worldObj.getBlockMetadata(pos.blockX, pos.blockY, pos.blockZ);

            ItemStack blockStack = new ItemStack(block, 1, meta);
            ItemStack result = FurnaceRecipes.smelting().getSmeltingResult(blockStack);

            if (result != null && result.getItem() instanceof ItemBlock) {
                boolean decremented = false;

                if (playerData.containsKey(p.getGameProfile().getName())) {
                    SmeltData data = playerData.get(p.getGameProfile().getName());

                    if (data.equalPos(pos)) {
                        data.progress--;
                        decremented = true;
                        if (data.progress <= 0) {
                            if (!p.worldObj.isRemote) {
                                p.worldObj.setBlock(pos.blockX, pos.blockY, pos.blockZ, Block.getBlockFromItem(result.getItem()), result.getItemDamage(), 1 | 2);
                                wand.consumeAllVis(stack, p, visUsage, true, false);
                                playerData.remove(p.getGameProfile().getName());
                                decremented = false;
                            }
                            //  Sound
                            ////////////////////
                            p.worldObj.playSoundAtEntity(p, "fire.ignite", 0.6F, 1F);
                            p.worldObj.playSoundAtEntity(p, "fire.fire", 1F, 1F);
                            //  Particle
                            ////////////////////
                            for (int i = 0; i < 25; i++) {
                                double x = pos.blockX + Math.random();
                                double y = pos.blockY + Math.random();
                                double z = pos.blockZ + Math.random();

                                ThaumicTinkerer.tcProxy.wispFX2(p.worldObj, x, y, z, (float) Math.random() / 2F, 4, true, true, (float) -Math.random() / 10F);
                            }
                        }
                    }
                }

                if (!decremented) {
                    int potency = wand.getFocusPotency(stack);
                    playerData.put(p.getGameProfile().getName(), new SmeltData(pos, 20 - Math.min(3, potency) * 5));
                } else{
                    //  Sound
                    ////////////////////
                    if (time % soundCooldown == 0)
                            p.worldObj.playSoundAtEntity(p, "fire.fire", 0.2F, 1F);
                    //  Particle
                    ////////////////////
                    for (int i = 0; i < 2; i++) {
                        double x = pos.blockX + Math.random();
                        double y = pos.blockY + Math.random();
                        double z = pos.blockZ + Math.random();

                        ThaumicTinkerer.tcProxy.wispFX2(p.worldObj, x, y, z, (float) Math.random() / 2F, 4, true, true, (float) -Math.random() / 10F);
                    }
                }

                if (p.worldObj.isRemote)
                    ThaumicTinkerer.tcProxy.beamCont(p.worldObj, p, pos.blockX + 0.5, pos.blockY + 0.5, pos.blockZ + 0.5, 2, 0xFF0000, true, 0F, null, 1);
            }
        }
    }

    @Override
    public String getSortingHelper(ItemStack itemstack) {
        return "SMELT";
    }

    @Override
    protected boolean hasOrnament() {
        return true;
    }

    @Override
    public int getFocusColor(ItemStack stack) {
        return 0xFF0000;
    }

    @Override
    public AspectList getVisCost(ItemStack stack) {
        return visUsage;
    }

    @Override
    public FocusUpgradeType[] getPossibleUpgradesByRank(ItemStack itemStack, int i) {
        return new FocusUpgradeType[]{FocusUpgradeType.treasure, FocusUpgradeType.potency};
    }

    static class SmeltData {
        public MovingObjectPosition pos;
        public int progress;

        public SmeltData(MovingObjectPosition pos, int progress) {
            this.pos = pos;
            this.progress = progress;
        }

        public boolean equalPos(MovingObjectPosition pos) {
            return pos.blockX == this.pos.blockX && pos.blockY == this.pos.blockY && pos.blockZ == this.pos.blockZ;
        }
    }

}
