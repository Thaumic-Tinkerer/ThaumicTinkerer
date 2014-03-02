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
 * File Created @ [Dec 29, 2013, 9:33:23 PM (GMT)]
 */
package vazkii.tinkerer.common.core.handler.kami;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import vazkii.tinkerer.common.lib.LibMisc;
import vazkii.tinkerer.common.network.PacketManager;
import vazkii.tinkerer.common.network.packet.kami.PacketSoulHearts;

public class SoulHeartHandler {

	private static final String COMPOUND = LibMisc.MOD_ID;
	private static final String TAG_HP = "soulHearts";
	private static final int MAX_HP = 20;

	@ForgeSubscribe
	public void onPlayerDamage(LivingHurtEvent event) {
		if(event.entityLiving instanceof EntityPlayer && event.ammount > 0) {
			EntityPlayer player = (EntityPlayer) event.entityLiving;
			event.ammount = removeHP(player, (int) event.ammount);
			updateClient(player);
		}
	}

	public static void addHearts(EntityPlayer player) {
		addHP(player, 1);
		updateClient(player);
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
		if(player instanceof EntityPlayerMP && ((EntityPlayerMP) player).playerNetServerHandler!=null){
			PacketDispatcher.sendPacketToPlayer(PacketManager.buildPacket(new PacketSoulHearts(getHP(player))), (Player) player);
		}
	}

	// =============== METHODS COPIED FROM ENTITYLIVING ==================== //

	protected float applyArmorCalculations(EntityLivingBase entity, DamageSource par1DamageSource, float par2) {
		if (!par1DamageSource.isUnblockable()) {
			int i = 25 - entity.getTotalArmorValue();
			float f1 = par2 * i;
			//			this.damageArmor(par2);
			par2 = f1 / 25.0F;
		}

		return par2;
	}

	protected float applyPotionDamageCalculations(EntityLivingBase entity, DamageSource par1DamageSource, float par2) {
		int i;
		int j;
		float f1;

		if (entity.isPotionActive(Potion.resistance) && par1DamageSource != DamageSource.outOfWorld) {
			i = (entity.getActivePotionEffect(Potion.resistance).getAmplifier() + 1) * 5;
			j = 25 - i;
			f1 = par2 * j;
			par2 = f1 / 25.0F;
		}

		if (par2 <= 0.0F)
			return 0.0F;
		else {
			i = EnchantmentHelper.getEnchantmentModifierDamage(entity.getLastActiveItems(), par1DamageSource);

			if (i > 20)
				i = 20;

			if (i > 0 && i <= 20) {
				j = 25 - i;
				f1 = par2 * j;
				par2 = f1 / 25.0F;
			}

			return par2;
		}
	}
}