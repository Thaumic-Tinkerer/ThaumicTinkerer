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
 * File Created @ [3 Jul 2013, 17:46:58 (GMT)]
 */
package vazkii.tinkerer.entity;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import vazkii.tinkerer.ThaumicTinkerer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EntityLovePotion extends EntityThrowable {

	public EntityLovePotion(World par1World) {
		super(par1World);
	}

	public EntityLovePotion(World par1World, EntityPlayer par2EntityPlayer) {
		super(par1World, par2EntityPlayer);
	}

	@SideOnly(Side.CLIENT)
	public EntityLovePotion(World par1World, double par2, double par4, double par6, int par8) {
		this(par1World, par2, par4, par6);
	}

	public EntityLovePotion(World par1World, double par2, double par4, double par6) {
		super(par1World, par2, par4, par6);
	}

	@Override
	protected float getGravityVelocity() {
		return 0.03F;
	}

	@Override
	protected float func_70182_d() {
		return 0.7F;
	}

	@Override
	protected float func_70183_g() {
		return -20.0F;
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		ThaumicTinkerer.tcProxy.sparkle((float) posX, (float) posY, (float) posZ, 0);
	}

	@Override
	protected void onImpact(MovingObjectPosition mop) {
		spawnParticles();
		AxisAlignedBB bb = boundingBox.expand(4.0D, 2.0D, 4.0D);
		List eList = worldObj.getEntitiesWithinAABB(EntityAnimal.class, bb);
		Iterator i = eList.iterator();
		while (i.hasNext()) {
			EntityAnimal e = (EntityAnimal) i.next();
			if (e == null)
				continue;
		
			if (e.getGrowingAge() != 0)
				continue;
			
			e.inLove = 600;
			for (int var3 = 0; var3 < 7; var3++) {
				double var4 = rand.nextGaussian() * 0.02D;
				double var6 = rand.nextGaussian() * 0.02D;
				double var8 = rand.nextGaussian() * 0.02D;
				worldObj.spawnParticle("heart", e.posX + rand.nextFloat() * e.width * 2.0F - e.width, e.posY + 0.5D + rand.nextFloat() * e.height, e.posZ + rand.nextFloat() * e.width * 2.0F - e.width, var4, var6, var8);
			}
		}
		
		setDead();
	}

	private void spawnParticles() {
		double var8 = posX;
		double var10 = posY;
		double var12 = posZ;
		String var14 = "iconcrack_" + Item.potion.itemID;
		Random var7 = rand;
		for (int var15 = 0; var15 < 8; ++var15) {
			worldObj.spawnParticle(var14, var8, var10, var12, var7.nextGaussian() * 0.15D, var7.nextDouble() * 0.2D, var7.nextGaussian() * 0.15D);
		}
		float red = 1.0F;
		float green = 0.0F;
		float blue = 0.0F;
		String var19 = "spell";
		for (int var20 = 0; var20 < 100; ++var20) {
			double var39 = var7.nextDouble() * 4.0D;
			double var23 = var7.nextDouble() * Math.PI * 2.0D;
			double var25 = Math.cos(var23) * var39;
			double var27 = 0.01D + var7.nextDouble() * 0.5D;
			double var29 = Math.sin(var23) * var39;
			if (worldObj.isRemote) {
				EntityFX var31 = Minecraft.getMinecraft().renderGlobal.doSpawnParticle(var19, var8 + var25 * 0.1D, var10 + 0.3D, var12 + var29 * 0.1D, var25, var27, var29);
				if (var31 != null) {
					float var32 = 0.75F + var7.nextFloat() * 0.25F;
					var31.setRBGColorF(red * var32, green * var32, blue * var32);
					var31.multiplyVelocity((float)var39);
				}
			}
		}
		worldObj.playSoundEffect(posX + 0.5D, posY + 0.5D, posZ + 0.5D, "random.glass", 1.0F, worldObj.rand.nextFloat() * 0.1F + 0.9F);
	}
	
}
