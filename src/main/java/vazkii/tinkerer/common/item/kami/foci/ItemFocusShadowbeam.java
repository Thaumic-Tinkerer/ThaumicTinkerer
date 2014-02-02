package vazkii.tinkerer.common.item.kami.foci;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.client.codechicken.core.vec.Vector3;
import thaumcraft.client.fx.FXSparkle;
import thaumcraft.common.config.Config;
import thaumcraft.common.items.wands.ItemWandCasting;
import vazkii.tinkerer.client.core.proxy.TTClientProxy;
import vazkii.tinkerer.common.ThaumicTinkerer;
import vazkii.tinkerer.common.item.foci.ItemModFocus;
import cpw.mods.fml.common.registry.EntityRegistry;

public class ItemFocusShadowbeam extends ItemModFocus {

	AspectList cost = new AspectList().add(Aspect.ORDER, 25).add(Aspect.ENTROPY, 25).add(Aspect.AIR, 15);

	public ItemFocusShadowbeam(int par1) {
		super(par1);

		EntityRegistry.registerModEntity(Beam.class, "ShadowbeamStaffBeam", 0, ThaumicTinkerer.instance, 0, 0, false);
	}

	@Override
	public void onUsingFocusTick(ItemStack stack, EntityPlayer player, int count) {
		ItemWandCasting wand = (ItemWandCasting) stack.getItem();

		if(!player.worldObj.isRemote && wand.consumeAllVis(stack, player, getVisCost(), true)) {
			int potency = EnchantmentHelper.getEnchantmentLevel(Config.enchPotency.effectId, wand.getFocusItem(stack));

			if(player.worldObj.rand.nextInt(10) == 0)
				player.worldObj.playSoundAtEntity(player, "thaumcraft:brain", 0.5F, 1F);

			Beam beam = new Beam(player.worldObj, player, potency);
			beam.updateUntilDead();
		}
	}

	@Override
	public boolean isVisCostPerTick() {
		return true;
	}

	@Override
	protected boolean hasOrnament() {
		return true;
	}

	@Override
	public int getFocusColor() {
		return 0x4B0053;
	}

	@Override
	public AspectList getVisCost() {
		return cost;
	}

	@Override
	public EnumRarity getRarity(ItemStack par1ItemStack) {
		return TTClientProxy.kamiRarity;
	}

	public static class Particle extends FXSparkle {

		public Particle(World world, double d, double d1, double d2, float f, int type, int m) {
			super(world, d, d1, d2, f, type, m);
			noClip = true;
		}

		@Override
		public void onUpdate() {
			super.onUpdate();
			if(particleAge > 1)
				setDead();
		}
	}

	public static class Beam extends EntityThrowable {

		int potency;
		Vector3 movementVector;
		final int maxTicks = 300;

		public Beam(World par1World, EntityLivingBase par2EntityLivingBase, int potency) {
			super(par1World, par2EntityLivingBase);

			this.potency = potency;
			setProjectileVelocity(motionX / 10, motionY / 10, motionZ / 10);
			movementVector = new Vector3(motionX, motionY, motionZ);
		}

		// Copy of setVelocity, because that is client only for some reason
		public void setProjectileVelocity(double par1, double par3, double par5) {
			this.motionX = par1;
			this.motionY = par3;
			this.motionZ = par5;

			if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F) {
				float f = MathHelper.sqrt_double(par1 * par1 + par5 * par5);
				this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(par1, par5) * 180.0D / Math.PI);
				this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(par3, (double)f) * 180.0D / Math.PI);
			}
		}

		@Override
		public void setThrowableHeading(double par1, double par3, double par5, float par7, float par8) {
			super.setThrowableHeading(par1, par3, par5, par7, par8);
			float f2 = MathHelper.sqrt_double(par1 * par1 + par3 * par3 + par5 * par5);
			par1 /= f2;
			par3 /= f2;
			par5 /= f2;
			par1 += 0.007499999832361937 * par8;
			par3 += 0.007499999832361937 * par8;
			par5 += 0.007499999832361937 * par8;
			par1 *= par7;
			par3 *= par7;
			par5 *= par7;
			motionX = par1;
			motionY = par3;
			motionZ = par5;
		}

		@Override
		protected void onImpact(MovingObjectPosition movingobjectposition) {
			if(movingobjectposition == null)
				return;

			if(movingobjectposition.entityHit != null) {
				if((MinecraftServer.getServer().isPVPEnabled() || !(movingobjectposition.entityHit instanceof EntityPlayer)) && movingobjectposition.entityHit != getThrower() && getThrower() instanceof EntityPlayer && !movingobjectposition.entityHit.worldObj.isRemote)
					movingobjectposition.entityHit.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer) getThrower()), 8 + potency);
				return;
			}

			Vector3 movementVec = new Vector3(motionX, motionY, motionZ);
			ForgeDirection dir = ForgeDirection.getOrientation(movingobjectposition.sideHit);
			Vector3 normalVector = new Vector3(dir.offsetX, dir.offsetY, dir.offsetZ).normalize();

			movementVector = normalVector.multiply(-2 * movementVec.dotProduct(normalVector)).add(movementVec);

			motionX = movementVector.x;
			motionY = movementVector.y;
			motionZ = movementVector.z;
		}

		@Override
		public void onUpdate() {
			motionX = movementVector.x;
			motionY = movementVector.y;
			motionZ = movementVector.z;

			super.onUpdate();

			if(ticksExisted > 2)
				ThaumicTinkerer.proxy.shadowSparkle(worldObj, (float) posX, (float) posY, (float) posZ, 6);

			++ticksExisted;
			if(ticksExisted >= maxTicks)
				setDead();
		}

		public void updateUntilDead() {
			while(!isDead)
				onUpdate();
		}

		@Override
		protected float getGravityVelocity() {
			return 0F;
		}
	}

	@Override
	public String getSortingHelper(ItemStack paramItemStack) {
		return "SHADOWBEAM";
	}
}
