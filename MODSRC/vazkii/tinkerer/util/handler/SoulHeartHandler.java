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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import org.lwjgl.opengl.GL11;

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
}
