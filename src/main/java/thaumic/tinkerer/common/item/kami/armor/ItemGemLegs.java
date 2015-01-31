/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the ThaumicTinkerer Mod.
 *
 * ThaumicTinkerer is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * ThaumicTinkerer is a Derivative Work on Thaumcraft 4.
 * Thaumcraft 4 (c) Azanor 2012
 * (http://www.minecraftforum.net/topic/1585216-)
 *
 * File Created @ [Dec 27, 2013, 1:03:05 AM (GMT)]
 */
package thaumic.tinkerer.common.item.kami.armor;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MathHelper;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.research.ResearchPage;
import thaumcraft.codechicken.lib.vec.Vector3;
import thaumcraft.common.config.ConfigItems;
import thaumic.tinkerer.common.ThaumicTinkerer;
import thaumic.tinkerer.common.item.ItemBrightNitor;
import thaumic.tinkerer.common.item.foci.ItemFocusSmelt;
import thaumic.tinkerer.common.item.kami.ItemKamiResource;
import thaumic.tinkerer.common.lib.LibItemNames;
import thaumic.tinkerer.common.lib.LibResearch;
import thaumic.tinkerer.common.registry.ThaumicTinkererInfusionRecipe;
import thaumic.tinkerer.common.registry.ThaumicTinkererRecipe;
import thaumic.tinkerer.common.research.IRegisterableResearch;
import thaumic.tinkerer.common.research.KamiResearchItem;
import thaumic.tinkerer.common.research.ResearchHelper;

public class ItemGemLegs extends ItemIchorclothArmorAdv {

    public ItemGemLegs() {
        super(2);

    }

    @Override
    boolean ticks() {
        return true;
    }

    @Override
    void tickPlayer(EntityPlayer player) {
        ItemStack armor = player.getCurrentArmor(1);
        if (armor.getItemDamage() == 1 || !ThaumicTinkerer.proxy.armorStatus(player))
            return;
        PotionEffect effect=player.getActivePotionEffect(Potion.fireResistance);
        if (effect != null && effect.duration <= 202)
            effect.duration = 202;
        else
            player.addPotionEffect(new PotionEffect(Potion.fireResistance.id,
                    202, 10, true));
        ItemBrightNitor.meta = 1;
        ThaumicTinkerer.registry.getFirstItemFromClass(ItemBrightNitor.class).onUpdate(null, player.worldObj, player, 0, false);
        ItemBrightNitor.meta = 0;

        int x = (int) Math.floor(player.posX);
        int y = (int) player.posY + 1;
        int z = (int) Math.floor(player.posZ);

        float yaw = MathHelper.wrapAngleTo180_float(player.rotationYaw + 90F) * (float) Math.PI / 180F;
        Vector3 lookVector = new Vector3(Math.cos(yaw), Math.sin(yaw), 0).normalize();
        Vector3 newVector = new Vector3(lookVector.x, lookVector.y, 0);

        for (int i = 0; i < 5; i++) {
            newVector = newVector.add(lookVector);

            int x1 = x + (int) newVector.x;
            int z1 = z + (int) newVector.y;
            ItemBrightNitor.setBlock(x1, y, z1, player.worldObj);
        }
    }

    @Override
    public String getItemName() {
        return LibItemNames.ICHOR_LEGS_GEM;
    }

    @Override
    public IRegisterableResearch getResearchItem() {
        return (IRegisterableResearch) new KamiResearchItem(LibResearch.KEY_ICHORCLOTH_LEGS_GEM, new AspectList().add(Aspect.FIRE, 2).add(Aspect.HEAL, 1).add(Aspect.GREED, 1).add(Aspect.ENERGY, 1), 17, 9, 5, new ItemStack(this)).setParents(LibResearch.KEY_ICHORCLOTH_ARMOR)
                .setPages(new ResearchPage("0"), ResearchHelper.infusionPage(LibResearch.KEY_ICHORCLOTH_LEGS_GEM), new ResearchPage("1"));

    }

    @Override
    public ThaumicTinkererRecipe getRecipeItem() {
        return new ThaumicTinkererInfusionRecipe(LibResearch.KEY_ICHORCLOTH_LEGS_GEM, new ItemStack(this), 13, new AspectList().add(Aspect.FIRE, 50).add(Aspect.ARMOR, 32).add(Aspect.HEAL, 32).add(Aspect.ENERGY, 32).add(Aspect.LIGHT, 64).add(Aspect.GREED, 16).add(Aspect.ELDRITCH, 16), (new ItemStack(ThaumicTinkerer.registry.getItemFromClassAndName(ItemIchorclothArmor.class, LibItemNames.ICHOR_LEGS))),
                new ItemStack(Items.diamond, 1), new ItemStack(ThaumicTinkerer.registry.getFirstItemFromClass(ItemKamiResource.class)), new ItemStack(ThaumicTinkerer.registry.getFirstItemFromClass(ItemKamiResource.class)), new ItemStack(ConfigItems.itemFocusPrimal), new ItemStack(ConfigItems.itemThaumonomicon), new ItemStack(Items.golden_chestplate), new ItemStack(Items.potionitem, 1, 8195), new ItemStack(ThaumicTinkerer.registry.getFirstItemFromClass(ItemFocusSmelt.class)), new ItemStack(ThaumicTinkerer.registry.getFirstItemFromClass(ItemBrightNitor.class)), new ItemStack(Items.lava_bucket), new ItemStack(Items.fire_charge), new ItemStack(Items.blaze_rod));

    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onDamageTaken(LivingHurtEvent event) {
        if (event.entityLiving instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.entityLiving;
            if (player.getCurrentArmor(1) != null && player.getCurrentArmor(1).getItem() == this && event.source.isFireDamage() && ThaumicTinkerer.proxy.armorStatus(player)) {
                event.setCanceled(true);
                player.heal(event.ammount);
            }
        }
    }
}
