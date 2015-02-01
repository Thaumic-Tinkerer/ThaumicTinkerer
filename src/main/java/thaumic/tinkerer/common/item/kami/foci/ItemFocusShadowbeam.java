package thaumic.tinkerer.common.item.kami.foci;

import cpw.mods.fml.common.registry.EntityRegistry;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.research.ResearchPage;
import thaumcraft.api.wands.FocusUpgradeType;
import thaumcraft.client.fx.particles.FXSparkle;
import thaumcraft.codechicken.lib.vec.Vector3;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.items.wands.ItemWandCasting;
import thaumic.tinkerer.common.ThaumicTinkerer;
import thaumic.tinkerer.common.core.proxy.TTCommonProxy;
import thaumic.tinkerer.common.item.foci.ItemFocusDeflect;
import thaumic.tinkerer.common.item.kami.ItemKamiResource;
import thaumic.tinkerer.common.lib.LibItemNames;
import thaumic.tinkerer.common.lib.LibResearch;
import thaumic.tinkerer.common.registry.ThaumicTinkererInfusionRecipe;
import thaumic.tinkerer.common.registry.ThaumicTinkererRecipe;
import thaumic.tinkerer.common.research.IRegisterableResearch;
import thaumic.tinkerer.common.research.KamiResearchItem;
import thaumic.tinkerer.common.research.ResearchHelper;

public class ItemFocusShadowbeam extends ItemModKamiFocus {

    AspectList cost = new AspectList().add(Aspect.ORDER, 25).add(Aspect.ENTROPY, 25).add(Aspect.AIR, 15);

    public ItemFocusShadowbeam() {
        super();

        EntityRegistry.registerModEntity(Beam.class, "ShadowbeamStaffBeam", 0, ThaumicTinkerer.instance, 0, 0, false);
    }

    @Override
    public void onUsingFocusTick(ItemStack stack, EntityPlayer player, int count) {
        ItemWandCasting wand = (ItemWandCasting) stack.getItem();

        if (!player.worldObj.isRemote && wand.consumeAllVis(stack, player, getVisCost(stack), true, false)) {
            int potency = wand.getFocusPotency(stack);

            if (player.worldObj.rand.nextInt(10) == 0)
                player.worldObj.playSoundAtEntity(player, "thaumcraft:brain", 0.5F, 1F);

            Beam beam = new Beam(player.worldObj, player, potency);
            beam.updateUntilDead();
        }
    }

    @Override
    public boolean isVisCostPerTick(ItemStack stack) {
        return true;
    }

    @Override
    protected boolean hasOrnament() {
        return true;
    }

    @Override
    public int getFocusColor(ItemStack stack) {
        return 0x4B0053;
    }

    @Override
    public AspectList getVisCost(ItemStack stack) {
        return cost;
    }

    @Override
    public FocusUpgradeType[] getPossibleUpgradesByRank(ItemStack itemStack, int rank) {
        return new FocusUpgradeType[0];
    }

    @Override
    public EnumRarity getRarity(ItemStack par1ItemStack) {
        return TTCommonProxy.kamiRarity;
    }

    @Override
    public String getItemName() {
        return LibItemNames.FOCUS_SHADOWBEAM;
    }

    @Override
    public IRegisterableResearch getResearchItem() {
        return (IRegisterableResearch) new KamiResearchItem(LibResearch.KEY_FOCUS_SHADOWBEAM, new AspectList().add(Aspect.DARKNESS, 2).add(Aspect.MAGIC, 1).add(Aspect.ELDRITCH, 1).add(Aspect.TAINT, 1), 14, 4, 5, new ItemStack(this)).setParents(LibResearch.KEY_ICHORCLOTH_ROD)
                .setPages(new ResearchPage("0"), ResearchHelper.infusionPage(LibResearch.KEY_FOCUS_SHADOWBEAM));

    }

