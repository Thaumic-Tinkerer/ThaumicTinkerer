package vazkii.tinkerer.common.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import thaumcraft.client.codechicken.core.vec.Vector3;
import thaumcraft.client.fx.FXSparkle;
import vazkii.tinkerer.common.ThaumicTinkerer;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class ItemShadowStaff extends ItemMod {

	public ItemShadowStaff(int par1) {
		super(par1);
		setMaxStackSize(1);

		EntityRegistry.registerModEntity(Beam.class, "ShadowbeamStaffBeam", 0, ThaumicTinkerer.instance, 0, 0, false);

		// Sloppy registry, couldn't bother to actually write a lang
		setUnlocalizedName("ttinkerer:shadowStaff");
		LanguageRegistry.addName(this, "Shadowbeam Staff");
	}

	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
		par3EntityPlayer.setItemInUse(par1ItemStack, getMaxItemUseDuration(par1ItemStack));
		par3EntityPlayer.worldObj.playSoundAtEntity(par3EntityPlayer, "thaumcraft:brain", 1F, 1F);

		return par1ItemStack;
	}

	@Override
	public void onUsingItemTick(ItemStack stack, EntityPlayer player, int count) {
		if(!player.worldObj.isRemote) {
			Beam beam = new Beam(player.worldObj, player);
			beam.updateUntilDead();
		}
	}

	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack) {
		return 72000;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack) {
		return EnumAction.bow;
	}

	@Override
	public boolean isFull3D() {
		return true;
	}

	@Override
	public boolean shouldRotateAroundWhenRendering() {
		return true;
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
		Vector3 movementVector;
		final int maxTicks = 1000;

		public Beam(World par1World, EntityLivingBase par2EntityLivingBase) {
			super(par1World, par2EntityLivingBase);

			setVelocity(motionX / 10, motionY / 10, motionZ / 10);
			movementVector = new Vector3(motionX, motionY, motionZ);
		}

		public void setThrowableHeading(double par1, double par3, double par5, float par7, float par8)
		{
			super.setThrowableHeading(par1, par3, par5, par7, par8);
			float f2 = MathHelper.sqrt_double(par1 * par1 + par3 * par3 + par5 * par5);
			par1 /= (double)f2;
			par3 /= (double)f2;
			par5 /= (double)f2;
			par1 += 0.007499999832361937D * (double)par8;
			par3 += 0.007499999832361937D * (double)par8;
			par5 += 0.007499999832361937D * (double)par8;
			par1 *= (double)par7;
			par3 *= (double)par7;
			par5 *= (double)par7;
			this.motionX = par1;
			this.motionY = par3;
			this.motionZ = par5;
		}

		@Override
		protected void onImpact(MovingObjectPosition movingobjectposition) {
			if(movingobjectposition == null)
				return;

			if(movingobjectposition.entityHit != null) {
				if(movingobjectposition.entityHit != getThrower() && getThrower() instanceof EntityPlayer && !movingobjectposition.entityHit.worldObj.isRemote)
					movingobjectposition.entityHit.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer) getThrower()), 5);
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

			ThaumicTinkerer.proxy.shadowSparkle(worldObj, (float) posX, (float) posY, (float) posZ, 6);
			/**
			 * ItemShadowStaff.Particle particle = new ItemShadowStaff.Particle(world, x, y, z, 1.5F, 0, size);
			 * ClientHelper.minecraft().effectRenderer.addEffect(particle);
			 */

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
}
