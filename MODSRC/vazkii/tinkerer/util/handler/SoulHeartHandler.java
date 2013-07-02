/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the ThaumicTinkerer Mod.
 *
 * ThaumicTinkerer is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * ThaumicTinkerer is a Derivative Work on Thaumcraft 3.
 * Thaumcraft 3 © Azanor 2012
 * (http://www.minecraftforum.net/topic/1585216-)
 *
 * File Created @ [2 Jul 2013, 16:45:54 (GMT)]
 */
package vazkii.tinkerer.util.handler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.event.EventPriority;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import org.lwjgl.opengl.GL11;

import vazkii.tinkerer.item.ModItems;
import vazkii.tinkerer.lib.LibMisc;
import vazkii.tinkerer.lib.LibResources;
import vazkii.tinkerer.network.PacketManager;
import vazkii.tinkerer.network.packet.PacketSoulHeartSync;
import vazkii.tinkerer.util.helper.MiscHelper;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class SoulHeartHandler {

	@SideOnly(Side.CLIENT)
	public static int clientPlayerHP = 0;

	private static final String COMPOUND = LibMisc.MOD_ID;
	private static final String TAG_HP = "soulHearts";
	private static final int MAX_HP = 20;

	@SideOnly(Side.CLIENT)
	@ForgeSubscribe
	public void renderHealthBar(RenderGameOverlayEvent event) {
		if(event.type == ElementType.FOOD && clientPlayerHP > 0) {
			if(event instanceof RenderGameOverlayEvent.Post) {
				Minecraft mc = MiscHelper.getMc();

				int x = event.resolution.getScaledWidth() / 2 + 10;
				int y = event.resolution.getScaledHeight() - 39;

				GL11.glTranslatef(0F, 10F, 0F);
				mc.renderEngine.bindTexture(LibResources.GUI_SOUL_HEARTS);
				int it = 0;
				for(int i = 0; i < clientPlayerHP; i++) {
					boolean half = i == clientPlayerHP - 1 && clientPlayerHP % 2 != 0;
					if(half || i % 2 == 0) {
						renderHeart(x + it * 8, y, !half);
						it++;
					}
				}

				mc.renderEngine.bindTexture("/gui/icons.png");
			}

			GL11.glTranslatef(0F, -10F, 0F);
		}

		if(event.type == ElementType.AIR && event instanceof RenderGameOverlayEvent.Post && clientPlayerHP > 0)
			GL11.glTranslatef(0F, 10F, 0F);
	}

	@SideOnly(Side.CLIENT)
	private static void renderHeart(int x, int y, boolean full) {
		Tessellator tess = Tessellator.instance;
		float size = 1 / 16F;

		float startX = full ? 0 : 9 * size;
		float endX = full ? 9 * size : 1;
		float startY = 0;
		float endY = 9 * size;

		tess.startDrawingQuads();
		tess.addVertexWithUV(x, y + 9, 0, startX, endY);
		tess.addVertexWithUV(x + (full ? 9 : 7), y + 9, 0, endX, endY);
		tess.addVertexWithUV(x + (full ? 9 : 7), y, 0, endX, startY);
		tess.addVertexWithUV(x, y, 0, startX, startY);
		tess.draw();
	}

	@ForgeSubscribe
	public void onPlayerDamage(LivingHurtEvent event) {
		if(event.entityLiving instanceof EntityPlayer && event.ammount > 0) {
			EntityPlayer player = (EntityPlayer) event.entityLiving;
			event.ammount = removeHP(player, event.ammount);
			updateClient(player);
		}
	}

	@ForgeSubscribe(priority = EventPriority.LOWEST)
	public void onMobDamage(LivingHurtEvent event) {
		Entity attacker = event.source.getEntity();
		if(event.entityLiving instanceof IMob && attacker != null && attacker instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) attacker;
			ItemStack stack = player.getCurrentEquippedItem();

			if(stack != null && stack.itemID == ModItems.scythe.itemID) {
				int actualDamage = event.ammount;
				actualDamage = applyArmorCalculations(event.entityLiving, event.source, actualDamage);
				actualDamage = applyPotionDamageCalculations(event.entityLiving, event.source, actualDamage);

				if(actualDamage >= event.entityLiving.getHealth()) {
					// The mob will die from this attack, calculate soul hearts now...
					addHP(player, 2);
					updateClient(player);
					player.worldObj.playSoundAtEntity(player, "mob.blaze.death", 0.9F, 1F);
				}
			}
		}
	}

	public static boolean addHP(EntityPlayer player, int hp) {
		int current = getHP(player);
		if(current >= MAX_HP)
			return false;

		setHP(player, Math.min(MAX_HP, current + hp));
		return true;
	}

	// Returns overflow damage
	public static int removeHP(EntityPlayer player, int hp) {
		int current = getHP(player);
		int newHp = current - hp;
		setHP(player, Math.max(0, newHp));

		return Math.max(0, -newHp);
	}

	public static void setHP(EntityPlayer player, int hp) {
		NBTTagCompound cmp = getCompoundToSet(player);
		cmp.setInteger(TAG_HP, hp);
	}

	public static int getHP(EntityPlayer player) {
		NBTTagCompound cmp = getCompoundToSet(player);
		return cmp.hasKey(TAG_HP) ? cmp.getInteger(TAG_HP) : 0;
	}

	private static NBTTagCompound getCompoundToSet(EntityPlayer player) {
		NBTTagCompound cmp = player.getEntityData();
		if(!cmp.hasKey(COMPOUND))
			cmp.setCompoundTag(COMPOUND, new NBTTagCompound());

		return cmp.getCompoundTag(COMPOUND);
	}

	public static void updateClient(EntityPlayer player) {
		PacketManager.sendPacketToClient((Player) player, new PacketSoulHeartSync(getHP(player)));
	}

	// =============== METHODS COPIED FROM ENTITYLIVING ==================== //

    private int applyArmorCalculations(EntityLiving entity, DamageSource par1DamageSource, int par2) {
        if (!par1DamageSource.isUnblockable()) {
            int j = 25 - entity.getTotalArmorValue();
            int k = par2 * j + entity.carryoverDamage;
            //entity.damageArmor(par2);
            par2 = k / 25;
            entity.carryoverDamage = k % 25;
        }

        return par2;
    }

    private int applyPotionDamageCalculations(EntityLiving entity, DamageSource par1DamageSource, int par2) {
        int j;
        int k;
        int l;

        if (entity.isPotionActive(Potion.resistance)) {
            j = (entity.getActivePotionEffect(Potion.resistance).getAmplifier() + 1) * 5;
            k = 25 - j;
            l = par2 * k + entity.carryoverDamage;
            par2 = l / 25;
            entity.carryoverDamage = l % 25;
        }

        if (par2 <= 0)
            return 0;
        else  {
            j = EnchantmentHelper.getEnchantmentModifierDamage(entity.getLastActiveItems(), par1DamageSource);

            if (j > 20)
                j = 20;

            if (j > 0 && j <= 20) {
                k = 25 - j;
                l = par2 * k + entity.carryoverDamage;
                par2 = l / 25;
                entity.carryoverDamage = l % 25;
            }

            return par2;
        }
    }
}
