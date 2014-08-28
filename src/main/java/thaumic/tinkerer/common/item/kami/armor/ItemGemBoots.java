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
 * File Created @ [Dec 27, 2013, 4:12:03 PM (GMT)]
 */
package thaumic.tinkerer.common.item.kami.armor;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.research.ResearchPage;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.config.ConfigItems;
import thaumic.tinkerer.common.ThaumicTinkerer;
import thaumic.tinkerer.common.item.kami.ItemKamiResource;
import thaumic.tinkerer.common.lib.LibItemNames;
import thaumic.tinkerer.common.lib.LibResearch;
import thaumic.tinkerer.common.registry.ThaumicTinkererInfusionRecipe;
import thaumic.tinkerer.common.registry.ThaumicTinkererRecipe;
import thaumic.tinkerer.common.research.IRegisterableResearch;
import thaumic.tinkerer.common.research.KamiResearchItem;
import thaumic.tinkerer.common.research.ResearchHelper;

import java.util.ArrayList;
import java.util.List;

public class ItemGemBoots extends ItemIchorclothArmorAdv {

	public static List<String> playersWith1Step = new ArrayList();

	public ItemGemBoots() {
		super(3);
	}

	@Override
	boolean ticks() {
		return true;
	}

	@Override
	void tickPlayer(EntityPlayer player) {
		ItemStack armor = player.getCurrentArmor(0);
		if (!ThaumicTinkerer.proxy.armorStatus(player) || armor.getItemDamage() == 1)
			return;
		player.addPotionEffect(new PotionEffect(Potion.digSpeed.id, 2, 1, true));

		if (player.worldObj.isRemote)
			player.stepHeight = player.isSneaking() ? 0.5F : 1F;
		if ((player.onGround || player.capabilities.isFlying) && player.moveForward > 0F)
			player.moveFlying(0F, 1F, player.capabilities.isFlying ? 0.075F : 0.15F);
		player.jumpMovementFactor = player.isSprinting() ? 0.05F : 0.04F;
		player.fallDistance = 0F;

		int x = (int) player.posX;
		int y = (int) player.posY - 1;
		int z = (int) player.posZ;
		if (player.worldObj.getBlock(x, y, z) == Blocks.dirt && player.worldObj.getBlockMetadata(x, y, z) == 0)
			player.worldObj.setBlock(x, y, z, Blocks.grass, 0, 2);
	}

	@Override
	public String getItemName() {
		return LibItemNames.ICHOR_BOOTS_GEM;
	}

	@Override
	public IRegisterableResearch getResearchItem() {
		return (IRegisterableResearch) new KamiResearchItem(LibResearch.KEY_ICHORCLOTH_BOOTS_GEM, new AspectList().add(Aspect.EARTH, 2).add(Aspect.TRAVEL, 1).add(Aspect.MINE, 1).add(Aspect.PLANT, 1), 15, 10, 5, new ItemStack(this)).setParents(LibResearch.KEY_ICHORCLOTH_ARMOR)
				.setPages(new ResearchPage("0"), ResearchHelper.infusionPage(LibResearch.KEY_ICHORCLOTH_BOOTS_GEM));

	}

	@Override
	public ThaumicTinkererRecipe getRecipeItem() {
		return new ThaumicTinkererInfusionRecipe(LibResearch.KEY_ICHORCLOTH_BOOTS_GEM, new ItemStack(this), 13, new AspectList().add(Aspect.EARTH, 50).add(Aspect.ARMOR, 32).add(Aspect.MINE, 32).add(Aspect.MOTION, 32).add(Aspect.LIGHT, 64).add(Aspect.PLANT, 16).add(Aspect.TRAVEL, 16), new ItemStack(ThaumicTinkerer.registry.getItemFromClassAndName(ItemIchorclothArmor.class, LibItemNames.ICHOR_BOOTS)),
				new ItemStack(Items.diamond, 1), new ItemStack(ThaumicTinkerer.registry.getFirstItemFromClass(ItemKamiResource.class)), new ItemStack(ThaumicTinkerer.registry.getFirstItemFromClass(ItemKamiResource.class)), new ItemStack(ConfigItems.itemThaumonomicon), new ItemStack(ConfigItems.itemFocusPrimal), new ItemStack(Items.golden_boots), new ItemStack(Blocks.grass), new ItemStack(ConfigBlocks.blockWoodenDevice, 1, 5), new ItemStack(ConfigBlocks.blockMetalDevice, 1, 8), new ItemStack(Items.wheat_seeds), new ItemStack(Blocks.wool), new ItemStack(Items.lead));

	}

	@SubscribeEvent
	public void onPlayerJump(LivingJumpEvent event) {
		if (event.entityLiving instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.entityLiving;
			boolean hasArmor = player.getCurrentArmor(0) != null && player.getCurrentArmor(0).getItem() == this;

			if (hasArmor && ThaumicTinkerer.proxy.armorStatus(player) && player.getCurrentArmor(0).getItemDamage() == 0)
				player.motionY += 0.3;
		}
	}

    //Attempt at fixing a bug with falling on SMP
    //In theory, this should be redundant
    @SubscribeEvent
    public void onFall(LivingFallEvent event) {
        if (event.entityLiving instanceof EntityPlayer) {

            EntityPlayer player = (EntityPlayer) event.entityLiving;
            boolean hasArmor = player.getCurrentArmor(0) != null && player.getCurrentArmor(0).getItem() == this;
            if (hasArmor) {
                event.distance = 0;
            }
        }
    }

	@SubscribeEvent(priority = EventPriority.HIGH)
	public void onLivingUpdate(LivingUpdateEvent event) {
		if (event.entityLiving instanceof EntityPlayer && event.entityLiving.worldObj.isRemote) {
			EntityPlayer player = (EntityPlayer) event.entityLiving;

			boolean highStepListed = playersWith1Step.contains(player.getGameProfile().getName());
			boolean hasHighStep = player.getCurrentArmor(0) != null && player.getCurrentArmor(0).getItem() == this;

			if (!highStepListed && (hasHighStep && ThaumicTinkerer.proxy.armorStatus(player) && player.getCurrentArmor(0).getItemDamage() == 0))
				playersWith1Step.add(player.getGameProfile().getName());


			if ((!hasHighStep || !ThaumicTinkerer.proxy.armorStatus(player) || player.getCurrentArmor(0).getItemDamage() == 1) && highStepListed) {
				playersWith1Step.remove(player.getGameProfile().getName());
				player.stepHeight = 0.5F;
			}
		}
	}

}