    @Override
    public ThaumicTinkererRecipe getRecipeItem() {
        return new ThaumicTinkererInfusionRecipe(LibResearch.KEY_FOCUS_SHADOWBEAM, new ItemStack(this), 12, new AspectList().add(Aspect.DARKNESS, 65).add(Aspect.ELDRITCH, 32).add(Aspect.MAGIC, 50).add(Aspect.WEAPON, 32), new ItemStack(ConfigItems.itemFocusShock),
                new ItemStack(ThaumicTinkerer.registry.getFirstItemFromClass(ItemKamiResource.class)), new ItemStack(Items.arrow), new ItemStack(Items.diamond), new ItemStack(ConfigItems.itemFocusExcavation), new ItemStack(ThaumicTinkerer.registry.getFirstItemFromClass(ItemFocusDeflect.class)), new ItemStack(ThaumicTinkerer.registry.getFirstItemFromClass(ItemKamiResource.class)));

    }

    @Override
    public String getSortingHelper(ItemStack paramItemStack) {
        return "SHADOWBEAM";
    }

    public static class Particle extends FXSparkle {

        public Particle(World world, double x, double y, double z, float scale, float red, float green, float blue, int maxAge) {
            super(world,x, y, z, scale, red, green, blue, 1);
            this.particleMaxAge /= 3;
            this.particleMaxAge = maxAge;
            this.shrink = false;
            this.blendmode = 0;
            this.multiplier = 1;
            this.particle = 1;
            this.slowdown = false;
            this.noClip = true;
            this.setGravity(-0.7f);
//////////////////////////////////////////////////
//        For more check the parent class
//////////////////////////////////////////////////
//        this.leyLineEffect = false;
//        this.multiplier = 2;
//        this.shrink = true;
//        this.particle = 16;
//        this.tinkle = false;
//        this.blendmode = 1;
//        this.slowdown = true;
//        this.currentColor = 0;
//        if(f1 == 0.0F) {
//            f1 = 1.0F;
//        }
//        this.particleRed = f1;
//        this.particleGreen = f2;
//        this.particleBlue = f3;
//        this.particleGravity = 0.0F;
//        this.motionX = this.motionY = this.motionZ = 0.0D;
//        this.particleScale *= f;
//        this.particleMaxAge = 3 * m;
//        this.multiplier = m;
//        this.noClip = false;
//        this.setSize(0.01F, 0.01F);
//        this.prevPosX = this.posX;
//        this.prevPosY = this.posY;
//        this.prevPosZ = this.posZ;
//////////////////////////////////////////////////
        }
    }

    public static class Beam extends EntityThrowable {

        private int initialOffset = 2;
        private int length = 298;
        private int maxTicks = initialOffset + length;
        private int size = 4;

        private int potency;
        private Vector3 movementVector;

        private EntityLivingBase player;

        public Beam(World world, EntityLivingBase player, int potency) {
            super(world, player);

            this.potency = potency;
            this.player = player;
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
                this.prevRotationYaw = this.rotationYaw = (float) (Math.atan2(par1, par5) * 180.0D / Math.PI);
                this.prevRotationPitch = this.rotationPitch = (float) (Math.atan2(par3, (double) f) * 180.0D / Math.PI);
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
            if (movingobjectposition == null)
                return;

            if (movingobjectposition.entityHit != null) {
                if ((MinecraftServer.getServer().isPVPEnabled() || !(movingobjectposition.entityHit instanceof EntityPlayer)) && movingobjectposition.entityHit != getThrower() && getThrower() instanceof EntityPlayer && !movingobjectposition.entityHit.worldObj.isRemote)
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

            if (ticksExisted > initialOffset)
                ThaumicTinkerer.proxy.shadowSparkle(worldObj, (float) posX, (float) posY, (float) posZ, size);

            ++ticksExisted;

            if (ticksExisted >= maxTicks)
                setDead();
        }

        public void updateUntilDead() {
            while (!isDead)
                onUpdate();
        }

        @Override
        protected float getGravityVelocity() {
            return 0F;
        }
    }
}
